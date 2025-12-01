#include <string>
#include <vector>

namespace year2025::day01
{
  enum Direction
  {
    LEFT,
    RIGHT,
  };

  class Command
  {
  public:
    const Direction direction;
    const int amount;

    Command(day01::Direction direction, int amount);
  };

  int get_password_for_door(const std::string input);
  int get_password_for_door_0x434C49434B(const std::string input);
} // year2025::day01