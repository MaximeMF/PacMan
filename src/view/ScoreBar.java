package view;
import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScoreBar extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public ScoreBar(int score, int lives) {
		
		this.setLayout(new BorderLayout());
		
		JLabel jScore = new JLabel("Score : "+score);
		this.add(jScore, BorderLayout.WEST);
		
		JLabel jLives = new JLabel("Lives : "+lives);
		this.add(jLives, BorderLayout.EAST);
		
	}

}