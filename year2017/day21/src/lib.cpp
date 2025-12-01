#include "lib.h"

#include <boost/algorithm/string/classification.hpp>
#include <boost/algorithm/string/split.hpp>

#include <algorithm>
#include <vector>
#include <string>
#include <sstream>
#include <iostream>

year2017::day21::Rule::Rule(std::string input, std::string output) : input(input), output(output) {}

int year2017::day21::count_pixels_after_x(std::string &input_string)
{
    std::vector<std::string> lines{};
    boost::split(lines, input_string, boost::is_any_of("\n"), boost::token_compress_on);

    std::vector<day21::Rule> rules{};
    for (std::string &line : lines)
    {
        day21::Rule::add_rule(rules, line);
    }

    std::string image{day21::input_program};

    for (int i = 0; i < 5; i++)
    {
        image = day21::enhance(rules, image);
    }

    return day21::count_pixels(image);
}

void year2017::day21::Rule::add_rule(std::vector<day21::Rule> &rules, std::string &rule_string)
{
    std::vector<std::string> rule_parts{};
    boost::split(rule_parts, rule_string, boost::is_any_of("=>"), boost::token_compress_on);

    for (std::string rule_part : rule_parts)
    {
        for (int i = 0; i < rule_part.size(); i++)
        {
            if (rule_part[i] == '/')
            {
                rule_part[i] = '\n';
            }
        }
    }

    rules.emplace_back(Rule(rule_parts[0], rule_parts[1]));
}

std::string year2017::day21::enhance(std::vector<day21::Rule> &rules, std::string &image)
{
    std::vector<std::string> fractals{};

    if (true)
    {
        split_2_by_2(fractals, image);
    }
    else if (true)
    {
        split_3_by_3(fractals, image);
    }

    std::stringstream result_stream{};
    for (std::string fractal : fractals)
    {
        for (day21::Rule rule : rules)
        {
            if (rule.input.size() == fractal.size())
            {
                if (rule.input == fractal)
                {
                    result_stream << rule.output;
                }
                else if (day21::flip(fractal) == image)
                {
                    result_stream << day21::flip(rule.output);
                }
                else if (day21::rotate(fractal) == image)
                {
                    result_stream << day21::rotate(rule.output);
                }
                else
                {
                    result_stream << fractal;
                }
            }
        }
    }

    return result_stream.str();
}

void year2017::day21::split_3_by_3(std::vector<std::string> &results, std::string &image)
{
    return;
}

void year2017::day21::split_2_by_2(std::vector<std::string> &results, std::string &image)
{
    return;
}

std::string year2017::day21::rotate(std::string &fractal)
{
    return "";
}

std::string year2017::day21::flip(std::string &fractal)
{
    return "";
}

int year2017::day21::count_pixels(std::string &image)
{
    return 0;
}