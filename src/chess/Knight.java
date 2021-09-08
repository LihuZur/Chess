package chess;

public class Knight extends Soldier {
	
	public Knight(char color, int row, int col, Soldier[][] board) {
		super(color, row, col, board);
	}
	
	public boolean move(int row, int col, Soldier[][] board) {
		if(!super.move(row, col, board)) {
			Helper_functions.print_move_err();
			return false;
		}
		
		if(((col == this.curr_col - 2) && (Math.abs(row - this.curr_row) == 1))||
		   ((col == this.curr_col - 1) && (Math.abs(row - this.curr_row) == 2))||
		   ((col == this.curr_col + 1) && (Math.abs(row - this.curr_row) == 2))||
		   ((col == this.curr_col + 2) && (Math.abs(row - this.curr_row) == 1)))
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
