package chess;

public abstract class Soldier {
	protected Color color;
	protected int curr_row;
	protected int curr_col;
	protected boolean first_move;
	protected Color_set set;
	protected Color_set other_set;
	protected int last_row = -1;
	protected int last_col = -1;

	public boolean is_legal(int row, int col, Soldier[][] board) {
		// initial legality tests
		return row >= 0 && row <= 7 && col >= 0 && col <= 7
				&& ((board[row][col] == null) || (board[row][col].color != this.color))
				&& !((this.curr_row == row) && (this.curr_col == col));
	};

	public Soldier(Color color, int row, int col, Soldier[][] board, Color_set set, Color_set other_set) {
		this.color = color;
		this.curr_row = row;
		this.curr_col = col;
		board[row][col] = this;
		this.first_move = true;
		this.set = set;
		this.other_set = other_set;
	}

	public boolean move(int row, int col, Soldier[][] board) {
		Soldier target = board[row][col];// may be null
		if (target != null) {
			Class c = target.getClass();
			target.set.get(c).remove(target);// deleting the target from the game
		}

		this.first_move = false;
		Pair<Integer,Integer> p = new Pair<>(this.curr_row,this.curr_col);
		this.init_location(row, col, board);
		
		if(this.other_set.check(this.set.get_king(),board)) {
			this.undo_move();
			return false;
		}
		
		if(this.set.check(this.other_set.get_king(),board)) {
			other_set.set_in_check(true);
		}
		
		this.last_row = p.first;
		this.last_col = p.second;
		return true;

	}

	private void init_location(int row, int col, Soldier[][] board) {
		board[this.curr_row][this.curr_col] = null;// emptying the previous location
		this.curr_row = row;
		this.curr_col = col;
		board[row][col] = this;
	}
	
	public void undo_move() {
		this.curr_row = last_row;
		this.curr_col = last_col;
	}

}
