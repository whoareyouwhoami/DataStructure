/*
 * Name: YOUNG WOO JU
 * Student ID #: 201412207
 */

/* 
 * Do NOT import any additional packages/classes.
 * If you (un)intentionally use some additional packages/classes we did not
 * provide, you may receive a 0 for the homework.
 */

public final class Set implements ISet {
    /*
     * Add some variables you will use.
     */

    // Set array
    private int[] arrSet;

    // Initial set array size
    private int setSize = 0;

    public Set() {
        /*
         * Constructor 
         * This function is an initializer for this class.
         */

        // Create array of size 0
        arrSet = new int[setSize];
    }

    // Linear search
    private int search(int[] arr, int value) {
        for(int i = 0; i < arr.length; i++) {
            if(arr[i] == value) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void insert(int value) {
        /*
         * Function input:
         *  + value: An integer to be inserted.
         * 
         * Job:
         *  Insert the integer if it does not exist.
         */

        // If value doesn't exist
        if(search(arrSet, value) < 0) {
            // Increase array size
            setSize++;

            // Create temporary array
            int[] tempArr = new int[setSize];

            // Insert previous array values to temporary array
            for(int i = 0; i < arrSet.length; i++) {
                tempArr[i] = arrSet[i];
            }

            // Insert value
            tempArr[setSize - 1] = value;
            
            // Reassign
            arrSet = tempArr;
        }
    }

    @Override
    public void delete(int value) throws IllegalStateException {
        /**
         * Function input:
         *  + value: An integer to be deleted
         * 
         * Job:
         *  Delete the integer from the set.
         *  Raise an exception if it does not exist.
         */

        // Search if value exist
        int deleteIndex = search(arrSet, value);

        // If value exist
        if(deleteIndex >= 0) {
            // Decrease array size
            setSize--;

            // Create temporary array
            int[] tempArr = new int[setSize];

            // Copy values
            int idx = 0;
            for(int i = 0; i < arrSet.length; i++) {
                if(i != deleteIndex) {
                    tempArr[idx] = arrSet[i];
                    idx++;
                }
            }

            // Reassign
            arrSet = tempArr;

        } else {
            // If value doesn't exist throw exception
            throw new IllegalStateException();
        }
    }

    @Override
    public void union(ISet set) {
        /**
         * Function input:
         *  + set: A set to be unioned
         * 
         * Job:
         *  Union the other set to this set
         */

        // Convert input set object to int[]
        int[] inputSet = set.show();

        // Add to current array only the values that doesn't exist from input set
        for(int i = 0; i < inputSet.length; i++) {
            int existIndex = search(arrSet, inputSet[i]);
            if(existIndex < 0) {
                insert(inputSet[i]);
            }            
        }
    }

    @Override
    public void intersection(ISet set) {
        /**
         * Function input:
         *  + set: A set to be intersectioned
         * 
         * Job:
         *  Intersect the other set to this set
         */

        // Create new Set object
        Set tempSet = new Set();

        // Convert input set object to int[]
        int[] inputSet = set.show();

        // Insert to new Set object only the values that exist in both current and input array
        for(int i = 0; i < arrSet.length; i++) {
            for(int j = 0; j < inputSet.length; j++) {
                if(arrSet[i] == inputSet[j]) {
                    tempSet.insert(arrSet[i]);
                }
            }
        }

        // Reassign 
        arrSet = tempSet.show();

        // Change array size
        setSize = arrSet.length;
    }

    @Override
    public void subtraction(ISet set) {
        /**
         * Function input:
         *  + set: A set to be subtracted
         * 
         * Job:
         *  Subtract the other set from this set
         */

        // Convert input set object to int[]
        int[] inputSet = set.show();

        // Delete input values if exist, else ignore and continue
        for(int i = 0; i < inputSet.length; i++) {
            try {
                delete(inputSet[i]);
            } catch (Exception e) {

            }
        }
    }

    @Override
    public int[] show() {
        /**
         * Function input: Nothing
         * 
         * Job:
         *  Return the elements of the set as an array.
         */

        int[] returnArr = new int[setSize];
        
        for(int i = 0; i < setSize; i++) {
            returnArr[i] = arrSet[i];
        }

        // return new int[];
        return returnArr;
    }
}
