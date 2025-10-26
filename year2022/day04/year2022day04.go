package year2022day04

import (
	"fmt"
	"strconv"
	"strings"
)

func CountPairsContainAnother(input string) int {
	pairs := strings.Split(input, "\n")

	countOverlapping := 0
	for _, pair := range pairs {
		elves := strings.Split(pair, ",")

		elf1 := strings.Split(elves[0], "-")
		elf1Min, err := strconv.Atoi(elf1[0])
		if err != nil {
			fmt.Errorf("Error parsing start range to int %s", elf1[0])
		}
		elf1Max, err := strconv.Atoi(elf1[1])
		if err != nil {
			fmt.Errorf("Error parsing end range to int %s", elf1[1])
		}

		elf2 := strings.Split(elves[1], "-")
		elf2Min, err := strconv.Atoi(elf2[0])
		if err != nil {
			fmt.Errorf("Error parsing start range to int %s", elf2[0])
		}
		elf2Max, err := strconv.Atoi(elf2[1])
		if err != nil {
			fmt.Errorf("Error parsing end range to int %s", elf2[1])
		}

		if elf1Min >= elf2Min && elf1Max <= elf2Max {
			countOverlapping++
			continue
		}

		if elf2Min >= elf1Min && elf2Max <= elf1Max {
			countOverlapping++
			continue
		}
	}

	return countOverlapping
}

func CountPairsContainAnyPart(input string) int {
	pairs := strings.Split(input, "\n")

	countOverlapping := 0
	for _, pair := range pairs {
		elves := strings.Split(pair, ",")

		elf1 := strings.Split(elves[0], "-")
		elf1Min, err := strconv.Atoi(elf1[0])
		if err != nil {
			fmt.Errorf("Error parsing start range to int %s", elf1[0])
		}
		elf1Max, err := strconv.Atoi(elf1[1])
		if err != nil {
			fmt.Errorf("Error parsing end range to int %s", elf1[1])
		}

		elf2 := strings.Split(elves[1], "-")
		elf2Min, err := strconv.Atoi(elf2[0])
		if err != nil {
			fmt.Errorf("Error parsing start range to int %s", elf2[0])
		}
		elf2Max, err := strconv.Atoi(elf2[1])
		if err != nil {
			fmt.Errorf("Error parsing end range to int %s", elf2[1])
		}

		if elf1Min >= elf2Min && elf1Max <= elf2Max {
			countOverlapping++
		} else if elf1Min <= elf2Min && elf1Max >= elf2Max {
			countOverlapping++
		} else if elf1Max >= elf2Min && elf1Min <= elf2Max {
			countOverlapping++
		}
	}

	return countOverlapping
}
