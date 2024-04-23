/******************* Convert a string to integer **************/

#include <stdio.h>
#include <string.h>
#include <stdlib.h>

char Buffer[20];
int Integer;

int main()
{
    strcpy(Buffer,"123");    //for example string is 123

   Integer = atoi(Buffer);

    printf("%d", Integer);     //resultat is 123     

    return 0;
}

