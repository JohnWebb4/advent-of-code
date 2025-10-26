package year2022day14

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
	return fmt.Sprintf("Rectangle{ x:%d, y:%d, width:%d, height:%d}", rect.X, rect.Y, rect.Width, rect.Height)
}

func (rect Rectangle) Contains(x int, y int) bool {
	return rect.X <= x && x <= rect.Width+rect.X && rect.Y <= y && y <= rect.Height+rect.Y
}
