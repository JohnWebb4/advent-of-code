#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "libalchemy.h"

static const char TEST_1_INPUT[] = "dabAcCaCBAcCcaDA";
#define TEST_1_INPUT_LENGTH (sizeof(TEST_1_INPUT) - 1)

#define INPUT_FILENAME "./input.txt"

static int test_libalchemy_get_units_remain_after_polymer_1(const char *name, const char *input, const size_t input_length, const size_t expected);
static int test_libalchemy_get_units_remain_after_removing_a_polymer(const char *name, const char *input, size_t input_length, size_t expected);

static const char *read_input(const char *filename);

int main(void)
{
    bool is_success = true;

    const char *input = read_input(INPUT_FILENAME);

    if (input == NULL)
    {
        perror("Failed to read input file");
        return EXIT_FAILURE;
    }

    const size_t input_length = strlen(input);

    is_success &= (test_libalchemy_get_units_remain_after_polymer_1("Test 1", TEST_1_INPUT, TEST_1_INPUT_LENGTH, 10) == EXIT_SUCCESS);
    is_success &= (test_libalchemy_get_units_remain_after_polymer_1("Input", input, input_length, 9390) == EXIT_SUCCESS);

    is_success &= (test_libalchemy_get_units_remain_after_removing_a_polymer("Test 1", TEST_1_INPUT, TEST_1_INPUT_LENGTH, 4) == EXIT_SUCCESS);
    is_success &= (test_libalchemy_get_units_remain_after_removing_a_polymer("Input", input, input_length, 5898) == EXIT_SUCCESS);

    free((void *)input);

    if (is_success)
    {
        printf("2018 Day 05 Passed\n");
        return EXIT_SUCCESS;
    }
    else
    {
        printf("2018 Day 05 Failed\n");
        return EXIT_FAILURE;
    }
}

static int test_libalchemy_get_units_remain_after_polymer_1(const char *name, const char *input, const size_t input_length, const size_t expected)
{
    size_t result = libalchemy_get_units_remain_after_polymer_1(input, input_length);

    if (result == expected)
    {
        return EXIT_SUCCESS;
    }

    fprintf(stderr, "Advent 2018 Day 5 Part 1 %s: %zu != %zu\n", name, result, expected);

    return EXIT_FAILURE;
}

static int test_libalchemy_get_units_remain_after_removing_a_polymer(const char *name, const char *input, size_t input_length, size_t expected)
{
    size_t result = libalchemy_get_units_remain_after_removing_a_polymer(input, input_length);

    if (result == expected)
    {
        return EXIT_SUCCESS;
    }

    fprintf(stderr, "Advent 2018 Day 5 Part 2 %s: %zu != %zu\n", name, result, expected);

    return EXIT_FAILURE;
}

static const char *read_input(const char *filename)
{
    FILE *file_ptr = fopen(filename, "r");

    if (file_ptr == NULL)
    {
        fprintf(stderr, "Failed to read filename %s", filename);
        return NULL;
    }

    fseek(file_ptr, 0L, SEEK_END);
    long file_size = ftell(file_ptr);
    if (file_size == -1)
    {
        perror("Error reading file size");
        fclose(file_ptr);
        return NULL;
    }

    rewind(file_ptr);

    char *input = calloc(1, file_size + 1);
    if (input == NULL)
    {
        perror("Failed to allocate file input");
        fclose(file_ptr);

        return NULL;
    }

    if (fread(input, file_size, 1, file_ptr) != 1)
    {
        perror("Reading file failed");
        fclose(file_ptr);
        free(input);

        return NULL;
    }

    fclose(file_ptr);

    return input;
}