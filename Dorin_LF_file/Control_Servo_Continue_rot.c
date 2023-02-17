/*************** THIS PATTERN IS IN THE WORK *********************/


/*************** This version from web documentations example *********************/

#include <stdio.h>

    int servoStop = 1500;
    int servoSpeed = 50;
    int PotVal;
    int PotStop = 65;
    int ServoStart;
    
int main()
{
    scanf("%d",&PotVal);
    
    if(PotVal > PotStop)
    {
        int potSpeed = PotStop - PotVal;
        ServoStart = servoStop + (servoSpeed + potSpeed);
        printf("%d", ServoStart);
    }
    return 0;
}



/*************** This version is used in the project *********************/

#define SERVOMOTOR_CW_DC 550
#define SERVOMOTOR_STOP_DC 1100
#define SERVOMOTOR_CCW_DC 1100

void SERVO_vChangeAngle(uint32_t u32ServoAngle)
{
    // For compatibility reasons, rotation will be used as angle
    // Initialize static variable to retain the old value given to the angle
    static uint32_t u32OldAngle = SERVOMOTOR_STOP_DC;

    // Change the current angle only if it is CW or CCW and is different from the old angle given
    if ((u32OldAngle != u32ServoAngle) && (u32ServoAngle != SERVOMOTOR_STOP_DC))
    {
        // Update the PWM in order to change the rotation direction. Defaults to no rotation.
        switch (u32ServoAngle)
        {
        case SERVOMOTOR_CW_DC:
            PWM_vSetDutyCycle(SERVO_MOTOR_PWM_CHANNEL, SERVOMOTOR_CW_DC);
            break;
        case SERVOMOTOR_CCW_DC:
            PWM_vSetDutyCycle(SERVO_MOTOR_PWM_CHANNEL, SERVOMOTOR_CCW_DC);
            break;
        default:
            PWM_vSetDutyCycle(SERVO_MOTOR_PWM_CHANNEL, SERVOMOTOR_STOP_DC);
            break;
        }
    }
    // Stop the servomotor
    else
    {
        PWM_vSetDutyCycle(SERVO_MOTOR_PWM_CHANNEL, SERVOMOTOR_STOP_DC);
    }

    // Store the current rotation given
    u32OldAngle = u32ServoAngle;
}
