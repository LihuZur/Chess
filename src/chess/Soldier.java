package chess;

public abstract class Soldier {
	protected final char color;//either 'W' or 'B
	protected int curr_row;
	protected int curr_col;
	protected boolean alive;
	
	public Soldier(char color, int row, int col,Soldier[][] board) {
		this.color = color;
		this.curr_row = row;
		this.curr_col = col;
		this.alive = true;
		board[row][col] = this;
	}
	
	
	public boolean move(int row, int col, Soldier[][] board) {
		return row >= 0 && row <= 7 && 
			   col >= 0 && col <= 7 &&
			   ((board[row][col] == null) || (board[row][col].color != this.color)) && alive
			   && !((this.curr_row == row) && (this.curr_col == col));
	}
	
}
