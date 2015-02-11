package de.darthpumpkin.pkmnlib;

// TODO improve javadoc
/**
 * Wrapper class for conveniently generating a PokemonInstance. Offers the
 * possibility to set attributes in a chain.
 * 
 * @author DarthPumpkin
 * 
 */
public class PokemonInstanceBuilder {

	private PokemonInstance pokemon;

	PokemonInstanceBuilder(PokemonSpecies species) {
		this.pokemon = new PokemonInstance(species);
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
