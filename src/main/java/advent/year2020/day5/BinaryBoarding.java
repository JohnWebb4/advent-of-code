/* Licensed under Apache-2.0 */
package advent.year2020.day5;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class BinaryBoarding {
    public static int getMissingSeatId(String seatInput) {
        return getMissingSeatId(seatInput.split("\n"));
    }

    public static int getMissingSeatId(String[] seats) {
        AtomicBoolean foundValue = new AtomicBoolean(false);

        return Arrays.stream(seats).mapToInt(BinaryBoarding::getSeatId).sorted().reduce((memo, value) -> {
            if (foundValue.get()) {
                return memo;
            }

            if (value == memo + 2) {
                foundValue.set(true);
                return memo + 1;
            }

            return value;
        }).getAsInt();
    }

    public static int getHighestSeatId(String seatInput) {
        return getHighestSeatId(seatInput.split("\n"));
    }

    public static int getHighestSeatId(String[] seats) {
        int highestId = -1;

        for (String seat : seats) {
            int seatId = getSeatId(seat);

            if (seatId > highestId) {
                highestId = seatId;
            }
        }

        return highestId;
    }

    public static int getSeatId(String seat) {
        int row = 0;
        double rowStep = 64;

        for (int i = 0; i < 7; i++) {
            char key = seat.charAt(i);

            if (key == 'B') {
                row += rowStep;
            }

            rowStep = Math.ceil(rowStep / 2);
        }

        int column = 0;
        double columnStep = 4;

        for (int i = 7; i < 10; i++) {
            char key = seat.charAt(i);

            if (key == 'R') {
                column += columnStep;
            }

            columnStep = Math.ceil(columnStep / 2);
        }

        return row * 8 + column;
    }
}
