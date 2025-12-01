#include <string>
#include <vector>

namespace year2017::day21 {
    class Rule {
    public:
        std::string input;
        std::string output;

        Rule(std::string input, std::string output);

        static void add_rule(std::vector<day21::Rule>& rules, std::string& rule_string);
    };

    static const std::string input_program = ".#.\n..#\n###";

    int count_pixels_after_x(std::string& input_string);

    std::string enhance(std::vector<Rule> &rules, std::string &image);

    void split_3_by_3(std::vector<std::string> &results, std::string &image);
    void split_2_by_2(std::vector<std::string> &results, std::string &image);
    std::string rotate(std::string &fractal);
    std::string flip(std::string &fractal);
    int count_pixels(std::string &image);
} // year2017::day21