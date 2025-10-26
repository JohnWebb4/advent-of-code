package year2022day07

import (
	"fmt"
	"strings"
)

type File struct {
	name string
	size int
}

func NewFile(name string, size int) *File {
	return &File{
		name: name,
		size: size,
	}
}

func (file File) PrintFile(depth int) {
	fmt.Printf("%s- %s (file, size=%d)\n", strings.Repeat("  ", depth), file.name, file.size)
}
