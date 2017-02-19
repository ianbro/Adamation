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

void *setheader(char header[]) {
  header[0] = 0xff;
  header[1] = '.';
  header[2] = 'n';
  header[3] = 'r';
  header[4] = 'n';
  header[5] = 'r';
  header[6] = 'n';
  header[7] = 0x00;
}

void *storenrn(Neuron _nrn) {
  unsigned char header[] = malloc(sizeof(char) * 20);
  setheader(header);
  unsigned char typesignifyer[] = {'t', 'y', 'p', 'e', ':'};
  
}