#include "lib.h"

#include <gtest/gtest.h>

#include <fstream>
#include <string>
#include <sstream>

TEST(Year2025, Day09)
{
    const std::string test_1 = R"(7,1
11,1
11,7
9,7
9,5
2,5
2,3
7,3)";

    std::ifstream input_file{"../input.txt"};
    std::stringstream input_stream;
    input_stream << input_file.rdbuf();

    EXPECT_EQ(year2025::day09::get_area_of_largest_rectangle(test_1), 50);
    EXPECT_EQ(year2025::day09::get_area_of_largest_rectangle(input_stream.view()), 4749929916);

    EXPECT_EQ(year2025::day09::get_area_of_largest_rectangle_red_green(test_1), 24);
    //  159183472 low
    // 4631146730 high
    // 3071225340 high

    // 4487602019 NO
    // 4636004616 NO
    // 2943107510 NO
    // 2988431288 NO

    // 1572047142 YES
    EXPECT_EQ(year2025::day09::get_area_of_largest_rectangle_red_green(input_stream.view()), 1572047142);
}