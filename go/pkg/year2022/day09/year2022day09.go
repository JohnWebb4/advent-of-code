package year2022day09

import (
	"fmt"
	"strconv"
	"strings"
)

func CountTailPositions(input string, size int) int {
	commands := strings.Split(input, "\n")

	rope := MakeRope(size)

	tailPositionsMap := make(map[string]bool)
	tailPositionsMap[rope[size-1].ToString()] = true

	for _, command := range commands {
		parts := strings.Split(command, " ")
		direction := parts[0]
		amount, err := strconv.Atoi(parts[1])

		if err != nil {
			fmt.Printf("failed to move head %v", err)
		}

		if direction == "U" {
			for i := 0; i < amount; i++ {
				rope, err = rope.MoveRope(NewVector(0, 1))

				if err != nil {
					fmt.Printf("Failed to move rope up %v\n", err)
				}

				tailPositionsMap[rope[size-1].ToString()] = true
			}
		} else if direction == "D" {
			for i := 0; i < amount; i++ {
				rope, err = rope.MoveRope(NewVector(0, -1))

				if err != nil {
					fmt.Printf("Failed to move rope down %v\n", err)
				}

				tailPositionsMap[rope[size-1].ToString()] = true
			}
		} else if direction == "L" {
			for i := 0; i < amount; i++ {
				rope, err = rope.MoveRope(NewVector(-1, 0))

				if err != nil {
					fmt.Printf("Failed to move rope left %v\n", err)
				}

				tailPositionsMap[rope[size-1].ToString()] = true
			}
		} else if direction == "R" {
			for i := 0; i < amount; i++ {
				rope, err = rope.MoveRope(NewVector(1, 0))

				if err != nil {
					fmt.Printf("Failed to move rope right %v\n", err)
				}

				tailPositionsMap[rope[size-1].ToString()] = true
			}
		}
	}

	return len(tailPositionsMap)
}
