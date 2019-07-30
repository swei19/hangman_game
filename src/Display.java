import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JLabel;

/**
 * @author Calvin Chan, Denes Marton, Spencer Wei This class will be responsible
 *         for displaying any graphics of the
 *         game.
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

			};
		}
		System.out.println(stringToPrint);
		return stringToPrint;
		
		
	}






}