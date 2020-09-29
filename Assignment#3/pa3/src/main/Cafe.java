/*
 * Name: 
 * Student ID #: 
 */

/* 
 * Do NOT import any additional packages/classes.
 * If you (un)intentionally use some additional packages/classes we did not
 * provide, you may receive a 0 for the homework.
 */

public final class Cafe implements ICafe {
    /*
    * Use some variables for your implementation.
    */

    public Cafe() {
        /*
        * Constructor
        * This function is an initializer for this class.
        */
    }

    @Override
    public void arrive(int Id, int arrivaltime, int coffee) {
        /*
        * Function input:
        *  + Id: Students Id
        *  + arrivaltime: Students arrival time
        *  + coffee: Time needed to prepare the coffee
        *
        * Job:
        *  Save the information to use later.
        */
    }

    @Override
    public int serve() {
        /**
         * Function input: Nothing
         * 
         * Job:
         *  Serve the coffee to a strudent when ready. Return the student's Id.
         */
        return -1;
    }

    @Override
    public int stat() {
        /**
         * Function input: Nothing
         * 
         * Job:
         *  Calculate the sum of the waiting time of the served students.
         */
        return -1;
    }
}
