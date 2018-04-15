package view;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class BottomBar extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JLabel jScore = new JLabel();
	private JLabel jLevel = new JLabel();
	private JLabel jLives = new JLabel();
	
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
	
	public void update(int score, int lives, int level) {
		this.jScore.setText("Score : "+score);
		this.jLevel.setText("Level : "+level);
		this.jLives.setText("Lives : "+lives);
	}

}