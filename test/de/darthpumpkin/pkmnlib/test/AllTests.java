package de.darthpumpkin.pkmnlib.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ PokemonSpeciesTest.class, PokemonInstanceBuilderTest.class,
		PokemonInstanceTest.class, DummyXMLProviderTest.class,
		TrainerBattle1vs1Test.class })
public class AllTests {

}
