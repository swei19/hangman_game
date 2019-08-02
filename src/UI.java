import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
    private static JPanel hangmanPanel;
    private JLabel headerLabel;

    
	private ArrayList<String> guessedLetters = new ArrayList<String>();
	private String playerName = "";
	private int numWrongGuesses = 0;
	private static Hangman hm = new Hangman();
	
	JLabel noCatChosenMsg = new JLabel("");
	
	private String categoryChosen = "";
	private int currentScore;

	public static void main(String[] args) {
		UI ui = new UI();
		ui.initStartGameUI();

		hangmanPanel = hm.readImage(0, false);
	}

	public void newGame () {
		//UI ui = new UI();
		//initStartGameUI();
		guessedLetters.clear();
		numWrongGuesses = 0;
		changeImage(false);
		
	}
	
	public void initStartGameUI(){
		
		overallFrame = new JFrame("Hangman");
		overallFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		overallFrame.setSize(620, 520);

		GridBagConstraints c = new GridBagConstraints();

		JPanel startGamePanel = new JPanel();
		
		JTextField nameField = new JTextField("Please enter a name");
		//nameField.setMinimumSize(new Dimension(100, 25));
		
		if (playerName != "") {
			nameField.setText(playerName);
		}
		

		GridBagLayout gridBagLayout = new GridBagLayout();
		
		startGamePanel.setLayout(gridBagLayout);

		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 0, 0, 0);
		nameField.setMargin(new Insets(5, 5, 5, 5));

		startGamePanel.add(nameField, c);
		
		c.gridy = 2;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 0, 0, 0);
		
		startGamePanel.add(getModePanels(), c);

		c.gridy = 0;

		headerLabel = new JLabel("Hangman Game");
		startGamePanel.add(headerLabel, c);

		c.gridy = 3;
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
				newGame();
				playerName = nameField.getText();
				if (categoryChosen != "") {
					initMainUI();
				} else {
					
					c.gridy = 4;
					c.insets = new Insets(0, 0, 0, 0);
					c.gridwidth = 1;
					c.anchor = GridBagConstraints.SOUTH;
					
					if (noCatChosenMsg.getText() == "") {
						noCatChosenMsg.setText("Please select a category first");
						startGamePanel.add(noCatChosenMsg, c);
						startGamePanel.revalidate();
					}
				}
			}
		});

		startGamePanel.add(newGameButton, c);

		overallFrame.add(startGamePanel);

		// overallFrame.pack();

		overallFrame.setVisible(true);
		overallFrame.requestFocusInWindow();

		
	}

	public void initMainUI() {
		GuessWord gw;
		if (categoryChosen != "Competitive") {
			 gw = new GuessWord(categoryChosen);
		} else {
			gw = new GuessWord("Animals");
		}
		

		BorderLayout frameLayout = new BorderLayout();

		String currentWord = gw.selectWord();
		// we may want to change the arguments of the Score class
		//Score score = new Score(numWrongGuesses, currentWord.length());
		//currentScore = score.scoreGame(currentWord, numWrongGuesses);
		

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
		JLabel scoreTracking = new JLabel("Max Score: " + currentScore);
		JLabel numTriesTracking = new JLabel("Wrong Guesses: " + numWrongGuesses);

		trackingPanel.add(nameDisplayLabel);
		trackingPanel.add(scoreTracking);
		trackingPanel.add(numTriesTracking);
		
		
		trackingPanel.add(newGamePanel());
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
					//Score updateScore = new Score(numWrongGuesses, currentWord.length());
					//int updatedScore = updateScore.scoreGame(currentWord, numWrongGuesses);
					
					if (numWrongGuesses != NUMBER_OF_WRONG_GUESSES_ALLOWED) {
						guessedLetters.add(currentButton.getText());
						currentDisplay.setText(display.displayLetterAndEmptyWordUnderlines(guessedLetters));

						if (!uniqueLettersOfCurrentWord.contains(currentButton.getText())) {
							numWrongGuesses += 1;
							changeImage(false);
						}

						//Score updateScore = new Score(numWrongGuesses, currentWord.length());
						//updatedScore = updateScore.scoreGame(currentWord, numWrongGuesses);

					 // scoreTracking.setText("Current Score: " + updatedScore);
						numTriesTracking.setText("Wrong Guesses: " + numWrongGuesses);
						overallFrame.setVisible(true);
						currentButton.setEnabled(false);

						if (gameIsWon(uniqueLettersOfCurrentWord)) {

							changeImage(true);
						}

					}
					if (numWrongGuesses == NUMBER_OF_WRONG_GUESSES_ALLOWED) {
						//updatedScore = 0;
						//scoreTracking.setText("Current Score: " + updatedScore);
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

	
	
	public JPanel newGamePanel() {
		JPanel endGamePanel = new JPanel();
		JButton newGameButton = new JButton("New Game");
		
		newGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				overallFrame.dispose();
				initStartGameUI();
			}
			
		
			
		});
		
		endGamePanel.add(newGameButton);
		
		return endGamePanel;
	}
	
	
	public JPanel getModePanels() {

		JPanel modePanel = new JPanel(new SpringLayout());
		JButton competitiveButton = new JButton("Competitive Mode");
		JButton casualButton = new JButton("Casual Mode");
		
		//CC Added
		casualButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				for (int i = 0; i < modePanel.getComponents().length; i++) {
					modePanel.getComponent(i).setVisible(true);
				}
				categoryChosen = "";
				casualButton.setEnabled(false);
				competitiveButton.setEnabled(true);
				
			}
		});
		
		competitiveButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				for (int i = 0; i < modePanel.getComponents().length; i++) {
					
					modePanel.getComponent(i).setEnabled(true);
					
				} 
				
				for (int i = 0; i < modePanel.getComponents().length; i++) {
					if (modePanel.getComponent(i) instanceof JTextField) {
						modePanel.getComponent(i).setVisible(false);
					}
				}
				
				competitiveButton.setVisible(true);
				casualButton.setVisible(true);
			
				
				competitiveButton.setEnabled(false);
				categoryChosen = "Competitive";
				noCatChosenMsg.setText("");
				overallFrame.revalidate();
				overallFrame.repaint();
			}
			
		});
		
		modePanel.add(competitiveButton);
		modePanel.add(casualButton);
	
		
		String [] wordCategories = GuessWord.getPossibleCategories();
		for (int i = 0; i < wordCategories.length; i++) {
			JButton currentCatButton = new JButton(wordCategories[i]);
			
			currentCatButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					categoryChosen = currentCatButton.getText();
				
					for (int i = 0; i < modePanel.getComponents().length; i++) {
						modePanel.getComponent(i).setEnabled(true);
						casualButton.setEnabled(false);
					} 
					currentCatButton.setEnabled(false);
					noCatChosenMsg.setText("");
					overallFrame.revalidate();
					overallFrame.repaint();
					
				}
				
			});

			currentCatButton.setVisible(false);
			modePanel.add(currentCatButton);
		}
		
		SpringUtilities.makeGrid(modePanel, 3, 2, 0, 0, 0, 0);
		
		return modePanel;

	}

	public int getNumWrongGuesses() {
		return numWrongGuesses;
	}

	public int getCurrentScore() {
		return currentScore;
	}
	
	
	
}
