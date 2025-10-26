package year2022day10

import (
	"fmt"
	"math"
	"sort"
	"strconv"
	"strings"
)

func FindSignalSums(input string, signalIds []int) int {
	sort.Ints(signalIds)
	maxSignalId := signalIds[len(signalIds)-1]

	commands := strings.Split(input, "\n")

	instructions := getInstructions(commands)
	instructionLength := len(instructions)

	signalStrengths := []int{}
	signalX := 1

	for i := 1; i <= maxSignalId; i++ {
		instruction := instructions[(i-1)%instructionLength]

		if i == signalIds[0] {
			signalStrength := signalX * i
			signalStrengths = append(signalStrengths, signalStrength)

			signalIds = signalIds[1:]
		}

		newSignal, err := getSignalX(signalX, instruction)
		signalX = newSignal

		if err != nil {
			fmt.Printf("Error running instruction %v", err)
		}

	}

	sumSignalStrengths := 0
	for _, signalStrength := range signalStrengths {
		sumSignalStrengths += signalStrength
	}

	return sumSignalStrengths
}

func PrintCRT(input string) string {
	commands := strings.Split(input, "\n")

	instructions := getInstructions(commands)
	instructionLength := len(instructions)

	crtScreen := []string{}
	signalX := 1

	for i := 0; i < 240; i++ {
		if math.Abs(float64(signalX-(i%40))) <= 1 {
			crtScreen = append(crtScreen, "#")
		} else {
			crtScreen = append(crtScreen, ".")
		}

		if i%40 == 39 && i < 239 {
			crtScreen = append(crtScreen, "\n")
		}

		instruction := instructions[i%instructionLength]

		newSignal, err := getSignalX(signalX, instruction)
		signalX = newSignal

		if err != nil {
			fmt.Printf("Error running instruction %v", err)
		}
	}

	return strings.Join(crtScreen, "")
}

func getInstructions(commands []string) []string {
	instructions := []string{}
	for _, command := range commands {
		if strings.HasPrefix(command, "noop") {
			instructions = append(instructions, "noop")
		} else if strings.HasPrefix(command, "addx") {
			instructions = append(instructions, "addx-begin")
			instructions = append(instructions, command)
		}
	}

	return instructions
}

func getSignalX(signalX int, instruction string) (int, error) {
	if strings.HasPrefix(instruction, "noop") {
		return signalX, nil
	} else if strings.HasPrefix(instruction, "addx-begin") {
		return signalX, nil
	} else if strings.HasPrefix(instruction, "addx") {
		arguments := strings.Split(instruction, " ")
		value, err := strconv.Atoi(arguments[1])

		if err != nil {
			return signalX, err
		}
		signalX += value

		return signalX, nil
	}

	return signalX, nil
}
