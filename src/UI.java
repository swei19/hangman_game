import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import layout.SpringUtilities;

public class UI {

	final static int NUMBER_OF_WRONG_GUESSES_ALLOWED = 6;
	private JFrame overallFrame;
	private JPanel trackingPanel;

	private JLabel headerLabel;
	private ArrayList<String> guessedLetters = new ArrayList<String>();
	private String playerName;

	private int numWrongGuesses = 0;
	private static Hangman hm = new Hangman();
	private static JPanel hangmanPanel;

	public static void main(String[] args) {
		UI ui = new UI();
		ui.initStartGameUI();

		hangmanPanel = hm.readImage(0, false);
	}

	private int currentScore;

	public void initStartGameUI() {

		overallFrame = new JFrame("Hangman");

		overallFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		overallFrame.setSize(600, 500);
		GridBagConstraints c = new GridBagConstraints();

		JPanel startGamePanel = new JPanel();
		JTextField nameField = new JTextField("Please enter a name");

		GridBagLayout gridBagLayout = new GridBagLayout();

		startGamePanel.setLayout(gridBagLayout);

		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 0, 0, 0);
		nameField.setMargin(new Insets(5, 5, 5, 5));

		startGamePanel.add(nameField, c);

		c.gridy = 0;

		headerLabel = new JLabel("Hangman Game");
		startGamePanel.add(headerLabel, c);

		c.gridy = 2;
		c.insets = new Insets(20, 0, 0, 0);
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		JButton newGameButton = new JButton("New Game");
		newGameButton.setMargin(new Insets(12, 12, 12, 12));

		nameField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				nameField.setText("");
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if (nameField.getText() == "") {
					nameField.setText("Please enter a name");
				}
			}

		});

		newGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				playerName = nameField.getText();
				initMainUI();

			}
		});

		startGamePanel.add(newGameButton, c);

		overallFrame.add(startGamePanel);

		// overallFrame.pack();

		overallFrame.setVisible(true);
		overallFrame.requestFocusInWindow();
	}

	public void initMainUI() {

		GuessWord gw = new GuessWord("Animals");

		BorderLayout frameLayout = new BorderLayout();

		String currentWord = gw.selectWord();
		Score score = new Score(numWrongGuesses, currentWord.length());
		currentScore = score.scoreGame();

		Display display = new Display(currentWord);
		Set<String> uniqueLettersOfCurrentWord = gw.getUniqueLettersOfSelectedWord();

		JLabel currentDisplay = new JLabel(display.displayLetterAndEmptyWordUnderlines(guessedLetters));
		currentDisplay.setHorizontalAlignment(JLabel.CENTER);
		currentDisplay.setVerticalAlignment(JLabel.CENTER);

		overallFrame.getContentPane().removeAll();
		overallFrame.getContentPane().repaint();
		overallFrame.setLayout(frameLayout);

		GridBagConstraints c = new GridBagConstraints();

		trackingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
		JLabel nameDisplayLabel = new JLabel(playerName);
		JLabel scoreTracking = new JLabel("Current Score: " + currentScore);
		JLabel numTriesTracking = new JLabel("Wrong Guesses: " + numWrongGuesses);

		trackingPanel.add(nameDisplayLabel);
		trackingPanel.add(scoreTracking);
		trackingPanel.add(numTriesTracking);

		overallFrame.add(trackingPanel, BorderLayout.NORTH);
		overallFrame.add(hangmanPanel, BorderLayout.CENTER);

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
					if (numWrongGuesses != NUMBER_OF_WRONG_GUESSES_ALLOWED) {
						guessedLetters.add(currentButton.getText());
						currentDisplay.setText(display.displayLetterAndEmptyWordUnderlines(guessedLetters));

						if (!uniqueLettersOfCurrentWord.contains(currentButton.getText())) {
							numWrongGuesses += 1;
							changeImage(false);
						}

						Score updateScore = new Score(numWrongGuesses, currentWord.length());
						int updatedScore = updateScore.scoreGame();

						scoreTracking.setText("Current Score: " + updatedScore);
						numTriesTracking.setText("Wrong Guesses: " + numWrongGuesses);
						overallFrame.setVisible(true);
						currentButton.setEnabled(false);

						if (gameIsWon(uniqueLettersOfCurrentWord)) {

							changeImage(true);
						}

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

	public boolean gameIsWon(Set<String> uniqueLettersOfSelectedWord) {

		for (String uniqueLetters : uniqueLettersOfSelectedWord) {
			if (!guessedLetters.contains(uniqueLetters)) {
				return false;
			}
		}

		return true;

	}

	public void changeImage(boolean gameIsWon) {
		overallFrame.remove(hangmanPanel);
		overallFrame.revalidate();
		overallFrame.repaint();

		if (gameIsWon) {
			JLabel wonMessage = new JLabel("You won!");

			hangmanPanel = hm.readImage(numWrongGuesses, true);

			trackingPanel.add(wonMessage);
		} else if (numWrongGuesses >= NUMBER_OF_WRONG_GUESSES_ALLOWED) {

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

	public void checkIfHighScore() {
		HighScore highScore = new HighScore(playerName, currentScore);
		EndGame endGame = new EndGame(highScore);
		int scoreToBeat;
		int numOfScores = EndGame.getScoreBoard().size();

		if (numOfScores < EndGame.numHighScoresToDisplay) {
			scoreToBeat = 0;
		} else {
			scoreToBeat = EndGame.getScoreBoard().get(EndGame.numHighScoresToDisplay - 1).getScore();
		}

		if (currentScore > scoreToBeat) {
			endGame.addToScoreAndSort();
			endGame.displayScore();
			endGame.writeToScoreTextFile();
		}
		EndGame.displayCredits();

	}

}
