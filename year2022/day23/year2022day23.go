package year2022day23

import (
	"fmt"
	"log"
	"math"
	"strings"

	"github.com/google/uuid"
)

func GetCountEmptyGroundTiles(input string, numRounds int) int {
	elves := readInput(input)

	for i := 0; i < numRounds; i++ {
		elves, _ = updateElves(elves, Direction(i % 4))
	}

	return countGroundTiles(elves)
}

func GetFirstRoundNoElfMoves(input string) int {
	elves := readInput(input)

	for i := 0; i < 1000; i++ {
		nextElves, hasUpdate := updateElves(elves, Direction(i % 4))

		elves = nextElves

		if !hasUpdate {
			return i + 1
		}
	}

	return -1
}

func updateElves(elves []*Elf, startDirection Direction) ([]*Elf, bool) {
	hasUpdate := false

	groundMap := make(map[string]*Elf)
	for _, elf := range elves {
		groundMap[getGroundKey(elf.X, elf.Y)] = elf
	}

	// Propose moves
	proposedElves := make([]*Elf, len(elves))
	for elfI, elf := range elves {
		proposedElf := elf.Copy()

		cellN := groundMap[getGroundKey(elf.X, elf.Y-1)] != nil
		cellNE := groundMap[getGroundKey(elf.X+1, elf.Y-1)] != nil
		cellE := groundMap[getGroundKey(elf.X+1, elf.Y)] != nil
		cellSE := groundMap[getGroundKey(elf.X+1, elf.Y+1)] != nil
		cellS := groundMap[getGroundKey(elf.X, elf.Y+1)] != nil
		cellSW := groundMap[getGroundKey(elf.X-1, elf.Y+1)] != nil
		cellW := groundMap[getGroundKey(elf.X-1, elf.Y)] != nil
		cellNW := groundMap[getGroundKey(elf.X-1, elf.Y-1)] != nil

		if cellN || cellNE || cellE || cellSE || cellS || cellSW || cellW || cellNW {
			for index := int(startDirection); index < int(startDirection)+4; index++ {
				if index%4 == 0 {
					if !cellN && !cellNE && !cellNW {
						proposedElf.Y--
						break
					}
				} else if index%4 == 1 {
					if !cellS && !cellSE && !cellSW {
						proposedElf.Y++
						break
					}
				} else if index%4 == 2 {
					if !cellW && !cellNW && !cellSW {
						proposedElf.X--
						break
					}
				} else if index%4 == 3 {
					if !cellE && !cellNE && !cellSE {
						proposedElf.X++
						break
					}
				}
			}
		}

		proposedElves[elfI] = proposedElf
	}

	// Count elves moving to position
	nextGroundMap := make(map[string]int)
	for _, nextElf := range proposedElves {
		nextGroundMap[getGroundKey(nextElf.X, nextElf.Y)]++
	}

	// Validate
	nextElves := make([]*Elf, len(elves))
	for i := 0; i < len(nextElves); i++ {
		proposedElf := proposedElves[i]
		if nextGroundMap[getGroundKey(proposedElf.X, proposedElf.Y)] == 1 {
			nextElves[i] = proposedElf 

			if proposedElf.X != elves[i].X || proposedElf.Y != elves[i].Y {
				hasUpdate = true
			}
		} else {
			// Move elf back
			// TODO: Does moving back cascade? Can it prevent other elves from moving?
			nextElves[i] = elves[i]
		}
	}

	return nextElves, hasUpdate
}

func countGroundTiles(elves []*Elf) int {
	xMin := math.MaxInt32
	xMax := math.MinInt32

	yMin := math.MaxInt32
	yMax := math.MinInt32

	for _, elf := range elves {
		if elf.X > xMax {
			xMax = elf.X
		}

		if elf.X < xMin {
			xMin = elf.X
		}

		if elf.Y > yMax {
			yMax = elf.Y
		}

		if elf.Y < yMin {
			yMin = elf.Y
		}
	}

	area := (xMax - xMin + 1) * (yMax - yMin + 1)

	return area - len(elves)
}

func readInput(input string) []*Elf {
	mapRows := strings.Split(input, "\n")
	elves := []*Elf{}

	for rowI, row := range mapRows {
		for columnI, cell := range row {
			if cell == '#' {
				elves = append(elves, newElf(uuid.NewString(), columnI, rowI))
			}
		}
	}

	return elves
}

func PrintElves(name string, elves []*Elf) {
	xMin := math.MaxInt32
	xMax := math.MinInt32

	yMin := math.MaxInt32
	yMax := math.MinInt32

	groundMap := make(map[string]bool)
	for _, elf := range elves {
		if elf.X > xMax {
			xMax = elf.X
		}

		if elf.X < xMin {
			xMin = elf.X
		}

		if elf.Y > yMax {
			yMax = elf.Y
		}

		if elf.Y < yMin {
			yMin = elf.Y
		}

		groundMap[getGroundKey(elf.X, elf.Y)] = true
	}

	sb := strings.Builder{}
	for y := yMin - 1; y <= yMax+1; y++ {
		for x := xMin - 1; x <= xMax+1; x++ {
			if groundMap[fmt.Sprintf("%d,%d", x, y)] {
				sb.WriteRune('#')
			} else {
				sb.WriteRune('.')
			}
		}
		sb.WriteRune('\n')
	}

	log.Printf("Ground Map %s:\n%s", name, sb.String())
}

func getGroundKey(x int, y int) string {
	return fmt.Sprintf("%d,%d", x, y)

}
