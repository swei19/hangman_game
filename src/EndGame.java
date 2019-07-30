import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Calvin Chan, Denes Marton, Spencer Wei The class below is responsible
 *         for displaying the ending credits of the game
 */

public class EndGame {

	final static String highScoreFileName = "HighScores.txt";
	final static int numHighScoresToDisplay = 10;
	private static ArrayList<HighScore> scoreBoard;
	private HighScore currentScore;

	public EndGame(HighScore highScore) {
		scoreBoard = new ArrayList<HighScore>();
		
		this.currentScore = highScore;
		initScoreList();
	}

	public void initScoreList() {
		
		File file = new File(highScoreFileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("File is already created");
			}
		}
		
		try {
			Scanner fileReader = new Scanner(new File(highScoreFileName));
			while (fileReader.hasNextLine()) {
				String currentScoreLine = fileReader.nextLine();
				String[] currentScoreEntry = currentScoreLine.split(" ");
				String playerName;
				int playerScore;
				
				if (currentScoreEntry.length > 2) {
					playerName = String.join(" ", Arrays.copyOfRange(currentScoreEntry,0,currentScoreEntry.length - 2));
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
	
	public void addToScoreAndSort() {
		scoreBoard.add(currentScore);
		scoreBoard.sort(null);
	}
	
	
	public void displayScore() {
		for (int i = 0; i < scoreBoard.size(); i++) {
			HighScore currentHighScore = scoreBoard.get(i);
			System.out.println((i + 1) + "." + currentHighScore.getName() + ": " + currentHighScore.getScore());
			if (i == numHighScoresToDisplay - 1) {
				break;
			}
		}
	}
	
	public static void displayCredits() {
		System.out.println("This game is brought to you by Calvin, Denes, and Spencer. Special Thanks for our TA Gabrielle!:D");
	}
	

	public void writeToScoreTextFile() {
		try {
			PrintWriter pw = new PrintWriter(new File(highScoreFileName));
			
			for (int i = 0; i < scoreBoard.size() ; i++) {
				HighScore currentHighScore = scoreBoard.get(i);
				pw.println(currentHighScore.getName() + " " + currentHighScore.getScore());
				if (i == numHighScoresToDisplay - 1) {
					break;
				}
			}
			pw.flush();
			pw.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	
	public static ArrayList<HighScore> getScoreBoard() {
		return scoreBoard;
	}
	
	/* Uncomment this code to see what the code does :)
	 * public static void main(String[] args) { EndGame endGame = new
	 * EndGame("Garfield", 100); endGame.initScoreList(); endGame.displayScore();
	 * endGame.writeToScoreTextFile(); }
	 */

}
