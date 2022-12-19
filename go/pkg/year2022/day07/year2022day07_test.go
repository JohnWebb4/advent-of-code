package year2022day07_test

import (
	year2022day07 "johnwebb4/adventcode/year2022day07"
	"os"
	"testing"
)

func TestFindSumOfDirectories(t *testing.T) {
	test := `$ cd /
$ ls
dir a
14848514 b.txt
8504156 c.dat
dir d
$ cd a
$ ls
dir e
29116 f
2557 g
62596 h.lst
$ cd e
$ ls
584 i
$ cd ..
$ cd ..
$ cd d
$ ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k`

	got := year2022day07.FindSumOfSmallDirectories(test)
	expected := 95437

	if got != expected {
		t.Errorf("Advent of code year 2022 day 07 part one test. Expected %d but got %d", expected, got)
	}

	data, err := os.ReadFile("./input.txt")
	if err != nil {
		t.Fatalf("Advent of code year 2022 day 07. Failed to read input file.")
	}
	input := string(data)

	got = year2022day07.FindSumOfSmallDirectories(input)
	expected = 1443806

	if got != expected {
		t.Errorf("Advent of code year 2022 day 07 part one input. Expected %d but got %d", expected, got)
	}

	got = year2022day07.FindSizeSmallestDirectoryForFreeSpace(test, 70000000, 30000000)
	expected = 24933642

	if got != expected {
		t.Errorf("Advent of code year 2022 day 07 part two test. Expected %d but got %d", expected, got)
	}

	got = year2022day07.FindSizeSmallestDirectoryForFreeSpace(input, 70000000, 30000000)
	expected = 942298

	if got != expected {
		t.Errorf("Advent of code year 2022 day 07 part two input. Expected %d but got %d", expected, got)
	}
}
