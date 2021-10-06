/* Licensed under Apache-2.0 */
package advent.year2016.day7;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

public class InternetProtocolTest {
    public static String input;

    @BeforeClass
    public static void initialize() {
        try {
            input = new String(Files.readAllBytes(Paths.get("./src/test/java/advent/year2016/day7/input.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getCountIPsSupportTLS() {
        assertEquals(1, InternetProtocol.getCountIPsSupportTLS("abba[mnop]qrst"));
        assertEquals(0, InternetProtocol.getCountIPsSupportTLS("abcd[bddb]xyyx"));
        assertEquals(0, InternetProtocol.getCountIPsSupportTLS("aaaa[qwer]tyui"));
        assertEquals(1, InternetProtocol.getCountIPsSupportTLS("ioxxoj[asdfgh]zxcvbn"));
        assertEquals(118, InternetProtocol.getCountIPsSupportTLS(input));
    }

    @Test
    public void getCountSupportSSL() {
        assertEquals(1, InternetProtocol.getCountSupportSSL("aba[bab]xyz"));
        assertEquals(0, InternetProtocol.getCountSupportSSL("xyx[xyx]xyx"));
        assertEquals(1, InternetProtocol.getCountSupportSSL("aaa[kek]eke"));
        assertEquals(1, InternetProtocol.getCountSupportSSL("zazbz[bzb]cdb"));
        assertEquals(260, InternetProtocol.getCountSupportSSL(input));
    }
}
