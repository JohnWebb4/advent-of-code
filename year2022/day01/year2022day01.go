package year2022day01

import (
	"container/heap"
	"fmt"
	"strconv"
	"strings"
)

func CountCalories(input string) int {
	elves := strings.Split(input, "\n\n")

	maxTotalCalories := 0
	for _, elf := range elves {
		foods := strings.Split(elf, "\n")
		totalCalories := 0

		for _, food := range foods {
			calories, err := strconv.Atoi(food)

			if err != nil {
				fmt.Errorf("Error parsing calores: %d", calories)
			}

			totalCalories += calories
		}

		if totalCalories > maxTotalCalories {
			maxTotalCalories = totalCalories
		}
	}

	return maxTotalCalories
}

func CountTopThree(input string) int {
	elves := strings.Split(input, "\n\n")

	ch := &CalorieHeap{}
	heap.Init(ch)

	for _, elf := range elves {
		foods := strings.Split(elf, "\n")
		totalCalories := 0

		for _, food := range foods {
			calories, err := strconv.Atoi(food)

			if err != nil {
				fmt.Errorf("Error parsing calores: %d", calories)
			}

			totalCalories += calories
		}

		heap.Push(ch, totalCalories)

	}

	maxTotalCalories := 0
	for i := 0; i < 3; i++ {
		maxTotalCalories += heap.Pop(ch).(int)
	}

	return maxTotalCalories
}
