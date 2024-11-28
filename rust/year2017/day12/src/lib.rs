use std::collections::{HashSet, LinkedList};

pub fn count_in_group_0(pipe_list: &str) -> usize {
    let pipes = pipe_list.split('\n').collect::<Vec<&str>>();
    let pipe_re = regex::Regex::new(r"(\d+) <-> (.+)").unwrap();

    let pipe_relations = pipes
        .iter()
        .map(|pipe| pipe_re.captures(pipe).unwrap())
        .map(|pipe_capture| {
            (
                pipe_capture[1].to_string(),
                pipe_capture[2]
                    .to_string()
                    .split(", ")
                    .map(|s| s.to_string())
                    .collect::<Vec<String>>(),
            )
        })
        .collect::<Vec<(String, Vec<String>)>>();

    let mut pipe_set = HashSet::<String>::new();
    pipe_set.insert("0".to_string());

    let mut has_changes = true;
    while has_changes {
        let prev_set_count = pipe_set.len();

        pipe_relations.iter().for_each(|(program, relations)| {
            if pipe_set.contains(program) {
                relations.iter().for_each(|relation| {
                    pipe_set.insert(relation.clone());
                });
            }

            if relations.iter().any(|relation| pipe_set.contains(relation)) {
                pipe_set.insert(program.clone());
            }
        });

        has_changes = prev_set_count != pipe_set.len();
    }

    pipe_set.len()
}

pub fn count_groups(pipe_list: &str) -> usize {
    let pipes = pipe_list.split('\n').collect::<Vec<&str>>();
    let pipe_re = regex::Regex::new(r"(\d+) <-> (.+)").unwrap();

    let pipe_relations = pipes
        .iter()
        .map(|pipe| pipe_re.captures(pipe).unwrap())
        .map(|pipe_capture| {
            (
                pipe_capture[1].to_string(),
                pipe_capture[2]
                    .to_string()
                    .split(", ")
                    .map(|s| s.to_string())
                    .collect::<Vec<String>>(),
            )
        })
        .collect::<Vec<(String, Vec<String>)>>();

    let mut program_list = pipe_relations
        .iter()
        .fold(HashSet::<String>::new(), |mut set, (program, relations)| {
            set.insert(program.clone());

            for relation in relations {
                set.insert(relation.clone());
            }

            set
        })
        .iter()
        .fold(LinkedList::<String>::new(), |mut list, program| {
            list.push_back(program.to_string());

            list
        });

    let mut group_count = 0;
    while !program_list.is_empty() {
        let program = program_list.pop_front().unwrap();

        let mut pipe_set = HashSet::<String>::new();
        pipe_set.insert(program.to_string());

        let mut has_changes = true;
        while has_changes {
            let prev_set_count = pipe_set.len();

            pipe_relations.iter().for_each(|(program, relations)| {
                if pipe_set.contains(program) {
                    relations.iter().for_each(|relation| {
                        pipe_set.insert(relation.clone());
                    });
                }

                if relations.iter().any(|relation| pipe_set.contains(relation)) {
                    pipe_set.insert(program.clone());
                }
            });

            has_changes = prev_set_count != pipe_set.len();
        }

        program_list = program_list
            .iter()
            .filter(|program| !pipe_set.contains(*program))
            .fold(LinkedList::new(), |mut list, p| {
                list.push_back(p.clone());
                list
            });

        group_count += 1;
    }

    group_count
}

#[cfg(test)]
mod tests {
    use std::fs;

    use super::*;

    const TEST_1: &str = "0 <-> 2
1 <-> 1
2 <-> 0, 3, 4
3 <-> 2, 4
4 <-> 2, 3, 6
5 <-> 6
6 <-> 4, 5";

    #[test]
    fn test_count_in_group_0() {
        let file = fs::read_to_string("./input.txt").unwrap();
        let input = file.as_str();

        assert_eq!(count_in_group_0(TEST_1), 6);
        assert_eq!(count_in_group_0(input), 130);

        assert_eq!(count_groups(TEST_1), 2);
        assert_eq!(count_groups(input), 189);
    }
}
