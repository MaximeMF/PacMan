/**
 * Class Launching the Game
 * @author Robin Algier - Maxime Mathis--Fumel - Yassin Ourkia
 *
 */
public class Launcher {
	
	public static void main(String[] args) { 

		javax.swing.SwingUtilities.invokeLater (new Runnable() { 
    		public void run() { 
					new view.Window();
    		}
		});
	} 
}