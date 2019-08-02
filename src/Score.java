import java.util.ArrayList;
import java.util.HashSet;


/**
 * @author Calvin Chan, Denes Marton, Spencer Wei This class is responsible for
 *         scoring the game based on the number of tries and the word length
 *         that the player had to guess
 */
public class Score {

	boolean isCompetitive;

	/**
	 * Score constructor
	 * 
	 * @param numWrongTries   number of incorrect letter guesses by the player
	 * @param guessWordLength the length of the word that the player is trying to
	 *                        guess
	 */
	public Score(boolean isCompetitive) {
		this.isCompetitive = isCompetitive;

		// this.guessWordLength = guessWordLength;
		// this.numWrongTries = numWrongTries;
	}

	// GameUI gUI = new GameUI();

	/**
	 * The scoreGame method will look at each letter in the word and give a max score based on the letters in the word
	 * 	vowels (a,e,i,o,u),n,r,t,l,s are worth 1 point each
	 * 	d,g are worth 2 points each
	 * 	b,c,m,p are worth 3 pts each
	 *  f,h,v,w,y are worth 4 points each
	 * 	k is worth 5 pts
	 * 	j,x are worth 8 pts each
	 * 	q,z are worth 10 pts each
	 * 
	 * @return the score of that the player achieved
	 */

	/*public int scoreGame(int numWrongTries, int guessWordLength) {
		if (guessWordLength - numWrongTries < 0) {
			return 0;
		} else {
			return guessWordLength - numWrongTries;
		}
	}*/
	public int scoreGame (String guessWord, int numWrongGuesses, ArrayList<String> guessedLetters) {
		int scoreToReturn = 0;
		//HashMap<String, Integer> letterOccurences = new HashMap<String, Integer>();
		
		HashSet<String> uniqueLettersOfGuessWord = new HashSet<String>();
		for (String uniqueLetter: guessWord.split("")) {
			uniqueLettersOfGuessWord.add(uniqueLetter);
		}
		
		for (String uniqueLetter: uniqueLettersOfGuessWord) {
			int numOccurences = guessWord.length() - guessWord.replace(uniqueLetter, "").length();
			
			if (guessedLetters.contains(uniqueLetter)) {
				scoreToReturn += numOccurences * (calculateLetterScore(uniqueLetter));
			}
		}
		

		if (scoreToReturn - numWrongGuesses >= 0) {
			return scoreToReturn - numWrongGuesses;
		} else {
			return 0;
		}
		
	}

	public int calculateLetterScore(String uniqueLetter) {
		uniqueLetter = uniqueLetter.toLowerCase();
		int points = 0;

		if (uniqueLetter.matches("[aieounrtls]")) {
			points = 1;
		}
		if (uniqueLetter.matches("[dg]")) {
			points = 2;
		}
		if (uniqueLetter.matches("[bcmp]")) {
			points = 3;
		}
		if (uniqueLetter.matches("[fhvwy]")) {
			points = 4;
		}
		if (uniqueLetter.matches("[k]")) {
			points = 5;
		}
		if (uniqueLetter.matches("[xj]")) {
			points = 8;
		}
		if (uniqueLetter.matches("[qz]")) {
			points = 10;
		}

		return points;
	}

	/*
	 * public static void main(String[] args) { Score score = new Score (0, 0);
	 * System.out.println(score.scoreGame("zebra")); }
	 */
}
