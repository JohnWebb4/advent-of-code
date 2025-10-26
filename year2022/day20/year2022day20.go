package year2022day20

import (
	"fmt"
	"strconv"
	"strings"
)

func GetSumGrooveCoordinates(input string) (int, error) {
	encryptedFile, err := readInput(input)

	if err != nil {
		return 0, err
	}

	mixedFile := MixFile(encryptedFile, len(encryptedFile), 1)

	zeroIndex := 0
	for mixedFileI := 0; mixedFileI < len(mixedFile); mixedFileI++ {
		if mixedFile[mixedFileI] == 0 {
			zeroIndex = mixedFileI
			break
		}
	}

	grooveCoordinateSum := 0
	for i := 0; i <= 3000; i++ {
		if i == 1000 {
			grooveCoordinateSum += mixedFile[(zeroIndex+i)%len(mixedFile)]
		} else if i == 2000 {
			grooveCoordinateSum += mixedFile[(zeroIndex+i)%len(mixedFile)]
		} else if i == 3000 {
			grooveCoordinateSum += mixedFile[(zeroIndex+i)%len(mixedFile)]
		}
	}

	return grooveCoordinateSum, nil
}

func GetDecryptedSumGrooveCoordinates(input string) (int, error) {
	encryptedFile, err := readInput(input)

	if err != nil {
		return 0, err
	}

	mixedFile := make([]int, len(encryptedFile))
	for encryptedI, encryptedValue := range encryptedFile {
		mixedFile[encryptedI] = 811589153 * encryptedValue
	}

	mixedFile = MixFile(mixedFile, len(encryptedFile), 10)

	zeroIndex := 0
	for mixedFileI := 0; mixedFileI < len(mixedFile); mixedFileI++ {
		if mixedFile[mixedFileI] == 0 {
			zeroIndex = mixedFileI
			break
		}
	}

	grooveCoordinateSum := 0
	for i := 0; i <= 3000; i++ {
		if i == 1000 {
			grooveCoordinateSum += mixedFile[(zeroIndex+i)%len(mixedFile)]
		} else if i == 2000 {
			grooveCoordinateSum += mixedFile[(zeroIndex+i)%len(mixedFile)]
		} else if i == 3000 {
			grooveCoordinateSum += mixedFile[(zeroIndex+i)%len(mixedFile)]
		}
	}

	return grooveCoordinateSum, nil
}

func DecryptAndMixFileFromInput(input string, numMixes int) (string, error) {
	encryptedFile, err := readInput(input)

	if err != nil {
		return "", err
	}

	mixedFile := make([]int, len(encryptedFile))
	for encryptedI, encryptedValue := range encryptedFile {
		mixedFile[encryptedI] = 811589153 * encryptedValue
	}

	mixedFile = MixFile(mixedFile, len(encryptedFile), numMixes)

	mixedFileString := make([]string, len(mixedFile))
	for valueI, value := range mixedFile {
		mixedFileString[valueI] = fmt.Sprintf("%d", value)
	}

	return strings.Join(mixedFileString, ", "), nil

}

func MixFileFromInput(input string, maxIterations int) (string, error) {
	encryptedFile, err := readInput(input)

	if err != nil {
		return "", err
	}

	mixedFile := MixFile(encryptedFile, maxIterations, 1)
	mixedFileString := make([]string, len(mixedFile))
	for valueI, value := range mixedFile {
		mixedFileString[valueI] = fmt.Sprintf("%d", value)
	}

	return strings.Join(mixedFileString, ", "), nil
}

func MixFile(encryptedFile []int, maxIterations int, numMixes int) []int {
	encryptedItems := make([]*Item, len(encryptedFile))
	mixedItems := make([]*Item, len(encryptedFile))

	for valueI, value := range encryptedFile {
		item := &Item{Value: value}
		mixedItems[valueI] = item
		encryptedItems[valueI] = item
	}

	for mixI := 0; mixI < numMixes; mixI++ {

		for itemI := 0; itemI < len(encryptedItems) && itemI < maxIterations; itemI++ {
			item := encryptedItems[itemI]
			for mixedItemI, mixedItem := range mixedItems {
				if item == mixedItem {
					if mixedItem.Value < 0 {
						currentIndex := mixedItemI

						numSwaps := (-mixedItem.Value) % (len(mixedItems) - 1)
						for i := 0; i > -numSwaps; i-- {
							nextIndex := (currentIndex - 1) % len(mixedItems)
							for nextIndex < 0 {
								nextIndex += len(mixedItems)
							}

							mixedItems[currentIndex] = mixedItems[nextIndex]
							mixedItems[nextIndex] = item

							currentIndex = nextIndex

							if nextIndex == 0 {
								startValue := mixedItems[0]
								// If swapping with last element shift everything by one
								for j := 1; j < len(mixedItems); j++ {
									mixedItems[j-1] = mixedItems[j]
								}

								mixedItems[len(mixedItems)-1] = startValue

								currentIndex = len(mixedItems) - 1
							}
						}
					} else {
						currentIndex := mixedItemI

						numSwaps := (mixedItem.Value) % (len(mixedItems) - 1)
						for i := 0; i < numSwaps; i++ {
							nextIndex := (currentIndex + 1) % len(mixedItems)

							mixedItems[currentIndex] = mixedItems[nextIndex]
							mixedItems[nextIndex] = item

							currentIndex = nextIndex

							if nextIndex == 0 {
								startValue := mixedItems[len(mixedItems)-1]
								// If swapping with last element shift everything by one
								for j := len(mixedItems) - 2; j >= 0; j-- {
									mixedItems[j+1] = mixedItems[j]
								}

								mixedItems[0] = startValue

								currentIndex = 1
							}
						}
					}

					break
				}
			}
		}
	}

	mixedFile := make([]int, len(mixedItems))
	for i, item := range mixedItems {
		mixedFile[i] = item.Value
	}

	return mixedFile
}

func readInput(input string) ([]int, error) {
	lines := strings.Split(input, "\n")
	encryptedFile := make([]int, len(lines))

	for lineI, line := range lines {
		value, err := strconv.Atoi(line)

		if err != nil {
			return nil, err
		}

		encryptedFile[lineI] = value
	}

	return encryptedFile, nil
}
