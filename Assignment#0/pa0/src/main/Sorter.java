
/*
 * Name: Young Woo Ju
 * Student ID #: 2014122074 
 */

/* 
 * Do NOT import any additional packages/classes.
 * If you (un)intentionally use some additional packages/classes we did not
 * provide, you may receive a 0 for the homework.
 */

public class Sorter implements ISorter {
	public Sorter() { ; }


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
	public int[] ascending(int[] a) {
		/*
		 * Input:
		 *  - an integer array A
		 *
		 * Output: a sorted array A in *ascending* order.
		 */
		return mergeSort("asc", a);
	}
	
	@Override
	public int[] descending(int[] a) {
		/*
		 * Input:
		 *  - an integer array A
		 *
		 * Output: a sorted array A in *descending* order.
		 */
		return mergeSort("dsc", a);
	}
}