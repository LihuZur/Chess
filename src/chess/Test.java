package chess;


public class Test {
	public static void main(String[] args) {
	Board board = new Board();
	
	board.print();
	
	board.get_board()[1][0].move(2,0);
	
	board.print();
	
	board.get_board()[2][0].undo_move();
		
	board.print();
	
	
	/*
	Pair<Integer,Integer> p = new Pair<>(0,2);
	
	Soldier s = find_soldier(black,Rook.class,'\0','\0',"",p,board);
	if(s == null) {
		System.out.println("no result");
	}
	else {
		System.out.println("row: " + s.get_row() + " col: " + s.get_col());
	}
}
	
	private static Soldier find_soldier(Color_set curr_set,Class c,char letter_info,char number_info,String middle,Pair<Integer,Integer> input_p,Soldier[][] board) {
		Set<Soldier> s = curr_set.get(c);
		boolean found = false;
		boolean found_mid = false;
		Soldier res = null;
		
		for(Soldier sol: s) {
			if(sol.is_legal(input_p.first,input_p.second,board)) {
				if(!found) {
					found = true;
					res = sol;
				}
				
				else {//2 or more Soldiers of this kind can make the move
					if(found_mid || middle.length() == 0) {//mid cannot decide
						Board.print_move_err();
						return null;
					}
					else if(middle.length() == 1) {
						if(letter_info != '\0') {//a letter is given
							if(sol.get_col() == init_col(letter_info)) {
								found_mid = true;
								res = sol;
							}	
						}
						else{//a number is given
							if(sol.get_row() == init_row(number_info)) {
								found_mid = true;
								res = sol;
							}
						}
					}
					
					else {//middle.length() == 2
						Pair<Integer,Integer> p = translate_location(letter_info,number_info);
						if(p.first == sol.get_row() && p.second == sol.get_col()) {
							found_mid = true;
							res = sol;
						}
					}
				}
			}
		}
		
		return res;
	}
	
	private static Pair<Integer,Integer> translate_location(char letter, char number){
		int row = init_row(number);
		int column = init_col(letter);
		return new Pair<Integer,Integer>(row,column);
	}
	
	private static int init_row(char c) {
		return 8 - Character.getNumericValue(c);
	}
	
	private static int init_col(char c) {
		return c - 97;//97 is the ASCII value of 'a'
	}*/
}
}
