public class test {
	

	public int[] mergeSortedArray(String type, int[] left, int[] right) {
		System.out.println("=== MERGE SORT CALLED ===");

		// Inital index
		int leftIndex = 0, rightIndex = 0, sortedIndex = 0;

		// Set sorted array size
		int totalSize = left.length + right.length;
		int[] sortedArray = new int[totalSize];

		// Compare and merge
		while(leftIndex < left.length || rightIndex < right.length) {
			// If left array has reached its last index
			// Copy the rest of right array
			if(leftIndex == left.length) {
				System.out.println("COPY REST OF RIGHT PART");
				for(int r = rightIndex; r < right.length; r++) {
					sortedArray[sortedIndex] = right[rightIndex];
					sortedIndex++;
					rightIndex++;
					System.out.println("RIGHT INDEX " + rightIndex);
				}
			} 
			// If right array has reached its last index
			// Copty the rest of left array
			else if(rightIndex == right.length) {
				System.out.println("COPY REST OF LEFT PART");
				for(int l = leftIndex; l < left.length; l++) {
					sortedArray[sortedIndex] = left[leftIndex];
					sortedIndex++;
					leftIndex++;
					System.out.println("LEFT INDEX " + leftIndex);
				}
			} 
			// Else compare and merge in ascending order
			else {
				if(type=="asc") {
					if(left[leftIndex] < right[rightIndex]) {
						sortedArray[sortedIndex] = left[leftIndex];
						leftIndex++;
						System.out.println("LEFT INDEX " + leftIndex);
					} else {
						sortedArray[sortedIndex] = right[rightIndex];
						rightIndex++;
						System.out.println("RIGHT INDEX " + rightIndex);
					}
				}
				if(type=="dsc") {
					if(left[leftIndex] > right[rightIndex]) {
						sortedArray[sortedIndex] = left[leftIndex];
						leftIndex++;
						System.out.println("LEFT INDEX " + leftIndex);
					} else {
						sortedArray[sortedIndex] = right[rightIndex];
						rightIndex++;
						System.out.println("RIGHT INDEX " + rightIndex);
					}
				}

				sortedIndex++;
			}

		}
		
		return sortedArray;
	}

	public int[] splitArray(int[] xArray, int start, int end) {
		int arraySize = end - start + 1;
		int[] resultArray = new int[arraySize];
		int k = 0;

		for(int i = start; i < end + 1; i++) {
			resultArray[k] = xArray[i];
			k++;
		}
		return resultArray;
	}

	public int[] mergeSort(String type, int[] unorderArray) {
		if(unorderArray.length == 0) {
			return unorderArray;
		}

		// Find mid point and divide the array into two part]
		int midIndex = unorderArray.length/2;

		int[] splittedLeft = splitArray(unorderArray, 0, midIndex-1);
		int[] splittedRight = splitArray(unorderArray, midIndex, unorderArray.length-1);

		System.out.println("SPLIT LEFT SIZE: " + splittedLeft.length);
		System.out.println("SPLIT RIGHT SIZE: " + splittedRight.length);
		System.out.println();

		for(int i=0; i<splittedLeft.length;i++) {
			System.out.print(splittedLeft[i] + " ");
		}
		System.out.println();
		for(int i=0; i<splittedRight.length;i++) {
			System.out.print(splittedRight[i] + " ");
		}

		System.out.println("\n-----------------------");

		
		if(unorderArray.length == 1) {
			System.out.println("EXITING");
			return unorderArray;
		}

		int[] l = mergeSort(type, splittedLeft);
		int[] r = mergeSort(type, splittedRight);
		int[] res = mergeSortedArray(type, l,r);

		return res;
	}



	public static void main(String[] args) {
		test x = new test();

		// int[] left = {2,4,7,8};
		// int[] right = {3,5,9,10};
		// int[] result = x.mergeSortedArray(left, right);

		int[] unorder = {1,10, 5,1,3,7,2,9,8};
		// int[] unorder = {};
		// int[] unorder = {5,1};
		int[] result = x.mergeSort("asc", unorder);


		
		System.out.println("\n=== RESULT ===");
		for(int i = 0; i < result.length; i++) {
			System.out.print(result[i] + " ");
		}
		System.out.println("");




	}
}