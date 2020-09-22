import java.time.Duration;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import testrunner.annotation.Score;

public class RRSchedulerTests {
    @Test
    @Score(1)
    public void testCreation() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IRRScheduler scheduler = new RRScheduler();
        });
    }

    @Test
    @Score(1)
    public void testBasic() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IRRScheduler scheduler = new RRScheduler();
            scheduler.insert(1);
            scheduler.insert(2);
            scheduler.done();
            assertThat(scheduler.currentJob(), is(2));
        });
    }

    @Test
    @Score(1)
    public void testAdvanced() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IRRScheduler scheduler = new RRScheduler();
            scheduler.insert(1);
            scheduler.insert(2);
            scheduler.changeDirection();
            scheduler.timeflow(1);
            assertThat(scheduler.currentJob(), is(2));
            scheduler.insert(3);
            scheduler.done();
            assertThat(scheduler.currentJob(), is(3));
            scheduler.done();
            scheduler.done();
            assertThrows(IllegalStateException.class, () -> scheduler.currentJob());
        });
    }
}
