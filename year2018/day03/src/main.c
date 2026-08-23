#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

#include "libfabric.h"

static const char TEST_INPUT_FILENAME[] = "./input.txt";
static const int TEST_INPUT_LENGTH = 1347;

static const char *TEST_1_INPUT[] = {"#1 @ 1,3: 4x4",
                                     "#2 @ 3,1: 4x4",
                                     "#3 @ 5,5: 2x2"};
static const size_t TEST_1_LENGTH = 3;

// Private Function Declarations

int test_libfabric_find_nonoverlapping_id(const char *name, char **input, size_t input_length, int expected);
int test_libfabric_count_overlapping_fabric(const char *name, char **input, size_t input_length, int expected);

char **load_test_input(const char *filename);
void destroy_test_input(char **test_input);

// Function Definitions

int main(void)
{
    char **test_input = load_test_input(TEST_INPUT_FILENAME);

    if (test_input == NULL)
    {
        return EXIT_FAILURE;
    }

    bool is_success = true;

    is_success &= (test_libfabric_count_overlapping_fabric("Part 1 Test 1", (char **)TEST_1_INPUT, TEST_1_LENGTH, 4) == EXIT_SUCCESS);
    is_success &= (test_libfabric_count_overlapping_fabric("Part 1 Input", test_input, TEST_INPUT_LENGTH, 113966) == EXIT_SUCCESS);

    is_success &= (test_libfabric_find_nonoverlapping_id("Part 2 Test 1", (char **)TEST_1_INPUT, TEST_1_LENGTH, 3) == EXIT_SUCCESS);
    is_success &= (test_libfabric_find_nonoverlapping_id("Part 2 Input", test_input, TEST_INPUT_LENGTH, 235) == EXIT_SUCCESS);

    destroy_test_input(test_input);

    if (is_success)
    {
        return EXIT_SUCCESS;
    }
    else
    {
        return EXIT_FAILURE;
    }
}

int test_libfabric_find_nonoverlapping_id(const char *name, char **input, size_t input_length, int expected)
{
    const int result = libfabric_find_nonoverlapping_id(input, input_length);

    if (result != expected)
    {
        fprintf(stderr, "2018 Day 03 %s Failed. %d != %d\n", name, result, expected);

        return EXIT_FAILURE;
    }

    return EXIT_SUCCESS;
}

int test_libfabric_count_overlapping_fabric(const char *name, char **input, size_t input_length, int expected)
{
    const int result = libfabric_count_overlapping_fabric(input, input_length);

    if (result != expected)
    {
        fprintf(stderr, "2018 Day 03 %s Failed. %d != %d\n", name, result, expected);

        return EXIT_FAILURE;
    }

    return EXIT_SUCCESS;
}

char **load_test_input(const char *filename)
{
    char **test_input = calloc(TEST_INPUT_LENGTH, sizeof(char *));

    if (test_input == NULL)
    {
        return NULL;
    }

    FILE *file = fopen(filename, "r");

    if (file == NULL)
    {
        perror("Error reading input file");
        return NULL;
    }

    char buffer[100];
    for (size_t input_i = 0; input_i < TEST_INPUT_LENGTH; input_i++)
    {
        if (fgets(buffer, sizeof(buffer), file) == NULL)
        {
            fclose(file);

            return NULL;
        }

        buffer[strcspn(buffer, "\r\n")] = '\0';

        char *buffer_copy = strdup(buffer);
        if (buffer_copy == NULL)
        {
            fclose(file);

            return NULL;
        }

        test_input[input_i] = buffer_copy;
    }

    fclose(file);

    return test_input;
}

void destroy_test_input(char **test_input)
{
    for (size_t input_i = 0; input_i < TEST_INPUT_LENGTH; input_i++)
    {
        free(test_input[input_i]);
    }

    free(test_input);
}