#include <stdlib.h>

#include "libcoord_point.h"

struct libcoord_point *libcoord_point_create(int x, int y)
{
    struct libcoord_point *point = malloc(sizeof(*point));

    point->x = x;
    point->y = y;

    return point;
}