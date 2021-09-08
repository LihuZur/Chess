package chess;

public class Bishop extends Soldier{
	
	public Bishop(char color, int row, int col, Soldier[][] board) {
		super(color, row, col, board);
	}
	
	public boolean move(int row, int col, Soldier[][] board) {
		if(!super.move(row, col, board)) {
			Helper_functions.print_move_err();
			return false;
		}
		
		if((Math.abs(col - this.curr_col) == Math.abs(row - this.curr_row)) 
				&& Helper_functions.diagonal_legal_way((Soldier)this,row,col,board)) {
			
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
