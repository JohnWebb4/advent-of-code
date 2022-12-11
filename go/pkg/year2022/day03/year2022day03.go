package year2022day03

import (
	"strings"
)

func FindTotalPriorityOfErrors(input string) int {
	totalPriorities := 0
	rucksacks := strings.Split(input, "\n")

	for _, rucksack := range rucksacks {
		presents := []rune(rucksack)
		lenPresents := len(presents)
		leftComparment := presents[:lenPresents/2]
		rightComparment := presents[lenPresents/2:]

		leftHash := make(map[rune]bool)
		for _, present := range leftComparment {
			leftHash[present] = true
		}

		for _, present := range rightComparment {
			if leftHash[present] {
				priority := getPriority(present)

				totalPriorities += priority

				break
			}
		}
	}

	return totalPriorities
}

func FindTotalPriorityOfBadges(input string) int {
	totalPriorities := 0
	rucksacks := strings.Split(input, "\n")
	numGroups := len(rucksacks) / 3

	for groupId := 0; groupId < numGroups; groupId += 1 {
		firstHash := make(map[rune]bool)
		for _, present := range rucksacks[groupId*3] {
			firstHash[present] = true
		}

		secondHash := make(map[rune]bool)
		for _, present := range rucksacks[groupId*3+1] {
			secondHash[present] = true
		}

		for _, present := range rucksacks[groupId*3+2] {
			if firstHash[present] && secondHash[present] {
				priority := getPriority(present)

				totalPriorities += priority
				break
			}
		}
	}

	return totalPriorities
}

func getPriority(present rune) int {
	if present >= 97 && present <= 122 {
		return int(present) - 96
	}

	if present >= 65 && present <= 90 {
		return int(present) - 38
	}

	return int(present)
}
