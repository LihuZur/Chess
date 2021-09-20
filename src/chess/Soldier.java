package chess;

import java.util.Optional;

public abstract class Soldier {
	private Color color;
	private int curr_row;
	private int curr_col;
	private boolean first_move;
	protected Color_set set;
	private int last_row;
	private int last_col;
	private Soldier last_soldier;
	private char letter;

	public boolean is_legal(int row, int col) {
		// initial legality tests
		Optional<Soldier> s = this.set.get_board().get(row, col);
		if(s == null) {
			return false;
		}
		return  ((!s.isPresent()) || (s.get().color != this.color))
				&& !((this.curr_row == row) && (this.curr_col == col));
	};

	public Soldier(Color color, int row, int col, Color_set set) {
		this.color = color;
		this.curr_row = row;
		this.curr_col = col;
		this.first_move = true;
		this.set = set;
		this.set.get_board().get_board()[row][col] = this;
		this.last_row = curr_row;
		this.last_col = curr_col;
	}

	public boolean move(int row, int col) {
		Soldier target = this.set.get_board().get_board()[row][col];// may be null
		if (target != null) {
			Class c = target.getClass();
			target.set.get(c).remove(target);// deleting the target from the game
		}

		this.first_move = false;
		Pair<Integer,Integer> p = new Pair<>(this.curr_row,this.curr_col);
		this.last_soldier = this.set.get_board().get_board()[row][col];
		this.init_location(row, col);
		
		if(this.set.is_in_check()) {
			this.undo_move();
			return false;
		}
		
		this.last_row = p.first;
		this.last_col = p.second;
		return true;

	}

	private void init_location(int row, int col) {
		this.set.get_board().get_board()[this.curr_row][this.curr_col] = null;// emptying the previous location
		this.curr_row = row;
		this.curr_col = col;
		this.set.get_board().get_board()[row][col] = this;
	}
	
	public void undo_move() {
		this.set.get_board().get_board()[this.curr_row][this.curr_col] = this.last_soldier;
		this.curr_row = this.last_row;
		this.curr_col = this.last_col;
		this.set.get_board().get_board()[this.curr_row][this.curr_col] = this;
	}
	
	public char get_letter() {
		return this.letter;
	}
	
	public void set_letter(char letter) {
		this.letter = letter;
	}
	
	public int get_row() {
		return this.curr_row;
	}
	
	public int get_col() {
		return this.curr_col;
	}
	
	public Color get_color() {
		return this.color;
	}
	
	public boolean get_first_move() {
		return this.first_move;
	}
	

}
