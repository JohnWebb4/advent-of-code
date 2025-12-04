#include "lib.h"

#include <gtest/gtest.h>

#include <fstream>
#include <sstream>
#include <string>

TEST(Year2025, Day04)
{
    const std::string test_1{R"(..@@.@@@@.
@@@.@.@.@@
@@@@@.@.@@
@.@@@@..@.
@@.@@@@.@@
.@@@@@@@.@
.@.@.@.@@@
@.@@@.@@@@
.@@@@@@@@.
@.@.@@@.@.)"};

    std::ifstream input_file("../input.txt");
    std::stringstream input_stream{};
    input_stream << input_file.rdbuf();

    EXPECT_EQ(year2025::day04::count_rolls_accessed(test_1), 13);
    EXPECT_EQ(year2025::day04::count_rolls_accessed(input_stream.view()), 1578);

    EXPECT_EQ(year2025::day04::count_rolls_removed(test_1), 43);
    EXPECT_EQ(year2025::day04::count_rolls_removed(input_stream.view()), 10132);
}