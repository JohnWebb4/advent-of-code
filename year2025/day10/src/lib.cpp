#include "lib.h"

#include <charconv>
#include <climits>
#include <format>
#include <numeric>
#include <queue>
#include <string_view>
#include <sstream>
#include <unordered_map>
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
    std::vector<std::vector<int>> values;
    std::size_t width;
    std::size_t height;

    Matrix(std::size_t width, std::size_t height) : values(), width(width), height(height)
    {
      for (std::size_t y = 0; y < height; y++)
      {
        this->values.emplace_back(std::vector<int>(width));
      }
    }

    const T &at(int x, int y) const
    {
      return this->values[y][x];
    }

    auto emplace(int x, int y, T value)
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
  };

  long long count_fewest_presses_to_configure_machine_voltage(const Machine &machine)
  {
    // Simplex
    // Since every coefficient is 1 (every button increments by 1), we can pre-calculate the transpose and duality matrix.

    std::unique_ptr<Matrix<int>> duality_matrix = std::make_unique<Matrix<int>>(Matrix<int>(machine.voltages.size() + machine.wiring_schematics.size() + 2, machine.voltages.size() + machine.wiring_schematics.size() + 1));

    for (std::size_t schematic_i = 0; schematic_i < machine.wiring_schematics.size(); schematic_i++)
    {
      for (std::size_t y = 0; y < machine.voltages.size(); y++)
      {
        if (std::find(machine.wiring_schematics[schematic_i].wiring.begin(), machine.wiring_schematics[schematic_i].wiring.end(), y) != machine.wiring_schematics[schematic_i].wiring.end())
        {
          duality_matrix->emplace(y, schematic_i, 1);
        }
      }

      duality_matrix->emplace(machine.voltages.size() + schematic_i, schematic_i, 1);
      duality_matrix->emplace(duality_matrix->width - 1, schematic_i, 1);
    }

    for (std::size_t voltage_i = 0; voltage_i < machine.voltages.size(); voltage_i++)
    {
      duality_matrix->emplace(voltage_i, duality_matrix->height - 1, -machine.voltages[voltage_i]);
    }
    duality_matrix->emplace(duality_matrix->width - 2, duality_matrix->height - 1, 1);

    // Sort diagonal
    for (std::size_t diagonal_i = 0; diagonal_i < duality_matrix->height; diagonal_i++)
    {
      std::vector<std::vector<int>>::iterator diagonal_iter = std::find_if(duality_matrix->values.begin() + diagonal_i, duality_matrix->values.end(), [diagonal_i](const std::vector<int> &v)
                                                                           { return v[diagonal_i] == 1; });

      if (diagonal_iter != duality_matrix->values.end())
      {
        std::iter_swap(duality_matrix->values.begin() + diagonal_i, diagonal_iter);
      }
      else
      {
        // We have to copy a previous row to make this work
        std::vector<std::vector<int>>::iterator copy_diagonal_iter = std::find_if(duality_matrix->values.begin(), duality_matrix->values.begin() + diagonal_i - 1, [diagonal_i](const std::vector<int> &v)
                                                                                  { return v[diagonal_i] == 1; });

        if (copy_diagonal_iter != duality_matrix->values.end())
        {
          for (int x = 0; x < copy_diagonal_iter->size(); x++)
          {
            duality_matrix->values[diagonal_i][x] += (*copy_diagonal_iter)[x];
          }
        }
        else
        {
          throw std::invalid_argument{"I can't find a good row"};
        }
      }
    }

    // Reduced row echelon
    // Assuming the first diagonal is one. From the problem description.
    for (std::size_t diag_i = 0; diag_i < machine.voltages.size(); diag_i++)
    {
      {
        int scale = duality_matrix->at(diag_i, diag_i);
        for (int x = 0; x < duality_matrix->width; x++)
        {
          duality_matrix->values[diag_i][x] *= scale;
        }
      }

      for (std::size_t y = diag_i + 1; y < machine.voltages.size(); y++)
      {
        if (duality_matrix->at(diag_i, y) != 0)
        {
          int scale = duality_matrix->at(diag_i, y);

          for (std::size_t x = 0; x < duality_matrix->width; x++)
          {
            duality_matrix->values[y][x] -= (scale * duality_matrix->values[diag_i][x]);
          }
        }
      }
    }

    for (long long diag_i = machine.voltages.size() - 1; diag_i >= 0; diag_i--)
    {
      {
        int scale = duality_matrix->at(diag_i, diag_i);
        for (int x = 0; x < duality_matrix->width; x++)
        {
          duality_matrix->values[diag_i][x] *= scale;
        }
      }

      for (long long y = (diag_i - 1); y >= 0; y--)
      {
        if (duality_matrix->at(diag_i, y) != 0)
        {
          int scale = duality_matrix->at(diag_i, y);

          for (std::size_t x = 0; x < duality_matrix->width; x++)
          {
            duality_matrix->values[y][x] -= (scale * duality_matrix->values[diag_i][x]);
          }
        }
      }
    }

    for (std::size_t diag_i = 0; diag_i < duality_matrix->height; diag_i++)
    {
      {
        int scale = duality_matrix->at(diag_i, diag_i);
        for (int x = 0; x < duality_matrix->width; x++)
        {
          duality_matrix->values[diag_i][x] *= scale;
        }
      }

      for (std::size_t y = diag_i + 1; y < duality_matrix->height; y++)
      {
        if (duality_matrix->at(diag_i, y) != 0)
        {
          int scale = duality_matrix->at(diag_i, y);

          for (std::size_t x = 0; x < duality_matrix->width; x++)
          {
            duality_matrix->values[y][x] -= (scale * duality_matrix->values[diag_i][x]);
          }
        }
      }
    }

    return duality_matrix->at(duality_matrix->width - 1, duality_matrix->height - 1);
  }

  long long count_fewest_presses_to_configure_voltage(std::string_view manual_instructions)
  {
    std::vector<Machine> machines;

    parse_machines(machines, manual_instructions);

    long long num_fewest_presses_to_configure{0};
    std::size_t num_machines{0};
    for (Machine &machine : machines)
    {
      std::cout << "Looking at machine " << num_machines << std::endl;
      num_fewest_presses_to_configure += count_fewest_presses_to_configure_machine_voltage(machine);
      num_machines++;
    }

    return num_fewest_presses_to_configure;
  }

} // year2025::day10