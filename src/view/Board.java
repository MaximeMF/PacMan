package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import enums.*;
import logic.Game;

/**
 * Class displaying the maze and the game elements.
 * @author Robin Algier - Maxime Mathis--Fumel - Yassin Ourkia
 */
public class Board extends JPanel implements KeyListener {
	
	private static final long serialVersionUID = 1L;
	
	Timer t = new Timer();
	
	public static final int CELLSIZE = 12;
	
	JLabel jPacman;
	
	logic.IGame game = Game.getInstance();
	
	int[] position;
	
	// RESIZE A REVOIR
	ImageIcon wallIcon = new ImageIcon(new ImageIcon("res/wall.png").getImage().getScaledInstance(CELLSIZE, CELLSIZE, Image.SCALE_DEFAULT));
	//ImageIcon wallIcon = new ImageIcon("res/wall.png");
	ImageIcon gumIcon = new ImageIcon(new ImageIcon("res/gum.png").getImage().getScaledInstance(CELLSIZE, CELLSIZE, Image.SCALE_DEFAULT));
	ImageIcon supergumIcon = new ImageIcon(new ImageIcon("res/fruit.png").getImage().getScaledInstance(CELLSIZE, CELLSIZE, Image.SCALE_DEFAULT));
	ImageIcon fruitIcon = new ImageIcon(new ImageIcon("res/fruit_cherry.png").getImage().getScaledInstance(CELLSIZE, CELLSIZE, Image.SCALE_DEFAULT));
	ImageIcon tunnelIcon = new ImageIcon(new ImageIcon("res/wall2.png").getImage().getScaledInstance(CELLSIZE, CELLSIZE, Image.SCALE_DEFAULT));
	ImageIcon ghostIcon = new ImageIcon(new ImageIcon("res/ghost.png").getImage().getScaledInstance(CELLSIZE, CELLSIZE, Image.SCALE_DEFAULT));
	ImageIcon ghost2Icon = new ImageIcon(new ImageIcon("res/ghost2.png").getImage().getScaledInstance(CELLSIZE, CELLSIZE, Image.SCALE_DEFAULT));
	ImageIcon ghost3Icon = new ImageIcon(new ImageIcon("res/ghost3.gif").getImage().getScaledInstance(CELLSIZE, CELLSIZE, Image.SCALE_DEFAULT));
	
	BottomBar scoreBar;
	
	Direction dir;
	
	Direction previousDir;

	/**
	 * Constructs an instance of Board.
	 * @param bottomBar the BottomBar to refresh
	 */
	public Board(BottomBar bottomBar) {
		this.scoreBar = bottomBar;
		this.setBackground(Color.BLACK);
		position = game.getPlayer().getPosition();
		this.setLayout(new GridLayout(game.getBoardHeight(),game.getBoardWidth())); 
		this.setPreferredSize(new Dimension(CELLSIZE*game.getBoardWidth(), CELLSIZE*game.getBoardHeight()));
		jPacman = new JLabel();
		jPacman.setIcon(new ImageIcon(new ImageIcon("res/imageleft.gif").getImage().getScaledInstance(CELLSIZE, CELLSIZE, Image.SCALE_DEFAULT)));


		this.addKeyListener(this);
		this.setFocusable(true);

		//jPacman.setPreferredSize(new Dimension(CELLSIZE, CELLSIZE));


		//int x2 = position[0]*CELLSIZE;
		//int y2 = position[1]*CELLSIZE;
		//jPacman.setLocation(x2,y2);
		//this.add(jPacman).setBounds(x2, y2, CELLSIZE, CELLSIZE);;
		//this.add(jPacman);
		//jPacman.setFocusable(true);
		this.dir = Direction.LEFT;
		this.previousDir = Direction.LEFT;
		toRepeat();
		
		for(GhostType gt : GhostType.values())
			if(gt != GhostType.RED)
				game.getGhost(gt).init();
		
		t.scheduleAtFixedRate(new TimerTask() {	
			@Override
			public void run() {
				move();
			}
		}, 0, 300);
	}
	
	
	// PLACER TOUT CE QUI SERT A INITIALISER L'AFFICHAGE DU BOARD AU DEBUT ICI
	public void initialise() {
		
	}
	
	

	/**
	 * Display the elements of the game.
	 */
	private void toRepeat() {
		this.removeAll();
		for(int i=0; i<game.getBoard().length; i++) {
			for(int j=0; j<game.getBoard()[0].length; j++) {
				Entity e = game.getBoard()[i][j];
				boolean g = false;
				int[][] pos = {{-1,-1},{-1,-1},{-1,-1},{-1,-1}};
				int k = 0;
				for(GhostType gt : GhostType.values()) {
					int[] gtPos = game.getGhost(gt).getPosition();
					if(j == gtPos[0] && i == gtPos[1]) {
						boolean ga = false;
						for(int kk = 0 ; kk < k ; kk++)
							if(Arrays.equals(pos[kk], game.getGhost(gt).getPosition())) {
								ga = true;
							}
						if(!ga) {
							JLabel ghost = new JLabel();
							if(game.getGhost(gt).getState() == 1)
								ghost.setIcon(ghostIcon);
							else if(game.getGhost(gt).getState() == 2)
								ghost.setIcon(ghost2Icon);
							else
								ghost.setIcon(ghost3Icon);
							this.add(ghost);
							pos[k] = gtPos;
							k++;
						}
						g = true;
					}
				}
				if(g)
					continue;
				if(j == game.getPlayer().getPosition()[0] && i == game.getPlayer().getPosition()[1]) {
					this.add(jPacman);
				}
				else
					switch(e) {
					case MUR : 
						JLabel wall = new JLabel();
						wall.setIcon(wallIcon);
						//wall.setPreferredSize(new Dimension(CELLSIZE, CELLSIZE));
						this.add(wall);//.setBounds(k*CELLSIZE, m*CELLSIZE, CELLSIZE, CELLSIZE);;
						break;
					case GOMME :
						JLabel gum = new JLabel();
						gum.setIcon(gumIcon);
						//gum.setPreferredSize(new Dimension(CELLSIZE, CELLSIZE));
						this.add(gum);//.setBounds(k*CELLSIZE, m*CELLSIZE, CELLSIZE, CELLSIZE);;
						break;
					case SUPERGOMME :
						JLabel supergum = new JLabel();
						supergum.setIcon(supergumIcon);
						//supergum.setPreferredSize(new Dimension(CELLSIZE, CELLSIZE));
						this.add(supergum);//.setBounds(k*CELLSIZE, m*CELLSIZE, CELLSIZE, CELLSIZE);
						break;
					case CHEMIN :
						JLabel chemin = new JLabel();
						chemin.setPreferredSize(new Dimension(CELLSIZE, CELLSIZE));
						this.add(chemin);//.setBounds(k*CELLSIZE, m*CELLSIZE, CELLSIZE, CELLSIZE);
						break;
					case TUNNEL:
						JLabel tunnel = new JLabel();
						tunnel.setIcon(tunnelIcon);
						//tunnel.setPreferredSize(new Dimension(CELLSIZE, CELLSIZE));
						this.add(tunnel);//.setBounds(k*CELLSIZE, m*CELLSIZE, CELLSIZE, CELLSIZE);
						break;
					case FRUIT :
						JLabel fruit = new JLabel("res/fruit_cherry.png");
						fruit.setIcon(fruitIcon);
						//fruit.setPreferredSize(new Dimension(CELLSIZE, CELLSIZE));
						this.add(fruit);//.setBounds(k*CELLSIZE, m*CELLSIZE, CELLSIZE, CELLSIZE);
						break;
					default :

					}
			}
		}
		revalidate();
		repaint();
	}

    /**
     * {@inheritDoc }
     */
	@Override
	public void keyPressed(KeyEvent ke) {
		if(ke.getKeyCode() == KeyEvent.VK_DOWN)
		{
			this.dir = Direction.DOWN;
			jPacman.setIcon(new ImageIcon(new ImageIcon("res/imagedown.gif").getImage().getScaledInstance(CELLSIZE, CELLSIZE, Image.SCALE_DEFAULT)));
		}
		else if(ke.getKeyCode() == KeyEvent.VK_UP)
		{
			this.dir = Direction.UP;
			jPacman.setIcon(new ImageIcon(new ImageIcon("res/imageup.gif").getImage().getScaledInstance(CELLSIZE, CELLSIZE, Image.SCALE_DEFAULT)));
		}
		else if(ke.getKeyCode() == KeyEvent.VK_LEFT)
		{
			this.dir = Direction.LEFT;
			jPacman.setIcon(new ImageIcon(new ImageIcon("res/imageleft.gif").getImage().getScaledInstance(CELLSIZE, CELLSIZE, Image.SCALE_DEFAULT)));
		}
		else if(ke.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			this.dir = Direction.RIGHT;
			jPacman.setIcon(new ImageIcon(new ImageIcon("res/imageright.gif").getImage().getScaledInstance(CELLSIZE, CELLSIZE, Image.SCALE_DEFAULT)));
		}
	}
	
	

	/**
	 * Moves the pacman and the ghosts.
	 */
	private void move() {
		if (game.getPlayer().canMove(this.dir) && (this.dir != Direction.opposite(this.previousDir) || !game.getPlayer().canMove(this.previousDir))) {
			game.getPlayer().move(this.dir);
			this.previousDir = this.dir;
		}
		else if(game.getPlayer().canMove(this.previousDir))
			game.getPlayer().move(this.previousDir);
		for(GhostType gt : GhostType.values())
			game.getGhost(gt).move();
		toRepeat();
		scoreBar.update(game.getPlayer().getScore(), game.getPlayer().getLives(), game.getPlayer().getLevel());
		if(game.hasLost())
		{
			
			int exit = JOptionPane.showConfirmDialog(null, " Vous avez perdu", "Information", JOptionPane.OK_CANCEL_OPTION); 
		    if(exit == JOptionPane.OK_OPTION || exit == JOptionPane.CANCEL_OPTION){
		    	   System.exit(0);
		    }
			System.out.println("GAME OVER");
		
		}else if(game.hasWon())
		{
			ImageIcon img = new ImageIcon(new ImageIcon("res/youwin.png").getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
		    JOptionPane.showMessageDialog(null, "Bravo !! Vous avez gagné", "Information", JOptionPane.OK_CANCEL_OPTION, img); 
		}
	}
	
	

	public void keyReleased(KeyEvent arg0) {}
	
	public void keyTyped(KeyEvent arg0) {}

}