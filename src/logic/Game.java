package logic;

import data.Entity;
import data.GhostType;

public class Game implements IGame{

	@Override
	public Entity[][] getBoard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getBoardHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getBoardWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getBestScore() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasWon() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasLost() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IPacMan getPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IGhost getGhost(GhostType type) {
		// TODO Auto-generated method stub
		return null;
	}

}