#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>

static const char *TEST_INPUT_1[] = {
    "[1518-11-01 00:00] Guard #10 begins shift",
    "[1518-11-01 00:05] falls asleep",
    "[1518-11-01 00:25] wakes up",
    "[1518-11-01 00:30] falls asleep",
    "[1518-11-01 00:55] wakes up",
    "[1518-11-01 23:58] Guard #99 begins shift",
    "[1518-11-02 00:40] falls asleep",
    "[1518-11-02 00:50] wakes up",
    "[1518-11-03 00:05] Guard #10 begins shift",
    "[1518-11-03 00:24] falls asleep",
    "[1518-11-03 00:29] wakes up",
    "[1518-11-04 00:02] Guard #99 begins shift",
    "[1518-11-04 00:36] falls asleep",
    "[1518-11-04 00:46] wakes up",
    "[1518-11-05 00:03] Guard #99 begins shift",
    "[1518-11-05 00:45] falls asleep",
    "[1518-11-05 00:55] wakes up",
};
static const size_t TEST_INPUT_1_LENGTH = 17;

// Private Declarations

int test_strategy_1_best_guard(const char *name, char **input, int input_length, int expected);

// Definitions

int main(void)
{
    bool is_success = true;

    is_success &= (test_strategy_1_best_guard("Part 1 Test 1", (char **)TEST_INPUT_1, TEST_INPUT_1_LENGTH, 240) == EXIT_SUCCESS);

    if (is_success)
    {
        printf("Year 2018 Day 03 Passed\n");
        return EXIT_SUCCESS;
    }
    else
    {
        printf("Year 2018 Day 03 Failed\n");
        return EXIT_FAILURE;
    }
}

int test_strategy_1_best_guard(const char *name, char **input, int input_length, int expected)
{
    return EXIT_FAILURE;
}