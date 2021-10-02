package chess;

import java.util.*;

public class Board {
	private Soldier[][] board = new Soldier[8][8];
	private Player black = new Player(this);
	private Player white = new Player(this);
	public static final HashSet<Character> board_letters = new HashSet<>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'));
	public static final HashSet<Character> board_numbers = new HashSet<>(Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8'));
	public static final HashSet<Character> class_letters = new HashSet<>(Arrays.asList('Q', 'R', 'K', 'B', 'N', 'S', '='));

	public Board(Boolean b){//Construtor for tests only! creates an empty board
		this.black.put(Pawn.class,new HashSet<Soldier>());
		this.black.put(Rook.class,new HashSet<Soldier>());
		this.black.put(Bishop.class,new HashSet<Soldier>());
		this.black.put(King.class,new HashSet<Soldier>());
		this.black.put(Queen.class,new HashSet<Soldier>());
		this.black.put(Knight.class,new HashSet<Soldier>());

		this.white.put(Pawn.class,new HashSet<Soldier>());
		this.white.put(Rook.class,new HashSet<Soldier>());
		this.white.put(Bishop.class,new HashSet<Soldier>());
		this.white.put(King.class,new HashSet<Soldier>());
		this.white.put(Queen.class,new HashSet<Soldier>());
		this.white.put(Knight.class,new HashSet<Soldier>());

	}
	
	public Board() {//Initializing the game's starting position
		
		HashSet<Soldier> brs = new HashSet<>();
		brs.add(new Rook(Color.BLACK, 7, 0, black));
		brs.add(new Rook(Color.BLACK, 7, 7, black));
		this.black.put(Rook.class, brs);
		HashSet<Soldier> bks = new HashSet<>();
		bks.add(new Knight(Color.BLACK, 7, 1, black));
		bks.add(new Knight(Color.BLACK, 7, 6, black));
		this.black.put(Knight.class, bks);
		HashSet<Soldier> bbs = new HashSet<>();
		bbs.add(new Bishop(Color.BLACK, 7, 2, black));
		bbs.add(new Bishop(Color.BLACK, 7, 5, black));
		this.black.put(Bishop.class, bbs);
		HashSet<Soldier> bqs = new HashSet<>();
		bqs.add(new Queen(Color.BLACK, 7, 3, black));
		this.black.put(Queen.class, bqs);
		HashSet<Soldier> bk = new HashSet<>();
		bk.add(new King(Color.BLACK, 7, 4, black));
		this.black.put(King.class, bk);
		HashSet<Soldier> bps = new HashSet<>();
		for(int i=0;i<8;i++) {
			bps.add(new Pawn(Color.BLACK, 6, i, black));
		}
		this.black.put(Pawn.class, bps);

		

		HashSet<Soldier> wrs = new HashSet<>();
		wrs.add(new Rook(Color.WHITE, 0, 0, white));
		wrs.add(new Rook(Color.WHITE, 0, 7, white));
		this.white.put(Rook.class, wrs);
		HashSet<Soldier> wks = new HashSet<>();
		wks.add(new Knight(Color.WHITE, 0, 1, white));
		wks.add(new Knight(Color.WHITE, 0, 6, white));
		this.white.put(Knight.class, wks);
		HashSet<Soldier> wbs = new HashSet<>();
		wbs.add(new Bishop(Color.WHITE, 0, 2, white));
		wbs.add(new Bishop(Color.WHITE, 0, 5, white));
		this.white.put(Bishop.class, wbs);
		HashSet<Soldier> wqs = new HashSet<>();
		wqs.add(new Queen(Color.WHITE, 0, 3, white));
		this.white.put(Queen.class, wqs);
		HashSet<Soldier> wk = new HashSet<>();
		wk.add(new King(Color.WHITE, 0, 4, white));
		this.white.put(King.class, wk);
		HashSet<Soldier> wps = new HashSet<>();
		for(int i=0;i<8;i++) {
			wps.add(new Pawn(Color.WHITE, 1, i, white));
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
		System.out.println("Illegal move! \n");
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

	public boolean is_threatened(Soldier s) {
		Player enemy = s.get_color() == Color.WHITE ? this.black : this.white;
		for(Set<Soldier> player : enemy.values()) {
			for(Soldier sol : player) {
				if(sol.is_legal(s.get_row(), s.get_col())) {
					boolean success = sol.try_move(s.get_row(), s.get_col());
					if(success) {
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
		for(int i=7;i>=0;i--) {
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

	public Board clone(){
		Board res = new Board();
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				res.get_board()[i][j] = null;
			}
		}
		res.black = this.black.clone(res);
		res.white = this.white.clone(res);
		return res;
	}

	public Player get_black(){
		return this.black;
	}

	public Player get_white(){
		return this.white;
	}

	protected static Board build_board(String[] soldiers, Boolean white_turn){
		Board b = new Board(true);//creating an empty board
		Player white = b.get_white();
		Player black = b.get_black();

		for(int i=0;i<soldiers.length;i++){
			Player curr = soldiers[i].charAt(0) == 'W' ? white : black;
			Color c = soldiers[i].charAt(0) == 'W' ? Color.WHITE : Color.BLACK;
			int row = Character.getNumericValue(soldiers[i].charAt(2));
			int col = Character.getNumericValue(soldiers[i].charAt(3));

			Class<? extends Soldier> sol_class = Pawn.class;
			Soldier s = new Pawn(c,row,col,curr);

			switch(soldiers[i].charAt(1)){
				case 'B':
					sol_class = Bishop.class;
					s = new Bishop(c,row,col,curr);
					break;
				case 'K':
					sol_class = King.class;
					s = new King(c,row,col,curr);
					break;
				case 'N':
					sol_class = Knight.class;
					s = new Knight(c,row,col,curr);
					break;
				case 'Q':
					sol_class = Queen.class;
					s = new Queen(c,row,col,curr);
					break;
				case 'R':
					sol_class = Rook.class;
					s = new Rook(c,row,col,curr);
					break;
				//default: remain as initialized
			}

			curr.get(sol_class).add(s);
		}

		return b;
	}
}
