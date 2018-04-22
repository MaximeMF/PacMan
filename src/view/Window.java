package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import logic.Game;
import logic.IGame;

/**
 * Class displaying the frame.
 * @author Robin Algier - Maxime Mathis--Fumel - Yassin Ourkia
 */
public class Window extends JFrame {
	
	private static final long serialVersionUID = 1L;
	public static final int WINDOW_HEIGHT = 800;
	public static final int WINDOW_WIDTH = 600;
	/**
	 * Constructs an instance of Window.
	 */
	public Window() {

		super("Pac-Man");
	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	    this.setBounds(20, 20, WINDOW_WIDTH  ,WINDOW_HEIGHT);
	    
	    JLabel logo = new JLabel(new ImageIcon("res/logo.png"));
	    
	    this.add(logo, BorderLayout.NORTH);
	    
	    IGame game = Game.getInstance();
	    BottomBar scoreBar = new BottomBar(0, game.getBestScore(), game.getPlayer().getLives(), game.getPlayer().getLevel());
	    Board board = new Board(scoreBar);
	    this.add(board, BorderLayout.CENTER);
	    
	    this.add(scoreBar, BorderLayout.SOUTH);

	    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	    this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	    this.setResizable(false);
	
		this.setVisible(true);

	}
	
}