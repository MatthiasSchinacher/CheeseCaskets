package de.schini.cheesecaskets;

/**
 * Listener, that want to stay informed, what is happening with a game, implement this interface.
 * 
 * @author mschina
 */
public interface ICheeseCasketsGameListener {

	void gameStarted(CheeseCaskets game, int currentPlayer, int numberOfPlayers);

	void gameStopped(CheeseCaskets game);
	
	void gameEnded(CheeseCaskets game);

	void playerCountChanged(CheeseCaskets game, int player, int count);
	
	void currentPlayerChanged(CheeseCaskets game, int currentPlayer);
	
	void markChanged(CheeseCaskets game, Casket casket);

	void gridChanged(CheeseCaskets game);
}
