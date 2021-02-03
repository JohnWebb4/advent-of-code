/* Licensed under Apache-2.0 */
package advent.year2020.day23;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CrabCupsTest {
	public Long test1;
	public Long input;

	@Before
	public void initialize() {
		test1 = 389125467l;
		input = 157623984l;
	}

	@Test
	public void getStateAfterXMoves_92658374() {
		assertEquals(92658374, CrabCups.getStateAfterXMoves(10, test1));
	}

	@Test
	public void getStateAfterXMoves_67384529() {
		assertEquals(67384529, CrabCups.getStateAfterXMoves(100, test1));
	}
}