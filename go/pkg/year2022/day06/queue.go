package year2022day06

import "fmt"

type Queue []rune

func (queue Queue) IsEmpty() bool {
	return len(queue) == 0
}

func (queue *Queue) Push(r rune) {
	*queue = append(*queue, r)
}

func (queue *Queue) Pop() (*rune, error) {
	if queue.IsEmpty() {
		return nil, fmt.Errorf("Queue is empty")
	}

	element := (*queue)[0]
	*queue = (*queue)[1:]

	return &element, nil
}
