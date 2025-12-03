#include "lib.h"

#include <gtest/gtest.h>

#include <fstream>
#include <sstream>

TEST(Year2017, Day22)
{
    const std::string test_1 = R"(..#
#..
...
)";

    const std::ifstream input_file{"../input.txt"};
    std::stringstream input_stream{};

    input_stream << input_file.rdbuf();

    EXPECT_EQ(year2017::day22::count_bursts_causing_infection(test_1), 5587);
    EXPECT_EQ(year2017::day22::count_bursts_causing_infection(input_stream.str()), 5406);
}