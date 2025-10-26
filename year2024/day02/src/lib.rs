pub fn count_safe_reports(report_string: &str) -> usize {
    report_string
        .split('\n')
        .map(|report| {
            report
                .split(' ')
                .map(|level_string| level_string.parse::<usize>().unwrap())
                .collect::<Vec<usize>>()
        })
        .fold(0, |count_safe, report| {
            if is_safe(&report) {
                return count_safe + 1;
            }

            count_safe
        })
}

pub fn count_safe_reports_with_dampener(report_string: &str) -> usize {
    report_string
        .split('\n')
        .map(|report| {
            report
                .split(' ')
                .map(|level_string| level_string.parse::<usize>().unwrap())
                .collect::<Vec<usize>>()
        })
        .fold(0, |count_safe, report| {
            if get_all_dampened_possibilities(report).iter().any(is_safe) {
                return count_safe + 1;
            }

            count_safe
        })
}

fn is_safe(report: &Vec<usize>) -> bool {
    // If decreasing by less than 3
    if report
        .iter()
        .enumerate()
        .skip(1)
        .all(|(i, level)| level < &report[i - 1] && report[i - 1] - level <= 3)
    {
        return true;
    }

    // If increasing by less than 3
    if report
        .iter()
        .enumerate()
        .skip(1)
        .all(|(i, level)| level > &report[i - 1] && level - report[i - 1] <= 3)
    {
        return true;
    }

    false
}

fn get_all_dampened_possibilities(report: Vec<usize>) -> Vec<Vec<usize>> {
    report
        .iter()
        .enumerate()
        .map(|(i, level)| {
            let mut possible_report = report.clone();
            possible_report.remove(i);

            possible_report
        })
        .collect::<Vec<Vec<usize>>>()
}

#[cfg(test)]
mod tests {
    use std::fs;

    use super::*;

    const TEST_1: &str = "7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9";

    #[test]
    fn it_works() {
        let file = fs::read_to_string("./input.txt").unwrap();
        let input = file.as_str();

        assert_eq!(count_safe_reports(TEST_1), 2);
        assert_eq!(count_safe_reports(input), 432);

        assert_eq!(count_safe_reports_with_dampener(TEST_1), 4);
        assert_eq!(count_safe_reports_with_dampener(input), 488);
    }
}
