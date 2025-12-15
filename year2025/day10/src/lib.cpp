#include "lib.h"

#include <charconv>
#include <climits>
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

  class VoltageSolution
  {
  public:
    std::vector<int> button_presses;
    long long value;

    VoltageSolution(const std::vector<int> &button_presses, long long value) : button_presses(button_presses), value(value) {}
  };

  const long long VOLTAGE_VALUE_SCALE = 2;
  struct SortVoltageByValue
  {
    bool operator()(const VoltageSolution &l, const VoltageSolution &r) const
    {
      return l.value < r.value;
    }
  } custom_voltage_sort_by_value;

  const std::string voltages_to_string(const std::vector<int> &voltages)
  {
    std::stringstream key_stream{};

    for (const int &voltage : voltages)
    {
      key_stream << voltage << ',';
    }

    return key_stream.str();
  }

  long long count_fewest_presses_to_configure_machine_voltage(const Machine &machine)
  {
    const std::string machine_voltages_key = voltages_to_string(machine.voltages);

    std::priority_queue<VoltageSolution, std::vector<VoltageSolution>, SortVoltageByValue>
        solution_queue{custom_voltage_sort_by_value};

    std::vector<int> initial_button_presses{};
    for (int i = 0; i < machine.wiring_schematics.size(); i++)
    {
      initial_button_presses.emplace_back(0);
    }
    solution_queue.emplace(VoltageSolution(initial_button_presses, 0));

    std::unordered_map<std::string, long long> voltages_steps_map{};

    long long num_fewest_presses{LONG_LONG_MAX};
    long long num_steps = 0;
    while ((solution_queue.size() > 0))
    {
      if ((num_steps % 1000) == 0)
      {
        std::cout << "Thinking...." << num_steps << " " << solution_queue.size() << std::endl;
      }
      num_steps++;

      if (solution_queue.size() > 1000000)
      {
        throw std::runtime_error("Solution queue is too large");
      }

      VoltageSolution solution = solution_queue.top();
      solution_queue.pop();

      long long num_button_presses = std::reduce(solution.button_presses.begin(), solution.button_presses.end());

      if (num_button_presses < num_fewest_presses)
      {
        std::vector<int> voltages(static_cast<int>(machine.voltages.size()), 0);
        for (std::size_t button_i = 0; button_i < solution.button_presses.size(); button_i++)
        {
          for (const int &counter : machine.wiring_schematics[button_i].wiring)
          {
            voltages[counter] += solution.button_presses[button_i];
          }
        }
        std::string voltages_key = voltages_to_string(voltages);

        if (!voltages_steps_map.contains(voltages_key) || (voltages_steps_map.at(voltages_key) >= num_button_presses))
        {
          // std::cout << voltages_key << std::endl;

          // Check match
          if (voltages_key == machine_voltages_key)
          {
            std::cout << "Found a solution in " << num_steps << " => " << num_button_presses << std::endl;
            if (num_button_presses < num_fewest_presses)
            {
              num_fewest_presses = num_button_presses;
            }
          }
          else if (num_button_presses < (num_fewest_presses - 1))
          {
            for (std::size_t button_i = 0; button_i < machine.wiring_schematics.size(); button_i++)
            {
              // Find the max value we can increment this schematic by
              // Then count down from that number and emplace
              int max_button_presses = INT32_MAX;
              for (int counter_i : machine.wiring_schematics[button_i].wiring)
              {
                max_button_presses = std::min(max_button_presses, machine.voltages[counter_i] - voltages[counter_i]);
              }

              for (int button_presses = max_button_presses; button_presses >= 0; button_presses--)
              {
                bool is_any_counter_over_limit = false;
                std::vector<int> next_voltages = voltages;
                std::vector<int> next_button_presses = solution.button_presses;
                next_button_presses[button_i] += button_presses;
                for (int counter : machine.wiring_schematics.at(button_i).wiring)
                {
                  // You can only increment counters.
                  // If any counter is over the desired value, we skip.
                  if ((next_voltages[counter] + button_presses) > machine.voltages[counter])
                  {
                    is_any_counter_over_limit = true;
                    break;
                  }
                  else
                  {
                    next_voltages[counter] += button_presses;
                  }
                }

                if (!is_any_counter_over_limit)
                {

                  std::string next_voltages_key = voltages_to_string(next_voltages);
                  long long next_num_button_presses = num_button_presses + button_presses;

                  if (!voltages_steps_map.contains(next_voltages_key) || voltages_steps_map.at(next_voltages_key) > num_button_presses)
                  {
                    long long next_value{-next_num_button_presses};
                    for (std::size_t counter_i = 0; counter_i < next_voltages.size(); counter_i++)
                    {
                      next_value += VOLTAGE_VALUE_SCALE * next_voltages[counter_i];
                    }

                    solution_queue.emplace(VoltageSolution(next_button_presses, next_value));

                    if (voltages_steps_map.contains(next_voltages_key))
                    {
                      voltages_steps_map[next_voltages_key] = next_num_button_presses;
                    }
                    else
                    {
                      voltages_steps_map.emplace(next_voltages_key, next_num_button_presses);
                    }
                  }
                }
              }
            }
          }
        }
      }
    }

    std::cout << "Num fewest presses in " << num_steps << " => " << num_fewest_presses << std::endl;

    return num_fewest_presses;
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