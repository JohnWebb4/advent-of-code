/* Licensed under Apache-2.0 */
package advent.year2019.day8;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.junit.BeforeClass;
import org.junit.Test;

public class SpaceImageDecoderTest {
    static String encodedImage = "";

    @BeforeClass
    public static void setup() {
        try {
            File file = new File("./src/test/java/advent/year2019/day8/Image.txt");

            BufferedReader br = new BufferedReader(new FileReader(file));

            String codeString;
            while ((codeString = br.readLine()) != null) {
                encodedImage = encodedImage + codeString;
            }

            br.close();
        } catch(Exception e) {
            System.out.println(String.format("Error reading 2019 day 2 int codes %s"));
        }
    }
    public void decodeImage3By2() {
        assertArrayEquals(new String[][] {
                new String[] {
                        "123",
                        "456"
                },
                new String[] {
                        "789",
                        "012"
                }
        }, SpaceImageDecoder.decode("123456789012", 3,  2));
    }

    public void getNum1sTimes2sOnLayerWithMostZeros() {
        assertEquals(1, SpaceImageDecoder.getNumber1sTime2sForLayerWithFewestZeros("123456789012", 3, 2));
    }

    @Test
    public void getNum1sTimes2sOnLayerWithMostZerosAnswer() {
        assertEquals(1976, SpaceImageDecoder.getNumber1sTime2sForLayerWithFewestZeros(encodedImage, 25, 6));
    }
}