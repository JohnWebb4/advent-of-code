package year2022day24

import (
	"math"
	"strings"
)

type Map struct {
	Blizzards []*Blizzard
	Walls     []*Vector
	Bounds    *Rectangle
}

func NewMap(blizzards []*Blizzard, walls []*Vector) *Map {
	bounds := getBounds(walls)

	return &Map{
		Blizzards: blizzards,
		Walls:     walls,
		Bounds:    bounds,
	}
}

func (aMap Map) String() string {
	sb := strings.Builder{}

	cells := make([][]rune, aMap.Bounds.Height+1)
	for y := 0; y < len(cells); y++ {
		cells[y] = make([]rune, aMap.Bounds.Width+1)
	}

	for _, wall := range aMap.Walls {
		cells[wall.Y][wall.X] = '#'
	}

	for _, blizzard := range aMap.Blizzards {
		if cells[blizzard.Y][blizzard.X] != 0 {
			cells[blizzard.Y][blizzard.X] = '2'
		} else if blizzard.XDiff == 0 && blizzard.YDiff == -1 {
			cells[blizzard.Y][blizzard.X] = UpDirection
		} else if blizzard.XDiff == 1 && blizzard.YDiff == 0 {
			cells[blizzard.Y][blizzard.X] = RightDirection
		} else if blizzard.XDiff == 0 && blizzard.YDiff == 1 {
			cells[blizzard.Y][blizzard.X] = DownDirection
		} else if blizzard.XDiff == -1 && blizzard.YDiff == 0 {
			cells[blizzard.Y][blizzard.X] = LeftDirection
		} else {
			cells[blizzard.Y][blizzard.X] = '!'
		}
	}

	for y := 0; y < aMap.Bounds.Height; y++ {
		for x := 0; x < aMap.Bounds.Width; x++ {
			if cells[y][x] == 0 {
				sb.WriteRune('.')
			} else {
				sb.WriteRune(cells[y][x])
			}
		}
		sb.WriteRune('\n')
	}

	return sb.String()
}

func getBounds(walls []*Vector) *Rectangle {
	xMin := math.MaxInt32
	xMax := math.MinInt32

	yMin := math.MaxInt32
	yMax := math.MinInt32

	for _, wall := range walls {
		if wall.X < xMin {
			xMin = wall.X
		}

		if wall.X > xMax {
			xMax = wall.X
		}

		if wall.Y < yMin {
			yMin = wall.Y
		}

		if wall.Y > yMax {
			yMax = wall.Y
		}
	}

	width := xMax - xMin + 1
	height := yMax - yMin + 1

	return NewRectangle(xMin, yMin, width, height)
}
