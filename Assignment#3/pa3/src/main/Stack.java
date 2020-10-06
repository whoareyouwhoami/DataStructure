/*
 * Name: Young Woo Ju
 * Student ID #: 2014122074
 */

/* 
 * Do NOT import any additional packages/classes.
 * If you (un)intentionally use some additional packages/classes we did not
 * provide, you may receive a 0 for the homework.
 */

public final class Stack<E> implements IStack<E> {
    public Node<E> top = null;
    /*
    * Use some variables for your implementation.
    */
    private int stackSize = 0;

    public Stack() {
        /*
        * Constructor
        * This function is an initializer for this class.
        */
        this.top = top;
    }

    @Override
    public void push(E item) {
        /*
        * Function input:
        *  item: an item to be inserted.
        *
        * Job:
        *  Insert the item at the top of the stack.
        */

        // Stacking node
        Node<E> insertNode = new Node(item);

        if(top == null) { // When stack is first inserted
            top = insertNode;
        } else { // If an element already exist
            insertNode.setNext(top);
            top = insertNode;
        }
        
        // Increase stack size
        stackSize++; 
    }

    @Override
    public E pop() throws IllegalStateException {
        /*
        * Function input:
        *  item: an item to be inserted.
        *
        * Job:
        *  Remove an item from the top of the stack and return that item.
        */
        if(!isEmpty()) {
            // Get pop value
            E result = top.getValue();
            
            // Set top to next node
            top = top.getNext();
            
            // Reduce stack size
            stackSize--;

            // System.out.println("Popped: " + result);
            return result;
        }

        throw new IllegalStateException();
    }

    @Override
    public int size() {
        /*
        * Function input: Nothing
        *
        * Job:
        *  Return the number of items in the stack.
        */
        return stackSize;
    }

    @Override
    public boolean isEmpty() {
        /* You must not edit this function. */
        return top == null;
    }
}
