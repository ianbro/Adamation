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

# Output files for adamation
OBJS    = $(BIN)$(PP)memory/files.o

MAIN = $(BIN)$(PP)main/adamation_main.o

TEST_FSTORE = $(BIN)$(TESTS)test_file_storage.o

# Builds entire project for production
$(PROG): $(OBJS) $(MAIN)
	$(CC) $(CFLAGS) $(OBJS) $(MAIN) -o $(PROG).o
	
$(BIN)$(TESTS)%.o: $(OBJS) $(SRC)$(TESTS)%.c
	$(CC) $(CFLAGS) $^ -o $@

$(BIN)$(PP)%.o: $(SRC)$(PP)%.c
	$(CC) $(CFLAGS) -c $^ -o $@

clean:
	rm -f *.o *~
	cp -rf src/ bin/
	find bin -name "*.c" -type f -delete
	find bin -name "*.o" -type f -delete