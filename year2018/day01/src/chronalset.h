#include <stdbool.h>

#ifndef LIB_CHRONAL_SET
#define LIB_CHRONAL_SET

#define SET_NUM_BUCKETS 101

struct ChronalSetNode
{
    int value;
    struct ChronalSetNode *next;
};

struct ChronalSet
{
    struct ChronalSetNode *buckets[SET_NUM_BUCKETS]; 
};

struct ChronalSet *chronal_set_create(void);
bool chronal_set_contains(const struct ChronalSet *set, int value);
bool chronal_set_add(struct ChronalSet *set, int value);
void chronal_set_destroy(struct ChronalSet *set);

#endif // LIB_CHRONAL_SET