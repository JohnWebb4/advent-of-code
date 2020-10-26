/* Licensed under Apache-2.0 */
package advent.year2019.day16;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FFTTest {
    @Test
    public void apply1Time48226158() {
        FFT fft = new FFT(new int[]{0, 1, 0, -1});

        assertEquals("48226158", fft.applyXTimes("12345678", 1));
    }

    @Test
    public void apply2Times34040438() {
        FFT fft = new FFT(new int[]{0, 1, 0, -1});

        assertEquals("34040438", fft.applyXTimes("12345678", 2));
    }

    @Test
    public void apply3Times03415518() {
        FFT fft = new FFT(new int[]{0, 1, 0, -1});

        assertEquals("03415518", fft.applyXTimes("12345678", 3));
    }

    @Test
    public void apply4Times01029498() {
        FFT fft = new FFT(new int[]{0, 1, 0, -1});

        assertEquals("01029498", fft.applyXTimes("12345678", 4));
    }

    @Test
    public void apply100Times24176176() {
        FFT fft = new FFT(new int[]{0, 1, 0, -1});

        assertEquals(
                "24176176", fft.applyXTimes("80871224585914546619083218645595", 100).substring(0, 8));
    }

    @Test
    public void apply100Times73745418() {
        FFT fft = new FFT(new int[]{0, 1, 0, -1});

        assertEquals(
                "73745418", fft.applyXTimes("19617804207202209144916044189917", 100).substring(0, 8));
    }

    @Test
    public void apply100Times52432133() {
        FFT fft = new FFT(new int[]{0, 1, 0, -1});

        assertEquals(
                "52432133", fft.applyXTimes("69317163492948606335995924319873", 100).substring(0, 8));
    }

    @Test
    public void apply100Times59522422() {
        FFT fft = new FFT(new int[]{0, 1, 0, -1});

        assertEquals(
                "59522422",
                fft.applyXTimes(
                        "59791871295565763701016897619826042828489762561088671462844257824181773959378451545496856546977738269316476252007337723213764111739273853838263490797537518598068506295920453784323102711076199873965167380615581655722603274071905196479183784242751952907811639233611953974790911995969892452680719302157414006993581489851373437232026983879051072177169134936382717591977532100847960279215345839529957631823999672462823375150436036034669895698554251454360619461187935247975515899240563842707592332912229870540467459067349550810656761293464130493621641378182308112022182608407992098591711589507803865093164025433086372658152474941776320203179747991102193608",
                        100)
                        .substring(0, 8));
    }

    @Test
    public void decodeMessage84462026() {
        FFT fft = new FFT(new int[]{0, 1, 0, -1});

        assertEquals("84462026", fft.decodeMessage("03036732577212944063491565474664", 100));
    }

    @Test
    public void decodeMessage78725270() {
        FFT fft = new FFT(new int[]{0, 1, 0, -1});

        assertEquals("78725270", fft.decodeMessage("02935109699940807407585447034323", 100));
    }

    @Test
    public void decodeMessage53553731() {
        FFT fft = new FFT(new int[]{0, 1, 0, -1});

        assertEquals("53553731", fft.decodeMessage("03081770884921959731165446850517", 100));
    }

    @Test
    public void decodeMessage18650834() {
        FFT fft = new FFT(new int[]{0, 1, 0, -1});

        assertEquals("18650834", fft.decodeMessage("59791871295565763701016897619826042828489762561088671462844257824181773959378451545496856546977738269316476252007337723213764111739273853838263490797537518598068506295920453784323102711076199873965167380615581655722603274071905196479183784242751952907811639233611953974790911995969892452680719302157414006993581489851373437232026983879051072177169134936382717591977532100847960279215345839529957631823999672462823375150436036034669895698554251454360619461187935247975515899240563842707592332912229870540467459067349550810656761293464130493621641378182308112022182608407992098591711589507803865093164025433086372658152474941776320203179747991102193608", 100));
    }

}
