package chess;

public class Bishop extends Soldier{
	
	public Bishop(Color color, int row, int col, Soldier[][] board, Color_set set, Color_set other_set) {
		super(color, row, col, board,set, other_set);
	}
	
	public boolean is_legal(int row, int col, Soldier[][] board) {
		return  super.is_legal(row, col, board) && 
				Math.abs(col - this.curr_col) == Math.abs(row - this.curr_row);
	}	
}
