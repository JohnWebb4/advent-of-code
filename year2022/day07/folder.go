package year2022day07

import (
	"fmt"
	"strings"
)

type Folder struct {
	name    string
	folders []*Folder
	files   []*File
	parent  *Folder
	size    int
}

func NewFolder(name string, parent *Folder) *Folder {
	return &Folder{
		name:    name,
		folders: []*Folder{},
		files:   []*File{},
		parent:  parent,
		size:    0,
	}
}

func (folder Folder) PrintFolderRecursive() {
	queue := [][]interface{}{}

	queue = append(queue, []interface{}{folder, 0})

	for len(queue) > 0 {
		length := len(queue)
		element := queue[length-1]
		queue = queue[:length-1]

		currentFolder := element[0].(Folder)
		depth := element[1].(int)

		currentFolder.PrintFolder(depth)

		for _, child := range currentFolder.folders {
			queue = append(queue, []interface{}{*child, depth + 1})
		}

		for _, file := range currentFolder.files {
			file.PrintFile(depth + 1)
		}
	}
}

func (folder Folder) PrintFolder(depth int) {
	fmt.Printf("%s- %s (dir, size=%d)\n", strings.Repeat("  ", depth), folder.name, folder.size)
}
