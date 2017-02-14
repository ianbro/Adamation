#################################################################
# variables, flags for CFLAGS
# -Wall flags all errors
# -Werror treats warning as an error
# -g  includes symbol table to help gdb
#
#################################################################

CFLAGS  = -g -Wall -Werror -pedantic
CC	= gcc

# Standard rule name for project. Builds production binaries
PROG	= Adamation

# Path to project within bin or src folder
PP			= com/ianmann/Adamation/

# Source files for adamation
CEESRCS = $(PP)adamation_main.c

# Output files for adamation
OBJS    = $(PP)adamation_main.o

# Builds entire project for production
$(PROG): $(OBJS)
	$(CC) $(OBJS) -o $(PROG)

%.o: %.c
	$(CC) $(CFLAGS) -c $<

clean:
	rm -f *.o *~ $(MRULE)