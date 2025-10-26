package year2022day12

import (
	"container/heap"
	"math"
	"strings"
)

func FindFewestStepsToSignal(input string) int {
	heightGrid := getHeightGrid(input)
	beginning := (*Point)(nil)

	for i, row := range heightGrid {
		for j, cell := range row {
			if cell == 0 {
				beginning = NewPoint(i, j, 0)
			}
		}
	}

	return FindFewestStepsFromStart(input, beginning, heightGrid)
}

func FindFewestStepsToSignalFromLow(input string) int {
	heightGrid := getHeightGrid(input)
	minStepsToSignal := math.MaxInt32

	for i, row := range heightGrid {
		for j, cell := range row {
			if cell == 1 {
				stepsToSignal := FindFewestStepsFromStart(input, NewPoint(i, j, 1), heightGrid)

				if stepsToSignal < minStepsToSignal {
					minStepsToSignal = stepsToSignal
				}
			}
		}
	}

	return minStepsToSignal
}

func FindFewestStepsFromStart(input string, beginning *Point, heightGrid [][]int) int {
	xLength := len(heightGrid)
	yLength := len(heightGrid[0])

	pq := make(Heap, 1)
	pq[0] = NewPath([]*Point{beginning})
	heap.Init(&pq)

	leastStepsMap := make(map[string]int)
	minStepsToSignal := math.MaxInt32

	for pq.Len() > 0 {
		item := heap.Pop(&pq)
		currentPath := item.(*Path)
		lastPoint := currentPath.LastPoint()
		key := lastPoint.ToString()
		currentSteps := currentPath.Steps() - 1

		if leastSteps, ok := leastStepsMap[key]; !ok || leastSteps > currentSteps && currentSteps < minStepsToSignal {
			if lastPoint.height == 27 && minStepsToSignal > currentSteps {
				minStepsToSignal = currentSteps
			}

			// Left
			if lastPoint.y > 0 {
				newX := lastPoint.x
				newY := lastPoint.y - 1

				updateMinStepsToSignal(newX, newY, heightGrid, *currentPath, leastStepsMap, minStepsToSignal, &pq)
			}

			// Right
			if lastPoint.y < yLength-1 {
				newX := lastPoint.x
				newY := lastPoint.y + 1

				updateMinStepsToSignal(newX, newY, heightGrid, *currentPath, leastStepsMap, minStepsToSignal, &pq)
			}

			// Up
			if lastPoint.x > 0 {
				newX := lastPoint.x - 1
				newY := lastPoint.y

				updateMinStepsToSignal(newX, newY, heightGrid, *currentPath, leastStepsMap, minStepsToSignal, &pq)
			}

			// Down
			if lastPoint.x < xLength-1 {
				newX := lastPoint.x + 1
				newY := lastPoint.y

				updateMinStepsToSignal(newX, newY, heightGrid, *currentPath, leastStepsMap, minStepsToSignal, &pq)
			}

			if leastStepsMap[key] == 0 || leastStepsMap[key] > currentSteps {
				leastStepsMap[key] = currentSteps
			}
		}
	}

	return minStepsToSignal
}

func updateMinStepsToSignal(newX int, newY int, heightGrid [][]int, currentPath Path, leastStepsMap map[string]int, minStepsToSignal int, pq *Heap) {
	lastPoint := currentPath.LastPoint()

	if int(heightGrid[newX][newY])-int(lastPoint.height) <= 1 {
		newPoint := NewPoint(newX, newY, heightGrid[newX][newY])

		newRoute := append([]*Point{}, currentPath.route...)
		newRoute = append(newRoute, newPoint)
		newPath := NewPath(newRoute)
		newSteps := newPath.Steps() - 1

		key := newPoint.ToString()
		if leastSteps, ok := leastStepsMap[key]; !ok || leastSteps > newSteps && newSteps > minStepsToSignal {
			heap.Push(pq, newPath)
		}
	}
}

func getHeightGrid(input string) [][]int {
	rows := strings.Split(input, "\n")
	heightGrid := [][]int{}

	for _, row := range rows {
		heightRow := []int{}

		for _, r := range row {
			heightRow = append(heightRow, getHeightValue(r))
		}

		heightGrid = append(heightGrid, heightRow)
	}

	return heightGrid
}

func getHeightValue(height rune) int {
	value := int(height) - 96

	if height == 'S' {
		value = 0
	} else if height == 'E' {
		value = 27
	}

	return value
}
