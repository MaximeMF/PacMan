package logic;

import data.*;

public class Logic implements ILogic {

	private IPlateau grid;
	
	public Logic() {
		this.grid = new Plateau();
	}
	
	@Override
	public boolean move(int id, int dir) {
		int[] pos = getPos(id);
		boolean moved = false;
		switch(dir) { //check the next case in the grid
		case DIR_UP:
			if(pos[1] > 0)
				if(this.grid.getCase(pos[0], pos[1] - 1) == VIDE) {
					this.grid.changeCell(pos[0], pos[1] - 1, id);
					moved = true;
				}
			break;
		case DIR_LEFT:
			if(pos[0] > 0)
				if(this.grid.getCase(pos[0] - 1, pos[1]) == VIDE) {
					this.grid.changeCell(pos[0] - 1, pos[1], id);
					moved = true;
				}
			break;
		case DIR_RIGHT:
			if(pos[0] < this.grid.getWidth())
				if(this.grid.getCase(pos[0] + 1, pos[1]) == VIDE) {
					this.grid.changeCell(pos[0] + 1, pos[1], id);
					moved = true;
				}
			break;
		case DIR_DOWN:
			if(pos[1] < this.grid.getHeight())
				this.grid.changeCell(pos[0], pos[1] + 1, id);
				this.grid.previous(pos[0], pos[1]);
				if(this.grid.getCase(pos[0], pos[1] + 1) == VIDE) {
					moved = true;
				}
			break;
		}
		return false;
	}
	
	private int[] getPos(int id) {
		return this.grid.getPos(id);
	}

	@Override
	public IPlateau getPlateau() {
		return this.grid;
	}

	@Override
	public IPacman getPlayer() {
		return this.grid.getPlayer();
	}

	@Override
	public IGhost getGhost(int id) {
		return this.grid.getGhost(id);
	}
	
	public boolean hasWon() {
		if(this.grid.getNbGommes() == 0) {
			this.grid = new Plateau();
			return true;
		}
		return false;
	}

}
