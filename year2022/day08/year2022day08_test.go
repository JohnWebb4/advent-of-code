package year2022day08_test

import (
	year2022day08 "johnwebb4/adventofcode/year2022day08"
	"os"
	"testing"
)

func TestCountTrees(t *testing.T) {
	test := `30373
25512
65332
33549
35390`
	got := year2022day08.CountVisibleTrees(test)
	expected := 21

	if got != expected {
		t.Errorf("Advent of code year 2022 day 08 part one test. Expected %d but got %d", expected, got)
	}

	data, err := os.ReadFile("./input.txt")
	if err != nil {
		t.Fatalf("Advent of code year 2022 day 08 failed to read input file")
	}
	input := string(data)

	got = year2022day08.CountVisibleTrees(input)
	expected = 1647

	if got != expected {
		t.Errorf("Advent of code year 2022 day 08 part one input. Expected %d but got %d", expected, got)
	}

	got = year2022day08.FindHighestScenicScore(test)
	expected = 8

	if got != expected {
		t.Errorf("Advent of code year 2022 day 08 part two test. Expected %d but got %d", expected, got)
	}

	got = year2022day08.FindHighestScenicScore(input)
	expected = 392080

	if got != expected {
		t.Errorf("Advent of code year 2022 day 08 part two input. Expected %d but got %d", expected, got)
	}
}
