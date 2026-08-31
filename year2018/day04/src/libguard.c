#include <stdio.h>
#include <stdlib.h>

#include "libguard_event.h"

#include "libguard.h"

static long get_epoch_1518(const struct tm *time);

#define MINUTES_IN_A_DAY 1440

struct libguard_sleep_times
{
    long minutes_asleep;
    long epoch_1518_asleep;
    long epoch_1518_awake;
    int guard_id;
};

int libguard_get_strategy_1_best_guard(struct libguard_event *const *events, size_t events_length)
{
    size_t num_sleep_times = 0;
    struct libguard_sleep_times sleep_times[10];

    int current_guard = -1;
    const struct tm *fell_asleep_time = NULL;
    for (size_t event_i = 0; event_i < events_length; event_i++)
    {
        const struct libguard_event *event = events[event_i];

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

            long epoch_1518_asleep = get_epoch_1518(fell_asleep_time);
            long epoch_1518_awake = get_epoch_1518(&event->date_time);

            long diff_minutes = epoch_1518_awake - epoch_1518_asleep;

            sleep_times[num_sleep_times].guard_id = current_guard;
            sleep_times[num_sleep_times].minutes_asleep = diff_minutes;
            sleep_times[num_sleep_times].epoch_1518_asleep = epoch_1518_asleep;
            sleep_times[num_sleep_times].epoch_1518_awake = epoch_1518_awake;
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

    int times_asleep_minute_in_a_day[MINUTES_IN_A_DAY];
    for (size_t minute_i = 0; minute_i < MINUTES_IN_A_DAY; minute_i++)
    {
        times_asleep_minute_in_a_day[minute_i] = 0;
    }

    for (size_t sleep_time_i = 0; sleep_time_i < num_sleep_times; sleep_time_i++)
    {
        if (sleep_times[sleep_time_i].guard_id == guard_id)
        {
            for (long minute_asleep = sleep_times[sleep_time_i].epoch_1518_asleep; minute_asleep < sleep_times[sleep_time_i].epoch_1518_awake; minute_asleep++)
            {
                int time_asleep_minute_in_a_day = minute_asleep % MINUTES_IN_A_DAY;

                times_asleep_minute_in_a_day[time_asleep_minute_in_a_day] += 1;
            }
        }
    }

    int max_times_asleep = 0;
    int minute_most_asleep_in_a_day = 0;

    for (size_t minute_i = 0; minute_i < MINUTES_IN_A_DAY; minute_i++)
    {
        if (times_asleep_minute_in_a_day[minute_i] > max_times_asleep)
        {
            max_times_asleep = times_asleep_minute_in_a_day[minute_i];
            minute_most_asleep_in_a_day = minute_i;
        }
    }

    return guard_id * minute_most_asleep_in_a_day;
}

static long get_epoch_1518(const struct tm *time)
{
    long epoch_1518 = time->tm_yday;
    epoch_1518 = (24 * epoch_1518) + time->tm_hour;
    epoch_1518 = (60 * epoch_1518) + time->tm_min;

    return epoch_1518;
}