package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import data.Entity;


public class Board extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public static final int CELLSIZE = 12;
	JLabel label;
	public Board(Entity[][] board) {
		this.setBackground(Color.BLACK);
		this.setLayout(new FlowLayout()); // A MODIFIER
		this.setPreferredSize(new Dimension(CELLSIZE*31, CELLSIZE*28));
		label = new JLabel(new ImageIcon("res/pacmanmin.gif"));
        //label.setBounds(120,200,16,19);
		Point p1 = this.getLocation();
        label.addKeyListener(new KeyAdapter()
            {
        	 
            public void keyPressed(KeyEvent ke)
                {
            	
                while(ke.getKeyCode() == KeyEvent.VK_DOWN)
                    {
                		
                		label.setBounds(label.getX(), label.getY()+CELLSIZE, CELLSIZE, CELLSIZE);
                		
                    
                }
                while(ke.getKeyCode() == KeyEvent.VK_UP)
                    {
                			
                			label.setBounds(label.getX(), label.getY()-CELLSIZE, CELLSIZE, CELLSIZE);
                			
                  
                    
                }
                while(ke.getKeyCode() == KeyEvent.VK_LEFT)
                    {
                    		label.setLocation(label.getX()-CELLSIZE,label.getY());
               
                   
                }
                while(ke.getKeyCode() == KeyEvent.VK_RIGHT)
                    {
                		
                    label.setLocation(label.getX()+CELLSIZE,label.getY());
                 
                }
            }
        });
		
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board[0].length; j++) {
				Entity e = board[i][j];
				int k = i;
				int m = j;
				switch(e) {
					case MUR:
						
						JLabel cell = new JLabel(new ImageIcon("res/wall2.png"));
						Point p11 = cell.getLocation();
						
						int x = p11.x;
		                int y = p11.y;
		                cell.setLocation(x+5,y);
						cell.setPreferredSize(new Dimension(CELLSIZE, CELLSIZE));
						this.add(cell);
						break;
					case PACMAN:
						label.setPreferredSize(new Dimension(CELLSIZE, CELLSIZE));
						Point p2 = label.getLocation();
						
						int x2 = p2.x;
		                int y2 = p2.y;
		                label.setLocation(x2+5,y2);
						this.add(label);
						label.setFocusable(true );
						break;
					case SUPERGOMME :
						JLabel supergum = new JLabel(new ImageIcon("res/fruit.png"));
						supergum.setPreferredSize(new Dimension(CELLSIZE, CELLSIZE));
						this.add(supergum).setBounds(k*CELLSIZE, m*CELLSIZE, (CELLSIZE+5)/2, (CELLSIZE+5)/2);
						break;
					
					default:
						
						JLabel gum = new JLabel(new ImageIcon("res/gum.png"));
						Point p3 = gum.getLocation();
						int x3 = p3.x;
		                int y3 = p3.y;
		                gum.setLocation(x3+5,y3);
						
						gum.setPreferredSize(new Dimension(CELLSIZE, CELLSIZE));
						this.add(gum);
						break;
					
				}
			}
		}
		
		
		
		
	}
	
	
}