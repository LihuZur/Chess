package chess;

public class Rook extends Soldier{

	public Rook(Color color, int row, int col, Color_set set) {
		super(color, row, col, set);
		char letter = color == Color.WHITE ? '♖' : '♜';
		this.set_letter(letter);
	}
	
	public boolean is_legal(int row, int col) {
		return super.is_legal(row, col) && 
			   (((this.get_col() == col) || (this.get_row() == row)) 
			   && this.set.get_board().horizontal_legal_way((Soldier)this,row,col));
	}	
}
