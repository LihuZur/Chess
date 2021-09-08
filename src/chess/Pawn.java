package chess;

public class Pawn extends Soldier{
	private boolean first_move;
	
	public Pawn(char color, int row, int col, Soldier[][] board) {
		super(color, row, col, board);
		this.first_move = true;
	}
	
	
	public boolean move(int row, int col, Soldier[][] board) {
		if(!super.move(row, col, board)) {
			Helper_functions.print_move_err();
			return false;
		}
		//move
		if(col == this.curr_col) {
			if(this.color == 'W') {
				if(row == this.curr_row - 1) {
					if(board[row][col] == null) {
						this.first_move = false;
						Helper_functions.init_location((Soldier) this,row,col,board);
						return true;
					}
					else {
						Helper_functions.print_move_err();
						return false;
					}
				}
				
				else if(row == this.curr_row - 2 && first_move){
					if(board[row][col] == null) {
						this.first_move = false;
						Helper_functions.init_location((Soldier) this,row,col,board);
						return true;
					}
					else {
						Helper_functions.print_move_err();
						return false;
					}
				}
				
				Helper_functions.print_move_err();
				return false;
				}
			
			else {//color == 'B'
				if(row == this.curr_row + 1) {
					if(board[row][col] == null) {
						this.first_move = false;
						Helper_functions.init_location((Soldier) this,row,col,board);
						return true;
					}
					else {
						Helper_functions.print_move_err();
						return false;
					}
				}
				
				else if(row == this.curr_row + 2 && first_move){
					if(board[row][col] == null) {
					this.first_move = false;
					Helper_functions.init_location((Soldier) this,row,col,board);
					return true;
					}
					else {
						Helper_functions.print_move_err();
						return false;
					}
				}
				
				Helper_functions.print_move_err();
				return false;
			}
		}
		//attack
		else {
			if((this.color == 'W' && row == curr_row -1 && Math.abs(col - this.curr_col) == 1)||
			   (this.color == 'B' && row == curr_row +1 && Math.abs(col - this.curr_col) == 1)) {
				if(board[row][col]!= null && board[row][col].color != this.color) {
					board[row][col].alive = false;
					this.first_move = false;
					Helper_functions.init_location((Soldier) this,row,col,board);
					return true;
				}
				
				else {
					Helper_functions.print_move_err();
					return false;
				}
			}
			
			else {
				Helper_functions.print_move_err();
				return false;
			}
		}
	}
}
