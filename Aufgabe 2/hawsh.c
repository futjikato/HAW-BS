#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <time.h>

#ifdef _WIN32
#include <windows.h>
#include <direct.h>
#define USERENV "USERNAME"
#define chdir _chdir
#define getcwd _getcwd
#else
#include <unistd.h>
#define USERENV "USER"
#endif


void printPrompt(char* user, char* cwd);


int main(int argc, char* argv[])
{
	char* user = getenv(USERENV);
	char command[255];
	char cwd[255];

	getcwd(cwd, 255);
	
	while (1) {
		printPrompt(user, cwd);
		fgets(command, sizeof(command), stdin);
		command[strlen(command) - 1] = 0;
		if (strcmp(command, "quit") == 0) {
			printf("Bye, bye.\n");
			return 0;
		} else if (strcmp(command, "version") == 0) {
			printf("HAW-Shell Version 0.1 (c) TeamNahme 2013\n");
		} else if (command[0] == '/') {
#ifdef _WIN32
				strcpy(command, command + 1);
#endif
			if (chdir(command) != 0) {
				printf("Unable to change dir : %s\n", strerror(errno));
			} else {
				strcpy(cwd, command);
			}
		} else if (command[strlen(command) - 1] == '&') {
#ifndef _WIN32
			// create new thread for command
			command[strlen(command) - 1] = 0;
			int pid = fork();
			if(pid == 0) {
				execvp(command, (char *[]){command, NULL});
				perror("execvp");
			}
#endif
		} else if (strlen(command) == 0) {
			continue;
		} else {
#ifdef _WIN32
			wchar_t wtext[255];
			STARTUPINFO info;
			PROCESS_INFORMATION processInfo;
			mbstowcs(wtext, command, strlen(command));
			if (CreateProcess(wtext, L"", NULL, NULL, TRUE, 0, NULL, NULL, &info, &processInfo)) {
				WaitForSingleObject(processInfo.hProcess, INFINITE);
				CloseHandle(processInfo.hProcess);
				CloseHandle(processInfo.hThread);
			}
#else
			int pid = fork();
			if(pid == 0) {
				execvp(command, (char *[]){command, NULL});
				perror("execvp");
			} else {
				waitpid(pid, 0, 0);
			}
#endif
		}
	}

	return 0;
}
void printPrompt(char* user, char* cwd)
{
	time_t rawtime = time(NULL);
	struct tm* now = localtime(&rawtime);
	printf("[%02d:%02d][%s:%s]# ", now->tm_hour, now->tm_min, user, cwd);
}