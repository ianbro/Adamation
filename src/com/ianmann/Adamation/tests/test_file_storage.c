#include <stdio.h>
#include <stdlib.h>

#include "../memory/files.h"

int main(int argc, char *argv[]) {
  char file[20];
  setnrnheader(file);
  printf("%c\n", file[4]);
  exit(0);
}
