#include <xc.h>

#pragma config FOSC = INTRC_NOCLKOUT   
#pragma config WDTE = OFF            
#pragma config PWRTE = ON             
#pragma config MCLRE = ON             
#pragma config CP = OFF               
#pragma config BOREN = OFF           
#pragma config LVP = OFF             

#define _XTAL_FREQ 8000000  

#define DOOR_SENSOR RB3
#define KLEM15_SENSOR RB4
#define KLEM31_SENSOR RB5
#define BELT_SENSOR RB6
#define LIGHT_SENSOR RB7

#define DOOR_LIGHT RA0
#define BELT_LIGHT RA1
#define LIGHTS_LIGHT RA2

#define PWM_DS CCPR1L
#define ON 62
#define OFF 0
#define PRESCALER 8
volatile unsigned char overflow_count = 0;
volatile unsigned int timer_ms = 0;

char door_status = 0;
char belt_status = 0;
char light_status = 0;
char door_pressed = 0;

void init_oscillator() {
    OSCCONbits.IRCF = 0b111;  
    OSCCONbits.SCS = 1;       
}

void init() {
    TRISC = 0x50;                 
    PR2 = 124;                 
    CCP1CON = 0b00001100;       
    T2CON = 0b0000111;            
    PWM_DS = OFF;                
    CCP1CONbits.DC1B = OFF;

    TRISB = 0b11111111;          
    TRISA = 0b00000000;           
    ANSELH = 0x00;               
    ANSEL = 0x00;               

    OPTION_REG = 0b0000010;  
    TMR0 = 6;                           
    INTCONbits.TMR0IE = 1;      
    INTCONbits.PEIE = 1;        
    INTCONbits.GIE = 1;      
	OPTION_REGbits.nRBPU = 1;  
}

void __interrupt() isr() {
    if (INTCONbits.TMR0IF) {
        TMR0 = 6;               
        overflow_count++;
        
        if (overflow_count >= 10) {  
            timer_ms += 10;
            overflow_count = 0;     
        }
      INTCONbits.TMR0IF = 0;  
   }
}

void beltCheck() {

unsigned int start_time = 0;
unsigned int state = 0;
	__delay_ms(10);
	if((BELT_SENSOR == 1) && ((KLEM31_SENSOR == 1) && (KLEM15_SENSOR == 1))) {
        BELT_LIGHT = 1;
		PWM_DS = OFF;
        if(belt_status == 0) {
			timer_ms = 300;
			while(timer_ms < 3300){
					__delay_ms(10);
					if((BELT_SENSOR == 0) || (KLEM31_SENSOR == 0) || (KLEM15_SENSOR == 0)) {
						BELT_LIGHT = 0;
						belt_status = 0;
						PWM_DS = OFF;
						break;
					}
					if (timer_ms - start_time >= 300 && state == 0) { 
						PWM_DS = ON;    
						BELT_LIGHT = 0;
						start_time = timer_ms; 
						state = 1;      
					} 
					else if (timer_ms - start_time >= 200 && state == 1) {
								PWM_DS = OFF;     
								BELT_LIGHT = 1;
								start_time = timer_ms;
								state = 0;
					}
					__delay_ms(10);
					if((DOOR_SENSOR == 1) && (door_pressed == 0)) {
						door_pressed = 1;
						PWM_DS = OFF;
						break;
					}
					else if((DOOR_SENSOR == 0) && (door_pressed == 1)) {
								door_pressed = 0;
								door_status = 0;
								DOOR_LIGHT = 0;   
					}
            }
			PWM_DS = OFF; 
            belt_status = 1;
        }
    }
    else{
        BELT_LIGHT = 0;
        belt_status = 0;
		PWM_DS = OFF; 
    }
}


void doorCheck() {
unsigned int start_time = 0;
unsigned int state = 0;
	__delay_ms(10);
    if(DOOR_SENSOR == 1) {   
        door_pressed = 1;
		__delay_ms(10);
        if(KLEM15_SENSOR == 1) {
            if(door_status == 0){
				DOOR_LIGHT = 1;
                PWM_DS = OFF;
				timer_ms = 500;
				while(timer_ms < 3500){
						if (timer_ms - start_time >= 500 && state == 0) { 
							PWM_DS = ON;    
							start_time = timer_ms;
							state = 1;
						}
						else if (timer_ms - start_time >= 500 && state == 1) {
									PWM_DS = OFF;    
									start_time = timer_ms; 
									state = 0;       
						}
						__delay_ms(10);
						if ((BELT_SENSOR == 1) && (KLEM31_SENSOR == 1)) {
							BELT_LIGHT = 1;
						}
						if(DOOR_SENSOR == 0) {
							__delay_ms(10);
							DOOR_LIGHT = 0;
							door_pressed = 0;
							door_status = 0;
							PWM_DS = OFF;
							break;
						}
						else if (KLEM15_SENSOR == 0){
								__delay_ms(10);
								DOOR_LIGHT = 0;	
								door_status = 0;
								PWM_DS = OFF;
								break;
						}				
                }
				PWM_DS = OFF;
                door_status = 1;
            }
        }
		else{
				DOOR_LIGHT = 0;
				door_status = 0;
				PWM_DS = OFF;
		}
    }
    else{
        door_pressed = 0;
        DOOR_LIGHT = 0;
        door_status = 0;
		PWM_DS = OFF;
    }
}

void lightCheck() {
unsigned int start_time = 0;
unsigned int state = 0;
	__delay_ms(10);
    if((LIGHT_SENSOR == 1) && (KLEM31_SENSOR == 0) && (KLEM15_SENSOR == 0)) {
        DOOR_LIGHT = 0;
		door_status = 0;
		LIGHTS_LIGHT = 1;
        if(light_status == 0){
			timer_ms = 150;
			while(timer_ms < 3150){
					if((LIGHT_SENSOR == 0) || (KLEM31_SENSOR == 1) || (KLEM15_SENSOR == 1)) {
						LIGHTS_LIGHT = 0;
						light_status = 0;
						PWM_DS = OFF;
						break;
					}
					 if ((timer_ms - start_time >= 150) && state == 0) { 
							PWM_DS = ON;  
							start_time = timer_ms;
							state = 1;  
						} 
					else if ((timer_ms - start_time >= 100 && state == 1)) { 
						   PWM_DS = OFF;    
						  start_time = timer_ms;
							state = 0;    
						}  
            }
			PWM_DS = OFF;
            light_status = 1;
       }
    }
    else {
        LIGHTS_LIGHT = 0;
        light_status = 0;
		PWM_DS = OFF;
    }
}

void main() {
    init();
	init_oscillator();

    DOOR_LIGHT = 0;
    BELT_LIGHT = 0;
    LIGHTS_LIGHT = 0;

	while(1){
		__delay_ms(100);
			doorCheck();
		  __delay_ms(100);
			beltCheck();
		 __delay_ms(100);
			lightCheck();
		
		}
}

