#include <stdlib.h>

#include "chronalset.h"

#include "libchronal.h"

static const size_t MAX_FREQ_TWICE_ITER = 1000000;

int chronal_get_result_frequency(int *freq_changes, size_t size)
{
    int pos = 0;

    for (size_t i = 0; i < size; i++)
    {
        pos += freq_changes[i];
    }

    return pos;
}

int chronal_first_frequency_twice(int *freq_changes, size_t size, int *result)
{
    struct ChronalSet *prev_pos = chronal_set_create();

    long long pos = 0;
    chronal_set_add(prev_pos, 0);

    for (size_t i = 0; i < MAX_FREQ_TWICE_ITER; i++)
    {
        int freq = freq_changes[i % size];
        pos += freq;

        if (chronal_set_contains(prev_pos, pos))
        {
            chronal_set_destroy(prev_pos);

            *result = pos;
            return EXIT_SUCCESS;
        }
        else
        {
            chronal_set_add(prev_pos, pos);
        }
    }

    printf("Failed to find solution in %zu iterations\n", MAX_FREQ_TWICE_ITER);

    result = NULL;
    return EXIT_FAILURE;
}