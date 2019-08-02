import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class selects the correct image to display depending on the 
 * number of wrong guesses
 * @author Spencer Wei, Denes Marton, Calvin Chan
 *
 */
public class Hangman {
	
	private final static String directoryToImage = System.getProperty("user.dir") + "\\images";
	private final static String imageFormat = ".png";
	private String filename;
	
	/**
	 * This method takes in the number of wrong guesses and selects the correct image file.
	 * The number of wrong guesses corresponds to the file name that is shown 
	 * (1 wrong guess displays image 1, 2 wrong guesses displays image 2, etc)
	 * 
	 * @param numWrongGuesses Number of
	 * @param wonGame
	 * @return
	 */
	public JPanel readImage(int numWrongGuesses, boolean wonGame){
		
		filename = null;
		
		if (numWrongGuesses != UI.NUMBER_OF_WRONG_GUESSES_ALLOWED && !wonGame) {
			filename =  "\\" + numWrongGuesses + imageFormat;
			
		}
		else if (wonGame) {
			filename = "\\Winner.png";
		} else {
			filename = "\\Lose.png";
		}
		
		
		File f = Paths.get(directoryToImage + filename).toFile();
		JPanel imagePanel = new JPanel();
		BufferedImage hangmanImages;
		try {			
			hangmanImages = ImageIO.read(f);
			JLabel currentImage = new JLabel(new ImageIcon(hangmanImages));
			imagePanel.add(currentImage);
		} catch (IOException e) {
			System.out.println(f.getAbsolutePath());
		}
		return imagePanel;
	}
	

	public String getFileName() {
		return filename;
	}
	
}

