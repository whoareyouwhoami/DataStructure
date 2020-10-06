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
    private Stack<Node<Integer>> stack;
    private Node<Node<Integer>> stackBottom;
    private Node<Node<Integer>> stackBottomTemp;

    private int timeTotalWait = 0;

    // Order array
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

    private Node<Integer> getStudentId(Node<Integer> infoNode) {
        return infoNode;
    }

    private Node<Integer> getArrivalTime(Node<Integer> infoNode) {
        return infoNode.getNext();
    }

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

        // Create Node of Node
        Node<Integer> nodeInfo = createNode(Id, arrivaltime, coffee);
        Node<Node<Integer>> insertNode = new Node(nodeInfo);

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

        if(stack.isEmpty() && order[0] == null && order[1] == null) return -1;

        Node<Integer> orderOneNode = null;
        int returnStudent = -1;

        if(order[0] != null) {
            orderOneNode = order[0];
        }
        
        // O(1)
        if(order[0] == null && !stack.isEmpty()) {
            orderOneNode = stack.pop();
            stackBottom = stackBottom.getNext();
            order[0] = orderOneNode;
        }

        //O(1)
        if(order[1] == null && !stack.isEmpty()) {
            Node<Integer> orderTwoNode = stack.pop();
            stackBottom = stackBottom.getNext();

            int orderTwoTime = getArrivalTime(orderTwoNode).getValue();
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

            order[1] = orderTwoNode;
        }

        // O(coffee time) = O(1)
        while(true) {
            if(order[0] != null) {
                getCoffeeTime(order[0]).setValue(getCoffeeTime(order[0]).getValue()-1);
                timeOne++;
            }

            if(order[1] != null) {
                getCoffeeTime(order[1]).setValue(getCoffeeTime(order[1]).getValue()-1);
                timeTwo++;
            }
            simulationTime++;

            if(order[0] != null && getCoffeeTime(order[0]).getValue() == 0) {
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

                break;
            }

            if(order[1] != null && getCoffeeTime(order[1]).getValue() == 0) {
                timeTotalWait += timeTwo;
                timeTwo = 0;
                returnStudent = getStudentId(order[1]).getValue();
                order[1] = null;
                break;                
            }
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
