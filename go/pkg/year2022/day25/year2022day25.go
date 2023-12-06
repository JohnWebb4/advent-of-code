package year2022day25

import (
	"fmt"
	"math"
	"strings"
)

func SumSnafuNumbers(input string) (string, error) {
	sumDecimal := 0

	snafuStrings := strings.Split(input, "\n")

	for _, snafuString := range snafuStrings {
		sumDecimal += GetDecimalFromSnafu(snafuString)
	}

	return GetSnafuFromDecimal(sumDecimal)
}

func GetSnafuFromDecimal(input int) (string, error) {
	snafuNumber := ""

	largetsPlaceValue := 1
	for largetsPlaceValue < input {
		largetsPlaceValue *= 5
	}

	// Convert to base 5
	remainder := input
	for placeValue := largetsPlaceValue; placeValue > 0; placeValue /= 5 {

		if remainder >= 0 {
			for digit := 2; digit >= 0; digit-- {
				value := digit * placeValue

				if value <= remainder {
					nextRemainder := remainder - value

					if placeValue > 1 && digit < 2 && nextRemainder >= int(math.Round(float64(placeValue)/2)) {
						// Increment one value
						prevDigit := digit + 1
						prevValue := prevDigit * placeValue

						snafuNumber = snafuNumber + fmt.Sprintf("%d", prevDigit)
						remainder -= prevValue
					} else {
						// Ommit leading zeros
						if placeValue != largetsPlaceValue || digit != 0 {
							snafuNumber = snafuNumber + fmt.Sprintf("%d", digit)
							remainder -= value
						}
					}

					break
				}
			}
		} else if remainder < 0 {
			for digit := -2; digit <= 0; digit++ {
				value := digit * placeValue

				if value >= remainder {
					nextRemainder := remainder - value

					if placeValue > 1 && digit > -2 && AbsInt(nextRemainder) >= int(math.Round(float64(placeValue)/2)) {
						prevDigit := digit - 1
						prevValue := prevDigit * placeValue

						if prevDigit == -2 {
							snafuNumber = snafuNumber + "="
						} else if prevDigit == -1 {
							snafuNumber = snafuNumber + "-"
						} else {
							snafuNumber = snafuNumber + "0"
						}

						remainder -= prevValue
					} else {
						if digit == -2 {
							snafuNumber = snafuNumber + "="
						} else if digit == -1 {
							snafuNumber = snafuNumber + "-"
						} else {
							snafuNumber = snafuNumber + "0"
						}

						remainder -= value
					}

					break
				}
			}
		}
	}

	if remainder != 0 {
		return "", fmt.Errorf("bad decimal to snafu conversion. %d => %s has remainder %d", input, snafuNumber, remainder)
	}

	return snafuNumber, nil
}

func GetDecimalFromSnafu(input string) int {
	base10Number := 0

	for i := 0; i < len(input); i++ {
		digit := 0
		if input[i] == '2' {
			digit = 2
		} else if input[i] == '1' {
			digit = 1
		} else if input[i] == '0' {
			digit = 0
		} else if input[i] == '-' {
			digit = -1
		} else if input[i] == '=' {
			digit = -2
		}

		digitValue := digit * int(math.Pow(5, float64(len(input)-i-1)))

		base10Number += digitValue
	}

	return base10Number
}
