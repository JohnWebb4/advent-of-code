package year2022day03_test

import (
	"johnwebb4/adventcode/year2022day03"
	"os"
	"testing"
)

func Test(t *testing.T) {
	test := `vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw`

	got := year2022day03.FindTotalPriorityOfErrors(test)
	expected := 157

	if got != expected {
		t.Fatalf("Advent of code year 2022 day 03 part one test. Expected %d but got %d", expected, got)
	}

	data, err := os.ReadFile("./input.txt")

	if err != nil {
		t.Fatalf("Advent of code year 2022. Failed to read input file: %v", err)
	}

	input := string(data)

	got = year2022day03.FindTotalPriorityOfErrors(input)
	expected = 8018

	if got != expected {
		t.Fatalf("Advent of code year 2022 day 03 part one input. Expected %d but got %d", expected, got)
	}

	got = year2022day03.FindTotalPriorityOfBadges(test)
	expected = 70

	if got != expected {
		t.Fatalf("Advent of code year 2022 day 03 part two test. Expected %d but got %d", expected, got)
	}

	got = year2022day03.FindTotalPriorityOfBadges(input)
	expected = 0

	if got != expected {
		t.Fatalf("Advent of code year 2022 day 03 part two input. Expected %d but got %d", expected, got)
	}
}
