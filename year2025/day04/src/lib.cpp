#include "lib.h"

#include <expected>
#include <format>
#include <memory>
#include <optional>
#include <unordered_map>
#include <string_view>
#include <vector>

namespace year2025::day04
{
  const char grid_type_roll = '@';

  enum class grid_type
  {
    roll
  };

  class grid_map
  {
  public:
    std::unordered_map<std::string, day04::grid_type> map;
    std::size_t max_x{0};
    std::size_t max_y{0};

    static const std::string get_key(std::size_t x, std::size_t y);

    grid_map();

    std::optional<day04::grid_type> at(std::size_t x, std::size_t y);
    void emplace(std::size_t x, std::size_t y, day04::grid_type type);
    std::size_t erase(std::size_t x, std::size_t y);
  };

  std::expected<day04::grid_type, std::string> char_to_grid_type(char grid_char);
  std::expected<char, std::string> grid_type_to_char(day04::grid_type grid_type);
  void parse_rolls_grid_string(day04::grid_map &grid_map, std::string_view rolls_grid_string);

  int count_rolls_accessed(const std::string_view rolls_grid_string)
  {
    std::unique_ptr<day04::grid_map> grid_map{std::make_unique<day04::grid_map>(day04::grid_map())};

    parse_rolls_grid_string(*grid_map, rolls_grid_string);

    int num_accessible_rolls = 0;
    for (const auto &[key, value] : grid_map->map)
    {
      int comma_index = key.find(',');
      int x = std::stol(key.substr(0, comma_index));
      int y = std::stol(key.substr(comma_index + 1));

      int num_surrounding_rolls{0};

      std::vector<std::pair<long, long>> neighbors{
          std::pair<long, long>{x - 1, y - 1},
          std::pair<long, long>{x - 1, y},
          std::pair<long, long>{x - 1, y + 1},
          std::pair<long, long>{x, y - 1},
          std::pair<long, long>{x, y + 1},
          std::pair<long, long>{x + 1, y - 1},
          std::pair<long, long>{x + 1, y},
          std::pair<long, long>{x + 1, y + 1},
      };

      for (std::pair<long, long> neighbor : neighbors)
      {
        if (neighbor.first >= 0 && neighbor.second >= 0)
        {
          if (grid_map->at(static_cast<std::size_t>(neighbor.first), static_cast<std::size_t>(neighbor.second)))
          {
            num_surrounding_rolls++;
          }
        }
      }

      if (num_surrounding_rolls < 4)
      {
        num_accessible_rolls++;
      }
    }

    return num_accessible_rolls;
  }

  int count_rolls_removed(const std::string_view rolls_grid_string)
  {
    std::unique_ptr<day04::grid_map> grid_map{std::make_unique<day04::grid_map>(day04::grid_map())};

    parse_rolls_grid_string(*grid_map, rolls_grid_string);

    int num_accessible_rolls = 0;
    bool has_removed = true;
    while (has_removed)
    {
      has_removed = false;

      std::vector<std::pair<long, long>> cells_to_erase{};
      for (const auto &[key, value] : grid_map->map)
      {
        int comma_index = key.find(',');

        long x = std::stol(key.substr(0, comma_index));
        long y = std::stol(key.substr(comma_index + 1));

        int num_surrounding_rolls{0};

        // It might make sense to pre-calculate this and then update all neighbors on remove
        std::vector<std::pair<long, long>> neighbors{
            std::pair<long, long>{x - 1, y - 1},
            std::pair<long, long>{x - 1, y},
            std::pair<long, long>{x - 1, y + 1},
            std::pair<long, long>{x, y - 1},
            std::pair<long, long>{x, y + 1},
            std::pair<long, long>{x + 1, y - 1},
            std::pair<long, long>{x + 1, y},
            std::pair<long, long>{x + 1, y + 1},
        };

        for (std::pair<long, long> neighbor : neighbors)
        {
          if (neighbor.first >= 0 && neighbor.second >= 0)
          {
            if (grid_map->at(static_cast<std::size_t>(neighbor.first), static_cast<std::size_t>(neighbor.second)))
            {
              num_surrounding_rolls++;
            }
          }
        }

        if (num_surrounding_rolls < 4)
        {
          cells_to_erase.emplace_back(x, y);
          num_accessible_rolls++;
          has_removed = true;
        }
      }

      for (std::pair<long, long> &cell_to_erase : cells_to_erase)
      {
        grid_map->erase(cell_to_erase.first, cell_to_erase.second);
      }
      cells_to_erase.clear();
    }

    return num_accessible_rolls;
  }

  void parse_rolls_grid_string(day04::grid_map &grid_map, std::string_view rolls_grid_string)
  {
    std::size_t x = 0;
    std::size_t y = 0;
    for (std::size_t i = 0; i < rolls_grid_string.size(); i++)
    {
      if (rolls_grid_string[i] == '\n')
      {
        x = 0;
        y += 1;
      }
      else if (rolls_grid_string[i] == day04::grid_type_roll)
      {
        grid_map.emplace(x, y, day04::grid_type::roll);
        x++;
      }
      else
      {
        x++;
      }
    }
  }

  std::expected<day04::grid_type, std::string> char_to_grid_type(char &grid_char)
  {
    if (grid_char == '@')
    {
      return day04::grid_type::roll;
    }

    return std::unexpected("Error converting char to grid type");
  }

  std::expected<char, std::string> grid_type_to_char(day04::grid_type grid_type)
  {
    if (grid_type == day04::grid_type::roll)
    {
      return '@';
    }

    return std::unexpected("Error converting grid type to char");
  }

  day04::grid_map::grid_map()
  {
  }

  const std::string day04::grid_map::get_key(std::size_t x, std::size_t y)
  {
    return std::format("{0},{1}", x, y);
  }

  std::optional<day04::grid_type> day04::grid_map::at(std::size_t x, std::size_t y)
  {
    try
    {
      return std::optional<day04::grid_type>(this->map.at(day04::grid_map::get_key(x, y)));
    }
    catch (const std::out_of_range &e)
    {
      return std::nullopt;
    }
  }

  void day04::grid_map::emplace(std::size_t x, std::size_t y, day04::grid_type type)
  {
    this->map.emplace(day04::grid_map::get_key(x, y), type);

    if (this->max_x < x)
    {
      this->max_x = x;
    }

    if (this->max_y < y)
    {
      this->max_y = y;
    }
  }

  std::size_t day04::grid_map::erase(std::size_t x, std::size_t y)
  {
    return this->map.erase(day04::grid_map::get_key(x, y));

    // Skipping worrying about updating min_x,max_x, etc.
  }

} // year2025::day04