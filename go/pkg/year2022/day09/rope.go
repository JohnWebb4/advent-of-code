package year2022day09

import (
	"errors"
)

type Rope []Vector

func MakeRope(size int) Rope {
	return make(Rope, size)
}

func (rope Rope) MoveRope(move Vector) (Rope, error) {
	if len(rope) > 0 {
		rope[0] = rope[0].Add(move)

		for i := 1; i < len(rope); i++ {
			rope[i] = updateKnot(rope[i-1], rope[i])
		}

		return rope, nil
	} else {
		return rope, errors.New("Rope is empty")
	}
}

func updateKnot(head Vector, tail Vector) Vector {
	difference := head.Subtract(tail)

	absX := absInt(difference.x)
	absY := absInt(difference.y)

	length := difference.Length()

	if length > 1 && (absX != 1 || absY != 1) {
		xDiffNorm := 0
		if absX >= 1 {
			xDiffNorm = difference.x / absX
		}

		yDiffNorm := 0
		if absY >= 1 {
			yDiffNorm = difference.y / absY
		}

		return NewVector(tail.x+xDiffNorm, tail.y+yDiffNorm)
	} else {
		return tail
	}

}

func absInt(x int) int {
	if x >= 0 {
		return x
	} else {
		return -x
	}
}
