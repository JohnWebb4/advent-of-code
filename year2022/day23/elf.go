package year2022day23

import "fmt"

type Elf struct {
	Id string
	X int
	Y int
}

func newElf(id string, x int, y int) *Elf {
	return &Elf {
		Id: id,
		X: x,
		Y: y,
	}
}

func (elf Elf) String() string {
	return fmt.Sprintf("Elf %s x:%d,y:%d", elf.Id, elf.X, elf.Y)
}

func (elf Elf) Copy() *Elf {
	return newElf(elf.Id, elf.X, elf.Y)
}