package view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.json.simple.parser.ParseException;

import data.DataLoader;
import data.IDataLoader;




public class Window extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	
	public Window() throws IOException, ParseException {

		super("Pac-Man");
	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setSize(new Dimension(600,600));
	    this.setBounds(20, 20, 490,700 );
	    
	    JLabel logo = new JLabel(new ImageIcon("res/logo.png"));
	    
	    this.add(logo, BorderLayout.NORTH);
	    
	    IDataLoader data = new DataLoader(); // A SUPPRIMER
	    Board board = new Board(data.getBoard());
	    this.add(board, BorderLayout.CENTER);
	   
	   
	    ScoreBar scoreBar = new ScoreBar(10, 20); // A MODIFIER
	    this.add(scoreBar, BorderLayout.SOUTH);
		
		//this.pack();
	    
	    //this.setResizable(false);
	
		this.setVisible(true);

	}
	
}