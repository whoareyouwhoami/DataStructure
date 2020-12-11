/*
* Name: Young Woo Ju
* Student ID#: 2014122074
*/

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
/*
* Do NOT use any external packages/classes.
* If you (un)intentionally use them we did not provide,
* you will get 0.
* Also do NOT use auto-import function on IDEs.
* If the import statements change, you will also get 0.
*/

public final class SCC implements ISCC {

    private IGraph graph;
    private int[][] adjMat;


    public SCC(String filename) {
        // Get adjacency matrix
        graph = new Graph(filename);
        adjMat = graph.matrix();
    }

    @Override
    public boolean path(int u, int v) {
        // Add starting vertex to stack
        List<Integer> stack = new ArrayList<>();
        stack.add(u);

        // DFS iteratively
        while(!stack.isEmpty()) {
            int nextV = stack.remove(0);

            if(nextV == v) return true;

            for(int col = 0; col < adjMat[nextV].length; col++) {
                if(adjMat[nextV][col] == 1) {
                    stack.add(0, col);
                }
            }
        }
        return false;
    }

    private int[][] reverseMat(int[][] adjMat) {
        int l = adjMat.length;
        int[][] revMat = new int[l][l];

        for(int r = 0; r < l; r++) {
            for(int c = 0; c < l; c++) {
                revMat[c][r] = adjMat[r][c];
            }
        }
        return revMat;
    }

    private void DFS(int[][] matrix, boolean[] visited, int v) {
        // If vertex v is visited, exit
        if(visited[v]) return;

        // Mark vertex v visited
        visited[v] = true;

        // DFS
        for(int c = 0; c < matrix[v].length; c++) {
            if(matrix[v][c] == 1) DFS(matrix, visited, c);
        }
    }

    @Override
    public boolean connectivity() {
        // Create visited array
        boolean[] visited = new boolean[graph.size()];

        // DFS recursively
        DFS(adjMat, visited, 0);

        // Check for any unvisited node
        for(boolean b: visited) {
            if(!b) return false;
        }

        // Check again from the reversed graph
        int[][] revMat = reverseMat(adjMat);
        visited = new boolean[graph.size()];

        // Run DFS
        DFS(revMat, visited, 0);

        // Check for any unvisited node
        for(boolean b: visited) {
            if(!b) return false;
        }

        return true;
    }

    private void SCCdfs(int[][] matrix, List<Integer> stack, boolean[] visited, int v) {
        if(visited[v]) return;

        visited[v] = true; 

        for(int c = 0; c < matrix[v].length; c++) {
            if(matrix[v][c] == 1) {
                SCCdfs(matrix, stack, visited, c);
                
            }
        }
        // Add to stack if DFS is done
        stack.add(0, v);
    }

    private void RevSCCdfs(int[][] matrix, boolean[] visited, List<Integer> scc, int v) {
        if(visited[v]) return;

        visited[v] = true;
        scc.add(v);

        for(int c = 0; c < matrix[v].length; c++) {
            if(matrix[v][c] == 1) {
                RevSCCdfs(matrix, visited, scc, c);
            }
        }
    }

    private void sortList(List<Integer> unordered) {
        // Create int[] array
        int[] unsorted = new int[unordered.size()];

        // Copy unordered array to int[] array
        for(int i = 0; i < unordered.size(); i++) {
            unsorted[i] = unordered.get(i);
        }

        // Sort the array
        int[] sorted = mergeSort(unsorted);

        // Add sorted element to list
        unordered.clear();
        for(int i: sorted) {
            unordered.add(i);
        }
    }

    @Override
    public List<List<Integer>> findSCC() {
        List<List<Integer>> result = new ArrayList<>();

        // Visited array
        boolean[] visited = new boolean[graph.size()];

        // Stack
        List<Integer> stack = new ArrayList<>();

        // Run DFS
        for(int v = 0; v < adjMat.length; v++) {
            SCCdfs(adjMat, stack, visited, v);
        }

        // Reverse graph and create new visited array
        int[][] revMat = reverseMat(adjMat);
        boolean[] revVisited = new boolean[graph.size()];

        // Pop stack and run DFS
        while(!stack.isEmpty()) {
            // Pop vertex v
            int v = stack.remove(0);

            // Continue if vertex v is visited
            if(revVisited[v]) continue;

            // Else run DFS
            List<Integer> scc = new ArrayList<>();
            RevSCCdfs(revMat, revVisited, scc, v);

            // Sort the list
            sortList(scc);

            // Find ordered insertion point
            int position = 0;
            int val = scc.get(0);            

            for(position = 0; position < result.size(); position++) {
                if(val < result.get(position).get(0)) break;
            }

            // Add scc to result array
            result.add(position, scc);
        }

        return result;
    }

    /*
     * Slight modification of Merge Sort in pa0
     */
    private int[] mergeSortedArray(int[] left, int[] right) {
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
            // Copy the rest of LEFT array
            else if(rightIndex == right.length) {
                for(int l = leftIndex; l < left.length; l++) {
                    sortedArray[sortedIndex] = left[leftIndex];
                    sortedIndex++;
                    leftIndex++;
                }
            } 
            // Else compare and merge in ascending/descending order
            else {
                if(left[leftIndex] < right[rightIndex]) {
                    sortedArray[sortedIndex] = left[leftIndex];
                    leftIndex++;
                } else {
                    sortedArray[sortedIndex] = right[rightIndex];
                    rightIndex++;
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

    private int[] mergeSort(int[] unorderArray) {
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
        int[] l = mergeSort(splittedLeft);
        int[] r = mergeSort(splittedRight);
        int[] res = mergeSortedArray(l,r);

        return res;
    }


}
