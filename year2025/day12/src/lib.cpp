#include "lib.h"

#include <memory>
#include <optional>
#include <unordered_set>
#include <vector>

#include <iostream>

namespace year2025::day12
{
    enum class ShapeValues
    {
        SOLID,
        EMPTY
    };

    class Shape
    {
    public:
        const std::unordered_map<std::string, ShapeValues> values;

        const std::size_t width;
        const std::size_t height;

        static std::optional<ShapeValues> parse_shape_values(char c)
        {
            if (c == '#')
            {
                return std::optional<ShapeValues>(ShapeValues::SOLID);
            }
            else if (c == '.')
            {
                return std::optional<ShapeValues>(ShapeValues::EMPTY);
            }
            else
            {
                return std::nullopt;
            }
        }

        Shape(const std::unordered_map<std::string, ShapeValues> &values, std::size_t width, std::size_t height) : values(values), width(width), height(height)
        {
        }
    };

    class Region
    {
    public:
        const std::size_t width;
        const std::size_t height;
        const std::vector<long long> num_presents;

        Region(const std::vector<long long> &num_presents, std::size_t width, std::size_t height) : num_presents(num_presents), width(width), height(height) {}
    };

    class SituationSummary
    {
    public:
        const std::vector<Shape> present_shapes;
        const std::vector<Region> regions;

        SituationSummary(const std::vector<Shape> &present_shapes, const std::vector<Region> &regions) : present_shapes(present_shapes), regions(regions) {}
    };

    std::unique_ptr<SituationSummary> parse_situation_summary(const std::string_view situtation_summary_view)
    {
        std::vector<Shape> present_shapes{};
        std::vector<Region> regions{};

        std::size_t prev_section_index = 0;
        while (prev_section_index != -1)
        {
            std::size_t section_index = situtation_summary_view.find("\n\n", prev_section_index);
            std::string_view section_view = situtation_summary_view.substr(prev_section_index, section_index - prev_section_index);

            int hi = 0;

            if (section_index == -1)
            {
                break;
            }
            else
            {
                prev_section_index = section_index + 2;
            }
        }

        return std::make_unique<SituationSummary>(SituationSummary(present_shapes, regions));
    }

    long long count_regions_can_fit_presents(std::string_view input_view)
    {
        std::unique_ptr<SituationSummary> situation_summary = parse_situation_summary(input_view);

        return 0;
    }
} // year2025::day12