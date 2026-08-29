#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>

#include <libguard.h>
#include <libguardevent.h>

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
constexpr size_t TEST_INPUT_1_LENGTH = 17;

constexpr size_t INPUT_LENGTH = 1186;

int test_get_strategy_1_best_guard(const char *name, struct LibGuardEvent *events, size_t events_length, int expected_id);
int parse_libguard_event(struct LibGuardEvent *event, char *event_string);

int main(void)
{
    bool is_success = true;

    struct LibGuardEvent events_test_1[TEST_INPUT_1_LENGTH];
    for (size_t event_i = 0; event_i < TEST_INPUT_1_LENGTH; event_i++)
    {
        if (parse_libguard_event(&events_test_1[event_i], (char *)TEST_INPUT_1[event_i]) != EXIT_SUCCESS)
        {
            perror("Error parsing event test input");
            return EXIT_FAILURE;
        }
    }

    is_success &= (test_get_strategy_1_best_guard("Part 1 Test 1", events_test_1, TEST_INPUT_1_LENGTH, 240) == EXIT_SUCCESS);

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

int test_get_strategy_1_best_guard(const char *name, struct LibGuardEvent *events, size_t events_length, int expected_id)
{
    int result_id = libguard_get_strategy_1_best_guard(events, events_length);

    if (result_id == expected_id)
    {
        return EXIT_SUCCESS;
    }

    fprintf(stderr, "2018 Day 04 %s: %d != %d\n", name, result_id, expected_id);
    return EXIT_FAILURE;
}

int parse_libguard_event(struct LibGuardEvent *event, char *event_string)
{
    return EXIT_SUCCESS;
}