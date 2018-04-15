package view;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScoreBar extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public ScoreBar(int score, int lives, int level) {
		
		this.setLayout(new BorderLayout());
		
		JLabel jScore = new JLabel("Score : "+score);
		this.setLayout(new GridLayout(1,3));
		this.add(jScore);
		this.add(new JLabel("Level : "+level));
		JLabel jLives = new JLabel("Lives : "+lives);
		this.add(jLives);

		
	}
	
	public void update(int score, int lives, int level) {
		this.removeAll();
		this.add(new JLabel("Score : "+score));
		this.add(new JLabel("Level : "+level));
		this.add(new JLabel("Lives : "+lives));
	}

}