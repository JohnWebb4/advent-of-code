package year2022day08

import (
	"fmt"
	"strconv"
	"strings"
)

func CountVisibleTrees(input string) int {
	rows := strings.Split(input, "\n")
	trees := [][]int{}

	for _, row := range rows {
		rowTrees := strings.Split(strings.Trim(row, " "), "")
		intTrees := []int{}

		for _, sCell := range rowTrees {
			i, err := strconv.Atoi(sCell)

			if err != nil {
				fmt.Errorf("failed to decode %s", sCell)
			}

			intTrees = append(intTrees, i)
		}

		trees = append(trees, intTrees)
	}

	totalVisibleTrees := 0

	rowLength := len(trees)
	columnLength := len(trees[0])
	hasSeenMap := make(map[string]bool)

	for i := 0; i < rowLength; i++ {
		totalVisibleTrees += countTrees(trees, i, 0, 0, 1, hasSeenMap)
		totalVisibleTrees += countTrees(trees, i, columnLength-1, 0, -1, hasSeenMap)
	}

	for i := 0; i < columnLength; i++ {
		totalVisibleTrees += countTrees(trees, 0, i, 1, 0, hasSeenMap)
		totalVisibleTrees += countTrees(trees, rowLength-1, i, -1, 0, hasSeenMap)
	}

	return totalVisibleTrees
}

func FindHighestScenicScore(input string) int {
	rows := strings.Split(input, "\n")
	trees := [][]int{}

	for _, row := range rows {
		rowTrees := strings.Split(strings.Trim(row, " "), "")
		intTrees := []int{}

		for _, sCell := range rowTrees {
			i, err := strconv.Atoi(sCell)

			if err != nil {
				fmt.Errorf("failed to decode %s", sCell)
			}

			intTrees = append(intTrees, i)
		}

		trees = append(trees, intTrees)
	}

	maxScenicScore := 0

	rowLength := len(trees)
	columnLength := len(trees[0])
	scenicMap := make(map[string]int)

	for i := 1; i < rowLength-1; i++ {
		for j := 1; j < columnLength-1; j++ {
			scenicScore := getScenicScore(trees, i, j)

			scenicMap[getIndexKey(i, j)] = scenicScore

			if scenicScore > maxScenicScore {
				maxScenicScore = scenicScore
			}
		}
	}

	return maxScenicScore
}

func countTrees(trees [][]int, x int, y int, xDiff int, yDiff int, hasSeenMap map[string]bool) int {
	rowLength := len(trees)
	columnLength := len(trees[0])

	count := 0
	previousTreeSize := -1

	hasCheckedXOnce := false
	for iX := x; iX < rowLength && iX >= 0 && (xDiff != 0 || !hasCheckedXOnce); iX += xDiff {
		hasCheckedYOnce := false
		for iY := y; iY < columnLength && iY >= 0 && (yDiff != 0 || !hasCheckedYOnce); iY += yDiff {
			indexKey := getIndexKey(iX, iY)

			if trees[iX][iY] > previousTreeSize {
				if !hasSeenMap[indexKey] {
					count++
					hasSeenMap[indexKey] = true
				}

				previousTreeSize = trees[iX][iY]
			}
			hasCheckedYOnce = true
		}
		hasCheckedXOnce = true
	}

	return count
}

func getScenicScore(trees [][]int, x int, y int) int {
	treeHeight := trees[x][y]
	rowLength := len(trees)
	columnLength := len(trees[0])

	topTreeCount := 0
	for i := x - 1; i >= 0; i-- {
		topTreeCount++

		if trees[i][y] >= treeHeight {
			break
		}
	}

	bottomCount := 0
	for i := x + 1; i < rowLength; i++ {
		bottomCount++

		if trees[i][y] >= treeHeight {
			break
		}
	}

	leftCount := 0
	for i := y - 1; i >= 0; i-- {
		leftCount++

		if trees[x][i] >= treeHeight {
			break
		}
	}

	rightCount := 0
	for i := y + 1; i < columnLength; i++ {
		rightCount++

		if trees[x][i] >= treeHeight {
			break
		}
	}

	return topTreeCount * bottomCount * leftCount * rightCount
}

func getIndexKey(i int, j int) string {
	return fmt.Sprintf("%d,%d", i, j)
}
