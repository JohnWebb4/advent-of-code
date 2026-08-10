#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

#include "libims.h"

// Constants

#define TEST_INPUT_LENGTH 250
#define BUFFER_MAX_LIMIT 256

static const char INPUT_NAME[] = "./input.txt";
static const size_t BUFFER_MAX_SIZEOF = BUFFER_MAX_LIMIT * sizeof(char);

const char *const part_1_test_1[] = {
    "abcdef",
    "bababc",
    "abbcde",
    "abcccd",
    "aabcdd",
    "abcdee",
    "ababab",
};

const char *const part_2_test_1[] = {
    "abcde",
    "fghij",
    "klmno",
    "pqrst",
    "fguij",
    "axcye",
    "wvxyz",
};

const char *part_2_test_1_result = "fgij";
const char *part_2_test_input = "qysdtrkloagnfozuwujmhrbvx";

// Private Declarations

int main(void);

static int test_ims_get_list_checksum(const char *name, const char *const *box_ids, size_t boxc, int expected);
static int test_ims_get_common_letters_between_correct_boxes(const char *name, const char *const *box_ids, size_t box_c, const char *expected);
static char **test_input_create(const char *file_name);
static void test_input_destroy(char **box_ids, int boxc);

// Definitions

int main(void)
{
  bool is_success = true;

  char **box_ids = test_input_create(INPUT_NAME);

  if (box_ids == NULL)
  {
    perror("Failed to read input file");
    return EXIT_FAILURE;
  }

  // Part 1

  is_success &= (test_ims_get_list_checksum("Part 1 Test 1", part_1_test_1, 7, 12) == EXIT_SUCCESS);

  is_success &= (test_ims_get_list_checksum("Part 1 Input", (const char *const *)box_ids, TEST_INPUT_LENGTH, 4980) == EXIT_SUCCESS);

  // Part 2

  is_success &= (test_ims_get_common_letters_between_correct_boxes("Part 2 Test 1", part_2_test_1, 7, part_2_test_1_result) == EXIT_SUCCESS);

  is_success &= (test_ims_get_common_letters_between_correct_boxes("Part 2 Input", (const char *const *)box_ids, TEST_INPUT_LENGTH, part_2_test_input) == EXIT_SUCCESS);

  if (is_success)
  {
    printf("Passed\n");
    test_input_destroy(box_ids, TEST_INPUT_LENGTH);
    box_ids = NULL;
    return EXIT_SUCCESS;
  }
  else
  {
    fprintf(stderr, "Failed\n");
    test_input_destroy(box_ids, TEST_INPUT_LENGTH);
    box_ids = NULL;
    return EXIT_FAILURE;
  }
}

static int test_ims_get_list_checksum(const char *name, const char *const *box_ids, size_t boxc, int expected)
{
  int result = ims_get_list_checksum(box_ids, boxc);
  if (result != expected)
  {
    fprintf(stderr, "2018 Day 02 %s Failed: %d != %d\n", name, result, expected);
    return EXIT_FAILURE;
  }

  return EXIT_SUCCESS;
}

static int test_ims_get_common_letters_between_correct_boxes(const char *name, const char *const *box_ids, size_t box_c, const char *expected)
{
  const char *result = ims_get_common_letters_between_correct_boxes(box_ids, box_c);
  if (strcmp(result, expected) != 0)
  {
    fprintf(stderr, "2018 Day 02 %s Failed: %s != %s\n", name, result, expected);
    return EXIT_FAILURE;
  }

  return EXIT_SUCCESS;
}

static char **test_input_create(const char *file_name)
{
  FILE *fp = fopen(file_name, "r");

  if (fp == NULL)
  {
    return NULL;
  }

  char **box_ids = malloc(TEST_INPUT_LENGTH * sizeof(char *));
  if (box_ids == NULL)
  {
    return NULL;
  }

  for (int i = 0; i < TEST_INPUT_LENGTH; i++)
  {
    box_ids[i] = malloc(BUFFER_MAX_SIZEOF);

    if (box_ids[i] == NULL)
    {
      test_input_destroy(box_ids, i);
      box_ids = NULL;

      return NULL;
    }

    if (fgets(box_ids[i], BUFFER_MAX_SIZEOF, fp) == NULL)
    {
      if (ferror(fp))
      {
        // Error
        test_input_destroy(box_ids, i - 1);
        box_ids = NULL;

        return NULL;
      }
      else if (feof(fp))
      {
        // EOF
        break;
      }
    }
  }

  fclose(fp);
  fp = NULL;

  return box_ids;
}

static void test_input_destroy(char **box_ids, int boxc)
{
  for (int i = 0; i < boxc; i++)
  {
    free(box_ids[i]);
    box_ids[i] = NULL;
  }

  free(box_ids);
}