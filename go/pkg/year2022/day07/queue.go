package year2022day07

type FolderSearch struct {
	Folder      *Folder
	IsRecursive bool
}

func NewFolderSearch(folder *Folder, isRecursive bool) *FolderSearch {
	return &FolderSearch{
		Folder:      folder,
		IsRecursive: isRecursive,
	}

}

type FolderStack []FolderSearch

func (folderStack FolderStack) IsEmpty() bool {
	return len(folderStack) == 0
}

func (folderStack *FolderStack) Push(folderSearch FolderSearch) {
	*folderStack = append(*folderStack, folderSearch)
}

func (folderStack *FolderStack) Pop() FolderSearch {
	length := len(*folderStack)
	element := (*folderStack)[length-1]

	*folderStack = (*folderStack)[:length-1]

	return element
}
