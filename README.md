The is the README from Spencer Wei, Denes Marton and Calvin Chan for our Summer 2019 - 591 final design project.

The Hangman game is started from the main method in the Runner.java class. Download the folders “src”, “bin”, “images”, “WordLists” and the “HighScores.txt” file and ensure all of the above listed are on the same folder level.

Once the game is started, the user can enter their name and choose between competitive and casual mode. 

Casual mode allows the user to play rounds separately. They will have the option to select what kind of category they would like their word to be from. 


Game play in casual and competitive mode is the same. The only difference is competitive mode allows the player to carry over their score into the next round if they win the current round and casual mode starts a new game and resets the scoring after each game.

Once a new game is started, the user guesses what the word is by selecting letters. The number of underlines underneath the image signify how many letters are in the word. If the selected letter is in the word, that letter will replace the underline and points will be added for the round. Point values are linked to each letter with more common letters (ex. a, e, i, o, u) worth less than uncommon letters (ex. z, x, q). The current score and number of wrong guesses are shown on the header above the hangman display. If a correct letter is guessed, the points associated with that letter are added to the score. If a wrong letter is selected, 1 point will be deducted for each wrong guess. If the player loses the round, the score is 0. If a wrong letter is chosen, a body part of the hangman will appear and the number of wrong guesses will be incremented. Letters that have been chosen will have their button disabled so the player can keep track of which letters have already been chosen. The user always has the option to select a new game by utilizing the “New Game” button, even in the middle of the round. 

The player wins the game by correctly guessing all the letters of the word before the image of the man being hanged is completed.

Competitive mode offers the same game play but the score carries over to the next round. If the player wins the round, an option to go to the next game appears. When a round is lost the user selects “High Score” and a message will appear to let them know they got into the high score board. If their cumulative score is high enough, their name and score will be entered into the high score board.

The top 10 scores on the high score board will be displayed. The user also has the option to start a new game from this page.



























Summary 

Description

Hangman is a game where a word is randomly selected and the player will then need to guess that word. The word that the player has to guess is at first, displayed with empty word underlines (one for each letter of the word) and the player is then allowed to guess one letter one at a time. Each time the player guesses a letter correctly, the empty underline containing that letter will be displayed in place. If the player guesses incorrectly, a body part of a person is is drawn or bolded. After 6 unsuccessful attempts, the player loses and an image showing the person being “hung” is displayed.

In our version of the game, we introduced two modes. The casual mode, where the word the player has to guess is drawn by the category chosen by the player. There is also a competitive mode, where the game randomly decide a word from any category. In casual mode, the game ends after one round, when either the player successfully guessed the word or loses (after 6 unsuccessful guesses). The player is then given a score based on the letters the player guessed correctly.

In competitive however, the game will continue after winning or losing to the next round, and each time, a different word is randomly selected for the user to guess. The score from previous rounds will carry forward, and the game will continue until the player loses a round. Afterwards, the player will be entered into a high score board, if the player’s score is high enough.
