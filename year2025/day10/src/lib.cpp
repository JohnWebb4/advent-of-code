#include "lib.h"

#include <format>
#include <memory>
#include <numeric>
#include <optional>
#include <queue>
#include <stack>
#include <string_view>
#include <sstream>
#include <unordered_set>
#include <vector>

#include <iostream>

namespace year2025::day10
{
  class Machine
  {
  public:
    std::vector<bool> light_diaghram;
    std::vector<std::vector<std::size_t>> button_wiring;
    std::vector<long long> voltages;

    Machine(const std::vector<bool> &light_diaghram, const std::vector<std::vector<std::size_t>> &button_wiring, const std::vector<long long> voltages) : light_diaghram(light_diaghram), button_wiring(button_wiring), voltages(voltages) {}
  };

  std::vector<bool> parse_light_diaghram(const std::string_view &diaghram_view)
  {
    std::vector<bool> light_diaghram{};

    for (const char &c : diaghram_view)
    {
      if (c == '#')
      {
        light_diaghram.emplace_back(true);
      }
      else
      {
        light_diaghram.emplace_back(false);
      }
    }

    return light_diaghram;
  }

  const std::vector<std::size_t> parse_button(const std::string_view &button_view)
  {
    std::vector<std::size_t> button{};

    std::size_t prev_comma_index = 0;
    while (prev_comma_index != -1)
    {
      std::size_t comma_index = button_view.find(',', prev_comma_index);
      std::string_view counter_view = button_view.substr(prev_comma_index, comma_index - prev_comma_index);

      button.emplace_back(std::atoi(static_cast<std::string>(counter_view).c_str()));

      if (comma_index == -1)
      {
        break;
      }
      else
      {
        prev_comma_index = comma_index + 1;
      }
    }

    return button;
  }

  const std::vector<std::vector<std::size_t>> parse_button_wiring(const std::string_view &button_wiring_view)
  {
    std::vector<std::vector<std::size_t>> button_wiring{};

    std::size_t prev_space_index = 0;
    while (prev_space_index != -1)
    {
      std::size_t space_index = button_wiring_view.find(' ', prev_space_index);
      std::string_view button_view = button_wiring_view.substr(prev_space_index + 1, space_index - prev_space_index - 1 - 1);

      button_wiring.emplace_back(parse_button(button_view));

      if (space_index == -1)
      {
        break;
      }
      else
      {
        prev_space_index = space_index + 1;
      }
    }

    return button_wiring;
  }

  const std::vector<long long> parse_voltages(const std::string_view &voltages_view)
  {
    std::vector<long long> voltages;

    std::size_t prev_comma_index = 0;
    while (prev_comma_index != -1)
    {
      std::size_t comma_index = voltages_view.find(',', prev_comma_index);
      std::string_view voltage_view = voltages_view.substr(prev_comma_index, comma_index - prev_comma_index);

      voltages.emplace_back(std::atoi(static_cast<std::string>(voltage_view).c_str()));

      if (comma_index == -1)
      {
        break;
      }
      else
      {
        prev_comma_index = comma_index + 1;
      }
    }

    return voltages;
  }

  Machine parse_machine(const std::string_view &machine_view)
  {
    const std::size_t diaghram_index = machine_view.find(']');
    const std::string_view diaghram_view = machine_view.substr(1, diaghram_index - 1);
    const std::vector<bool> light_dighram = parse_light_diaghram(diaghram_view);

    const std::size_t voltage_index = machine_view.find('{');
    const std::string_view button_wiring_view = machine_view.substr(diaghram_index + 2, voltage_index - diaghram_index - 2 - 1);
    const std::vector<std::vector<std::size_t>> button_wiring = parse_button_wiring(button_wiring_view);

    const std::string_view voltages_view = machine_view.substr(voltage_index + 1, machine_view.size() - voltage_index - 1 - 1);
    const std::vector<long long> voltages = parse_voltages(voltages_view);

    return Machine(light_dighram, button_wiring, voltages);
  }

  const std::vector<Machine> parse_machines(const std::string_view &machines_view)
  {
    std::vector<Machine> machines{};

    std::size_t prev_line_index = 0;
    while (prev_line_index != -1)
    {
      std::size_t line_index = machines_view.find('\n', prev_line_index);
      std::string_view line = machines_view.substr(prev_line_index, line_index - prev_line_index);

      machines.emplace_back(parse_machine(line));

      if (line_index == -1)
      {
        break;
      }
      else
      {
        prev_line_index = line_index + 1;
      }
    }

    return machines;
  }

  class MachineSolution
  {
  public:
    std::vector<long long> presses;
    std::vector<bool> light_diaghram;

    MachineSolution(const std::vector<long long> &presses, const std::vector<bool> &light_diaghram) : presses(presses), light_diaghram(light_diaghram) {}
  };

  const std::string get_presses_key(const std::vector<long long> &presses)
  {
    std::stringstream ss;

    for (const long long &press : presses)
    {
      ss << press << ',';
    }

    return ss.str();
  }

  long long counter_fewest_presses_to_configure_machine(const Machine &machine)
  {
    std::queue<MachineSolution> solution_queue{};
    const std::vector<long long> initial_presses(machine.button_wiring.size());
    const std::vector<bool> initial_light_diaghram(machine.light_diaghram.size());
    solution_queue.emplace(MachineSolution(initial_presses, initial_light_diaghram));

    std::optional<long long> min_presses_to_configure{};
    std::unordered_set<std::string> has_seen_presses{};

    while (!solution_queue.empty())
    {
      std::unique_ptr<MachineSolution> solution = std::make_unique<MachineSolution>(solution_queue.front());
      solution_queue.pop();

      long long num_presses = std::reduce(solution->presses.begin(), solution->presses.end());

      if (!min_presses_to_configure || (num_presses < *min_presses_to_configure))
      {
        if (solution->light_diaghram == machine.light_diaghram)
        {
          min_presses_to_configure = num_presses;
        }
        else if (!min_presses_to_configure || (num_presses < *min_presses_to_configure - 1))
        {
          for (std::size_t button_i = 0; button_i < machine.button_wiring.size(); button_i++)
          {
            std::vector<long long> next_presses = solution->presses;
            next_presses.at(button_i)++;

            std::string next_presses_key = get_presses_key(next_presses);

            if (!has_seen_presses.contains(next_presses_key))
            {
              std::vector<bool> next_light_diaghram = solution->light_diaghram;
              for (const std::size_t &diaghram_index : machine.button_wiring.at(button_i))
              {
                next_light_diaghram.at(diaghram_index) = !next_light_diaghram.at(diaghram_index);
              }

              solution_queue.emplace(MachineSolution(next_presses, next_light_diaghram));
              has_seen_presses.emplace(next_presses_key);
            }
          }
        }
      }
    }

    if (min_presses_to_configure)
    {
      return *min_presses_to_configure;
    }
    else
    {
      throw std::invalid_argument{"Failed to find solution"};
    }
  }

  long long count_fewest_presses_to_configure(const std::string_view &manual_instructions)
  {
    const std::vector<Machine> machines = parse_machines(manual_instructions);

    long long num_presses_to_configure{0};

    for (const Machine &machine : machines)
    {
      num_presses_to_configure += counter_fewest_presses_to_configure_machine(machine);
    }

    return num_presses_to_configure;
  }

  class WeightedIndex
  {
  public:
    std::size_t index;
    float weight;

    WeightedIndex(const std::size_t index, const float weight) : index(index), weight(weight) {}
  };

  class Pivot
  {
  public:
    std::size_t column;
    std::size_t row;

    Pivot(const std::size_t column, const std::size_t row) : column(column), row(row) {}
  };

  class VoltageSolution
  {
  public:
    std::vector<std::vector<long long>> matrix;
    std::vector<Pivot> visited_pivots;

    VoltageSolution(const std::vector<std::vector<long long>> &matrix, const std::vector<Pivot> &visited_pivots) : matrix(matrix), visited_pivots(visited_pivots) {}
  };

  std::vector<std::vector<long long>> get_simplex_matrix_from_machine(const Machine &machine)
  {
    std::vector<std::vector<long long>> matrix = std::vector<std::vector<long long>>{};

    for (std::size_t button_i = 0; button_i < machine.button_wiring.size(); button_i++)
    {
      std::vector<long long>
          row(2 * machine.voltages.size() + machine.button_wiring.size() + 1);
      for (const long long &counter_i : machine.button_wiring.at(button_i))
      {
        row.at(2 * counter_i) = 1;
        row.at(2 * counter_i + 1) = -1;
      }
      row.at(2 * machine.voltages.size() + button_i) = 1;
      row.at(row.size() - 1) = 1;

      matrix.emplace_back(row);
    }

    std::vector<long long> cost_row(2 * machine.voltages.size() + machine.button_wiring.size() + 1);
    for (std::size_t voltage_i = 0; voltage_i < machine.voltages.size(); voltage_i++)
    {
      cost_row.at(2 * voltage_i) = -machine.voltages.at(voltage_i);
      cost_row.at(2 * voltage_i + 1) = machine.voltages.at(voltage_i);
    }
    matrix.emplace_back(cost_row);

    return matrix;
  }

  std::vector<std::vector<long long>> pivot(const std::vector<std::vector<long long>> &matrix, const std::size_t pivot_column, const std::size_t pivot_row)
  {
    std::vector<std::vector<long long>> pivoted_matrix = matrix;
    for (std::size_t y = 0; y < matrix.size(); y++)
    {
      if ((y != pivot_row) && (pivoted_matrix.at(y).at(pivot_column) != 0))
      {
        long long row_scale = std::abs(pivoted_matrix.at(pivot_row).at(pivot_column));
        long long factor = row_scale * pivoted_matrix.at(y).at(pivot_column);

        long long pivot_scale = factor / pivoted_matrix.at(pivot_row).at(pivot_column);

        for (std::size_t x = 0; x < matrix.at(y).size(); x++)
        {
          pivoted_matrix.at(y).at(x) = (row_scale * pivoted_matrix.at(y).at(x)) - (pivot_scale * pivoted_matrix.at(pivot_row).at(x));
        }
      }
    }

    return pivoted_matrix;
  }

  std::string convert_matrix_to_str(const std::vector<std::vector<long long>> &matrix)
  {
    std::stringstream ss;

    for (const std::vector<long long> &row : matrix)
    {
      for (const long long &v : row)
      {
        ss << v << ',';
      }

      ss << std::endl;
    }

    return ss.str();
  }

  const std::size_t MAX_VOLTAGE_SOLUTION_ITERATIONS = 100000;

  long long counter_fewest_presses_to_configure_voltage(const Machine &machine)
  {
    // Bound
    std::vector<long long> max_button_presses{};
    for (std::size_t button_i = 0; button_i < machine.button_wiring.size(); button_i++)
    {
      long long max_presses = machine.voltages.at(machine.button_wiring.at(button_i).at(0));
      for (const std::size_t &counter_i : machine.button_wiring.at(button_i))
      {
        max_presses = std::min(machine.voltages.at(counter_i), max_presses);
      }

      max_button_presses.emplace_back(max_presses);
    }

    // Branch
    std::stack<VoltageSolution> solution_stack{};
    solution_stack.emplace(VoltageSolution(get_simplex_matrix_from_machine(machine), std::vector<Pivot>{}));
    std::optional<long long> min_presses_to_configure{};
    std::unordered_set<std::string> has_seen{};
    has_seen.emplace(convert_matrix_to_str(solution_stack.top().matrix));

    long long num_iterations = 0;
    while (!solution_stack.empty() && num_iterations < day10::MAX_VOLTAGE_SOLUTION_ITERATIONS)
    {
      const VoltageSolution solution = solution_stack.top();
      solution_stack.pop();
      const std::size_t width = solution.matrix.at(0).size();
      const std::size_t height = solution.matrix.size();

      if (num_iterations % (day10::MAX_VOLTAGE_SOLUTION_ITERATIONS / 10) == 0)
      {
        std::cout << "...thinking..." << solution_stack.size() << std::endl;
      }
      num_iterations++;

      if (!min_presses_to_configure || (solution.matrix.at(height - 1).at(width - 1) < *min_presses_to_configure))
      {
        if (std::all_of(solution.matrix.at(height - 1).begin(), solution.matrix.at(height - 1).end(), [](const long long &v)
                        { return v >= 0; }))
        {
          // Done solving
          std::cout << std::format("Found solution in {} presses. Iterations {}. Stack size {}", solution.matrix.at(height - 1).at(width - 1), num_iterations, solution_stack.size()) << std::endl;
          min_presses_to_configure = solution.matrix.at(height - 1).at(width - 1);
        }
        else if (!min_presses_to_configure || (solution.matrix.at(height - 1).at(width - 1) < (*min_presses_to_configure - 1)))
        {
          // Find pivot column
          const auto compare_columns = [](const WeightedIndex &column1, const WeightedIndex &column2)
          {
            return column1.weight < column2.weight;
          };
          std::priority_queue<WeightedIndex, std::vector<WeightedIndex>, decltype(compare_columns)> pivot_columns{compare_columns};

          for (std::size_t x = 0; x < solution.matrix.at(height - 1).size(); x++)
          {
            if (solution.matrix.at(height - 1).at(x) < 0)
            {
              pivot_columns.emplace(WeightedIndex(x, solution.matrix.at(height - 1).at(x)));
            }
          }

          while (!pivot_columns.empty())
          {
            const WeightedIndex &pivot_column = pivot_columns.top();
            const auto compare_rows = [](const WeightedIndex &row1, const WeightedIndex &row2)
            {
              return row1.weight < row2.weight;
            };
            std::priority_queue<WeightedIndex, std::vector<WeightedIndex>, decltype(compare_rows)> pivot_rows{compare_rows};
            for (std::size_t y = 0; y < height - 1; y++)
            {
              float quotient = static_cast<float>(solution.matrix.at(y).at(width - 1)) / static_cast<float>(solution.matrix.at(y).at(pivot_column.index));
              // A quotient that is a zero, or a negative number, or that has a zero in the denominator, is ignored.
              // Quoting: https://math.libretexts.org/Bookshelves/Applied_Mathematics/Applied_Finite_Mathematics_(Sekhon_and_Bloom)/04%3A_Linear_Programming_The_Simplex_Method/4.02%3A_Maximization_By_The_Simplex_Method
              if (quotient != 0 && quotient > 0 && !isnan(quotient) && !isinf(abs(quotient)))
              {
                pivot_rows.emplace(WeightedIndex(y, quotient));
              }
            }

            while (!pivot_rows.empty())
            {
              const WeightedIndex &pivot_row = pivot_rows.top();

              // Do not attempt to pivot the same cell twice
              if (!std::any_of(solution.visited_pivots.begin(), solution.visited_pivots.end(), [&pivot_column, &pivot_row](const Pivot &pivot)
                               { return pivot.column == pivot_column.index && pivot.row == pivot_row.index; }))
              {
                // Pivot
                // TODO: The bounding needs to be enforced on the pivot itself.
                std::vector<std::vector<long long>> next_matrix = pivot(solution.matrix, pivot_column.index, pivot_row.index);

                std::string next_matrix_key = convert_matrix_to_str(next_matrix);
                if (!has_seen.contains(next_matrix_key))
                {
                  bool is_bounded = true;
                  for (std::size_t button_i = 0; (button_i < machine.button_wiring.size()) && is_bounded; button_i++)
                  {
                    is_bounded = is_bounded && (next_matrix.at(next_matrix.size() - 1).at(machine.voltages.size() * 2 + button_i) <= max_button_presses.at(button_i));
                  }
                  if (is_bounded)
                  {
                    std::vector<Pivot> next_visited_pivots = solution.visited_pivots;
                    next_visited_pivots.emplace_back(Pivot(pivot_column.index, pivot_row.index));

                    solution_stack.emplace(VoltageSolution(next_matrix, next_visited_pivots));
                    has_seen.emplace(next_matrix_key);
                  }
                }
              }

              pivot_rows.pop();
            }

            pivot_columns.pop();
          }
        }
      }
    }

    if (min_presses_to_configure)
    {
      return *min_presses_to_configure;
    }
    else
    {
      throw std::invalid_argument{std::format("Failed to find solution after: {} iterations", num_iterations)};
    }
  }

  long long count_fewest_presses_to_configure_voltage(const std::string_view &manual_instructions)
  {
    const std::vector<Machine> machines = parse_machines(manual_instructions);

    long long num_presses_to_configure{0};

    std::cout << "Starting" << std::endl;

    for (std::size_t machine_i = 0; machine_i < machines.size(); machine_i++)
    {
      std::cout << std::format("Solving matrix: {}", machine_i) << std::endl;

      long long num_presses_for_machine = counter_fewest_presses_to_configure_voltage(machines.at(machine_i));

      num_presses_to_configure += num_presses_for_machine;
      std::cout << std::format("New solution found in: {}. New max {}", num_presses_for_machine, num_presses_to_configure) << std::endl;
    }

    return num_presses_to_configure;
  }

} // year2025::day10
