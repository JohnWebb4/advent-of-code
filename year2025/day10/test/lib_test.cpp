#include "lib.h"

#include <gtest/gtest.h>

#include <string>
#include <fstream>
#include <sstream>

TEST(Year2025, Day10)
{
    const std::string test_1 = R"([.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5})";

    std::ifstream input_file{};
    input_file.open("../input.txt");
    std::stringstream input_stream{};
    input_stream << input_file.rdbuf();

    EXPECT_EQ(year2025::day10::count_fewest_presses_to_configure(test_1), 7);
    EXPECT_EQ(year2025::day10::count_fewest_presses_to_configure(input_stream.view()), 390);

    input_file.close();
}