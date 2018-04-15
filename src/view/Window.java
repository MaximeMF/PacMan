package view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.json.simple.parser.ParseException;

import logic.Game;




public class Window extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	
	public Window() throws IOException, ParseException {

		super("Pac-Man");
	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setSize(new Dimension(600,600));
	    this.setBounds(20, 20, 490,700);
	    
	    JLabel logo = new JLabel(new ImageIcon("res/logo.png"));
	    
	    this.add(logo, BorderLayout.NORTH);
	    
	    BottomBar scoreBar = new BottomBar(0, Game.getInstance().getPlayer().getLives(), Game.getInstance().getPlayer().getLevel()); // A MODIFIER
	    Board board = new Board(scoreBar);
	    this.add(board, BorderLayout.CENTER);
	    
	    this.add(scoreBar, BorderLayout.SOUTH);
		
		//this.pack();
	    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	    this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	    this.setResizable(false);
	
		this.setVisible(true);

	}
	
}