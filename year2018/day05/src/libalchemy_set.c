#include <stdlib.h>
#include <stdio.h>

#include "libalchemy_set.h"

struct libalchemy_set_node *libalchemy_set_node_create(char value, struct libalchemy_set_node *next, struct libalchemy_set_node *prev)
{
    struct libalchemy_set_node *node = malloc(sizeof(*node));

    if (node == NULL)
    {
        perror("libalchemy set node error creating");
        return NULL;
    }

    node->value = value;
    node->next = next;
    node->prev = prev;

    return node;
}

struct libalchemy_set *libalchemy_set_create(void)
{
    struct libalchemy_set *set = malloc(sizeof(*set));

    if (set == NULL)
    {
        perror("libalchemy set error creating");
        return NULL;
    }

    set->start = NULL;
    set->end = NULL;

    return set;
}

void libalchemy_set_destroy(struct libalchemy_set *set)
{
    if (set == NULL)
    {
        perror("libalchemy set cannot destroy NULL");
        return;
    }

    struct libalchemy_set_node *current = set->start;

    while (current != NULL)
    {
        struct libalchemy_set_node *next = current->next;

        current->next = NULL;
        current->prev = NULL;
        current->value = '\0';

        free(current);

        current = next;
    }

    free(set);
}

struct libalchemy_set_node *libalchemy_set_push_back(struct libalchemy_set *set, char value)
{
    struct libalchemy_set_node *node = libalchemy_set_node_create(value, NULL, NULL);

    if (node == NULL)
    {
        return NULL;
    }

    if (set->start == NULL)
    {
        set->start = node;
        set->end = node;
    }
    else
    {
        node->prev = set->end;
        set->end->next = node;
        set->end = node;
    }

    set->length++;

    return node;
}

void libalchemy_set_remove(struct libalchemy_set *set, struct libalchemy_set_node *node)
{
    if (node == NULL)
    {
        perror("libalchemy set cannot remove NULL from set");
        return;
    }

    if (set == NULL)
    {
        perror("libalchmey set cannot remove value from NULL set");
        return;
    }

    struct libalchemy_set_node *prev = node->prev;
    struct libalchemy_set_node *next = node->next;

    if (node == set->start)
    {
        set->start = next;
    }

    if (node == set->end)
    {
        set->end = prev;
    }

    if (prev != NULL)
    {
        prev->next = next;
    }

    if (next != NULL)
    {
        next->prev = prev;
    }

    node->next = NULL;
    node->prev = NULL;
    node->value = '\0';

    set->length--;

    free(node);
}

struct libalchemy_set *libalchemy_set_copy(const struct libalchemy_set *set)
{
    if (set == NULL)
    {
        perror("libalchemy set cannot copy NULL set");
        return NULL;
    }

    struct libalchemy_set *new_set = libalchemy_set_create();

    struct libalchemy_set_node *current = set->start;
    while (current != NULL)
    {
        libalchemy_set_push_back(new_set, current->value);

        current = current->next;
    }

    return new_set;
}