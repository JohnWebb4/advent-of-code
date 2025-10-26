package year2022day16_test

import (
	"johnwebb4/adventofcode/year2022day16"
	"log"
	"os"
	"testing"
)

const test = `Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
Valve BB has flow rate=13; tunnels lead to valves CC, AA
Valve CC has flow rate=2; tunnels lead to valves DD, BB
Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
Valve EE has flow rate=3; tunnels lead to valves FF, DD
Valve FF has flow rate=0; tunnels lead to valves EE, GG
Valve GG has flow rate=0; tunnels lead to valves FF, HH
Valve HH has flow rate=22; tunnel leads to valve GG
Valve II has flow rate=0; tunnels lead to valves AA, JJ
Valve JJ has flow rate=21; tunnel leads to valve II`

func TestYear2022Day16(t *testing.T) {
	assertPartOne(t, "test one", test, 1651)

	inputData, err := os.ReadFile("./input.txt")
	if err != nil {
		t.Fatalf("Advent of code year 2022 day 16 failed to read input file %v", err)
	}
	input := string(inputData)

	assertPartOne(t, "input one", input, 1880)

	assertPartTwo(t, "test two", test, 1707)

	assertPartTwo(t, "input two", input, 2520)
}

func assertPartOne(t *testing.T, name string, input string, expected int) {
	result, err := year2022day16.GetMostPressure(input, 30)

	if err != nil {
		log.Fatalf("error part one: %v", err)
	}

	if expected != result {
		t.Errorf("Year 2022 day 16 part one %s. Expected %d but got %d", name, expected, result)
	}
}

func assertPartTwo(t *testing.T, name string, input string, expected int) {
	result, err := year2022day16.GetMostPressureWithElephant(input, 26)

	if err != nil {
		log.Fatalf("error part two: %v", err)
	}

	if expected != result {
		t.Errorf("Year 2022 day 16 part two %s. Expected %d but got %d", name, expected, result)
	}
}
