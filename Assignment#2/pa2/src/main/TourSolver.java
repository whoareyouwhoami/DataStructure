/*
 * Name: 
 * Student ID #: 
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

    private int choice = 1;

    private int[] coordStart = new int[2];

    private int[] pointStart(Board board) {
        int boardLength = board.getWidth() * board.getHeight();

        for(int h=0; h<board.getHeight(); h++) {
            for(int w=0; w<board.getWidth(); w++) {
                if(!board.isMissing(w,h)) {
                    coordStart[0] = w;
                    coordStart[1] = h;
                    return coordStart;
                }
            }
        }

        return new int[0];
    }

    private int[] moveKnightConditions(int choice, int w, int h) {
        int[] coord = new int[2];

        // There are 8 choices
        if(choice == 1) {
            w += 2;
            h += 1;
        } else if(choice == 2) {
            w += 1;
            h += 2;
        } else if(choice == 3) {
            w += 2;
            h -= 1;
        } else if(choice == 4) {
            w += 1;
            h -= 2;
        } else if(choice == 5) {
            w -= 2;
            h += 1;
        } else if(choice == 6) {
            w -= 1;
            h += 2;
        } else if(choice == 7) {
            w -= 2;
            h -= 1;
        } else if(choice == 8) {
            w -= 1;
            h -= 2;
        } else {
            throw new IndexOutOfBoundsException("Not a possible choice");
        }

        // Return coordinate
        coord[0] = w;
        coord[1] = h;

        return coord;
    }

    private void moveKnightRecur(boolean[] visited, Board board, int w, int h) {
        // if isMissing false -> set it to true
        // add squareId to visited[]
        
        // if isMissing -> set it to true
        
        System.out.println("@ w:" + w + " h:" + h + " | squareId: " + board.squareId(w, h) + " | isMissing: " + board.isMissing(w, h));
        if(!board.isMissing(w, h)) {
            visited[board.squareId(w, h)] = true;
        }



        // System.out.print("Board visited: [ ");
        // for(int i=0; i<visited.length; i++) {
        //     System.out.print(visited[i] + " ");
        // }
        // System.out.println("]\n");


        // int[] nextCoord = {-1, -1};

        // while(choice < 9) {
        //     nextCoord = moveKnightConditions(choice, w, h);

        //     if(nextCoord[0] <= board.getWidth()-1 && nextCoord[0] > -1 && nextCoord[1] <= board.getHeight()-1 && nextCoord[1] > -1) {
        //         System.out.println("Coordinates... w: " + nextCoord[0] + " h: " + nextCoord[1]);


        //         int cellPosition = board.squareId(nextCoord[0], nextCoord[1]);
        //         System.out.println("Cell position: " + cellPosition);
        //         System.out.println("Cell status: " + board.isMissing(nextCoord[0], nextCoord[1]));

        //         break;
        //     } else {
        //         System.out.println("will cause error at: " + nextCoord[0] + " , " + nextCoord[1]);
        //         choice++;
        //     }
            
        // }

        // // Do something
        // if(nextCoord[0] > -1) {
            

        //     choice = 1;
        //     moveKnightRecur(visited, board, nextCoord[0], nextCoord[1]);
        // }
    }

    private Board moveKnight(Board board, int w, int h) {
        boolean[] visited = new boolean[board.getWidth() * board.getHeight()];

        moveKnightRecur(visited, board, w, h);

        return board;
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


        /*
            === STEPS ===
            1. Find non-missing square
            2. Do the movement
            3. Check if the square IS MISSING
                - if isMissing is FALSE
                    - Add to squareId -> visited[]
                    - Change isMissing -> TRUE

                - if isMissing is TRUE
                    - continue
        */

         // STEP 1
        int[] startPosition = pointStart(board);
        // int w = startPosition[0];
        // int h = startPosition[1];

        // STEP 2
        moveKnight(board, w, h);
        
        return null;
    }
}
