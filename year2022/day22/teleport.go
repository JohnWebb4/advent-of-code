package year2022day22

import "fmt"

type Teleport struct {
	X          int
	Y          int
	TurnAmount int
}

func newTeleport(x int, y int, turnAmount int) *Teleport {
	return &Teleport{
		X:          x,
		Y:          y,
		TurnAmount: turnAmount,
	}
}

func (telp Teleport) String() string {
	return fmt.Sprintf("Teleport{x:%d, y:%d, turn:%d}", telp.X, telp.Y, telp.TurnAmount)
}
