import java.time.Duration;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import testrunner.annotation.Score;

import java.lang.Math;
import java.util.Comparator;
import java.util.List;

class TreeTests {
    @Test
    @Score(1)
    void testBasic() {
        assertTimeoutPreemptively(
            Duration.ofSeconds(1),
            () -> {
                Tree<Integer> tree = new Tree<>(
                    Comparator.<Integer>naturalOrder());

                assertThat(tree.isEmpty(), is(true));

                TreeNode<Integer> n1 = tree.insert(1);
                TreeNode<Integer> n2 = tree.insert(2);
                TreeNode<Integer> n3 = tree.insert(3);

                assertThat(tree.size(), is(3));
                assertThat(tree.root(), is(sameInstance(n1)));
                assertThat(tree.search(3), is(true));

                TreeNode<Integer> n4 = tree.insert(4);

                assertThat(tree.root().getChild(0).getKey(0), is(1));
                assertThat(tree.size(), is(4));

                tree.delete(4);

                assertThat(tree.search(4), is(false));
                assertThat(tree.size(), is(3));
                assertThat(tree.isEmpty(), is(false));

                tree.delete(1);
                tree.delete(2);
                tree.delete(3);

                assertThat(tree.isEmpty(), is(true));

        });
    }
}
