#include "lib.h"

#include <gtest/gtest.h>

#include <fstream>
#include <string>
#include <string_view>
#include <sstream>

TEST(Year2025, Day12)
{
    const std::string TEST_CASE_1 = R"(0:
###
##.
##.

1:
###
##.
.##

2:
.##
###
##.

3:
##.
###
##.

4:
###
#..
###

5:
###
.#.
###

4x4: 0 0 0 0 2 0
12x5: 1 0 1 0 2 2
12x5: 1 0 1 0 3 2)";

    std::ifstream file_stream{"../input.txt"};
    std::stringstream input_stream{};
    input_stream << file_stream.rdbuf();

    EXPECT_EQ(year2025::day12::count_regions_can_fit_presents(static_cast<std::string_view>(TEST_CASE_1)), 2);
    EXPECT_EQ(year2025::day12::count_regions_can_fit_presents(static_cast<std::string_view>(input_stream.view())), 2);

    file_stream.close();
}