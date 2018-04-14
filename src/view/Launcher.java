package view;

import java.io.IOException;

import org.json.simple.parser.ParseException;

public class Launcher {
	
	public static void main(String[] args) { 

		javax.swing.SwingUtilities.invokeLater (new Runnable() { 
    		public void run() { 
    			try {
					new Window();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
    		}
		});
	} 
}