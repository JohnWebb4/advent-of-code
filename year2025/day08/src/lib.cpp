#include "lib.h"

#include <algorithm>
#include <cmath>
#include <charconv>
#include <format>
#include <optional>
#include <queue>
#include <unordered_set>
#include <vector>

namespace year2025::day08
{
  class JunctionBox
  {
  public:
    int x;
    int y;
    int z;

    JunctionBox(int x_i, int y_i, int z_i) : x(x_i), y(y_i), z(z_i) {}

    inline double get_distance_to(const JunctionBox &another_box) const
    {
      double diff_x = double(this->x) - double(another_box.x);
      double diff_y = double(this->y) - double(another_box.y);
      double diff_z = double(this->z) - double(another_box.z);

      return std::sqrt((diff_x * diff_x) + (diff_y * diff_y) + (diff_z * diff_z));
    }

    inline const std::string to_string() const
    {
      return std::format("{},{},{}", this->x, this->y, this->z);
    }
  };

  std::optional<JunctionBox> parse_junction_box(std::string_view junction_box_view)
  {
    std::size_t first_comma = junction_box_view.find(',');
    std::size_t second_comma = junction_box_view.find(',', first_comma + 1);

    const char *view_ptr = junction_box_view.data();

    int x;
    if (auto [pc, ec] = std::from_chars(view_ptr, view_ptr + first_comma, x); ec == std::errc::invalid_argument)
    {
      return std::nullopt;
    }

    int y;
    if (auto [pc, ec] = std::from_chars(view_ptr + first_comma + 1, view_ptr + second_comma, y); ec == std::errc::invalid_argument)
    {
      return std::nullopt;
    }

    int z;
    if (auto [pc, ec] = std::from_chars(view_ptr + second_comma + 1, view_ptr + junction_box_view.size(), z); ec == std::errc::invalid_argument)
    {
      return std::nullopt;
    }

    return JunctionBox(x, y, z);
  }

  void parse_junction_boxes(std::vector<JunctionBox> &junction_boxes, std::string_view junction_boxes_view)
  {
    std::size_t comma_position = 0;
    for (std::size_t next_position = junction_boxes_view.find('\n', comma_position); next_position != -1; next_position = junction_boxes_view.find('\n', comma_position))
    {
      if (std::optional<JunctionBox> junction_box = parse_junction_box(junction_boxes_view.substr(comma_position, next_position - comma_position)); junction_box)
      {
        junction_boxes.emplace_back(*junction_box);
      }

      comma_position = next_position + 1;
    }

    if (std::optional<JunctionBox> junction_box = parse_junction_box(junction_boxes_view.substr(comma_position, junction_boxes_view.size() - comma_position)))
    {
      junction_boxes.emplace_back(*junction_box);
    }
  }

  class JunctionBoxDistanceQueue
  {
  public:
    std::size_t box_1;
    std::size_t box_2;
    double distance;

    JunctionBoxDistanceQueue(std::size_t box_1, std::size_t box_2, int distance) : box_1(box_1), box_2(box_2), distance(distance) {}
  };

  class SortByDistance
  {
  public:
    inline bool operator()(const JunctionBoxDistanceQueue &box_1, const JunctionBoxDistanceQueue &box_2) const
    {
      return box_1.distance > box_2.distance;
    }
  };

  long long product_of_three_largest_circuits(std::string_view input_view, std::size_t num_connections)
  {
    std::vector<JunctionBox> boxes;

    parse_junction_boxes(boxes, input_view);

    std::priority_queue<JunctionBoxDistanceQueue, std::vector<JunctionBoxDistanceQueue>, SortByDistance> distances;

    for (std::size_t box_i = 0; box_i < boxes.size(); box_i++)
    {
      for (std::size_t box_j = box_i + 1; box_j < boxes.size(); box_j++)
      {
        double distance = boxes.at(box_i).get_distance_to(boxes.at(box_j));

        distances.emplace(JunctionBoxDistanceQueue(box_i, box_j, distance));
      }
    }

    std::vector<std::vector<std::size_t>> groups{};

    for (std::size_t connection_i = 0; connection_i < num_connections; connection_i++)
    {
      const JunctionBoxDistanceQueue distance_queue = distances.top();

      // Check for existing connections
      std::vector<std::vector<std::size_t>>::iterator group_1 = std::find_if(groups.begin(), groups.end(), [&distance_queue](const std::vector<std::size_t> &yo)
                                                                             { return std::find(yo.begin(), yo.end(), distance_queue.box_1) != yo.end(); });

      std::vector<std::vector<std::size_t>>::iterator group_2 = std::find_if(groups.begin(), groups.end(), [&distance_queue](const std::vector<std::size_t> &yo)
                                                                             { return std::find(yo.begin(), yo.end(), distance_queue.box_2) != yo.end(); });

      if (group_1 != groups.end() && group_1 == group_2)
      {
        // No op
      }
      else if (group_1 != groups.end() && group_2 != groups.end())
      {
        // Need to merge
        for (std::size_t box : *group_2)
        {
          group_1->emplace_back(box);
        }
        group_2->clear();
      }
      else if (group_1 != groups.end())
      {
        group_1->emplace_back(distance_queue.box_2);
      }
      else if (group_2 != groups.end())
      {
        group_2->emplace_back(distance_queue.box_1);
      }
      else
      {
        groups.emplace_back(std::vector<std::size_t>{distance_queue.box_1, distance_queue.box_2});
      }

      distances.pop();
    }

    std::sort(groups.begin(), groups.end(), [](const std::vector<std::size_t> &group_1, const std::vector<std::size_t> &group_2)
              { return group_1.size() > group_2.size(); });

    return groups[0].size() * groups[1].size() * groups[2].size();
  }

  long long get_distance_of_entire_circuit_from_wall(std::string_view input_view)
  {
    std::vector<JunctionBox> boxes;

    parse_junction_boxes(boxes, input_view);

    std::priority_queue<JunctionBoxDistanceQueue, std::vector<JunctionBoxDistanceQueue>, SortByDistance> distances;

    for (std::size_t box_i = 0; box_i < boxes.size(); box_i++)
    {
      for (std::size_t box_j = box_i + 1; box_j < boxes.size(); box_j++)
      {
        double distance = boxes.at(box_i).get_distance_to(boxes.at(box_j));

        distances.emplace(JunctionBoxDistanceQueue(box_i, box_j, distance));
      }
    }

    std::unordered_set<std::size_t> boxes_merged_set;
    std::vector<std::vector<std::size_t>>
        groups{};

    while (!distances.empty())
    {
      const JunctionBoxDistanceQueue distance_queue = distances.top();

      // Check for existing connections
      std::vector<std::vector<std::size_t>>::iterator group_1 = std::find_if(groups.begin(), groups.end(), [&distance_queue](const std::vector<std::size_t> &yo)
                                                                             { return std::find(yo.begin(), yo.end(), distance_queue.box_1) != yo.end(); });

      std::vector<std::vector<std::size_t>>::iterator group_2 = std::find_if(groups.begin(), groups.end(), [&distance_queue](const std::vector<std::size_t> &yo)
                                                                             { return std::find(yo.begin(), yo.end(), distance_queue.box_2) != yo.end(); });

      if (group_1 != groups.end() && group_1 == group_2)
      {
        // No op
      }
      else if (group_1 != groups.end() && group_2 != groups.end())
      {
        // Need to merge
        for (std::size_t box : *group_2)
        {
          group_1->emplace_back(box);
        }
        group_2->clear();
        groups.erase(group_2);
      }
      else if (group_1 != groups.end())
      {
        group_1->emplace_back(distance_queue.box_2);
      }
      else if (group_2 != groups.end())
      {
        group_2->emplace_back(distance_queue.box_1);
      }
      else
      {
        groups.emplace_back(std::vector<std::size_t>{distance_queue.box_1, distance_queue.box_2});
      }

      boxes_merged_set.emplace(distance_queue.box_1);
      boxes_merged_set.emplace(distance_queue.box_2);

      if (boxes_merged_set.size() == boxes.size())
      {
        return (long long)(boxes[distance_queue.box_1].x) * (long long)(boxes[distance_queue.box_2].x);
      }

      distances.pop();
    }

    return -1;
  }
} // year20xx::dayxx