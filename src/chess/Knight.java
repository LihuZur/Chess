package chess;

public class Knight extends Soldier {
	
	public Knight(Color color, int row, int col, Soldier[][] board,Color_set set,Color_set other_set) {
		super(color, row, col, board,set,other_set);
	}
	
	public boolean is_legal(int row, int col, Soldier[][] board) {
		return super.is_legal(row, col, board) && 
			   (((col == this.curr_col - 2) && (Math.abs(row - this.curr_row) == 1))||
			   ((col == this.curr_col - 1) && (Math.abs(row - this.curr_row) == 2))||
			   ((col == this.curr_col + 1) && (Math.abs(row - this.curr_row) == 2))||
			   ((col == this.curr_col + 2) && (Math.abs(row - this.curr_row) == 1)));
	}
}
