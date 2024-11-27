pub fn get_group_score(input: &str) -> u32 {
    let input_chars = input.chars().collect::<Vec<char>>();
    let mut score = 0;
    let mut depth: u32 = 0;
    let mut is_garbage = false;
    let mut i = 0;

    while i < input.len() {
        let c = input_chars[i];

        if c == '!' {
            i += 2;
            continue;
        } else if c == '>' {
            is_garbage = false;
        } else if c == '<' {
            is_garbage = true;
        } else if !is_garbage && c == '{' {
            depth += 1
        } else if !is_garbage && c == '}' {
            score += depth;
            depth -= 1;
        }

        i += 1;
    }

    score
}

pub fn count_garbage(input: &str) -> u32 {
    let input_chars = input.chars().collect::<Vec<char>>();
    let mut garbage_count = 0;
    let mut is_garbage = false;
    let mut i = 0;

    while i < input.len() {
        let c = input_chars[i];

        if c == '!' {
            i += 2;
            continue;
        } else if is_garbage && c == '>' {
            is_garbage = false;
        } else if !is_garbage && c == '<' {
            is_garbage = true;
        } else if is_garbage {
            garbage_count += 1;
        }

        i += 1;
    }

    garbage_count
}

#[cfg(test)]
mod tests {
    use std::fs;

    use super::*;

    #[test]
    fn test_get_group_score() {
        let file = fs::read_to_string("./input.txt").unwrap();
        let input = file.as_str();

        assert_eq!(get_group_score("{}"), 1);
        assert_eq!(get_group_score("{{{}}}"), 6);
        assert_eq!(get_group_score("{{},{}}"), 5);
        assert_eq!(get_group_score("{{{},{},{{}}}}"), 16);
        assert_eq!(get_group_score("{<a>,<a>,<a>,<a>}"), 1);
        assert_eq!(get_group_score("{{<ab>},{<ab>},{<ab>},{<ab>}}"), 9);
        assert_eq!(get_group_score("{{<!!>},{<!!>},{<!!>},{<!!>}}"), 9);
        assert_eq!(get_group_score("{{<a!>},{<a!>},{<a!>},{<ab>}}"), 3);
        assert_eq!(get_group_score(input), 11846);

        assert_eq!(count_garbage("<>"), 0);
        assert_eq!(count_garbage("<random characters>"), 17);
        assert_eq!(count_garbage("<<<<>"), 3);
        assert_eq!(count_garbage("<{!>}>"), 2);
        assert_eq!(count_garbage("<!!>"), 0);
        assert_eq!(count_garbage("<!!!>>"), 0);
        assert_eq!(count_garbage("<{o\"i!a,<{i<a>"), 10);
        assert_eq!(count_garbage(input), 6285);
    }
}
