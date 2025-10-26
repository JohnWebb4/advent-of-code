mod hand;

use std::collections::{HashMap, HashSet};

use hand::{new_hand, Hand};
use queues::{IsQueue, Queue};

#[derive(Debug)]
struct HandValue {
    value: i32,
    bid_amount: i32,
}

pub fn get_total_winnings(input: &str) -> Option<i32> {
    let mut hand_value = read_hands(input)
        .iter()
        .map(get_hand_value)
        .collect::<Vec<HandValue>>();

    hand_value.sort_by(|hand_value_a, hand_value_b| {
        hand_value_a.value.partial_cmp(&hand_value_b.value).unwrap()
    });

    hand_value
        .iter()
        .map(|hand| hand.bid_amount)
        .enumerate()
        .reduce(|(_, total_winnings), (i, bid_amount)| {
            (0, total_winnings + (i as i32 + 1) * bid_amount)
        })
        .map(|(_, total_winnings)| total_winnings)
}

pub fn get_total_winnings_wild(input: &str) -> Option<i32> {
    let mut hand_value = read_hands_wild(input)
        .iter()
        .map(get_hand_value_wild)
        .collect::<Vec<HandValue>>();

    hand_value.sort_by(|hand_value_a, hand_value_b| {
        hand_value_a.value.partial_cmp(&hand_value_b.value).unwrap()
    });

    hand_value
        .iter()
        .map(|hand| hand.bid_amount)
        .enumerate()
        .reduce(|(_, total_winnings), (i, bid_amount)| {
            (0, total_winnings + (i as i32 + 1) * bid_amount)
        })
        .map(|(_, total_winnings)| total_winnings)
}

fn get_hand_value_wild(initial_hand: &Hand) -> HandValue {
    if initial_hand.cards.len() != 5 {
        panic!("Too many cards");
    }

    if initial_hand
        .cards
        .iter()
        .map(|c| c.to_string())
        .collect::<Vec<String>>()
        .join(" ")
        == "1 1 1 1 1"
    {
        return HandValue {
            value: get_suite_value(&[14, 14, 14, 14, 14]) + get_card_value(&initial_hand.cards),
            bid_amount: initial_hand.bid_amount,
        };
    }

    let mut max_suite_value = 0;
    let mut hand_queue = Queue::<Hand>::new();
    hand_queue.add(initial_hand.clone()).unwrap();

    let mut hands_seen = HashSet::<Hand>::new();

    while hand_queue.size() > 0 {
        let hand = hand_queue.remove().unwrap();
        let suite_value = get_suite_value(&hand.cards);

        if suite_value > max_suite_value {
            max_suite_value = suite_value;
        }

        for (card_i, card) in hand.cards.iter().enumerate() {
            if *card == 1 {
                // Go through all possibilities
                for wild_card in 2..15 {
                    let mut new_cards = hand.cards[..].to_vec();
                    new_cards[card_i] = wild_card;
                    let new_hand = new_hand(new_cards, hand.bid_amount);

                    if !hands_seen.contains(&new_hand) {
                        hand_queue.add(new_hand.clone()).unwrap();
                        hands_seen.insert(new_hand.clone());
                    }
                }
            }
        }
    }

    let value = max_suite_value + get_card_value(&initial_hand.cards);

    HandValue {
        value,
        bid_amount: initial_hand.bid_amount,
    }
}

fn get_hand_value(hand: &Hand) -> HandValue {
    if hand.cards.len() != 5 {
        panic!("Too many cards");
    }

    let mut value = get_suite_value(&hand.cards);
    value += get_card_value(&hand.cards);

    HandValue {
        value,
        bid_amount: hand.bid_amount,
    }
}

fn get_suite_value(cards: &[i32]) -> i32 {
    let card_map = cards.iter().fold(HashMap::new(), |mut acc, card| {
        *acc.entry(card).or_insert(0) += 1;
        acc
    });

    let kind_map = card_map.iter().fold(
        HashMap::new(),
        |mut acc: HashMap<&i32, Vec<i32>>, (card, card_count)| {
            (*acc.entry(card_count).or_default()).push(**card);

            acc
        },
    );

    let mut value = 0;

    if kind_map.get(&5).is_some_and(|cards| cards.len() == 1) {
        // Five of a kind
        value += 7 * 20_i32.pow(5)
    } else if kind_map.get(&4).is_some_and(|cards| cards.len() == 1) {
        // Four of a kind
        value += 6 * 20_i32.pow(5)
    } else if kind_map.get(&3).is_some_and(|cards| cards.len() == 1)
        && kind_map.get(&2).is_some_and(|cards| cards.len() == 1)
    {
        // Full house
        value += 5 * 20_i32.pow(5)
    } else if kind_map.get(&3).is_some_and(|cards| cards.len() == 1) {
        // Three of a kind
        value += 4 * 20_i32.pow(5)
    } else if kind_map.get(&2).is_some_and(|cards| cards.len() == 2) {
        // Two pair
        value += 3 * 20_i32.pow(5)
    } else if kind_map.get(&2).is_some_and(|cards| cards.len() == 1) {
        // One pair
        value += 2 * 20_i32.pow(5)
    }

    value
}

fn get_card_value(cards: &Vec<i32>) -> i32 {
    // Handle high card
    let mut value = 0;

    cards.iter().enumerate().for_each(|(card_i, card)| {
        value += card * 20_i32.pow((cards.len() - 1 - card_i) as u32);
    });

    value
}

fn read_hands(input: &str) -> Vec<Hand> {
    input
        .split('\n')
        .map(|hand_string| {
            let hand_parts = hand_string.split(' ').collect::<Vec<&str>>();
            let cards = hand_parts[0]
                .trim()
                .chars()
                .map(|card_string| {
                    if card_string == 'A' {
                        14
                    } else if card_string == 'K' {
                        13
                    } else if card_string == 'Q' {
                        12
                    } else if card_string == 'J' {
                        11
                    } else if card_string == 'T' {
                        10
                    } else {
                        card_string.to_string().parse().unwrap()
                    }
                })
                .collect::<Vec<i32>>();
            let bid_amount = hand_parts[1].trim().parse().unwrap();

            new_hand(cards, bid_amount)
        })
        .collect()
}

fn read_hands_wild(input: &str) -> Vec<Hand> {
    input
        .split('\n')
        .map(|hand_string| {
            let hand_parts = hand_string.split(' ').collect::<Vec<&str>>();
            let cards = hand_parts[0]
                .trim()
                .chars()
                .map(|card_string| {
                    if card_string == 'A' {
                        14
                    } else if card_string == 'K' {
                        13
                    } else if card_string == 'Q' {
                        12
                    } else if card_string == 'J' {
                        // Joker
                        1
                    } else if card_string == 'T' {
                        10
                    } else {
                        card_string.to_string().parse().unwrap()
                    }
                })
                .collect::<Vec<i32>>();
            let bid_amount = hand_parts[1].trim().parse().unwrap();

            new_hand(cards, bid_amount)
        })
        .collect()
}

#[cfg(test)]
mod test {
    use std::fs;

    use crate::{get_total_winnings, get_total_winnings_wild};

    const TEST_INPUT_1: &str = "32T3K 765
T55J5 684
KK677 28
KTJJT 220
QQQJA 483";

    #[test]
    fn test_get_total_winnings() {
        assert_eq!(6440, get_total_winnings(TEST_INPUT_1).unwrap());

        let input = fs::read_to_string("./input.txt").unwrap();

        assert_eq!(249_204_891, get_total_winnings(input.as_str()).unwrap());

        assert_eq!(5905, get_total_winnings_wild(TEST_INPUT_1).unwrap());

        // > 249317951
        // > 249324956
        //  !249418892
        // > 249459759
        //  !249655270
        assert_eq!(249666369, get_total_winnings_wild(input.as_str()).unwrap());
    }
}
