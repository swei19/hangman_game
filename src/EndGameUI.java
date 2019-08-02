import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class is responsible for ending the game from competitive mode, 
 * displaying a message whether the play got onto the high score board and
 * deciding if the score is high enough to enter into the high score board
 * Also allows the user to start a new game from this page.
 * 
 * @author Spencer Wei, Denes Marton, Calvin Chan
 *
 */
public class EndGameUI {
	String playerName;
	int playerScore;
	static JFrame endGameFrame = new JFrame();

	JPanel highScorePanel = new JPanel();
	JPanel headerPanel = new JPanel();
	JPanel newGamePanel = new JPanel();

	public EndGameUI(String playerName, int playerScore) {

		endGameFrame = new JFrame("Hangman");
		endGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		endGameFrame.setSize(620, 520);
		endGameFrame.setVisible(true);
		endGameFrame.requestFocusInWindow();

		this.playerName = playerName;
		this.playerScore = playerScore;
	}

	/**
	 * This method displays the high scores from the file and determines if the 
	 * player's score is high enough to enter the high score board. 
	 */
	public void displayBoard() {

		HighScore highScore = new HighScore(playerName, playerScore);

		GridBagLayout highScorePanelLayout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		endGameFrame.setLayout(highScorePanelLayout);

		EndGame endGame = new EndGame(highScore);
		int scoreToBeat;
		int numOfScores = EndGame.getScoreBoard().size();

		if (numOfScores < EndGame.NUM_HIGH_SCORES_TO_DISPLAY) {
			scoreToBeat = -1;
		} else {
			scoreToBeat = EndGame.getScoreBoard().get(EndGame.NUM_HIGH_SCORES_TO_DISPLAY - 1).getScore();
		}

		JLabel scoreBoardMsg = new JLabel("");
		if (playerScore > scoreToBeat) {

			endGame.addToScoreAndSort();
			endGame.writeToScoreTextFile();
			scoreBoardMsg.setText("Awesome, you got into the High Score Board!");

		} else {
			scoreBoardMsg.setText("Sorry, you were not able to get into the High Score Board this game");
		}

		headerPanel.add(scoreBoardMsg);
		highScorePanel.add(endGame.getDisplayScorePanel());

		JLabel credits = new JLabel(EndGame.getDisplayCredits());
		credits.setText(EndGame.getDisplayCredits());

		c.gridx = 1;
		c.gridy = 3;
		c.insets = new Insets(-50, 0, 0, 0);
		endGameFrame.add(credits, c);

		JButton newGameButton = new JButton("New Game");

		c.gridx = 1;
		c.gridy = 0;
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(10, 0, 0, 0);

		newGamePanel.add(newGameButton);
		endGameFrame.add(newGamePanel, c);

		newGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				endGameFrame.dispose();
				StartGameUI newGame = new StartGameUI();
				newGame.initStartGameUI();
			}
		});

		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 1;
		c.gridy = 2;
		c.weighty = 1;
		c.insets = new Insets(-100, 0, 0, 0);

		endGameFrame.add(highScorePanel, c);

		c.gridx = 1;
		c.gridy = 1;
		c.weighty = 0.5;
		c.insets = new Insets(0, 0, 0, 0);

		endGameFrame.add(headerPanel, c);

	}

	/*
	 * public static void main(String[] args) { endGameFrame = new
	 * JFrame("Hangman");
	 * endGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 * endGameFrame.setSize(620, 520); EndGameUI eg = new EndGameUI("Random", 0);
	 * eg.displayBoard(); endGameFrame.setVisible(true);
	 * endGameFrame.requestFocusInWindow(); }
	 */

}
