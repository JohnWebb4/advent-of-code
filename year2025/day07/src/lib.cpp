#include "lib.h"

#include <expected>
#include <format>
#include <memory>
#include <optional>
#include <stack>
#include <unordered_map>
#include <unordered_set>
#include <vector>

namespace year2025::day07
{
    class tachyon
    {
    public:
        int x;
        int y;

        tachyon(int _x, int _y) : x(_x), y(_y) {}
    };

    enum diaghram_types
    {
        empty,
        start,
        splitter,
    };

    std::expected<diaghram_types, std::string> parse_diagram_type(char c)
    {
        if (c == '.')
        {
            return diaghram_types::empty;
        }

        if (c == 'S')
        {
            return diaghram_types::start;
        }

        if (c == '^')
        {
            return diaghram_types::splitter;
        }

        return std::unexpected("Cannot parse value");
    }

    template <typename T>
    class map_2d
    {
    public:
        std::unordered_map<std::string, T> map;

        inline const static std::string get_map_key(int x, int y)
        {
            return std::format("{},{}", x, y);
        }

        map_2d() : map() {}

        std::optional<T> at(int x, int y) const
        {
            try
            {
                return this->map.at(map_2d::get_map_key(x, y));
            }
            catch (std::exception)
            {
                return std::nullopt;
            }
        }

        void emplace(int x, int y, T value)
        {
            this->map.emplace(map_2d::get_map_key(x, y), value);
        }
    };

    class manifold
    {
    public:
        std::optional<std::pair<int, int>> start;
        map_2d<diaghram_types> map_2d;

        manifold() : map_2d(), start(std::nullopt) {}

        std::optional<diaghram_types> at(int x, int y) const
        {
            return this->map_2d.at(x, y);
        }

        void emplace(int x, int y, diaghram_types type)
        {
            this->map_2d.emplace(x, y, type);
        }
    };

    void parse_manifold(manifold &manifold, std::string_view manifold_view)
    {
        std::size_t x = 0;
        std::size_t y = 0;

        for (std::size_t char_i = 0; char_i < manifold_view.size(); char_i++)
        {
            char manifold_cell = manifold_view.at(char_i);

            if (manifold_cell == '\n')
            {
                x = 0;
                y++;
            }
            else if (std::expected<diaghram_types, std::string> diaghram_type = parse_diagram_type(manifold_view.at(char_i)); diaghram_type)
            {
                if (*diaghram_type == diaghram_types::start)
                {
                    manifold.start = std::pair<int, int>(x, y);
                }

                manifold.emplace(x, y, *diaghram_type);
                x++;
            }
            else
            {
                throw "Error parsing manifold";
            }
        }
    }

    int count_beam_splits(std::string_view input_view)
    {
        std::unique_ptr<manifold> man = std::make_unique<manifold>();
        parse_manifold(*man, input_view);

        if (std::optional<std::pair<int, int>> start = man->start; start)
        {
            std::vector<tachyon> tachyons{
                tachyon(start->first, start->second),
            };

            int num_splits = 0;
            bool can_move = true;

            std::unordered_set<std::string> tachyon_positions;

            while (can_move)
            {
                can_move = false;

                std::vector<tachyon> new_tachyons{};

                for (tachyon &tach : tachyons)
                {
                    if (!tachyon_positions.contains(std::format("{},{}", tach.x, tach.y)))
                    {
                        if (std::optional<diaghram_types> current_cell = man->at(tach.x, tach.y); current_cell)
                        {
                            if (*current_cell == diaghram_types::empty || *current_cell == diaghram_types::start)
                            {
                                new_tachyons.emplace_back(tachyon(tach.x + 0,
                                                                  tach.y + 1));

                                can_move = true;
                            }
                            else if (*current_cell == diaghram_types::splitter)
                            {
                                tachyon_positions.emplace(std::format("{},{}", tach.x, tach.y));

                                num_splits++;

                                new_tachyons.emplace_back(tachyon(tach.x + 1, tach.y));
                                new_tachyons.emplace_back(tachyon(tach.x - 1, tach.y));
                                can_move = true;
                            }
                        }

                        tachyon_positions.emplace(std::format("{},{}", tach.x, tach.y));
                    }
                }

                // There is probably a better way to merge these
                tachyons.clear();
                for (std::size_t tach_i = 0; tach_i < new_tachyons.size(); tach_i++)
                {
                    // Only add if no conflicts
                    bool has_conflict = false;
                    for (std::size_t prev_tach_i = 0; prev_tach_i < tach_i; prev_tach_i++)
                    {
                        if ((new_tachyons.at(tach_i).x == new_tachyons.at(prev_tach_i).x) &&
                            (new_tachyons.at(tach_i).y == new_tachyons.at(prev_tach_i).y))
                        {
                            has_conflict = true;
                            break;
                        }
                    }

                    if (!has_conflict)
                    {
                        tachyons.emplace_back(tachyon(new_tachyons.at(tach_i).x,
                                                      new_tachyons.at(tach_i).y));
                    }
                }
                new_tachyons.clear();
            }

            return num_splits;
        }
        else
        {
            throw "Failed to find start point";
        }
    }

    long long count_beam_splits_quantum(std::string_view input_view)
    {
        std::unique_ptr<manifold> man = std::make_unique<manifold>();
        parse_manifold(*man, input_view);

        if (std::optional<std::pair<int, int>> start = man->start; start)
        {
            std::stack<tachyon> tachyon_stack{};
            tachyon_stack.emplace(tachyon(start->first, start->second));

            std::unique_ptr<map_2d<long long>> has_solved = std::make_unique<map_2d<long long>>();

            while (!tachyon_stack.empty())
            {
                // Copy value and remove from stack
                tachyon tach = tachyon_stack.top();
                tachyon_stack.pop();

                if (std::optional<long long> position_solution = has_solved->at(tach.x, tach.y); !position_solution)
                {
                    // I have not solved this yet
                    if (std::optional<diaghram_types> current_cell = man->at(tach.x, tach.y); current_cell)
                    {
                        // I can move
                        if (*current_cell == diaghram_types::empty || *current_cell == diaghram_types::start)
                        {
                            int next_x = tach.x;
                            int next_y = tach.y + 1;

                            if (std::optional<long long> next_solution = has_solved->at(next_x, next_y); next_solution)
                            {
                                // I already solved the next position
                                has_solved->emplace(tach.x, tach.y, *next_solution);
                            }
                            else
                            {
                                // I have not solved next
                                // Push back on stack
                                tachyon_stack.emplace(tachyon(tach.x, tach.y));
                                tachyon_stack.emplace(tachyon(next_x, next_y));
                            }
                        }
                        else if (*current_cell == diaghram_types::splitter)
                        {
                            int left_x = tach.x - 1;

                            std::optional<long long> left_solution = has_solved->at(left_x, tach.y);

                            int right_x = tach.x + 1;

                            std::optional<long long> right_solution = has_solved->at(right_x, tach.y);

                            if (left_solution && right_solution) {
                                // has solved both
                                has_solved->emplace(tach.x, tach.y, *left_solution + *right_solution);
                            } else {
                                // At least one is still solving
                                tachyon_stack.emplace(tachyon(tach.x, tach.y));

                                if (!left_solution)
                                {
                                    tachyon_stack.emplace(tachyon(left_x, tach.y));
                                }

                                if (!right_solution)
                                {
                                    tachyon_stack.emplace(tachyon(right_x, tach.y));
                                }
                            }
                        }
                    }
                    else
                    {
                        // Cannot move. Done solving.
                        has_solved->emplace(tach.x, tach.y, 1);
                    }
                }
            }

            return has_solved->at(start->first, start->second).value();
        }
        else
        {
            throw "Failed to find start point";
        }
    }
} // year2025::day07