#include "lib.h"

#include <memory>
#include <numeric>
#include <queue>
#include <string_view>
#include <sstream>
#include <unordered_set>
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

  long long count_fewest_presses_to_configure_voltage(const std::string_view &manual_instructions)
  {

    return -1;
  }

} // year2025::day10
