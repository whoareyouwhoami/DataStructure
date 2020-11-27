
/*
* Name: Young Woo Ju
* Student ID #: 2014122074
*/
import java.lang.Math;
/*
* Do NOT import additional packages/classes.
* If you (un)intentionally use packages/classes we did not provide,
* you will get 0.
*/

public class Autocomplete implements IAutocomplete {

	private Trie trie;
	private float sum;
	private int trieSize;

	public Autocomplete() {
		/*
		* Constructor.
		*
		* Note that we will check the number of compare calls;
		* if the count is too low or too high (depending on cases),
		* you will fail the case.
		*/

		this.trie =  new Trie();
		this.sum = 0;
		this.trieSize = 0;
	}

	private void compressWord(Node<String> node, String str, int idx) {
		// Compressing
		if(node.numChildren() == 1) {
			String compressed = str + node.getKey(0);

			// Replace with compressed key
			node.removeKey(0);
			node.insertKey(0, compressed);

			// Get child node
			Node<String> child = node.getChild(0);
			Node<String> parent = node.getParent();
			
			// Set parent to current node's parent
			child.setParent(parent);

			// Remove current node from parent node
			// -> set parent's child to current child 
			parent.removeChild(idx);
			parent.insertChild(idx, child);

			// Remove current key from parent node
			// -> set parent's child key to current child key
			parent.removeKey(idx);
			parent.insertKey(idx, compressed);

			// Change the node to it's parent
			node = parent;
		}

		// If node has more than 1 children loop again
		for(int i = 0; i < node.numChildren(); i++) {
			compressWord(node.getChild(i), node.getKey(i), i);
		}
	}

	@Override
	public void construct(String[] s) {
		// Construct standard trie
		for(int i = 0; i < s.length; i++) {
			trie.insert(s[i]);
		}

		// Construct compressed trie
		Node<String> current = trie.root();

		for(int i = 0; i < current.numChildren(); i++) {
			compressWord(current.getChild(i), current.getKey(i), i);
		}

		// Set trie size
		trieSize = s.length;
	}

	@Override
	public String autocompletedWord(String s) {
		Node<String> current = trie.root();
		String result = "";

		for(int i = 0; i < s.length(); i++) {
			String char_input = s.substring(i, i + 1);

			for(int k = 0; k < current.numKeys(); k++) {
				String key = current.getKey(k);
				String char_key = key.substring(0, 1);

				if(char_input.equals(char_key)) {
					result += key;
					current = current.getChild(k);
				} 
			}
		}

		return result;
	}

	private void getAverage(Node<String> node, float wordLength) {
		if(node.numChildren() == 0)
			sum += wordLength;

		for(int i = 0; i < node.numChildren(); i++) {
			getAverage(node.getChild(i), wordLength + 1);
		}
	}

	@Override
	public float average() {
		Node<String> current = trie.root();

		sum = 0;

		for(int i = 0; i < current.numChildren(); i++) {
			getAverage(current.getChild(i), 1);
		}

		return (float) Math.round(sum / trieSize * 100) / 100;
	}
}