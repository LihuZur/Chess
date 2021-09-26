package chess;

public class Knight extends Soldier {
	
	public Knight(Color color, int row, int col,Player player) {
		super(color, row, col,player);
		char letter = color == Color.WHITE ? '♘' : '♞';
		this.set_letter(letter);
	}
	
	public boolean is_legal(int row, int col) {
		return super.is_legal(row, col) && 
			   (((col == this.get_col() - 2) && (Math.abs(row - this.get_row()) == 1))||
			   ((col == this.get_col() - 1) && (Math.abs(row - this.get_row()) == 2))||
			   ((col == this.get_col() + 1) && (Math.abs(row - this.get_row()) == 2))||
			   ((col == this.get_col() + 2) && (Math.abs(row - this.get_row()) == 1)));
	}

	public Soldier clone(Player p){
		return new Knight(this.get_color(),this.get_row(),this.get_col(),p);
	}
}
