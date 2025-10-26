package year2022day19_test

import (
	"johnwebb4/adventofcode/year2022day19"
	"os"
	"testing"
)

const testInput = `Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.
Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian.`

func TestYear2022Day19(t *testing.T) {
	AssertPartOne(t, "Test", 33, testInput)

	data, err := os.ReadFile("./input.txt")
	if err != nil {
		t.Fatalf("Year 2022 day 19. Cannot open input file %v", err)
	}
	input := string(data)

	AssertPartOne(t, "Input", 1404, input)

	AssertPartTwo(t, "Test", 3472, testInput)

	AssertPartTwo(t, "Input", 5880, input)
}

func AssertPartOne(t *testing.T, name string, expected int, input string) {
	result, err := year2022day19.GetSumBlueprintQualityLevel(24, input)

	if err != nil {
		t.Fatalf("Year 2022 day 19 %s. Error %v\n", name, err)
	}

	if result != expected {
		t.Errorf("Year 2022 Day 19 %s. Expected %d but got %d.\n", name, expected, result)
	}
}

func AssertPartTwo(t *testing.T, name string, expected int, input string) {
	result, err := year2022day19.GetProductOfTopThreeBlueprintGeodes(32, input)

	if err != nil {
		t.Fatalf("Year 2022 day 19 Part Two %s. Error %v\n", name, err)
	}

	if result != expected {
		t.Errorf("Year 2022 day 19 %s. Expected %d but got %d.\n", name, expected, result)
	}
}
