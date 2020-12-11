/*
* Name: Young Woo Ju
* Student ID#: 2014122074
*/

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
/*
* Do NOT use any external packages/classes.
* If you (un)intentionally use them we did not provide,
* you will get 0.
* Also do NOT use auto-import function on IDEs.
* If the import statements change, you will also get 0.
*/

public final class GraphSort implements IGraphSort {

    private BufferedReader graph;
    private int[][] adjMat;
    private int numVertex = 0;
    private int numEdge = 0;

    public GraphSort(String filename) {
        try {
            graph = new BufferedReader(new FileReader(filename));
            
            // Get number of vertices and edges
            String[] nodeInformation = graph.readLine().split(" ");
            numVertex = Integer.parseInt(nodeInformation[0]);
            numEdge = Integer.parseInt(nodeInformation[1]);

            // Create adjacency matrix
            adjMat = new int[numVertex][numVertex];

            // Insert edge information
            String edge = "";
            while((edge = graph.readLine()) != null) {
                String[] direction = edge.split(" ");
                int from = Integer.parseInt(direction[0]);
                int to = Integer.parseInt(direction[1]);
                adjMat[from][to] = 1;
            }
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean cycleDFS(boolean[] visited, int v) {
        // If node is visited again, cycle exist -> return true
        if(visited[v]) return true;

        // Visit the node and iterate through its edges
        visited[v] = true;
        for(int c = 0; c < adjMat[v].length; c++) {
            if(adjMat[v][c] == 1)
                return cycleDFS(visited, c);
        }

        // Nothing is visited twice -> return false
        return false;
    }

    @Override
    public boolean cycle() {
        for(int v = 0; v < numVertex; v++) {
            // Set visited array
            boolean[] visited = new boolean[numVertex];

            // Check for if cycle exist
            boolean result = cycleDFS(visited, v);
            
            // True if exist and exit
            if(result) return true;
        }
        return false;
    }

    private void topologicalOrderSorting(int[] ordered, boolean[] visited, int v) {
        // If node v is visited, exit
        if(visited[v]) return;

        // Check for any connected node
        visited[v] = true;
        for(int c = 0; c < adjMat[v].length; c++) {
            if(adjMat[v][c] == 1)
                topologicalOrderSorting(ordered, visited, c);
        }

        // Search for place to insert node v
        for(int i = numVertex - 1; i > -1; i--) {
            if(ordered[i] < 0) {
                ordered[i] = v;
                break;
            }
        }
    }

    @Override
    public int[] topologicalOrder() {
        // Set visited array
        boolean[] visited = new boolean[numVertex];

        // Topological sorting ordered array, set it to -1
        int[] ordered = new int[numVertex];
        for(int i = 0; i < numVertex; i++) {
            ordered[i] = -1;
        }

        // Run DFS
        for(int v = 0; v < numVertex; v++) {
            topologicalOrderSorting(ordered, visited, v);
        }

        return ordered;
    }
}
