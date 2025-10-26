package year2022day07

type IntHeap []int

func (intHeap IntHeap) Len() int {
	return len(intHeap)
}

func (intHeap IntHeap) Less(i int, j int) bool {
	return intHeap[i] < intHeap[j]
}

func (intHeap IntHeap) Swap(i int, j int) {
	intHeap[i], intHeap[j] = intHeap[j], intHeap[i]
}

func (intHeap *IntHeap) Push(x interface{}) {
	*intHeap = append(*intHeap, x.(int))
}

func (intHeap *IntHeap) Pop() interface{} {
	length := len(*intHeap)
	element := (*intHeap)[length-1]
	*intHeap = (*intHeap)[:length-1]

	return element
}
