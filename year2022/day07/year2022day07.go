package year2022day07

import (
	"container/heap"
	"fmt"
	"strconv"
	"strings"
)

func FindSumOfSmallDirectories(input string) int {
	consoleReader := NewReader(input)

	rootFolder := &Folder{
		name: "/",
	}
	currentFolder := rootFolder

	for commandAndResult, err := consoleReader.ReadCommand(); err == nil; commandAndResult, err = consoleReader.ReadCommand() {
		if strings.HasPrefix(strings.Trim(*commandAndResult, " "), "$") {
			currentFolder, err = handleCommand(*commandAndResult, currentFolder, rootFolder)

			if err != nil {
				fmt.Printf("Failed to run command %v", err)
			}
		} else {
			fmt.Printf("Unrecognized line %s\n", *commandAndResult)
		}
	}

	sumSmallFolderSizes := 0
	folderStack := new(FolderStack)
	folderStack.Push(*NewFolderSearch(rootFolder, true))

	for !folderStack.IsEmpty() {
		folderSearch := folderStack.Pop()

		if folderSearch.IsRecursive {
			folderStack.Push(*NewFolderSearch(folderSearch.Folder, false))

			for _, childFolder := range folderSearch.Folder.folders {
				folderStack.Push(*NewFolderSearch(childFolder, len(childFolder.folders) > 0))
			}
		} else {
			folderSize := 0

			for _, file := range folderSearch.Folder.files {
				folderSize += file.size
			}

			for _, childFolder := range folderSearch.Folder.folders {
				folderSize += childFolder.size
			}

			folderSearch.Folder.size = folderSize

			if folderSize < 100000 {
				sumSmallFolderSizes += folderSize
			}
		}
	}

	return sumSmallFolderSizes
}

func FindSizeSmallestDirectoryForFreeSpace(input string, diskSize int, spaceNeeded int) int {
	consoleReader := NewReader(input)

	rootFolder := &Folder{
		name: "/",
	}
	currentFolder := rootFolder

	for commandAndResult, err := consoleReader.ReadCommand(); err == nil; commandAndResult, err = consoleReader.ReadCommand() {
		if strings.HasPrefix(strings.Trim(*commandAndResult, " "), "$") {
			currentFolder, err = handleCommand(*commandAndResult, currentFolder, rootFolder)

			if err != nil {
				fmt.Printf("Failed to run command %v", err)
			}
		} else {
			fmt.Printf("Unrecognized line %s\n", *commandAndResult)
		}
	}

	folderStack := new(FolderStack)
	folderStack.Push(*NewFolderSearch(rootFolder, true))
	folderSizeHeap := &IntHeap{}

	for !folderStack.IsEmpty() {
		folderSearch := folderStack.Pop()

		if folderSearch.IsRecursive {
			folderStack.Push(*NewFolderSearch(folderSearch.Folder, false))

			for _, childFolder := range folderSearch.Folder.folders {
				folderStack.Push(*NewFolderSearch(childFolder, len(childFolder.folders) > 0))
			}
		} else {
			folderSize := 0

			for _, file := range folderSearch.Folder.files {
				folderSize += file.size
			}

			for _, childFolder := range folderSearch.Folder.folders {
				folderSize += childFolder.size
			}

			folderSearch.Folder.size = folderSize

			heap.Push(folderSizeHeap, folderSize)
		}
	}

	memoryUsed := rootFolder.size
	memoryFree := diskSize - memoryUsed
	minFolderSizeToDelete := spaceNeeded - memoryFree

	for len(*folderSizeHeap) > 0 {
		folderSize := heap.Pop(folderSizeHeap).(int)

		if folderSize >= minFolderSizeToDelete {
			return folderSize
		}
	}

	return 0
}

func handleCommand(commandAndResult string, currentFolder *Folder, rootFolder *Folder) (*Folder, error) {
	if strings.HasPrefix(commandAndResult, "$ cd") {
		return handleCommandCd(commandAndResult, currentFolder, rootFolder)
	} else if strings.HasPrefix(commandAndResult, "$ ls") {
		return handleCommandLs(commandAndResult, currentFolder, rootFolder)
	}

	return nil, fmt.Errorf("unknown command %s", commandAndResult)
}

func handleCommandCd(commandAndResult string, currentFolder *Folder, rootFolder *Folder) (*Folder, error) {
	lines := strings.Split(commandAndResult, "\n")
	commandLine := strings.Split(lines[0], " ")
	argument1 := commandLine[2]

	if argument1 == "/" {
		return rootFolder, nil
	} else if argument1 == ".." {
		return currentFolder.parent, nil
	} else {
		for _, child := range currentFolder.folders {
			if child.name == argument1 {
				return child, nil
			}
		}
	}

	return nil, fmt.Errorf("failed to find directory %s", lines[0])
}

func handleCommandLs(commandAndResult string, currentFolder *Folder, rootFolder *Folder) (*Folder, error) {
	lines := strings.Split(commandAndResult, "\n")

	for i := 1; i < len(lines); i++ {
		line := lines[i]

		parts := strings.Split(line, " ")

		if parts[0] == "dir" {
			name := parts[1]

			folder := NewFolder(name, currentFolder)

			currentFolder.folders = append(currentFolder.folders, folder)
		} else {
			size, err := strconv.Atoi(parts[0])

			if err != nil {
				return currentFolder, err
			}

			name := parts[1]

			file := NewFile(name, size)

			currentFolder.files = append(currentFolder.files, file)
		}
	}

	return currentFolder, nil
}
