#include <stdio.h>
#include <stdlib.h>

#include "libguard_event.h"

#include "libguard.h"

struct libguard_sleep_times
{
    int minutes_asleep;
    int guard_id;
};

int libguard_get_strategy_1_best_guard(struct libguard_event *events, size_t events_length)
{
    size_t num_sleep_times = 0;
    struct libguard_sleep_times sleep_times[10];

    int current_guard = -1;
    struct tm *fell_asleep_time = NULL;
    for (size_t event_i = 0; event_i < events_length; event_i++)
    {
        struct libguard_event *event = &events[event_i];

        if (event->event_type == BEGIN_SHIFT)
        {
            current_guard = event->guard_id;
        }
        else if (event->event_type == FALLS_ASLEEP)
        {
            if (fell_asleep_time != NULL)
            {
                perror("Guard already asleep");
                return -1;
            }

            fell_asleep_time = &event->date_time;
        }
        else if (event->event_type == WAKES_UP)
        {
            if (fell_asleep_time == NULL)
            {
                perror("Guard not asleep");
                return -1;
            }

            int minute_asleep = fell_asleep_time->tm_min;
            int minute_awake = event->date_time.tm_min;

            int diff_minutes = minute_awake - minute_asleep;

            sleep_times[num_sleep_times].guard_id = current_guard;
            sleep_times[num_sleep_times].minutes_asleep = diff_minutes;
            num_sleep_times++;

            fell_asleep_time = NULL;
        }
    }

    int guard_ids[10];
    int guard_minutes_asleep[10];
    size_t num_guards = 0;

    for (size_t sleep_time_i = 0; sleep_time_i < num_sleep_times; sleep_time_i++)
    {
        const struct libguard_sleep_times *sleep_time = &sleep_times[sleep_time_i];

        bool alread_exists = false;
        for (size_t guard_i = 0; guard_i < num_guards; guard_i++)
        {
            if (guard_ids[guard_i] == sleep_time->guard_id)
            {
                guard_minutes_asleep[guard_i] += sleep_time->minutes_asleep;
                alread_exists = true;
                break;
            }
        }

        if (!alread_exists)
        {
            guard_ids[num_guards] = sleep_time->guard_id;
            guard_minutes_asleep[num_guards] = sleep_time->minutes_asleep;
            num_guards++;
        }
    }

    int max_minutes_asleep = 0;
    int guard_id = -1;
    for (size_t guard_i = 0; guard_i < num_guards; guard_i++)
    {
        if (max_minutes_asleep < guard_minutes_asleep[guard_i])
        {
            max_minutes_asleep = guard_minutes_asleep[guard_i];
            guard_id = guard_ids[guard_i];
        }
    }

    return -1;
}