/* Licensed under Apache-2.0 */
package advent.year2020.day16;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

public class TicketTranslationTest {
    public static String test1;
    public static String test2;
    public static String input;

    @BeforeClass
    public static void initialize() {
        test1 = "class: 1-3 or 5-7\n" +
                "row: 6-11 or 33-44\n" +
                "seat: 13-40 or 45-50\n" +
                "\n" +
                "your ticket:\n" +
                "7,1,14\n" +
                "\n" +
                "nearby tickets:\n" +
                "7,3,47\n" +
                "40,4,50\n" +
                "55,2,20\n" +
                "38,6,12";

        test2 = "class: 0-1 or 4-19\n" +
                "row: 0-5 or 8-19\n" +
                "seat: 0-13 or 16-19\n" +
                "\n" +
                "your ticket:\n" +
                "11,12,13\n" +
                "\n" +
                "nearby tickets:\n" +
                "3,9,18\n" +
                "15,1,5\n" +
                "5,14,9";

        try {
            input = new String(Files.readAllBytes(Paths.get("./src/test/java/advent/year2020/day16/input.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getErrorRate_71() {
        assertEquals(71, TicketTranslation.getErrorRate(test1));
    }

    @Test
    public void getErrorRate_28882() {
        assertEquals(28882, TicketTranslation.getErrorRate(input));
    }

    @Test
    public void getValidTicketFieldsAndReturnProductStartingWithName_12() {
        assertEquals(12, TicketTranslation.getValidTicketFieldsAndReturnProductStartingWithName(test2, "class"));
    }

    @Test
    public void getValidTicketFieldsAndReturnProductStartingWithName_11() {
        assertEquals(11, TicketTranslation.getValidTicketFieldsAndReturnProductStartingWithName(test2, "row"));
    }

    @Test
    public void getValidTicketFieldsAndReturnProductStartingWithName_13() {
        assertEquals(13, TicketTranslation.getValidTicketFieldsAndReturnProductStartingWithName(test2, "seat"));
    }

    @Test
    public void getValidTicketFieldsAndReturnProductStartingWithName_1() {
        assertEquals(1429779530273l, TicketTranslation.getValidTicketFieldsAndReturnProductStartingWithName(input, "departure"));
    }
}