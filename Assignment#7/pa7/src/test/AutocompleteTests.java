import java.time.Duration;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import testrunner.annotation.Score;

import java.lang.Math;
import java.util.Comparator;
import java.util.List;

class AutocompleteTests {
    @Test
    @Score(1)
    void testCreation() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IAutocomplete a = new Autocomplete();
        });
    }

    @Test
    @Score(1)
    void testSimple1() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IAutocomplete a = new Autocomplete();

            String[] dic = { "pear", "peel" };
            a.construct(dic);

            assertThat(a.autocompletedWord("p"), is("pe"));
            assertThat(a.autocompletedWord("pa"), is("pear"));

            assertThat(a.average(), is(2.00f));

        });
    }

    @Test
    @Score(1)
    void testSimple2() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IAutocomplete a = new Autocomplete();

            //autocomplete: 3("cso")  2("cm")    3("csi")      1("n")
            String[] dic = { "cosmos", "comet", "cosmicbeige", "night" };
            a.construct(dic);

            assertThat(a.autocompletedWord("c"), is("co"));
            assertThat(a.autocompletedWord("cs"), is("cosm"));
            assertThat(a.autocompletedWord("cso"), is("cosmos"));

            assertThat(a.autocompletedWord("cm"), is("comet"));
            assertThat(a.autocompletedWord("csi"), is("cosmicbeige"));
            assertThat(a.autocompletedWord("n"), is("night"));

            assertThat(a.average(), is(2.25f));

        });
    }

    @Test
    @Score(1)
    void testAdvanced1() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IAutocomplete a = new Autocomplete();

            //autocomplete:  1("p")   2("so") 3("spi")     1("c")       2("ta") 1("i")    2("sa") 2("ti")   3("spo")
            String[] dic = { "paddle", "son", "spiritual", "concerned", "thaw", "island", "sand", "things",
                    "spotless" };
            a.construct(dic);

            assertThat(a.autocompletedWord("i"), is("island"));
            assertThat(a.autocompletedWord("p"), is("paddle"));
            assertThat(a.autocompletedWord("c"), is("concerned"));

            assertThat(a.autocompletedWord("s"), is("s"));
            assertThat(a.autocompletedWord("sa"), is("sand"));
            assertThat(a.autocompletedWord("so"), is("son"));

            assertThat(a.autocompletedWord("sp"), is("sp"));
            assertThat(a.autocompletedWord("spi"), is("spiritual"));
            assertThat(a.autocompletedWord("spo"), is("spotless"));

            assertThat(a.autocompletedWord("t"), is("th"));
            assertThat(a.autocompletedWord("ti"), is("things"));
            assertThat(a.autocompletedWord("ta"), is("thaw"));
            
            assertThat(a.average(), is(1.89f));
        });
    }
}
