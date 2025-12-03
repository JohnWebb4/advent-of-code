#include "lib.h"

#include <expected>
#include <format>
#include <memory>
#include <string>
#include <unordered_set>

namespace year2017::day22
{
  const std::string get_node_key(int x, int y);

  enum class rotation
  {
    left = -1,
    right = 1
  };

  enum class direction
  {
    up = 0,
    right = 1,
    down = 2,
    left = 3,
  };
  const std::string direction_to_str(day22::direction direction);

  class vector2
  {
  public:
    int x;
    int y;

    vector2(int _x, int _y);
  };

  const std::expected<vector2, std::string> map_direction_to_vector(day22::direction direction);
  direction rotate(direction direction, rotation rotation);
} // year2017::day22

namespace year2017::day22
{

  int count_bursts_causing_infection(const std::string_view &input_string)
  {
    size_t size_x = input_string.find('\n');
    size_t size_y = input_string.size() / (size_x + 1);

    std::unordered_set<std::string> node_set{};

    int x = -(size_x / 2);
    int y = -(size_y / 2);

    for (size_t i = 0; i < input_string.size(); i++)
    {
      if (input_string[i] == '.')
      {
        x++;
      }
      else if (input_string[i] == '#')
      {
        node_set.insert(day22::get_node_key(x, y));
        x++;
      }
      else if (input_string[i] == '\n')
      {
        x = -(size_x / 2);
        y++;
      }
    }

    int virus_x = 0;
    int virus_y = 0;
    day22::direction virus_direction = day22::direction::up;

    int count_infections = 0;
    const int max_bursts = 10000;
    for (int i = 0; i < max_bursts; i++)
    {
      std::string currenty_key = day22::get_node_key(virus_x, virus_y);

      if (node_set.contains(currenty_key))
      {
        virus_direction = day22::rotate(virus_direction, day22::rotation::right);
        node_set.erase(currenty_key);
      }
      else
      {
        virus_direction = day22::rotate(virus_direction, day22::rotation::left);
        node_set.insert(currenty_key);
        count_infections++;
      }

      // std::cout << "Move " << day22::direction_to_str(virus_direction) << " from " << virus_x << "," << virus_y << std::endl;

      std::unique_ptr<day22::vector2> position_diff = std::make_unique<day22::vector2>(day22::map_direction_to_vector(virus_direction).value());

      virus_x = virus_x + position_diff->x;
      virus_y = virus_y + position_diff->y;
    }

    // Implementation goes here
    return count_infections;
  }

  const std::expected<day22::vector2, std::string> map_direction_to_vector(day22::direction direction)
  {
    if (direction == day22::direction::up)
    {
      return day22::vector2(0, -1);
    }
    else if (direction == day22::direction::right)
    {
      return day22::vector2(1, 0);
    }
    else if (direction == day22::direction::down)
    {
      return day22::vector2(0, 1);
    }
    else if (direction == day22::direction::left)
    {
      return day22::vector2(-1, 0);
    }

    return std::unexpected("Cannot map direction to value");
  }

  const std::string direction_to_str(day22::direction direction)
  {
    if (direction == day22::direction::up)
    {
      return "UP";
    }
    else if (direction == day22::direction::right)
    {
      return "RIGHT";
    }
    else if (direction == day22::direction::down)
    {
      return "DOWN";
    }
    else if (direction == day22::direction::left)
    {
      return "LEFT";
    }

    return "UNKNOWN";
  }

  day22::vector2::vector2(int x, int y) : x(x), y(y)
  {
  }

  direction rotate(direction direction, rotation rotation)
  {
    int next_direction_value = static_cast<int>(direction) + static_cast<int>(rotation);

    if (next_direction_value < 0)
    {
      next_direction_value += 4;
    }

    return static_cast<day22::direction>(next_direction_value % 4);
  }

  const std::string get_node_key(int x, int y)
  {
    return std::format("{0},{1}", x, y);
  }

} // year2017::day22
