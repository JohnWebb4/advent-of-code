#include "lib.h"

#include <sstream>
#include <string_view>
#include <vector>

namespace year2025::day03
{
  int get_total_voltage(const std::string_view &input)
  {
    std::vector<std::vector<int>> banks{};

    int bank_index = 0;
    std::vector<int> bank{};
    for (int i = 0; i < input.size(); i++)
    {
      if (input[i] == '\n')
      {
        banks.emplace_back(bank);
        bank.clear();
      }
      else
      {
        int value = input[i] - '0';
        bank.emplace_back(value);
      }
    }
    banks.emplace_back(bank);
    bank.clear();

    int total_voltage = 0;

    for (std::vector<int> bank : banks)
    {
      int first_digit = 0;
      int first_digit_index = 0;
      // We need two digits, so skip the last value
      for (int i = 0; i < bank.size() - 1; i++)
      {
        if (bank[i] > first_digit)
        {
          first_digit = bank[i];
          first_digit_index = i;
        }
      }

      int second_digit = 0;
      for (int i = first_digit_index + 1; i < bank.size(); i++)
      {
        if (bank[i] > second_digit)
        {
          second_digit = bank[i];
        }
      }

      total_voltage += std::stoi(std::format("{}{}", first_digit, second_digit));
    }

    return total_voltage;
  }

  long long get_total_voltage_override(const std::string_view &input)
  {
    std::vector<std::vector<int>> banks{};

    int bank_index = 0;
    std::vector<int> bank{};
    for (int i = 0; i < input.size(); i++)
    {
      if (input[i] == '\n')
      {
        banks.emplace_back(bank);
        bank.clear();
      }
      else
      {
        int value = static_cast<int>(input[i] - '0');
        bank.emplace_back(value);
      }
    }
    banks.emplace_back(bank);
    bank.clear();

    long long total_voltage = 0;

    for (std::vector<int> bank : banks)
    {
      std::vector<char> digits{};
      int next_digit_start_index = 0;
      for (int voltage_digit_index = 0; voltage_digit_index < 12; voltage_digit_index++)
      {
        int max_digit = 0;
        for (int battery_i = next_digit_start_index; battery_i < bank.size() - 11 + voltage_digit_index; battery_i++)
        {
          if (bank[battery_i] > max_digit)
          {
            max_digit = bank[battery_i];
            next_digit_start_index = battery_i + 1;
          }
        }

        digits.emplace_back(max_digit);
      }

      std::stringstream voltage_stream;
      for (int digit : digits)
      {
        voltage_stream << static_cast<char>(digit + '0');
      }

      total_voltage += std::stoll(voltage_stream.str());
    }

    return total_voltage;
  }
} // year2025::day03