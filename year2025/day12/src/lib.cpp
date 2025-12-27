#include "lib.h"

#include <format>
#include <memory>
#include <optional>
#include <unordered_set>
#include <vector>

namespace year2025::day12
{
    enum class ShapeValues
    {
        SOLID,
        EMPTY
    };

    class Shape
    {
    private:
        long long _num_horizontal_lines;
        long long _num_vertical_lines;

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

        static const std::string get_key(std::size_t x, std::size_t y)
        {
            return std::format("{},{}", x, y);
        }

        Shape(const std::unordered_map<std::string, ShapeValues> &values, std::size_t width, std::size_t height, long long surface_area) : values(values), width(width), height(height)
        {
            // Count vertical lines
            this->_num_vertical_lines = 0;
            for (std::size_t x = 0; x < width; x++)
            {
                bool is_vertical_line = true;
                for (std::size_t y = 0; y < height; y++)
                {
                    if (!values.contains(Shape::get_key(x, y)))
                    {
                        is_vertical_line = false;
                        break;
                    }
                }

                if (is_vertical_line)
                {
                    this->_num_vertical_lines++;
                }
            }

            // Count horizontal lines
            this->_num_horizontal_lines = 0;
            for (std::size_t y = 0; y < height; y++)
            {
                bool is_horizontal_line = true;
                for (std::size_t x = 0; x < width; x++)
                {
                    if (!values.contains(Shape::get_key(x, y)))
                    {
                        is_horizontal_line = false;
                        break;
                    }
                }

                if (is_horizontal_line)
                {
                    this->_num_horizontal_lines++;
                }
            }
        }

        const ShapeValues &at(std::size_t x, std::size_t y) const
        {
            return this->values.at(Shape::get_key(x, y));
        }

        const long long num_horizontal_lines() const
        {
            return this->_num_horizontal_lines;
        }

        const long long num_vertical_lines() const
        {
            return this->_num_vertical_lines;
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

    Shape parse_shape(const std::string_view shape_view)
    {
        std::unordered_map<std::string, ShapeValues> values;
        std::size_t width{0};

        std::size_t prev_line_index = 0;
        std::size_t line_i = 0;
        long long surface_area = 0;
        while (prev_line_index != -1)
        {
            std::size_t line_index = shape_view.find('\n', prev_line_index);
            std::string_view line = shape_view.substr(prev_line_index, line_index - prev_line_index);

            // Skip first line
            if (line_i != 0)
            {
                for (std::size_t char_i = 0; char_i < line.size(); char_i++)
                {
                    if (std::optional<ShapeValues> value = Shape::parse_shape_values(line[char_i]); value)
                    {
                        if (*value == ShapeValues::SOLID)
                        {
                            values.emplace(Shape::get_key(char_i, line_i - 1), *value);
                        }
                    }
                }
                width = std::max(width, line.size());
            }

            if (line_index == -1)
            {
                break;
            }
            else
            {
                prev_line_index = line_index + 1;
                line_i++;
            }
        }

        return Shape(values, width, line_i, surface_area);
    }

    const static std::string REGION_PART_DELIM = ": ";
    const static std::string DIMENSIONS_PART_DELIM = "x";
    Region parse_region(const std::string_view region_view)
    {
        std::size_t part_delim_index = region_view.find(REGION_PART_DELIM);
        std::string_view dimensions_view = region_view.substr(0, part_delim_index);
        std::size_t dimension_delim_index = dimensions_view.find(DIMENSIONS_PART_DELIM);
        std::size_t width = std::atoi(static_cast<std::string>(dimensions_view.substr(0, dimension_delim_index)).c_str());
        std::size_t height = std::atoi(static_cast<std::string>(dimensions_view.substr(dimension_delim_index + DIMENSIONS_PART_DELIM.size())).c_str());

        std::vector<long long> num_presents;
        std::string_view num_presents_view = region_view.substr(part_delim_index + REGION_PART_DELIM.size());
        std::size_t prev_present_index = 0;
        while (prev_present_index != -1)
        {
            std::size_t present_index = num_presents_view.find(' ', prev_present_index);
            std::string_view present_view = num_presents_view.substr(prev_present_index, present_index - prev_present_index);

            num_presents.emplace_back(std::atoi(static_cast<std::string>(present_view).c_str()));

            if (present_index == -1)
            {
                break;
            }
            else
            {
                prev_present_index = present_index + 1;
            }
        }

        return Region(num_presents, width, height);
    }

    std::vector<Region> parse_regions(const std::string_view regions_view)
    {
        std::vector<Region> regions;

        std::size_t prev_line_index = 0;
        while (prev_line_index != -1)
        {
            std::size_t line_index = regions_view.find('\n', prev_line_index);
            std::string_view region_view = regions_view.substr(prev_line_index, line_index - prev_line_index);

            regions.emplace_back(parse_region(region_view));

            if (line_index == -1)
            {
                break;
            }
            else
            {
                prev_line_index = line_index + 1;
            }
        }

        return regions;
    }

    SituationSummary parse_situation_summary(const std::string_view situtation_summary_view)
    {
        std::vector<Shape> present_shapes{};
        std::vector<Region> regions{};

        std::size_t prev_section_index = 0;
        while (prev_section_index != -1)
        {
            std::size_t section_index = situtation_summary_view.find("\n\n", prev_section_index);
            std::string_view section_view = situtation_summary_view.substr(prev_section_index, section_index - prev_section_index);

            if (section_index == -1)
            {
                regions = parse_regions(section_view);
                break;
            }
            else
            {
                present_shapes.emplace_back(parse_shape(section_view));

                prev_section_index = section_index + 2;
            }
        }

        return SituationSummary(present_shapes, regions);
    }

    long long count_regions_can_fit_presents(std::string_view input_view)
    {
        std::unique_ptr<SituationSummary> situation_summary = std::make_unique<SituationSummary>(parse_situation_summary(input_view));

        long long num_regions_can_fit{0};

        for (const Region &region : situation_summary->regions)
        {
            // Simple area test. If tests take up more area than avaialable then stop.
            long long region_area = region.width * region.height;

            long long total_present_area{0};
            for (std::size_t num_present_i = 0; num_present_i < region.num_presents.size(); num_present_i++)
            {
                total_present_area += region.num_presents.at(num_present_i) * situation_summary->present_shapes.at(num_present_i).values.size();
            }

            if (total_present_area < region_area)
            {
                long long num_presents_width = std::floor(static_cast<float>(region.width) / 3);
                long long num_presents_height = std::floor(static_cast<float>(region.height) / 3);

                long long num_presents_possible = num_presents_width * num_presents_height;

                long long total_presents{0};
                for (const long long &num_present : region.num_presents)
                {
                    total_presents += num_present;
                }

                if (num_presents_possible >= total_presents)
                {
                    num_regions_can_fit++;
                }
            }
        }

        return num_regions_can_fit;
    }
} // year2025::day12