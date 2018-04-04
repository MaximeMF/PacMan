package logic;

import data.Ghost;
import data.Pacman;
import data.Plateau;

public interface ILogic {
	public boolean move(int id, int dir);
	
	public Plateau getPlateau();
	
	public Pacman getPlayer();
	
	public Ghost getGhost();
}
