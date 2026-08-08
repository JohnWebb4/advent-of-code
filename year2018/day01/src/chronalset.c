#include <stdbool.h>
#include <stdlib.h>

#include "chronalset.h"

static unsigned int hash_function(int x)
{
    x ^= x >> 16;
    x = (x * 0x85ebca6b) & 0xffffffff;
    x ^= x >> 13;
    x = (x * 0xc2b2ae35) & 0xffffffff;
    x ^= x >> 16;

    return x % SET_NUM_BUCKETS;
}

struct ChronalSet *chronal_set_create()
{
    struct ChronalSet *set = malloc(sizeof(struct ChronalSet));
    if (!set)
    {
        return NULL;
    }

    return set;
}

bool chronal_set_contains(struct ChronalSet *set, int value)
{
    unsigned int index = hash_function(value);
    struct ChronalSetNode *current = set->buckets[index];

    while (current != NULL)
    {
        if (current->value == value)
        {
            return true;
        }

        current = current->next;
    }

    return false;
}

bool chronal_set_add(struct ChronalSet *set, int value)
{
    struct ChronalSetNode *new_node = malloc(sizeof(struct ChronalSetNode));
    if (!new_node)
    {
        return false;
    }
    new_node->value = value;

    unsigned int index = hash_function(value);
    struct ChronalSetNode *bucket = set->buckets[index];

    new_node->next = bucket;

    set->buckets[index] = new_node;

    return true;
}

void chronal_set_destroy(struct ChronalSet *set)
{
    for (int i = 0; i < SET_NUM_BUCKETS; i++)
    {
        struct ChronalSetNode *current = set->buckets[i];

        while (current != NULL)
        {
            struct ChronalSetNode *tmp = current;
            current = current->next;

            free(tmp);
        }
    }


    free(set);
}