#include <stdlib.h>

#include "libfabricclaim.h"

// Private Function Declarations

// Function Definitions

struct LibFabricClaim *libfabric_create_claim(int id, int x_min, int x_max, int y_min, int y_max)
{
    struct LibFabricClaim *claim = malloc(sizeof(*claim));
    if (claim == NULL)
    {
        return NULL;
    }

    claim->id = id;

    claim->x_min = x_min;
    claim->x_max = x_max;

    claim->y_min = y_min;
    claim->y_max = y_max;

    return claim;
}

void libfabric_destroy_claim(struct LibFabricClaim *claim)
{
    free(claim);
}