#include <xc.h>

#pragma config FOSC = HS             // Oscilator extern de tip HS (quartz de 8 MHz)
#pragma config WDTE = OFF            // Dezactiveaza watchdog timer
#pragma config PWRTE = ON            // Timer pornire activat
#pragma config MCLRE = ON            // Master Clear Reset pin activ
#pragma config CP = OFF              // Code protection dezactivat
#pragma config BOREN = ON            // Brown-out Reset activat
#pragma config LVP = OFF             // Low Voltage Programming dezactivat

#define _XTAL_FREQ 8000000

#define DOOR_SENSOR RB1
#define KLEM1_SENSOR RB4
#define KLEM2_SENSOR RB5
#define BELT_SENSOR RB6
#define LIGHT_SENSOR RB7

#define DOOR_LIGHT RA0
#define BELT_LIGHT RA1
#define LIGHTS_LIGHT RA2
#define WARNING_LIGHT RA3

#define PWM_DS CCPR1L

char door_status = 0;
char belt_status = 0;
char light_status = 0;
char door_pressed = 0;

void init() 
{
    TRISC = 0x00;                 // Configurare RC2 ca iesire (PWM)
    PR2 = 0xFF;                   // Setare perioada PWM
    CCP1CON = 0b00001100;         // Modul PWM activat
    T2CON = 0b00000111;           // Timer2 pornit, prescaler 1:16 (modificare pentru efect oscilant)
    PWM_DS = 0x00;                // Duty cycle inițial

    TRISB = 0b11111111;           
    TRISA = 0b00000000;           // RA0 configurat ca iesire
    ANSELH = 0x00;                // Dezactiveaza funcțiile analogice pentru AN8-AN13
    ANSEL = 0x00;                 // Dezactiveaza funcțiile analogice pentru AN0-AN7
}

void beltCheck()
{
    if((BELT_SENSOR == 1) && (KLEM2_SENSOR == 1) && (KLEM1_SENSOR == 1))
    {
        BELT_LIGHT = 1;
        if(belt_status == 0)
        {
            PWM_DS = 0x00;
            for (char time = 0; time < 3; time++)
            {
                if((DOOR_SENSOR == 1) && (door_pressed == 0))
                {
                    door_pressed = 1;
                    break;
                }
                else if((DOOR_SENSOR == 0) && (door_pressed == 1))
                {
                    door_pressed = 0;
                    door_status = 0;
                    DOOR_LIGHT = 0;
                }
                else{
                WARNING_LIGHT = 1;
                PWM_DS = 50;
                __delay_ms(300); 
                WARNING_LIGHT = 0;
                PWM_DS = 0;
                __delay_ms(150);
                }
                if((BELT_SENSOR == 0) || (KLEM2_SENSOR == 0) || (KLEM1_SENSOR == 0))
                {
                    BELT_LIGHT = 0;
                    belt_status = 0;
                    break;
                }
            }
            belt_status = 1;
        }
    }
    else
    {
        BELT_LIGHT = 0;
        belt_status = 0;
    }
}

void doorCheck()
{
    if(DOOR_SENSOR == 1)
    {   
        door_pressed = 1;
        if(KLEM1_SENSOR == 1)
        {
            DOOR_LIGHT = 1;
            if(door_status == 0)
            {
                PWM_DS = 0x00;
                for (char time = 0; time < 3; time++)
                {
                    PWM_DS = 0x50;
                    __delay_ms(500);
                    PWM_DS = 0x00;
                    __delay_ms(500);
                    if ((BELT_SENSOR == 1) && (KLEM2_SENSOR == 1))
                    {
                        BELT_LIGHT = 1;
                    }
                    if((DOOR_SENSOR == 0) || (KLEM1_SENSOR == 0))
                    {   
                        DOOR_LIGHT = 0;
                        break;
                    }
                }
                door_status = 1;
            }
        }
    }
    else
    {
        door_pressed = 0;
        DOOR_LIGHT = 0;
        door_status = 0;
    }
}

void lightCheck()
{
    if((LIGHT_SENSOR == 1) && (KLEM2_SENSOR == 0) && (KLEM1_SENSOR == 0))
    {
        DOOR_LIGHT = 0;
        door_status = 0;
        LIGHTS_LIGHT = 1;
        if(light_status == 0)
        {
            for (char time = 0; time < 3; time++)
            {   
                PWM_DS = 0x50;
                __delay_ms(150); 
                WARNING_LIGHT = 0;
                PWM_DS = 0x00;
                __delay_ms(300);
                if((LIGHT_SENSOR == 0) || (KLEM2_SENSOR == 1) || (KLEM1_SENSOR == 1))
                {
                    LIGHTS_LIGHT = 0;
                    light_status = 0;
                    break;
                }
            }
            light_status = 1;
        }
    }
    else
    {
        LIGHTS_LIGHT = 0;
        light_status = 0;
    }
}

void main() 
{
    init();
    DOOR_LIGHT = 0;
    BELT_LIGHT = 0;
    LIGHTS_LIGHT = 0;
    WARNING_LIGHT = 0;

    while(1) 
    {
        doorCheck();
        __delay_ms(10);
        beltCheck();
        __delay_ms(10);
        lightCheck();
        __delay_ms(10);
    }
}
