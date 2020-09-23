/*
 * Name: 
 * Student ID #: 
 */

/* 
 * Do NOT import any additional packages/classes.
 * If you (un)intentionally use some additional packages/classes we did not
 * provide, you may receive a 0 for the homework.
 */

public final class CDLList implements ICDLList {
    private Node head;
    /*
     * Add some variables you will use.
     */
    private int linkSize = 0;

    public CDLList() {
        /*
         * Constructor 
         * This function is an initializer for this class.
         */
        this.head = new Node();
    }

    @Override
    public void insert(int value) {
        /*
         * Function input:
         *  + value: An integer to be inserted.
         * 
         * Job:
         *  Insert the given integer to the list.
         */

        // If linked list is empty
        if(!isEmpty()) {
            // Inserting node
            Node insertNode = new Node(value, head.getPrev(), head);

            head.getPrev().setNext(insertNode);
            head.setPrev(insertNode);
        } else {
            head.setValue(value);
            head.setNext(head);
            head.setPrev(head);
        }

        // Inserted
        linkSize++;
    }

    @Override
    public void delete() throws IllegalStateException {
        /*
         * Function input: Nothing
         * 
         * Job:
         *  Delete the previous node of the head.
         */

        if(!isEmpty()) {
            Node connectNode = head.getPrev().getPrev();

            connectNode.setNext(head);
            head.setPrev(connectNode);

            // Decrease list size
            linkSize--; 

        } else {
            throw new IllegalStateException("DELETION ERROR BRO");
        }
    }

    @Override
    public Node getHead() throws IllegalStateException {
        /*
         * Function input: Nothing
         * 
         * Job:
         *  return the reference of the head. If none, raise an exception.
         */
        if(!isEmpty()) {
            return head;
        } else {
            throw new IllegalStateException("No head");
        }
    }

    @Override
    public void rotateForward() throws IllegalStateException {
        /*
         * Function input: Nothing
         * 
         * Job:
         *  Rotate the list forward. If none, raise an exception.
         */
        if(!isEmpty()) {
            // Change head to next node
            head = head.getNext();
        } else {
            throw new IllegalStateException("ROTATE FORWARD: No head");
        }
    }

    @Override
    public void rotateBackward() throws IllegalStateException {
        /*
         * Function input: Nothing
         * 
         * Job:
         *  Rotate the list backward. If none, raise an exception.
         */
        if(!isEmpty()) {
            // Change head to previous node
            head = head.getPrev();
        } else {
            throw new IllegalStateException("ROTATE BACKWARD: No head");
        }
    }

    @Override
    public int size() {
        /*
         * Function input: Nothing
         * 
         * Job:
         *  Return the size of the list
         */
        return linkSize;
    }

    @Override
    public boolean isEmpty() {
        /* You do not have to edit this function. */
        return size() == 0;
    }
}
