import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Hangman {
	
	final static String directoryToImage = System.getProperty("user.dir") + "\\images";
	final static String imageFormat = ".png";
	
	public JPanel readImage(int numWrongGuesses, boolean wonGame){
		
		String filename = null;
		
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
	

	
}

