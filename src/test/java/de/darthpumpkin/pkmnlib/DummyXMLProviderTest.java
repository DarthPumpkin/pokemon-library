/**
 * 
 */
package de.darthpumpkin.pkmnlib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import de.darthpumpkin.pkmnlib.AtomarMove;
import de.darthpumpkin.pkmnlib.DummyXMLMoveTreeProvider;
import de.darthpumpkin.pkmnlib.Move;

/**
 * @author dominik
 * 
 */
public class DummyXMLProviderTest {

	private DummyXMLMoveTreeProvider provider;

	@Before
	public void setUpBefore() {
		provider = new DummyXMLMoveTreeProvider();
	}

	/**
	 * Test method for
	 * {@link de.darthpumpkin.pkmnlib.DummyXMLMoveTreeProvider#attachMoveTree()}
	 * with Move 'Tackle'.
	 */
	@Test
	public void testAttachMoveTreeTackle() {
		Move tackle = new Move(33);
		provider.setMove(tackle);
		provider.attachMoveTree();
		assertEquals(tackle.getId(), 33);
		AtomarMove succ = tackle.getSuccessElement();
		assertNotNull(succ);
		assertNull(tackle.getFailureElement());
		assertEquals(succ.getEffectId(), 1);
		assertEquals(succ.getProbability(), 100);
		assertNull(succ.getSuccessElement());
		assertNull(succ.getFailureElement());
	}

	/**
	 * Test method for
	 * {@link de.darthpumpkin.pkmnlib.DummyXMLMoveTreeProvider#attachMoveTree()}
	 * with Move 'Growl'.
	 */
	@Test
	public void testAttachMoveTreeGrowl() {
		Move growl = new Move(45);
		provider.setMove(growl);
		provider.attachMoveTree();
		assertEquals(growl.getId(), 45);
		AtomarMove succ = growl.getSuccessElement();
		assertNotNull(succ);
		assertNull(growl.getFailureElement());
		assertEquals(succ.getEffectId(), 101);
		assertEquals(succ.getProbability(), 100);
		assertNull(succ.getSuccessElement());
		assertNull(succ.getFailureElement());
	}

	/**
	 * Test method for
	 * {@link de.darthpumpkin.pkmnlib.DummyXMLMoveTreeProvider#attachMoveTree()}
	 * with Move 'Leech-Seed'.
	 */
	@Test
	public void testAttachMoveTreeLeechSeed() {
		Move leechSeed = new Move(73);
		provider.setMove(leechSeed);
		provider.attachMoveTree();
		assertEquals(leechSeed.getId(), 73);
		AtomarMove succ = leechSeed.getSuccessElement();
		assertNotNull(succ);
		assertNull(leechSeed.getFailureElement());
		assertEquals(succ.getEffectId(), 1001);
		assertEquals(succ.getProbability(), 100);
		assertNull(succ.getSuccessElement());
		assertNull(succ.getFailureElement());
	}

}
