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

public final class Graph implements IGraph {

    private BufferedReader graph;
    private int[][] adjMat;
    private int numVertex = 0;
    private int numEdge = 0;

    public Graph(String filename) {
        /*
         * Constructor
         * This function is an initializer for this class.
         */

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

    @Override
    public void insertVertex() {
        // Increase number of vertex
        numVertex++;

        // Create temporary matrix with new size
        int[][] tmpMat = new int[numVertex][numVertex];

        // Copy previous adjacency matrix
        for(int row = 0; row < adjMat.length; row++) {
            for(int col = 0; col < adjMat[row].length; col++) {
                tmpMat[row][col] = adjMat[row][col];
            }
        }

        // Reassign
        adjMat = tmpMat;
    }

    @Override
    public void deleteVertex(int n) {
        // If deleting vertex is larger than current vertex number, exit
        if((n + 1) > numVertex) return;

        // Decrease number of vertex
        numVertex--;

        // Create temporary matrix with new size
        int[][] tmpMat = new int[numVertex][numVertex];

        // Delete nth row and nth column
        int prevRow = 0;
        for(int row = 0; row < numVertex; row++) {
            int prevCol = 0;
            if(prevRow == n)
                prevRow++;
            
            for(int col = 0; col < numVertex; col++) {
                if(prevCol == n)
                    prevCol++;
                tmpMat[row][col] = adjMat[prevRow][prevCol];
                prevCol++;
            }
            prevRow++;
        }

        // Reassign
        adjMat = tmpMat;
    }

    @Override
    public void insertEdge(int u, int v) {
        adjMat[u][v] = 1;
    }

    @Override
    public void deleteEdge(int u, int v) {
        adjMat[u][v] = 0;
    }

    @Override
    public int[][] matrix() {
        return adjMat;
    }

    @Override
    public int size() {
        return numVertex;
    }
}