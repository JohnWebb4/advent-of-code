#include "lib.h"

#include <gtest/gtest.h>

#include <fstream>
#include <string>
#include <sstream>

TEST(Year2025, Day06) {
    const std::string test_1 = R"(123 328  51 64 
 45 64  387 23 
  6 98  215 314
*   +   *   +  )";

    std::ifstream input_stream{"../input.txt"};
    std::stringstream input_string_stream{};
    input_string_stream << input_stream.rdbuf();

    EXPECT_EQ(year2025::day06::get_grand_total(test_1), 4277556);
    EXPECT_EQ(year2025::day06::get_grand_total(input_string_stream.view()), 6725216329103);

    EXPECT_EQ(year2025::day06::get_grand_total_v2(test_1), 3263827);
    EXPECT_EQ(year2025::day06::get_grand_total_v2(input_string_stream.view()), 10600728112865);
}