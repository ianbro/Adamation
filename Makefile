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

SRC 		= src/
BIN			= bin/

# Path to project within src folder
PP			= com/ianmann/Adamation/
TESTS		= $(PP)tests/

# Source files for adamation
CEESRCS = $(SRC)$(TESTS)adamation_main.c

# Output files for adamation
OBJS    = $(BIN)$(TESTS)/tests/adamation_main.o

# Builds entire project for production
$(PROG): $(OBJS)
	$(CC) $(OBJS) -o $(PROG)

$(BIN)$(PP)%.o: $(SRC)$(PP)%.c
	$(CC) $(CFLAGS) $< -o $@

clean:
	rm -f *.o *~ $(MRULE)