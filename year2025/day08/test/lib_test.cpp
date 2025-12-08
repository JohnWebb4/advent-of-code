#include "lib.h"

#include <gtest/gtest.h>

#include <fstream>
#include <string>
#include <sstream>

TEST(Year2025, Day08)
{
    const std::string test_1 = R"(162,817,812
57,618,57
906,360,560
592,479,940
352,342,300
466,668,158
542,29,236
431,825,988
739,650,466
52,470,668
216,146,977
819,987,18
117,168,530
805,96,715
346,949,466
970,615,88
941,993,340
862,61,35
984,92,344
425,690,689)";

    std::ifstream input_file{"../input.txt"};
    std::stringstream input_stream;
    input_stream << input_file.rdbuf();

    EXPECT_EQ(year2025::day08::product_of_three_largest_circuits(test_1, 10), 40);
    EXPECT_EQ(year2025::day08::product_of_three_largest_circuits(input_stream.view(), 1000), 123234);

    EXPECT_EQ(year2025::day08::get_distance_of_entire_circuit_from_wall(test_1), 25272);
    EXPECT_EQ(year2025::day08::get_distance_of_entire_circuit_from_wall(input_stream.view()), 9259958565);
}