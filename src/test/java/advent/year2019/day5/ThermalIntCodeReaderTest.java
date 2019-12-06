/* Licensed under Apache-2.0 */
package advent.year2019.day5;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

public class ThermalIntCodeReaderTest {
    static int[] codes;

    @BeforeClass
    public static void setup() {
        try {
            File file = new File("./src/test/java/advent/year2019/day5/Codes.txt");

            BufferedReader br = new BufferedReader(new FileReader(file));

            String codeString;
            while ((codeString = br.readLine()) != null) {
                codes = Arrays.stream(codeString.split(",")).mapToInt(i->Integer.parseInt(i)).toArray();
            }

            br.close();
        } catch(Exception e) {
            System.out.println(String.format("Error reading 2019 day 3 int codes %s", e));
        }
    }

    @Test
    public void getOperation1002() {
        assertArrayEquals(new int[] {2, 0, 1, 0}, ThermalIntCodeReader.getOprations(1002));
    }

    @Test
    public void getParamValue0() {
        assertEquals(10, ThermalIntCodeReader.getParamValue(2, 0, new int[] {
                0, 0, 10,
        }));
    }

    @Test
    public void getParamValue1() {
        assertEquals(2, ThermalIntCodeReader.getParamValue(2, 1, new int[] { }));
    }

    public void runIntCodes() {
        assertEquals(new IntResult(new int[] {
                1002,4,3,4,99
        }, new int[] {}), ThermalIntCodeReader.runIntCodes(new int[] {
                1002,4,3,4,33
        }));
    }

    @Test
    public void runIntCodes2() {
        assertEquals(new IntResult(new int[] {
            1101,100,-1,4,99
        }, new int[] {}), ThermalIntCodeReader.runIntCodes(new int[] {
            1101,100,-1,4,0
        }));
    }

    @Test
    public void runIntCodesAnswer1() {
        int[] mutableCodes = new int[codes.length];
        System.arraycopy(codes, 0, mutableCodes, 0, mutableCodes.length);

        IntResult result = ThermalIntCodeReader.runIntCodes(mutableCodes, new int[] { 1 });

        assertArrayEquals(new int[] {3, 0, 0, 0, 0, 0, 0, 0, 0, 13818007}, result.output);
    }

    @Test
    public void runIntCodesAdvancedPositionEqual8() {
        assertEquals(new IntResult(new int[] {
            3,9,8,9,10,9,4,9,99,1,8
        }, new int[] {1}), ThermalIntCodeReader.runIntCodes(new int[] {
            3,9,8,9,10,9,4,9,99,-1,8
        }, new int[] { 8 }));
    }

    @Test
    public void runIntCodesAdvancedPositionNotEqual8() {
        assertEquals(new IntResult(new int[] {
            3,9,8,9,10,9,4,9,99,0,8
        }, new int[] {0}), ThermalIntCodeReader.runIntCodes(new int[] {
            3,9,8,9,10,9,4,9,99,-1,8
        }, new int[] { 7 }));
    }

    @Test
    public void runIntCodesAdvancedPositionLessThan8() {
        assertEquals(new IntResult(new int[] {
            3,9,7,9,10,9,4,9,99,1,8
        }, new int[] {1}), ThermalIntCodeReader.runIntCodes(new int[] {
            3,9,7,9,10,9,4,9,99,-1,8
        }, new int[] { 7 }));
    }

    @Test
    public void runIntCodesAdvacedPositionNotLessThan8() {
        assertEquals(new IntResult(new int[] {
            3,9,7,9,10,9,4,9,99,0,8
        }, new int[] {0}), ThermalIntCodeReader.runIntCodes(new int[] {
            3,9,7,9,10,9,4,9,99,-1,8
        }, new int[] { 8 }));
    }

    @Test
    public void runIntCodesAdvancedImmediateEqual8() {
        assertEquals(new IntResult(new int[] {
            3,3,1108,1,8,3,4,3,99
        }, new int[] {1}), ThermalIntCodeReader.runIntCodes(new int[] {
            3,3,1108,-1,8,3,4,3,99
        }, new int[] { 8 }));
    }

    @Test
    public void runIntCodesAdvancedImmediateNotEqual8() {
        assertEquals(new IntResult(new int[] {
            3,3,1108,0,8,3,4,3,99
        }, new int[] {0}), ThermalIntCodeReader.runIntCodes(new int[] {
            3,3,1108,-1,8,3,4,3,99
        }, new int[] { 7 }));
    }

    @Test
    public void runIntCodesAdvancedImmediateLessThan8() {
        assertEquals(new IntResult(new int[] {
            3,3,1107,1,8,3,4,3,99
        }, new int[] {1}), ThermalIntCodeReader.runIntCodes(new int[] {
            3,3,1107,-1,8,3,4,3,99
        }, new int[] { 7 }));
    }

    @Test
    public void runIntCodesAdvancedImmediateNotLessThan8() {
        assertEquals(new IntResult(new int[] {
            3,3,1107,0,8,3,4,3,99
        }, new int[] {0}), ThermalIntCodeReader.runIntCodes(new int[] {
            3,3,1107,-1,8,3,4,3,99
        }, new int[] { 8 }));
    }

    @Test
    public void runIntCodesAdvancedPositionJumpZero() {
        assertEquals(new IntResult(new int[] {
            3,12,6,12,15,1,13,14,13,4,13,99,0,0,1,9
        }, new int[] {0}), ThermalIntCodeReader.runIntCodes(new int[] {
            3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9
        }, new int[] { 0 }));
    }

    @Test
    public void runIntCodesAdvancedPositionJumpNonZero() {
        assertEquals(new IntResult(new int[] {
            3,12,6,12,15,1,13,14,13,4,13,99,8,1,1,9
        }, new int[] {1}), ThermalIntCodeReader.runIntCodes(new int[] {
            3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9
        }, new int[] { 8 }));
    }

    @Test
    public void runIntCodesAdvancedImmediateJumpZero() {
        assertEquals(new IntResult(new int[] {
            3,3,1105,0,9,1101,0,0,12,4,12,99,0
        }, new int[] {0}), ThermalIntCodeReader.runIntCodes(new int[] {
            3,3,1105,-1,9,1101,0,0,12,4,12,99,1
        }, new int[] { 0 }));
    }

    @Test
    public void runIntCodesAdvancedImmediateJumpNonZero() {
        assertEquals(new IntResult(new int[] {
            3,3,1105,8,9,1101,0,0,12,4,12,99,1
        }, new int[] {1}), ThermalIntCodeReader.runIntCodes(new int[] {
            3,3,1105,-1,9,1101,0,0,12,4,12,99,1
        }, new int[] { 8 }));
    }

    @Test
    public void runIntCodesAnswer5() {
        int[] mutableCodes = new int[codes.length];
        System.arraycopy(codes, 0, mutableCodes, 0, mutableCodes.length);

        IntResult result = ThermalIntCodeReader.runIntCodes(mutableCodes, new int[] { 5 });

        assertArrayEquals(new int[] {3176266}, result.output);
    }
}