package year2022day13

import (
	"encoding/json"
	"fmt"
	"sort"
	"strings"

	"github.com/emirpasic/gods/stacks/arraystack"
)

func GetSumIndiciesPairs(input string) (int, error) {
	sumIndiciePairs := 0

	packetPairStrings := strings.Split(input, "\n\n")

	for pairI, packetPairString := range packetPairStrings {
		packetsString := strings.Split(packetPairString, "\n")

		var packetLeft []interface{}
		var packetRight []interface{}
		err := json.Unmarshal([]byte(packetsString[0]), &packetLeft)

		if err != nil {
			return 0, err
		}

		err = json.Unmarshal([]byte(packetsString[1]), &packetRight)

		if err != nil {
			return 0, err
		}

		if isValid, err := isValidPacketPair(packetLeft, packetRight); isValid && err == nil {
			sumIndiciePairs += pairI + 1
		} else if err != nil {
			return 0, err
		}
	}

	return sumIndiciePairs, nil
}

func GetProductOfSortedDecoderPackets(input string) (int, error) {
	packetStrings := strings.Split(strings.Join(strings.Split(input, "\n\n"), "\n"), "\n")

	// Add decoders
	packetStrings = append(packetStrings, "[[2]]")
	packetStrings = append(packetStrings, "[[6]]")

	// Sort packets
	sort.SliceStable(packetStrings, func(i, j int) bool {
		var packetI []interface{}
		var packetJ []interface{}
		json.Unmarshal([]byte(packetStrings[i]), &packetI)
		json.Unmarshal([]byte(packetStrings[j]), &packetJ)

		// False if i < j
		isValidPair, _ := isValidPacketPair(packetI, packetJ)

		return isValidPair
	})

	// Find decoders
	productOfDecoderPackets := 1
	for i := 0; i < len(packetStrings); i++ {
		if packetStrings[i] == "[[2]]" || packetStrings[i] == "[[6]]" {
			productOfDecoderPackets *= i + 1
		}
	}

	return productOfDecoderPackets, nil
}

func isValidPacketPair(packetLeft []interface{}, packetRight []interface{}) (bool, error) {
	stack := arraystack.New()
	stack.Push([]interface{}{packetLeft, packetRight})

	for !stack.Empty() {
		item, ok := stack.Pop()

		if ok {
			pair := item.([]interface{})
			leftValue := pair[0]
			rightValue := pair[1]

			leftFloat, isLeftFloat := leftValue.(float64)
			rightFloat, isRightFloat := rightValue.(float64)

			if isLeftFloat && isRightFloat {
				// If comparing two numbers
				if leftFloat < rightFloat {
					return true, nil
				} else if leftFloat > rightFloat {
					return false, nil
				}
				// Else same value. Continue checking
			} else {
				leftArray, isLeftArray := leftValue.([]interface{})
				rightArray, isRightArray := rightValue.([]interface{})

				if isLeftArray && isRightArray {
					// Compare each element in the array
					maxLength := max(len(leftArray), len(rightArray))
					for i := maxLength - 1; i >= 0; i-- {
						var nextLeftValue interface{}
						if i < len(leftArray) {
							nextLeftValue = leftArray[i]
						}

						var nextRightValue interface{}
						if i < len(rightArray) {
							nextRightValue = rightArray[i]
						}

						stack.Push([]interface{}{nextLeftValue, nextRightValue})
					}
				} else if isLeftArray && isRightFloat {
					// Convert right float to array
					stack.Push([]interface{}{leftArray, []interface{}{rightFloat}})
				} else if isLeftFloat && isRightArray {
					// Convert left float to array
					stack.Push([]interface{}{[]interface{}{leftFloat}, rightArray})
				} else if leftValue == nil && rightValue != nil {
					// Left ran out of items first
					return true, nil
				} else if leftValue != nil && rightValue == nil {
					// Right ran out of items first
					return false, nil
				}
			}

		} else {
			return false, fmt.Errorf("2022 day 23 isValidPacket Pair. Error reading queue")
		}
	}

	return false, nil
}
