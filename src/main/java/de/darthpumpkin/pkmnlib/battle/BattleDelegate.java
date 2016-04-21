package de.darthpumpkin.pkmnlib.battle;

/**
 * Takes care of the actual battle logic in a synchronous way by implementing
 * all the turn steps.
 * 
 * @author Dominik Fay
 */
public interface BattleDelegate {

    /**
     * Performs those actions that happen once at the beginning of the battle,
     * before any player is asked to do anything. These involve sending out the
     * first pokemon(s), initializing weather, and more. This method has to be
     * called before the main loop is entered.
     *
     * @throws IllegalStateException
     *             if the battle is already running or finished
     */
    void init();

    /**
     *
     */
    void preTurn();

    void midTurn();

    void postTurn();

    /**
     * Performs actions happening at the end of the battle, after all turns are
     * finished. It can be seen as the counterpart of {@link #init()}. This
     * method will determine the winner and tell {@link #waitForEnd()} to
     * return.
     *
     * @throws IllegalStateException
     *             if the battle is still running or not started yet
     */
    void finish();

}
