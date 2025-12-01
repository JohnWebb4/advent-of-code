#include "lib.h"

#include <gtest/gtest.h>

#include "fstream"

TEST(Year2025, Day01)
{
    const std::string test_1 = R"(L68
L30
R48
L5
R60
L55
L1
L99
R14
L82)";

    std::stringstream input_stream;
    std::ifstream input_in("../input.txt");

    input_stream << input_in.rdbuf();

    EXPECT_EQ(year2025::day01::get_password_for_door(test_1), 3);
    EXPECT_EQ(year2025::day01::get_password_for_door(input_stream.str()), 1040);

    EXPECT_EQ(year2025::day01::get_password_for_door_0x434C49434B(test_1), 6);
    EXPECT_EQ(year2025::day01::get_password_for_door_0x434C49434B(input_stream.str()), 6027);
}