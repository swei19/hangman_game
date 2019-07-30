import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * @author Calvin Chan, Denes Marton, Spencer Wei 
 * Contains several units test to test the GuessWord and Score class
 */

class HangmanTest {

	/**
	 * Test that that the selectWord method from the GuessWord class returns an
	 * expected word from the chosen category
	 */

	@Test
	void testGuessWord1() {
		String[] wordsExpected = { "Giraffe", "Kangaroo", "Zebra" };
		GuessWord gw = new GuessWord("Animals");
		gw.initWordList();
		assertTrue(Arrays.asList(wordsExpected).contains(gw.selectWord()));
	}

	/**
	 * Test that the scoreGame method from the score class returns 2
	 */
	@Test
	void testScoreGame1() {
		Score score = new Score(3, 5);
		int currentScore = score.scoreGame();
		assertEquals(2, currentScore);
	}

	/**
	 * Test that the scoreGame method from the score class returns 0 when the user
	 * number of guesses exceeds the length of word
	 * 
	 * @see Score#scoreGame()
	 */

	@Test
	void testScoreGame2() {
		Score score = new Score(6, 1);
		int currentScore = score.scoreGame();
		assertEquals(0, currentScore);
	}

}
