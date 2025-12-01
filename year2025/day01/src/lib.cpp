#include "lib.h"

#include <iostream>
#include <optional>
#include <string>
#include <sstream>
#include <vector>

namespace year2025::day01
{
  const std::optional<day01::Direction> parse_direction(char direction_char);
  const std::optional<day01::Command> parse_command(const std::string command_str);

  void read_commands(std::vector<std::optional<day01::Command>> &result, const std::string input);
}

namespace year2025::day01
{
  int get_password_for_door(const std::string input)
  {
    std::vector<std::optional<day01::Command>> commands{};

    read_commands(commands, input);

    int position = 50;
    int num_zeros = 0;

    for (std::optional<day01::Command> command : commands)
    {
      if (command)
      {
        if (command->direction == day01::Direction::LEFT)
        {
          position -= (command->amount % 100);
          position = (position + 100) % 100;
        }
        else if (command->direction == day01::Direction::RIGHT)
        {
          position = (position + command->amount) % 100;
        }

        if (position == 0)
        {
          num_zeros += 1;
        }
      }
    }

    return num_zeros;
  }

  int get_password_for_door_0x434C49434B(const std::string input)
  {
    std::vector<std::optional<day01::Command>> commands{};

    read_commands(commands, input);

    int position = 50;
    int num_zeros = 0;

    for (std::optional<day01::Command> command : commands)
    {
      if (command)
      {
        if (command->direction == day01::Direction::LEFT)
        {

          for (int i = 0; i < command->amount; i++)
          {
            position = ((position - 1) + 100) % 100;

            if (position == 0)
            {
              num_zeros += 1;
            }
          }
        }
        else if (command->direction == day01::Direction::RIGHT)
        {
          for (int i = 0; i < command->amount; i++)
          {
            position = (position + 1) % 100;
            if (position == 0)
            {
              num_zeros += 1;
            }
          }
        }
      }
    }

    return num_zeros;
  }

  const std::optional<day01::Direction> parse_direction(char direction_char)
  {
    if (std::toupper(direction_char) == 'L')
    {
      return std::optional<day01::Direction>{day01::Direction::LEFT};
      return Direction::LEFT;
    }
    else if (std::toupper(direction_char) == 'R')
    {
      return std::optional<day01::Direction>{day01::Direction::RIGHT};
    }
    else
    {
      return {};
    }
  }

  const std::optional<day01::Command> parse_command(const std::string command_str)
  {
    char direction_char = command_str[0];

    if (std::optional<day01::Direction> direction = parse_direction(direction_char))
    {

      std ::string num_string = command_str.substr(1, command_str.size());
      int amount = std::stoi(num_string);

      return day01::Command{*direction, amount};
    }

    return {};
  }

  void read_commands(std::vector<std::optional<day01::Command>> &result, const std::string input)
  {
    std::stringstream ss{input};
    std::string token;
    while (std::getline(ss, token, '\n'))
    {
      result.emplace_back(parse_command(token));
    }
  }

  day01::Command::Command(day01::Direction direction, int amount) : direction{direction}, amount{amount} {}

} // year2025::day01