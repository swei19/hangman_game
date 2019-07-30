/**
 * @author Calvin Chan, Denes Marton, Spencer Wei 
 * This class is responsible for scoring the game based on the number of tries 
 * and the word length that the player had to guess
 */
public class Score {
	private int numWrongTries;
	private int guessWordLength;

	/**
	 * Score constructor
	 * @param numWrongTries   number of incorrect letter guesses by the player
	 * @param guessWordLength the length of the word that the player is trying to
	 *                        guess
	 */
	public Score(int numWrongTries, int guessWordLength) {
		this.guessWordLength = guessWordLength;
		this.numWrongTries = numWrongTries;

	}

	/**
	 * The scoreGame method will take the two variables numWrongTries and
	 * guessWordLength and will calculate and return a score, which will later be
	 * passed to the HighScoreBoard class.
	 * 
	 * @return the score of that the player achieved
	 */

	public int scoreGame() {
		if (guessWordLength - numWrongTries < 0) {
			return 0;
		} else {
			return guessWordLength - numWrongTries;
		}
	}
}
