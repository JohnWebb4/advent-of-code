#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include "libcoord.h"
#include "libcoord_point.h"

const static char NEWLINE = '\n';
const static char NEWLINE_DELIM[1] = {'\n'};

static struct libcoord_point *libcoord_parse_point(struct libcoord_point *point, const char *input);

long libcoord_get_size_largest_area(const char *input_string)
{
    char *input_copy = strdup(input_string);
    if (input_copy == NULL)
    {
        return 0;
    }

    size_t num_lines = 0;
    const char *new_line_ptr = input_copy;
    while (*new_line_ptr)
    {
        if (*new_line_ptr == NEWLINE)
        {
            num_lines++;
        }
        new_line_ptr++;
    }
    struct libcoord_point **points = calloc(num_lines, sizeof(*points));

    char *line = strtok(input_copy, NEWLINE_DELIM);
    size_t line_i = 0;
    while (line != NULL)
    {
        if (libcoord_parse_point(points[line_i], line) == NULL)
        {
            perror("Failed to parse point");

            free(input_copy);
            free(points);
            return 0;
        }

        line = strtok(NULL, NEWLINE_DELIM);
        line_i++;
    }

    free(input_copy);
    free(points);
    return 0;
}

static struct libcoord_point *libcoord_parse_point(struct libcoord_point *point, const char *input)
{
    return NULL;
}