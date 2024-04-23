/*********************** RETURN AN VALUE AND CONVERT TO INTEGER FROM A STRING BUFFER ******************************/


#include <stdio.h>
#include <string.h>


int main() {

    char str[] = "servo1=value"; 
    
    char *ptr = strchr(str, '=');  // find the first occurrence of '=' in the string

    if (ptr != NULL) 
    {
        int value = atoi(ptr+1);   // convert the substring after '=' to an integer
        printf("Value: %d\n", value);
    }

    return 0;
}