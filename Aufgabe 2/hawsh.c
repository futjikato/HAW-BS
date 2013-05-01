#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <unistd.h>

#if _WIN32
    #define getcwd _getcwd
#endif // _WIN32

int main (void) {

	// get current working directory
	char* cwd;
    cwd = getcwd(0, 0);

    // get current user
    char* user = getlogin();

    // reserved for user input
	char command[100];

	while(1) {
		// promt
        printFormatedDate("[", "]");
		printf(" %s@%s - ", user, cwd);
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

/**
 * Print a formated date string
 */
int printFormatedDate (char* prefix, char* suffix) {
    time_t ct;
    struct tm *ts;
    
    ct = time(NULL);
    ts = localtime(&ct);
    
    printf("%s%d.%d.%d %d:%d%s", prefix, ts->tm_mday, ts->tm_mon, ts->tm_year + 1900, ts->tm_hour, ts->tm_min, suffix);
    return 0;
}