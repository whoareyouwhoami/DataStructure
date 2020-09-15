import java.time.Duration;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import testrunner.annotation.Score;

import java.util.Arrays;

public class SetTests {
    @Test
    @Score(1)
    void testCreation() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            ISet s = new Set();
        });
    }

    @Test
    @Score(1)
    public void testInsertandDelete() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            ISet s = new Set();
            s.insert(1);
            s.insert(1);
            assertThat(s.show(), is(new int[] { 1 }));
            s.delete(1);
            assertThat(s.show(), is(new int[] {}));
        });
    }

    @Test
    @Score(1)
    public void testException() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            ISet s = new Set();
            assertThrows(IllegalStateException.class, () -> s.delete(0));
        });
    }

    @Test
    @Score(1)
    public void testUnion() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            ISet a = new Set();
            ISet b = new Set();
            a.insert(1);
            b.insert(2);
            a.union(b);
            int[] arr = a.show();
            assertThat(arr.length, is(2));
            Arrays.sort(arr);
            assertThat(arr, is(new int[] { 1, 2 }));
        });
    }

    @Test
    @Score(1)
    public void testIntersection() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            ISet a = new Set();
            ISet b = new Set();
            a.insert(1);
            a.insert(2);
            b.insert(2);
            a.intersection(b);
            assertThat(a.show(), is(new int[] { 2 }));
        });
    }

    @Test
    @Score(1)
    public void testSubtraction() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            ISet a = new Set();
            ISet b = new Set();
            a.insert(1);
            a.insert(2);
            b.insert(2);
            a.subtraction(b);
            assertThat(a.show(), is(new int[] { 1 }));
        });
    }
}