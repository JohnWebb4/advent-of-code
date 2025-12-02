#include "lib.h"

#include <format>
#include <iostream>
#include <string>
#include <unordered_set>

namespace year2017::day22
{
  const std::string get_node_key(int x, int y);
} // year2017::day22

int year2017::day22::count_bursts_causing_infection(const std::string_view &input_string)
{
  std::unordered_set<std::string> node_set{};

  int x = 0;
  int y = 0;
  for (std::string_view::iterator hi = input_string.begin(); input_string.end(); hi++)
  {
    if (*hi == '.')
    {
      x++;
    }
    else if (*hi == '#')
    {
      node_set.insert(day22::get_node_key(x, y));
      x++;
    }
    else if (*hi == '\n')
    {
      x = 0;
      y++;
    }
  }

  for (auto hi : node_set)
  {
    std::cout << hi << std::endl;
  }

  // Implementation goes here
  return 0;
}

const std::string year2017::day22::get_node_key(int x, int y)
{
  return std::format("{0},{1}", x, y);
}