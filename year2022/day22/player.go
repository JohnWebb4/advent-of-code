package year2022day22

import "fmt"

type Player struct {
	X         int
	Y         int
	Direction Direction
}

func NewPlayer(x int, y int, direction Direction) *Player {
	return &Player{
		X:         x,
		Y:         y,
		Direction: direction,
	}
}

func (player Player) String() string {
	return fmt.Sprintf("Player{x:%d, y:%d, direction:%s}", player.X, player.Y, player.Direction)
}
