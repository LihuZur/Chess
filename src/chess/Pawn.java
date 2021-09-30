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
		if(col == this.get_col() && !s.isPresent()) {
			if ((row == this.get_row() + big_move && get_first_move())) {//The enemy may be able to attempt en passant next turn
				this.player.set_can_get_ep(this);
				this.player.set_ep_now(true);
				return true;
			}

			else if(row == this.get_row() + small_move){
				return true;
			}

			return false;

		}

		else {
			if((row == this.get_row() + small_move && Math.abs(col - this.get_col()) == 1)){
			   if((s.isPresent() && s.get().get_color() != this.get_color())) {//attack
					return true;
				}
			
				else {//e.p.
					return en_passant(this,row,col);
				}
			}

		}
		return false;

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
					new_soldier_class = Bishop.class;
					s = new Bishop(this.get_color(),this.get_row(),this.get_col(),this.player);
					break;
				case '♘':
				case '♞':
				case 'N':
				case 'S':
					new_soldier_class = Knight.class;
					s = new Knight(this.get_color(),this.get_row(),this.get_col(),this.player);
					break;
			}

			//adding the new Soldier and removing the Pawn
			this.player.get(new_soldier_class).add((new_soldier_class.cast(s)));
			this.player.remove_soldier(Pawn.class,this.get_row(),this.get_col());

			this.promotion_letter = '\0';

	}

	public void set_promotion_letter(char c){
		this.promotion_letter = c;
	}

	public char get_promotion_letter(){
		return this.promotion_letter;
	}

	private boolean en_passant(Pawn p, int row, int col){
		Board b = this.player.get_board();
		Player enemy = this.get_color() == Color.WHITE ? b.get_black() : b.get_white();
		Pawn enemy_pawn = enemy.get_can_get_ep();

		if(enemy_pawn == null){
			return false;
		}

		if((this.get_color() == Color.WHITE && this.get_row() == 4) || (this.get_color() == Color.BLACK && this.get_row() == 3)){
			if(Math.abs(this.get_col() - enemy_pawn.get_col()) == 1){
				enemy.remove_soldier(Pawn.class, enemy_pawn.get_row(), enemy_pawn.get_col());//removing the enemy pawn
				b.get_board()[enemy_pawn.get_row()][enemy_pawn.get_col()] = null;
				return true;
			}
		}

		return false;
	}
}
