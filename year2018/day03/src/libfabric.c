#include <limits.h>
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <regex.h>

#include <libfabricclaim.h>

#include "libfabric.h"

static const char *LIBFABRIC_CLAIM_REGEX = "#([0-9]+) @ ([0-9]+),([0-9]+): ([0-9]+)x([0-9]+)";

// Private Function Declarations

struct LibFabricClaim **libfabric_parse_claims(char **input, size_t input_length);
void libfabric_destroy_claims(struct LibFabricClaim **claims, size_t claims_length);
struct LibFabricClaim *libfabric_parse_claim(const char *input, regex_t *regex);

int libfabric_extract_regex_int(regmatch_t regex_group, const char *input, int *result);
int libfabric_extract_regex_string(regmatch_t regex_group, const char *input, char **result);

bool libfabric_are_claims_overlapping(struct LibFabricClaim *claim_1, struct LibFabricClaim *claim_2);

// Function Definitions

int libfabric_find_nonoverlapping_id(char **input, size_t input_length)
{
    struct LibFabricClaim **claims = libfabric_parse_claims(input, input_length);

    if (claims == NULL)
    {
        perror("Error parsing claims");

        return -1;
    }

    for (size_t claim_i = 0; claim_i < input_length; claim_i++)
    {
        bool is_nonoverlapping = true;

        for (size_t claim_j = 0; claim_j < input_length; claim_j++)
        {
            if (claim_i == claim_j)
            {
                continue;
            }

            if (libfabric_are_claims_overlapping(claims[claim_i], claims[claim_j]))
            {
                is_nonoverlapping = false;
                break;
            }
        }

        if (is_nonoverlapping)
        {
            int nonoverlapping_id = claims[claim_i]->id;

            libfabric_destroy_claims(claims, input_length);

            return nonoverlapping_id;
        }
    }

    libfabric_destroy_claims(claims, input_length);

    return -1;
}

int libfabric_count_overlapping_fabric(char **input, size_t input_length)
{
    struct LibFabricClaim **claims = libfabric_parse_claims(input, input_length);

    if (claims == NULL)
    {
        perror("Error parsing claims");

        return -1;
    }

    int x_min = INT_MAX;
    int x_max = INT_MIN;

    int y_min = INT_MAX;
    int y_max = INT_MIN;

    for (size_t claim_i = 0; claim_i < input_length; claim_i++)
    {
        if (claims[claim_i]->x_min < x_min)
        {
            x_min = claims[claim_i]->x_min;
        }

        if (claims[claim_i]->x_max > x_max)
        {
            x_max = claims[claim_i]->x_max;
        }

        if (claims[claim_i]->y_min < y_min)
        {
            y_min = claims[claim_i]->y_min;
        }

        if (claims[claim_i]->y_max > y_max)
        {
            y_max = claims[claim_i]->y_max;
        }
    }

    int global_height = y_max - y_min;
    int global_width = x_max - x_min;

    int *claims_per_spot = calloc(global_height * global_width, sizeof(int));
    if (claims_per_spot == NULL)
    {
        perror("Error allocating claims per spot");

        libfabric_destroy_claims(claims, input_length);

        return -1;
    }

    for (size_t claim_i = 0; claim_i < input_length; claim_i++)
    {
        struct LibFabricClaim *claim_1 = claims[claim_i];

        for (int x = claim_1->x_min; x < claim_1->x_max; x++)
        {
            for (int y = claim_1->y_min; y < claim_1->y_max; y++)
            {
                size_t index = (x - x_min) + ((y - y_min) * global_width);
                claims_per_spot[index]++;
            }
        }
    }

    int fabric_count = 0;

    for (int x = x_min; x < x_max; x++)
    {
        for (int y = y_min; y < y_max; y++)
        {
            size_t index = (x - x_min) + ((y - y_min) * global_width);

            if (claims_per_spot[index] >= 2)
            {
                fabric_count++;
            }
        }
    }

    libfabric_destroy_claims(claims, input_length);
    claims = NULL;

    free(claims_per_spot);
    claims_per_spot = NULL;

    return fabric_count;
}

struct LibFabricClaim **libfabric_parse_claims(char **input, size_t input_length)
{
    struct LibFabricClaim **claims = calloc(input_length, sizeof(struct LibFabricClaim *));

    if (claims == NULL)
    {
        perror("Error allocating claims");

        return NULL;
    }

    regex_t regex;
    if (regcomp(&regex, LIBFABRIC_CLAIM_REGEX, REG_EXTENDED) != 0)
    {
        perror("Error compiling regex");

        regfree(&regex);
        return NULL;
    }

    for (size_t input_i = 0; input_i < input_length; input_i++)
    {
        struct LibFabricClaim *claim = libfabric_parse_claim(input[input_i], &regex);

        if (claim == NULL)
        {
            perror("Error parsing claim");

            regfree(&regex);

            libfabric_destroy_claims(claims, input_i);
            claims = NULL;
            return NULL;
        }

        claims[input_i] = claim;
    }

    regfree(&regex);

    return claims;
}

void libfabric_destroy_claims(struct LibFabricClaim **claims, size_t claims_length)
{
    for (size_t claim_i = 0; claim_i < claims_length; claim_i++)
    {
        libfabric_destroy_claim(claims[claim_i]);
        claims[claim_i] = NULL;
    }

    free(claims);
}

struct LibFabricClaim *libfabric_parse_claim(const char *input, regex_t *regex)
{
    size_t max_groups = 6;
    regmatch_t regex_groups[max_groups];

    const int result = regexec(regex, input, max_groups, regex_groups, 0);
    if (result != 0)
    {
        size_t error_buffer_size = 1000;
        char error_buffer[1000];

        regerror(result, regex, error_buffer, 1000);
        printf("Regex Error: %s for %s\n", error_buffer, input);
        perror("Error parsing claim string");

        return NULL;
    }

    if (regex_groups[1].rm_so == (regoff_t)-1)
    {
        perror("Failed to find id");

        return NULL;
    }

    static const char *FIELD_ERRORS[5] = {
        "Failed to read id",
        "Failed to read x_left",
        "Failed to read y_top",
        "Failed to read width",
        "Failed to read height",
    };

    int fields[5] = {0};

    for (size_t field_i = 0; field_i < 5; field_i++)
    {
        if (libfabric_extract_regex_int(regex_groups[field_i + 1], input, &fields[field_i]) != 0)
        {
            perror(FIELD_ERRORS[field_i]);

            return NULL;
        }
    }

    int id = fields[0];
    int x_left = fields[1];
    int y_top = fields[2];
    int width = fields[3];
    int height = fields[4];

    int x_min = x_left;
    int x_max = x_min + width;

    int y_min = y_top;
    int y_max = y_min + height;

    struct LibFabricClaim *claim = libfabric_create_claim(id, x_min, x_max, y_min, y_max);

    if (claim == NULL)
    {
        return NULL;
    }

    return claim;
}

int libfabric_extract_regex_int(regmatch_t regex_group, const char *input, int *result)
{
    char *group_str = NULL;
    if (libfabric_extract_regex_string(regex_group, input, &group_str) != 0)
    {
        perror("Failed to parse regex group int");
        return EXIT_FAILURE;
    }

    char *group_end = NULL;
    int value = strtol(group_str, &group_end, 10);

    *result = value;

    free(group_str);
    group_str = NULL;

    return EXIT_SUCCESS;
}

int libfabric_extract_regex_string(regmatch_t regex_group, const char *input, char **result)
{
    size_t reg_length = regex_group.rm_eo - regex_group.rm_so;
    char *group_str = malloc(reg_length + 1);
    if (group_str == NULL)
    {
        perror("id str OOM");
        return EXIT_FAILURE;
    }
    memcpy(group_str, input + regex_group.rm_so, reg_length);
    group_str[reg_length] = '\0';

    *result = group_str;

    return EXIT_SUCCESS;
}

bool libfabric_are_claims_overlapping(struct LibFabricClaim *claim_1, struct LibFabricClaim *claim_2)
{
    return claim_1->x_min < claim_2->x_max && claim_1->x_max > claim_2->x_min &&
           claim_1->y_min < claim_2->y_max && claim_1->y_max > claim_2->y_min;
}