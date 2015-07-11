package de.darthpumpkin.pkmnlib.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.darthpumpkin.pkmnlib.DummySpeciesFactory;
import de.darthpumpkin.pkmnlib.MaximumSizeExceededException;
import de.darthpumpkin.pkmnlib.PokemonInstance;
import de.darthpumpkin.pkmnlib.PokemonInstanceBuilder;
import de.darthpumpkin.pkmnlib.PokemonSpecies;
import de.darthpumpkin.pkmnlib.UniqueBoundedList;

public class UniqueBoundedListTest {

	private PokemonSpecies s;

	@Before
	public void setUpSpecies() {
		s = new DummySpeciesFactory().getSpeciesById(1);
	}

	@Test
	public void testUniqueBoundedList() {
		assertTrue(UniqueBoundedList.DEFAULT_MAX_SIZE >= 0);
		UniqueBoundedList<PokemonInstance> t = new UniqueBoundedList<PokemonInstance>();
		assertEquals(UniqueBoundedList.DEFAULT_MAX_SIZE, t.maxSize());
		assertTrue(t.isEmpty());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUniqueBoundedListIntIllegalMaxSize() {
		new UniqueBoundedList<PokemonInstance>(-1);
	}

	@Test
	public void testUniqueBoundedListIntLegalMaxSize() {
		int[] sizes = new int[] { 0, 1, 6, 100, 10000 };
		for (int size : sizes) {
			UniqueBoundedList<PokemonInstance> t = new UniqueBoundedList<PokemonInstance>(
					size);
			assertEquals(size, t.maxSize());
			assertTrue(t.isEmpty());
		}
	}

	@Test
	public void testUniqueBoundedListCollectionOfQextendsPokemonInstance() {
		List<PokemonInstance[]> emptyArrays = new ArrayList<>();
		List<PokemonInstance[]> nonEmptyArrays = new ArrayList<>();

		emptyArrays.add(new PokemonInstance[0]);
		emptyArrays.add(new PokemonInstance[100]);

		nonEmptyArrays
				.add(new PokemonInstance[] { new PokemonInstanceBuilder(s)
						.makePokemon() });

		for (PokemonInstance[] nonEmpty : nonEmptyArrays) {
			UniqueBoundedList<PokemonInstance> t = new UniqueBoundedList<PokemonInstance>(
					Arrays.asList(nonEmpty));
			assertEquals(UniqueBoundedList.DEFAULT_MAX_SIZE, t.maxSize());
			assertFalse(t.isEmpty());
			assertArrayEquals(nonEmpty, t.toArray());
		}
		for (PokemonInstance[] empty : emptyArrays) {
			UniqueBoundedList<PokemonInstance> t = new UniqueBoundedList<PokemonInstance>(
					Arrays.asList(empty));
			assertEquals(UniqueBoundedList.DEFAULT_MAX_SIZE, t.maxSize());
			assertTrue(t.isEmpty());
		}
	}

	@Test(expected = MaximumSizeExceededException.class)
	public void testUniqueBoundedListCollectionOfQextendsPokemonInstanceTooLarge() {
		PokemonInstanceBuilder b = new PokemonInstanceBuilder(s);
		Collection<PokemonInstance> coll = new ArrayList<>();
		for (int i = 0; i < UniqueBoundedList.DEFAULT_MAX_SIZE + 1; i++) {
			coll.add(b.makePokemon());
		}
		new UniqueBoundedList<PokemonInstance>(coll);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUniqueBoundedListCollectionOfQextendsPokemonInstanceIntIllegalArgument() {
		new UniqueBoundedList<PokemonInstance>(
				Collections.<PokemonInstance> emptyList(), -1);
	}

	@Test(expected = MaximumSizeExceededException.class)
	public void testUniqueBoundedListCollectionOfQextendsPokemonInstanceIntTooLarge() {
		PokemonInstanceBuilder b = new PokemonInstanceBuilder(s);
		int maxSize = 10;
		Collection<PokemonInstance> coll = new ArrayList<>();
		for (int i = 0; i < maxSize + 1; i++) {
			coll.add(b.makePokemon());
		}
		new UniqueBoundedList<PokemonInstance>(coll, maxSize);
	}

	@Test
	public void testAddItemToEmptyList() {
		UniqueBoundedList<PokemonInstance> ubl = new UniqueBoundedList<PokemonInstance>();
		PokemonInstance p = new PokemonInstanceBuilder(
				new DummySpeciesFactory().getSpeciesById(1)).makePokemon();
		ubl.add(p);
		assertTrue(ubl.contains(p));
		assertEquals(1, ubl.size());
		assertArrayEquals(new PokemonInstance[] { p }, ubl.toArray());
	}

	@Test(expected = MaximumSizeExceededException.class)
	public void testAddItemToEmptyListWithMaxSize0() {
		UniqueBoundedList<PokemonInstance> ubl = new UniqueBoundedList<PokemonInstance>(
				0);
		PokemonInstance p = new PokemonInstanceBuilder(
				new DummySpeciesFactory().getSpeciesById(1)).makePokemon();
		ubl.add(p);
	}

	@Test(expected = MaximumSizeExceededException.class)
	public void testAddItemToFullList() {
		UniqueBoundedList<PokemonInstance> ubl = new UniqueBoundedList<PokemonInstance>();
		PokemonInstanceBuilder builder = new PokemonInstanceBuilder(s);
		try {
			for (int i = 0; i < ubl.maxSize(); i++) {
				ubl.add(builder.makePokemon());
			}
		} catch (MaximumSizeExceededException e) {
			fail();
		}
		ubl.add(builder.makePokemon());
	}

	@Test
	public void testAddDuplicateItem() {
		UniqueBoundedList<PokemonInstance> ubl = new UniqueBoundedList<PokemonInstance>();
		PokemonInstanceBuilder builder = new PokemonInstanceBuilder(s);
		PokemonInstance p = builder.makePokemon();
		ubl.add(p);
		ubl.add(p);
		assertTrue(ubl.contains(p));
		assertEquals(1, ubl.size());
		assertArrayEquals(new PokemonInstance[] { p }, ubl.toArray());
	}

	@Test
	public void testAddDuplicateItemToFullList() {
		UniqueBoundedList<PokemonInstance> ubl = new UniqueBoundedList<PokemonInstance>();
		PokemonInstanceBuilder builder = new PokemonInstanceBuilder(s);
		PokemonInstance p = builder.makePokemon();
		ubl.add(p);
		for (int i = 0; i < ubl.maxSize() - 1; i++) {
			ubl.add(builder.makePokemon());
		}
		assertEquals(ubl.maxSize(), ubl.size());
		Object[] before = ubl.toArray();
		ubl.add(p);
		Object[] after = ubl.toArray();
		assertArrayEquals(before, after);
	}

	@Test
	public void testAddAllCollectionOfQextendsItem() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddAllIntCollectionOfQextendsItem() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddIntItem() {
		fail("Not yet implemented");
	}

}
