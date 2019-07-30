/**
 * @author Calvin Chan, Denes Marton, Spencer Wei Class 
 * containing information about the player including 
 * the player's username and number of incorrect word guesses
 */
public class Player {
	private String username;
	private int numWrongTries;

	/**
	 * Constructor to initialize a player object.
	 * 
	 * @param username the player's username
	 */
	public Player(String username) {
		this.username = username;
		this.numWrongTries = 0;
	}

}
