import java.time.Duration;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import testrunner.annotation.Score;

public class TourSolverTests {
    @Test
    @Score(1)
    public void testCreation() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            ITourSolver solver = new TourSolver();
        });
    }

    @Test
    @Score(1)
    public void testWorking() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            ITourSolver solver = new TourSolver();
            Board board = new Board(1, 1);
            int[] ans = solver.solve(board);
            assertThat(ans, is(new int[] { 0 }));
        });
    }
}