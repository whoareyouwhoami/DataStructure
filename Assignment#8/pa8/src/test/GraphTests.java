import java.time.Duration;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import testrunner.annotation.Score;

public class GraphTests {
    @Test
    @Score(1)
    void testCreation() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IGraph G = new Graph("./src/test/graph1.txt");
        });
    }

    @Test
    @Score(1)
    void testNodeInsertDelete() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IGraph G = new Graph("./src/test/graph1.txt");
            G.insertVertex();
            G.insertVertex();
            G.insertVertex();
            G.insertVertex();
            G.insertVertex();
            assertThat(G.size(), is(6));
            G.deleteVertex(3);
            G.deleteVertex(3);
            G.deleteVertex(3);
            assertThat(G.size(), is(3));
        });
    }

    @Test
    @Score(1)
    void testConstruction() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IGraph G = new Graph("./src/test/graph2.txt");
            assertThat(G.matrix(),
                    is(new int[][] { { 0, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
                            { 0, 0, 0, 1, 0, 0, 0, 0, 0, 1 }, { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
                            { 0, 0, 0, 0, 0, 1, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },
                            { 0, 0, 0, 0, 0, 0, 0, 1, 0, 1 }, { 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 },
                            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } }));
        });
    }

    @Test
    @Score(1)
    void testEdgeInsertDelete() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IGraph G = new Graph("./src/test/graph2.txt");
            G.insertEdge(9, 0);
            G.insertEdge(9, 2);
            G.deleteEdge(0, 9);
            G.deleteEdge(1, 9);
            G.insertEdge(9, 4);
            G.insertEdge(9, 6);
            G.insertEdge(9, 8);
            assertThat(G.matrix(),
                    is(new int[][] { { 0, 1, 1, 1, 1, 1, 1, 1, 1, 0 }, { 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
                            { 0, 0, 0, 1, 0, 0, 0, 0, 0, 1 }, { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
                            { 0, 0, 0, 0, 0, 1, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },
                            { 0, 0, 0, 0, 0, 0, 0, 1, 0, 1 }, { 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 },
                            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 } }));
        });
    }
}
