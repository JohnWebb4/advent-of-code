package year2022day12

type Heap []*Path

func (heap Heap) Len() int           { return len(heap) }
func (heap Heap) Less(i, j int) bool { return heap[i].cost < heap[j].cost }
func (h Heap) Swap(i, j int)         { h[i], h[j] = h[j], h[i] }

func (heap *Heap) Push(path any) {
	*heap = append(*heap, path.(*Path))
}

func (heap *Heap) Pop() any {
	old := *heap
	n := len(old)
	x := old[n-1]
	*heap = old[0 : n-1]

	return x
}
