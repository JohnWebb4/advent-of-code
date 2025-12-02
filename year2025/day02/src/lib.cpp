#include "lib.h"

#include <iostream>
#include <vector>
#include <string>
#include <sstream>

namespace year2025::day02
{
  class Range
  {
  public:
    const long long start;
    const long long end;

    Range(long long s, long long e) : start(s), end(e) {}

    static day02::Range parse_range(const std::string &range_string);
  };

  void read_input(std::vector<day02::Range> &output, const std::string &input_string);

} // year2025::day02

long long year2025::day02::sum_all_invalid_ids(const std::string_view &input_string)
{
  std::vector<day02::Range> ranges;

  read_input(ranges, std::string(input_string));

  long long count_invalid_ids = 0;
  for (const day02::Range range : ranges)
  {
    for (long long id = range.start; id <= range.end; id++)
    {
      std::string id_str = std::to_string(id);

      if (id_str.substr(0, id_str.size() / 2) == id_str.substr(id_str.size() / 2))
      {
        count_invalid_ids += id;
      }
    }
  }

  return count_invalid_ids;
}

long long year2025::day02::sum_all_invalid_ids_2(const std::string_view &input_string)
{
  std::vector<day02::Range> ranges;

  read_input(ranges, std::string(input_string));

  long long count_invalid_ids = 0;
  for (const day02::Range range : ranges)
  {
    for (long long id = range.start; id <= range.end; id++)
    {
      std::string id_str = std::to_string(id);

      bool is_invalid = false;
      for (size_t group_size = 1; (group_size <= (id_str.size() / 2)) && !is_invalid; group_size++)
      {
        if (id_str.size() % group_size != 0)
        {
          // If does not evenly divide, skip
          continue;
        }

        const std::string group_0 = id_str.substr(0, group_size);
        int num_groups = id_str.size() / group_size;
        for (int group_i = 1; group_i < num_groups; group_i++)
        {
          int start_index = group_i * group_size;
          std::string group = id_str.substr(start_index, group_size);
          if (group_0 != id_str.substr(start_index, group_size))
          {
            break;
          }

          // If all valid
          if (group_i == num_groups - 1)
          {
            is_invalid = true;
          }
        }
      }

      if (is_invalid)
      {
        count_invalid_ids += id;
      }
    }
  }

  return count_invalid_ids;
}

void year2025::day02::read_input(std::vector<day02::Range> &output, const std::string &input_string)
{
  std::stringstream input_stream(input_string);
  std::string token;

  while (std::getline(input_stream, token, ','))
  {
    output.push_back(day02::Range::parse_range(token));
  }
}

year2025::day02::Range year2025::day02::Range::parse_range(const std::string &range_string)
{
  size_t dash_pos = range_string.find('-');
  long long start = std::stoll(range_string.substr(0, dash_pos));
  long long end = std::stoll(range_string.substr(dash_pos + 1));

  return day02::Range(start, end);
}