package year2022day24

import (
	"fmt"
)

type Rectangle struct {
	X      int
	Y      int
	Width  int
	Height int
}

func NewRectangle(x int, y int, width int, height int) *Rectangle {
	return &Rectangle{
		X:      x,
		Y:      y,
		Width:  width,
		Height: height,
	}
}

func (rect Rectangle) String() string {
	return fmt.Sprintf("Rectangle{X:%d, Y:%d, Width:%d, Height:%d}", rect.X, rect.Y, rect.Width, rect.Height)
}

func (rect Rectangle) Contains(vect *Vector) bool {
	xMax := rect.X + rect.Width
	yMax := rect.Y + rect.Height

	return rect.X <= vect.X && rect.Y <= vect.Y && vect.X < xMax && vect.Y < yMax
}
