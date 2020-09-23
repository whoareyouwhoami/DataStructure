
public class Main {
	public static void main(String[] args) {
		TourSolver solver = new TourSolver();
		Board board = new Board(6,6);

		int[] asc = solver.solve(board);

		System.out.println("Hello, world\n");
	}
}