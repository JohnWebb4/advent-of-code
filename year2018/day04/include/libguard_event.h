#pragma once

#include <time.h>

enum libguard_event_type
{
    BEGIN_SHIFT,
    FALLS_ASLEEP,
    WAKES_UP,
};

struct libguard_event
{
    enum libguard_event_type event_type;
    struct tm date_time;
    int guard_id;
};