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
    ICDLList cdl = new CDLList();
    boolean directionChange = false; // Not changed

    @Override
    public void insert(int id) {
        /*
        * Function input:
        *  + id: the job id to insert
        *
        * Job:
        *  Insert the job at the back of the scheduler.
        */
        cdl.insert(id);
    }

    @Override
    public void done() {
        /*
        * Function input: Nothing
        *
        * Job:
        *  One time segment passes and the job processed is deleted
        */
        timeflow(1);
        cdl.delete();
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
        for(int i=0; i<n; i++) {
            if(!directionChange) {
                cdl.rotateForward();
            } else {
                cdl.rotateBackward();
            }
        }
    }

    @Override
    public void changeDirection() {
        /*
        * Function input: Nothing
        *
        * Job:
        *  Change the direction of the scheduler.
        */
        directionChange = !directionChange;
        System.out.println("DIRECTION CHANGED? " + directionChange);
    }

    @Override
    public int currentJob() throws IllegalStateException {
        /*
        * Function input: Nothing
        *
        * Job:
        *  Return the current job.
        */
        System.out.println("CURRENT ID: " + cdl.getHead().getValue());
        return cdl.getHead().getValue();
    }
}
