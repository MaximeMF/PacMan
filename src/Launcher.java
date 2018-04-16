

import java.io.IOException;

import org.json.simple.parser.ParseException;

/**
 * Class Launching the Game
 * @author Robin Algier - Maxime Mathis--Fumel - Yassin Ourkia
 *
 */
public class Launcher {
	
	public static void main(String[] args) { 

		javax.swing.SwingUtilities.invokeLater (new Runnable() { 
    		public void run() { 
    			try {
					new view.Window();
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