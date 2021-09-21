package chess;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Board {
	private Soldier[][] board = new Soldier[8][8];
	private Player black = new Player(this);
	private Player white = new Player(this);
	
	public Board() {//Initializing the game's starting position
		
		HashSet<Soldier> brs = new HashSet<>();
		brs.add(new Rook(Color.BLACK, 0, 0, black));
		brs.add(new Rook(Color.BLACK, 0, 7, black));
		this.black.put(Rook.class, brs);
		HashSet<Soldier> bks = new HashSet<>();
		bks.add(new Knight(Color.BLACK, 0, 1, black));
		bks.add(new Knight(Color.BLACK, 0, 6, black));
		this.black.put(Knight.class, bks);
		HashSet<Soldier> bbs = new HashSet<>();
		bbs.add(new Bishop(Color.BLACK, 0, 2, black));
		bbs.add(new Bishop(Color.BLACK, 0, 5, black));
		this.black.put(Bishop.class, bbs);
		HashSet<Soldier> bqs = new HashSet<>();
		bqs.add(new Queen(Color.BLACK, 0, 3, black));
		this.black.put(Queen.class, bqs);
		HashSet<Soldier> bk = new HashSet<>();
		bk.add(new King(Color.BLACK, 0, 4, black));
		this.black.put(King.class, bk);
		HashSet<Soldier> bps = new HashSet<>();
		for(int i=0;i<8;i++) {
			bps.add(new Pawn(Color.BLACK, 1, i, black));
		}
		this.black.put(Pawn.class, bps);

		

		HashSet<Soldier> wrs = new HashSet<>();
		wrs.add(new Rook(Color.WHITE, 7, 0, white));
		wrs.add(new Rook(Color.WHITE, 7, 7, white));
		this.white.put(Rook.class, wrs);
		HashSet<Soldier> wks = new HashSet<>();
		wks.add(new Knight(Color.WHITE, 7, 1, white));
		wks.add(new Knight(Color.WHITE, 7, 6, white));
		this.white.put(Knight.class, wks);
		HashSet<Soldier> wbs = new HashSet<>();
		wbs.add(new Bishop(Color.WHITE, 7, 2, white));
		wbs.add(new Bishop(Color.WHITE, 7, 5, white));
		this.white.put(Bishop.class, wbs);
		HashSet<Soldier> wqs = new HashSet<>();
		wqs.add(new Queen(Color.WHITE, 7, 3, white));
		this.white.put(Bishop.class, wqs);
		HashSet<Soldier> wk = new HashSet<>();
		wk.add(new King(Color.WHITE, 7, 4, white));
		this.white.put(King.class, wk);
		HashSet<Soldier> wps = new HashSet<>();
		for(int i=0;i<8;i++) {
			bps.add(new Pawn(Color.WHITE, 6, i, white));
		}
		this.white.put(Pawn.class, wps);
		
		
	}
	
	public Player get_player(Color c) {
		if(c == Color.WHITE) {
			return this.white;
		}
		
		return this.black;
	}
	
	public Soldier[][] get_board(){
		return this.board;
	}
		
	//Helper_functions
	public static void print_move_err() {
		System.out.println("Illegal move!");
	}
		
	public static void print_check_err() {
		System.out.println("This move puts you on a check!");
	}

	public boolean diagonal_legal_way(Soldier s, int row, int col) {
		int i_move = (col > s.get_col() && row > s.get_row()) || (col <= s.get_col() && row > s.get_row())? 1 : -1;
		int j_move = col > s.get_col() ? 1 : -1;
		
		for(int i = s.get_row() + i_move, j = s.get_col() + j_move;i!=row;i+=i_move,j+=j_move) {
			if(board[i][j] != null) {
				return false;
			}
		}
		return true;
	}
	
	public boolean horizontal_legal_way(Soldier s, int row, int col) {
		int i_move = (row == s.get_row() && col > s.get_col()) || (col == s.get_col() && row > s.get_row())? 1 : -1;
		int target = row == s.get_row() ? col : row;
		
		if(row == s.get_row()) {
			for(int i = s.get_col() + i_move;i!=target;i+=i_move) {
				if(board[row][i] != null) {
					return false;
				}
			}
		}
		
		else {//col == s.curr_col
			for(int i = s.get_row() + i_move;i!=target;i+=i_move) {
				if(board[i][col] != null) {
					return false;
				}
			}
		}
		return true;
	}
		
	public static int board_to_status(int i) {
		return i * 2 + 1;
	}
	
	public static int status_to_board(int i) {
		return (int) Math.floor(i / 2.0);
	}
	
	public boolean is_threatened(Soldier s) {
		Player enemy = s.get_color() == Color.WHITE ? this.white : this.black;
		for(Set<Soldier> player : enemy.values()) {
			for(Soldier sol : player) {
				if(sol.is_legal(0, 0)) {
					boolean success = sol.move(s.get_row(), s.get_col());
					if(success) {
						sol.undo_move();
						return true;
					}
					
				}	
			}
		}
		return false;
	}
	
	public Optional<Soldier> get(int row, int col) {
		if(!(row >= 0 && row <= 7 && col >= 0 && col <= 7)) {
			return null;
		}
		if(this.get_board()[row][col] == null) {
			return Optional.empty();
		}
		return Optional.of(this.get_board()[row][col]);
	}
	
	public void print() {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				Optional<Soldier> s = this.get(i, j);
				if(s.isPresent()) {
					System.out.print(s.get().get_letter());
				}
				else {
					System.out.print("#");
				}
				
				System.out.print("\t|");			
			}
			
			System.out.println();
		}
		System.out.println();
	}
}
