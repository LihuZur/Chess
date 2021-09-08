package chess;

public class Queen extends Soldier{
	
	public Queen(char color, int row, int col, Soldier[][] board) {
		super(color, row, col, board);
	}
	
	public boolean move(int row, int col, Soldier[][] board) {
		if(!super.move(row, col, board)) {
			Helper_functions.print_move_err();
			return false;
		}
		
		if(((Math.abs(col - this.curr_col) == Math.abs(row - this.curr_row))//diagonal move
				&& Helper_functions.diagonal_legal_way((Soldier)this,row,col,board))||
				(((this.curr_col == col) || (this.curr_row == row))//horizontal move
						&& Helper_functions.horizontal_legal_way((Soldier)this,row,col,board))) 
		{
			if(board[row][col] != null) {
				board[row][col].alive = false;
			}
			
			Helper_functions.init_location((Soldier) this,row,col,board);
			return true;
		}
		
		else {
			Helper_functions.print_move_err();
			return false;
		}
	}
}
