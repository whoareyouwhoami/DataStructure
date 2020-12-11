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

public final class MST implements IMST {

    private BufferedReader graph;
    private int[][] adjMat;
    private int numVertex = 0;
    private int numEdge = 0;
    private int[][] pq;

    public MST(String filename) {
        try {
            graph = new BufferedReader(new FileReader(filename));
            
            // Get number of vertices and edges
            String[] nodeInformation = graph.readLine().split(" ");
            numVertex = Integer.parseInt(nodeInformation[0]);
            numEdge = Integer.parseInt(nodeInformation[1]);

            // Create adjacency matrix
            adjMat = new int[numVertex][numVertex];

            // Insert edge and weight information
            String edge = "";
            while((edge = graph.readLine()) != null) {
                String[] direction = edge.split(" ");
                int from = Integer.parseInt(direction[0]);
                int to = Integer.parseInt(direction[1]);
                int weight = Integer.parseInt(direction[2]);
                adjMat[to][from] = weight;
                adjMat[from][to] = weight;
            }
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private int[] getMinNode() {
        int minIdx = -1;
        int minVal = Integer.MAX_VALUE;

        /*
         * PQ: [[nodeTo, weight], [], ..., []]
         */

        // Get minimum weight
        for(int i = 0; i < pq.length; i++) {
            int[] node = pq[i];

            if(node[1] < minVal) {
                minIdx = i;
                minVal = node[1];
            }
        }

        // Set result
        int[] result = new int[2];
        result[0] = pq[minIdx][0];
        result[1] = pq[minIdx][1];

        // Delete node in PQ
        int start = 0;
        int[][] newPQ = new int[pq.length - 1][pq.length - 1];
        for(int i = 0; i < pq.length; i++) {
            if(i == minIdx) continue;
            newPQ[start++] = pq[i];
        }

        // Set new PQ to current PQ
        pq = newPQ;

        return result;
    }

    private void insertPQ(int[] vw) {
        // Get new size and create matrix
        int newSize = pq.length + 1;
        int[][] tmpPQ = new int[newSize][newSize];

        // Copy values
        for(int i = 0; i < pq.length; i++) {
            tmpPQ[i] = pq[i];
        }

        // Insert new value and reassign
        tmpPQ[newSize - 1] = vw;
        pq = tmpPQ;
    }

    private void getDistance(boolean[] visited, int[] distance, int u, int v) {
        // Exit if vertex u is visited
        if(visited[u] || u == v) return;

        // Set vertex u visited
        visited[u] = true;

        // Find connected edges from vertex v and insert them to PQ
        for(int c = 0; c < numVertex; c++) {
            if(adjMat[u][c] > 0) {
                // Edge relaxation
                if(adjMat[u][c] + distance[u] < distance[c]) {
                    distance[c] = adjMat[u][c] + distance[u];
                }
                // Add [vertex, weight] to PQ
                insertPQ(new int[] {c, distance[c]});
            }
        }

        // Get min weight
        int[] node = getMinNode();
        int n = node[0];

        // Check if the node has been visited
        while(visited[n]) {
            node = getMinNode();
            n = node[0];
        }

        // Perform shortest path from n until v is reached
        getDistance(visited, distance, n, v);
    }

    @Override
    public int[] shortestPath(int u, int v) {
        // Set initial PQ
        this.pq = new int[0][0];

        // Set visited array
        boolean[] visited = new boolean[numVertex];

        // Set distance array
        int[] distance = new int[numVertex];
        for(int i = 0; i < numVertex; i++) {
            distance[i] = Integer.MAX_VALUE;
        }

        // Set initial distance for vertex u
        distance[u] = 0;

        // Find shortest path starting from vertex u
        getDistance(visited, distance, u, v);

        return distance;
    }

    private int getMST(boolean[] visited, int result, int v) {
        // Find connected edges from vertex v and insert them to PQ
        for(int r = 0; r < adjMat.length; r++) {
            if(adjMat[r][v] > 0)
                insertPQ(new int[] {r, adjMat[r][v]});
        }

        // Get mininum weight and it's corresponding vertex
        int[] node = getMinNode();
        int n = node[0];
        int w = node[1];

        // Exit if node had been visited
        if(visited[n])
            return result;

        // Visit the node and add edge weight
        visited[n] = true;
        result += w;
        
        // Recursively iterate untill all nodes are visited
        return getMST(visited, result, n);
    }

    @Override
    public int findMST() {
        // Set initial PQ
        this.pq = new int[0][0];

        // Set visited array
        boolean[] visited = new boolean[numVertex];

        return getMST(visited, 0, 0);
    }
}
