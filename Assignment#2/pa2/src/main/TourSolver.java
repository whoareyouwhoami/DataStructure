/*
 * Name: Young Woo Ju
 * Student ID #: 2014122074
 */

/* 
 * Do NOT import any additional packages/classes.
 * If you (un)intentionally use some additional packages/classes we did not
 * provide, you may receive a 0 for the homework.
 */

public final class TourSolver implements ITourSolver {
    /*
     * Add some variables you will use.
     */

    // Knight's movement
    private int[] x = {1, 2, -1, -2, 1, 2, -1, -2};
    private int[] y = {2, 1, 2, 1, -2, -1, -2, -1};

    // squareId visited status array
    private boolean[] visited = new boolean[0];

    // Next possible path coordinates array
    private int[] possibilities = new int[0];

    // Final result array
    private int[] resultArray = new int[0];

    // Result array size
    private int filled = 0;

    // True values
    private int trueFilled = 0;

    private int[] insertNum(int[] array, int num) {
        int tempSize = array.length;
        tempSize++;

        int[] tempArr = new int[tempSize];

        for(int i=0; i<array.length; i++) {
            tempArr[i] = array[i];
        }
        
        tempArr[array.length] = num;

        return tempArr;
    }

    private int[] insertCoordinate(int[] array, int w, int h) {
        int tempSize = array.length;
        tempSize += 2;

        int[] tempArr = new int[tempSize];

        for(int i=0; i<array.length; i++) {
            tempArr[i] = array[i];
        }
        
        // Inserting coordinates
        tempArr[tempSize - 2] = w;
        tempArr[tempSize - 1] = h;

        return tempArr;
    }

    private void removeNum() {
        int arraySize = resultArray.length;
  
        int[] tempArr = new int[arraySize-1];

        for(int i=0; i<arraySize-1; i++) {
            tempArr[i] = resultArray[i];
        }

        resultArray = tempArr;
    }

    private int[] getPossibleCoordinate(Board board, int w, int h) {
        // Resetting array
        int numPossibility = 0;
        possibilities = new int[0];

        for(int i=0; i<8; i++) {
            int checkW = w + x[i];
            int checkH = h + y[i];

            try{
                if(!board.isMissing(checkW, checkH)) { // if board squareId = false
                    possibilities = insertCoordinate(possibilities, checkW, checkH);
                }
            } catch(IndexOutOfBoundsException e) {}
        }

        return possibilities;
    }

    private boolean[] getBoardMissing(Board board) {
        // Resetting true value
        trueFilled = 0;

        // True value list
        boolean[] tempVisited = new boolean[board.getWidth() * board.getHeight()];

        for(int i = 0; i < board.getWidth(); i++) {
            for(int j = 0; j < board.getHeight(); j++) {
                // Cell index
                int cell = board.squareId(i,j);

                // Duplicate board missing status
                if(board.isMissing(i,j)) {
                    tempVisited[cell] = true;
                    trueFilled++;
                } else {
                    tempVisited[cell] = false;
                }
            }
        }
        return tempVisited;
    }

    private void knightTour(Board board, int w, int h) {

        // Retrieve possible coordinates from (w, h)
        int[] possibleCoordinate = getPossibleCoordinate(board, w, h);

        // Possible coordinate size
        int possibleSize = possibleCoordinate.length / 2;

        // Pointers
        int pointLeft = 0;
        int pointRight = 1;

        // Board size
        int boardSize = board.getWidth() * board.getHeight();

        // Add (w, h) coordinate to result array
        resultArray = insertNum(resultArray, board.squareId(w, h));
        
        // Increase result array size
        filled++;

        // Change visited index -> true
        visited[board.squareId(w, h)] = true;
        trueFilled++;

        // While there is a possible path from current coordinate (w, h)
        while(possibleSize > 0) {

            // IF the next possible path coordinate has NOT been visited
            if(visited[board.squareId(possibleCoordinate[pointLeft], possibleCoordinate[pointRight])] == false) {

                // If the whole square ID has not been visited
                if(trueFilled != boardSize) {
                    // Repeat this method with the next possible path coordinate
                    knightTour(board, possibleCoordinate[pointLeft], possibleCoordinate[pointRight]);
                } else {
                    // Exit if the every cell in the board is visited
                    break;
                }
            }

            // IF the next possible path coordinate has ALREADY been visited, try out the next possible path coordinates
            pointLeft += 2;
            pointRight += 2;
            possibleSize--;    
        }


        // IF there is no more possible path coordinates available
        if(trueFilled != boardSize && resultArray.length > 0) {

            // Change current coordinate to false 
            visited[board.squareId(w, h)] = false;
            trueFilled--;

            // Remove current coordinate squareId from result array
            removeNum();

            // Decrease the result array size
            filled--;
        }

        // knightTour() END 
    }


    @Override
    public int[] solve(Board board) {
        /*
        * Function input:
        *  + board: A board with some missing squares.
        *
        * Job:
        *  Return a seqence of knight's tour solution on the given board.
        *  If there is no solution, return an empty sequence.
        */
        for(int i = 0; i < board.getWidth(); i++) {
            for(int j = 0; j < board.getHeight(); j++) {
                
                // Process when squareId(i, j) is NOT missing
                if(!board.isMissing(i, j)) {

                    // Resetting result array
                    resultArray = new int[0];

                    // Resetting visited array
                    visited = getBoardMissing(board);

                    // Perfome knight's tour
                    knightTour(board, i, j);

                    // Exit if answer is found
                    int maxSize = board.getWidth() * board.getHeight();
                    
                    // If true filled is equal to the board size
                    if(trueFilled == maxSize) {
                        return resultArray;
                    }
                }
            }
        }
        return resultArray;
    }
}