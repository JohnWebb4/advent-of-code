#include <time.h>

#include "libguard_event.h"

void libguard_initialize_event(struct libguard_event *event, const char *event_type, struct tm date_time, int guard_id)
{
    event->event_type = event_type;
    event->date_time = date_time;
    event->guard_id = guard_id;
}