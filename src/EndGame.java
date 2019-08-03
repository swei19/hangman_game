import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Calvin Chan, Denes Marton, Spencer Wei The class below is responsible
 *         for displaying the ending credits of the game
 */

public class EndGame {

	final static String HIGH_SCORE_FILE_NAME = "HighScores.txt";
	final static int NUM_HIGH_SCORES_TO_DISPLAY = 10;
	private static ArrayList<HighScore> scoreBoard;
	private HighScore currentScore;

	/**
	 * EndGame constructor that takes in a high score object which then calls the
	 * initScoreList method which reads in a a text file containing previous high
	 * scores.
	 * 
	 * @param highScore
	 */

	public EndGame(HighScore highScore) {
		scoreBoard = new ArrayList<HighScore>();
		this.currentScore = highScore;
		initScoreList();
	}

	/**
	 * The below methods reads in a text file containing previous high scores
	 * obtained and save them to the scoreBoard ArrayList
	 */

	public void initScoreList() {

		File file = new File(HIGH_SCORE_FILE_NAME);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("File is already created");
			}
		}

		try {
			Scanner fileReader = new Scanner(new File(HIGH_SCORE_FILE_NAME));
			while (fileReader.hasNextLine()) {
				String currentScoreLine = fileReader.nextLine();
				String[] currentScoreEntry = currentScoreLine.split(" ");
				String playerName;
				int playerScore;

				if (currentScoreEntry.length > 2) {
					playerName = String.join(" ",
							Arrays.copyOfRange(currentScoreEntry, 0, currentScoreEntry.length - 2));
					playerScore = Integer.parseInt(currentScoreEntry[currentScoreEntry.length - 1]);
				} else {
					playerName = currentScoreEntry[0];
					playerScore = Integer.parseInt(currentScoreEntry[1]);

				}

				HighScore highScore = new HighScore(playerName, playerScore);
				scoreBoard.add(highScore);
			}
			scoreBoard.sort(null);
			fileReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The below method adds the score of the current game to the scoreBoard
	 * ArrayList and sorts it in descending order using the comparator in the
	 * HighScore class
	 * 
	 * @see HighScore#compareTo(Object)
	 */
	public void addToScoreAndSort() {
		scoreBoard.add(currentScore);
		scoreBoard.sort(null);
	}

	/**
	 * The below method creates a new JPanel which will contain the high scores. It
	 * will loop through the scoreBoard data structure and for each score, create a
	 * new JLabel which is then added to the JPanel.
	 * 
	 * @return the panel containing the JLabels which contains the scores and will
	 *         be used to display the scores
	 */

	public JPanel getDisplayScorePanel() {

		JPanel scorePanel = new JPanel();
		GridLayout scorePanelLayout = new GridLayout(NUM_HIGH_SCORES_TO_DISPLAY, 1);
		scorePanel.setLayout(scorePanelLayout);
		scorePanelLayout.setVgap(10);
		String labelText = "";

		for (int i = 0; i < scoreBoard.size(); i++) {
			HighScore currentHighScore = scoreBoard.get(i);

			if (i != 9) {
				labelText = "  " + (i + 1) + ". " + currentHighScore.getName() + ": " + currentHighScore.getScore();
			} else {
				labelText = (i + 1) + ". " + currentHighScore.getName() + ": " + currentHighScore.getScore();

			}

			JLabel currentScore = new JLabel(labelText);

			scorePanel.add(currentScore);

			if (i == NUM_HIGH_SCORES_TO_DISPLAY - 1) {
				break;
			}
		}
		return scorePanel;
	}

	/**
	 * Display's the game credits
	 * 
	 * @return the game credits
	 */

	public static String getDisplayCredits() {
		return ("This game is brought to you by Calvin, Denes, and Spencer. Special Thanks for our TA Gabrielle! :D");
	}

	/**
	 * The below method will be called in order to save a new high score. It will
	 * loop through the scoreBoard and write each score to a textfile which will
	 * allow the game to access previous high scores
	 * 
	 */
	public void writeToScoreTextFile() {
		try {
			PrintWriter pw = new PrintWriter(new File(HIGH_SCORE_FILE_NAME));

			for (int i = 0; i < scoreBoard.size(); i++) {
				HighScore currentHighScore = scoreBoard.get(i);
				pw.println(currentHighScore.getName() + " " + currentHighScore.getScore());
				if (i == NUM_HIGH_SCORES_TO_DISPLAY - 1) {
					break;
				}
			}
			pw.flush();
			pw.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	/**
	 * @return the scoreBoard that contains the current high scores
	 */

	public static ArrayList<HighScore> getScoreBoard() {
		return scoreBoard;
	}

}
