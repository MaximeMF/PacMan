package view;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Class displaying score, level and lives information.
 * @author Robin Algier - Maxime Mathis--Fumel - Yassin Ourkia
 */
public class BottomBar extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JLabel jScore = new JLabel();
	private JLabel jLevel = new JLabel();
	private JLabel jLives = new JLabel();
	
	/** Constructs an instance of BottomBar.
	 * @param score the score to display
	 * @param lives the number of lives to display
	 * @param level the level to display
	 */
	public BottomBar(int score, int lives, int level) {
		
		this.setLayout(new BorderLayout());
		
		this.jScore.setText("Score : "+score);
		this.jScore.setFont(new Font("Helvetica", Font.BOLD, 20));
		this.add(jScore, BorderLayout.WEST);
		
		this.jLevel.setText("Level : "+level);
		this.jLevel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		this.jLevel.setFont(new Font("Helvetica", Font.BOLD, 20));
		this.add(jLevel, BorderLayout.CENTER);
		
		this.jLives.setText("Lives : "+lives);
		this.jLives.setFont(new Font("Helvetica", Font.BOLD, 20));
		this.add(jLives, BorderLayout.EAST);
	}
	
	/**
	 * Updates the values of score, level and lives.
	 * @param score the new score to display
	 * @param lives the new number of lives to display
	 * @param level the level to display
	 */
	public void update(int score, int lives, int level) {
		this.jScore.setText("Score : "+score);
		this.jLevel.setText("Level : "+level);
		this.jLives.setText("Lives : "+lives);
	}

}