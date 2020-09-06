import java.time.Duration;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import testrunner.annotation.Score;

public class SorterTests {
	@Test
	@Score(1)
	public void testBasic1() {
		assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
			ISorter s = new Sorter();
			int[] a = new int[] {};
			assertThat(s.ascending(a.clone()), is(new int[] {}));
			assertThat(s.descending(a.clone()), is(new int[] {}));
		});
	}

	@Test
	@Score(1)
	public void testBasic2() {
		assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
			ISorter s = new Sorter();
			int[] a = new int[] { 2, 1, 4, 3 };
			assertThat(s.ascending(a.clone()), is(new int[] { 1, 2, 3, 4 }));
			assertThat(s.descending(a.clone()), is(new int[] { 4, 3, 2, 1 }));
		});
	}
}