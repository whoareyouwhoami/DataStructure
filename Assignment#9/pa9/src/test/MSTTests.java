import java.time.Duration;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import testrunner.annotation.Score;

public class MSTTests {
    @Test
    @Score(1)
    void testCreation() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IMST G = new MST("./src/test/wgraph1.txt");
        });
    }

    @Test
    @Score(1)
    void testPath() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IMST G = new MST("./src/test/wgraph1.txt");
            assertThat(G.shortestPath(2, 3), is(new int[] { 2, 1, 0, 4, 3 }));
        });
    }

    @Test
    @Score(1)
    void testMST() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IMST G = new MST("./src/test/wgraph1.txt");
            assertThat(G.findMST(), is(4));
        });
    }
}
