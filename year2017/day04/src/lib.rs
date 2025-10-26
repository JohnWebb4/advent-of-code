use std::collections::HashSet;

pub fn is_valid(passphrase: &str) -> bool {
    let words = passphrase.split(" ").collect::<Vec<&str>>();
    let mut word_set = HashSet::<&str>::new();

    for word in words {
        if word_set.contains(word) {
            return false;
        }

        word_set.insert(word);
    }

    true
}

pub fn count_valid_passphrases(text: &str) -> usize {
    text.split('\n').map(is_valid).fold(0, |total, is_valid| {
        if is_valid {
            return total + 1;
        }
        total
    })
}

pub fn is_valid_secure(passphrase: &str) -> bool {
    let words = passphrase
        .split(" ")
        .map(|word| {
            let mut sorted_word = word.chars().collect::<Vec<char>>();
            sorted_word.sort();

            return String::from_iter(sorted_word);
        })
        .collect::<Vec<String>>();
    let mut word_set = HashSet::<String>::new();

    for word in words {
        if word_set.contains(&word) {
            return false;
        }

        word_set.insert(word);
    }

    true
}

pub fn count_valid_secure_passphrases(text: &str) -> usize {
    text.split('\n')
        .map(is_valid_secure)
        .fold(0, |total, is_valid| {
            if is_valid {
                return total + 1;
            }
            total
        })
}

#[cfg(test)]
mod tests {
    use std::fs;

    use super::*;

    #[test]
    fn test_count_valid_passphrases() {
        let file = fs::read_to_string("./input.txt").unwrap();
        let input = file.as_str();

        assert_eq!(count_valid_passphrases("aa bb cc dd ee"), 1);
        assert_eq!(count_valid_passphrases("aa bb cc dd aa"), 0);
        assert_eq!(count_valid_passphrases("aa bb cc dd aaa"), 1);
        assert_eq!(count_valid_passphrases(input), 477);

        assert_eq!(count_valid_secure_passphrases("abcde fghij"), 1);
        assert_eq!(count_valid_secure_passphrases("abcde xyz ecdab"), 0);
        assert_eq!(count_valid_secure_passphrases("a ab abc abd abf abj"), 1);
        assert_eq!(
            count_valid_secure_passphrases("iiii oiii ooii oooi oooo"),
            1
        );
        assert_eq!(count_valid_secure_passphrases("oiii ioii iioi iiio"), 0);
        assert_eq!(count_valid_secure_passphrases(input), 0);
    }
}
