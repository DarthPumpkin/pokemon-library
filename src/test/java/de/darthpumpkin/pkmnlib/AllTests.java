package de.darthpumpkin.pkmnlib;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.darthpumpkin.pkmnlib.UniqueBoundedListTest;

@RunWith(Suite.class)
@SuiteClasses({ PokemonSpeciesTest.class, PokemonInstanceBuilderTest.class,
		PokemonInstanceTest.class, DummyXMLProviderTest.class,
		TrainerBattle1vs1Test.class, UniqueBoundedListTest.class })
public class AllTests {

}
