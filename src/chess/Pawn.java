package chess;

import java.util.Optional;

public class Pawn extends Soldier{
	private char promotion_letter;

	public Pawn(Color color, int row, int col,Player player) {
		super(color, row, col,player);
		char letter = color == Color.WHITE ? '♙' : '♟';
		this.set_letter(letter);
		this.promotion_letter = '\0';
	}
	
	
	public boolean is_legal(int row, int col) {
		if(!super.is_legal(row, col)) {
			return false;
		}
		
		int small_move = this.get_color() == Color.WHITE ? 1 : -1;
		int big_move = this.get_color() == Color.WHITE ? 2 : -2;
		Optional<Soldier> s = this.player.get_board().get(row, col);
		//move
		if(col == this.get_col()) {
			if(!s.isPresent()) {
				if((row == this.get_row() + small_move) || (row == this.get_row() + big_move && get_first_move())) {
					return true;
				}
			}
			
			return false;
		}
		//attack/error
		else {
			if((row == this.get_row() + small_move && Math.abs(col - this.get_col()) == 1) &&
			   (s.isPresent() && s.get().get_color() != this.get_color())) {
					return true;
			}
			
			else {
				return false;
			}
		}
	}

	public Soldier clone(Player p){
		return new Pawn(this.get_color(),this.get_row(),this.get_col(),p);
	}

	public void pawn_promotion(int row, int col){
		Class<? extends Soldier> new_soldier_class = Pawn.class;//random initialization
		Soldier s = new Pawn(this.get_color(),this.get_row(),this.get_col(),this.player);//random initialization
		switch(this.promotion_letter){
				case '♕':
				case '♛':
				case 'Q':
					new_soldier_class = Queen.class;
					s = new Queen(this.get_color(),this.get_row(),this.get_col(),this.player);
					break;
				case '♖':
				case '♜':
				case 'R':
					new_soldier_class = Rook.class;
					s = new Rook(this.get_color(),this.get_row(),this.get_col(),this.player);
					break;
				case '♗':
				case '♝':
				case 'B':
					s = new Bishop(this.get_color(),this.get_row(),this.get_col(),this.player);
					break;
				case '♘':
				case '♞':
				case 'N':
				case 'S':
					s = new Knight(this.get_color(),this.get_row(),this.get_col(),this.player);
					break;
			}

			//adding the new Soldier and removing the Pawn
			this.player.get(new_soldier_class).add((new_soldier_class.cast(s)));

			for(Soldier sol : this.player.get(Pawn.class)){
				if(sol.get_row() == this.get_row() && sol.get_col() == this.get_col()){
					this.player.get(Pawn.class).remove(sol);
					break;
				}
			}

			this.promotion_letter = '\0';

	}

	public void set_promotion_letter(char c){
		this.promotion_letter = c;
	}

	public char get_promotion_letter(){
		return this.promotion_letter;
	}
}
