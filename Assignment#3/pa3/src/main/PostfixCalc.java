/*
* Name: Young Woo Ju
* Student ID #: 2014122074
*/

/*
* Do NOT import any additional packages/classes.
* If you (un)intentionally use some additional packages/classes we did not
* provide, you may receive a 0 for the homework.
*/

public class PostfixCalc implements ICalc {
    public IStack<Integer> stack;
    /* Use some variables for your implementation. */

    public PostfixCalc() {
        this.stack = new Stack<>();
        /*
        * Constructor
        */
    }

    @Override
    public int evaluate(String expression) {
        /*
        * Function input:
        *  + expression: A postfix expression.
        *
        * Job:
        *  Return the value of the given expression.
        *
        * The postfix expression is a valid expression with
        * length of at least 1.
        * There are +(addition), -(subtraction) and *(multiplication)
        * operators and only non-negative integers, seperated with
        * a single space symbol.
        */

        // Separate space
        String[] stringArr = expression.split(" ");

        for(int i = 0; i < stringArr.length; i++) {
            try {
                // Convert string to integer
                int pushVal = Integer.parseInt(stringArr[i]);
                stack.push(pushVal);

            } catch(NumberFormatException e) {
                // If string is an operator, pop 2 values
                int popVal1 = stack.pop();
                int popVal2 = stack.pop();

                // Do calculation
                switch(stringArr[i]) {
                    case "*": popVal2 *= popVal1;
                    break;
                    case "+": popVal2 += popVal1;
                    break;
                    case "-": popVal2 = popVal2 - popVal1;
                    break;
                }

                // Push back the result to stack
                stack.push(popVal2);
            }
        }

        return stack.pop();
    }
}