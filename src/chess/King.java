package chess;

public class King extends Soldier{
	
	public King(Color color, int row, int col, Player player) {
		super(color, row, col,player);
		char letter = color == Color.WHITE ? '♔' : '♚';
		this.set_letter(letter);
	}
	
	public boolean is_legal(int row, int col) {
		return super.is_legal(row,col) && 
			   ((Math.abs(row - this.get_row()) <= 1) && (Math.abs(col - this.get_col()) <= 1));
	}

	public Soldier clone(Player p){
		return new King(this.get_color(),this.get_row(),this.get_col(),p);
	}

}
