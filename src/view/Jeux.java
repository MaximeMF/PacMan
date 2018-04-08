package view;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Jeux {
	 private static void createAndShowGui() {
 		PacmanPicture mainPanel = new PacmanPicture();

     JFrame frame = new JFrame("Pac Man");
     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     frame.getContentPane().add(mainPanel);
     frame.pack();
     frame.setLocationRelativeTo(null);
     frame.setVisible(true);
 }

	 public static void main(String[] args) {
		 SwingUtilities.invokeLater(() -> createAndShowGui());
	 }
}
