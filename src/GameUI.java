import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import layout.SpringUtilities;

/**
 * @author Calvin Chan, Denes Marton, Spencer Wei The class below will handle
 *         the main game UI after the player has entered his or her name and
 *         selected a category from the startGameUI class It will be responsible
 *         for displaying the buttons that will allow the user to interact with
 *         the game (to guess letters), displaying the letter the player must
 *         guess itself and any scoring information. It will also display the
 *         appropriate image as the user guesses correctly or incorrectly
 * 
 */

public class GameUI {

	final static int NUMBER_OF_WRONG_GUESSES_ALLOWED = 6;
	private JFrame overallFrame;
	private static JPanel hangmanPanel;
	private JPanel trackingPanel;
	private JButton nextGameButton;

	private static Hangman hm = new Hangman();

	private Score score;
	private int currentScore;
	private String playerName;
	private int numWrongGuesses = 0;
	private ArrayList<String> guessedLetters = new ArrayList<String>();
	private String categoryChosen;
	String currentWord;
	int previousScore;

	public GameUI(String playerName, String categoryChosen, JFrame overallFrame) {
		this.playerName = playerName;
		this.categoryChosen = categoryChosen;
		hangmanPanel = hm.readImage(0, false);
		this.overallFrame = overallFrame;
	}

	/**
	 * This method handles the overall flow of the game. A guessword is randomly
	 * selected. Panels that house the buttons for the letters of the alphabet , the
	 * images of the man being hanged and score tracking are in this method. This
	 * handles both modes of game play, competitive and casual.
	 * 
	 * Each letter button will have an action listener which trigger several events
	 * when pressed. They include updating the score and number of wrong guesses,
	 * checking if the game has been won or lost and calling methods that is
	 * responsible for displaying the appropriate graphics
	 * 
	 */
	public void initMainUI() {

		GuessWord gw = new GuessWord(categoryChosen);

		BorderLayout frameLayout = new BorderLayout();

		currentWord = gw.selectWord();

		// int carryOnScore, boolean isCompetitive, int numWrongGuessee;
		if (categoryChosen == "Competitive") {
			System.out.println("1:" + currentScore);
			previousScore = currentScore;
			score = new Score(true);
		} else {
			score = new Score(false);
			previousScore = 0;
		}

		// currentScore = score.scoreGame(currentWord, numWrongGuesses, guessedLetters)
		// + previousScore;

		Display display = new Display(currentWord);
		Set<String> uniqueLettersOfCurrentWord = gw.getUniqueLettersOfSelectedWord();

		JLabel currentDisplay = new JLabel(display.displayLetterAndEmptyWordUnderlines(guessedLetters));
		currentDisplay.setHorizontalAlignment(JLabel.CENTER);
		currentDisplay.setVerticalAlignment(JLabel.CENTER);

		overallFrame.getContentPane().removeAll();
		overallFrame.getContentPane().repaint();
		overallFrame.setLayout(frameLayout);

		GridBagConstraints c = new GridBagConstraints();

		trackingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
		JLabel nameDisplayLabel = new JLabel(playerName);
		JLabel scoreTracking = new JLabel("Current Score: " + currentScore);
		JLabel numTriesTracking = new JLabel("Wrong Guesses: " + numWrongGuesses);

		trackingPanel.add(nameDisplayLabel);
		trackingPanel.add(scoreTracking);
		trackingPanel.add(numTriesTracking);

		trackingPanel.add(newGamePanel());
		overallFrame.add(trackingPanel, BorderLayout.NORTH);
		overallFrame.add(hangmanPanel, BorderLayout.CENTER);
		// overallFrame.add(newGamePanel(), BorderLayout.EAST);

		JPanel buttonAndGuessWordPanel = new JPanel(new GridBagLayout());
		JPanel topButtonsPanel = new JPanel(new SpringLayout());
		JPanel otherButtonsPanel = new JPanel(new SpringLayout());

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(10, 10, 10, 10);

		buttonAndGuessWordPanel.add(currentDisplay, c);

		for (int i = 0; i < 26; i++) {

			String currentLetter = Character.toString((char) (i + 65));

			JButton currentButton = new JButton(currentLetter);
			currentButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					Score updateScore = new Score(false);
					int updatedScore = updateScore.scoreGame(currentWord, numWrongGuesses, guessedLetters);

					if (numWrongGuesses != NUMBER_OF_WRONG_GUESSES_ALLOWED) {
						guessedLetters.add(currentButton.getText());
						currentDisplay.setText(display.displayLetterAndEmptyWordUnderlines(guessedLetters));

						if (!uniqueLettersOfCurrentWord.contains(currentButton.getText())) {
							numWrongGuesses += 1;
							System.out.println("Wrong Guesses: " + numWrongGuesses);

							changeImage(false);
						}
						updatedScore = updateScore.scoreGame(currentWord, numWrongGuesses, guessedLetters)
								+ previousScore;

						if (numWrongGuesses == NUMBER_OF_WRONG_GUESSES_ALLOWED) {
							scoreTracking.setText("Current Score: " + previousScore);
							currentScore = previousScore;
							if (categoryChosen == "Competitive") {
								nextGameButton.setText("Highscore");
							}

						} else {
							scoreTracking.setText("Current Score: " + updatedScore);
						}

						numTriesTracking.setText("Num Wrong Guesses: " + numWrongGuesses);
						overallFrame.setVisible(true);
						currentButton.setEnabled(false);

						if (gameIsWon(uniqueLettersOfCurrentWord)) {
							changeImage(true);

							nextGameButton.setEnabled(true);

							if (categoryChosen == "Competitive") {
								nextGameButton.setText("Next");
								nextGameButton.setVisible(true);
							}

						}
					}
					if (numWrongGuesses == NUMBER_OF_WRONG_GUESSES_ALLOWED)
						if (categoryChosen != "Competitive") {
							updatedScore = 0;
							scoreTracking.setText("Current Score: " + updatedScore);
						} else {
							nextGameButton.setVisible(true);
							nextGameButton.setEnabled(true);
						}
				}
			});

			if (i < 6) {
				topButtonsPanel.add(currentButton);
			} else {
				otherButtonsPanel.add(currentButton);
			}
		}

		SpringUtilities.makeGrid(topButtonsPanel, 1, 6, 0, 0, 0, 0);
		SpringUtilities.makeGrid(otherButtonsPanel, 5, 4, 0, 0, 0, 0);

		c.insets = new Insets(0, 0, 0, 0);
		c.gridx = 0;
		c.gridy = 1;
		buttonAndGuessWordPanel.add(topButtonsPanel, c);

		c.insets = new Insets(0, 0, 0, 0);
		c.gridx = 0;
		c.gridy = 2;
		buttonAndGuessWordPanel.add(otherButtonsPanel, c);

		overallFrame.add(buttonAndGuessWordPanel, BorderLayout.SOUTH);
		overallFrame.setVisible(true);
	}

	/**
	 * The reinit class will initialize the GUI of the current instance of the game
	 */
	public void reinit() {
		overallFrame = new JFrame("Hangman");
		overallFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		overallFrame.setSize(620, 520);
		overallFrame.setVisible(true);
		overallFrame.requestFocusInWindow();
	}

	/**
	 * This method determines if the game has been won
	 * 
	 * @param uniqueLettersOfSelectedWord A set of unique letters of the guess word
	 * @return returns true if the set of guessed letters contains all the unique
	 *         letters of the guess word returns false otherwise
	 */
	public boolean gameIsWon(Set<String> uniqueLettersOfSelectedWord) {

		for (String uniqueLetters : uniqueLettersOfSelectedWord) {
			if (!guessedLetters.contains(uniqueLetters)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This method changes the image file once a letter of the guess word is
	 * correctly selected. Shows a separate image file for when the game is won,
	 * when the game is lost and when the number of wrong guesses is incremented.
	 * 
	 * @param gameIsWon boolean that is true if all the letters of the word are
	 *                  guessed before the number of tries is exceeded
	 */
	public void changeImage(boolean gameIsWon) {
		overallFrame.remove(hangmanPanel);
		overallFrame.revalidate();
		overallFrame.repaint();

		if (gameIsWon) {
			JLabel wonMessage = new JLabel("You won!");

			hangmanPanel = hm.readImage(numWrongGuesses, true);

			trackingPanel.add(wonMessage);
		} else if (numWrongGuesses == NUMBER_OF_WRONG_GUESSES_ALLOWED) {

			JLabel loseMessage = new JLabel("The man has been hanged :'(");

			hangmanPanel = hm.readImage(numWrongGuesses, false);
			overallFrame.add(hangmanPanel);
			overallFrame.revalidate();
			trackingPanel.add(loseMessage);
		}

		else {
			hangmanPanel = hm.readImage(numWrongGuesses, false);
		}

		overallFrame.add(hangmanPanel);
		overallFrame.revalidate();
		overallFrame.repaint();

	}

	public JPanel newTrackingPanel() {
		return null;
	}

	public String getCurrentWord() {
		return currentWord;
	}

	public void setCurrentWord(String currentWord) {
		this.currentWord = currentWord;
	}

	public int getNumWrongGuesses() {
		return numWrongGuesses;
	}

	public void setNumWrongGuesses(int numWrongGuesses) {
		this.numWrongGuesses = numWrongGuesses;
	}

	public ArrayList<String> getGuessedLetters() {
		return guessedLetters;
	}

	public void setGuessedLetters(ArrayList<String> guessedLetters) {
		this.guessedLetters = guessedLetters;
	}

	/**
	 * The next game button will contain an action listener that will call the
	 * appropriate class to either start a completely new game (when in casual mode)
	 * or to go to the next game that carry on any of the previous scores obtained
	 * by the player (competitive mode)
	 */
	public JPanel newGamePanel() {
		JPanel nextPanel = new JPanel();
		nextGameButton = new JButton("");

		if (categoryChosen != "Competitive") {
			nextGameButton.setText("New Game");

		} else {
			nextGameButton.setVisible(false);
			nextGameButton.setEnabled(false);
			nextGameButton.setText("");

		}

		nextGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				overallFrame.dispose();
				if (categoryChosen != "Competitive") {
					StartGameUI newGame = new StartGameUI();
					newGame.initStartGameUI();
				} else {

					if (numWrongGuesses == NUMBER_OF_WRONG_GUESSES_ALLOWED) {
						EndGameUI endingUI = new EndGameUI(playerName, currentScore); // currentScore here is incorrect,
																						// placeholder
						endingUI.displayBoard();

					} else {
						nextGameButton.setVisible(true);

						int previousGameScore = score.scoreGame(currentWord, numWrongGuesses, guessedLetters)
								+ previousScore;
						reinit();
						GameUI newGameUI = new GameUI(playerName, categoryChosen, overallFrame);
						newGameUI.currentScore = previousGameScore;
						System.out.println("previous: " + previousGameScore);
						newGameUI.initMainUI();
						// Score updateScore = new Score(previousScore, true, numWrongGuesses);
						// gameUI.currentScore = previousScore;

					}
				}
			}

		});

		nextPanel.add(nextGameButton);

		return nextPanel;
	}

}
