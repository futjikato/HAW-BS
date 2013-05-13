#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <direct.h>
#include <time.h>

#ifdef _WIN32
#include <windows.h>
#define USERENV "USERNAME"
#define chdir _chdir
#else
#define USERENV "USER"
#endif


void printPrompt(char* user, char* cwd);


int main(int argc, char* argv[])
{
	char* user = getenv(USERENV);
	char command[255];
	char* cwd = (char*)malloc(255);
	char* occurrence;

#ifdef _WIN32
	strcpy(cwd, argv[0]);
	occurrence = strrchr(cwd, '\\');
	cwd[occurrence - cwd] = 0;
#else
	cwd = getcwd(0, 0);
#endif
	
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
			char tmp[255];
			strcpy(tmp, command + 1);
			if (chdir(tmp) != 0) {
				printf("Unable to change dir : %s\n", strerror(errno));
			} else {
				strcpy(cwd, tmp);
			}
#else
			if (chdir(command) != 0) {
				printf("Unable to change dir : %s\n", strerror(errno));
			} else {
				cwd = command;
			}
#endif
		} else if (command[strlen(command) - 1] == '&') {
			// create new thread for command
			puts("Start new thread for given command");
			command[strlen(command) - 1] = 0;
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
				execve(command, 0, 0);
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