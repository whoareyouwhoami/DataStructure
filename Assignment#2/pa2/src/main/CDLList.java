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

    public CDLList() {
        /*
         * Constructor 
         * This function is an initializer for this class.
         */
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
    }

    @Override
    public void delete() throws IllegalStateException {
        /*
         * Function input: Nothing
         * 
         * Job:
         *  Delete the previous node of the head.
         */
    }

    @Override
    public Node getHead() throws IllegalStateException {
        /*
         * Function input: Nothing
         * 
         * Job:
         *  return the reference of the head. If none, raise an exception.
         */
        return null;
    }

    @Override
    public void rotateForward() throws IllegalStateException {
        /*
         * Function input: Nothing
         * 
         * Job:
         *  Rotate the list forward. If none, raise an exception.
         */
    }

    @Override
    public void rotateBackward() throws IllegalStateException {
        /*
         * Function input: Nothing
         * 
         * Job:
         *  Rotate the list backward. If none, raise an exception.
         */
    }

    @Override
    public int size() {
        /*
         * Function input: Nothing
         * 
         * Job:
         *  Return the size of the list
         */
        return 0;
    }

    @Override
    public boolean isEmpty() {
        /* You do not have to edit this function. */
        return size() == 0;
    }
}
