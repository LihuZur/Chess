package chess;

public class King extends Soldier{
	
	public King(Color color, int row, int col, Color_set set) {
		super(color, row, col,set);
		char letter = color == Color.WHITE ? '♔' : '♚';
		this.set_letter(letter);
	}
	
	public boolean is_legal(int row, int col) {
		return super.is_legal(row,col) && 
			   ((Math.abs(row - this.get_row()) <= 1) && (Math.abs(col - this.get_col()) <= 1));
	}	
}
