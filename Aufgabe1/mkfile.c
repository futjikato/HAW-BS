#include <stdio.h>
#include <fcntl.h> // creat
#include <string.h> // strlen

int main (void) {
    char name[30];
    
    printf("Name der neuen Datei: ");
    
    fgets(name, sizeof(name), stdin);
    
    // remove new line
    name[strlen(name) - 1] = 0;
    
    // file descriptor
    int fd = creat(name, S_IRWXU);
    if (fd < 0) {
   	 printf("Es ist ein Fehler aufgetreten!\n");
   	 return 1;
    } else {
   	 close(fd);
    }
    
    printf("Die Datei %s wurde erfolgreich angelegt!\n", name);
    
    return 0;
}