#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#include "imsset.h"

// Private Declarations

static size_t hash_function(const char *str);

// Definitions

static size_t hash_function(const char *str)
{
	size_t hash = 5381;
	int c;

	while ((c = *str++))
	{
		hash = ((hash << 5) + hash) + c;
	}

	return hash % IMS_SET_NUM_BUCKETS;
}

struct IMSSet *ims_set_create(void)
{
	struct IMSSet *set = (struct IMSSet *)calloc(1, sizeof(*set));
	if (!set)
	{
		return NULL;
	}

	for (size_t bucket_i = 0; bucket_i < IMS_SET_NUM_BUCKETS; bucket_i++)
	{
		set->buckets[bucket_i] = (struct IMSSetNode *)calloc(1, sizeof(*set));
	}

	return set;
}

bool ims_set_contains(const struct IMSSet *set, const char *value)
{
	const int index = hash_function(value);
	struct IMSSetNode *current = set->buckets[index];

	while (current != NULL && current->value != NULL)
	{
		if (strcmp(current->value, value) == 0)
		{
			return true;
		}

		current = current->next;
	}

	return false;
}

bool ims_set_add(struct IMSSet *set, const char *value)
{
	struct IMSSetNode *new_node = malloc(sizeof(*new_node));
	if (!new_node)
	{
		return false;
	}
	char *new_value = malloc(strlen(value) + 1);
	strcpy(new_value, value);
	new_node->value = new_value;

	const size_t index = hash_function(new_value);
	struct IMSSetNode *bucket = set->buckets[index];

	new_node->next = bucket;

	set->buckets[index] = new_node;

	return true;
}

void ims_set_destroy(struct IMSSet *set)
{
	for (int bucket_i = 0; bucket_i < IMS_SET_NUM_BUCKETS; bucket_i++)
	{
		struct IMSSetNode *current = set->buckets[bucket_i];

		while (current != NULL)
		{
			struct IMSSetNode *tmp = current;
			current = current->next;

			tmp->next = NULL;

			free(tmp);
		}

		set->buckets[bucket_i] = NULL;
	}

	free(set);
}