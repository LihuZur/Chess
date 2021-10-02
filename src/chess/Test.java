package chess;


public class Test {
	public static void main(String[] args) {
		String[] soldiers = {"BQ20","WK01","BK31","BR32","BR22","WB13"};
		Board b = Board.build_board(soldiers,false);
		Game game = new Game();
		game.set_board(b);
		game.play(false);

	}
}
