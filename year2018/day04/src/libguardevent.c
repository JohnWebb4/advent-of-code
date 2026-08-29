#include <time.h>

#include "libguardevent.h"

void libguard_initialize_event(struct LibGuardEvent *event, const char *event_type, struct tm date_time, int guard_id)
{
    event->event_type = event_type;
    event->date_time = date_time;
    event->guard_id = guard_id;
}