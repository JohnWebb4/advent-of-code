/* Licensed under Apache-2.0 */
package advent.year2015.day19;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class MedicineRudolphTest {
    public static String testReplacement;
    public static String testFabricate;
    public static String inputReplacement;

    @BeforeClass
    public static void initialize() {
        testReplacement = "H => HO\n" +
                "H => OH\n" +
                "O => HH";
        testFabricate = "e => H\n" +
                "e => O\n" +
                "H => HO\n" +
                "H => OH\n" +
                "O => HH";
        inputReplacement = "Al => ThF\n" +
                "Al => ThRnFAr\n" +
                "B => BCa\n" +
                "B => TiB\n" +
                "B => TiRnFAr\n" +
                "Ca => CaCa\n" +
                "Ca => PB\n" +
                "Ca => PRnFAr\n" +
                "Ca => SiRnFYFAr\n" +
                "Ca => SiRnMgAr\n" +
                "Ca => SiTh\n" +
                "F => CaF\n" +
                "F => PMg\n" +
                "F => SiAl\n" +
                "H => CRnAlAr\n" +
                "H => CRnFYFYFAr\n" +
                "H => CRnFYMgAr\n" +
                "H => CRnMgYFAr\n" +
                "H => HCa\n" +
                "H => NRnFYFAr\n" +
                "H => NRnMgAr\n" +
                "H => NTh\n" +
                "H => OB\n" +
                "H => ORnFAr\n" +
                "Mg => BF\n" +
                "Mg => TiMg\n" +
                "N => CRnFAr\n" +
                "N => HSi\n" +
                "O => CRnFYFAr\n" +
                "O => CRnMgAr\n" +
                "O => HP\n" +
                "O => NRnFAr\n" +
                "O => OTi\n" +
                "P => CaP\n" +
                "P => PTi\n" +
                "P => SiRnFAr\n" +
                "Si => CaSi\n" +
                "Th => ThCa\n" +
                "Ti => BP\n" +
                "Ti => TiTi\n" +
                "e => HF\n" +
                "e => NAl\n" +
                "e => OMg";
    }

    @Test
    public void getDistinctMoleculeCount() {
        assertEquals(4, MedicineRudolph.getDistinctMoleculeCount(testReplacement, "HOH"));
        assertEquals(7, MedicineRudolph.getDistinctMoleculeCount(testReplacement, "HOHOHO"));
        assertEquals(535, MedicineRudolph.getDistinctMoleculeCount(inputReplacement, "CRnCaCaCaSiRnBPTiMgArSiRnSiRnMgArSiRnCaFArTiTiBSiThFYCaFArCaCaSiThCaPBSiThSiThCaCaPTiRnPBSiThRnFArArCaCaSiThCaSiThSiRnMgArCaPTiBPRnFArSiThCaSiRnFArBCaSiRnCaPRnFArPMgYCaFArCaPTiTiTiBPBSiThCaPTiBPBSiRnFArBPBSiRnCaFArBPRnSiRnFArRnSiRnBFArCaFArCaCaCaSiThSiThCaCaPBPTiTiRnFArCaPTiBSiAlArPBCaCaCaCaCaSiRnMgArCaSiThFArThCaSiThCaSiRnCaFYCaSiRnFYFArFArCaSiRnFYFArCaSiRnBPMgArSiThPRnFArCaSiRnFArTiRnSiRnFYFArCaSiRnBFArCaSiRnTiMgArSiThCaSiThCaFArPRnFArSiRnFArTiTiTiTiBCaCaSiRnCaCaFYFArSiThCaPTiBPTiBCaSiThSiRnMgArCaF"));
    }

    @Test
    public void getFewestStepsToFabricate() {
        assertEquals(3, MedicineRudolph.getFewestStepsToFabricate(testFabricate, "HOH"));
        assertEquals(6, MedicineRudolph.getFewestStepsToFabricate(testFabricate, "HOHOHO"));
        assertEquals(212, MedicineRudolph.getFewestStepsToFabricate(inputReplacement, "CRnCaCaCaSiRnBPTiMgArSiRnSiRnMgArSiRnCaFArTiTiBSiThFYCaFArCaCaSiThCaPBSiThSiThCaCaPTiRnPBSiThRnFArArCaCaSiThCaSiThSiRnMgArCaPTiBPRnFArSiThCaSiRnFArBCaSiRnCaPRnFArPMgYCaFArCaPTiTiTiBPBSiThCaPTiBPBSiRnFArBPBSiRnCaFArBPRnSiRnFArRnSiRnBFArCaFArCaCaCaSiThSiThCaCaPBPTiTiRnFArCaPTiBSiAlArPBCaCaCaCaCaSiRnMgArCaSiThFArThCaSiThCaSiRnCaFYCaSiRnFYFArFArCaSiRnFYFArCaSiRnBPMgArSiThPRnFArCaSiRnFArTiRnSiRnFYFArCaSiRnBFArCaSiRnTiMgArSiThCaSiThCaFArPRnFArSiRnFArTiTiTiTiBCaCaSiRnCaCaFYFArSiThCaPTiBPTiBCaSiThSiRnMgArCaF"));
    }
}