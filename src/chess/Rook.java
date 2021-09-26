package chess;

public class Rook extends Soldier{

	public Rook(Color color, int row, int col, Player player) {
		super(color, row, col, player);
		char letter = color == Color.WHITE ? '♖' : '♜';
		this.set_letter(letter);
	}
	
	public boolean is_legal(int row, int col) {
		return super.is_legal(row, col) && 
			   (((this.get_col() == col) || (this.get_row() == row)) 
						&& this.player.get_board().horizontal_legal_way(this, row, col));
	}

	public Soldier clone(Player p){
		return new Rook(this.get_color(),this.get_row(),this.get_col(),p);
	}
}
