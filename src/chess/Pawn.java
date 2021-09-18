package chess;

public class Pawn extends Soldier{
	
	public Pawn(Color color, int row, int col, Soldier[][] board,Color_set set,Color_set other_set) {
		super(color, row, col, board,set,other_set);
	}
	
	
	public boolean is_legal(int row, int col, Soldier[][] board) {
		if(!super.is_legal(row, col, board)) {
			return false;
		}
		
		int small_move = this.color == Color.WHITE ? -1 : 1;
		int big_move = this.color == Color.WHITE ? -2 : 2;
		//move
		if(col == this.curr_col) {
			if(board[row][col] == null) {
				if((row == this.curr_row + small_move) || (row == this.curr_row + big_move && first_move)) {
					return true;
				}
			}
			
			return false;
		}
		//attack/error
		else {
			if((row == this.curr_row + small_move && Math.abs(col - this.curr_col) == 1) &&
			   (board[row][col]!= null && board[row][col].color != this.color)) {
					return true;
			}
			
			else {
				return false;
			}
		}
	}
}
