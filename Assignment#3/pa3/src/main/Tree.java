/*
* Name: Young Woo Ju
* Student ID#: 2014122074
*/

/*
* Do NOT use any external packages/classes.
* If you (un)intentionally use them we did not provide,
* you will get 0.
* Also do NOT use auto-import function on IDEs.
* If the import statements change, you will also get 0.
*/

import java.util.List;
import java.util.ArrayList;

public final class Tree<E> implements ITree<E> {
    // Initial arity value
    private int arity = 0;

    // Root node
    private TreeNode<E> rootNode;

    // For queue implementation
    private Node<TreeNode<E>> stackBottom;
    private Node<TreeNode<E>> stackBottomTemp;
    private Stack<TreeNode<E>> stack;

    // Height
    private int treeMaxHeight = 0;

    public Tree(int arity) {
        /*
        * Input:
        *  + arity: max number of node's children. always positive.
        */
        this.arity = arity;
    }

    /*
     * Finding height of a tree
     */
    private void findHeight(Stack<TreeNode<E>> treeStack, int height) {
        TreeNode<E> topNode = treeStack.top.getValue();

        if(height > treeMaxHeight) {
            treeMaxHeight = height;
        }

        if(topNode.numChildren() > 0) {
            height++;
            for(int i=0; i<topNode.numChildren(); i++) {
                treeStack.push(topNode.getChild(i));
                findHeight(treeStack, height);
                treeStack.pop();
                height--;
            }

        } 
    }

    /*
     * Pre-order traversal
     */
    private void treePreOrder(TreeNode<E> node, List<E> result) {
        // Add node value
        result.add(node.getValue());

        // Search node's children
        for(int i = 0; i < node.numChildren(); i++) {
            treePreOrder(node.getChild(i), result);
        }
    }

    /*
     * Post-order traversal
     */
    private void treePostOrder(TreeNode<E> node, List<E> result) {
        // Search node's children
        for(int i = 0; i < node.numChildren(); i++) {
            treePostOrder(node.getChild(i), result);
        }

        // Add node value
        result.add(node.getValue());
    }

    @Override
    public TreeNode<E> root() throws IllegalStateException {
        /*
        * Return the root node.
        * If there is no root, raise an IllegalStateException.
        */
        if(rootNode != null) {
            // If root exist, create a stack (for queue implementation later)
            stack = new Stack<>();
            stack.push(rootNode);
            stackBottomTemp = stack.top;
            stackBottom = stackBottomTemp;

            return rootNode;
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public int arity() {
        /*
        * Return the max number of children its node can have
        */
        return arity;
    }

    @Override
    public int size() {
        /*
        * Return the number of nodes in this tree.
        */
        if(rootNode == null) return 0;

        // Since root node exist
        int nodeNum = 1;
        
        // Add root to queue
        root();

        // While queue is not empty
        while(!stack.isEmpty()) {
            // queue.poll()
            TreeNode<E> popNode = stack.pop();
            stackBottom = stackBottom.getNext();

            // For each polled node, get children
            for(int j = 0; j < popNode.numChildren(); j++) {
                // Add children 
                nodeNum++;
                stack.push(popNode.getChild(j));
                Node<TreeNode<E>> tempNode = new Node(popNode.getChild(j));

                // Queue
                if(stack.size() == 1) {
                    stackBottomTemp = stack.top;
                    stackBottom = stackBottomTemp;
                } else {
                    stackBottomTemp.setNext(tempNode);
                    stackBottomTemp = stackBottomTemp.getNext();
                    stack.top = stackBottom;
                }
            }
        }
        
        return nodeNum;
    }

    @Override
    public boolean isEmpty() {
        /*
        * Return true if this tree is empty.
        */
        return rootNode == null;
    }

    @Override
    public int height() throws IllegalStateException {
        /*
        * Return height of this tree.
        * If there are no nodes in this tree,
        * raise an IllegalStateException.
        */
        if(rootNode == null) throw new IllegalStateException();
        
        // Initialize max height
        treeMaxHeight = 0;

        // Create stack and push root
        Stack<TreeNode<E>> treeStack = new Stack<>();
        treeStack.push(rootNode);
        
        // Find height
        findHeight(treeStack, 0);

        return treeMaxHeight;
    }

    @Override
    public TreeNode<E> add(E item) {
        /*
        * Insert the given node at the *end* of this tree and
        * return THE inserted NODE.
        * *End* means that the leftmost possible slot of
        * smallest depth of this tree.
        */
        TreeNode<E> insertNode = new TreeNode<>(arity, item);
        
        if(rootNode == null) {
            // Add as root node
            rootNode = insertNode;
        } else {
            // Initialize a queue with root 
            root();

            // While queue is not empty
            while(!stack.isEmpty()) {

                // queue.poll()
                TreeNode<E> popNode = stack.pop();
                stackBottom = stackBottom.getNext();

                // If the node's children is less than arity
                // INSERT item as polled node's children
                if(popNode.numChildren() < arity) {
                    insertNode.setParent(popNode);
                    popNode.insertChild(popNode.numChildren(), insertNode);

                    return insertNode;
                } else {
                    // When polled node's children reaches arity
                    // Add its children to the queue
                    for(int j = 0; j < popNode.numChildren(); j++) {
                        stack.push(popNode.getChild(j));
                        Node<TreeNode<E>> tempNode = new Node(popNode.getChild(j));

                        // Queue
                        if(stack.size() == 1) {
                            stackBottomTemp = stack.top;
                            stackBottom = stackBottomTemp;
                        } else {
                            stackBottomTemp.setNext(tempNode);
                            stackBottomTemp = stackBottomTemp.getNext();
                            stack.top = stackBottom;
                        }
                    }
                }
            }
        }

        return insertNode;
    }

    @Override
    public void detach(TreeNode<E> node) throws IllegalArgumentException {
        /*
        * Detach the given node (and its descendants) from this tree.
        * if the node is not in this tree,
        * raise an IllegalArgumentException.
        */
        
        // If node is a root node make a root null
        if(node == rootNode) {
            rootNode = null;
        } else {
            if(rootNode != null) {
                // Initialize a queue with root 
                root();
                while(!stack.isEmpty()) {
                    TreeNode<E> popNode = stack.pop();
                    stackBottom = stackBottom.getNext();

                    // If polled node is same as detach node
                    if(popNode == node) {
                        // Get the node's child position by accessing it's parent's node
                        for(int i = 0; i < popNode.getParent().numChildren(); i++) {
                            if(popNode.getParent().getChild(i) == popNode) {
                                // Detach and exit
                                popNode.getParent().removeChild(i);
                                return;
                            }
                        }
                    }

                    // ELSE
                    // If polled node is NOT SAME as detach node
                    // add it's children to queue
                    for(int i = 0; i < popNode.numChildren(); i++) {
                        stack.push(popNode.getChild(i));
                         Node<TreeNode<E>> tempNode = new Node(popNode.getChild(i));

                         if(stack.size() == 1) {
                            stackBottomTemp = stack.top;
                            stackBottom = stackBottomTemp;
                        } else {
                            stackBottomTemp.setNext(tempNode);
                            stackBottomTemp = stackBottomTemp.getNext();
                            stack.top = stackBottom;
                        }
                    }

                }
            }
            throw new IllegalArgumentException();
        }
    }

    @Override
    public List<E> preOrder() {
        /*
        * Return the sequence of items visited by preorder traversal.
        * If there are no nodes, return an empty list, NOT NULL.
        */

        // Create array
        List<E> seq = new ArrayList<>();        

        // If root is empty return empty array
        if(rootNode == null) return seq;

        // Else traverse
        treePreOrder(rootNode, seq);

        return seq;
    }

    @Override
    public List<E> postOrder() {
        /*
        * Return the sequence of items visited by postorder traversal.
        * If there are no nodes, return an empty list, NOT NULL.
        */

        // Create array
        List<E> seq = new ArrayList<>();

        // If root is empty return empty array
        if(rootNode == null) return seq;

        // Else traverse
        treePostOrder(rootNode, seq);

        return seq;
    }

    @Override
    public List<E> depthOrder() {
        /*
        * Return the sequence of items visited by depthorder traversal.
        * If there are no nodes, return an empty list, NOT NULL.
        */

        // Create array
        List<E> seq = new ArrayList<>();

        // If root is empty return empty array
        if(rootNode == null) return seq;

        // Else traverse level by level
        root();
        while(!stack.isEmpty()) {
            TreeNode<E> popNode = stack.pop();
            stackBottom = stackBottom.getNext();
            seq.add(popNode.getValue());

            for(int i = 0; i < popNode.numChildren(); i++) {
                stack.push(popNode.getChild(i));
                 Node<TreeNode<E>> tempNode = new Node(popNode.getChild(i));

                 if(stack.size() == 1) {
                    stackBottomTemp = stack.top;
                    stackBottom = stackBottomTemp;
                } else {
                    stackBottomTemp.setNext(tempNode);
                    stackBottomTemp = stackBottomTemp.getNext();
                    stack.top = stackBottom;
                }
            }

        }
        return seq;
    }
}