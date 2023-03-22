#include<ctype.h>
#include<unistd.h>
#include<stdlib.h>
#include<stdio.h>
#include<errno.h>
#include<fcntl.h>
#include<string.h>

int main(int argc, char* argv[]){
	//checks for correct number of arguments
	if (argc != 2){
		printf("Error: Expected one argument, found %d\n", argc-1);
		return -1;
	}

	char *filename = argv[1];

	int fd = open (filename,O_RDONLY);

	//if open() has an error prints a basic error description
	if (fd == -1){
		printf("Error: %s\n", strerror(errno));
		return (-1);
	}

	int numLines = 0;
	int numCharacters = 0;
	int numWords = 0;

	char buffer = '\0';
	int r;
	char prevChar = ' ';

	//Finds the number of charcters, lines, and words
	while (read(fd, &buffer,1) != 0){
		++numCharacters;

		if (buffer == '\n'){
			++numLines;
		}


		if (isspace(buffer) && !isspace(prevChar)){
			++numWords;
		}

		prevChar = buffer;

	}

	//checks if the last word was counted
	if (!isspace(prevChar) && prevChar != '\n' && prevChar != '\0'){
		++numWords;
	}

	//makes sure that the last line is counted even if there isn't a '\n'
	if (prevChar != '\n'){
		++numLines;
	}

	//checks if the file is empty, if empty sets lines to 0
	if (numCharacters == 0){
		numLines = 0;
	}


	printf("%d %d %d %s\n", numLines, numWords, numCharacters, filename);

	close(fd);

	return 0;
}
