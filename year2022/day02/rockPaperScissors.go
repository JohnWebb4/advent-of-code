package year2022day02

import (
	"strings"
)

type RockPaperScissors string

const (
	Rock     RockPaperScissors = "Rock"
	Paper    RockPaperScissors = "Paper"
	Scissors RockPaperScissors = "Scissors"
)

type WinDrawLose string

const (
	Win  WinDrawLose = "Win"
	Draw WinDrawLose = "Draw"
	Lose WinDrawLose = "Lose"
)

type OpponentChoiceMap map[string]RockPaperScissors
type UserChoiceMap map[string]RockPaperScissors
type UserStrategyMap map[string]WinDrawLose

var opponentChoiceMap = make(OpponentChoiceMap)
var userChoiceMap = make(UserChoiceMap)
var userStrategyMap = make(UserStrategyMap)

func GetScoreFromStrategy(strategy string) int {
	setup()

	rounds := strings.Split(strategy, "\n")

	totalScore := 0
	for _, round := range rounds {
		choices := strings.Split(round, " ")
		opponentChoice := opponentChoiceMap[choices[0]]
		yourChoice := userChoiceMap[choices[1]]

		if yourChoice == Rock {
			totalScore += 1
		} else if yourChoice == Paper {
			totalScore += 2
		} else if yourChoice == Scissors {
			totalScore += 3
		}

		result := compareRockPaperScissors(yourChoice, opponentChoice)

		if result == 1 {
			totalScore += 6
		} else if result == 0 {
			totalScore += 3
		}
	}

	return totalScore
}

func GetScoreFromWinLoseStrategy(strategy string) int {
	setup()

	rounds := strings.Split(strategy, "\n")

	totalScore := 0
	for _, round := range rounds {
		choices := strings.Split(round, " ")
		opponentChoice := opponentChoiceMap[choices[0]]
		yourStrategy := userStrategyMap[choices[1]]

		if yourStrategy == Win {
			totalScore += 6
		} else if yourStrategy == Draw {
			totalScore += 3
		} else if yourStrategy == Lose {
			totalScore += 0
		}

		yourChoice := getYourChoiceFromStrategy(yourStrategy, opponentChoice)

		if yourChoice == Rock {
			totalScore += 1
		} else if yourChoice == Paper {
			totalScore += 2
		} else if yourChoice == Scissors {
			totalScore += 3
		}
	}

	return totalScore
}

func setup() {
	opponentChoiceMap["A"] = Rock
	opponentChoiceMap["B"] = Paper
	opponentChoiceMap["C"] = Scissors

	userChoiceMap["X"] = Rock
	userChoiceMap["Y"] = Paper
	userChoiceMap["Z"] = Scissors

	userStrategyMap["X"] = Lose
	userStrategyMap["Y"] = Draw
	userStrategyMap["Z"] = Win
}

func compareRockPaperScissors(yourChoice RockPaperScissors, opponentChoice RockPaperScissors) int {
	if yourChoice == opponentChoice {
		return 0
	}

	if yourChoice == Rock {
		if opponentChoice == Paper {
			return -1
		} else if opponentChoice == Scissors {
			return 1
		}
	} else if yourChoice == Paper {
		if opponentChoice == Rock {
			return 1
		} else if opponentChoice == Scissors {
			return -1
		}
	} else if yourChoice == Scissors {
		if opponentChoice == Rock {
			return -1
		} else if opponentChoice == Paper {
			return 1
		}
	}

	return -1
}

func getYourChoiceFromStrategy(yourStrategy WinDrawLose, opponentChoice RockPaperScissors) RockPaperScissors {
	if yourStrategy == Draw {
		return opponentChoice
	}

	if opponentChoice == Rock {
		if yourStrategy == Win {
			return Paper
		} else if yourStrategy == Lose {
			return Scissors
		}
	} else if opponentChoice == Paper {
		if yourStrategy == Win {
			return Scissors
		} else if yourStrategy == Lose {
			return Rock
		}
	} else if opponentChoice == Scissors {
		if yourStrategy == Win {
			return Rock
		} else if yourStrategy == Lose {
			return Paper
		}
	}

	return Scissors
}
