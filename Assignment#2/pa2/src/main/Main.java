
public class Main {
	public static void main(String[] args) {
		TourSolver solver = new TourSolver();
		Board board = new Board(0, 0);

		int[] asc = solver.solve(board);

	}
}