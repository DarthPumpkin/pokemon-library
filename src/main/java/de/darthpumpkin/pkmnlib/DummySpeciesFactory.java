package de.darthpumpkin.pkmnlib;

import de.darthpumpkin.pkmnlib.Move.DamageClass;
import net.alexmack.poketypes.Poketype;
import net.alexmack.poketypes.PoketypeSet;
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
		MoveTreeProvider mtProvider = new DummyXMLMoveTreeProvider();
		for (Move move : bulbaSpecies.getMovesLearnableByLevel()) {
			mtProvider.setMove(move);
			mtProvider.attachMoveTree();
		}
		Move tackle = bulbaSpecies.getMovesLearnableByLevel()[0];
		tackle.setAccuracy(100);
		tackle.setPower(50);
		tackle.setPriority(0);
		tackle.setType(Poketype.NORMAL);
		tackle.setBasePp(35);
		tackle.setCurrentPp(35);
		tackle.setDamageClass(DamageClass.PHYSICAL);
		bulbaSpecies.setSpeciesId(1);
		bulbaSpecies.setTypes(new PoketypeSet (Poketype.GRASS, Poketype.POISON));
		bulbaSpecies.setWeight(69);
		return bulbaSpecies;
	}

}
