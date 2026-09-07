#pragma once

struct libalchemy_set_node
{
    char value;
    struct libalchemy_set_node *next;
    struct libalchemy_set_node *prev;
};

struct libalchemy_set
{
    struct libalchemy_set_node *start;
    struct libalchemy_set_node *end;
    size_t length;
};

struct libalchemy_set_node *libalchemy_set_node_create(char value, struct libalchemy_set_node *next, struct libalchemy_set_node *prev);

struct libalchemy_set *libalchemy_set_create(void);
void libalchemy_set_destroy(struct libalchemy_set *set);
struct libalchemy_set_node *libalchemy_set_push_back(struct libalchemy_set *set, char value);
void libalchemy_set_remove(struct libalchemy_set *set, struct libalchemy_set_node *node);
struct libalchemy_set *libalchemy_set_copy(const struct libalchemy_set *set);