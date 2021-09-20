package chess;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Color_set extends HashMap<Class,HashSet<Soldier>>{
	private boolean in_check;
	private Board board;
	
	public Color_set(Board board) {
		this.board = board;
	}

	protected boolean is_in_check() {
		Soldier this_king = this.get_king();
		return this.board.is_threatened(this_king);
	}
	
	protected Soldier get_king() {
		return this.get(King.class).iterator().next();
	}
	
	public boolean get_in_check() {
		return this.in_check;
	}
	
	public boolean has_legal_moves() {
		for(HashSet<Soldier> s: this.values()) {
			for(Soldier sol: s) {
				for(int i=0;i<8;i++) {
					for(int j=0;j<8;j++) {
						if(sol.is_legal(i, j)) {
							boolean legal = sol.move(i, j);
							if(legal) {
								sol.undo_move();
								return true;
							}
						}
					}
				}
			}
		}
		
		return false;
	}
	
	
	public void update_in_check() {
		this.in_check = this.is_in_check();
	}
	
	public Board get_board() {
		return this.board;
	}
}

