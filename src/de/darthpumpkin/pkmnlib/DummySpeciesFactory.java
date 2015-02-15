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
		bulbaSpecies.setAbilities(new int[] { 65, 0, 34, 0 });
		bulbaSpecies.setBaseFriendship(70);
		bulbaSpecies.setBaseStats(new int[] { 45, 49, 49, 65, 65, 45 });
		bulbaSpecies.setCatchRate(45);
		bulbaSpecies.setEvYield(new int[] { 0, 0, 0, 1, 0, 0 });
		bulbaSpecies.setGenderRate(1);
		bulbaSpecies.setGrowthRate(4);
		bulbaSpecies.setHeight(7);
		bulbaSpecies.setLevelsForMovesLearnableByLevel(new int[] { 1, 3, 7, 9,
				13, 13, 15, 19, 21, 25, 27, 31, 33, 37 });
		bulbaSpecies.setMovesLearnableByLevel(new Move[] { new Move(33),
				new Move(45), new Move(73), new Move(22), new Move(77),
				new Move(79), new Move(36), new Move(75), new Move(230),
				new Move(74), new Move(38), new Move(388), new Move(235),
				new Move(402) });
		bulbaSpecies.setSpeciesId(1);
		bulbaSpecies.setTypes(new Type[] { Type.GRASS, Type.POISON });
		bulbaSpecies.setWeight(69);
		return bulbaSpecies;
	}

}
