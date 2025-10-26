package year2022day22_test

import (
	"johnwebb4/adventofcode/year2022day22"
	"os"
	"testing"
)

const test1Input = `        ...#
        .#..
        #...
        ....
...#.......#
........#...
..#....#....
..........#.
        ...#....
        .....#..
        .#......
        ......#.

10R5L5R10L4R5L5`

func TestFinalPassword(t *testing.T) {
	assertPartOne(t, "Test1", 6032, test1Input)

	data, err := os.ReadFile("./input.txt")

	if err != nil {
		t.Fatalf("Year 2022 Day 22. Error reading file %v", err)
	}

	input := string(data)

	assertPartOne(t, "Input", 93226, input)

	assertPartTwo(t, "Test1", 5031, test1Input)

	assertPartTwo(t, "Input", 37415, input)
}

func assertPartOne(t *testing.T, name string, expected int, input string) {
	result, err := year2022day22.GetFinalPassword(input)

	if err != nil {
		t.Fatalf("Year 2022 Day 22 Part One %s. Error %v", name, err)
	}

	if result != expected {
		t.Errorf("Year 2022 Day 22 Part One %s. Expected %d but got %d", name, expected, result)
	}
}

func assertPartTwo(t *testing.T, name string, expected int, input string) {
	result, err := year2022day22.GetFinalPasswordCube(input)

	if err != nil {
		t.Fatalf("Year 2022 Day 22 Part Two %s. Error %v", name, err)
	}

	if result != expected {
		t.Errorf("Year 2022 Day 22 Part Two %s. Expected %d but got %d", name, expected, result)
	}

}
