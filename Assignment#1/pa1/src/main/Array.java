/*
 * Name: YOUNG WOO JU
 * Student ID #: 2014122074
 */

/* 
 * Do NOT import any additional packages/classes.
 * If you (un)intentionally use some additional packages/classes we did not
 * provide, you may receive a 0 for the homework.
 */

public final class Array implements IArray {
    /*
     * Add some variables you will use.
     */

    // Array
    private int[] arr;

    // Initial array size
    private int arrSize = 0;

    // Sorting status
    private boolean sortStatus = false;

    public Array() {
        /*
         * Constructor 
         * This function is an initializer for this class.
         */

        // Create array of size 0
        arr = new int[arrSize];
    }

    private int[] mergeSortedArray(String type, int[] left, int[] right) {
        // Inital index
        int leftIndex = 0, rightIndex = 0, sortedIndex = 0;

        // Set sorted array size
        int totalSize = left.length + right.length;
        int[] sortedArray = new int[totalSize];

        // Compare and merge
        while(leftIndex < left.length || rightIndex < right.length) {
            // If left array has reached its last index
            // Copy the rest of RIGHT array
            if(leftIndex == left.length) {
                for(int r = rightIndex; r < right.length; r++) {
                    sortedArray[sortedIndex] = right[rightIndex];
                    sortedIndex++;
                    rightIndex++;
                }
            } 
            // If right array has reached its last index
            // Copty the rest of LEFT array
            else if(rightIndex == right.length) {
                for(int l = leftIndex; l < left.length; l++) {
                    sortedArray[sortedIndex] = left[leftIndex];
                    sortedIndex++;
                    leftIndex++;
                }
            } 
            // Else compare and merge in ascending/descending order
            else {
                if(type=="asc") {
                    if(left[leftIndex] < right[rightIndex]) {
                        sortedArray[sortedIndex] = left[leftIndex];
                        leftIndex++;
                    } else {
                        sortedArray[sortedIndex] = right[rightIndex];
                        rightIndex++;
                    }
                }
                if(type=="dsc") {
                    if(left[leftIndex] > right[rightIndex]) {
                        sortedArray[sortedIndex] = left[leftIndex];
                        leftIndex++;
                    } else {
                        sortedArray[sortedIndex] = right[rightIndex];
                        rightIndex++;
                    }
                }
                sortedIndex++;
            }
        }
        return sortedArray;
    }

    private int[] splitArray(int[] xArray, int start, int end) {
        int k = 0;
        
        //  Set array size
        int arraySize = end - start + 1;
        int[] resultArray = new int[arraySize];
        
        // Copying array
        for(int i = start; i < end + 1; i++) {
            resultArray[k] = xArray[i];
            k++;
        }
        return resultArray;
    }

    private int[] mergeSort(String type, int[] unorderArray) {
        // If array is empty 
        if(unorderArray.length == 0) {
            return unorderArray;
        }

        // Find mid point and divide the array into two part]
        int midIndex = unorderArray.length / 2;

        // Splitting into two arrays
        int[] splittedLeft = splitArray(unorderArray, 0, midIndex - 1);
        int[] splittedRight = splitArray(unorderArray, midIndex, unorderArray.length - 1);
        
        // When there is no more split return the array
        if(unorderArray.length == 1) {
            return unorderArray;
        }

        // Recursion + Merging
        int[] l = mergeSort(type, splittedLeft);
        int[] r = mergeSort(type, splittedRight);
        int[] res = mergeSortedArray(type, l,r);

        return res;
    }

    @Override
    public void insert(int value) {
        /*
         * Function input:
         *  + value: An integer to be inserted.
         * 
         * Job:
         *  Insert the given integer according to the state of the array.
         *  - unsorted: insert the integer as the last element of the array.
         *  - sorted: insert the integer at the position that makes the array sorted in ascending order.
         */
        
        // Increase array size
        arrSize++;
        
        // Make a temporary array
        int[] tempArr = new int[arrSize];

        if(sortStatus) { // If sorted
            int insertIndex;

            if(value <= arr[0]) { // If value is smaller than smallest value in array
                insertIndex = 0;
            } else if(value >= arr[arr.length - 1]) { // If value is higher than the largest value in array
                insertIndex = arr.length;
            } else { // If value is between smallest and largest value in array
                int left = 0;
                int right = arr.length - 1;

                while(right - left > 1) {
                    int mid = (left + right) / 2;

                    if(value == arr[mid]) {
                        left = mid;
                    } else if(value < arr[mid]) {
                        right = mid;
                    } else {
                        left = mid + 1;
                    }
                }
                // Inserting index
                insertIndex = right;
            }

            // Left part of array
            for(int i = 0; i < insertIndex; i++) {
                tempArr[i] = arr[i];
            }
            
            // Insert value
            tempArr[insertIndex] = value;

            // Right part of array
            for(int i = insertIndex + 1; i < arr.length + 1; i++) {
                tempArr[i] = arr[i - 1];
            }

        } else { // If NOT sorted
            // Copy original array to temporary array
            for(int i = 0; i < arr.length; i++) {
                tempArr[i] = arr[i];
            }

            // Now insert a new value
            tempArr[arrSize - 1] = value;
        }
        
        // Reassign
        arr = tempArr;
    }

    @Override
    public void delete(int value) throws IllegalStateException {
        /*
         * Function input:
         *  + value: An integer to delete.
         * 
         * Job:
         *   Delete the first element that has the given integer as its value.
         *   If there is no such element, raise an exception.
         */
        int deleteIndex = -1;

        // Search for value based on search status
        if(sortStatus) {
            try {
                deleteIndex = search(value);
            } catch(IllegalStateException e) {
                throw new IllegalStateException();
            }
        } else {
            for(int i = 0; i < arr.length; i++) {
                if(arr[i] == value) {
                    deleteIndex = i;
                }
            }

            if(deleteIndex < 0) {
                throw new IllegalStateException();
            }
        }

        // Since there is a value to remove, decrease array size
        arrSize--;

        // Create temporary array
        int[] tempArr = new int[arrSize];

        // Copy values
        int idx = 0;
        for(int i = 0; i < arr.length; i++) {
            if(i != deleteIndex) {
                tempArr[idx] = arr[i];
                idx++;
            }
        }

        // Reassign
        arr = tempArr;
    }

    @Override
    public int search(int value) throws IllegalStateException {
        /*
         * Function input:
         *  + value: An integer to search.
         * 
         * Job:
         *  Return the first index of the element with the given value.
         *  If there is no such element, raise an exception.
         */

        boolean foundStatus = false;
        int start = 0;

        // Binary search
        if(sortStatus) {
            // Do binary search
            int left = 0;
            int right = arr.length - 1;

            while(left < right) {
                int mid = (left + right) / 2;

                if(value == arr[mid]) {
                    foundStatus = true;
                    start = mid;
                    break;
                }
                if(value < arr[mid]) {
                    right = mid;
                } 
                else {
                    left = mid + 1;
                }
            }
        } else {
            // Linear search
            while(start < arr.length) {
                if(arr[start] == value) {
                    foundStatus = true;
                    break;
                }
                start++;
            }
        }

        if(!foundStatus) { // If value is not found, throw exception
            throw new IllegalStateException();    
        } else { // return index of value
            return start;
        }
    }

    @Override
    public void sort() {
        /**
         * Function input: Nothing
         * 
         * Job:
         *  Change the state of the array to sorted.
         *  Sort the elements in the array in ascending order.
         */

        // Merge sort
        arr = mergeSort("asc", arr);
        sortStatus = true;
    }

    @Override
    public void unsort() {
        /**
         * Function input: Nothing
         * 
         * Job:
         *  Change the state of the array to unsorted.
         */
        sortStatus = false;
    }

    @Override
    public int atIndex(int index) throws IndexOutOfBoundsException {
        /**
         * Function input:
         *  + index: An integer to find the element at that position
         * 
         * Job:
         *  Return the value of the element at the given index.
         */
        if(index < 0 || index > arrSize) {
            throw new IndexOutOfBoundsException();
        }

        return arr[index];
    }

    @Override
    public int size() {
        /*
        * Function input: Nothing
        *
        * Job:
        *  Return the number of elements in this array.
        */
        return arrSize;
    }

    @Override
    public boolean isSorted() {
        /**
         * Function input: Nothing
         * 
         * Job:
         *  Return true if the array is sorted and false otherwise.
         */
        return sortStatus;
    }

    @Override
    public boolean isEmpty() {
        /* You do not have to edit this function. */
        return size() == 0;
    }
}
