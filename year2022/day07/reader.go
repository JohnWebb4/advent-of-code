package year2022day07

import (
	"fmt"
	"strings"
)

type Reader struct {
	lines []string
	index int
}

const EOF = "EOF"

func NewReader(input string) *Reader {
	lines := strings.Split(input, "\n")

	return &Reader{
		lines: lines,
		index: 0,
	}
}

func (reader *Reader) ReadCommand() (*string, error) {
	if reader.index < len(reader.lines) {
		lines := []string{reader.lines[reader.index]}

		newIndex := reader.index + 1
		for newIndex < len(reader.lines) && !strings.HasPrefix(reader.lines[newIndex], "$") {
			lines = append(lines, reader.lines[newIndex])
			newIndex++
		}

		line := strings.Join(lines, "\n")
		reader.index = newIndex
		return &line, nil
	} else {
		return nil, fmt.Errorf(EOF)
	}
}
