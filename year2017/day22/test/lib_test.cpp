#include "lib.h"

#include <gtest/gtest.h>

TEST(Year2017, Day22)
{
    const std::string test_1 = R"(..#
#..
...
)";

    EXPECT_EQ(year2017::day22::count_bursts_causing_infection(test_1), 5587);
}