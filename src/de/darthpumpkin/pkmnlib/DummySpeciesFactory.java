package de.darthpumpkin.pkmnlib;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * very simple factory implementation without using the database at all. Only
 * creates bulbasaurs, ignoring the passed arguments.
 * 
 * @author dominik
 * 
 */
public class DummySpeciesFactory implements PokemonSpeciesFactory {

	@Override
	public PokemonSpecies getSpeciesById(int speciesId) {
		if (speciesId != 1) {
			throw new NotImplementedException();
		}
		return makeBulbasaur();
	}

	@Override
	public PokemonSpecies getSpeciesByName(String name, int languageId) {
		if (!name.toLowerCase().equals("bulbasaur") || languageId != 9) {
			throw new NotImplementedException();
		}
		return makeBulbasaur();
	}

	private PokemonSpecies makeBulbasaur() {
		PokemonSpecies bulbaSpecies = new PokemonSpecies();
		bulbaSpecies.setBaseFriendship(70);
		bulbaSpecies.setBaseStats(new int[] { 45, 49, 49, 65, 65, 45 });
		bulbaSpecies.setCatchRate(45);
		bulbaSpecies.setEvYield(new int[] { 0, 0, 0, 1, 0, 0 });
		bulbaSpecies.setGenderRate(1);
		bulbaSpecies.setGrowthRate(4);
		bulbaSpecies.setHeight(7);
		bulbaSpecies.setSpeciesId(1);
		bulbaSpecies.setTypes(new Type[] { Type.GRASS, Type.POISON });
		bulbaSpecies.setWeight(69);
		return bulbaSpecies;
	}

}
