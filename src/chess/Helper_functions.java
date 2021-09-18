package chess;

import java.util.HashSet;

public class Helper_functions {
	public static void print_move_err() {
		System.out.println("Illegal move!");
	}
		
	public static void print_check_err() {
		System.out.println("This move puts you on a check!");
	}

	public static boolean diagonal_legal_way(Soldier s, int row, int col, Soldier[][] board) {
		int i_move = (col > s.curr_col && row > s.curr_row) || (col <= s.curr_col && row > s.curr_row)? 1 : -1;
		int j_move = col > s.curr_col ? 1 : -1;
		
		for(int i = s.curr_row + i_move, j = s.curr_col + j_move;i!=row;i+=i_move,j+=j_move) {
			if(board[i][j] != null) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean horizontal_legal_way(Soldier s, int row, int col, Soldier[][] board) {
		int i_move = (row == s.curr_row && col > s.curr_col) || (col == s.curr_col && row > s.curr_row)? 1 : -1;
		int target = row == s.curr_row ? col : row;
		
		if(row == s.curr_row) {
			for(int i = s.curr_col + i_move;i!=target;i+=i_move) {
				if(board[row][i] != null) {
					return false;
				}
			}
		}
		
		else {//col == s.curr_col
			for(int i = s.curr_row + i_move;i!=target;i+=i_move) {
				if(board[i][col] != null) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static void init_location(Soldier s,int row, int col, Soldier[][] board) {
		board[s.curr_row][s.curr_col] = null;//emptying the previous location
		s.curr_row = row;
		s.curr_col = col;
		board[row][col] = s;
	}
	
	/*public boolean move(int row, int col, Soldier[][] board) {
		int small_move = this.color == 'W' ? -1 : 1;
		int big_move = this.color == 'W' ? -2 : 2;
		if(!super.move(row, col, board)) {
			Helper_functions.print_move_err();
			return false;
		}
		//move
		if(col == this.curr_col) {
			if(this.color == 'W') {
				if(row == this.curr_row - 1) {
					if(board[row][col] == null) {
						this.first_move = false;
						this.init_location(row,col,board);
						return true;
					}
					else {
						Helper_functions.print_move_err();
						return false;
					}
				}
				
				else if(row == this.curr_row - 2 && first_move){
					if(board[row][col] == null) {
						this.first_move = false;
						this.init_location(row,col,board);
						return true;
					}
					else {
						Helper_functions.print_move_err();
						return false;
					}
				}
				
				Helper_functions.print_move_err();
				return false;
				}
			
			else {//color == 'B'
				if(row == this.curr_row + 1) {
					if(board[row][col] == null) {
						this.first_move = false;
						this.init_location(row,col,board);
						return true;
					}
					else {
						Helper_functions.print_move_err();
						return false;
					}
				}
				
				else if(row == this.curr_row + 2 && first_move){
					if(board[row][col] == null) {
					this.first_move = false;
					this.init_location(row,col,board);
					return true;
					}
					else {
						Helper_functions.print_move_err();
						return false;
					}
				}
				
				Helper_functions.print_move_err();
				return false;
			}
		}
		//attack
		else {
			if((this.color == 'W' && row == curr_row -1 && Math.abs(col - this.curr_col) == 1)||
			   (this.color == 'B' && row == curr_row +1 && Math.abs(col - this.curr_col) == 1)) {
				if(board[row][col]!= null && board[row][col].color != this.color) {
					board[row][col].alive = false;
					this.first_move = false;
					this.init_location(row,col,board);
					return true;
				}
				
				else {
					Helper_functions.print_move_err();
					return false;
				}
			}
			
			else {
				Helper_functions.print_move_err();
				return false;
			}
		}
	}*/
	
	/*public static boolean diagonal_legal_way(Soldier s, int row, int col, Soldier[][] board) {
		int i_move = (col > s.curr_col && row > s.curr_row) || (col <= s.curr_col && row > s.curr_row)
					 ? 1 : -1;
		int j_move = col > s.curr_col ? 1 : -1;
		
		if(col > s.curr_col) {//right
			if(row > s.curr_row){//down-right
				for(int i = s.curr_row + 1, j = s.curr_col + 1;i!=row;i++,j++) {
					if(board[i][j] != null) {
						return false;
					}
				}
				return true;
			}
			
			else {//up-right
				for(int i = s.curr_row - 1, j = s.curr_col + 1;i!=row;i--,j++) {
					if(board[i][j] != null) {
						return false;
					}
				}
				return true;
			}
		}
		
		else {//left
			if(row > s.curr_row) {//down-left
				for(int i = s.curr_row + 1, j = s.curr_col - 1;i!=row;i++,j--) {
					if(board[i][j] != null) {
						return false;
					}
				}
				return true;
			}
			
			else {//up-left
				for(int i = s.curr_row - 1, j = s.curr_col - 1;i!=row;i--,j--) {
					if(board[i][j] != null) {
						return false;
					}
				}
				return true;
			}
		}
	}*/
	
	/*public static boolean horizontal_legal_way(Soldier s, int row, int col, Soldier[][] board) {
		if(row == s.curr_row) {//right or left
			if(col > s.curr_col) {//right
				for(int i = s.curr_col + 1;i!=col;i++) {
					if(board[row][i] != null) {
						return false;
					}
				}
				return true;
			}
			
			else {//left
				for(int i = s.curr_col - 1;i!=col;i--) {
					if(board[row][i] != null) {
						return false;
					}
				}
				return true;
			}
		}
		
		else {//up or down
			if(row > s.curr_row) {//down
				for(int i = s.curr_row + 1;i!=col;i++) {
					if(board[i][col] != null) {
						return false;
					}
				}
				return true;
			}
			
			else {//up
				for(int i = s.curr_col - 1;i!=col;i--) {
					if(board[i][col] != null) {
						return false;
					}
				}
				return true;
			}
		}
	}*/
	
	/*if(col == this.curr_col) {
				if(row == this.curr_row + small_move) {
					if(board[row][col] == null) {
						return true;
					}
					
					else {
						return false;
					}
				}
				
				else if(row == this.curr_row + big_move && first_move){
					if(board[row][col] == null) {
						return true;
					}
					
					else {
						return false;
					}
				}
				
				return false;
		}*/
}
/*package chess;

import java.util.HashSet;

public class Color_set {
	Color color;
	King king;
	HashSet<Queen> queens;
	HashSet<Rook> rooks;
	HashSet<Knight> knights;
	HashSet<Bishop> Bishops;
	HashSet<Pawn> pawns;
	
	public Color_set(Color color, King king, HashSet<Queen> queens, HashSet<Rook> rooks, 
			HashSet<Knight> knights, HashSet<Bishop> Bishops, HashSet<Pawn> pawns) {
		this.color = color;
		this.king = king;
		this.queens = queens;
		this.rooks = rooks;
		this.knights = knights;
		this.Bishops = Bishops;
		this.pawns = pawns;		
	}
}*/
/*//initializing the board
		Soldier[][] board = new Soldier[8][8];
		//black
		HashSet<Rook> brs = new HashSet<>();
		brs.add(new Rook(Color.BLACK,0,0,board));
		brs.add(new Rook(Color.BLACK,0,7,board));
		HashSet<Knight> bks = new HashSet<>();
		bks.add(new Knight(Color.BLACK,0,1,board));
		bks.add(new Knight(Color.BLACK,0,6,board));
		HashSet<Bishop> bbs = new HashSet<>();
		bbs.add(new Bishop(Color.BLACK,0,2,board));
		bbs.add(new Bishop(Color.BLACK,0,5,board));
		HashSet<Queen> bqs = new HashSet<>();
		bqs.add(new Queen(Color.BLACK,0,3,board));
		King bk = new King(Color.BLACK,0,4,board);
		HashSet<Pawn> bps = new HashSet<>();
		bps.add(new Pawn(Color.BLACK,1,0,board));
		bps.add(new Pawn(Color.BLACK,1,1,board));
		bps.add(new Pawn(Color.BLACK,1,2,board));
		bps.add(new Pawn(Color.BLACK,1,3,board));
		bps.add(new Pawn(Color.BLACK,1,4,board));
		bps.add(new Pawn(Color.BLACK,1,5,board));
		bps.add(new Pawn(Color.BLACK,1,6,board));
		bps.add(new Pawn(Color.BLACK,1,7,board));
		Color_set black = new Color_set(Color.BLACK,bk,bqs,brs,bks,bbs,bps);
				
		//white
		HashSet<Rook> wrs = new HashSet<>();
		wrs.add(new Rook(Color.WHITE,7,0,board));
		wrs.add(new Rook(Color.WHITE,7,7,board));
		HashSet<Knight> wks = new HashSet<>();
		wks.add(new Knight(Color.WHITE,7,1,board));
		wks.add(new Knight(Color.WHITE,7,6,board));
		HashSet<Bishop> wbs = new HashSet<>();
		wbs.add(new Bishop(Color.WHITE,7,2,board));
		wbs.add(new Bishop(Color.WHITE,7,5,board));
		HashSet<Queen> wqs = new HashSet<>();
		wqs.add(new Queen(Color.WHITE,7,3,board));
		King wk = new King(Color.WHITE,7,4,board);
		HashSet<Pawn> wps = new HashSet<>();
		wps.add(new Pawn(Color.WHITE,6,0,board));
		wps.add(new Pawn(Color.WHITE,6,1,board));
		wps.add(new Pawn(Color.WHITE,6,2,board));
		wps.add(new Pawn(Color.WHITE,6,3,board));
		wps.add(new Pawn(Color.WHITE,6,4,board));
		wps.add(new Pawn(Color.WHITE,6,5,board));
		wps.add(new Pawn(Color.WHITE,6,6,board));
		wps.add(new Pawn(Color.WHITE,6,7,board));
		Color_set white = new Color_set(Color.WHITE,wk,wqs,wrs,wks,wbs,wps);	
		*/
