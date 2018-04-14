package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import data.Entity;
import logic.Game;
import logic.IGame;
import logic.PacMan;


public class Board extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	Timer t = new Timer(5,this);
	public static final int CELLSIZE = 12;
	JLabel label;
	logic.IGame game = Game.getInstance();
    int []position = game.getPlayer().getPosition();
   
	public Board() {
		
		this.setBackground(Color.BLACK);
		
		this.setLayout(new FlowLayout()); // A MODIFIER
		this.setPreferredSize(new Dimension(CELLSIZE*28, CELLSIZE*31));
		label = new JLabel(new ImageIcon("res/pacmanmin.gif"));
        
		
        label.addKeyListener(new KeyAdapter()
        {
        		public void keyPressed(KeyEvent ke)
            {
        			if(ke.getKeyCode() == KeyEvent.VK_DOWN)
                {                		
                		label.setBounds(label.getX(), label.getY()+CELLSIZE, CELLSIZE, CELLSIZE);               		    
                }
                if(ke.getKeyCode() == KeyEvent.VK_UP)
                {                			
        				label.setBounds(label.getX(), label.getY()-CELLSIZE, CELLSIZE, CELLSIZE);                			              	                   
                }
                if(ke.getKeyCode() == KeyEvent.VK_LEFT)
                {
                		label.setLocation(label.getX()-CELLSIZE +(CELLSIZE/2),label.getY());      
                }
                if(ke.getKeyCode() == KeyEvent.VK_RIGHT)
                {
                    label.setLocation(label.getX()+CELLSIZE,label.getY());
                }
            }
        });
       
       
        label.setPreferredSize(new Dimension(CELLSIZE, CELLSIZE));
		
		
		int x2 = position[0]*CELLSIZE;
        int y2 = position[1]*CELLSIZE;
        label.setLocation(x2,y2);
		this.add(label).setBounds(x2, y2, CELLSIZE, CELLSIZE);;
		label.setFocusable(true );
		
        
       
        System.out.println();
		for(int i=0; i<game.getBoard().length; i++) {
			for(int j=0; j<game.getBoard()[0].length; j++) {
				Entity e = game.getBoard()[i][j];
				int k = i;
				int m = j;
				switch(e) {
					case MUR : 
						JLabel wall = new JLabel(new ImageIcon("res/wall.png"));
						wall.setPreferredSize(new Dimension(CELLSIZE, CELLSIZE));
						this.add(wall).setBounds(k*CELLSIZE, m*CELLSIZE, CELLSIZE, CELLSIZE);;
						break;
					case GOMME :
						JLabel gum = new JLabel(new ImageIcon("res/gum.png"));
						gum.setPreferredSize(new Dimension(CELLSIZE, CELLSIZE));
						this.add(gum).setBounds(k*CELLSIZE, m*CELLSIZE, CELLSIZE, CELLSIZE);;
						break;
					case SUPERGOMME :
						JLabel supergum = new JLabel(new ImageIcon("res/fruit.png"));
						supergum.setPreferredSize(new Dimension(CELLSIZE, CELLSIZE));
						this.add(supergum).setBounds(k*CELLSIZE, m*CELLSIZE, CELLSIZE, CELLSIZE);
						break;
					case CHEMIN :
						JLabel chemin = new JLabel();
						chemin.setPreferredSize(new Dimension(CELLSIZE, CELLSIZE));
						this.add(chemin).setBounds(k*CELLSIZE, m*CELLSIZE, CELLSIZE, CELLSIZE);
						break;
					case TUNNEL:
						JLabel tunnel = new JLabel("res/wall2.png");
						tunnel.setPreferredSize(new Dimension(CELLSIZE, CELLSIZE));
						this.add(tunnel).setBounds(k*CELLSIZE, m*CELLSIZE, CELLSIZE, CELLSIZE);
						break;
					case FRUIT :
						JLabel fruit = new JLabel("res/fruit_cherry.png");
						fruit.setPreferredSize(new Dimension(CELLSIZE, CELLSIZE));
						this.add(fruit).setBounds(k*CELLSIZE, m*CELLSIZE, CELLSIZE, CELLSIZE);
						break;
					default :
							
				}
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
	}
	
	
}