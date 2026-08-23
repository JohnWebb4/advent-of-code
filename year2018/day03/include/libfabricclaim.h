#pragma once

struct LibFabricClaim
{
    int id;

    int x_min;
    int x_max;

    int y_min;
    int y_max;
};

// Public Function Declarations

struct LibFabricClaim *libfabric_create_claim(int id, int x_min, int x_max, int y_min, int y_max);
void libfabric_destroy_claim(struct LibFabricClaim *claim);