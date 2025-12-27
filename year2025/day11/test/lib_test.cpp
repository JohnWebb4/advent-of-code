#include "lib.h"

#include <gtest/gtest.h>

#include <fstream>
#include <string>
#include <string_view>
#include <sstream>

TEST(Year2025, Day11)
{
    const std::string TEST_CASE_1 = R"(aaa: you hhh
you: bbb ccc
bbb: ddd eee
ccc: ddd eee fff
ddd: ggg
eee: out
fff: out
ggg: out
hhh: ccc fff iii
iii: out)";

    const std::string TEST_CASE_2 = R"(svr: aaa bbb
aaa: fft
fft: ccc
bbb: tty
tty: ccc
ccc: ddd eee
ddd: hub
hub: fff
eee: dac
dac: fff
fff: ggg hhh
ggg: out
hhh: out)";

    std::ifstream file_stream{"../input.txt"};
    std::stringstream input_stream{};
    input_stream << file_stream.rdbuf();

    EXPECT_EQ(year2025::day11::count_paths_out(std::string_view(TEST_CASE_1)), 5);
    EXPECT_EQ(year2025::day11::count_paths_out(input_stream.view()), 523);

    EXPECT_EQ(year2025::day11::count_problematic_paths_out(std::string_view(TEST_CASE_2)), 2);
    EXPECT_EQ(year2025::day11::count_problematic_paths_out(std::string_view(input_stream.view())), 517315308154944);
}