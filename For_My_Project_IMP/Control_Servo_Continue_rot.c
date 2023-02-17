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


/*********************** This code is at the enternet *********************************/

#include <Servo.h>

Servo myservo; 

const int servoStop = 1500;
const int servoSpeed = 50; 
int potVal;
int potStop;
unsigned int servoStart;

void setup() { 
  
  myservo.attach(6);
  
}

void loop() {
  
  potVal = map (analogRead(0), 0, 1023, 0, 65000);  
  
    if (potVal > potStop) { 
      int potSpeed = potStop - potVal; 
      servoStart = servoStop + (servoSpeed + potSpeed); 
      myservo.writeMicroseconds(servoStart); 
    }
  
    else if (potVal < potStop) {   
      int potSpeed = potStop - potVal;
      servoStart = servoStop - (servoSpeed - potSpeed);
      myservo.writeMicroseconds(servoStart); 
    }
  
    else myservo.writeMicroseconds(servoStop);
  
  potStop = potVal; 
  servoStart = 0;
  
  delay(15);  
                         
}
