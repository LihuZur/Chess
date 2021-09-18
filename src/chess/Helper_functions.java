package chess;


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
	
	
}