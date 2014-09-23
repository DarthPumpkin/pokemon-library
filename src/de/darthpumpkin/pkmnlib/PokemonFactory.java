package de.darthpumpkin.pkmnlib;

/**
 * Generates Pokemon, given an identifier. Implementations of this class must
 * translate database information into a Pokemon object. E.g. it could use the
 * on-board SQLite db by veekun. As this database handling is platform-specific
 * and must be efficient, this interface has to be implemented individually by
 * the game developer considering the target platform's properties.
 * 
 * @author dominik
 * 
 */
public interface PokemonFactory {

}
