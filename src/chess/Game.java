package chess;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Game {
	private Board board;

	public void play() throws ClassNotFoundException {

		this.board = new Board();

		boolean white_turn = true;
		Scanner scan = new Scanner(System.in);
		String s;
		Color_set curr_set, other_set;
		
		// game loop
		while (true) {
			board.print();
			s = white_turn ? "White turn: " : "Black turn: ";
			System.out.println(s);
			curr_set = white_turn ? board.get_set(Color.WHITE) : board.get_set(Color.BLACK);
			other_set = white_turn ? board.get_set(Color.BLACK) : board.get_set(Color.WHITE);
			Pair<Soldier, Pair<Integer, Integer>> move_now;
			boolean legal_move = false; 
			
			if(!curr_set.has_legal_moves()) {
				s = white_turn ? " Black wins!" : "White wins!";
				if(curr_set.get_in_check()) {
					System.out.println("Checkmate!" + s);
				}
				
				else {
					System.out.println("Stalemate!" + s);
				}
				
				break;
			}
			
			do {
				System.out.println(s + "Please enter your move");
				s = scan.nextLine();
				move_now = parse_input(white_turn, curr_set, other_set, s);
				if(move_now != null) {
					legal_move = move_now.first.move(move_now.second.first, move_now.second.second);
				}
				
			} while (!legal_move);

			other_set.update_in_check();
			white_turn = !white_turn;
		}
	}

	private Pair<Soldier, Pair<Integer, Integer>> parse_input(Boolean white_turn, Color_set curr_set,
			Color_set other_set, String input) throws ClassNotFoundException {
		// ADD SPECIAL MOVEMENT CASES
		if (input.length() < 2 || input.length() > 6) {
			Board.print_move_err();
			return null;
		}

		char c1 = input.charAt(input.length() - 1);
		char c2 = input.charAt(input.length() - 2);
		boolean colon_at_end = input.charAt(input.length() - 1) == ':' ? true : false;
		boolean capture_move = colon_at_end;
		Pair<Character, Character> dest = new Pair<>(null, null);
		Pair<Soldier, Pair<Integer, Integer>> res = new Pair<>(null, null);
		Class c;
		final HashSet<Character> board_letters = new HashSet<>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'));
		final HashSet<Character> board_numbers = new HashSet<>(Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8'));
		final HashSet<Character> class_letters = new HashSet<>(Arrays.asList('Q', 'R', 'K', 'B', 'N', 'S', '='));

		if ((!colon_at_end) && (!board_numbers.contains(c1))) {
			Board.print_move_err();
			return null;
		}

		input = clean_input(input, class_letters, c1, c2);// cleaning added characters

		switch (input.charAt(0)) {

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
			Board.print_move_err();
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
				Board.print_move_err();
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
				Board.print_move_err();
				return null;
			}
		}

		else if (middle.length() == 2) {// a letter and a number
			if (board_letters.contains(letter) && board_numbers.contains(middle.charAt(1))) {
				letter_info = letter;
				number_info = middle.charAt(1);
			}

			else {// illegal input
				Board.print_move_err();
				return null;
			}
		}

		else {// illegal input
			Board.print_move_err();
			return null;
		}

		// finding the Soldier
		Soldier s = find_soldier(curr_set, c, letter_info, number_info, middle, res.second);

		if (s == null) {
			return null;
		}

		res.first = s;
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
			Pair<Integer, Integer> input_p) {
		Set<Soldier> s = curr_set.get(c);
		Soldier res = null;
		HashSet<Soldier> potential = new HashSet<>();

		for (Soldier sol : s) {
			if (sol.is_legal(input_p.first, input_p.second)) {
				potential.add(sol);
			}
		}

		HashSet<Soldier> found = new HashSet<>();

		for (Soldier sol : potential) {

			if (middle.length() == 1) {
				if (letter_info != '\0') {// a letter is given
					if (sol.get_col() == init_col(letter_info)) {
						found.add(sol);
					}
				} else {// a number is given
					if (sol.get_row() == init_row(number_info)) {
						found.add(sol);
					}
				}
			}

			else {// middle.length() == 2
				Pair<Integer, Integer> p = translate_location(letter_info, number_info);
				if(p.first == sol.get_row() && p.second == sol.get_col()) {
					found.add(sol);
				}
			}

		}
		
		if(found.size() == 1) {
			return found.iterator().next();
		}
		
		Board.print_move_err();
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
