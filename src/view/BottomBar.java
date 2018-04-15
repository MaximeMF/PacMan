package view;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Class Displaying Score and Level and lives informations 
 * @author Robin Algier - Maxime Mathis--Fumel - Yassin Ourkia
 *
 */

public class BottomBar extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JLabel jScore = new JLabel();
	private JLabel jLevel = new JLabel();
	private JLabel jLives = new JLabel();
	
	/** Construct an instance of BottomBar 
	 * @param score
	 * @param lives
	 * @param level
	 */
	public BottomBar(int score, int lives, int level) {
		
		this.setLayout(new BorderLayout());
		
		this.jScore.setText("Score : "+score);
		this.jScore.setFont(new Font("Helvetica", Font.BOLD, 20));
		this.add(jScore, BorderLayout.WEST);
		
		this.jLevel.setText("Level : "+level);
		this.jLevel.setHorizontalAlignment(SwingConstants.CENTER);
		this.jLevel.setFont(new Font("Helvetica", Font.BOLD, 20));
		this.add(jLevel, BorderLayout.CENTER);
		
		this.jLives.setText("Lives : "+lives);
		this.jLives.setFont(new Font("Helvetica", Font.BOLD, 20));
		this.add(jLives, BorderLayout.EAST);
	}
	
	/**
	 * Update the values of score,level and lives according to the game
	 * @param score
	 * @param lives
	 * @param level
	 */
	public void update(int score, int lives, int level) {
		this.jScore.setText("Score : "+score);
		this.jLevel.setText("Level : "+level);
		this.jLives.setText("Lives : "+lives);
	}

}