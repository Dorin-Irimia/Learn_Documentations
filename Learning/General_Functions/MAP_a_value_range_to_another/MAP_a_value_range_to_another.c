/*********************** THIS FILE SHALL IMPLEMENT A FUNCTION FOR CONVERT A VALUE RANGE ONTO ANOTHER RANGE ***********************/
/*********************** https://stackoverflow.com/questions/5731863/mapping-a-numeric-range-onto-another ***********************/


#include <stdio.h>

int OutputValue;
int InputValue;

double InputStart = 0;      // 0 is first value at the first range
double InputStop = 10;      // 10 is last value at the first range

double OutputStart = 0;     // 0 is first value at the second range
double OutputStop = 100;    // 100 is a last value at the second range

int main()
{
    OutputValue = ( InputValue - InputStart ) / ( InputStop - InputStart ) * ( OutputStop - OutputStart ) + OutputStart;  

    /*  For example 
        InputValue = 4;
        OutputValue = (4-0)/(10-0)*(100-0)+0;
        'Result = 40';  
    */

    printf("Result = %d", OutputValue);

    return 0;
}