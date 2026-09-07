#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>
#include <ctype.h>

#include "libalchemy_set.h"

#define NUM_ALPHABET 26

static void libalchemy_set_react(struct libalchemy_set *set);

size_t libalchemy_get_units_remain_after_polymer_1(const char *input, const size_t input_length)
{
    struct libalchemy_set *set = libalchemy_set_create();

    if (set == NULL)
    {
        perror("Error initializing set");
        return 0;
    }

    for (size_t char_i = 0; char_i < input_length; char_i++)
    {
        if (libalchemy_set_push_back(set, input[char_i]) == NULL)
        {
            libalchemy_set_destroy(set);
            return 0;
        }
    }

    struct libalchemy_set_node *current = set->start;

    libalchemy_set_react(set);

    const size_t units_left = set->length;

    libalchemy_set_destroy(set);

    return units_left;
}

size_t libalchemy_get_units_remain_after_removing_a_polymer(const char *input, size_t input_length)
{
    struct libalchemy_set *set = libalchemy_set_create();
    if (set == NULL)
    {
        perror("Error initializing set");
        return 0;
    }

    for (size_t char_i = 0; char_i < input_length; char_i++)
    {
        if (libalchemy_set_push_back(set, input[char_i]) == NULL)
        {
            libalchemy_set_destroy(set);
            return 0;
        }
    }

    libalchemy_set_react(set);

    bool characters_seen[NUM_ALPHABET] = {false};
    struct libalchemy_set_node *current = set->start;
    while (current != NULL)
    {
        characters_seen[tolower(current->value) - 'a'] = true;
        current = current->next;
    }

    size_t min_units_left = SIZE_MAX;
    for (size_t char_i = 0; char_i < NUM_ALPHABET; char_i++)
    {
        if (characters_seen[char_i])
        {
            const char unit_to_remove = char_i + 'a';

            struct libalchemy_set *set_filtered = libalchemy_set_copy(set);
            if (set_filtered == NULL)
            {
                libalchemy_set_destroy(set);

                perror("Error copying set");
                return 0;
            }

            struct libalchemy_set_node *current = set_filtered->start;
            while (current != NULL)
            {
                if (tolower(current->value) == unit_to_remove)
                {
                    struct libalchemy_set_node *next = current->next;

                    libalchemy_set_remove(set_filtered, current);

                    current = next;
                }
                else
                {
                    current = current->next;
                }
            }

            // Filtered
            libalchemy_set_react(set_filtered);

            if (set_filtered->length < min_units_left)
            {
                min_units_left = set_filtered->length;
            }

            libalchemy_set_destroy(set_filtered);
        }
    }

    libalchemy_set_destroy(set);

    return min_units_left;
}

static void libalchemy_set_react(struct libalchemy_set *set)
{
    struct libalchemy_set_node *current = set->start;
    while (current != NULL && current->next != NULL)
    {
        if ((tolower(current->value) == tolower(current->next->value)) && (current->value != current->next->value))
        {
            struct libalchemy_set_node *prev = current->prev;
            struct libalchemy_set_node *next_next = current->next->next;

            libalchemy_set_remove(set, current->next);
            libalchemy_set_remove(set, current);

            if (prev != NULL)
            {
                current = prev;
            }
            else
            {
                current = next_next;
            }
        }
        else
        {
            current = current->next;
        }
    }
}