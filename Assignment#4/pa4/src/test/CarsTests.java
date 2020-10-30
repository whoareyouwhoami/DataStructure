import java.time.Duration;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import testrunner.annotation.Score;

import java.util.Arrays;

public class CarsTests {
    @Test
    @Score(1)
    public void testCreation() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            ICars cars = new Cars(0);
        });
    }

    @Test
    @Score(1)
    public void testCars11() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            ICars cars = new Cars(1);
            cars.carDistance(121);
            assertThat(cars.getCandidates(), is(new int[] { 121 }));
        });
    }

    @Test
    @Score(1)
    public void testCars1() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            ICars cars = new Cars(1);
            cars.carDistance(121);
            cars.carDistance(162);
            cars.carDistance(562);
            cars.carDistance(978);
            cars.carDistance(486);
            assertThat(cars.getCandidates(), is(new int[] { 978 }));
        });
    }

    @Test
    @Score(1)
    public void testHeapUse() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            Cars cars = new Cars(5);
            for (int i = 1; i < 15; i++) {
                cars.carDistance(i);
            }
            Heap heap = cars.heap;
            assertThat(heap.size(), is(5));
        });
    }

    @Test
    @Score(1)
    public void testCars10() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            ICars cars = new Cars(10);
            for (int i = 1; i <= 100; i++) {
                cars.carDistance(i);
            }
            int[] arr = cars.getCandidates();
            Arrays.sort(arr);
            assertThat(arr, is(new int[] { 91, 92, 93, 94, 95, 96, 97, 98, 99, 100 }));
        });
    }

    @Test
    @Score(1)
    public void testOnline() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            ICars cars = new Cars(10);
            for (int i = 1; i <= 100; i += 2) {
                cars.carDistance(i);
            }
            int[] arr = cars.getCandidates();
            Arrays.sort(arr);
            assertThat(arr, is(new int[] { 81, 83, 85, 87, 89, 91, 93, 95, 97, 99 }));
            for (int i = 2; i <= 100; i += 2) {
                cars.carDistance(i);
            }
            arr = cars.getCandidates();
            Arrays.sort(arr);
            assertThat(arr, is(new int[] { 91, 92, 93, 94, 95, 96, 97, 98, 99, 100 }));
        });
    }
}
