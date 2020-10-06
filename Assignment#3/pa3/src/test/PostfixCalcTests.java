import java.time.Duration;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import testrunner.annotation.Score;

public class PostfixCalcTests {
    @Test
    @Score(1)
    public void testCreation() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            ICalc calc = new PostfixCalc();
        });
    }

    @Test
    @Score(1)
    public void testEvaluate() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            ICalc calc = new PostfixCalc();
            assertThat(calc.evaluate("5 10 4 * +"), is(45));
            assertThat(calc.evaluate("1 2 -"), is(-1));
        });
    }
}