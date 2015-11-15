package de.darthpumpkin.pkmnlib.battle;

// TODO is one method enough or does it make sense to have more?

public interface BattleEventListener {
	
	public void onBattleEvent(BattleEvent e);
	
//	public void addEventFilter(Predicate<BattleEvent> filter);
//	
//	public void removeEventFilter(Predicate<BattleEvent> filter);
//	
//	public Collection<Predicate<? super BattleEvent>> getEventFilters();

}
