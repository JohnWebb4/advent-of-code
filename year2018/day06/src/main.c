#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>

#include "libcoord.h"

const static char *PART_1_TEST_1_INPUT = "1, 1\n\
1, 6\n\
8, 3\n\
3, 4\n\
5, 5\n\
8, 9";

static int test_libcoord_get_size_largest_area(const char *name, const char *input, long expected);

int main(void)
{
    bool is_success = true;

    is_success = (test_libcoord_get_size_largest_area("Test 1", PART_1_TEST_1_INPUT, 17) == EXIT_SUCCESS);

    if (is_success)
    {
        fprintf(stdout, "2018 Day 06 Success\n");
        return EXIT_SUCCESS;
    }
    else
    {
        fprintf(stderr, "2018 Day 06 Failed\n");
        return EXIT_FAILURE;
    }
}

static int test_libcoord_get_size_largest_area(const char *name, const char *input, long expected)
{
    long result = libcoord_get_size_largest_area(input);

    if (result == expected)
    {
        fprintf(stdout, "2018 Day 06 Part 1 %s Passed\n", name);
        return EXIT_SUCCESS;
    }

    fprintf(stderr, "2018 Day 06 Part 1 %s Failed: %ld != %ld\n", name, result, expected);

    return EXIT_FAILURE;
}