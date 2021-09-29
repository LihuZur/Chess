package chess;

import java.util.Optional;

public abstract class Soldier {
	private Color color;
	private int curr_row;
	private int curr_col;
	private boolean first_move;
	protected Player player;
	private char letter;

	public boolean is_legal(int row, int col) {
		// initial legality tests
		Optional<Soldier> s = this.player.get_board().get(row, col);
		if (s == null) {
			return false;
		}

		if (!s.isPresent()) {
			return true;
		}

		return (s.get().color != this.color) && !((this.curr_row == row) && (this.curr_col == col));
	}

	;

	public Soldier(Color color, int row, int col, Player player) {
		this.color = color;
		this.curr_row = row;
		this.curr_col = col;
		this.first_move = true;
		this.player = player;
		this.player.get_board().get_board()[row][col] = this;
	}

	public boolean move(int row, int col) {
		Soldier target = this.player.get_board().get_board()[row][col];// may be null
		if (target != null && target.getClass() != King.class) {
			Class<? extends Soldier> c = target.getClass();
			target.player.get(c).remove(target);// deleting the target from the game
		}

		boolean was_first_move = this.first_move;
		this.first_move = false;
		boolean is_in_check = this.player.get_in_check();

		if (!this.try_move(row, col)) {
			if (was_first_move) {
				this.first_move = true;
			}
			return false;
		}

		this.do_move(row, col);


		this.player.set_can_get_ep(null);

		if (promotion_check(this,row,col)) {
			((Pawn) this).pawn_promotion(row, col);
		}

		return true;
	}

	private void init_location(int row, int col) {
		this.player.get_board().get_board()[this.curr_row][this.curr_col] = null;// emptying the previous location
		this.curr_row = row;
		this.curr_col = col;
		this.player.get_board().get_board()[row][col] = this;
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

	public abstract Soldier clone(Player p);

	private boolean do_move(int row, int col) {
		this.init_location(row, col);
		return !this.player.is_in_check();
	}

	public boolean try_move(int row, int col) {
		Board b = this.player.get_board().clone();
		Soldier curr = b.get_board()[this.curr_row][this.curr_col];
		if(this.getClass() == Pawn.class && ((Pawn) this).get_promotion_letter() != '\0'){
			((Pawn) curr).set_promotion_letter(((Pawn) this).get_promotion_letter());
		}
		boolean move = curr.do_move(row, col);
		if (!move) {
			return false;
		}

		if(promotion_check(curr,row,col) && ((Pawn) curr).get_promotion_letter() == '\0'){
			System.out.println("Unspecified promotion!");
			return false;
		}

		return true;

	}


	public boolean promotion_check(Soldier s,int row, int col){
		return (((s.get_color() == Color.WHITE && row == 7) || (s.get_color() == Color.BLACK && row == 0)) && s.getClass() == Pawn.class);
	}
}
