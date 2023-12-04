package year2022day23_test

import (
	"johnwebb4/adventofcode/year2022day23"
	"os"
	"testing"
)

const testInput1 = `.....
..##.
..#..
.....
..##.
.....`

const testInput2 = `..............
..............
.......#......
.....###.#....
...#...#.#....
....#...##....
...#.###......
...##.#.##....
....#..#......
..............
..............
..............`

func TestXxx(t *testing.T) {
	assertPartOne(t, "Test1", 25, testInput1, 3)
	assertPartOne(t, "Test2", 110, testInput2, 10)

	data, err := os.ReadFile("./input.txt")
	if err != nil {
		t.Fatalf("Year 2022 Day 23. Failed to read input file %v", err)
	}
	input := string(data)

	assertPartOne(t, "Input", 3970, input, 10)

	assertPartTwo(t, "Test2", 20, testInput2)
	assertPartTwo(t, "Input", 923, input)
}

func assertPartOne(t *testing.T, name string, expected int, input string, numRounds int) {
	result := year2022day23.GetCountEmptyGroundTiles(input, numRounds)

	if result != expected {
		t.Errorf("Year 2022 Day 23 %s. Expected %d but got %d", name, expected, result)
	}
}

func assertPartTwo(t *testing.T, name string, expected int, input string) {
	result := year2022day23.GetFirstRoundNoElfMoves(input)

	if result != expected {
		t.Errorf("Year 2022 Day 23 %s. Expected %d but got %d", name, expected, result)
	}
}