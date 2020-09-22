/*
 * Name: 
 * Student ID #: 
 */

/* 
 * Do NOT import any additional packages/classes.
 * If you (un)intentionally use some additional packages/classes we did not
 * provide, you may receive a 0 for the homework.
 */

public final class RRScheduler implements IRRScheduler {
    /*
     * Add some variables you will use.
     */

    @Override
    public void insert(int id) {
        /*
        * Function input:
        *  + id: the job id to insert
        *
        * Job:
        *  Insert the job at the back of the scheduler.
        */

    }

    @Override
    public void done() {
        /*
        * Function input: Nothing
        *
        * Job:
        *  One time segment passes and the job processed is deleted
        */

    }

    @Override
    public void timeflow(int n) {
        /*
        * Function input:
        *  + n: An integer.
        *
        * Job:
        *  Simulate n time segments.
        */

    }

    @Override
    public void changeDirection() {
        /*
        * Function input: Nothing
        *
        * Job:
        *  Change the direction of the scheduler.
        */

    }

    @Override
    public int currentJob() throws IllegalStateException {
        /*
        * Function input: Nothing
        *
        * Job:
        *  Return the current job.
        */
        return 0;
    }
}
