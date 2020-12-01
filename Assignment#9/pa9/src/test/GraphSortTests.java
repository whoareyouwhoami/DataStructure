import java.time.Duration;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import testrunner.annotation.Score;

public class GraphSortTests {
    @Test
    @Score(1)
    void testCreation() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IGraphSort G = new GraphSort("./src/test/graph1.txt");
        });
    }

    @Test
    @Score(1)
    void testcycle() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IGraphSort G1 = new GraphSort("./src/test/graph3.txt");
            assertThat(G1.cycle(), is(true));
            IGraphSort G2 = new GraphSort("./src/test/graph4.txt");
            assertThat(G2.cycle(), is(true));
        });
    }

    @Test
    @Score(1)
    void testOrder() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            //can be any valid order
            IGraphSort G = new GraphSort("./src/test/graph2.txt");
            assertThat(G.topologicalOrder(), is(new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }));
        });
    }
}
