/*************** THIS PATTERN IS IN THE WORK *********************/

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
