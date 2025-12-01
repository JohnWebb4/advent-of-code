#include "lib.h"

#include <fstream>
#include <sstream>
#include <string>
#include <gtest/gtest.h>

TEST(Year2017, Day21) {
    std::ifstream input_file("../input.txt");
    std::stringstream input_buffer;
    input_buffer << input_file.rdbuf();
    std::string input_text = input_buffer.str();

    EXPECT_EQ(year2017::day21::count_pixels_after_x(input_text), 1);
}