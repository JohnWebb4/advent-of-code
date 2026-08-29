#include <stdlib.h>
#include <stdbool.h>
#include <errno.h>
#include <limits.h>
#include <memory.h>
#include <regex.h>
#include <time.h>

#include <libguard.h>
#include <libguard_event.h>

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
#define TEST_INPUT_1_LENGTH sizeof(TEST_INPUT_1) / sizeof(TEST_INPUT_1[0])

#define INPUT_LENGTH 1186

const static char *LIBGUARD_EVENT_PATTERN = "\\[([a-zA-Z -:]+)\\] ([a-zA-Z #0-9]+)";
const static char *LIBGUARD_EVENT_GUARD_BEGINS_SHIFT_PATTERN = "Guard #([0-9]+) begins shift";
const static char *LIBGUARD_EVENT_FALLS_ASLEEP_PATTERN = "falls asleep";
const static char *LIBGUARD_EVENT_WAKES_UP_PATTERN = "wakes up";

struct libguard_event_parser
{
    const regex_t *regex_libguard_event;
    const regex_t *regex_libguard_event_guard_begins_shift;
    const regex_t *regex_libguard_event_falls_asleep;
    const regex_t *regex_libguard_event_wakes_up;
};

int test_get_strategy_1_best_guard(const char *name, struct libguard_event *events, size_t events_length, int expected_id);
int parse_libguard_event(struct libguard_event *event, struct libguard_event_parser *parser, char *event_string);

int extract_regex_int(regmatch_t regex_group, const char *input, int *result);
int extract_regex_string(regmatch_t regex_group, const char *input, char **result);

int main(void)
{
    bool is_success = true;

    regex_t regex_libguard_event;
    if (regcomp(&regex_libguard_event, LIBGUARD_EVENT_PATTERN, REG_EXTENDED) != 0)
    {
        perror("Could not compile event regex");
        return EXIT_FAILURE;
    }

    regex_t regex_libguard_event_guard_begins_shift;
    if (regcomp(&regex_libguard_event_guard_begins_shift, LIBGUARD_EVENT_GUARD_BEGINS_SHIFT_PATTERN, REG_EXTENDED) != 0)
    {
        perror("Could not compile guard begins shift regex");
        return EXIT_FAILURE;
    }

    regex_t regex_libguard_event_falls_asleep;
    if (regcomp(&regex_libguard_event_falls_asleep, LIBGUARD_EVENT_FALLS_ASLEEP_PATTERN, REG_EXTENDED) != 0)
    {
        perror("Could not compile falls asleep regex");
        return EXIT_FAILURE;
    }

    regex_t regex_libguard_event_wakes_up;
    if (regcomp(&regex_libguard_event_wakes_up, LIBGUARD_EVENT_WAKES_UP_PATTERN, REG_EXTENDED) != 0)
    {
        perror("Could not compile wakes up regex");
        return EXIT_FAILURE;
    }

    struct libguard_event_parser parser = {
        .regex_libguard_event = &regex_libguard_event,
        .regex_libguard_event_guard_begins_shift = &regex_libguard_event_guard_begins_shift,
        .regex_libguard_event_falls_asleep = &regex_libguard_event_falls_asleep,
        .regex_libguard_event_wakes_up = &regex_libguard_event_wakes_up,
    };

    struct libguard_event events_test_1[TEST_INPUT_1_LENGTH];
    for (size_t event_i = 0; event_i < TEST_INPUT_1_LENGTH; event_i++)
    {
        if (parse_libguard_event(&events_test_1[event_i], &parser, (char *)TEST_INPUT_1[event_i]) != EXIT_SUCCESS)
        {
            perror("Error parsing event test input");
            return EXIT_FAILURE;
        }
    }

    regfree(&regex_libguard_event);
    regfree(&regex_libguard_event_guard_begins_shift);
    regfree(&regex_libguard_event_falls_asleep);
    regfree(&regex_libguard_event_wakes_up);

    is_success &= (test_get_strategy_1_best_guard("Part 1 Test 1", events_test_1, TEST_INPUT_1_LENGTH, 240) == EXIT_SUCCESS);

    if (is_success)
    {
        printf("Year 2018 Day 04 Passed\n");
        return EXIT_SUCCESS;
    }
    else
    {
        printf("Year 2018 Day 04 Failed\n");
        return EXIT_FAILURE;
    }
}

int test_get_strategy_1_best_guard(const char *name, struct libguard_event *events, size_t events_length, int expected_id)
{
    int result_id = libguard_get_strategy_1_best_guard(events, events_length);

    if (result_id == expected_id)
    {
        return EXIT_SUCCESS;
    }

    fprintf(stderr, "2018 Day 04 %s Failed: %d != %d\n", name, result_id, expected_id);
    return EXIT_FAILURE;
}

int parse_libguard_event(struct libguard_event *event, struct libguard_event_parser *parser, char *event_string)
{
    regmatch_t event_matches[3];
    int event_result = regexec(parser->regex_libguard_event, event_string, 3, event_matches, 0);
    if (event_result != 0)
    {
        char msgbuf[100];
        regerror(event_result, parser->regex_libguard_event, msgbuf, sizeof(msgbuf));
        fprintf(stderr, "Regex match failed: %s\n", msgbuf);

        return EXIT_FAILURE;
    }

    for (size_t group_i = 0; group_i < 3; group_i++)
    {
        if (event_matches[group_i].rm_so == (regoff_t)-1)
        {
            perror("Failed to read regex group");
            return EXIT_FAILURE;
        }
    }

    char *date_string;
    if (extract_regex_string(event_matches[1], event_string, &date_string) != EXIT_SUCCESS)
    {
        perror("Failed to match date time group");
        return EXIT_FAILURE;
    }

    if (strptime(date_string, "%Y-%m-%d %H:%M", &event->date_time) == NULL)
    {
        perror("Failed to parse date time string");
        return EXIT_FAILURE;
    }

    free(date_string);
    date_string = NULL;

    char *suffix;
    if (extract_regex_string(event_matches[2], event_string, &suffix) != EXIT_SUCCESS)
    {
        perror("Failed to match suffix group");
        return EXIT_FAILURE;
    }

    regmatch_t guard_match[2];
    if (regexec(parser->regex_libguard_event_guard_begins_shift, suffix, 2, guard_match, 0) == 0)
    {
        event->event_type = BEGIN_SHIFT;
        int guard_id;
        if (extract_regex_int(guard_match[1], suffix, &guard_id) == EXIT_FAILURE)
        {
            perror("Failed to extract group id");
            return EXIT_FAILURE;
        }

        event->guard_id = guard_id;
    }
    else if (regexec(parser->regex_libguard_event_falls_asleep, suffix, 0, NULL, 0) == 0)
    {
        event->event_type = FALLS_ASLEEP;
        event->guard_id = -1;
    }
    else if (regexec(parser->regex_libguard_event_wakes_up, suffix, 0, NULL, 0) == 0)
    {
        event->event_type = WAKES_UP;
        event->guard_id = -1;
    }
    else
    {
        free(suffix);
        suffix = NULL;

        perror("Unrecognized event type");
        return EXIT_FAILURE;
    }

    free(suffix);
    suffix = NULL;

    return EXIT_SUCCESS;
}

int extract_regex_int(regmatch_t regex_group, const char *input, int *result)
{
    char *group_str = NULL;
    if (extract_regex_string(regex_group, input, &group_str) != 0)
    {
        perror("Failed to parse regex group int");
        return EXIT_FAILURE;
    }

    char *group_end = NULL;
    long value = strtol(group_str, &group_end, 10);

    if (value < INT_MIN || value > INT_MAX)
    {
        perror("Value int out of bounds");

        free(group_str);
        group_str = NULL;

        return EXIT_FAILURE;
    }

    *result = value;

    free(group_str);
    group_str = NULL;

    return EXIT_SUCCESS;
}

int extract_regex_string(regmatch_t regex_group, const char *input, char **result)
{
    size_t reg_length = regex_group.rm_eo - regex_group.rm_so;
    char *group_str = malloc(reg_length + 1);
    if (group_str == NULL)
    {
        perror("id str OOM");
        return EXIT_FAILURE;
    }
    memcpy(group_str, input + regex_group.rm_so, reg_length);
    group_str[reg_length] = '\0';

    *result = group_str;

    return EXIT_SUCCESS;
}