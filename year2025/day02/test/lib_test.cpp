#include "lib.h"

#include <gtest/gtest.h>

#include <string>
#include <sstream>
#include <fstream>

TEST(Year2025, Day02)
{
    std::ifstream input_file("../input.txt");
    std::stringstream input_stream{};
    input_stream << input_file.rdbuf();
    std::string input_string = input_stream.str();

    std::string test_case_1 = R"(11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124)";

    EXPECT_EQ(year2025::day02::sum_all_invalid_ids(test_case_1), 1227775554);
    EXPECT_EQ(year2025::day02::sum_all_invalid_ids(input_string), 24157613387);

    EXPECT_EQ(year2025::day02::sum_all_invalid_ids_2(test_case_1), 4174379265);
    EXPECT_EQ(year2025::day02::sum_all_invalid_ids_2(input_string), 33832678380);
}