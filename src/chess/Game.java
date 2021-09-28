package chess;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Game {
	private Board board;

	public static void main(String[] args) {
		Game game = new Game();
		game.play();
	}

	public void play() {

		this.board = new Board();

		boolean white_turn = true;
		String s;
		Scanner scan = new Scanner(System.in);
		Player curr_player, other_player;

		// game loop
		while (true) {
			board.print();
			s = white_turn ? "White turn: " : "Black turn: ";
			curr_player = white_turn ? board.get_player(Color.WHITE) : board.get_player(Color.BLACK);
			other_player = white_turn ? board.get_player(Color.BLACK) : board.get_player(Color.WHITE);
			Pair<Soldier, Pair<Integer, Integer>> move_now;
			boolean legal_move = false;

			if (!curr_player.has_legal_moves()) {
				s = white_turn ? " Black wins!" : "White wins!";
				if (curr_player.get_in_check()) {
					System.out.println("Checkmate!" + s);
				}

				else {
					System.out.println("Stalemate!" + s);
				}

				break;
			}
			
			System.out.println(s);
			do {
				System.out.println("Please enter your move");
				s = scan.nextLine();
				move_now = parse_input(white_turn, curr_player, s);
				if (move_now != null) {
					legal_move = move_now.first.move(move_now.second.first, move_now.second.second);
				}

			} while (!legal_move);

			other_player.update_in_check();
			if(other_player.is_in_check()) {
				System.out.println("Check!");
			}
			white_turn = !white_turn;
		}
		scan.close();
	}

	private Pair<Soldier, Pair<Integer, Integer>> parse_input(Boolean white_turn, Player curr_player, String input) {
		// ADD SPECIAL MOVEMENT CASES
		if (input.length() < 2 || input.length() > 6) {
			Board.print_move_err();
			return null;
		}

		char promotion_letter = '\0';
		char last_letter = input.charAt(input.length()-1);
		if(Board.class_letters.contains(last_letter)){
			if(last_letter == 'K' || last_letter == '='){
				Board.print_move_err();
				return null;
			}

			promotion_letter = last_letter;
			input = input.substring(0,input.length()-1);
		}

		input = clean_input(input, Board.class_letters);// cleaning non-interesting added characters
		Pair<Soldier, Pair<Integer, Integer>> res = new Pair<>(null, new Pair<>(1,2));//random initialization
		if (!Board.board_numbers.contains(input.charAt(input.length() - 1))) {
			Board.print_move_err();
			return null;
		}

		Pair<Class<? extends Soldier>, String> stage_1 = get_soldier_type(input);//getting the soldier type

		Pair<Pair<Integer, Integer>, String> stage_2 = get_dest(stage_1.second);//getting the destination

		if (stage_2 == null) {
			Board.print_move_err();
			return null;
		}
		
		Pair<Pair<Character,Character>,String> stage_3  = get_source(stage_2.second);//getting the source (if exists)
		
		if (stage_3 == null) {
			Board.print_move_err();
			return null;
		}

		// finding the Soldier
		Soldier s = find_soldier(curr_player, stage_1.first, stage_3.first, stage_2.first);

		if (s == null) {
			Board.print_move_err();
			return null;
		}

		//dealing with pawn promotion if needed
		if(s.getClass() == Pawn.class && promotion_letter != '\0'){
			((Pawn)s).set_promotion_letter(promotion_letter);
		}

		res.first = s;
		res.second = stage_2.first;
		return res;

	}

	private static Pair<Integer, Integer> translate_location(char letter, char number) {
		int row = init_row(number);
		int column = init_col(letter);
		return new Pair<Integer, Integer>(row, column);
	}

	private static int init_row(char c) {
		return Character.getNumericValue(c) - 1;
	}

	private static int init_col(char c) {
		return c - 97;// 97 is the ASCII value of 'a'
	}

	private static Soldier find_soldier(Player curr_player, Class<? extends Soldier> c, Pair<Character,Character> src, Pair<Integer, Integer> dest) {
		Set<Soldier> s = curr_player.get(c);
		HashSet<Soldier> potential = new HashSet<>();
		
		for (Soldier sol : s) {
			if (sol.is_legal(dest.first, dest.second)) {
				potential.add(sol);
			}
		}

		HashSet<Soldier> found = new HashSet<>();
		
		if(src.first == '\0' && src.second == '\0') {
			found = potential;
		}
		
		else {
			for (Soldier sol : potential) {

				if (src.first == '\0' || src.second == '\0') {
					if (src.first != '\0') {// a letter is given
						if (sol.get_col() == init_col(src.first)) {
							found.add(sol);
						}
					} else {// a number is given
						if (sol.get_row() == init_row(src.second)) {
							found.add(sol);
						}
					}
				}

				else {// middle.length() == 2
					Pair<Integer, Integer> p = translate_location(src.first, src.second);
					if (p.first == sol.get_row() && p.second == sol.get_col()) {
						found.add(sol);
					}
				}

			}
		}

		if (found.size() == 1) {
			return found.iterator().next();
		}
		
		return null;
	}

	private String clean_input(String input, HashSet<Character> class_letters) {
		char c1 = input.charAt(input.length() - 1);
		char c2 = input.charAt(input.length() - 2);
		String res = input;
		if (class_letters.contains(c1)) {// removing promotion/draw letter if exists
			res = input.substring(0, input.length() - 1);
		}

		else if (c1 == '+' && c2 == '+') {// removing check letters if exist
			res = input.substring(0, input.length() - 2);
		}

		res = res.replace("[\\:x]", "");

		return res;
	}

	private Pair<Class<? extends Soldier>, String> get_soldier_type(String input) {
		Pair<Class<? extends Soldier>, String> res = new Pair<>(null, null);
		switch (input.charAt(0)) {

		case '♔':
		case '♚':
		case 'K':
			res.first = King.class;
			res.second = input.substring(1);
			break;
		case '♕':
		case '♛':
		case 'Q':
			res.first = Queen.class;
			res.second = input.substring(1);
			break;
		case '♖':
		case '♜':
		case 'R':
			res.first = Rook.class;
			res.second = input.substring(1);
			break;
		case '♗':
		case '♝':
		case 'B':
			res.first = Bishop.class;
			res.second = input.substring(1);
			break;
		case '♘':
		case '♞':
		case 'N':
		case 'S':
			res.first = Knight.class;
			res.second = input.substring(1);
			break;
		default:
			res.first = Pawn.class;// pawn or error
			res.second = input;
		}

		return res;
	}

	private Pair<Pair<Integer, Integer>, String> get_dest(String input) {
		char c1 = input.charAt(input.length() - 1);
		char c2 = input.charAt(input.length() - 2);
		Pair<Pair<Integer, Integer>, String> res = new Pair<>(new Pair<>(1,2), null);//random initialization

		if ((Board.board_letters.contains(c2) && Board.board_numbers.contains(c1))) {
			res.first = translate_location(c2, c1);
		}

		else {
			return null;
		}
		res.second = input.substring(0,input.length() - 2);
		return res;
	}

	private Pair<Pair<Character, Character>, String> get_source(String input) {
		Pair<Pair<Character, Character>, String> res = new Pair<>(new Pair<>('\0','\0'),"");//random initialization
		if(input.length() == 0) {
			return res;
		}
		char letter = input.charAt(0);
		if (input.length() == 1) {// a letter or a number
			if (Board.board_letters.contains(letter)) {
				res.first.first = letter;
				res.first.second = '\0';
				return res;
			}

			else if (Board.board_numbers.contains(letter)) {
				res.first.second = letter;
				res.first.first = '\0';
				return res;
			}

			else {// illegal input
				return null;
			}
		}

		else if (input.length() == 2) {// a letter and a number
			if (Board.board_letters.contains(letter) && Board.board_numbers.contains(input.charAt(1))) {
				res.first.first = letter;
				res.first.second = input.charAt(1);
				return res;
			}

			else {// illegal input
				return null;
			}
		}

		else {// illegal input
			return null;
		}
		

	}

}
