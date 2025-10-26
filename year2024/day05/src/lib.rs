use std::cmp::Ordering;

pub fn order_and_sum_middle_page(page_rules_string: &str) -> i32 {
    let sections = page_rules_string.split("\n\n").collect::<Vec<&str>>();

    let ordering = sections[0]
        .split("\n")
        .map(|order| {
            let parts = order.split("|").collect::<Vec<&str>>();

            (
                parts[0].parse::<i32>().unwrap(),
                parts[1].parse::<i32>().unwrap(),
            )
        })
        .collect::<Vec<(i32, i32)>>();
    let page_updates = sections[1]
        .split("\n")
        .map(|update| {
            update
                .split(",")
                .map(|num| num.parse::<i32>().unwrap())
                .collect::<Vec<i32>>()
        })
        .collect::<Vec<Vec<i32>>>();

    page_updates
        .iter()
        .filter(|update| {
            ordering.iter().all(|(page_before, page_after)| {
                if let Some(index_before) = update.iter().position(|p| p == page_before) {
                    if let Some(index_after) = update.iter().position(|p| p == page_after) {
                        if index_after < index_before {
                            return false;
                        }
                    }
                }

                true
            })
        })
        .fold(0_i32, |sum_middle_page, valid_update| {
            let middle_index = valid_update.len() / 2;

            sum_middle_page + valid_update[middle_index]
        })
}

pub fn fix_and_sum_middle_page(page_rules_string: &str) -> i32 {
    let sections = page_rules_string.split("\n\n").collect::<Vec<&str>>();

    let ordering = sections[0]
        .split("\n")
        .map(|order| {
            let parts = order.split("|").collect::<Vec<&str>>();

            (
                parts[0].parse::<i32>().unwrap(),
                parts[1].parse::<i32>().unwrap(),
            )
        })
        .collect::<Vec<(i32, i32)>>();
    let page_updates = sections[1]
        .split("\n")
        .map(|update| {
            update
                .split(",")
                .map(|num| num.parse::<i32>().unwrap())
                .collect::<Vec<i32>>()
        })
        .collect::<Vec<Vec<i32>>>();

    page_updates
        .iter()
        .filter(|update| {
            ordering.iter().any(|(page_before, page_after)| {
                if let Some(index_before) = update.iter().position(|p| p == page_before) {
                    if let Some(index_after) = update.iter().position(|p| p == page_after) {
                        if index_after < index_before {
                            return true;
                        }
                    }
                }

                false
            })
        })
        .map(|invalid_update| {
            // Sort
            let mut sorted_update = invalid_update.clone();

            sorted_update.sort_by(|a, b| {
                for (page_before, page_after) in ordering.iter() {
                    if a == page_before && b == page_after {
                        return Ordering::Less;
                    } else if a == page_after && b == page_before {
                        return Ordering::Greater;
                    }
                }

                Ordering::Equal
            });

            sorted_update
        })
        .fold(0_i32, |sum_middle_page, invalid_update| {
            let middle_index = invalid_update.len() / 2;

            sum_middle_page + invalid_update[middle_index]
        })
}

#[cfg(test)]
mod tests {
    use std::fs;

    use super::*;

    const TEST_1: &str = "47|53
97|13
97|61
97|47
75|29
61|13
75|53
29|13
97|29
53|29
61|53
97|53
61|29
47|13
75|47
97|75
47|61
75|61
47|29
75|13
53|13

75,47,61,53,29
97,61,53,29,13
75,29,13
75,97,47,61,53
61,13,29
97,13,75,29,47";

    #[test]
    fn test_order_and_sum_middle_page() {
        let file = fs::read_to_string("./input.txt").unwrap();
        let input = file.as_str();

        assert_eq!(order_and_sum_middle_page(TEST_1), 143);
        assert_eq!(order_and_sum_middle_page(input), 5129);

        assert_eq!(fix_and_sum_middle_page(TEST_1), 123);
        assert_eq!(fix_and_sum_middle_page(input), 4077);
    }
}
