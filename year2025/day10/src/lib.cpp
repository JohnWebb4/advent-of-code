#include "lib.h"

#include <charconv>
#include <climits>
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

      // std::cout << "Testing " << solution.num_button_presses << " " << solution.value << " ";
      // for (const ButtonState &button : solution.light_diaghram)
      // {
      //   std::cout << (button == ButtonState::on ? '#' : '.');
      // }
      // std::cout << "=>";
      // for (const ButtonState &button : machine.light_diaghram)
      // {
      //   std::cout << (button == ButtonState::on ? '#' : '.');
      // }
      // std::cout << std::endl;

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
          std::cout << "Found a solution in" << solution.num_button_presses << std::endl;
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

    std::cout << "Num fewest presses " << num_fewest_presses << std::endl;

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
      std::cout << "Looking at machine " << num_machines << std::endl;
      num_fewest_presses_to_configure += count_fewest_presses_to_configure_machine(machine);
      num_machines++;
    }

    return num_fewest_presses_to_configure;
  }

} // year2025::day10