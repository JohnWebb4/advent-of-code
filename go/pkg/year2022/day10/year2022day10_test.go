package year2022day10_test

import (
	"johnwebb4/adventofcode/year2022day10"
	"os"
	"testing"
)

func TestFindSignalSums(t *testing.T) {
	data, err := os.ReadFile("./test.txt")
	if err != nil {
		t.Fatalf("Advent of code year 2022 day 10. Cannot read test file: %v", err)
	}
	test := string(data)

	signalIds := []int{
		20, 60, 100, 140, 180, 220,
	}

	got := year2022day10.FindSignalSums(test, signalIds)
	expected := 13140

	if got != expected {
		t.Errorf("Advent of code year 2022 day 10 part one test. Expected %d but got %d", expected, got)
	}

	data, err = os.ReadFile("./input.txt")
	if err != nil {
		t.Fatalf("Advent of code year 2022 day 10. Cannot read input file: %v", err)
	}
	input := string(data)

	got = year2022day10.FindSignalSums(input, signalIds)
	expected = 12740

	if got != expected {
		t.Errorf("Advent of code year 2022 day 10 part one input. Expected %d but got %d", expected, got)
	}

	gotS := year2022day10.PrintCRT(test)
	expectedS := `##..##..##..##..##..##..##..##..##..##..
###...###...###...###...###...###...###.
####....####....####....####....####....
#####.....#####.....#####.....#####.....
######......######......######......####
#######.......#######.......#######.....`

	if gotS != expectedS {
		t.Errorf("Advent of code year 2022 day 10 part two test. Expected\n\n%s\n\nbut got\n\n%s", expectedS, gotS)
	}

	gotS = year2022day10.PrintCRT(input)
	expectedS = `###..###..###...##..###...##...##..####.
#..#.#..#.#..#.#..#.#..#.#..#.#..#.#....
#..#.###..#..#.#..#.#..#.#..#.#....###..
###..#..#.###..####.###..####.#.##.#....
#.#..#..#.#....#..#.#.#..#..#.#..#.#....
#..#.###..#....#..#.#..#.#..#..###.#....`

	if gotS != expectedS {
		t.Errorf("Advent of code year 2022 day 10 part two input. Expected\n\n%s\n\nbut got\n\n%s", expectedS, gotS)
	}
}
