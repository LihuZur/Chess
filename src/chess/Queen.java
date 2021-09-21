package chess;

public class Queen extends Soldier{
	
	public Queen(Color color, int row, int col,Player player) {
		super(color, row, col,player);
		char letter = color == Color.WHITE ? '♕' : '♛';
		this.set_letter(letter);
	}
	
	public boolean is_legal(int row, int col) {
		return  super.is_legal(row,col) && 
				(((Math.abs(col - this.get_col()) == Math.abs(row - this.get_row()))//diagonal move
				&& this.player.get_board().diagonal_legal_way((Soldier)this,row,col))||
				(((this.get_col() == col) || (this.get_row()== row))//horizontal move
				&& this.player.get_board().horizontal_legal_way((Soldier)this,row,col)));
	}
}
