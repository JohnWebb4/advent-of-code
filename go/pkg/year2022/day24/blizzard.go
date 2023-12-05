package year2022day24

import "fmt"

const UpDirection = '^'
const RightDirection = '>'
const DownDirection = 'v'
const LeftDirection = '<'

type Blizzard struct {
	X     int
	Y     int
	XDiff int
	YDiff int
}

func (blizz Blizzard) Copy() *Blizzard {
	return NewBlizzard(blizz.X, blizz.Y, blizz.XDiff, blizz.YDiff)
}

func NewBlizzard(x int, y int, xDiff int, yDiff int) *Blizzard {
	return &Blizzard{
		X:     x,
		Y:     y,
		XDiff: xDiff,
		YDiff: yDiff,
	}
}

func (blizz Blizzard) String() string {
	return fmt.Sprintf("Blizzard{X:%d, Y:%d, XDfiff:%d, YDiff:%d}", blizz.X, blizz.Y, blizz.XDiff, blizz.YDiff)
}
