package year2022day06_test

import (
	year2022day06 "johnwebb4/adventcode/year2022day06"
	"os"
	"testing"
)

func TestDetectPacket(t *testing.T) {
	test1 := "mjqjpqmgbljsphdztnvjfqwrcgsmlb"
	test2 := "bvwbjplbgvbhsrlpgdmjqwftvncz"
	test3 := "nppdvjthqldpwncqszvftbrmjlhg"
	test4 := "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"
	test5 := "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"

	testIntEqual(t, year2022day06.DetectPackage(test1, 4), 7)
	testIntEqual(t, year2022day06.DetectPackage(test2, 4), 5)
	testIntEqual(t, year2022day06.DetectPackage(test3, 4), 6)
	testIntEqual(t, year2022day06.DetectPackage(test4, 4), 10)
	testIntEqual(t, year2022day06.DetectPackage(test5, 4), 11)

	data, err := os.ReadFile("./input.txt")
	if err != nil {
		t.Fatalf("Advent of code year 2022 day 06. Failed to read input file")
	}
	input := string(data)

	testIntEqual(t, year2022day06.DetectPackage(input, 4), 1855)

	testIntEqual(t, year2022day06.DetectPackage(test1, 14), 19)
	testIntEqual(t, year2022day06.DetectPackage(test2, 14), 23)
	testIntEqual(t, year2022day06.DetectPackage(test3, 14), 23)
	testIntEqual(t, year2022day06.DetectPackage(test4, 14), 29)
	testIntEqual(t, year2022day06.DetectPackage(test5, 14), 26)

	testIntEqual(t, year2022day06.DetectPackage(input, 14), 3256)
}

func testIntEqual(t *testing.T, got int, expected int) {
	if got != expected {
		t.Fatalf("Advent of code year 2022 day 06. Expected '%d' but got '%d'", expected, got)
	}
}
