import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 * @author Calvin Chan, Denes Marton, Spencer Wei The class below will be
 *         responsible for randomly selecting a word from a list of words based
 *         on a category chosen by a player The player will then need to guess
 *         this word
 */

public class GuessWord {
	private final static String[] possibleCategories = { "Places", "Countries", "Food", "Animals" };
	private final static int numPossibleCategories = possibleCategories.length;
	private ArrayList<String> candidateGuessWords;
	private Set<String> uniqueLettersOfSelectedWord;

	/**
	 * Based on the category chosen by the player and in casual mode, this class is
	 * responsible for looping loop through the relevant .txt file adding those
	 * words to the wordsOfCategory ArrayLists
	 * 
	 * If competitive mode is selected, every word from every category will be added
	 * to wordsOfCategory ArrayList and a random word from this ArrayList will be selected
	 * 
	 * @param categoryChosen category of words that will be chosen by player
	 */

	public GuessWord(String categoryChosen) {
		String currentPathToTextFile;
		this.candidateGuessWords = new ArrayList<String>();
		this.uniqueLettersOfSelectedWord = new HashSet<String>();

		if (categoryChosen == "Competitive") {
			for (String category : possibleCategories) {
				currentPathToTextFile = Paths.get("wordLists\\" + category + ".txt").toString();
				initWordList(currentPathToTextFile);
			}
		} else {
			currentPathToTextFile = Paths.get("wordLists\\" + categoryChosen + ".txt").toString();
			initWordList(currentPathToTextFile);
		}
	}

	/**
	 * Opens a .txt file containing words of the chosen category and loops through
	 * each word in the .txt file to the 'wordsOfCategory' ArrayList
	 */

	public void initWordList(String currentPathToTextFile) {
		try {
			Path path = Paths.get(currentPathToTextFile).toAbsolutePath();
			Scanner s = new Scanner(new File(path.toString()));

			while (s.hasNext()) {
				candidateGuessWords.add(s.next());
			}
		} catch (FileNotFoundException e) {
			System.out.println(currentPathToTextFile + ".txt is not found. Please ensure it is in the wordList folder");
		}
	}

	/**
	 * Randomly chooses a word of a chosen category from the 'wordsOfCategory'
	 * ArrayList and returns it
	 * 
	 * @return a random word that the player will guess
	 */
	public String selectWord() {
		Random random = new Random();
		int randomIndex = random.nextInt(candidateGuessWords.size());

		String selectedWord = candidateGuessWords.get(randomIndex).toUpperCase();
		String[] selectedWordArr = selectedWord.split("");

		for (int i = 0; i < selectedWordArr.length; i++) {
			this.uniqueLettersOfSelectedWord.add(selectedWordArr[i]);
		}

		return selectedWord;
	}

	public static int getNumPossibleCategories() {
		return numPossibleCategories;
	}

	public static String[] getPossibleCategories() {
		return possibleCategories;
	}

	public Set<String> getUniqueLettersOfSelectedWord() {
		return uniqueLettersOfSelectedWord;
	}

}
