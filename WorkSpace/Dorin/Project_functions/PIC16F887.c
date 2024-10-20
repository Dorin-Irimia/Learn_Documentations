#include <xc.h>

// Configuratia pentru PIC16F887 (setarile pentru oscilator extern, watchdog, etc.)
#pragma config FOSC = HS             // Oscilator extern de tip HS (quartz de 8 MHz)
#pragma config WDTE = OFF            // Dezactiveaza watchdog timer
#pragma config PWRTE = ON            // Timer pornire activat
#pragma config MCLRE = ON            // Master Clear Reset pin activ
#pragma config CP = OFF              // Code protection dezactivat
#pragma config BOREN = ON            // Brown-out Reset activat
#pragma config LVP = OFF             // Low Voltage Programming dezactivat

// Definirea frecventei oscilatorului extern (8 MHz quartz)
#define _XTAL_FREQ 8000000

// Functie de initializare PWM pe pinul RC2/CCP1
void initPWM() {
    TRISC2 = 0;              // Configurare RC2 ca ie?ire (PWM)
    PR2 = 0xFF;              // Setare perioada PWM
    CCP1CON = 0b00001100;    // Modul PWM activat
    T2CON = 0b00000100;      // Timer2 pornit, prescaler 1:1
    CCPR1L = 0xFF;           // Duty cycle initial
}

// Functie de initializare pentru întreruperi pe buton (RB0)
void initInterrupts() {
    TRISB0 = 1;              // Configurare RB0 ca intrare
    INTCONbits.GIE = 1;      // Global Interrupt Enable
    INTCONbits.PEIE = 1;     // Peripheral Interrupt Enable
    INTCONbits.INTE = 1;     // External Interrupt Enable pe RB0
    OPTION_REGbits.INTEDG = 0; // Front descendent (apasarea butonului)
}

// Rutina de tratare a întreruperii (ISR)
void __interrupt() ISR() {
    if (INTCONbits.INTF) {
        // La apasarea butonului pe RB0, schimba duty cycle-ul PWM
        CCPR1L = (CCPR1L == 0x80) ? 0x20 : 0x80; // Comuta duty cycle între 50% ?i 25%
		TRISB1 = 0;
		RB1 = 1;
		_delay(500);
		RB1 = 0;
		

        INTCONbits.INTF = 0; // Reseteaza flag-ul de întrerupere
    }
}

void main() {
    initPWM();                // Ini?ializeaza PWM pe RC2
    initInterrupts();         // Ini?ializeaza întreruperi pentru buton pe RB0

    while(1) {
        // Bucla principala, codul principal va rula aici
        // PWM-ul continua sa functioneze independent
    }
}
