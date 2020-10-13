/*
 * Name: Young Woo Ju
 * Student ID #: 2014122074
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

    // Using for queue
    private Stack<Node<Integer>> stack;
    private Node<Node<Integer>> stackBottom;
    private Node<Node<Integer>> stackBottomTemp;

    // Total time wait for coffee to be served
    private int timeTotalWait = 0;

    // Order line array
    private Node<Integer>[] order = new Node[2];
    private int timeOne = 0;
    private int timeTwo = 0;

    // Simulation tmie
    private int simulationTime = 0;

    public Cafe() {
        /*
        * Constructor
        * This function is an initializer for this class.
        */
        this.stack = new Stack<Node<Integer>>();
    }

    /*
     * Customer information formed in Node of Nodes
     */
    private Node<Integer> createNode(int Id, int arrivaltime, int coffee) {
        // Create nodes
        Node<Integer> nodeId = new Node(Id);
        Node<Integer> nodeArrivalTime = new Node(arrivaltime);
        Node<Integer> nodeCoffeTime = new Node(coffee);
        
        // Set nodes
        nodeArrivalTime.setNext(nodeCoffeTime);
        nodeId.setNext(nodeArrivalTime);

        return nodeId;
    }

    /*
     * Gets student ID from a customer information node
     */
    private Node<Integer> getStudentId(Node<Integer> infoNode) {
        return infoNode;
    }

    /*
     * Gets arrival time from a customer information node
     */
    private Node<Integer> getArrivalTime(Node<Integer> infoNode) {
        return infoNode.getNext();
    }

    /*
     * Gets coffee serve time from a customer information node
     */
    private Node<Integer> getCoffeeTime(Node<Integer> infoNode) {
        return infoNode.getNext().getNext();
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

        // Create customer node information
        Node<Integer> nodeInfo = createNode(Id, arrivaltime, coffee);

        // Put the customer information node into a node for adding into a queue
        Node<Node<Integer>> insertNode = new Node(nodeInfo);

        // Add customer to a queue
        stack.push(nodeInfo);

        // Queue
        if(stack.size() == 1) {
            stackBottomTemp = stack.top;
            stackBottom = stackBottomTemp;
        } else {
            stackBottomTemp.setNext(insertNode);
            stackBottomTemp = stackBottomTemp.getNext();
            stack.top = stackBottom;
        }
    }

    @Override
    public int serve() {
        /**
         * Function input: Nothing
         * 
         * Job:
         *  Serve the coffee to a strudent when ready. Return the student's Id.
         */

        // Output student ID
        int returnStudent = -1;

        // If queue is EMPTY and both order lines are EMPTY return -1
        if(stack.isEmpty() && order[0] == null && order[1] == null) return returnStudent;

        // Creates order node
        Node<Integer> orderOneNode = null;

        if(order[0] != null) {
            orderOneNode = order[0];
        }
        
        // If order line 1 is EMPTY and queue IS NOT EMPTY -> poll customer and assign to 1st order line
        // O(1)
        if(order[0] == null && !stack.isEmpty()) {
            orderOneNode = stack.pop();
            stackBottom = stackBottom.getNext();
            order[0] = orderOneNode;
        }

        // If order line 2 is EMPTY and queue IS NOT EMPTY -> poll customer and assign to 2nd order line
        //O(1)
        if(order[1] == null && !stack.isEmpty()) {
            // Remove customer from queue
            Node<Integer> orderTwoNode = stack.pop();

            // Change queue peek to next customer
            stackBottom = stackBottom.getNext();

            // Get arrival time for polled customer
            int orderTwoTime = getArrivalTime(orderTwoNode).getValue();

            // If arrival time IS GREATER simulation time
            // UPDATE simulation time and 1st order coffee serve time
            if(orderTwoTime > simulationTime) {
                int timeDifference = orderTwoTime - simulationTime;

                // Update difference to order one
                getCoffeeTime(orderOneNode).setValue(getCoffeeTime(orderOneNode).getValue()-timeDifference);
                timeOne = timeDifference;
                simulationTime = timeDifference;
            } else {
                int timeDifference = simulationTime - orderTwoTime;
                timeTwo = timeDifference;
            }

            // Assign second customer to second order line
            order[1] = orderTwoNode;
        }

        // Simulate until coffee is served -> until return student ID is not -1
        // O(coffee time) = O(1)
        while(returnStudent == -1) {

            // Simulate order 1
            if(order[0] != null) {
                getCoffeeTime(order[0]).setValue(getCoffeeTime(order[0]).getValue()-1);
                if(getCoffeeTime(order[0]).getValue() >= 0) {
                    timeOne++;
                }
            }

            // Simulate order 2
            if(order[1] != null) {
                getCoffeeTime(order[1]).setValue(getCoffeeTime(order[1]).getValue()-1);
                if(getCoffeeTime(order[1]).getValue() >= 0) {
                    timeTwo++;
                }
            }

            // Check order one
            // If coffee serve time is <= 0
            // UPDATE student ID &
            //      IF order two exist, move it to order one
            if(order[0] != null && getCoffeeTime(order[0]).getValue() <= 0) {
                timeTotalWait += timeOne;
                timeOne = 0;
                returnStudent = getStudentId(order[0]).getValue();

                if(order[1] != null) {
                    order[0] = order[1];
                    timeOne = timeTwo;

                    order[1] = null;
                    timeTwo = 0;
                } else {
                    order[0] = null;
                }
            }

            if(order[1] != null && getCoffeeTime(order[1]).getValue() <= 0) {
                timeTotalWait += timeTwo;
                timeTwo = 0;
                returnStudent = getStudentId(order[1]).getValue();
                order[1] = null;        
            }

            // Increase simulation time
            simulationTime++;
        }

        return returnStudent;
    }

    @Override
    public int stat() {
        /**
         * Function input: Nothing
         * 
         * Job:
         *  Calculate the sum of the waiting time of the served students.
         */
        return timeTotalWait;
    }
}
