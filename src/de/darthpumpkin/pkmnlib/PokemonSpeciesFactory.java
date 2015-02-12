package de.darthpumpkin.pkmnlib;

/**
 * Instanciates {@link PokemonSpecies} from a database, given an identifier.
 * Implementations of this class must translate database information into a
 * {@link PokemonSpecies} object. E.g. it could use the on-board SQLite db by
 * veekun. As this database handling is platform-specific and must be efficient,
 * this interface has to be implemented individually by the game developer
 * considering the target platform's properties.
 * 
 * @author dominik
 * 
 */
public interface PokemonSpeciesFactory {

	// TODO add more factory methods

	public PokemonSpecies getSpeciesById(int speciesId);

	public PokemonSpecies getSpeciesByName(String name, int languageId);

}
