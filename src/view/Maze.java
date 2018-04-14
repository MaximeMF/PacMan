package view;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class Maze {
	
	
	private Figure figure;
	int rows = 45;
    int columns = 45;
    
    // Short delay between steps in making and solving maze
   

    // Graphics context for canvas, created in putSquare()
    Graphics me = null;
    
    // The following fields are set by checkSize()
    int width = -1;   // width of canvas
    int height = -1;  // height of canvas
    int cellWidth;    // width of cell
    int cellHeight;   // height of cell
    int left;         // left edge of maze, allowing for border
    int top;          // top edge of maze, allowing for border	

	public Maze() throws FileNotFoundException, IOException, ParseException {
		/*Figure[] figures = new Figure[]{
	            new Square(40, 230, 80,Color.BLUE), // house
	            new Square(40, 130, 180,Color.BLUE),
	            new Square(40, 330, 50,Color.BLUE),
	        };*/
		//Figure [][] figures = this.drawMaze();
	        //this.figure = new CompoundFigure(figures[1]);
	       // this.canvas = Canvas.getCanvas();
	       
		
	}


	public int[][] getBoard() throws FileNotFoundException, IOException, ParseException
	{
		JSONParser parser = new JSONParser();
		JSONObject a = (JSONObject) parser.parse(new FileReader("config/main.json"));
		int [][] board = new int[31][28];
		
		    JSONArray numbers = (JSONArray) a.get("board");
		    int i = 0;
		    for (Object c : numbers)
		    {
		    		int j=0;
		    		JSONArray h = (JSONArray) c;
		    		for (Object k : h)
		    		{
		    			board[i][j] =Integer.parseInt(k.toString());
		    			j++;
		    		}
		    		i++;
		    		
		    }
		    
		    return board;
		    
		 
	}
	
	
	
	public WallPane[][] drawMaze() throws FileNotFoundException, IOException, ParseException
	{
		WallPane [][] murs = new WallPane[31][28];
		int [][] board = this.getBoard();
		for( int i=0; i<board.length;i++)
		{
			for( int j=0; j<board[i].length;j++)
			{
				if(board[i][j] == 0)
				{
						int k = i ;
						int m = j ;
						murs[i][j] = new WallPane(k*20,m*20);
						
				}
				
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
		return murs;
   	}
	
	
	
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		Maze m = new Maze();
		//m.draw();
		WallPane pp [][] = m.drawMaze( );
		JFrame jp1 = new JFrame();
		jp1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//JPanel pan = new JPanel();
        for(int i=0;i<pp.length;i++)
        {
        		for (int j=0;j<pp[i].length;j++)
        		{
        			if(pp[i][j] != null)
        			{
        				
        				jp1.getContentPane().add(pp[i][j]).setBounds(pp[i][j].x,pp[i][j].y,40,40);
        				//System.out.println(pp[i][j].x+ " , "+pp[i][j].y);
        			}
        			
        		}
        }
		//
        //PacmanPicture mainPanel = new PacmanPicture();
        //jp1.getContentPane().add(mainPanel, BorderLayout.CENTER);
        jp1.setSize(new Dimension(500,500));
        jp1.setVisible(true);
		
		
		
		
		
		

		
			
	}
	
	
	private  class WallPane extends JPanel
	{
		private   BufferedImage wall ;
		public int x , y;

		public WallPane(int x,int y) throws IOException{
			this.x =x;
			this.y =y;
			this.wall = ImageIO.read(new File("res/wall.png"));
			this.wall = Maze.resize(wall,20,20);
		
		}
		
		
		public void paintComponent(Graphics g)
		{
			
			super.paintComponent(g);
			g.drawImage(this.wall, 20,20, null);
			
		}
		public int getHeightImage()
		{
			return wall.getHeight();
		}
		public int getWidthImage()
		{
			return wall.getWidth();
		}
		
	}
	
	  public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
	        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	        Graphics2D g2d = dimg.createGraphics();
	        g2d.drawImage(tmp, 0, 0, null);
	        g2d.dispose();

	        return dimg;
	    }
	
}


