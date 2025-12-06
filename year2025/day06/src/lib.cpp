#include "lib.h"

#include <algorithm>
#include <expected>
#include <iterator>
#include <string_view>
#include <sstream>
#include <unordered_map>
#include <vector>

namespace year2025::day06
{
    enum class operation
    {
        add,
        multiply
    };

    class problem
    {
    public:
        std::vector<long long> values;

        day06::operation operation;

        problem(std::vector<long long> v, day06::operation op) : values(v), operation(op) {}
    };

    void parse_problems(std::vector<day06::problem> &problems, std::string_view problems_view);
    void parse_problems_v2(std::vector<day06::problem> &problems, std::string_view problems_view);
    std::expected<day06::operation, std::string> parse_operation(char op_c);

    long long get_grand_total(std::string_view input_str)
    {
        std::vector<day06::problem> problems{};

        day06::parse_problems(problems, input_str);

        long long grand_total = 0;

        for (day06::problem &problem : problems)
        {
            long long problem_total = problem.values.at(0);

            for (std::size_t value_i = 1; value_i < problem.values.size(); value_i++)
            {
                if (problem.operation == day06::operation::add)
                {
                    problem_total += problem.values.at(value_i);
                }
                else if (problem.operation == day06::operation::multiply)
                {
                    problem_total *= problem.values.at(value_i);
                }
            }

            grand_total += problem_total;
        }

        return grand_total;
    }

    long long get_grand_total_v2(std::string_view input_str)
    {
        std::vector<day06::problem> problems{};

        day06::parse_problems_v2(problems, input_str);

        long long grand_total = 0;

        for (day06::problem &problem : problems)
        {
            long long problem_total = problem.values.at(0);

            for (std::size_t value_i = 1; value_i < problem.values.size(); value_i++)
            {
                if (problem.operation == day06::operation::add)
                {
                    problem_total += problem.values.at(value_i);
                }
                else if (problem.operation == day06::operation::multiply)
                {
                    problem_total *= problem.values.at(value_i);
                }
            }

            grand_total += problem_total;
        }

        return grand_total;
    }

    void parse_problems(std::vector<day06::problem> &problems, std::string_view problems_view)
    {
        std::vector<std::vector<long long>> values;

        std::size_t num_problems = 0;
        // Read each line
        std::size_t char_i = 0;
        while (problems_view.find('\n', char_i) < problems_view.size())
        {
            std::size_t end_of_line_i = problems_view.find('\n', char_i);
            std::string line = std::string(problems_view.substr(char_i, end_of_line_i - char_i));

            std::istringstream line_buf{line};
            std::vector<std::string> line_str_values{
                std::istream_iterator<std::string>(line_buf),
                {}};

            // Merging the vectors here is kinda gross
            std::vector<long long> line_values{};
            for (std::string &str_value : line_str_values)
            {
                line_values.emplace_back(std::stol(str_value));
            }
            values.emplace_back(line_values);

            if (num_problems < line_values.size())
            {
                num_problems = line_values.size();
            }

            char_i = end_of_line_i + 1;
        }

        std::string op_line = std::string(problems_view.substr(char_i));
        std::istringstream op_buf{op_line};
        std::vector<std::string> op_str_values{
            std::istream_iterator<std::string>(op_buf),
            {}};
        std::vector<day06::operation> operations{};
        for (std::string &op_str : op_str_values)
        {
            operations.emplace_back(day06::parse_operation(op_str.at(0)).value());
        }

        for (int problem_i = 0; problem_i < num_problems; problem_i++)
        {
            std::vector<long long> problem_values{};
            for (std::vector<long long> line : values)
            {
                problem_values.emplace_back(line.at(problem_i));
            }
            problems.emplace_back(day06::problem(problem_values, operations.at(problem_i)));
        }
    }

    void parse_problems_v2(std::vector<day06::problem> &problems, std::string_view problems_view)
    {
        std::vector<std::string> lines {};

        // Read each line
        std::size_t char_i = 0;
        while (problems_view.find('\n', char_i) < problems_view.size())
        {
            std::size_t end_of_line_i = problems_view.find('\n', char_i);

            lines.emplace_back(std::string(problems_view.substr(char_i, end_of_line_i - char_i)));

            char_i = end_of_line_i + 1;
        }
        lines.emplace_back(std::string(problems_view.substr(char_i)));

        std::size_t line_char_size = lines.at(0).size();
        std::vector<long long> current_problem_values{};
        for (std::size_t column_i = line_char_size; column_i --> 0;)
        {
            std::string value_str{};
            for (std::size_t line_i = 0; line_i < lines.size(); line_i++)
            {
                char c = lines.at(line_i).at(column_i);
                if (c == ' ')
                {
                    continue;
                } else if (std::isdigit(c))
                {
                    value_str += c;
                } else if (std::expected<day06::operation, std::string> op = day06::parse_operation(c); op)
                {
                    // Done. Save problem
                    long long current_value = std::stol(value_str);    
                    current_problem_values.emplace_back(current_value);
                    
                    problems.emplace_back(current_problem_values, *op);

                    current_problem_values.clear();
                    value_str.clear();
                }
            }

            value_str.erase(std::remove_if(value_str.begin(), value_str.end(), isspace));
            if (!value_str.empty())
            {
                current_problem_values.emplace_back(std::stol(value_str));
            }
        }
    }

    std::expected<day06::operation, std::string> parse_operation(char op_c)
    {
        if (op_c == '*')
        {
            return day06::operation::multiply;
        }
        else if (op_c == '+')
        {
            return day06::operation::add;
        }

        return std::unexpected("Failed to parse operation");
    }
} // year2025::day06