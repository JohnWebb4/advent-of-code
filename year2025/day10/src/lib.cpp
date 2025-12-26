#include "lib.h"

#include <algorithm>
#include <charconv>
#include <climits>
#include <format>
#include <numeric>
#include <queue>
#include <optional>
#include <string_view>
#include <sstream>
#include <unordered_map>
#include <unordered_set>
#include <vector>

#include <iostream>

namespace year2025::day10
{
  enum class ButtonState
  {
    on,
    off
  };

  class WiringSchematic
  {
  public:
    std::vector<int> wiring;

    WiringSchematic(const std::vector<int> &wiring) : wiring(wiring) {}
  };

  class Machine
  {
  public:
    std::vector<ButtonState> light_diaghram;
    std::vector<WiringSchematic> wiring_schematics;
    std::vector<int> voltages;

    Machine(const std::vector<ButtonState> &light_diaghram, const std::vector<WiringSchematic> &wiring_schematics, const std::vector<int> &voltages) : light_diaghram(light_diaghram), wiring_schematics(wiring_schematics), voltages(voltages) {}
  };

  class MachineSolution
  {
  public:
    std::vector<ButtonState> light_diaghram;
    int num_button_presses;
    int value;

    MachineSolution(const std::vector<ButtonState> &light_diaghram, int num_button_presses, int value) : light_diaghram(light_diaghram), num_button_presses(num_button_presses), value(value) {}
  };

  void parse_light_diaghram(std::vector<ButtonState> &light_diaghram, std::string_view light_diaghram_view)
  {
    for (std::size_t char_index = 1; char_index < light_diaghram_view.size() - 1; char_index++)
    {
      switch (light_diaghram_view.at(char_index))
      {
      case '.':
        light_diaghram.emplace_back(ButtonState::off);
        break;
      case '#':
        light_diaghram.emplace_back(ButtonState::on);
        break;
      default:
        throw "Error parsing light diaghram";
      }
    }
  }

  WiringSchematic parse_wiring_schematic(std::string_view wiring_schematic_view)
  {
    std::vector<int> buttons{};
    std::size_t prev_comma_index = 1;
    for (std::size_t comma_index = wiring_schematic_view.find(','); comma_index != -1; comma_index = wiring_schematic_view.find(',', prev_comma_index))
    {
      std::string_view value_view = wiring_schematic_view.substr(prev_comma_index, comma_index - prev_comma_index);

      int value{0};
      if (auto [p, ec] = std::from_chars(value_view.data(), value_view.data() + value_view.size(), value); ec == std::errc{})
      {
        buttons.emplace_back(value);
      }
      prev_comma_index = comma_index + 1;
    }
    std::string_view final_value_view = wiring_schematic_view.substr(prev_comma_index, wiring_schematic_view.size() - prev_comma_index - 1);
    int final_value{0};
    if (auto [p, ec] = std::from_chars(final_value_view.data(), final_value_view.data() + final_value_view.size(), final_value); ec == std::errc())
    {
      buttons.emplace_back(final_value);
    }

    return WiringSchematic(buttons);
  }

  void parse_wiring_schematics(std::vector<WiringSchematic> &wiring_schematics, std::string_view wiring_schematics_view)
  {
    std::size_t prev_space_index = 0;
    for (std::size_t space_index = wiring_schematics_view.find(' '); space_index != -1; space_index = wiring_schematics_view.find(' ', prev_space_index))
    {
      wiring_schematics.emplace_back(parse_wiring_schematic(wiring_schematics_view.substr(prev_space_index, space_index - prev_space_index)));

      prev_space_index = space_index + 1;
    }
    wiring_schematics.emplace_back(parse_wiring_schematic(wiring_schematics_view.substr(prev_space_index)));
  }

  void parse_voltages(std::vector<int> &voltages, std::string_view voltages_view)
  {
    std::size_t prev_comma_index = 1;
    for (std::size_t comma_index = voltages_view.find(','); comma_index != -1; comma_index = voltages_view.find(',', prev_comma_index))
    {
      std::string_view voltage_view = voltages_view.substr(prev_comma_index, comma_index - prev_comma_index);

      int value{0};
      if (auto [p, ec] = std::from_chars(voltage_view.data(), voltage_view.data() + voltage_view.size(), value); ec == std::errc())
      {
        voltages.emplace_back(value);
      }

      prev_comma_index = comma_index + 1;
    }

    std::string_view final_value_view = voltages_view.substr(prev_comma_index, voltages_view.size() - prev_comma_index - 1);
    int final_value{0};
    if (auto [p, ec] = std::from_chars(final_value_view.data(), final_value_view.data() + final_value_view.size(), final_value); ec == std::errc())
    {
      voltages.emplace_back(final_value);
    }
  }

  Machine parse_machine(std::string_view machine_instruction)
  {

    std::size_t light_diaghram_index = machine_instruction.find(']') + 1;

    std::string_view light_diaghram_view = machine_instruction.substr(
        0,
        light_diaghram_index);
    std::vector<ButtonState> light_diaghram{};
    parse_light_diaghram(light_diaghram, light_diaghram_view);

    std::size_t wiring_schematics_index = machine_instruction.find('{');
    std::string_view wiring_schematics_view = machine_instruction.substr(
        light_diaghram_index + 1,
        wiring_schematics_index - light_diaghram_index - 2);
    std::vector<WiringSchematic> wiring_schematics{};
    parse_wiring_schematics(wiring_schematics, wiring_schematics_view);

    std::string_view voltages_view = machine_instruction.substr(
        wiring_schematics_index);
    std::vector<int> voltages{};
    parse_voltages(voltages, voltages_view);

    return Machine(light_diaghram, wiring_schematics, voltages);
  }

  void parse_machines(std::vector<Machine> &machines, std::string_view manual_instructions)
  {
    std::size_t prev_eol_index = 0;
    std::size_t eol_index = 0;
    for (std::size_t eol_index = manual_instructions.find('\n'); eol_index != -1; eol_index = manual_instructions.find('\n', eol_index + 1))
    {
      std::string_view machine_instruction = manual_instructions.substr(prev_eol_index, eol_index - prev_eol_index);

      machines.emplace_back(parse_machine(machine_instruction));

      prev_eol_index = eol_index + 1;
    }

    {
      std::string_view machine_instruction = manual_instructions.substr(prev_eol_index);

      machines.emplace_back(parse_machine(machine_instruction));
    }
  }

  struct SortSolutionByValue
  {
    bool operator()(const MachineSolution &l, const MachineSolution &r) const { return (l.value - l.num_button_presses) < (r.value - r.num_button_presses); }
  } custom_sort_by_value;

  const std::string light_diaghram_to_string(std::vector<ButtonState> &light_diaghram)
  {
    std::stringstream key_stream{};

    for (ButtonState &button_state : light_diaghram)
    {
      key_stream << (button_state == ButtonState::on ? '#' : '.');
    }

    return key_stream.str();
  }

  long long count_fewest_presses_to_configure_machine(const Machine &machine)
  {
    // Find solution
    std::priority_queue<MachineSolution, std::vector<MachineSolution>, SortSolutionByValue>
        solution_queue{custom_sort_by_value};

    std::vector<ButtonState> initial_light_diaghram{};
    for (int i = 0; i < machine.light_diaghram.size(); i++)
    {
      initial_light_diaghram.emplace_back(ButtonState::off);
    }

    std::unordered_map<std::string, int> button_state_path_map{};
    solution_queue.emplace(MachineSolution(initial_light_diaghram, 0, 0));
    button_state_path_map.emplace(light_diaghram_to_string(initial_light_diaghram), 0);

    long long num_fewest_presses{LLONG_MAX};
    while ((solution_queue.size() > 0))
    {
      const MachineSolution solution = solution_queue.top();
      solution_queue.pop();

      if (solution_queue.size() > 100000)
      {
        throw std::runtime_error("Solution queue is too large");
      }

      if (solution.num_button_presses < num_fewest_presses)
      {
        bool has_solved = true;
        for (std::size_t button_i = 0; button_i < solution.light_diaghram.size(); button_i++)
        {
          if (solution.light_diaghram[button_i] != machine.light_diaghram[button_i])
          {
            has_solved = false;
            break;
          }
        }

        // Check match
        if (has_solved)
        {
          if (solution.num_button_presses < num_fewest_presses)
          {
            num_fewest_presses = solution.num_button_presses;
          }
        }
        else if (solution.num_button_presses < (num_fewest_presses - 1))
        {
          for (std::size_t wiring_schematic_i = 0; wiring_schematic_i < machine.wiring_schematics.size(); wiring_schematic_i++)
          {
            std::vector<ButtonState> next_light_diaghram = solution.light_diaghram;
            for (int button : machine.wiring_schematics.at(wiring_schematic_i).wiring)
            {
              next_light_diaghram[button] = next_light_diaghram[button] == ButtonState::on ? ButtonState::off : ButtonState::on;
            }

            int value{0};
            for (std::size_t button_i = 0; button_i < next_light_diaghram.size(); button_i++)
            {
              if (next_light_diaghram[button_i] == machine.light_diaghram[button_i])
              {
                value++;
              }
            }

            std::string next_light_diaghram_key = light_diaghram_to_string(next_light_diaghram);

            if (!button_state_path_map.contains(next_light_diaghram_key))
            {
              solution_queue.emplace(MachineSolution(next_light_diaghram, solution.num_button_presses + 1, value));
              button_state_path_map.emplace(next_light_diaghram_key, solution.num_button_presses + 1);
            }
            else if (button_state_path_map.at(next_light_diaghram_key) > (solution.num_button_presses + 1))
            {
              solution_queue.emplace(MachineSolution(next_light_diaghram, solution.num_button_presses + 1, value));
              button_state_path_map.emplace(next_light_diaghram_key, solution.num_button_presses + 1);
            }
          }
        }
      }
    }

    return num_fewest_presses;
  }

  long long count_fewest_presses_to_configure(std::string_view manual_instructions)
  {
    std::vector<Machine> machines;

    parse_machines(machines, manual_instructions);

    long long num_fewest_presses_to_configure{0};
    std::size_t num_machines{0};
    for (Machine &machine : machines)
    {
      num_fewest_presses_to_configure += count_fewest_presses_to_configure_machine(machine);
      num_machines++;
    }

    return num_fewest_presses_to_configure;
  }

  template <typename T>
  class Matrix
  {
  public:
    std::vector<std::vector<T>> values;
    std::size_t width;
    std::size_t height;

    Matrix(std::size_t width, std::size_t height) : values(), width(width), height(height)
    {
      for (std::size_t y = 0; y < height; y++)
      {
        this->values.emplace_back(std::vector<T>(width));
      }
    }

    const T &at(std::size_t x, std::size_t y) const
    {
      return this->values[y][x];
    }

    auto emplace(std::size_t x, std::size_t y, T value)
    {
      if (x >= this->width || x < 0)
      {
        throw std::invalid_argument{"Matrix x value is wrong"};
      }

      if (y >= this->height || y < 0)
      {
        throw std::invalid_argument{"Matrix y value is wrong"};
      }

      return this->values[y][x] = value;
    }

    std::string str() const
    {
      std::stringstream ss;

      for (std::size_t y = 0; y < this->height; y++)
      {
        for (std::size_t x = 0; x < this->width; x++)
        {
          ss << this->at(x, y) << ',';
        }
        ss << '\n';
      }

      return ss.str();
    }
  };

  Matrix<float> convert_machine_to_simple_tableau(const Machine &machine)
  {
    // Note: I am being a bity lazy and doing the simplex transpose along with the conversion.
    Matrix<float> duality_matrix = Matrix<float>(machine.voltages.size() + machine.wiring_schematics.size() + 2, machine.wiring_schematics.size() + 1);

    for (std::size_t schematic_i = 0; schematic_i < machine.wiring_schematics.size(); schematic_i++)
    {
      for (std::size_t y = 0; y < machine.voltages.size(); y++)
      {
        if (std::find(machine.wiring_schematics[schematic_i].wiring.begin(), machine.wiring_schematics[schematic_i].wiring.end(), y) != machine.wiring_schematics[schematic_i].wiring.end())
        {
          duality_matrix.emplace(y, schematic_i, 1);
        }
      }

      duality_matrix.emplace(machine.voltages.size() + schematic_i, schematic_i, 1);
      duality_matrix.emplace(duality_matrix.width - 1, schematic_i, 1);
    }

    for (std::size_t voltage_i = 0; voltage_i < machine.voltages.size(); voltage_i++)
    {
      duality_matrix.emplace(voltage_i, duality_matrix.height - 1, -machine.voltages[voltage_i]);
    }
    duality_matrix.emplace(duality_matrix.width - 2, duality_matrix.height - 1, 1);

    return duality_matrix;
  }

  std::optional<std::size_t> find_pivot_column(Matrix<float> &matrix)
  {
    // Find pivot column
    std::optional<std::size_t>
        pivot_column = std::nullopt;
    for (std::size_t col_i = 0; col_i < matrix.width; col_i++)
    {
      if ((matrix.values[matrix.height - 1][col_i] < 0))
      {
        if ((pivot_column == std::nullopt) || (matrix.values[matrix.height - 1][*pivot_column] > matrix.values[matrix.height - 1][col_i]))
        {
          pivot_column = col_i;
        }
      }
    }

    return pivot_column;
  }

  void pivot(Matrix<float> &matrix, std::size_t pivot_x, std::size_t pivot_y)
  {
    for (std::size_t row_i = 0; row_i < matrix.height; row_i++)
    {
      if (row_i != pivot_y)
      {
        if (matrix.values[row_i][pivot_x] != 0)
        {
          float scale = matrix.values[row_i][pivot_x] / matrix.values[pivot_y][pivot_x];

          if (std::ceilf(scale) == scale)
          {
            for (std::size_t x = 0; x < matrix.width; x++)
            {
              matrix.values[row_i][x] -= scale * matrix.values[pivot_y][x];
            }
          }
        }
      }
    }
  }

  bool is_simplex_solved(const Matrix<float> &matrix)
  {
    // The slack variables must be zero
    std::size_t slack_size = matrix.width - matrix.height - 1;

    // Not fractional or negative values on the bottom row
    for (std::size_t x = 0; x < matrix.width; x++)
    {
      float value = matrix.at(x, matrix.height - 1);

      if (std::ceilf(value) != value)
      {
        return false;
      }
      else if (value < 0)
      {
        return false;
      }
      else if ((x < slack_size) && (value != 0))
      {
        return false;
      }
    }

    return true;
  }

  void solve_simplex(Matrix<float> &duality_matrix)
  {
    std::vector<std::unordered_set<int>> has_tried{};
    for (int i = 0; i < duality_matrix.width; i++)
    {
      has_tried.emplace_back(std::unordered_set<int>{});
    }

    int count_i = 0;
    while (true)
    {
      count_i++;

      std::optional<std::size_t> pivot_column = find_pivot_column(duality_matrix);
      std::optional<float> pivot_quotient = std::nullopt;

      if (pivot_column)
      {
        std::optional<std::size_t> pivot_row = std::nullopt;

        for (std::size_t row_i = 0; row_i < duality_matrix.height - 1; row_i++)
        {
          float row_quotient = duality_matrix.values[row_i][duality_matrix.width - 1] / duality_matrix.values[row_i][*pivot_column];

          if ((!std::isnan(row_quotient)) && (std::abs(row_quotient) != std::numeric_limits<float>::infinity()))
          {
            if ((pivot_quotient == std::nullopt) || (*pivot_quotient > row_quotient))
            {
              if (!has_tried[*pivot_column].contains(row_i))
              {
                pivot_row = row_i;
                pivot_quotient = row_quotient;
              }
            }
          }
        }

        if (pivot_row)
        {
          day10::pivot(duality_matrix, *pivot_column, *pivot_row);
          has_tried[*pivot_column].emplace(*pivot_row);
        }
        else
        {
          throw std::invalid_argument{"Cannot solve"};
        }
      }
      else
      {
        // Done searching
        if (is_simplex_solved(duality_matrix))
        {
          int hi = 0;
          return;
        }
        else
        {
          throw std::invalid_argument{"Failed to solve solution"};
        }
      }
    }
  }

  template <typename T>
  T get_num_button_presses(const Matrix<T> &matrix)
  {
    return matrix.at(matrix.width - 1, matrix.height - 1);
  }

  template <typename T>
  std::string get_matrix_key(const Matrix<T> &matrix)
  {
    std::stringstream ss;

    for (std::size_t x = 0; x < matrix.width - matrix.height - 1; x++)
    {
      // Rounding to three decimal places. Floating precission needs three decimal places and then down to two.
      long long value = (matrix.at(x, matrix.height - 1) * 10000.0f) / 10.0f;
      ss << (static_cast<float>(value) / 1000.0f) << ',';
    }

    return ss.str();
  }

  class BranchAndBoundSolution
  {
  public:
    Matrix<float> matrix;
    float num_button_presses;
    int value;

    BranchAndBoundSolution(const Matrix<float> &matrix, int value) : matrix(matrix), value(value)
    {
      num_button_presses = get_num_button_presses(matrix);
    }
  };

  struct SortBranchAndBoundByValue
  {
    bool operator()(const BranchAndBoundSolution &l, const BranchAndBoundSolution &r) const { return l.value < r.value; }
  } branch_and_bound_sort;

  long long solve_branch_and_bound_simplex(Matrix<float> &duality_matrix)
  {
    std::priority_queue<BranchAndBoundSolution, std::vector<BranchAndBoundSolution>, SortBranchAndBoundByValue> branch_queue(branch_and_bound_sort);

    branch_queue.emplace(BranchAndBoundSolution(duality_matrix, 0));

    std::unordered_map<std::string, int> button_state_path_map{};
    std::optional<std::size_t> min_presses;

    while (!branch_queue.empty())
    {
      const BranchAndBoundSolution solution = branch_queue.top();
      branch_queue.pop();

      if (is_simplex_solved(solution.matrix))
      {
        if ((min_presses == std::nullopt) || (*min_presses > solution.num_button_presses))
        {
          std::cout << "Found solution" << std::endl;
          std::cout << solution.matrix.str() << std::endl;
          min_presses = solution.num_button_presses;
        }
      }
      else if (!min_presses || ((*min_presses - 1) > solution.num_button_presses))
      {
        // For each bottom row
        for (std::size_t pivot_column = 0; pivot_column < solution.matrix.width - 1; pivot_column++)
        {
          if (solution.matrix.at(pivot_column, solution.matrix.height - 1) < 0)
          {
            for (std::size_t pivot_row = 0; pivot_row < solution.matrix.height - 1; pivot_row++)
            {
              float row_quotient = solution.matrix.values[pivot_row][solution.matrix.width - 1] / solution.matrix.values[pivot_row][pivot_column];

            if ((!std::isnan(row_quotient)) && (std::abs(row_quotient) != std::numeric_limits<float>::infinity()))
            {
              // Possible solution
              Matrix<float> new_matrix = solution.matrix;
              pivot(new_matrix, pivot_column, pivot_row);

              std::string new_matrix_key = get_matrix_key(new_matrix);
              float num_button_presses = get_num_button_presses(new_matrix);

                if (!button_state_path_map.contains(new_matrix_key))
                {
                  float value{0.0f};
                  for (int i = 0; i < new_matrix.width - 1; i++)
                  {
                    float cell = new_matrix.at(i, new_matrix.height - 1);
                    if (cell < 0)
                    {
                      // Negative values need solving. Are a cost.
                      value -= std::abs(cell);
                    }
                  }
                  // Number of steps is a cost
                  value -= num_button_presses;

                  branch_queue.emplace(BranchAndBoundSolution(new_matrix, value));
                  button_state_path_map.emplace(new_matrix_key, num_button_presses);
                }
                else if (button_state_path_map.at(new_matrix_key) > num_button_presses)
                {
                  button_state_path_map.erase(new_matrix_key);
                  button_state_path_map.emplace(new_matrix_key, num_button_presses);
                }
              }
            }
          }
        }
      }
    }

    if (min_presses)
    {
      return *min_presses;
    }
    else
    {
      throw std::invalid_argument{"Failed to find solution"};
    }
  }

  long long count_fewest_presses_to_configure_machine_voltage(const Machine &machine)
  {
    std::unique_ptr<Matrix<float>> duality_matrix = std::make_unique<Matrix<float>>(convert_machine_to_simple_tableau(machine));

    return solve_branch_and_bound_simplex(*duality_matrix.get());
  }

  long long count_fewest_presses_to_configure_voltage(std::string_view manual_instructions)
  {
    std::vector<Machine> machines;

    parse_machines(machines, manual_instructions);

    long long num_fewest_presses_to_configure{0};
    std::size_t num_machines{0};
    for (Machine &machine : machines)
    {
      if (num_machines == 23)
      {
        int hi = 0;
      }

      std::cout << "Looking at machine " << num_machines << std::endl;
      long long num_presses = count_fewest_presses_to_configure_machine_voltage(machine);
      std::cout << "Found solution in " << num_presses << std::endl;
      num_fewest_presses_to_configure += num_presses;
      num_machines++;
    }

    return num_fewest_presses_to_configure;
  }

} // year2025::day10
