#pragma once

struct libcoord_point
{
    int x;
    int y;
};

struct libcoord_point *libcoord_point_create(int x, int y);