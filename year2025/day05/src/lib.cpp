#include "lib.h"

#include <algorithm>
#include <string>
#include <string_view>
#include <vector>

namespace year2025::day05
{

  class range
  {
  public:
    long long start;
    long long end;

    range(long long s, long long e) : start(s), end(e) {}

    bool operator<(const day05::range &other) const
    {
      return this->start < other.start;
    }
  };

  void parse_ranges(std::vector<day05::range> &ranges, std::string_view ranges_str);
  void parse_range(day05::range &range, std::string_view range_str);

  void parse_ingredients(std::vector<long long> &ingredients, std::string_view ingredients_str);

  int count_fresh_ingredients(std::string_view fresh_ingredients_str)
  {
    std::string split_token = "\n\n";

    std::size_t split_token_index = fresh_ingredients_str.find(split_token);

    std::string_view ranges_str = fresh_ingredients_str.substr(0, split_token_index);
    std::string_view ingredients_str = fresh_ingredients_str.substr(split_token_index + split_token.size());

    std::vector<day05::range> ranges{};
    day05::parse_ranges(ranges, ranges_str);

    std::vector<long long> ingredients{};
    day05::parse_ingredients(ingredients, ingredients_str);

    long long num_fresh_ingredients = 0;
    for (long long ingredient : ingredients)
    {
      for (day05::range &range : ranges)
      {
        if (ingredient >= range.start && ingredient <= range.end)
        {
          num_fresh_ingredients++;
          break;
        }
      }
    }

    return num_fresh_ingredients;
  }

  long long count_all_fresh_ingredients(std::string_view fresh_ingredients_str)
  {
    std::string split_token = "\n\n";

    std::size_t split_token_index = fresh_ingredients_str.find(split_token);

    std::string_view ranges_str = fresh_ingredients_str.substr(0, split_token_index);
    std::string_view ingredients_str = fresh_ingredients_str.substr(split_token_index + split_token.size());

    std::vector<day05::range> ranges{};
    day05::parse_ranges(ranges, ranges_str);

    std::vector<long long> ingredients{};
    day05::parse_ingredients(ingredients, ingredients_str);

    // Sorting by range
    std::stable_sort(ranges.begin(), ranges.end());

    std::size_t num_fresh_ingredients = 0;
    for (std::size_t range_i = 0; range_i < ranges.size(); range_i++)
    {
      day05::range &range = ranges.at(range_i);
      long long start = range.start;
      long long end = range.end;

      // Check all previous ranges
      for (std::size_t prev_range_i = 0; prev_range_i < range_i; prev_range_i++)
      {
        day05::range &prev_range = ranges.at(prev_range_i);
        if (start >= prev_range.start && start <= prev_range.end)
        {
          start = prev_range.end + 1;
        }

        if (end >= prev_range.start && end <= prev_range.end)
        {
          end = prev_range.end;
        }
      }

      if (end >= start)
      {
        num_fresh_ingredients += end - start + 1;
      }
    }

    return num_fresh_ingredients;
  }

  void parse_ranges(std::vector<day05::range> &ranges, std::string_view ranges_str)
  {
    std::size_t index = 0;
    while (index < ranges_str.size())
    {
      std::size_t line_index = ranges_str.find('\n', index);

      if (line_index == -1)
      {
        break;
      }

      std::string_view line_str = ranges_str.substr(index, line_index - index);

      day05::range range{0, 0};
      day05::parse_range(range, line_str);
      ranges.emplace_back(range);

      index = line_index + 1;
    }

    {
      std::string_view line_str = ranges_str.substr(index);
      day05::range range{0, 0};
      day05::parse_range(range, line_str);
      ranges.emplace_back(range);
    }
  }

  void parse_range(day05::range &range, std::string_view range_str)
  {
    std::size_t split_index = range_str.find('-');
    std::string start_str{range_str.substr(0, split_index)};
    range.start = std::stoll(start_str);

    std::string end_str{range_str.substr(split_index + 1)};
    range.end = std::stoll(end_str);
  }

  void parse_ingredients(std::vector<long long> &ingrdients, std::string_view ingredients_str)
  {
    std::size_t index = 0;
    while (index < ingredients_str.size())
    {
      std::size_t line_index = ingredients_str.find('\n', index);

      if (line_index == -1)
      {
        break;
      }

      std::string ingredient_str{ingredients_str.substr(index, line_index - index)};
      ingrdients.emplace_back(std::stoll(ingredient_str));

      index = line_index + 1;
    }

    {
      std::string ingredient_str{ingredients_str.substr(index)};
      ingrdients.emplace_back(std::stoll(ingredient_str));
    }
  }
} // year2025::day05