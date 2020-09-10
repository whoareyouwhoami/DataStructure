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

	@Test
	@Score(1)
	public void testBasic3() {
		assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
			ISorter s = new Sorter();
			int[] a = new int[] { 1, 3, 1, 5, 2, 10, 11, 13, 10, 11 };
			assertThat(s.ascending(a.clone()), is(new int[] { 1, 1, 2, 3, 5, 10, 10, 11, 11, 13 }));
			assertThat(s.descending(a.clone()), is(new int[] { 13, 11, 11, 10, 10, 5, 3, 2, 1, 1 }));
		});
	}

	@Test
	@Score(1)
	public void testBasic4() {
		assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
			ISorter s = new Sorter();
			int[] a = new int[] { 100, 0, 1, 3, 6, 3, 13, 18, 2, 1 };
			assertThat(s.ascending(a.clone()), is(new int[] { 0, 1, 1, 2, 3, 3, 6, 13, 18, 100 }));
			assertThat(s.descending(a.clone()), is(new int[] { 100, 18, 13, 6, 3, 3, 2, 1, 1, 0 }));
		});
	}

	@Test
	@Score(1)
	public void testBasic5() {
		assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
			ISorter s = new Sorter();
			int[] a = new int[] { 5, -1, -3, 2 , 0 , 1 };
			assertThat(s.ascending(a.clone()), is(new int[] { -3, -1, 0, 1, 2, 5 }));
			assertThat(s.descending(a.clone()), is(new int[] { 5, 2, 1, 0, -1, -3 }));
		});
	}
}