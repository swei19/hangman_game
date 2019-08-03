import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import layout.SpringUtilities;

/**
 * This class displays the first user interface page and allows the user to
 * enter their name, select mode of play and a category of words to choose from
 * @author Spencer Wei, Denes Marton, Calvin Chan
 *
 */
public class StartGameUI {
	final static int NUMBER_OF_WRONG_GUESSES_ALLOWED = 6;
	private JFrame overallFrame;
	private JLabel headerLabel;
	private JLabel noCatChosenMsg = new JLabel("");
	private JTextField nameField;

	private String playerName = "";
	private String categoryChosen = "";

	/**
	 * This method creates the overall frame where the rest of the components and panels will be placed
	 */
	public void initStartGameUI() {
		overallFrame = new JFrame("Hangman");
		overallFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		overallFrame.setSize(620, 520);
		overallFrame.add(getStartGamePanel());
		overallFrame.setVisible(true);
		overallFrame.requestFocusInWindow();
	}

	/**
	 * This method is the JPanel where the user can enter their name and start a new game.
	 * @return startGamePanel JPanel with text field to enter player name and new game button to start the game
	 */
	public JPanel getStartGamePanel() {

		GridBagLayout gridBagLayout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		JPanel startGamePanel = new JPanel();
		startGamePanel.setLayout(gridBagLayout);

		nameField = new JTextField("Please enter a name");
		nameField.setPreferredSize(new Dimension(130, 30));

		if (playerName != "") {
			nameField.setText(playerName);
		}

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
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 0, 0, 0);
		headerLabel = new JLabel("Hangman Game");
		startGamePanel.add(headerLabel, c);

		c.weightx = 0;
		c.weighty = 0;
		c.gridy = 3;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(20, 0, 0, 0);
		c.gridwidth = 2;

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
				if (categoryChosen != "") {

					GameUI mainUI = new GameUI(playerName, categoryChosen, overallFrame);
					mainUI.initMainUI();
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

		return startGamePanel;

	}

	/**
	 * This method is the JPanel that displays buttons and allows the player to select the
	 * mode of game and if applicable, the category from which a random word will be selected.
	 * @return modePanel JPanel with buttons for player to select mode of game and category of the word, if applicable
	 */
	public JPanel getModePanels() {

		JPanel modePanel = new JPanel(new SpringLayout());
		JButton competitiveButton = new JButton("Competitive Mode");
		JButton casualButton = new JButton("Casual Mode");

		casualButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				for (int i = 0; i < modePanel.getComponents().length; i++) {
					modePanel.getComponent(i).setVisible(true);
				}
				
				casualButton.setEnabled(false);
				categoryChosen = "";

				competitiveButton.setEnabled(true);

			}
		});

		competitiveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				for (int i = 0; i < modePanel.getComponents().length; i++) {

					modePanel.getComponent(i).setEnabled(true);

				}

				for (int i = 0; i < modePanel.getComponents().length; i++) {
					modePanel.getComponent(i).setVisible(false);
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

		String[] wordCategories = GuessWord.getPossibleCategories();
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
}
