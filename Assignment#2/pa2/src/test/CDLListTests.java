import java.time.Duration;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import testrunner.annotation.Score;

public class CDLListTests {
    @Test
    @Score(1)
    public void testCreation() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            ICDLList list = new CDLList();
        });
    }

    @Test
    @Score(1)
    public void testBasic() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            ICDLList list = new CDLList();
            list.insert(1);
            assertThat(list.getHead().getValue(), is(1));
            assertThat(list.size(), is(1));
            list.delete();
        });
    }

    @Test
    @Score(1)
    public void testAdvanced() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            ICDLList list = new CDLList();
            list.insert(1);
            list.insert(2);
            list.insert(3);
            list.rotateBackward();
            assertThat(list.getHead().getValue(), is(3));
            list.delete();
            list.rotateForward();
            assertThat(list.getHead().getValue(), is(1));
            list.delete();
            list.delete();
            assertThrows(IllegalStateException.class, () -> list.delete());
        });
    }
}