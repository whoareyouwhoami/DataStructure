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

public final class Cars implements ICars {
    public Heap heap;
    /*
    * Use some variables for your implementation.
    */
    private int heapLimit;

    public Cars(int k) {
        /*
        * Constructor
        * This function is an initializer for this class.
        * Input:
        *  + k: The number of the cars you have to consider.
        */
        this.heapLimit = k;
        this.heap = new Heap(new Integer[] {});
    }

    @Override
    public void carDistance(int d) {
        /*
        * Function input:
        *  d: The travel distance of a car. (always positive)
        *
        * Job:
        *  Determine whether or not to keep the travel distance d.
        *  Consider the total time complexity of the program
        */
        
        // Insert n times: O(n log k) since k < n
        if(heap.size() < heapLimit) {
            // O(log k)
            // Insert value if heap size is below k limit
            heap.insert(d);
        } else {
            // If heap size has reached its k limit
            // create temporary heap
            Heap temporary = new Heap(new Integer[] {});

            // Remove from original heap and insert the value to temporary heap
            // until the last node of original heap
            // O(k log k)
            for(int i = 0; i < (heapLimit - 1); i++) {
                int removed = heap.removeMax();
                temporary.insert(removed);
            }

            // If a value to insert d is larger than the minimum value of original heap
            // O(k log k)
            if(d > heap.max()) {
                // Insert d to temporary heap
                temporary.insert(d);
            } else {
                // Ignore d and insert last value in original heap to temporary heap
                temporary.insert(heap.removeMax());
            }

            // Change address to original heap
            heap = temporary;
        }

    }

    @Override
    public int[] getCandidates() {
        /*
        * Function input: Nothing.
        *
        * Job:
        *  Return the k longest travel distances of the travel distances inputed until now.
        *  (You do not have to return the travel distances sorted)
        */
        // Create temporary heap
        Heap temporary = new Heap(new Integer[] {});

        // Create final result array
        int[] result = new int[heap.size()];

        // Array index
        int i = 0;

        // Remove value from original heap
        // Insert into temporary heap (since we have to use them again later)
        // Insert into result array
        // O(k log k)
        while(!heap.isEmpty()) {
            int removed = heap.removeMax();
            temporary.insert(removed);
            result[i++] = removed;
        }

        // Change address to original heap
        heap = temporary;

        return result;
    }
}