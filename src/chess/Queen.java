package chess;

public class Queen extends Soldier{
	
	public Queen(Color color, int row, int col, Soldier[][] board,Color_set set,Color_set other_set) {
		super(color, row, col, board,set,other_set);
	}
	
	public boolean is_legal(int row, int col, Soldier[][] board) {
		return  super.is_legal(row,col,board) && 
				(((Math.abs(col - this.curr_col) == Math.abs(row - this.curr_row))//diagonal move
				&& Helper_functions.diagonal_legal_way((Soldier)this,row,col,board))||
				(((this.curr_col == col) || (this.curr_row == row))//horizontal move
				&& Helper_functions.horizontal_legal_way((Soldier)this,row,col,board)));
	}
}
