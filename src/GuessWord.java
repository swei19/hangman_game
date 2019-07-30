import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 * @author Calvin Chan, Denes Marton, Spencer Wei 
 * The class below will be responsible for randomly selecting a word from a list of words based
 * on a category chosen by a player The player will then need to guess this word
 */

public class GuessWord {
	private final static String[] possibleCategories = { "Places", "Countries", "Food", "Animals" };
	private final static int numPossibleCategories = possibleCategories.length; 
	private String chosenCategory = "";
	private ArrayList<String> wordsOfCategory;
	private String currentPathToTextFiles;
	private Set<String> uniqueLettersOfSelectedWord;
	

	/**
	 * Based on the category chosen by the player, this class is responsible for
	 * looping loop through the relevant .txt file using the index obtained and adding 
	 * those words to the wordsOfCategory ArrayLists
	 * 
	 * @param categoryChosen category of words that will be chosen by player
	 */

	public GuessWord(String categoryChosen) {
		int indexOfChosenCategory = Arrays.asList(possibleCategories).indexOf(categoryChosen);
		this.chosenCategory = possibleCategories[indexOfChosenCategory];
		this.wordsOfCategory = new ArrayList<String>();
		this.currentPathToTextFiles = "wordLists\\" + chosenCategory + ".txt";
		initWordList();
		
		this.uniqueLettersOfSelectedWord = new HashSet<String>();
		
	}

	/**
	 * Opens a .txt file containing words of the chosen category and loops through
	 * each word in the .txt file to the 'wordsOfCategory' ArrayList
	 */

	public void initWordList() {
		try {
			Path path = Paths.get(currentPathToTextFiles).toAbsolutePath();
			Scanner s = new Scanner(new File(path.toString()));

			while (s.hasNext()) {
				wordsOfCategory.add(s.next());
			}
		} catch (FileNotFoundException e) {
			System.out.println(chosenCategory + ".txt is not found. Please ensure it is in the wordList folder");
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
		int randomIndex = random.nextInt(wordsOfCategory.size());
		
		String selectedWord = wordsOfCategory.get(randomIndex).toUpperCase();
		String[] selectedWordArr = selectedWord.split("");
		
		for (int i = 0; i < selectedWordArr.length; i++) {
			this.uniqueLettersOfSelectedWord.add(selectedWordArr[i]);
		}
		
		return selectedWord;
	}
	
	public static String displayPossibleCategories() {
		String categoryDisplay = "";
		for (int i = 0; i < numPossibleCategories; i++) {
			categoryDisplay += (i + 1) + ". " + possibleCategories[i] + "\n";
		}
		
		return categoryDisplay;
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
