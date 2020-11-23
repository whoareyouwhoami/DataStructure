/*
* Name: Young Woo Ju
* Student ID #: 2014122074
*/

import java.lang.Math;
import java.util.Comparator;
/*
* Do NOT import additional packages/classes.
* If you (un)intentionally use packages/classes we did not provide,
* you will get 0.
*/

public class Tree<K> implements ITree<K> {
	private TreeNode<K> node = null;
	private Comparator<K> comp;
	private int treeSize = 0;

	private final int MAX_KEY = 3;
	private final int MAX_CHILDREN = 4;
	private final int INDEX_SPLIT = 2; 

	public Tree(Comparator<K> comp) {
		/*
        * Constructor.
        *
        * Note that we will check the number of compare calls;
        * if the count is too low or too high (depending on cases),
        * you will fail the case.
        */
        this.node = new TreeNode<>();
        this.comp = comp;
	}


	@Override
	public TreeNode<K> root()
		throws IllegalStateException {
		/*
        * Return the root node.
        * If there is no root, raise an IllegalStateException.
        */
        if(node == null)
			throw new IllegalStateException("NO ROOT!");

		while(node.getParent() != null) {
			node = node.getParent();
		}

        return node;
	}

	@Override
	public TreeNode<K> insert(K key) {
		/*
        * Insert the given key at the appropriate node
        * with correct position and return the node.
        * You need to handle the cases of 'overflow' and
        * perform a split operation.
        * Note that you do not need to consider the case of inserting
        * the key that is already in the tree. As stated in the
        * guideline, we will only insert the key that is not in the
        * tree.
        */

        // Get root
		TreeNode<K> treenode = getRoot();

		// Find insertion node
		int idx = 0;
		while(treenode.numChildren() > 0) {
			idx = getKeyPosition(treenode, key);
			treenode = treenode.getChild(idx);
		}

		// Find index to insert the key
		int keyPosition = getKeyPosition(treenode, key);

		// Insert key
		treenode.insertKey(keyPosition, key);

		// Insert and check overflow condition -> return node where key exist
        return overflowSplit(treenode, key);
	}

	@Override
	public void delete(K key) {
		/*
        * Find the node with a given key and delete the key.
        * You need to handle the cases of 'underflow' and
        * perform a fusion operation.
        * If there is no "key" in the tree, please ignore it.
        */

        // Exit is tree is empty or key doesn't exist
        if(isEmpty() || !search(key))
        	return;

		// Find node where key exist
		TreeNode<K> nodeKey = searchKeyNode(root(), key);

		// Get key index in the node
		int keyPosition = getKeyPosition(nodeKey, key);

		// When key is in internal node
		if(!isLeafNode(nodeKey)) {
			// Left node
			TreeNode<K> nodeLeft = nodeKey.getChild(keyPosition);
			// Right node
			TreeNode<K> nodeRight = nodeKey.getChild(keyPosition + 1);

			if(nodeLeft.numKeys() > 1) {
				nodeKey.removeKey(keyPosition);
				nodeKey.insertKey(keyPosition, nodeLeft.getKey(nodeLeft.numKeys() - 1));
				nodeLeft.removeKey(nodeLeft.numKeys() - 1);
			} else if(nodeRight.numKeys() > 1) {
				nodeKey.removeKey(keyPosition);
				nodeKey.insertKey(keyPosition, nodeRight.getKey(0));
				nodeRight.removeKey(0);
			} else if(nodeLeft.numKeys() == 1 && nodeRight.numKeys() == 1) {
				nodeLeft.insertKey(1, nodeRight.getKey(0));

				for(int i = 0; i < nodeRight.numChildren(); i++) {
					nodeLeft.insertChild(nodeLeft.numChildren(), nodeRight.getChild(i));
				}

				nodeKey.removeKey(keyPosition);
				nodeKey.removeChild(keyPosition + 1);
				
				if(nodeKey == node && nodeKey.numKeys() == 0) {
					nodeLeft.setParent(null);
					node = nodeLeft;
				}
			}
		} else {
			// When key is in the left node check from top-bottom if:
			// Parent has 1 key and 2 children where each child has 1 key
			// If that is the case, merge these nodes together
			boolean foundKey = false;
			TreeNode<K> nodeCheck = root();
	        while(!foundKey) {
	        	int position = 0;
	        	if(nodeCheck.numKeys() == 1 && nodeCheck.numChildren() == 2 && nodeCheck.getChild(0).numKeys() == 1 && nodeCheck.getChild(1).numKeys() == 1) {
	        		TreeNode<K> nodeLeft_temp = nodeCheck.getChild(0);
	        		TreeNode<K> nodeRight_temp = nodeCheck.getChild(1);

	        		nodeLeft_temp.insertKey(nodeLeft_temp.numKeys(), nodeCheck.getKey(0));
	        		nodeLeft_temp.insertKey(nodeLeft_temp.numKeys(), nodeRight_temp.getKey(0));

	        		for(int i = 0; i < nodeRight_temp.numChildren(); i++) {
	        			nodeLeft_temp.insertChild(nodeLeft_temp.numChildren(), nodeRight_temp.getChild(i));
	        		}

	        		nodeLeft_temp.setParent(null);
	        		nodeCheck = nodeLeft_temp;
	        		node = nodeCheck;
	        	}

	        	for(int i = 0; i < nodeCheck.numKeys(); i++) {
	        		int difference = comp.compare(key, nodeCheck.getKey(i));
	        		if(difference == 0) {
	        			foundKey = true;
	        			break;
	        		}
	        		if(difference > 0)
						position = i + 1;
	        	}

	        	nodeKey = nodeCheck;

	        	if(foundKey)
	        		break;
	        	
	        	nodeCheck = nodeCheck.getChild(position);
	        	nodeCheck.setParent(nodeKey);
	        }
	        
	        // New key position
	        keyPosition = getKeyPosition(nodeKey, key);

	        if(nodeKey.numKeys() > 1 || nodeKey == node) {
				nodeKey.removeKey(keyPosition);
				treeSize--;
				isRootEmpty();
				return;
			}

			// Get key node position among the child nodes
			int nodePosition = getNodePosition(nodeKey);

			// Parent node
			TreeNode<K> nodeParent = nodeKey.getParent();
			int swapKey_parent = nodePosition - 1 < 0 ? 0 : nodePosition - 1;

			// Sibling node
			TreeNode<K> nodeSibling = getSibling(nodeKey, getSiblingPosition(nodePosition));
			int swapKey_sibling = (swapKey_parent >= nodePosition) ? 0 : nodeSibling.numKeys() - 1;

			if(nodeKey.numKeys() == 1 && nodeSibling.numKeys() > 1) {
				nodeKey.removeKey(0);
				nodeKey.insertKey(0, nodeParent.getKey(swapKey_parent));

				nodeParent.removeKey(swapKey_parent);
				nodeParent.insertKey(swapKey_parent, nodeSibling.getKey(swapKey_sibling));

				nodeSibling.removeKey(swapKey_sibling);
			} else if(nodeKey.numKeys() == 1 && nodeSibling.numKeys() == 1) {
				nodeKey.removeKey(0);
				nodeKey.insertKey(0, nodeParent.getKey(swapKey_parent));
				nodeParent.removeKey(swapKey_parent);

				int mergePosition = nodePosition > getSiblingPosition(nodePosition) ? 0 : nodeKey.numKeys();
				nodeKey.insertKey(mergePosition, nodeSibling.getKey(0));
				nodeParent.removeChild(getSiblingPosition(nodePosition));
			}
		}

		// if root is empty but has a child, set the child as a root
		if(node.numKeys() == 0 && node.numChildren() > 0) {
			node = node.getChild(0);
			node.setParent(null);
		}

		// Decrease tree size
		treeSize--;

		// Check if root is empty
		isRootEmpty();
	}

	@Override
	public boolean search(K key) {
		/*
		* Find a node with a given key and return true if you can find it.
		* Return false if you cannot.
		*/
		return searchKey(root(), key);
	}

	@Override
	public int size() {
		/*
		* Return the number of keys in the tree.
		*/
		return treeSize;
	}
	
	@Override
	public boolean isEmpty() {
		/*
		* Return whether the tree is empty or not.
		*/
		return treeSize == 0;
	}


	private boolean searchKey(TreeNode<K> treenode, K key) {
		int position = 0;

		for(int i = 0; i < treenode.numKeys(); i++) {
			int difference = comp.compare(key, treenode.getKey(i));

			if(difference == 0)
				return true;

			if(difference > 0)
				position = i + 1;
		}

		if(treenode.numChildren() == 0)
			return false;

		return searchKey(treenode.getChild(position), key);
	}

	private TreeNode<K> searchKeyNode(TreeNode<K> treenode, K key) {
		if(treenode.numChildren() == 0) 
			return treenode;

		int position = 0;
		for(int i = 0; i < treenode.numKeys(); i++) {
			int difference = comp.compare(key, treenode.getKey(i));

			if(difference == 0)
				return treenode;

			if(difference > 0)
				position = i + 1;
		}

		return searchKeyNode(treenode.getChild(position), key);
	}

	private TreeNode<K> overflowSplit(TreeNode<K> treenode, K key) {
		// Check if it overflows
		while(treenode.numKeys() > 3) {
			// Splitting key
			K keySplit = treenode.getKey(INDEX_SPLIT);

			// Creating right node
			TreeNode<K> nodeRight = new TreeNode<>();

			// Retrieving parent node
			TreeNode<K> nodeUp = treenode.getParent();

			// If parent node doesn't exist, create new node
			if(nodeUp == null) {
				nodeUp = new TreeNode<>();
				nodeUp.insertChild(0, treenode);
			}

			// Inserting splitting key to parent node
			int parentIdx = getKeyPosition(nodeUp, keySplit);
			nodeUp.insertKey(parentIdx, keySplit);


			// Inserting right side of splitKey to right node
			for(int i = INDEX_SPLIT + 1; i < treenode.numKeys(); i++) {
				nodeRight.insertKey(nodeRight.numKeys(), treenode.getKey(i));
			}

			// Removing keys beyong INDEX_SPLIT
			for(int i = treenode.numKeys() - 1; i >= INDEX_SPLIT; i--) {
				treenode.removeKey(i);
			}

			// Setting left and right node's parent
			treenode.setParent(nodeUp);
			nodeRight.setParent(nodeUp);

			// Insert right node as a child of parent node
			int idxRight = getKeyPosition(nodeUp, nodeRight.getKey(nodeRight.numKeys() - 1));
			nodeUp.insertChild(idxRight, nodeRight);

			// If the current (left node) overflows, adjust children
			if(treenode.numChildren() > treenode.numKeys() + 1) {
				for(int i = treenode.numChildren() - 1; i > treenode.numKeys(); i--) {
					nodeRight.insertChild(0, treenode.getChild(i));
					nodeRight.getChild(0).setParent(nodeRight);
					treenode.removeChild(i);
				}
			}

			// Move treenode to parent node to check for overflow
			treenode = treenode.getParent();
		}

		// Increase tree size
		treeSize++;

		// Return a node where a key exist
		return searchKeyNode(treenode, key);
	}

	private boolean isBalanced(TreeNode<K> treenode) {
		if(!isParentNode(treenode) || (treenode.numKeys() != 0 && treenode.numChildren() == treenode.numKeys() + 1))
			return true;
		return false;
	}

	private boolean isParentNode(TreeNode<K> treenode) {
		if(treenode.numChildren() > 0)
			return true;
		return false;
	}

	private boolean isLeafNode(TreeNode<K> treenode) {
		if(treenode.numChildren() == 0)
			return true;
		return false;
	}

	private boolean isTwoNode(TreeNode<K> treenode) {
		if(treenode.numKeys() == 1 && treenode.numChildren() == 2 && treenode.getChild(0).numKeys() == 1 && treenode.getChild(1).numKeys() == 1)
			return true;
		return false;
	}

	private void isRootEmpty() {
		if(node.numKeys() == 0)
			node = null;
	}

	private int getKeyPosition(TreeNode<K> treenode, K key) {
		int position = 0;
		for(int i = 0; i < treenode.numKeys(); i++) {
			int difference = comp.compare(key, treenode.getKey(i));
			if(difference == 0)
				return i;
			if(difference > 0)
				position = i + 1;
		}
		return position;
	}

	private int getNodePosition(TreeNode<K> treenode) {
		for(int i = 0; i < treenode.getParent().numChildren(); i++) {
			if(treenode.getParent().getChild(i) == treenode)
				return i;
		}
		return 0;
	}

	private int getSiblingPosition(int nodePosition) {
		nodePosition--;
		if(nodePosition < 0)
			nodePosition *= -1;
		return nodePosition;
	}

	private TreeNode<K> getSibling(TreeNode<K> treenode, int nodePosition) {
		return treenode.getParent().getChild(nodePosition);
	}

	private TreeNode<K> getRoot() {
		if(node == null)
			node = new TreeNode<>();

		while(node.getParent() != null) {
			node = node.getParent();
		}

        return node;
	}

}