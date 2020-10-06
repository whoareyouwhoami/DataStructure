/*
 * Name: Young Woo Ju
 * Student ID #: 2014122074
 */

/* 
 * Do NOT import any additional packages/classes.
 * If you (un)intentionally use some additional packages/classes we did not
 * provide, you may receive a 0 for the homework.
 */

public final class LISP implements ILISP {
    public IStack<Character> stack = new Stack<Character>();
    /*
    * Use some variables for your implementation.
    */
    private char[] bracketForm = {'(', ')', '[', ']', '{', '}'};


    public LISP() {
        /*
        * Constructor
        * This function is an initializer for this class.
        */
        this.stack = stack;
    }

    private int getBracketIndex(char bracket) {
        for(int i = 0; i < 6; i++) {
            if(bracketForm[i] == bracket) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean checkBracketBalance(String expression) {
        /*
        * Function input:
        *  + expression: A expression containing brackets.
        *
        * Job:
        *  Return if the four conditions meet:
        *   1. A left bracket and a right bracket of the same type consist a pair.
        *   2. There are no two pairs containing the same bracket.
        *   3. The left bracket must precede the corresponding right bracket.
        *   4. Pairs of different types of brackets must not intersect each other.
        */

        // Convert string to char
        char[] charArr = expression.toCharArray();

        for(int i = charArr.length - 1; i >= 0; i--) {
            char bracket = charArr[i];
            int bracketIndex = getBracketIndex(bracket);
            
            if(bracketIndex % 2 == 0) { // Open bracket
                // Pop and compare
                try {
                    char charCompare = stack.pop();
                    int compareIndex = getBracketIndex(charCompare);

                    if(compareIndex - bracketIndex != 1) {
                        return false;
                    }
                } catch(IllegalStateException e) {
                    return false;
                }

            } else if(bracketIndex % 2 == 1) { // Closed bracket
                // Push to stack
                stack.push(bracket);
            }
        }
        if(stack.isEmpty()) {
            return true;
        }

        return false;
    }
}
