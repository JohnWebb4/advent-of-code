package year2022day22

import (
	"fmt"
	"strings"
)

type MonkeyMap struct {
	innerMap    map[string]Cell
	teleportMap map[string]*Teleport

	xMax int
	xMin int
	yMax int
	yMin int
}

func NewMonkeyMap() *MonkeyMap {
	return &MonkeyMap{
		innerMap: make(map[string]Cell),
	}
}

func (monkeyMap MonkeyMap) String() string {
	sb := strings.Builder{}

	for y := monkeyMap.yMin; y <= monkeyMap.yMax; y++ {
		for x := monkeyMap.xMin; x <= monkeyMap.xMax; x++ {
			cell := monkeyMap.innerMap[getMapKey(x, y)]

			if cell == VoidCell {
				sb.WriteRune(' ')
			} else {
				sb.WriteRune(rune(cell))
			}

		}

		sb.WriteRune('\n')
	}

	return sb.String()
}

func (monkeyMap *MonkeyMap) SetCell(x int, y int, cell Cell) {
	if x < monkeyMap.xMin {
		monkeyMap.xMin = x
	}
	if x > monkeyMap.xMax {
		monkeyMap.xMax = x
	}
	if y < monkeyMap.yMin {
		monkeyMap.yMin = y
	}
	if y > monkeyMap.yMax {
		monkeyMap.yMax = y
	}

	monkeyMap.innerMap[getMapKey(x, y)] = cell
}

func (monkeyMap MonkeyMap) GetCell(x int, y int) Cell {
	return monkeyMap.innerMap[getMapKey(x, y)]
}

func getMapKey(x int, y int) string {
	return fmt.Sprintf("x%d:y%d", x, y)
}

func getTeleportKey(x int, y int, direction Direction) string {
	return fmt.Sprintf("x%d,y%d,d:%s", x, y, direction)
}
