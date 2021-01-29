/* Licensed under Apache-2.0 */
package advent.year2020.day21;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

public class AllergenAssessmentTest {
	public String test1;
	public String input;

	@Before
	public void initialize() {
		test1 = "mxmxvkd kfcds sqjhc nhms (contains dairy, fish)\n" +
				"trh fvjkl sbzzf mxmxvkd (contains dairy)\n" +
				"sqjhc fvjkl (contains soy)\n" +
				"sqjhc mxmxvkd sbzzf (contains fish)";

		try {
			input = new String(Files.readAllBytes(Paths.get("./src/test/java/advent/year2020/day21/input.txt")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void countIngredientsNoAllergens_5() {
		assertEquals(5, AllergenAssessment.countIngredientsNoAllergens(test1));
	}

	@Test
	public void countIngredientsNoAllergen_2584() {
		assertEquals(2584, AllergenAssessment.countIngredientsNoAllergens(input));
	}

	@Test
	public void getAllergenIngredientPairs_mxm() {
		assertEquals("mxmxvkd,sqjhc,fvjkl", AllergenAssessment.getAllergenIngredientPairs(test1));
	}

	@Test
	public void getAllergenIngredientPairs_1() {
		assertEquals("fqhpsl,zxncg,clzpsl,zbbnj,jkgbvlxh,dzqc,ppj,glzb", AllergenAssessment.getAllergenIngredientPairs(input));
	}

}