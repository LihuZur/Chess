package chess;

public class Helper_functions {
	public static void print_move_err() {
		System.out.println("Illegal move!");
	}
	
	//public static int[] translate_index(String input) {}
	
	public static void print_dead_err() {
		System.out.println("This soldier is dead!");
	}

	public static boolean diagonal_legal_way(Soldier s, int row, int col, Soldier[][] board) {
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
	}
	
	public static boolean horizontal_legal_way(Soldier s, int row, int col, Soldier[][] board) {
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
	}
	
	public static void init_location(Soldier s,int row, int col, Soldier[][] board) {
		board[s.curr_row][s.curr_col] = null;//emptying the previous location
		s.curr_row = row;
		s.curr_col = col;
		board[row][col] = s;
	}
}
