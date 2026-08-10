#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "libims.h"
#include "imsset.h"

#define IMS_NUM_BOX_ID_CHARACTERS 26
#define IMS_MAX_BOX_ID_LENGTH 100

static const char IMS_FILTER_BOX_CHAR = '_';
static const char IMS_NEWLINE_BOX_CHAR = '\n';

// Private Declarations

// Definitions

long long ims_get_list_checksum(const char *const *box_ids, size_t boxc)
{
	int num_box_two_letters = 0;
	int num_box_three_letters = 0;

	for (size_t box_i = 0; box_i < boxc; box_i++)
	{
		const char *box = box_ids[box_i];
		int character_counts[IMS_NUM_BOX_ID_CHARACTERS] = {0};

		for (int char_i = 0; char_i < IMS_MAX_BOX_ID_LENGTH; char_i++)
		{
			if (box[char_i] == '\0')
			{
				break;
			}

			char c = box[char_i];
			int hash_index = (int)(c - 'a');

			if (hash_index >= 0 && hash_index < IMS_NUM_BOX_ID_CHARACTERS)
			{
				character_counts[hash_index]++;
			}
		}

		bool has_two = false;
		bool has_three = false;

		for (int char_i = 0; char_i < IMS_NUM_BOX_ID_CHARACTERS; char_i++)
		{
			if (has_two && has_three)
			{
				break;
			}

			if (!has_three && character_counts[char_i] == 3)
			{
				num_box_three_letters++;
				has_three = true;
			}
			else if (!has_two && character_counts[char_i] == 2)
			{
				num_box_two_letters++;
				has_two = true;
			}
		}
	}

	return num_box_two_letters * num_box_three_letters;
}

const char *ims_get_common_letters_between_correct_boxes(const char *const *box_ids, size_t boxc)
{
	struct IMSSet *set = ims_set_create();

	if (set == NULL)
	{
		return "";
	}

	for (size_t box_i = 0; box_i < boxc; box_i++)
	{
		const char *box = box_ids[box_i];
		const int box_length = strlen(box);

		for (int char_i = 0; char_i < box_length; char_i++)
		{
			char *filter_box = strdup(box);
			filter_box[char_i] = IMS_FILTER_BOX_CHAR;

			if (ims_set_contains(set, filter_box))
			{
				char *result = malloc(box_length);
				int result_char_i = 0;
				for (size_t char_i = 0; char_i < box_length; char_i++)
				{
					if (filter_box[char_i] != IMS_FILTER_BOX_CHAR && filter_box[char_i] != IMS_NEWLINE_BOX_CHAR)
					{
						result[result_char_i] = filter_box[char_i];
						result_char_i++;
					}
				}
				result[result_char_i] = '\0';

				ims_set_destroy(set);
				set = NULL;

				free(filter_box);
				filter_box = NULL;

				return result;
			}

			if (!ims_set_add(set, filter_box))
			{
				fprintf(stderr, "Failed to add %s to set\n", filter_box);

				ims_set_destroy(set);
				set = NULL;

				free(filter_box);
				filter_box = NULL;

				return NULL;
			}

			free(filter_box);
			filter_box = NULL;
		}
	}

	ims_set_destroy(set);
	set = NULL;

	return NULL;
}