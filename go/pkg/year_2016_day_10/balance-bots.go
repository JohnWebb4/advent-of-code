package advent_of_code

import (
	"math"
	"regexp"
	"strconv"
	"strings"
)

type BotInstruction struct {
	lowKey string
	highKey string 
}

// Create map of robots. ID is key.
// Iterate instructions
// // Assign chips to robots
// // // If robot does not exist, create it
// Create new map of robots
// Loop forever
// // Iterate robots
// // // If robot is comparing desired chips, return ID
// // // Otherwise, run against instructions
// // // // Assign result to new map, create robots as necessary
// // Assign new map to old map
func BalanceBots(instructions string, comparedChips []int) int {
	chipState, mapIdToInstruction := parseInstructionsToIterationState(instructions)

	for i := 0; i < 100; i++{
		queuedChips := make(map[string][]int)

		for key,bot := range chipState {
			if strings.HasPrefix(key, "bot") && len(bot) >= 2 {
				chip1 := bot[0]
				chip2 := bot[1]
				bot = bot[2:]

				instruction := mapIdToInstruction[key]

				minChip := int(math.Min(float64(chip1), float64(chip2)))
				maxChip := int(math.Max(float64(chip1), float64(chip2)))

				if minChip == comparedChips[0] && maxChip == comparedChips[1] {
					id,_ := strconv.Atoi(strings.Split(key, " ")[1])
					return id
				}

				lowChipArray := getValueOrDefault(instruction.lowKey, queuedChips)
				lowChipArray = append(lowChipArray, minChip)
				queuedChips[instruction.lowKey] = lowChipArray

				highChipArray := getValueOrDefault(instruction.highKey, queuedChips)
				highChipArray = append(highChipArray, maxChip)
				queuedChips[instruction.highKey] = highChipArray

				chipState[key] = bot
			}
		}

		for key,chips := range queuedChips {
			chipState[key] = append(chipState[key], chips...)
		}
	}

	return 0
}

func parseInstructionsToIterationState(instructions string) (map[string][]int, map[string]BotInstruction) {
	instructionArray := strings.Split(instructions, "\n")

	mapIdToChipArray := make(map[string][]int)
	mapIdToBotInstructions := make(map[string]BotInstruction)

	regexInstructionValue, _ := regexp.Compile("value (\\d*) goes to (.*)")
	regexInstructionBot, _ := regexp.Compile("(.*) gives low to (.*) and high to (.*)")

	for _, instruction := range instructionArray {
		if regexInstructionValue.MatchString(instruction) {
			regexpResult := regexInstructionValue.FindStringSubmatch(instruction)
			chipId,_ := strconv.Atoi(regexpResult[1])
			key := regexpResult[2]

			botChips := getValueOrDefault(key, mapIdToChipArray)

			botChips = append(botChips, chipId)
			mapIdToChipArray[key] = botChips
		} else if regexInstructionBot.MatchString(instruction) {
			regexpResult := regexInstructionBot.FindStringSubmatch(instruction)
			botKey := regexpResult[1]
			lowKey := regexpResult[2]
			highKey := regexpResult[3]

			mapIdToBotInstructions[botKey] = BotInstruction{
				lowKey: lowKey,
				highKey: highKey,
			}
		}
	}

	return mapIdToChipArray, mapIdToBotInstructions
}

func getValueOrDefault(key string, mapIdToArray map[string][]int) []int {
	if chipArray, ok := mapIdToArray[key]; ok {
		return chipArray
	} else {
		return []int{}
	}
}