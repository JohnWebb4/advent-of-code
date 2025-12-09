#include "lib.h"

#include <charconv>
#include <optional>
#include <format>
#include <limits>
#include <memory>
#include <string_view>
#include <unordered_map>
#include <unordered_set>
#include <vector>

#include <iostream>
#include <queue>

namespace year2025::day09
{
  class Point
  {
  public:
    int x;
    int y;

    Point(int x, int y) : x(x), y(y) {}
  };

  class Edge
  {
  public:
    int x_1;
    int x_2;
    int y_1;
    int y_2;

    Edge(int x_1, int x_2, int y_1, int y_2) : x_1(x_1), x_2(x_2), y_1(y_1), y_2(y_2) {}

    bool is_horizontal() const
    {
      return this->y_1 == this->y_2;
    }

    bool is_vertical() const
    {
      return this->x_1 == this->x_2;
    }

    bool contains(int x, int y) const
    {
      int x_min{std::min(this->x_1, this->x_2)};
      int x_max{std::max(this->x_1, this->x_2)};

      int y_min{std::min(this->y_1, this->y_2)};
      int y_max{std::max(this->y_1, this->y_2)};

      return x >= x_min && x <= x_max && y >= y_min && y <= y_max;
    }
  };

  class Rectangle
  {
  public:
    int x_min;
    int x_max;
    int y_min;
    int y_max;

    Rectangle(int x_min, int x_max, int y_min, int y_max) : x_min(x_min), x_max(x_max), y_min(y_min), y_max(y_max) {}

    long long get_area() const
    {
      return (x_max - x_min + 1) * (y_max - y_min + 1);
    }
  };

  bool does_rectangle_contain_edge(const Rectangle &rect, const Edge &edge)
  {
    int edge_x_min = std::min(edge.x_1, edge.x_2);
    int edge_x_max = std::max(edge.x_1, edge.x_2);

    int edge_y_min = std::min(edge.y_1, edge.y_2);
    int edge_y_max = std::max(edge.y_1, edge.y_2);

    // If vertical line
    if (edge_y_min == edge_y_max && edge_y_min == rect.y_min)
    {
      if (edge_x_min > rect.x_min && edge_x_min < rect.x_max)
      {
        return true;
      }

      if (edge_x_max > rect.x_min && edge_x_max < rect.x_max)
      {
        return true;
      }
    }

    if (edge_y_min == edge_y_max && edge_y_min == rect.y_max)
    {
      if (edge_x_min > rect.x_min && edge_x_min < rect.x_max)
      {
        return true;
      }

      if (edge_x_max > rect.x_min && edge_x_max < rect.x_max)
      {
        return true;
      }
    }

    return (
               edge.x_1 > rect.x_min && edge.x_1 < rect.x_max && edge.y_1 > rect.y_min && edge.y_1 < rect.y_max) ||
           (edge.x_2 > rect.x_min && edge.x_2 < rect.x_max && edge.y_2 > rect.y_min && edge.y_2 < rect.y_max);
  }

  template <typename T>
  class Map2d
  {
  public:
    std::unordered_map<std::string, T> inner_map;

    Map2d() : inner_map() {}

    static std::string get_key(int x, int y)
    {
      return std::format("{},{}", x, y);
    }

    bool contains(int x, int y) const
    {
      return this->inner_map.contains(Map2d::get_key(x, y));
    }

    const T &at(int x, int y) const
    {
      return this->inner_map.at(Map2d::get_key(x, y));
    }

    void emplace(int x, int y, T value)
    {
      this->inner_map.emplace(Map2d::get_key(x, y), value);
    }

    std::size_t size() const
    {
      return this->inner_map.size();
    }
  };

  std::optional<Point>
  parse_point(std::string_view point_view)
  {
    int x;
    int y;

    std::size_t comma_index = point_view.find(',');

    if (auto [pc, ec] = std::from_chars(point_view.data(), point_view.data() + comma_index, x); ec == std::errc::invalid_argument)
    {
      return std::nullopt;
    }

    if (auto [pc, ec] = std::from_chars(point_view.data() + comma_index + 1, point_view.data() + point_view.size(), y); ec == std::errc::invalid_argument)
    {
      return std::nullopt;
    }

    return Point(x, y);
  }

  void parse_points(std::vector<Point> &points, std::string_view points_view)
  {
    std::size_t prev_line_index = 0;
    for (std::size_t line_index = points_view.find('\n'); line_index != -1; line_index = points_view.find('\n', line_index + 1))
    {
      if (std::optional<Point> point_exp = parse_point(points_view.substr(prev_line_index, line_index - prev_line_index)); point_exp)
      {
        points.emplace_back(*point_exp);
      }

      prev_line_index = line_index + 1;
    }

    if (std::optional<Point> point_exp = parse_point(points_view.substr(prev_line_index)); point_exp)
    {
      points.emplace_back(*point_exp);
    }
  }

  long long get_area_of_largest_rectangle(std::string_view input_view)
  {
    std::vector<Point> points;

    parse_points(points, input_view);

    long long rectangle_max_area = 0;

    for (std::size_t point_i = 0; point_i < points.size(); point_i++)
    {
      for (std::size_t point_j = point_i + 1; point_j < points.size(); point_j++)
      {
        long long rectangle_area = (std::abs(long(points[point_i].x) -
                                             long(points[point_j].x)) +
                                    1l) *
                                   (std::abs(long(points[point_i].y) -
                                             long(points[point_j].y)) +
                                    1l);

        if (rectangle_area > rectangle_max_area)
        {
          rectangle_max_area = rectangle_area;
        }
      }
    }

    return rectangle_max_area;
  }

  bool is_point_in_polygon(const std::vector<Edge> &edges, int point_x, int point_y)
  {
    int winding_number = 0;

    for (const Edge &edge : edges)
    {
      if (edge.contains(point_x, point_y))
      {
        return true;
      }

      if (edge.is_vertical() && edge.x_1 <= point_x)
      {
        int y_min = std::min(edge.y_1, edge.y_2);
        int y_max = std::max(edge.y_1, edge.y_2);

        if (point_y >= y_min && point_y <= y_max)
        {
          if (winding_number == 0 && edge.y_1 >= edge.y_2)
          {
            winding_number = 1;
          }
          else if (winding_number == 1 && edge.y_1 <= edge.y_2)
          {
            winding_number = 0;
          }
        }
      }
    }

    return winding_number != 0;
  }

  long long get_area_of_largest_rectangle_red_green(std::string_view input_view)
  {
    std::vector<Point> points;

    parse_points(points, input_view);

    std::vector<std::vector<std::size_t>> corners;

    for (std::size_t point_i = 0; point_i < points.size(); point_i++)
    {
      for (std::size_t point_j = 0; point_j < points.size(); point_j++)
      {
        if (point_i != point_j)
        {
          if ((points[point_i].x == points[point_j].x) || (points[point_i].y == points[point_j].y))
          {
            if (corners.size() > point_i)
            {
              corners[point_i].emplace_back(point_j);
            }
            else
            {
              corners.emplace_back(std::vector<std::size_t>{point_j});
            }
          }
        }
      }
    }

    std::unordered_set<std::size_t> has_visited_point{};
    std::vector<Edge> edges;
    for (std::size_t point_i = 0; has_visited_point.size() < points.size();)
    {
      std::size_t next_point_i{};
      if (has_visited_point.contains(corners[point_i][0]) && has_visited_point.contains(corners[point_i][1]))
      {
        next_point_i = 0;
      }
      else if (!has_visited_point.contains(corners[point_i][0]))
      {
        next_point_i = corners[point_i][0];
      }
      else if (!has_visited_point.contains(corners[point_i][1]))
      {
        next_point_i = corners[point_i][1];
      }
      edges.emplace_back(Edge(points[point_i].x, points[next_point_i].x, points[point_i].y, points[next_point_i].y));

      has_visited_point.emplace(point_i);
      point_i = next_point_i;
    }

    struct SortBySize
    {
      bool operator()(const Rectangle l, const Rectangle r) const { return l.get_area() < r.get_area(); }
    };

    std::priority_queue<Rectangle, std::vector<Rectangle>, SortBySize> rectangles{};

    for (std::size_t point_i = 0; point_i < points.size(); point_i++)
    {

      for (std::size_t point_j = point_i + 1; point_j < points.size(); point_j++)
      {
        int x_min = std::min(points[point_i].x, points[point_j].x);
        int x_max = std::max(points[point_i].x, points[point_j].x);

        int y_min = std::min(points[point_i].y, points[point_j].y);
        int y_max = std::max(points[point_i].y, points[point_j].y);

        rectangles.emplace(Rectangle(x_min, x_max, y_min, y_max));
      }
    }

    Map2d<bool> point_valid_map{};

    long long max_area = 0;
    while (rectangles.size() > 0)
    {
      const Rectangle &rect = rectangles.top();

      std::cout << "Rect " << rectangles.size() << " => " << rect.get_area() << std::endl;

      bool contains_a_corner = false;

      for (Point &point : points)
      {
        if (point.x > rect.x_min && point.x < rect.x_max && point.y > rect.y_min && point.y < rect.y_max)
        {
          std::cout << "point conflict" << std::endl;
          contains_a_corner = true;
          break;
        }
      }

      if (!contains_a_corner)
      {
        bool contains_an_edge = false;
        for (Edge &edge : edges)
        {
          if ((edge.x_1 == edge.x_2) && (edge.x_1 > rect.x_min && edge.x_1 < rect.x_max))
          {
            // Vertical line
            if (std::min(edge.y_1, edge.y_2) < rect.y_min && std::max(edge.y_1, edge.y_2) > rect.y_max)
            {
              std::cout << "edge vert conflict" << std::endl;
              contains_an_edge = true;
              break;
            }
          }

          if ((edge.y_1 == edge.y_2) && (edge.y_1 > rect.y_min && edge.y_1 < rect.y_max))
          {
            // Horizontal line
            if (std::min(edge.x_1, edge.x_2) < rect.x_min && std::max(edge.x_1, edge.x_2) > rect.x_max)
            {
              std::cout << "edge hori conflict" << std::endl;
              contains_an_edge = true;
              break;
            }
          }
        }

        if (!contains_an_edge)
        {
          bool is_every_point_in_polygon = true;
          for (int x = rect.x_min + 1; (x < rect.x_max) && is_every_point_in_polygon; x++)
          {
            for (int y = rect.y_min + 1; (y < rect.y_max) && is_every_point_in_polygon; y++)
            {
              if (point_valid_map.contains(x, y))
              {
                is_every_point_in_polygon = is_every_point_in_polygon && point_valid_map.at(x, y);
              }
              else
              {
                bool is_point_valid = is_point_in_polygon(edges, x, y);

                point_valid_map.emplace(x, y, is_point_valid);
                is_every_point_in_polygon = is_every_point_in_polygon && is_point_valid;
              }
            }
          }

          if (is_every_point_in_polygon && rect.get_area() > max_area)
          {
            return rect.get_area();
          }
          else
          {
            std::cout << "winding number conflict" << std::endl;
          }
        }
      }

      rectangles.pop();
    }

    return 0;
  }
} // year2025::day09
