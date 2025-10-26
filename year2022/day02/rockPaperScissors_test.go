package year2022day02_test

import (
	"johnwebb4/adventcode/year2022day02"
	"os"
	"testing"
)

func TestDay2(t *testing.T) {
	test := `A Y
B X
C Z`

	got := year2022day02.GetScoreFromStrategy(test)
	expected := 15

	if got != expected {
		t.Fatalf("Advent of code year 2022 day 02 part 01 test. Expected %d but got %d", expected, got)
	}

	data, err := os.ReadFile("./input.txt")

	if err != nil {
		t.Fatalf("Error reading input file: %s", err)
	}

	input := string(data)

	got = year2022day02.GetScoreFromStrategy(input)
	expected = 12855

	if got != expected {
		t.Fatalf("Advent of code year 2022 day 02 part one input. Expected %d but got %d", expected, got)
	}

	got = year2022day02.GetScoreFromWinLoseStrategy(test)
	expected = 12

	if got != expected {
		t.Fatalf("Advent of code year 2022 day 02 part two test. Expected %d but got %d", expected, got)
	}

	got = year2022day02.GetScoreFromWinLoseStrategy(input)
	expected = 0

	if got != expected {
		t.Fatalf("Advent of code year 2022 day 02 part two input. Expected %d but got %d", expected, got)
	}
}
