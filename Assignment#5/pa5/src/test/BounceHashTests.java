import java.time.Duration;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import testrunner.annotation.Score;

import java.util.Arrays;

public class BounceHashTests {
    private class HashFn1 implements IBounceHashFunction<Integer> {
        int offset = 0;

        public int hash1(Integer i, int length) {
            return ((i + offset) % length);
        }

        public int hash2(Integer i, int length) {
            return (((i + offset) / length) % length);
        }

        public void changeHashFn(int tablesize) {
            offset++;
        }
    }

    private class ResizeFn1 implements IResizeFunction {
        public boolean checkResize(int tablesize, int numItems) {
            if (numItems > tablesize * 0.8) {
                return true;
            }
            return false;
        }

        public int extendTable(int tablesize) {
            return tablesize * 2;
        }
    }

    @Test
    @Score(1)
    public void testCreation() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IHash<Integer> h = new BounceHash<Integer>(new HashFn1(), new ResizeFn1(), 4);
            assertThat(h.tablesize(), is(4));
        });
    }

    @Test
    @Score(1)
    public void testInsertDelete() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IHash<Integer> h = new BounceHash<Integer>(new HashFn1(), new ResizeFn1(), 4);
            h.put(0);
            h.put(1);
            h.put(3);
            h.remove(1);
            assertThat(h.exists(1), is(false));
            assertThat(h.size(), is(2));
            assertThat(h.show(), is(Arrays.asList(new Integer[] { 0, null, null, 3 })));
        });
    }

    @Test
    @Score(1)
    public void testHashingCollision() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IHash<Integer> h = new BounceHash<Integer>(new HashFn1(), new ResizeFn1(), 7);
            h.put(10); //h_1(10)=3, h_2(10)=1
            h.put(3); //h_1(3)=3, h_2(3)=0
            assertThat(h.show(), is(Arrays.asList(new Integer[] { null, 10, null, 3, null, null, null })));

            h.put(11); //h_1(11)=4, h_2(11)=1
            h.put(32); //h_1(32)=4, h_2(32)=4
            assertThat(h.exists(4), is(false));
            assertThat(h.exists(10), is(true));
            assertThat(h.show(), is(Arrays.asList(new Integer[] { 3, 11, null, 10, 32, null, null })));
        });
    }

    @Test
    @Score(1)
    public void testRehashing1() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IHash<Integer> h = new BounceHash<Integer>(new HashFn1(), new ResizeFn1(), 4);
            h.put(13); //h_1(13)=1, h_2(13)=3
            h.put(77); //h_1(77)=1, h_2(77)=3
            h.put(4); //h_1(4)=0, h_2(4)=1
            assertThat(h.size(), is(3));
            assertThat(h.tablesize(), is(4));
            assertThat(h.show(), is(Arrays.asList(new Integer[] { 4, 77, null, 13 })));

            h.put(10); //h_1(10)=2, h_2(10)=1
            assertThat(h.tablesize(), is(8));
            assertThat(h.show(), is(Arrays.asList(new Integer[] { null, 77, 10, null, 4, 13, null, null })));
        });
    }

    @Test
    @Score(1)
    public void testRehashing2() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IHash<Integer> h = new BounceHash<Integer>(new HashFn1(), new ResizeFn1(), 4);
            h.put(13); //h_1(13)=1, h_2(13)=3
            h.put(15); //h_1(15)=3,h_2(15)=3
            h.put(11); //h_1(11)=3, h_2(11)=2
            h.put(6); // h_1(6)=2, h_2(6)=1
            // h = [15,6,13,11] but need to resize so,
            assertThat(h.show(), is(Arrays.asList(new Integer[] { 15, null, null, null, 11, null, 13, 6 })));
        });
    }
}
