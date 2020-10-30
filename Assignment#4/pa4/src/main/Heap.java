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

public final class Heap implements IHeap {
    public Node root;
    /*
    * Use some variables for your implementation.
    */
    private Node[] heap;
    private int heapSize = 0;

    public Heap(Integer[] array) {
        /*
        * Constructor
        * This function is an initializer for this class.
        * Input:
        *  + array: The initial unsorted array of values.
        *           You need to construct the heap bottom-up.
        */
        // Set heap size
        heapSize = array.length;

        // Assign heap array size
        heap = new Node[array.length];

        if(heapSize > 0) {
            // O(N)
            // Convert value into a node
            for(int i = 0; i < heap.length; i++) {
                Node v = new Node(array[i]);
                heap[i] = v;            
            }

            // O(N)
            // Bottom-Up building heap
            for(int i = (array.length / 2) - 1; i >= 0; i--) {
                buildMaxHeap(i, heap);
            }

            // Set root
            root = heap[0];
        }
    }

    private void buildMaxHeap(int position, Node[] array) {
        int leftChild = 2 * position + 1;
        int rightChild = 2 * position + 2;

        Node parentNode = array[position];
        Node leftNode = null;
        Node rightNode = null;

        if(leftChild > array.length - 1 && rightChild > array.length - 1) {
            return;
        } else if(leftChild < array.length && rightChild < array.length) {
            rightNode = array[rightChild];
            rightNode.setParent(parentNode);
        }

        leftNode = array[leftChild];
        leftNode.setParent(parentNode);

        // Set parent Node
        parentNode.setLChild(leftNode);
        parentNode.setRChild(rightNode);

        // Up heap
        Node v = parentNode;
        while(v != null) {
            maxHeapify(v);
            v = v.getParent();
        }
    }

    private void maxHeapify(Node node) {
        // Execute until right before leaf node
        while(node.getLChild() != null || node.getRChild() != null) {
            // Compare child values
            Node compareNode = new Node();
            if(node.getRChild() == null) {
                compareNode = node.getLChild();
            } else {
                if(node.getLChild().getData() > node.getRChild().getData()) {
                    compareNode = node.getLChild();
                } else {
                    compareNode = node.getRChild();
                }
            }

            // Compare parent and child
            if(node.getData() < compareNode.getData()) {
                Integer tempVal = node.getData();
                node.setData(compareNode.getData());
                compareNode.setData(tempVal);
            } else {
                break;
            }
            node = compareNode;
        }
    }

    private Node findNode(Node node, int position) {
        // Recursion - until the node reaches level 2
        if(position > 3 ) {
            node = findNode(node, position / 2);
        }

        // Find direction of node
        int direction = position % 2;

        // Return child node
        if(direction == 0) {
            return node.getLChild();
        } else {
            return node.getRChild();
        }
    }

    @Override
    public void insert(Integer value) {
        /*
        * Function input:
        *  value: A value to be inserted.
        *
        * Job:
        *  Insert the item value at the right position of the heap.
        */

        if(root == null) {
            root = new Node(value);
        } else {
            // Parent node of insertion
            Node nodeLast;

            // Insertion position
            int insertPosition = size() + 1;

            // Find parent node to insert the value
            // O(log n)
            if(insertPosition > 3) {
                nodeLast = findNode(root, insertPosition / 2);
            } else {
                nodeLast = root;
            }

            // Since heap is a complete binary tree
            if(nodeLast.getLChild() == null) {
                nodeLast.setLChild(new Node(value, nodeLast));
            } else {
                nodeLast.setRChild(new Node(value, nodeLast));
            }

            // Up heap
            // O(log n)
            Node v = nodeLast;
            while(v != null) {
                maxHeapify(v);
                v = v.getParent();
            }
        }
        
        // Increase heap size
        heapSize++;
    }

    @Override
    public Integer removeMax() throws IllegalStateException {
        /*
        * Function input: Nothing
        *
        * Job:
        *  Delete and return the maximum value in the heap.
        *  Throw an exception if the tree is empty.
        */
        // If heap is empty throw exception
        if(isEmpty()) {
            throw new IllegalStateException("HEAP IS EMPTY");
        }

        // Value to remove 
        Integer removeValue = max();
        
        // Swapping node position
        int exchangePosition = size();

        // Parent node of last node in a heap
        Node nodeLast;

        // If heap is a root itself, remove root
        if(exchangePosition == 1) {
            root = null;
            heapSize--;
            return removeValue;
        } else if(exchangePosition > 3) {
            // If swapping node is below level 2, find it's parent node
            nodeLast = findNode(root, exchangePosition / 2);
        } else {
            // If node to exchange is above level 2, let root be the parent node
            nodeLast = root;
        }

        // Finding swapping node position and swap with root
        if(exchangePosition % 2 == 0) {
            root.setData(nodeLast.getLChild().getData());
            nodeLast.setLChild(null);
        } else {
            root.setData(nodeLast.getRChild().getData());
            nodeLast.setRChild(null);
        }

        // Down heap
        maxHeapify(root);
        
        // Reduce heap size
        heapSize--;

        return removeValue;
    }

    @Override
    public Integer max() throws IllegalStateException {
        /*
        * Function input: Nothing
        *
        * Job:
        *  Return the maximum value in the heap.
        *  Throw an exception if the tree is empty.
        */
        return getRoot().getData();
    }

    @Override
    public int size() {
        /*
        * Function input: Nothing
        *
        * Job:
        *  Return the size of the heap
        */
        return heapSize;
    }

    @Override
    public Node getRoot() throws IllegalStateException {
        /*
        * Function input: Nothing
        *
        * Job:
        *  Return the root node of the heap.
        *  Throw an exception if the tree is empty.
        */
        if (!isEmpty()) {
            return root;
        }
        throw new IllegalStateException("There is no root");
    }

    @Override
    public boolean isEmpty() {
        /* You must not edit this function. */
        return root == null;
    }
}
