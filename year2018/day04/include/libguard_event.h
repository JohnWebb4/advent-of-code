#pragma once

#include <time.h>

static const char *libguard_event_type_begin_shift = "Begin Shift";
static const char *libguard_event_type_falls_asleep = "Falls Asleep";
static const char *libguard_event_type_wakes_up = "Wakes Up";

struct libguard_event
{
    const char *event_type;
    struct tm date_time;
    int guard_id;
};

void libguard_initialize_event(struct libguard_event *event, const char *event_type, struct tm date_time, int guard_id);