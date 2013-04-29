#include <stdio.h>
#include <fcntl.h> // creat
#include <string.h> // strlen

int main (void) {

	char date[] = "??.?? ??:??";
	char user[] = "moritz";
	char workingdir[] = "/home/User/moritz";

	char command[100];

	while(1) {
		// promt
		printf("[%s] %s@%s - ", date, user, workingdir);
		fgets(command, sizeof(command), stdin);

		// remove new line
    	command[strlen(command) - 1] = 0;

		// check if command is "build-in"
		if(strcmp(command, "quit") == 0) {
			return 0;
		} else if(strcmp(command, "version") == 0) {
			printf("HAW-Shell Version 0.1 (c) TeamNahme 2013\n");
		} else {
			printf("TODO\n");
		}
	}
}