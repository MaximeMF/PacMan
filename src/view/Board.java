package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import enums.*;
import logic.Game;
import logic.IGhost;

/**
 * Class displaying the maze and the game elements.
 * @author Robin Algier - Maxime Mathis--Fumel - Yassin Ourkia
 */
public class Board extends JPanel implements KeyListener {

	private static final long serialVersionUID = 1L;

	Timer t = new Timer();

	

	JLabel jPacman;

	logic.IGame game = Game.getInstance();
	public static final int CELLSIZE = Window.WINDOW_WIDTH / Game.getInstance().getBoardWidth();

	int[] position;

	// RESIZE A REVOIR
	ImageIcon wallIcon = new ImageIcon(new ImageIcon("res/wall.png").getImage().getScaledInstance(CELLSIZE, CELLSIZE, Image.SCALE_DEFAULT));
	//ImageIcon wallIcon = new ImageIcon("res/wall.png");
	ImageIcon gumIcon = new ImageIcon(new ImageIcon("res/gum.png").getImage().getScaledInstance(CELLSIZE-(CELLSIZE/2), CELLSIZE- (CELLSIZE/2), Image.SCALE_DEFAULT));
	ImageIcon supergumIcon = new ImageIcon(new ImageIcon("res/fruit.png").getImage().getScaledInstance(CELLSIZE, CELLSIZE, Image.SCALE_DEFAULT));
	ImageIcon fruitIcon = new ImageIcon(new ImageIcon("res/fruit_cherry.png").getImage().getScaledInstance(CELLSIZE, CELLSIZE, Image.SCALE_DEFAULT));
	ImageIcon tunnelIcon = new ImageIcon(new ImageIcon("res/wall2.png").getImage().getScaledInstance(CELLSIZE, CELLSIZE, Image.SCALE_DEFAULT));
	ImageIcon ghostIcon = new ImageIcon(new ImageIcon("res/ghost.png").getImage().getScaledInstance(CELLSIZE, CELLSIZE, Image.SCALE_DEFAULT));
	ImageIcon ghost2Icon = new ImageIcon(new ImageIcon("res/ghost2.png").getImage().getScaledInstance(CELLSIZE, CELLSIZE, Image.SCALE_DEFAULT));
	ImageIcon ghost3Icon = new ImageIcon(new ImageIcon("res/ghost3.gif").getImage().getScaledInstance(CELLSIZE, CELLSIZE, Image.SCALE_DEFAULT));
	ImageIcon pacLeft = new ImageIcon(new ImageIcon("res/imageleft.gif").getImage().getScaledInstance(CELLSIZE, CELLSIZE, Image.SCALE_DEFAULT));
	ImageIcon pacRight = new ImageIcon(new ImageIcon("res/imageright.gif").getImage().getScaledInstance(CELLSIZE, CELLSIZE, Image.SCALE_DEFAULT));
	ImageIcon pacUp = new ImageIcon(new ImageIcon("res/imageup.gif").getImage().getScaledInstance(CELLSIZE, CELLSIZE, Image.SCALE_DEFAULT));
	ImageIcon pacDown = new ImageIcon(new ImageIcon("res/imageDown.gif").getImage().getScaledInstance(CELLSIZE, CELLSIZE, Image.SCALE_DEFAULT));

	BottomBar scoreBar;

	Direction dir;

	Direction previousDir;

	private JLabel[][] jCells;


	/**
	 * Constructs an instance of Board.
	 * @param bottomBar the BottomBar to refresh
	 */
	public Board(BottomBar bottomBar) {
		this.scoreBar = bottomBar;
		this.setBackground(Color.BLACK);
		position = game.getPlayer().getPosition();
		GridLayout layout = new GridLayout(game.getBoardHeight(),game.getBoardWidth());
		layout.setHgap(0);
		layout.setVgap(0);
		this.setLayout(layout); 
		//this.setPreferredSize(new Dimension(CELLSIZE*game.getBoardWidth(), CELLSIZE*game.getBoardHeight()));
		jPacman = new JLabel();
		jPacman.setIcon(new ImageIcon(new ImageIcon("res/imageleft.gif").getImage().getScaledInstance(CELLSIZE, CELLSIZE, Image.SCALE_DEFAULT)));
		jCells = new JLabel[game.getBoardHeight()][game.getBoardWidth()];
		for(int i = 0; i < game.getBoardHeight() ; i++) {
			for(int j = 0; j < game.getBoardWidth() ; j++) {
				jCells[i][j] = new JLabel();
				jCells[i][j].setHorizontalAlignment(JLabel.CENTER);
				jCells[i][j].setVerticalAlignment(JLabel.CENTER);
				this.add(jCells[i][j]);
			}
		}


		this.addKeyListener(this);
		this.setFocusable(true);
		this.dir = Direction.RIGHT;
		this.previousDir = Direction.DOWN;
		
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
		game.getPlayer().move(Direction.RIGHT);
	}



	/**
	 * Display the elements of the game.
	 */
	private void toRepeat() {
		Entity[][] board = this.game.getBoard();
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board[0].length; j++) {
				switch(board[i][j]) {
				case MUR : 
					this.jCells[i][j].setIcon(wallIcon);
					break;
				case GOMME :
					this.jCells[i][j].setIcon(gumIcon);
					break;
				case SUPERGOMME :
					this.jCells[i][j].setIcon(supergumIcon);
					break;
				case VOID:
				case CHEMIN :
					this.jCells[i][j].setIcon(null);
					break;
				case TUNNEL:
					this.jCells[i][j].setIcon(tunnelIcon);
					break;
				case FRUIT :
					this.jCells[i][j].setIcon(fruitIcon);
					break;
				default :

				}
			}
		}
		for(GhostType gt : GhostType.values()) {
			IGhost ghost = game.getGhost(gt);
			int[] gtPos = ghost.getPosition();
			if(ghost.getState() == 1)
				jCells[gtPos[1]][gtPos[0]].setIcon(ghostIcon);
			else if(ghost.getState() == 2)
				jCells[gtPos[1]][gtPos[0]].setIcon(ghost2Icon);
			else
				jCells[gtPos[1]][gtPos[0]].setIcon(ghost3Icon);
		}
		int[] pacPos = game.getPlayer().getPosition();
		switch(this.dir) {
		case UP:
			jCells[pacPos[1]][pacPos[0]].setIcon(pacUp);
			break;
		case DOWN:
			jCells[pacPos[1]][pacPos[0]].setIcon(pacDown);
			break;
		case LEFT:
			jCells[pacPos[1]][pacPos[0]].setIcon(pacLeft);
			break;
		case RIGHT:
			jCells[pacPos[1]][pacPos[0]].setIcon(pacRight);
			break;
		}
	}

	/**
	 * {@inheritDoc }
	 */
	@Override
	public void keyPressed(KeyEvent ke) {
		if(ke.getKeyCode() == KeyEvent.VK_DOWN)
		{
			this.dir = Direction.DOWN;
		}
		else if(ke.getKeyCode() == KeyEvent.VK_UP)
		{
			this.dir = Direction.UP;
		}
		else if(ke.getKeyCode() == KeyEvent.VK_LEFT)
		{
			this.dir = Direction.LEFT;
		}
		else if(ke.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			this.dir = Direction.RIGHT;
		}
	}



	/**
	 * Moves the pacman and the ghosts.
	 */
	private void move() 
	{
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
			JOptionPane.showMessageDialog(null, "Bravo !! Vous avez gagn�", "Information", JOptionPane.OK_CANCEL_OPTION, img); 
		}
	}



	public void keyReleased(KeyEvent arg0) {}

	public void keyTyped(KeyEvent arg0) {}

}