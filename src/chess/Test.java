package chess;


public class Test {
	public static void main(String[] args) {
		Pair<Soldier, Pair<Integer, Integer>> p = new Pair<Soldier, Pair<Integer, Integer>>(null,null);
		if(p == null){
			System.out.println(true);
		}

		else{
			System.out.println(false);
		}
}
}
