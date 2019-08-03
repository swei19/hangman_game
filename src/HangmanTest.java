import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import javax.swing.JFrame;
import org.junit.jupiter.api.Test;

/**
 * @author Calvin Chan, Denes Marton, Spencer Wei Contains several units test to
 *         test the GuessWord and Score class
 */

class HangmanTest {

	/**
	 * Test that that the selectWord method from the GuessWord class returns an
	 * expected word from the chosen category
	 */

	@Test
	void testGuessWord1() {	
		HashMap<String, ArrayList<String>> wordsExpected = new HashMap<String, ArrayList<String>>();
		String[] wordCategories = {"Animals", "Countries", "Food", "Places"};
		
		for (String category: wordCategories) {
			Path pathToFile = Paths.get("wordLists\\" + category + ".txt");
			ArrayList<String> wordsOfCategory = new ArrayList<String>();
			try {
				Scanner s = new Scanner(pathToFile.toFile());
				while (s.hasNext()) {
					wordsOfCategory.add(s.next().toLowerCase());
				}
				wordsExpected.put(category, wordsOfCategory);
				s.close();
			} catch (FileNotFoundException e) {
				System.out.println("File not found, please ensure the " + category + ".txt file is located"
									+ "in at " + pathToFile.toString());
			}
		}
		
		String gwAnimals = new GuessWord("Animals").selectWord().toLowerCase();
		String gwCountries = new GuessWord("Countries").selectWord().toLowerCase();
		String gwFood = new GuessWord("Food").selectWord().toLowerCase();
		String gwPlaces = new GuessWord("Places").selectWord().toLowerCase();
		
		assertTrue(wordsExpected.get("Animals").contains(gwAnimals));
		assertTrue(wordsExpected.get("Countries").contains(gwCountries));
		assertTrue(wordsExpected.get("Food").contains(gwFood));
		assertTrue(wordsExpected.get("Places").contains(gwPlaces));
	}
	
	/**
	 * Test that in competitive mode, the word selected is from any of the
	 * categories 
	 */
	@Test
	void testGuessWord2() {
		
		ArrayList<String> wordsExpected = new ArrayList<String>();
		String[] wordCategories = {"Animals", "Countries", "Food", "Places"};
		for (String category: wordCategories) {
			Path pathToFile = Paths.get("wordLists\\" + category + ".txt");

			try {
				Scanner s = new Scanner(pathToFile.toFile());
				while (s.hasNext()) {
					wordsExpected.add(s.next().toLowerCase());
				}
				s.close();
			} catch (FileNotFoundException e) {
				System.out.println("File not found, please ensure the " + category + ".txt file is located"
									+ "in at " + pathToFile.toString());
			}		
		}
		String gwSelectedWord = new GuessWord("Competitive").selectWord().toLowerCase();
		assertTrue(wordsExpected.contains(gwSelectedWord));
	}
	
	

	/**
	 * Test that the scoreGame method returns the correct max score
	 */
	 @Test
	 void testScoreGame1() { 
		 Score score = new Score(true);
		 ArrayList<String> guessedLetters = new ArrayList<>();
		 guessedLetters.add("z");
		 guessedLetters.add("e");
		 guessedLetters.add("b");
		 guessedLetters.add("r");
		 guessedLetters.add("a"); 
		 assertEquals(16, score.scoreGame("zebra", 0, guessedLetters));
	 }
	 
	
	/**
	 * Test that the scoreGame method from the score class returns 0 when the user
	 * number of guesses exceeds the length of word
	 * 
	 * @see Score#scoreGame()
	 */

	  @Test 
	  void testScoreGame2() { 
		  Score score = new Score(true); 
		  ArrayList<String> guessedLetters = new ArrayList<>();
		  guessedLetters.add("k");
		  guessedLetters.add("g");
		  assertEquals(2, score.scoreGame("kangaroo", 5, guessedLetters)); 
	  }
	  
	  /**
	   * This tests if the gets a negative score, the stored score is 0 instead of
	   * negative. "g" is worth 2 points but there are 5 wrong guesses so score
	   * is 2-5 = -3 but if it is negative, we return a score of 0.
	   */
	  @Test 
	  void testScoreGame3() { 
		  Score score = new Score(true);
		  ArrayList<String> guessedLetters = new ArrayList<>();
		  guessedLetters.add("g");
		  assertEquals(0, score.scoreGame("kangaroo", 5, guessedLetters)); 
	  }
	  
	  /**
	   * Tests to ensure that a word with a letter guessed correctly and containing
	   * multiple of that letters, returns the points of that letter times the number
	   * of times that letter appears in the word
	   */
	  
	  @Test 
	  void testScoreGame4() { 
		  Score score = new Score(false); 
		  ArrayList<String> guessedLetters = new ArrayList<>();
		  guessedLetters.add("z");
		  assertEquals(50, score.scoreGame("zzzzz", 0, guessedLetters)); 
	  }
	  
	  /**
	   * This tests if the JPanel that displays the man is null. Should not be null
	   */
	  @Test 
	  void testHangMan1() { 
		  Hangman hm = new Hangman();
		  assertNotNull(hm.readImage(0, false), "This should not be null"); 
	  }
	  
	  /**
	   * This tests that even if somehow the number of wrong guesses exceeds
	   * the limit set in the game, an error message is displayed in the console 
	   * and the "lose" image will be displayed instead
	   */
	  @Test 
	  void testHangMan2() { 
		  Hangman hm = new Hangman();
		  assertNotNull(hm.readImage(7, false), "This should not be null"); 
	  }
	  
	  /**
	   * This tests that if the number of wrong guesses is equal to
	   * the number of wrong guesses allowed, it displays the lose message
	   */
	  @Test 
	  void testHangMan3() { 
		  Hangman hm = new Hangman();
		  hm.readImage(GameUI.NUMBER_OF_WRONG_GUESSES_ALLOWED, false);
		  assertEquals("\\Lose.png", hm.getFileName()); 
	  }
	  
	  /**
	   * This tests if the correct image is displayed for the number of
	   * wrong guesses
	   */
	  @Test 
	  void testHangMan4() { 
		  JFrame overallFrame = new JFrame();
		  GameUI gameUI = new GameUI("playerName", "Animals", overallFrame);
		  Hangman hm = new Hangman();
		  gameUI.setNumWrongGuesses(3);
		  hm.readImage(gameUI.getNumWrongGuesses(), false);
		  assertEquals("\\3.png", hm.getFileName()); 
	  }
	  
	  /**
	   * This tests if the all the correct letters are guessed, and there are extra letters
	   * that aren't in the guessword, then the game is won
	   */
	  @Test 
	  void testGameIsWon1() { 
		  JFrame overallFrame = new JFrame();
		  GameUI gameUI = new GameUI("playerName", "Animals", overallFrame);
		  ArrayList<String> guessedLetters = new ArrayList<>();
		  guessedLetters.add("p");
		  guessedLetters.add("a");
		  guessedLetters.add("r");
		  guessedLetters.add("k");
		  guessedLetters.add("z");
		  gameUI.setGuessedLetters(guessedLetters);
		  
		  Set<String> uniqueLetters = new HashSet<>();
		  uniqueLetters.add("p");
		  uniqueLetters.add("a");
		  uniqueLetters.add("r");
		  uniqueLetters.add("k");
		  assertTrue(gameUI.gameIsWon(uniqueLetters));
	  }
	  
	  /**
	   * This tests if not all the correct letters are guessed, then
	   * gameIsWon is false
	   */
	  @Test 
	  void testGameIsWon2() { 
		  JFrame overallFrame = new JFrame();
		  GameUI gameUI = new GameUI("playerName", "Animals", overallFrame);
		  ArrayList<String> guessedLetters = new ArrayList<>();
		  guessedLetters.add("p");
		  guessedLetters.add("r");
		  gameUI.setGuessedLetters(guessedLetters);
		  
		  Set<String> uniqueLetters = new HashSet<>();
		  uniqueLetters.add("p");
		  uniqueLetters.add("a");
		  uniqueLetters.add("r");
		  uniqueLetters.add("k");
		  assertFalse(gameUI.gameIsWon(uniqueLetters));
	  }
	  
	  /**
	   * This tests that if there are no letters in the guessedLetters array
	   * that gameIsWon still shows false
	   */
	  @Test 
	  void testGameIsWon3() { 
		  JFrame overallFrame = new JFrame();
		  GameUI gameUI = new GameUI("playerName", "Places", overallFrame);
		  ArrayList<String> guessedLetters = new ArrayList<>();
		  gameUI.setGuessedLetters(guessedLetters);
		  
		  Set<String> uniqueLetters = new HashSet<>();
		  uniqueLetters.add("p");
		  uniqueLetters.add("a");
		  uniqueLetters.add("r");
		  uniqueLetters.add("k");
		  assertFalse(gameUI.gameIsWon(uniqueLetters));
	  }
	  
	  /**
	   * This tests when the game is started, a JPanel with the start game UI
	   * will appear
	   */
	  @Test 
	  void startGameUI1() { 
		  StartGameUI startGameUI = new StartGameUI();
		  assertNotNull(startGameUI.getStartGamePanel());
	  }
	  
	  /**
	   * Test to ensure that any high scores added in the EndGame class will be sorted correctly 
	   * accordingly to score and in descending order. 
	   * @see EndGame#addToScoreAndSort()
	   * @see HighScore#compareTo(Object)
	   */
	  @Test
	  void TestHighScoreSort() {
		  HighScore testHS1 = new HighScore("Bob", 10);
		  HighScore testHS2 = new HighScore("Michael", 15);
		  HighScore testHS3 = new HighScore("Rachael", 12);
		  HighScore testHS4 = new HighScore("Kevin", 9);
		  HighScore testHS5 = new HighScore("Katie", 13);
		  
		  ArrayList<HighScore> currentScoreBoard = new ArrayList<HighScore>();
		  ArrayList<HighScore> expectedScoreBoard = new ArrayList<HighScore>();
		  
		  currentScoreBoard.add(testHS1);
		  currentScoreBoard.add(testHS2);
		  currentScoreBoard.add(testHS3);
		  currentScoreBoard.add(testHS4);
		  currentScoreBoard.add(testHS5);
		  currentScoreBoard.sort(null);
 
		  expectedScoreBoard.add(testHS2);
		  expectedScoreBoard.add(testHS5);
		  expectedScoreBoard.add(testHS3);
		  expectedScoreBoard.add(testHS1);
		  expectedScoreBoard.add(testHS4);
		  
		  assertTrue(currentScoreBoard.equals(expectedScoreBoard));
	  }
	  

}
