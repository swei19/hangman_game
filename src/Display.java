import java.util.ArrayList;

/**
 * @author Calvin Chan, Denes Marton, Spencer Wei This class will be responsible
 *         for displaying the letters of a word if it is guessed correctly and a 
 *         empty underline if the letter has not been guessed yet
 */

public class Display {

	String[] randomWordSelectedArr;

	/**
	 * UserInterface Constructor
	 */
	public Display(String randomWordSelected) {
		this.randomWordSelectedArr = randomWordSelected.split("");
	}

	/**
	 * The below method will display the letters that are guessed correctly and
	 * those that are not yet guessed as underlines
	 */

	public String displayLetterAndEmptyWordUnderlines(ArrayList<String> guessedLetters) {

		String stringToPrint = "";
		for (int i = 0; i < randomWordSelectedArr.length; i++) {

			if (guessedLetters.contains(randomWordSelectedArr[i])) {
				stringToPrint += " " + randomWordSelectedArr[i] + " ";

			} else {
				stringToPrint += " _ ";

			}
			;
		}
		return stringToPrint;

	}

}