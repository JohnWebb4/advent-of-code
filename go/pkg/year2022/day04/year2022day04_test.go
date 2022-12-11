package year2022day04_test

import (
	"johnwebb4/adventcode/year2022day04"
	"os"
	"testing"
)

func TestCountPairsContainAnother(t *testing.T) {
	test := `2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8`
	got := year2022day04.CountPairsContainAnother(test)
	expected := 2

	if got != expected {
		t.Fatalf("Advent of code year 2022 day 04 part one test. Expected %d but got %d", expected, got)
	}

	data, err := os.ReadFile("./input.txt")
	if err != nil {
		t.Fatalf("Advent of code year 2022 day 04. Error reading input file")
	}
	input := string(data)

	got = year2022day04.CountPairsContainAnother(input)
	expected = 477

	if got != expected {
		t.Fatalf("Advent of code year 2022 day 04 part one input. Expected %d but got %d", expected, got)
	}

	got = year2022day04.CountPairsContainAnyPart(test)
	expected = 4

	if got != expected {
		t.Fatalf("Advent of code year 2022 day 04 part two test. Expected %d but got %d", expected, got)
	}

	got = year2022day04.CountPairsContainAnyPart(input)
	expected = 830

	if got != expected {
		t.Fatalf("Advent of code year 2022 day 04 part two input. Expected %d but got %d", expected, got)
	}
}
