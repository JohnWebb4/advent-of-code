#include "lib.h"

#include <string_view>
#include <vector>

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

  long long
  count_fewest_presses_to_configure(const std::string_view &manual_instructions)
  {
    const std::vector<Machine> machines = parse_machines(manual_instructions);

    int hi = 0;

    return -1;
  }

  long long count_fewest_presses_to_configure_voltage(const std::string_view &manual_instructions)
  {

    return -1;
  }

} // year2025::day10
