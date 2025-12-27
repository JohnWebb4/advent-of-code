#include <algorithm>
#include <stack>
#include <string_view>
#include <unordered_map>
#include <vector>

namespace year2025::day11
{
    const std::string DEVICE_YOU = "you";
    const std::string DEVICE_SVR = "svr";
    const std::string DEVICE_DAC = "dac";
    const std::string DEVICE_FFT = "fft";
    const std::string DEVICE_OUT = "out";

    void parse_device_map(std::unordered_map<std::string, std::vector<std::string>> &device_map, const std::string_view devices_view)
    {
        std::size_t prev_line_index = 0;
        while (prev_line_index != -1)
        {
            std::size_t line_index = devices_view.find('\n', prev_line_index);
            std::string_view device_view = devices_view.substr(prev_line_index, line_index - prev_line_index);

            std::size_t colon_index = device_view.find(':');
            std::string current_device = std::string(device_view.substr(0, colon_index));
            std::string_view output_devices_view = device_view.substr(colon_index + 1 + 1); // There is an extra space after the color

            device_map.emplace(current_device, std::vector<std::string>{});

            std::size_t prev_output_index = 0;
            while (prev_output_index != -1)
            {
                std::size_t output_index = output_devices_view.find(' ', prev_output_index);

                std::string output_device = std::string(output_devices_view.substr(prev_output_index, output_index - prev_output_index));

                device_map.at(current_device).emplace_back(output_device);

                if (output_index == -1)
                {
                    break;
                }
                else
                {
                    prev_output_index = output_index + 1;
                }
            }

            if (line_index == -1)
            {
                break;
            }
            else
            {
                prev_line_index = line_index + 1;
            }
        }
    }

    void parse_paths_map(std::unordered_map<std::string, long long> &num_paths_map, const std::string &start, const std::string &end, const std::unordered_map<std::string, std::vector<std::string>> &device_map)
    {
        std::stack<std::string> solution_stack{};
        solution_stack.push(start);

        num_paths_map.emplace(end, 1);
        if (end != DEVICE_OUT)
        {
            num_paths_map.emplace(end, 0);
        }

        int iteration = 0;
        while (!solution_stack.empty())
        {
            std::string position = solution_stack.top();
            solution_stack.pop();
            iteration++;

            if (!num_paths_map.contains(position))
            {
                if (device_map.contains(position))
                {
                    // Are all children solved
                    const std::vector<std::string> &next_paths = device_map.at(position);
                    bool are_all_solved = std::all_of(next_paths.begin(), next_paths.end(), [&num_paths_map](const std::string &next_node)
                                                      { return num_paths_map.contains(next_node); });
                    if (are_all_solved)
                    {

                        long long num_paths = 0;
                        for (const std::string &next_position : device_map.at(position))
                        {
                            num_paths += num_paths_map.at(next_position);
                        }
                        num_paths_map.emplace(position, num_paths);
                    }
                    else
                    {
                        solution_stack.emplace(position);
                        for (const std::string &next_position : device_map.at(position))
                        {
                            if (!num_paths_map.contains(next_position))
                            {
                                solution_stack.emplace(next_position);
                            }
                        }
                    }
                }
                else
                {
                    num_paths_map.emplace(position, 0);
                }
            }
        }
    }

    long long count_paths_out(const std::string_view input_view)
    {
        std::unordered_map<std::string, std::vector<std::string>> device_map{};
        parse_device_map(device_map, input_view);

        std::unordered_map<std::string, long long> num_paths_out_map{};

        parse_paths_map(num_paths_out_map, DEVICE_YOU, DEVICE_OUT, device_map);

        return num_paths_out_map.at(DEVICE_YOU);
    }

    long long count_problematic_paths_out(const std::string_view input_view)
    {
        std::unordered_map<std::string, std::vector<std::string>> device_map{};
        parse_device_map(device_map, input_view);

        std::unordered_map<std::string, long long> svr_to_dac{};
        parse_paths_map(svr_to_dac, DEVICE_SVR, DEVICE_DAC, device_map);

        std::unordered_map<std::string, long long> dac_to_fft{};
        parse_paths_map(dac_to_fft, DEVICE_DAC, DEVICE_FFT, device_map);

        std::unordered_map<std::string, long long> fft_to_out{};
        parse_paths_map(fft_to_out, DEVICE_FFT, DEVICE_OUT, device_map);

        long long svr_dac_fft_out = svr_to_dac.at(DEVICE_SVR) * dac_to_fft.at(DEVICE_DAC) * fft_to_out.at(DEVICE_FFT);

        std::unordered_map<std::string, long long> svr_to_fft{};
        parse_paths_map(svr_to_fft, DEVICE_SVR, DEVICE_FFT, device_map);

        std::unordered_map<std::string, long long> fft_to_dac{};
        parse_paths_map(fft_to_dac, DEVICE_FFT, DEVICE_DAC, device_map);

        std::unordered_map<std::string, long long> dac_to_out{};
        parse_paths_map(dac_to_out, DEVICE_DAC, DEVICE_OUT, device_map);

        long long svr_fft_dac_out = svr_to_fft.at(DEVICE_SVR) * fft_to_dac.at(DEVICE_FFT) * dac_to_out.at(DEVICE_DAC);

        return svr_dac_fft_out + svr_fft_dac_out;
    }
} // year2025::day11