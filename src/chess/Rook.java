package chess;

public class Rook extends Soldier{
	private boolean first_move;

	public Rook(char color, int row, int col, Soldier[][] board) {
		super(color, row, col, board);
		this.first_move = true;
	}
	
	public boolean move(int row, int col, Soldier[][] board) {
		if(!super.move(row, col, board)) {
			Helper_functions.print_move_err();
			return false;
		}
		
		if(((this.curr_col == col) || (this.curr_row == row)) 
				&& Helper_functions.horizontal_legal_way((Soldier)this,row,col,board)) {
			
			if(board[row][col] != null) {
				board[row][col].alive = false;
			}
			
			Helper_functions.init_location((Soldier) this,row,col,board);
			this.first_move = false;
			return true;
		}
		
		else {
			Helper_functions.print_move_err();
			return false;
		}
	}
}
