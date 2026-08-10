#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include <errno.h>

#include "libchronal.h"

// Constants

#define MAX_LINE_LENGTH 256
#define TEST_INPUT_LENGTH 976

// Declarations

static int read_input_file(int *arr, size_t size);
static int test_lib_chronal_get_result_frequency(const int *arr, size_t size, const char *name, int expected);
static int test_lib_chronal_first_frequency_twice(const int *arr, size_t size, const char *name, int expected);

// Definitions

int main(void)
{
    bool is_success = true;
    int test_inputs[TEST_INPUT_LENGTH];

    if (read_input_file(test_inputs, TEST_INPUT_LENGTH) == EXIT_FAILURE)
    {
        perror("Error reading test input");
        return EXIT_FAILURE;
    }

    int part_1_test_1[] = {1, -2, 3, 1};

    // BEGIN TESTS
    is_success &= (test_lib_chronal_get_result_frequency(part_1_test_1, 4, "Part 1 Test 1", 3) == EXIT_SUCCESS);

    int part_1_test_2[] = {1, 1, 1};
    is_success &= (test_lib_chronal_get_result_frequency(part_1_test_2, 3, "Part 1 Test 2", 3) == EXIT_SUCCESS);

    is_success &= (test_lib_chronal_get_result_frequency(test_inputs, (int)TEST_INPUT_LENGTH, "Part 1 Input", 500) == EXIT_SUCCESS);

    int part_2_test_1[] = {1, -2, 3, 1};
    is_success &= (test_lib_chronal_first_frequency_twice(part_2_test_1, 4, "Part 2 Test 1", 2) == EXIT_SUCCESS);

    int part_2_test_2[] = {1, -1};
    is_success &= (test_lib_chronal_first_frequency_twice(part_2_test_2, 2, "Part 2 Test 2", 0) == EXIT_SUCCESS);

    int part_2_test_3[] = {3, 3, 4, -2, -4};
    is_success &= (test_lib_chronal_first_frequency_twice(part_2_test_3, 5, "Part 2 Test 3", 10) == EXIT_SUCCESS);

    int part_2_test_4[] = {-6, +3, +8, +5, -6};
    is_success &= (test_lib_chronal_first_frequency_twice(part_2_test_4, 5, "Part 2 Test 4", 5) == EXIT_SUCCESS);

    int part_2_test_5[] = {+7, +7, -2, -7, -4};
    is_success &= (test_lib_chronal_first_frequency_twice(part_2_test_5, 5, "Part 2 Test 5", 14) == EXIT_SUCCESS);

    is_success &= (test_lib_chronal_first_frequency_twice(test_inputs, TEST_INPUT_LENGTH, "Part 2 Input", 709) == EXIT_SUCCESS);

    // END TESTS

    if (is_success)
    {
        printf("Passed\n");
        return EXIT_SUCCESS;
    }
    else
    {
        printf("Failed\n");
        return EXIT_FAILURE;
    }
}

static int test_lib_chronal_get_result_frequency(const int *arr, size_t size, const char *name, int expected)
{
    int result = chronal_get_result_frequency(arr, size);
    if (result != expected)
    {
        fprintf(stderr, "2018 Day 01 %s Failed: %d != %d\n", name, result, expected);
        return EXIT_FAILURE;
    }

    return EXIT_SUCCESS;
}

static int test_lib_chronal_first_frequency_twice(const int *arr, size_t size, const char *name, int expected)
{
    int result = 0;
    if (chronal_first_frequency_twice(arr, size, &result) != EXIT_SUCCESS)
    {
        fprintf(stderr, "2018 Day 01 %s Error\n", name);
        return EXIT_FAILURE;
    }

    if (result != expected)
    {
        fprintf(stderr, "2018 Day 01 %s Failed: %d != %d\n", name, result, expected);
        return EXIT_FAILURE;
    }

    return EXIT_SUCCESS;
}

static int read_input_file(int *arr, size_t size)
{
    FILE *file = fopen("input.txt", "r");

    if (file == NULL)
    {
        perror("Error opening file");
        return EXIT_FAILURE;
    }

    char line[MAX_LINE_LENGTH];

    for (size_t i = 0; i < size; i++)
    {
        char *res = fgets(line, sizeof(line), file);
        if (res == NULL)
        {
            perror("Error reading file");
            return EXIT_FAILURE;
        }

        errno = 0;
        char *startptr = &line[0];
        char *endptr;
        long value = strtol(line, &endptr, 10);

        if (endptr == startptr)
        {
            perror("Error: No digits found");
            return EXIT_FAILURE;
        }
        else if (errno == ERANGE)
        {
            perror("Error: Number out of range");
            return EXIT_FAILURE;
        }
        else
        {
            int result = (int)value;
            arr[i] = result;
        }
    }
    fclose(file);

    return 0;
}
