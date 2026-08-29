#pragma once

#include <time.h>

static const char *LibGuardEventBeginShift = "Begin Shift";
static const char *LibGuardEventFallAsleep = "Falls Asleep";
static const char *LibGuardEventWakesUp = "Wakes Up";

struct LibGuardEvent
{
    const char *event_type;
    struct tm date_time;
    int guard_id;
};

void libguard_initialize_event(struct LibGuardEvent *event, const char *event_type, struct tm date_time, int guard_id);