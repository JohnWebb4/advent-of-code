CC := gcc

CFLAGS := -Wall -Wextra $(INCLUDES)

TARGET := day01
BIN_DIR := ./bin

SRC_DIR := src
SRCS := $(wildcard $(SRC_DIR)/*.c)

OBJ_DIR := obj
OBJS := $(SRCS:$(SRC_DIR)/%.c=$(OBJ_DIR)/%.o)

all: $(TARGET)

$(TARGET): $(OBJS) $(BIN_DIR) $(OBJ_DIR)
	$(CC) $(CFLAGS) -o $(BIN_DIR)/$(TARGET) $(OBJS)

$(OBJ_DIR)/%.o: $(SRC_DIR)/%.c | $(OBJ_DIR)
	$(CC) $(CFLAGS) -c $< -o $@

$(OBJ_DIR) $(BIN_DIR):
	mkdir -p $@

clean:
	rm $(OBJ_DIR)/*.o $(BIN_DIR)/*