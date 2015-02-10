/*
 * Short example usage of pkmnlib.
 * 
 * Assuming you already implemented a PokemonSpeciesFactory 'PkmnFactoryImpl'
 */

//"a wild rattata appeared!"
//1.Build a rattata instance
PokemonSpeciesFactory factory = new PkmnFactoryImpl();
PokemonSpecies rattataSpecies = factory.getSpeciesByName("Rattata");
PokemonInstanceBuilder rattataBuilder = new PokemonInstanceBuilder(rattataSpecies);
rattataBuilder.setLevel(3);
PokemonInstance rattataInstance = rattataBuilder.getInstance();
//2.Start the battle
List<PokemonInstance> rattataTeam = new ArrayList<PokemonInstance>();
rattataTeam.add(rattataInstance);
Player opponent = new StupidPlayer(rattataTeam);
RegularBattle battle = new RegularBattle(me, opponent);
battle.start();

//Win the battle
while(battle.isActive()) {
	List<Turns> turns = new ArrayList<Turn>();
	for (Player p : battle.getPlayers()) {
		turns.add(p.makeTurn());
	}
	battle.doTurn(turns);
}
//Print results
for (AtomarBattleAction action : battle.dumpActionLog()) {
	System.out.println(action.toString());
}