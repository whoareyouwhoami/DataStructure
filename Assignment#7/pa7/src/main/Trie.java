/*
* Name: Young Woo Ju
* Student ID #: 2014122074
*/

/*
* Do NOT import additional packages/classes.
* If you (un)intentionally use packages/classes we did not provide,
* you will get 0.
*/

public class Trie implements ITrie {

	private Node<String> root;
	private int trieSize;

	public Trie() {
		/*
		* Constructor.
		*
		* Note that we will check the number of compare calls;
		* if the count is too low or too high (depending on cases),
		* you will fail the case.
		*/

		this.root = new Node();
		this.trieSize = 0;

	}

	@Override
	public void insert(String s) {
		int idx;
		int pointer = 0;
		Node<String> current = this.root;

		while(pointer < s.length()) {
			String str = s.substring(pointer, pointer + 1);

			for(idx = 0; idx < current.numKeys(); idx++) {
				// If str is smaller or equal than current.getKey(idx) -> break
				if(str.compareTo(current.getKey(idx)) <= 0) {
					break;
				} 
			}

			if(current.numChildren() == 0 || idx == current.numChildren() || !str.equals(current.getKey(idx))) {
				// Insert a key
				current.insertKey(idx, str);
				
				// If it is not a last character in the string, then create a child node
				if(pointer < s.length()) {
					current.insertChild(idx, new Node<String>()); // Insert child in current node
					current.getChild(idx).setParent(current); // Visit the child node to set parent node
				}
			} 

			// Move to next node
			current = current.getChild(idx);

			pointer++;
		}

		// Set last node as last character
		current.setLastChar(true);

		// Increase number of strings inserted
		trieSize++;
	}

	@Override
	public boolean exists(String s) {
		Node<String> current = root();

		for(int i = 0; i < s.length(); i++) {
			String str = s.substring(i, i + 1);

			for(int child = 0; child < current.numChildren(); child++) {
				if(str.compareTo(current.getKey(child)) == 0 && !current.getChild(child).getLastChar()) {
					current = current.getChild(child);
					break;
				} else if(str.compareTo(current.getKey(child)) == 0 && current.getChild(child).getLastChar() && i == s.length() - 1) {
					return true;
				}
			}
		}
		return false;
	}

	private void addDict(String[] dict, Node<String> node, String s) {
		if(node.getLastChar()) {
			for(int d = 0; d < dict.length; d++) {
				if(dict[d] == null) {
					dict[d] = s;
					break;
				}
			}
		}

		for(int i = 0; i < node.numChildren(); i++) {
			addDict(dict, node.getChild(i), s + node.getKey(i));
		}
	}

	@Override
	public String[] dictionary() {
		Node<String> current = root();
		String[] dict = new String[trieSize];

		for(int i = 0; i < current.numChildren(); i++) {
			addDict(dict, current.getChild(i), current.getKey(i));
		}
		
		return dict;
	}

	@Override
	public Node root() {
		return this.root;
	}
}