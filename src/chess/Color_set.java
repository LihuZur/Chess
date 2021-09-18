package chess;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Color_set extends HashMap<Class,HashSet<Soldier>>{
	private boolean in_check;

	protected boolean check(Soldier enemy_king, Soldier[][] board) {
		for(HashSet<Soldier> s: this.values()) {
			for(Soldier sol: s) {
				if(sol.is_legal(enemy_king.curr_row,enemy_king.curr_col,board)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	protected Soldier get_king() {
		return this.get(King.class).iterator().next();
	}
	
	public boolean get_in_check() {
		return this.in_check;
	}
	
	public boolean has_legal_moves(Soldier[][] board) {
		for(HashSet<Soldier> s: this.values()) {
			for(Soldier sol: s) {
				for(int i=0;i<8;i++) {
					for(int j=0;j<8;j++) {
						if(sol.is_legal(i, j, board)) {
							boolean legal = sol.move(i, j, board);
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
	
	public void set_in_check(boolean input) {
		this.in_check = input;
	}
}

