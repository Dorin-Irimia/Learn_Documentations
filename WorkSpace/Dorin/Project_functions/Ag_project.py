# This file is used for build a faculty project with a raspberry
# the scope of that project is to be a sound controled in PWM when one of the condition is True

import RPi.GPIO as GPIO
import time

SECOND = 3
PWM_ON = 50

# GPIO pin setup
KLEM1_SENSOR_PIN = 13
KLEM2_SENSOR_PIN = 14
BELT_SENSOR_PIN = 15
LIGHT_SENSOR_PIN = 16
DOOR_SENSOR_PIN = 17   # GPIO pin for door sensor
BUZZER_PIN = 18        # GPIO pin for buzzer or speaker
WARNING_PIN = 19

# Setup GPIO mode
GPIO.setmode(GPIO.BCM)

GPIO.setup(KLEM1_SENSOR_PIN, GPIO.IN, pull_up_down=GPIO.PUD_UP)  # KlemControl first position sensor pin as input with pull-up
GPIO.setup(KLEM2_SENSOR_PIN, GPIO.IN, pull_up_down=GPIO.PUD_UP)  # KlemControl second position sensor pin as input with pull-up
GPIO.setup(BELT_SENSOR_PIN, GPIO.IN, pull_up_down=GPIO.PUD_UP)  # DriverBelt sensor pin as input with pull-up
GPIO.setup(LIGHT_SENSOR_PIN, GPIO.IN, pull_up_down=GPIO.PUD_UP)  # CarLights sensor pin as input with pull-up
GPIO.setup(DOOR_SENSOR_PIN, GPIO.IN, pull_up_down=GPIO.PUD_UP)  # Door sensor pin as input with pull-up

GPIO.setup(BUZZER_PIN, GPIO.OUT)  # Buzzer pin as output
GPIO.setup(WARNING_PIN, GPIO.OUT)  # Buzzer pin as output


def readStatus(case):
    result = True
    if case == 1:
        time.sleep(0.1)
        result |= GPIO.input(KLEM1_SENSOR_PIN)
        result |= GPIO.input(KLEM2_SENSOR_PIN)
        result &= GPIO.input(DOOR_SENSOR_PIN)
        return result
    elif case == 2:
        time.sleep(0.1)
        result &= GPIO.input(KLEM1_SENSOR_PIN)
        result &= GPIO.input(KLEM2_SENSOR_PIN)
        result &= GPIO.input(BELT_SENSOR_PIN)
        return result
    elif case == 3:
        time.sleep(0.1)
        result != GPIO.input(KLEM1_SENSOR_PIN)
        result != GPIO.input(KLEM2_SENSOR_PIN)
        result &= GPIO.input(LIGHT_SENSOR_PIN)
        return result
    else:
        print(f"Error to readState for case = {case}")
        return False
    
# Setup PWM on buzzer pin at 1 kHz frequency
pwm_buzzer = GPIO.PWM(BUZZER_PIN, 1000)  # 1000 Hz frequency

try:
    while True:
        klem1_status = GPIO.input(KLEM1_SENSOR_PIN)
        klem2_status = GPIO.input(KLEM2_SENSOR_PIN)
        
        if klem1_status:
            door_status = GPIO.input(DOOR_SENSOR_PIN)
            if door_status == True:
                for time in SECOND:
                    pwm_buzzer(PWM_ON)
                    time.sleep(0.3)
                    pwm_buzzer.stop()
                    time.sleep(0.2)
                    if readStatus(1) == False:
                        break
        elif klem1_status == True and klem2_status == True:
            belt_status = GPIO.input(BELT_SENSOR_PIN)
            if belt_status:
                for time in SECOND:
                    pwm_buzzer(PWM_ON)
                    time.sleep(0.15)
                    pwm_buzzer.stop()
                    # time.sleep(0.1)
                    if readStatus(2) == False:
                        break
        elif klem1_status == False and klem2_status == False:
            light_status = GPIO.input(LIGHT_SENSOR_PIN)
            if light_status:
                for time in SECOND:
                    pwm_buzzer(PWM_ON)
                    time.sleep(0.3)
                    pwm_buzzer.stop()
                    # time.sleep(0.1) is definted in function readStatus 
                    if readStatus(3) == False:
                        break
        else:
            print(f"Error to check if the KlemenControl is activated. \n State for KLM1 = {klem1_status}\n  State for KLM2 = {klem2_status}")
            
        time.sleep(0.1)    # Small delay to prevent CPU overload
            
except KeyboardInterrupt:
    pass  # Exit on Ctrl+C

finally:
    pwm_buzzer.stop()
    GPIO.cleanup()  # Clean up GPIO on exit
