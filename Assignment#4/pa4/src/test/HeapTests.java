import java.time.Duration;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import testrunner.annotation.Score;

public class HeapTests {
    @Test
    @Score(1)
    public void testCreation() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IHeap heap = new Heap(new Integer[] {});
            assertThat(heap.isEmpty(), is(true));
        });
    }

    @Test
    @Score(1)
    public void testInitialCreation() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IHeap heap = new Heap(new Integer[] { 4, 7, 3 });
            Node node = heap.getRoot();
            assertThat(node.getData(), is(7));
            if (node.getLChild().getData() == 4) {
                assertThat(node.getRChild().getData(), is(3));
            } else if (node.getLChild().getData() == 3){
                assertThat(node.getRChild().getData(), is(4));
            } else {
                assertThat(true, is(false));
            }
        });
    }

    @Test
    @Score(1)
    public void testInsertremoveMax() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IHeap heap = new Heap(new Integer[] { 4, 7, 3 });
            heap.insert(5);
            assertThat(heap.removeMax(), is(7));
            heap.insert(3);
            assertThat(heap.removeMax(), is(5));
            heap.insert(4);
            heap.insert(14);
            assertThat(heap.removeMax(), is(14));
            assertThat(heap.removeMax(), is(4));
            assertThat(heap.removeMax(), is(4));
            assertThat(heap.removeMax(), is(3));
            assertThat(heap.removeMax(), is(3));
        });
    }

    @Test
    @Score(1)
    public void testException() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IHeap heap = new Heap(new Integer[] {});
            assertThrows(IllegalStateException.class, () -> heap.removeMax());
            assertThrows(IllegalStateException.class, () -> heap.getRoot());
        });
    }

    @Test
    @Score(1)
    public void testHeap1() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IHeap heap = new Heap(new Integer[] { 5, 1, 2, 4, 6, 8, 3, 9, 10, 7 });
            assertThat(heap.removeMax(), is(10));
            assertThat(heap.removeMax(), is(9));
            assertThat(heap.removeMax(), is(8));
            assertThat(heap.removeMax(), is(7));
            assertThat(heap.removeMax(), is(6));
            assertThat(heap.removeMax(), is(5));
            assertThat(heap.max(), is(4));
            assertThat(heap.removeMax(), is(4));
            assertThat(heap.removeMax(), is(3));
            assertThat(heap.removeMax(), is(2));
            assertThat(heap.removeMax(), is(1));
        });
    }

    @Test
    @Score(1)
    public void testSmallthings() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IHeap heap = new Heap(new Integer[] { 5, 1, 2, 4, 6, 8, 3, 9, 10, 7 });
            assertThat(heap.size(), is(10));
            assertThat(heap.isEmpty(), is(false));
        });
    }
}
