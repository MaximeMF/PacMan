package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class Maze {
	
	private Canvas canvas;
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
	        this.canvas = Canvas.getCanvas();
	       
		
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
	
	
	
	public void drawMaze() throws FileNotFoundException, IOException, ParseException
	{
		
		int [][] board = this.getBoard();
		for( int i=0; i<board.length;i++)
		{
			for( int j=0; j<board[i].length;j++)
			{
				if(board[i][j] == 0)
				{
						int k = i ;
						int m = j ;
						new WallPane(k,m);		
				}
			}
		}
   	}
	
	
	
	public void draw()
    {
        this.figure.draw();
        this.canvas.redraw();
    }
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		Maze m = new Maze();
		//m.draw();
		Graphics g = null;
		m.drawMaze( );
		
		
		Canvas c = Canvas.getCanvas();
		
		/*for( int i=0; i<f.length;i++)
		{
			for( int j=0; j<f[i].length;j++)
			{
				f[i][j].draw();;
			}
			System.out.println();
		}
		*/
		
		
		
		

		
			
	}
	
	@SuppressWarnings("serial")
	class WallPane extends JPanel
	{
		private   BufferedImage wall ;
		private int x , y;

		public WallPane(int x,int y) throws IOException{
			this.x =x;
			this.y =y;
			this.wall = ImageIO.read(new File("res/wall.png"));
		
		}
		
		public void paintCompoenent(Graphics g)
		{
			super.paintComponent(g);
			g.drawImage(this.wall, this.x, this.y, null);
		}
		
	}
	
}
