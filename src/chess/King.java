package chess;

public class King extends Soldier{
	private boolean first_move;
	
	public King(char color, int row, int col, Soldier[][] board) {
		super(color, row, col, board);
		this.first_move = true;
	}
	
	public boolean move(int row, int col, Soldier[][] board) {
		if(!super.move(row, col, board)) {
			Helper_functions.print_move_err();
			return false;
		}
		
		if(((Math.abs(row - this.curr_row) <= 1) && (Math.abs(col - this.curr_col) <= 1)))
		{
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
