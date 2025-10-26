package year2022day01

type CalorieHeap []int

func (ch CalorieHeap) Len() int           { return len(ch) }
func (ch CalorieHeap) Less(i, j int) bool { return ch[i] > ch[j] }
func (ch CalorieHeap) Swap(i, j int)      { ch[i], ch[j] = ch[j], ch[i] }

func (ch *CalorieHeap) Push(x any) {
	*ch = append(*ch, x.(int))
}

func (ch *CalorieHeap) Pop() any {
	old := *ch
	n := len(old)
	x := old[n-1]
	*ch = old[0 : n-1]
	return x
}
