package chess;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Game {

	public void play() throws ClassNotFoundException {

		// initializing the board
		Soldier[][] board = new Soldier[8][8];
		Color_set black = new Color_set();
		Color_set white = new Color_set();
		

		HashSet<Soldier> brs = new HashSet<>();
		brs.add(new Rook(Color.BLACK, 0, 0, board, black,white));
		brs.add(new Rook(Color.BLACK, 0, 7, board, black,white));
		black.put(Rook.class, brs);
		HashSet<Soldier> bks = new HashSet<>();
		bks.add(new Knight(Color.BLACK, 0, 1, board, black,white));
		bks.add(new Knight(Color.BLACK, 0, 6, board, black,white));
		black.put(Knight.class, bks);
		HashSet<Soldier> bbs = new HashSet<>();
		bbs.add(new Bishop(Color.BLACK, 0, 2, board, black,white));
		bbs.add(new Bishop(Color.BLACK, 0, 5, board, black,white));
		black.put(Bishop.class, bbs);
		HashSet<Soldier> bqs = new HashSet<>();
		bqs.add(new Queen(Color.BLACK, 0, 3, board, black,white));
		black.put(Queen.class, bqs);
		HashSet<Soldier> bk = new HashSet<>();
		bk.add(new King(Color.BLACK, 0, 4, board, black,white));
		black.put(King.class, bk);
		HashSet<Soldier> bps = new HashSet<>();
		for(int i=0;i<8;i++) {
			bps.add(new Pawn(Color.BLACK, 1, i, board, black,white));
		}
		black.put(Pawn.class, bps);

		

		HashSet<Soldier> wrs = new HashSet<>();
		wrs.add(new Rook(Color.WHITE, 7, 0, board, white,black));
		wrs.add(new Rook(Color.WHITE, 7, 7, board, white,black));
		white.put(Rook.class, wrs);
		HashSet<Soldier> wks = new HashSet<>();
		wks.add(new Knight(Color.WHITE, 7, 1, board, white,black));
		wks.add(new Knight(Color.WHITE, 7, 6, board, white,black));
		white.put(Knight.class, wks);
		HashSet<Soldier> wbs = new HashSet<>();
		wbs.add(new Bishop(Color.WHITE, 7, 2, board, white,black));
		wbs.add(new Bishop(Color.WHITE, 7, 5, board, white,black));
		white.put(Bishop.class, wbs);
		HashSet<Soldier> wqs = new HashSet<>();
		wqs.add(new Queen(Color.WHITE, 7, 3, board, white,black));
		white.put(Bishop.class, wqs);
		HashSet<Soldier> wk = new HashSet<>();
		wk.add(new King(Color.WHITE, 7, 4, board, white,black));
		white.put(King.class, wk);
		HashSet<Soldier> wps = new HashSet<>();
		for(int i=0;i<8;i++) {
			bps.add(new Pawn(Color.WHITE, 6, i, board, white,black));
		}
		white.put(Pawn.class, wps);

		boolean white_turn = true;
		Scanner scan = new Scanner(System.in);
		String s;
		Color_set curr_set, other_set;
		
		// game loop
		while (true) {
			s = white_turn ? "White turn: " : "Black turn: ";
			curr_set = white_turn ? white : black;
			other_set = white_turn ? black : white;
			Pair<Pair<Soldier, Boolean>, Pair<Integer, Integer>> move_now;
			boolean legal_move = false; 
			
			if(!curr_set.has_legal_moves(board)) {
				if(curr_set.get_in_check()) {
					System.out.println("Checkmate!");
				}
				
				else {
					System.out.println("Stalemate!");
				}
				
				break;
			}
			
			do {
				System.out.println(s + "Please enter your move");
				s = scan.nextLine();
				move_now = parse_input(white_turn, curr_set, other_set, s, board);
				if(move_now != null) {
					legal_move = move_now.first.first.move(move_now.second.first, move_now.second.second, board);
				}
				
			} while (!legal_move);


			white_turn = !white_turn;
		}
	}

	private Pair<Pair<Soldier, Boolean>, Pair<Integer, Integer>> parse_input(Boolean white_turn, Color_set curr_set,
			Color_set other_set, String input, Soldier[][] board) throws ClassNotFoundException {
		// ADD SPECIAL MOVEMENT CASES
		if (input.length() < 2 || input.length() > 6) {
			Helper_functions.print_move_err();
			return null;
		}

		char c1 = input.charAt(input.length() - 1);
		char c2 = input.charAt(input.length() - 2);
		boolean colon_at_end = input.charAt(input.length() - 1) == ':' ? true : false;
		boolean capture_move = colon_at_end;
		Pair<Character, Character> dest = new Pair<>(null, null);
		Pair<Pair<Soldier, Boolean>, Pair<Integer, Integer>> res = new Pair<>(new Pair<>(null, false), null);
		Class c;
		final HashSet<Character> board_letters = new HashSet<>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'));
		final HashSet<Character> board_numbers = new HashSet<>(Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8'));
		final HashSet<Character> class_letters = new HashSet<>(Arrays.asList('Q', 'R', 'K', 'B', 'N', 'S', '='));

		if ((!colon_at_end) && (!board_numbers.contains(c1))) {
			Helper_functions.print_move_err();
			return null;
		}

		input = clean_input(input, class_letters, c1, c2);// cleaning added characters

		switch (input.charAt(0)) {// ADD WEIRD PICTURES CASES!

		case '♔':
		case '♚':
		case 'K':
			c = King.class;
			break;
		case '♕':
		case '♛':
		case 'Q':
			c = Queen.class;
			break;
		case '♖':
		case '♜':
		case 'R':
			c = Rook.class;
			break;
		case '♗':
		case '♝':
		case 'B':
			c = Bishop.class;
			break;
		case '♘':
		case '♞':
		case 'N':
		case 'S':
			c = Knight.class;
			break;
		default:
			c = Pawn.class;// pawn or error
		}

		if ((!colon_at_end && board_letters.contains(c2) && board_numbers.contains(c1))) {
			res.second = translate_location(c2, c1);
		}

		else if (colon_at_end && input.length() >= 3 && board_letters.contains(input.charAt(input.length() - 3))
				&& board_numbers.contains(c2)) {
			res.second = translate_location(input.charAt(input.length() - 3), c2);
		}

		else {
			Helper_functions.print_move_err();
			return null;
		}

		int start_index, end_index;
		String middle;
		if (c == Pawn.class) {
			start_index = 0;
		}

		else {
			start_index = 1;
		}

		if (colon_at_end) {
			end_index = input.length() - 3;
		}

		else {
			end_index = input.length() - 2;
		}

		if (start_index < end_index) {
			middle = input.substring(start_index, end_index);// everything except the soldier class and the destination
		}

		else {// there is no middle
			middle = "";
		}

		if (middle.charAt(middle.length() - 1) == 'x' || middle.charAt(middle.length() - 1) == ':') {// removing the
																										// capture
																										// character (if
																										// there is
																										// such)
			middle = middle.substring(0, middle.length() - 1);
			capture_move = true;
			if (colon_at_end && middle.charAt(middle.length() - 1) == ':') {// multiple ":"- error
				Helper_functions.print_move_err();
				return null;
			}
		}

		char letter_info = '\0';// random initialization
		char number_info = '\0';// random initialization
		char letter = middle.charAt(0);
		if (middle.length() == 1) {// a letter or a number
			if (board_letters.contains(letter)) {
				letter_info = letter;
			}

			else if (board_numbers.contains(letter)) {
				number_info = letter;
			}

			else {// illegal input
				Helper_functions.print_move_err();
				return null;
			}
		}

		else if (middle.length() == 2) {// a letter and a number
			if (board_letters.contains(letter) && board_numbers.contains(middle.charAt(1))) {
				letter_info = letter;
				number_info = middle.charAt(1);
			}

			else {// illegal input
				Helper_functions.print_move_err();
				return null;
			}
		}

		else {// illegal input
			Helper_functions.print_move_err();
			return null;
		}

		// finding the Soldier
		Soldier s = find_soldier(curr_set, c, letter_info, number_info, middle, res.second, board);

		if (s == null) {
			return null;
		}

		res.first.first = s;
		return res;

	}

	private static Pair<Integer, Integer> translate_location(char letter, char number) {
		int row = init_row(number);
		int column = init_col(letter);
		return new Pair<Integer, Integer>(row, column);
	}

	private static int init_row(char c) {
		return 8 - Character.getNumericValue(c);
	}

	private static int init_col(char c) {
		return c - 97;// 97 is the ASCII value of 'a'
	}

	private static Soldier find_soldier(Color_set curr_set, Class c, char letter_info, char number_info, String middle,
			Pair<Integer, Integer> input_p, Soldier[][] board) {
		Set<Soldier> s = curr_set.get(c);
		Soldier res = null;
		HashSet<Soldier> potential = new HashSet<>();

		for (Soldier sol : s) {
			if (sol.is_legal(input_p.first, input_p.second, board)) {
				potential.add(sol);
			}
		}

		HashSet<Soldier> found = new HashSet<>();

		for (Soldier sol : potential) {

			if (middle.length() == 1) {
				if (letter_info != '\0') {// a letter is given
					if (sol.curr_col == init_col(letter_info)) {
						found.add(sol);
					}
				} else {// a number is given
					if (sol.curr_row == init_row(number_info)) {
						found.add(sol);
					}
				}
			}

			else {// middle.length() == 2
				Pair<Integer, Integer> p = translate_location(letter_info, number_info);
				if(p.first == sol.curr_row && p.second == sol.curr_col) {
					found.add(sol);
				}
			}

		}
		
		if(found.size() == 1) {
			return found.iterator().next();
		}
		
		Helper_functions.print_move_err();
		return null;
	}



	private String clean_input(String input, HashSet<Character> class_letters, char c1, char c2) {
		String res = input;
		if (class_letters.contains(c1)) {// removing promotion/draw letter if exists
			res = input.substring(0, input.length() - 1);
		}

		else if (c1 == '+' && c2 == '+') {// removing check letters if exist
			res = input.substring(0, input.length() - 2);
		}

		return res;
	}

}
