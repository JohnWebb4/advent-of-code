package year2022day06

func DetectPackage(buffer string, messageLength int) int {
	queue := new(Queue)

	for i, r := range buffer {
		if len(*queue) >= messageLength {
			queue.Pop()
		}

		queue.Push(r)

		if len(*queue) == messageLength && !hasRepeat(queue) {
			return i + 1
		}
	}

	return -1
}

func hasRepeat(queue *Queue) bool {
	charSet := make(map[rune]bool)

	for _, r := range *queue {
		if charSet[r] {
			return true
		}

		charSet[r] = true
	}

	return false
}
