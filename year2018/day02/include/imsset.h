#pragma once

#define IMS_SET_NUM_BUCKETS 5

struct IMSSetNode
{
	const char *value;
	struct IMSSetNode *next;
};

struct IMSSet
{
	struct IMSSetNode *buckets[IMS_SET_NUM_BUCKETS];
};

// Public Declarations

struct IMSSet *ims_set_create(void);
bool ims_set_contains(const struct IMSSet *set, const char *value);
bool ims_set_add(struct IMSSet *set, const char *value);
void ims_set_destroy(struct IMSSet *set);