package de.darthpumpkin.pkmnlib;

// TODO improve javadoc
/**
 * Wrapper class for conveniently generating pkmns. Offers the possibility to
 * set attributes in a chain.
 * 
 * @author DarthPumpkin
 * 
 */
public class PokemonBuilder {

	private PokemonInstance pokemon;

	/**
	 * should only be called by PokemonFactory.
	 * 
	 * @param pokemon
	 *            the freshly inflated pokemon
	 */
	PokemonBuilder(PokemonInstance pokemon) {
		this.pokemon = pokemon;
	}

	// TODO add methods for setting attributes

	/**
	 * call this method to generate the PokemonInstance.
	 * 
	 * @return the pokemon represented by this builder
	 */
	public PokemonInstance makePokemon() {
		return pokemon;
	}
}
