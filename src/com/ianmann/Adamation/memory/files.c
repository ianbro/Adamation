#include <stdio.h>
#include <stdlib.h>

#include "files.h"

/*
 * Structure for a file containing information for a Neuron. This contains
 * all the members that a Neuron does.
 * See Neuron struct in
 * src/com/ianmann/Adamation/memory/neuron.h for information on the fields.
 */
 struct NeuronFile {
   
   char *path;
   
   char *data;
   
 };
typedef struct NeuronFile NeuronFile;

/* Sets the first 8 bytes in the header to the format of a neuron header.
  Headers for neuron files are:
  
    0xff ".nrn\r\n" 0x00
*/
void setnrnheader(char header[]) {
  header[0] = 0xff;
  header[1] = '.';
  header[2] = 'n';
  header[3] = 'r';
  header[4] = 'n';
  header[5] = '\r';
  header[6] = '\n';
  header[7] = 0x00;
}

/* Determines whether the file contains the correct header.
  The header should be in the format:
  
    0xff ".nrn\r\n" 0x00
    
  If the header does include the correct format, then 1 is returned. Otherwise
  a 0 is returned.
*/
int matchnrnheader(char file[]) {
  if (file[0] == (char) 0xff && file[1] == (char) '.' &&
      file[2] == (char) 'n' && file[3] == (char) 'r' &&
      file[4] == (char) 'n' && file[5] == (char) '\r' &&
      file[6] == (char) '\n' && file[7] == (char) 0x00)
  {
    return 1;
  } else {
    return 0;
  }
}

// void storenrn(Neuron _nrn) {
//   unsigned char header[] = malloc(sizeof(char) * 20);
//   setheader(header);
//   unsigned char typesignifyer[] = {'t', 'y', 'p', 'e', ':'};
//   
// }

char *extendfile(char file[], int fsize, char toextend[], int tesize) {
  int newsize = fsize + tesize;
  char *newarray = malloc(sizeof(char) * newsize);
  int i = 0;
  
  for ( ; i < fsize; i ++) {
    newarray[i] = file[i];
  }
  for ( ; (i-fsize) < tesize; i++) {
    newarray[i] = toextend[(i-fsize)];
  }
  
  return newarray;
}
