import java.time.Duration;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import testrunner.annotation.Score;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class SCCTests {
    @Test
    @Score(1)
    void testCreation() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            ISCC G = new SCC("./src/test/graph1.txt");
        });
    }

    @Test
    @Score(1)
    void testpath() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            ISCC G = new SCC("./src/test/graph2.txt");
            assertThat(G.path(1, 9), is(true));
        });
    }

    @Test
    @Score(1)
    void testconnectivity() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            ISCC G = new SCC("./src/test/graph2.txt");
            assertThat(G.connectivity(), is(false));
            ISCC G2 = new SCC("./src/test/graph3.txt");
            assertThat(G2.connectivity(), is(true));
        });
    }

    @Test
    @Score(1)
    void testSCC() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            // can be a different order
            ISCC G = new SCC("./src/test/graph4.txt");
            List list = new ArrayList();
            List sublist1 = new ArrayList();
            List sublist2 = new ArrayList();
            sublist1.add(0);
            sublist1.add(1);
            sublist2.add(2);
            sublist2.add(3);
            sublist2.add(4);
            list.add(sublist1);
            list.add(sublist2);
            for (int i = 0; i < list.size(); i++) {
                assertThat(G.findSCC(), is(list));
            }
        });
    }
}
