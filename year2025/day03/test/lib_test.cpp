#include "lib.h"

#include <gtest/gtest.h>

#include <fstream>
#include <string>
#include <sstream>

TEST(Year2025, Day03)
{
    const std::string test_1 = R"(987654321111111
811111111111119
234234234234278
818181911112111)";

    const std::ifstream input_file("../input.txt");
    std::stringstream input_stream{};
    input_stream << input_file.rdbuf();

    EXPECT_EQ(year2025::day03::get_total_voltage(test_1), 357);
    EXPECT_EQ(year2025::day03::get_total_voltage(input_stream.str()), 17031);

    EXPECT_EQ(year2025::day03::get_total_voltage_override(test_1), 3121910778619);
    EXPECT_EQ(year2025::day03::get_total_voltage_override(input_stream.str()), 168575096286051);
}