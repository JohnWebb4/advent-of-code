#include "lib.h"

#include <gtest/gtest.h>

#include <fstream>
#include <sstream>
#include <string>

TEST(Year2025, Day05)
{
    const std::string test_1 = R"(3-5
10-14
16-20
12-18

1
5
8
11
17
32)";

    std::ifstream input_stream{"../input.txt"};
    std::stringstream input_str{};
    input_str << input_stream.rdbuf();

    EXPECT_EQ(year2025::day05::count_fresh_ingredients(test_1), 3);
    EXPECT_EQ(year2025::day05::count_fresh_ingredients(input_str.view()), 577);

    EXPECT_EQ(year2025::day05::count_all_fresh_ingredients(test_1), 14);

    // 31962209644 Low
    // 350513176552941 Low
    // 350513176552981 High
    EXPECT_EQ(year2025::day05::count_all_fresh_ingredients(input_str.view()), 350513176552950);

    input_stream.close();
}