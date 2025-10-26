package year2022day14

import (
	"fmt"
	"strings"
)

type SandMap struct {
	InnerSandMap map[string]Cell
	Bounds       *Rectangle
}

func NewSandMap(innerSandMap map[string]Cell, bounds *Rectangle) *SandMap {
	return &SandMap{
		InnerSandMap: innerSandMap,
		Bounds:       bounds,
	}
}

func (sandMap SandMap) String() string {
	sb := strings.Builder{}

	for y := 0; y <= sandMap.Bounds.Height+sandMap.Bounds.Y+10; y++ {
		for x := sandMap.Bounds.X - 1; x <= sandMap.Bounds.Width+sandMap.Bounds.X+1; x++ {
			cell := sandMap.InnerSandMap[getSandMapKey(x, y)]

			if x == 500 && y == 0 {
				sb.WriteRune('+')
			} else if cell == RockCell {
				sb.WriteRune('#')
			} else if cell == SandCell {
				sb.WriteRune('o')
			} else {
				sb.WriteRune('.')
			}
		}

		sb.WriteRune('\n')
	}

	return sb.String()
}

func (sandMap SandMap) GetCell(x int, y int) Cell {
	return sandMap.InnerSandMap[getSandMapKey(x, y)]
}

func (sandMap SandMap) SetCell(x int, y int, cell Cell) {
	sandMap.InnerSandMap[getSandMapKey(x, y)] = cell
}

func getSandMapKey(x int, y int) string {
	return fmt.Sprintf("x:%d,y:%d", x, y)
}
