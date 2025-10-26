package year2022day11_test

import (
	"johnwebb4/adventofcode/year2022day11"
	"os"
	"testing"
)

func TestGetMonkeyBusinessLevel(t *testing.T) {
	data, err := os.ReadFile("./test.txt")
	if err != nil {
		t.Fatalf("Advent of code year 2022 day 11. Failed to read test file: %v", err)
	}
	test := string(data)

	got := year2022day11.GetMonkeyBusinessLevel(test, 20, true)
	expected := 10605

	if got != expected {
		t.Errorf("Advent of code year 2022 day 11 part one test. Expected %d but got %d", expected, got)
	}

	data, err = os.ReadFile("./input.txt")
	if err != nil {
		t.Fatalf("Advent of code year 2022 day 11. Failed to read input file: %v", err)
	}
	input := string(data)

	got = year2022day11.GetMonkeyBusinessLevel(input, 20, true)
	expected = 113220

	if got != expected {
		t.Errorf("Advent of code year 2022 day 11 part one input. Expected %d but got %d", expected, got)
	}

	got = year2022day11.GetMonkeyBusinessLevel(test, 10000, false)
	expected = 2713310158

	if got != expected {
		t.Errorf("Advent of code year 2022 day 11 part two test. Expected %d but got %d", expected, got)
	}

	got = year2022day11.GetMonkeyBusinessLevel(input, 10000, false)
	expected = 30_599_555_965

	if got != expected {
		t.Errorf("Advent of code year 2022 day 11 part two input. Expected %d but got %d", expected, got)
	}
}
