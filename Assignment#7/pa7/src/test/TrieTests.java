import java.time.Duration;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import testrunner.annotation.Score;

import java.lang.Math;
import java.util.Comparator;
import java.util.List;

class TrieTests {
	@Test
	@Score(1)
	void testCreation() {
		assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
			Trie t = new Trie();
		});
	}

	@Test
	@Score(1)
	void testSimple1() {
		assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
			Trie t = new Trie();
			t.insert("winter");
			t.insert("window");
			t.insert("april");

			assertThat(t.exists("winter"), is(true));
			assertThat(t.exists("winner"), is(false));

			Node<String> root = t.root();
			assertThat(root.getKey(0), is("a"));
			assertThat(root.getKey(1), is("w"));

		});
	}

	@Test
	@Score(1)
	void testSimple2() {
		assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
			Trie t = new Trie();
			t.insert("flower");
			t.insert("fly");
			t.insert("tree");

			assertThat(t.exists("fly"), is(true));
			assertThat(t.exists("tree"), is(true));
			assertThat(t.exists("try"), is(false));

			Node<String> root = t.root();
			assertThat(root.getKey(0), is("f"));
			assertThat(root.getKey(1), is("t"));

			assertThat(t.dictionary(), is(new String[] { "flower", "fly", "tree" }));
		});
	}

	@Test
	@Score(1)
	void testAdvanced() {
		assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
			Trie t = new Trie();
			t.insert("winter");
			t.insert("winner");
			t.insert("win");
			t.insert("widow");
			t.insert("won");
			t.insert("wonder");
			t.insert("wool");

			// arrange with the lexicographic order
			assertThat(t.dictionary(),
					is(new String[] { "widow", "win", "winner", "winter", "won", "wonder", "wool" }));
			assertThat(t.exists("win"), is(true));
			assertThat(t.exists("wool"), is(true));
			assertThat(t.exists("won"), is(true));

			Node<String> root = t.root();
			assertThat(root.getKey(0), is("w"));
			assertThat(root.numChildren(), is(1));
			assertThat(root.getChild(0).numChildren(), is(2));
			assertThat(root.getChild(0).getKey(0), is("i"));
			assertThat(root.getChild(0).getKey(1), is("o"));

			assertThat(root.getChild(0).getChild(0).getChild(0).getChild(0).getLastChar(), is(false));

			// node for "widow"
			assertThat(root.getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getLastChar(), is(true));
			// node for "win"
			assertThat(root.getChild(0).getChild(0).getChild(1).getLastChar(), is(true));
			// node for "winner"
			assertThat(root.getChild(0).getChild(0).getChild(1).getChild(0).getChild(0).getChild(0).getLastChar(),is(true));
			assertThat(root.getChild(0).getChild(0).getChild(1).getChild(0).getChild(0).getChild(0).numChildren(),is(0));
			//node for "won"
			assertThat(root.getChild(0).getChild(1).getChild(0).getLastChar(),is(true));
			//node for "wonder"
			assertThat(root.getChild(0).getChild(1).getChild(0).getChild(0).getChild(0).getChild(0).numChildren(),is(0));
			//node for "wool"
			assertThat(root.getChild(0).getChild(1).getChild(1).getChild(0).getLastChar(),is(true));
			assertThat(root.getChild(0).getChild(1).getChild(1).getChild(0).numChildren(),is(0));
		});
	}
}
