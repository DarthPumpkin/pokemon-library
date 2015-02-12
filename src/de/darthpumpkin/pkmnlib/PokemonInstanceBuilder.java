package de.darthpumpkin.pkmnlib;

// TODO improve javadoc
/**
 * Builder class for conveniently and safely generating a
 * {@link PokemonInstance}. Offers the possibility to set attributes in a chain.
 * 
 * @author DarthPumpkin
 * 
 */
public class PokemonInstanceBuilder {

	private PokemonInstance pokemon;

	public PokemonInstanceBuilder(PokemonSpecies species) {
		this.pokemon = new PokemonInstance(species);
	}

	// TODO add methods for setting attributes

	/**
	 * call this method to generate the PokemonInstance.
	 * 
	 * @return the PokemonInstance represented by this builder
	 */
	public PokemonInstance makePokemon() {
		return pokemon;
	}
}
