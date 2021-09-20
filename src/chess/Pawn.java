package chess;

import java.util.Optional;

public class Pawn extends Soldier{
	
	public Pawn(Color color, int row, int col,Color_set set) {
		super(color, row, col,set);
		char letter = color == Color.WHITE ? '♙' : '♟';
		this.set_letter(letter);
	}
	
	
	public boolean is_legal(int row, int col) {
		if(!super.is_legal(row, col)) {
			return false;
		}
		
		int small_move = this.get_color() == Color.WHITE ? -1 : 1;
		int big_move = this.get_color() == Color.WHITE ? -2 : 2;
		Optional<Soldier> s = this.set.get_board().get(row, col);
		//move
		if(col == this.get_col()) {
			if(s.isPresent()) {
				if((row == this.get_row() + small_move) || (row == this.get_row() + big_move && get_first_move())) {
					return true;
				}
			}
			
			return false;
		}
		//attack/error
		else {
			if((row == this.get_row() + small_move && Math.abs(col - this.get_col()) == 1) &&
			   (s.isPresent() && s.get().get_color() != this.get_color())) {
					return true;
			}
			
			else {
				return false;
			}
		}
	}
}
