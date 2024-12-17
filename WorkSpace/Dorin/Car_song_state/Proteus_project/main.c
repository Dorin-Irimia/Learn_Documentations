#include <xc.h>

#pragma config FOSC = INTRC_NOCLKOUT   // Configurare oscilator intern, iesire dezactivata
#pragma config WDTE = OFF             // Dezactivare Watchdog Timer
#pragma config PWRTE = ON             // Activare Power-up Timer
#pragma config MCLRE = ON             // Pinul MCLR configurat ca intrare
#pragma config CP = OFF               // Dezactivare protectie cod
#pragma config BOREN = OFF            // Dezactivare Brown-out Reset
#pragma config LVP = OFF              // Dezactivare programare low-voltage

#define _XTAL_FREQ 8000000            // Frecventa oscilatorului definit la 8 MHz

#define DOOR_SENSOR RB3               // Senzor usa definit pe RB3
#define KLEM15_SENSOR RB4             // Senzor KLEM15 definit pe RB4
#define KLEM31_SENSOR RB5             // Senzor KLEM31 definit pe RB5
#define BELT_SENSOR RB6               // Senzor centura definit pe RB6
#define LIGHT_SENSOR RB7              // Senzor lumina definit pe RB7

#define DOOR_LIGHT RA0                // Indicator LED usa definit pe RA0
#define BELT_LIGHT RA1                // Indicator LED centura definit pe RA1
#define LIGHTS_LIGHT RA2              // Indicator LED lumina definit pe RA2

#define PWM_DS CCPR1L                 // Control PWM definit pe registrul CCPR1L
#define ON 62                         // Valoare PWM pentru pornit
#define OFF 0                         // Valoare PWM pentru oprit
#define PRESCALER 8                   // Valoarea prescaler-ului utilizat
volatile unsigned char overflow_count = 0; // Contor pentru overflow Timer0
volatile unsigned int timer_ms = 0;        // Contor pentru timp in milisecunde

char door_status = 0;                // Stare usa
char belt_status = 0;                // Stare centura
char light_status = 0;               // Stare lumina
char door_pressed = 0;               // Flag pentru apasare usa

// Functie pentru configurarea oscilatorului intern
void init_oscillator() {
    OSCCONbits.IRCF = 0b111;  // Setare frecventa interna la 8 MHz
    OSCCONbits.SCS = 1;       // Selectare oscilator intern
}

// Functie de initializare pentru module si periferice
void init() {
    TRISC = 0x50;                 // RC4 si RC6 configurate ca intrari (PWM si senzori)
    PR2 = 124;                    // Setare perioada PWM pentru 1 ms
    CCP1CON = 0b00001100;         // Modul PWM activat
    T2CON = 0b0000111;            // Pornire Timer2 cu prescaler 1:16
    PWM_DS = OFF;                 // PWM oprit initial
    CCP1CONbits.DC1B = OFF;       // Duty cycle pe 0

    TRISB = 0b11111111;           // Portul B configurat ca intrare
    TRISA = 0b00000000;           // Portul A configurat ca iesire
    ANSELH = 0x00;                // Dezactivare intrari analogice pe AN8-AN13
    ANSEL = 0x00;                 // Dezactivare intrari analogice pe AN0-AN7

    OPTION_REG = 0b0000010;       // Configurare Timer0 cu prescaler 1:8
    TMR0 = 6;                     // Valoare initiala pentru Timer0
    INTCONbits.TMR0IE = 1;        // Activare intrerupere Timer0
    INTCONbits.PEIE = 1;          // Activare intreruperi periferice
    INTCONbits.GIE = 1;           // Activare intreruperi globale
    OPTION_REGbits.nRBPU = 1;     // Dezactivare rezistente pull-up pe PORTB
}

// Rutina de intrerupere
void __interrupt() isr() {
    if (INTCONbits.TMR0IF) {      // Verificare flag de overflow pentru Timer0
        TMR0 = 6;                // Resetare valoare Timer0
        overflow_count++;        // Incrementare contor overflow

        if (overflow_count >= 10) {  // Daca 10 overflow-uri au avut loc (~10 ms)
            timer_ms += 10;         // Incrementare contor timp
            overflow_count = 0;     // Resetare contor overflow
        }
        INTCONbits.TMR0IF = 0;      // Resetare flag de intrerupere Timer0
   }
}

// Functie pentru verificarea starii centurii
void beltCheck() {
    unsigned int start_time = 0;  // Timp initial
    unsigned int state = 0;       // Stare PWM
    __delay_ms(10);               // Delay de stabilizare

    if ((BELT_SENSOR == 1) && ((KLEM31_SENSOR == 1) && (KLEM15_SENSOR == 1))) {
        BELT_LIGHT = 1;           // Aprindere LED centura
        PWM_DS = OFF;             // Dezactivare PWM
        if (belt_status == 0) {   // Daca centura nu este activata
            timer_ms = 300;       // Resetare temporizator
            while (timer_ms < 3300) {  // Bucla timp de 3 secunde
                __delay_ms(10);       // Delay pentru stabilitate
                if ((BELT_SENSOR == 0) || (KLEM31_SENSOR == 0) || (KLEM15_SENSOR == 0)) {
                    BELT_LIGHT = 0;   // Oprire LED centura
                    belt_status = 0;  // Resetare stare centura
                    PWM_DS = OFF;     // Dezactivare PWM
                    break;            // Iesire din bucla
                }
                if (timer_ms - start_time >= 300 && state == 0) { 
                    PWM_DS = ON;    // Activare PWM
                    BELT_LIGHT = 0; // Oprire LED centura
                    start_time = timer_ms; // Actualizare timp initial
                    state = 1;      // Trecere in stare ON
                } 
                else if (timer_ms - start_time >= 200 && state == 1) {
                    PWM_DS = OFF;  // Dezactivare PWM
                    BELT_LIGHT = 1; // Aprindere LED centura
                    start_time = timer_ms; // Actualizare timp initial
                    state = 0;      // Trecere in stare OFF
                }
                __delay_ms(10);      // Delay pentru stabilitate
                if ((DOOR_SENSOR == 1) && (door_pressed == 0)) {
                    door_pressed = 1; // Flag usa apasata
                    PWM_DS = OFF;    // Dezactivare PWM
                    break;           // Iesire din bucla
                }
                else if ((DOOR_SENSOR == 0) && (door_pressed == 1)) {
                    door_pressed = 0; // Resetare flag usa apasata
                    door_status = 0;  // Resetare stare usa
                    DOOR_LIGHT = 0;   // Oprire LED usa
                }
            }
            PWM_DS = OFF;          // Dezactivare PWM la final
            belt_status = 1;       // Activare stare centura
        }
    }
    else {
        BELT_LIGHT = 0;           // Oprire LED centura
        belt_status = 0;          // Resetare stare centura
        PWM_DS = OFF;             // Dezactivare PWM
    }
}

// Functie pentru verificarea starii usii
void doorCheck() {
    unsigned int start_time = 0;  // Timp initial
    unsigned int state = 0;       // Stare PWM
    __delay_ms(10);               // Delay de stabilizare
    if (DOOR_SENSOR == 1) {       // Daca senzorul de usa este activ
        door_pressed = 1;         // Flag usa apasata
        __delay_ms(10);           // Delay de stabilizare
        if (KLEM15_SENSOR == 1) { // Daca KLEM15 este activ
            if (door_status == 0) {
                DOOR_LIGHT = 1;   // Aprindere LED usa
                PWM_DS = OFF;     // Dezactivare PWM
                timer_ms = 500;   // Resetare temporizator
                while (timer_ms < 3500) { // Bucla timp de 3 secunde
                    if (timer_ms - start_time >= 500 && state == 0) { 
                        PWM_DS = ON;  // Activare PWM
                        start_time = timer_ms; // Actualizare timp initial
                        state = 1;    // Trecere in stare ON
                    }
                    else if (timer_ms - start_time >= 500 && state == 1) {
                        PWM_DS = OFF; // Dezactivare PWM
                        start_time = timer_ms; // Actualizare timp initial
                        state = 0;    // Trecere in stare OFF
                    }
                    __delay_ms(10);  // Delay pentru stabilitate
                    if ((BELT_SENSOR == 1) && (KLEM31_SENSOR == 1)) {
                        BELT_LIGHT = 1; // Aprindere LED centura
                    }
                    if (DOOR_SENSOR == 0) { // Daca senzorul de usa este dezactivat
                        __delay_ms(10);  // Delay pentru stabilitate
                        DOOR_LIGHT = 0;  // Oprire LED usa
                        door_pressed = 0; // Resetare flag usa apasata
                        door_status = 0;  // Resetare stare usa
                        PWM_DS = OFF;    // Dezactivare PWM
                        break;           // Iesire din bucla
                    }
                    else if (KLEM15_SENSOR == 0) {
                        __delay_ms(10);  // Delay pentru stabilitate
                        DOOR_LIGHT = 0;  // Oprire LED usa
                        door_status = 0; // Resetare stare usa
                        PWM_DS = OFF;    // Dezactivare PWM
                        break;           // Iesire din bucla
                    }                
                }
                PWM_DS = OFF;      // Dezactivare PWM la final
                door_status = 1;   // Activare stare usa
            }
        }
        else {
            DOOR_LIGHT = 0;       // Oprire LED usa
            door_status = 0;      // Resetare stare usa
            PWM_DS = OFF;         // Dezactivare PWM
        }
    }
    else {
        door_pressed = 0;         // Resetare flag usa apasata
        DOOR_LIGHT = 0;           // Oprire LED usa
        door_status = 0;          // Resetare stare usa
        PWM_DS = OFF;             // Dezactivare PWM
    }
}

// Functie pentru verificarea starii luminilor
void lightCheck() {
    unsigned int start_time = 0;  // Timp initial
    unsigned int state = 0;       // Stare PWM
    __delay_ms(10);               // Delay de stabilizare
    if ((LIGHT_SENSOR == 1) && (KLEM31_SENSOR == 0) && (KLEM15_SENSOR == 0)) {
        DOOR_LIGHT = 0;           // Oprire LED usa
        door_status = 0;          // Resetare stare usa
        LIGHTS_LIGHT = 1;         // Aprindere LED lumina
        if (light_status == 0) {
            timer_ms = 150;       // Resetare temporizator
            while (timer_ms < 3150) { // Bucla timp de 3 secunde
                if ((LIGHT_SENSOR == 0) || (KLEM31_SENSOR == 1) || (KLEM15_SENSOR == 1)) {
                    LIGHTS_LIGHT = 0; // Oprire LED lumina
                    light_status = 0; // Resetare stare lumina
                    PWM_DS = OFF;     // Dezactivare PWM
                    break;            // Iesire din bucla
                }
                if ((timer_ms - start_time >= 150) && state == 0) { 
                    PWM_DS = ON;    // Activare PWM
                    start_time = timer_ms; // Actualizare timp initial
                    state = 1;      // Trecere in stare ON
                } 
                else if ((timer_ms - start_time >= 100 && state == 1)) { 
                    PWM_DS = OFF;   // Dezactivare PWM
                    start_time = timer_ms; // Actualizare timp initial
                    state = 0;      // Trecere in stare OFF
                }  
            }
            PWM_DS = OFF;          // Dezactivare PWM la final
            light_status = 1;      // Activare stare lumina
       }
    }
    else {
        LIGHTS_LIGHT = 0;         // Oprire LED lumina
        light_status = 0;         // Resetare stare lumina
        PWM_DS = OFF;             // Dezactivare PWM
    }
}

// Functie principala
void main() {
    init();                      // Initializare module
    init_oscillator();           // Initializare oscilator

    DOOR_LIGHT = 0;              // Oprire LED usa
    BELT_LIGHT = 0;              // Oprire LED centura
    LIGHTS_LIGHT = 0;            // Oprire LED lumina

    while (1) {
        __delay_ms(100);         // Delay pentru stabilitate
        doorCheck();             // Verificare stare usa
        __delay_ms(100);         // Delay pentru stabilitate
        beltCheck();             // Verificare stare centura
        __delay_ms(100);         // Delay pentru stabilitate
        lightCheck();            // Verificare stare lumina
    }
}
