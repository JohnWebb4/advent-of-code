#include <stdio.h>

#ifndef LIB_CHRONAL
#define LIB_CHRONAL

int chronal_get_result_frequency(int *freq_changes, size_t size);

int chronal_first_frequency_twice(int *freq_changes, size_t size, int *result);

#endif // LIB_CHRONAL