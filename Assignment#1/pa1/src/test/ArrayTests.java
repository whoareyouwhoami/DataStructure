import java.time.Duration;

import org.junit.jupiter.api.*;

import jdk.jfr.Timestamp;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import testrunner.annotation.Score;

public class ArrayTests {
	@Test
	@Score(1)
	void testCreation() {
		assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
			IArray a = new Array();
		});
	}

	@Test
	@Score(1)
	public void testInsert() {
		assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
			IArray a = new Array();
			assertThat(a.isEmpty(), is(true));
			a.insert(7);
			a.insert(1);
			a.insert(4);
			a.insert(8);
			a.insert(2);
			a.insert(5);
			assertThat(a.size(), is(6));
			assertThat(a.atIndex(4), is(2));
			assertThat(a.atIndex(2), is(4));
		});
	}

	@Test
	@Score(1)
	public void testException() {
		assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
			IArray a = new Array();
			assertThrows(IllegalStateException.class, () -> a.delete(0));
		});
	}

	@Test
	@Score(1)
	public void testSort() {
		assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
			IArray a = new Array();
			a.insert(7);
			a.insert(1);
			a.insert(4);
			a.insert(8);
			a.sort();
			assertThat(a.atIndex(1), is(4));
			assertThat(a.atIndex(2), is(7));
			assertThat(a.isSorted(), is(true));
		});
	}
}