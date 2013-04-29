#include <stdio.h>
#include <fcntl.h> // creat
#include <string.h> // strlen

int main (void) {

	char date[] = "??.?? ??:??";
	char user[] = "moritz";
	char workingdir[] = "/home/User/moritz";

	char command[100];

	while(1) {
		printf("[%s] %s@%s - ", date, user, workingdir);
		fgets(command, sizeof(command), stdin);

	}
}