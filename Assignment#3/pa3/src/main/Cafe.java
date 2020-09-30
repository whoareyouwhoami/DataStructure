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

    private Stack<Node<Integer>> stack;
    private Node<Node<Integer>> stackBottom;
    private Node<Node<Integer>> stackBottomTemp;

    private int timeSimulation = 0;
    private int servedCoffee = 0;

    private Node<Integer>[] orderLine = new Node[2];
    private int lineNum = 0;

    private int timeOne = 0;
    private int timeTwo = 0;
    

    public Cafe() {
        /*
        * Constructor
        * This function is an initializer for this class.
        */
        this.stack = new Stack<Node<Integer>>();
    }
    public void show() {
        System.out.print("\n");
        System.out.print("Queue: ");
        stack.show();
        showOrderLine();
    }

    public void showStack() {
        Node<Node<Integer>> x = stack.top;
        System.out.println("<-- QUEUE <--");
        while(x != null) {
            System.out.print("[ " + x.getValue().getValue() + " " + x.getValue().getNext().getValue() + " " + x.getValue().getNext().getNext().getValue() + " ]");
            x = x.getNext();
        }
        System.out.println("\n------------");

    }

    public void showOrderLine() {
        Node x1 = orderLine[0];
        Node x2 = orderLine[1];

        System.out.print("Order: ");
        if(orderLine[0] != null) {
            System.out.print("[ " + x1.getValue() + " " + x1.getNext().getValue() + " " + x1.getNext().getNext().getValue() + " ] ");
        }
        if(orderLine[1] != null) {
            System.out.print("[ " + x2.getValue() + " " + x2.getNext().getValue() + " " + x2.getNext().getNext().getValue() + " ]");
        }  
        System.out.println();
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

    private int getStudentId(Node<Integer> infoNode) {
        return infoNode.getValue();
    }

    private int getArrivalTime(Node<Integer> infoNode) {
        return infoNode.getNext().getValue();
    }

    private int getCoffeeTime(Node<Integer> infoNode) {
        return infoNode.getNext().getNext().getValue();
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

        if(orderLine[0] == null) {
            orderLine[0] = nodeInfo;
        } else if(orderLine[1] == null) {
            orderLine[1] = nodeInfo;
        } else {
            stack.push(nodeInfo);
            if(stackBottomTemp == null) {
                stackBottomTemp = stack.top;
                stackBottom = stackBottomTemp;
                // stack.top = bottom ?
            } else {
                stackBottomTemp.setNext(insertNode);
                stackBottomTemp = stackBottomTemp.getNext();
                stack.top = stackBottom;
            } 
        }

    }

    @Override
    public int serve() {
        showOrderLine();
        /**
         * Function input: Nothing
         * 
         * Job:
         *  Serve the coffee to a strudent when ready. Return the student's Id.
         */

        /* 
    
        IF timeOne < orderLine[1].arrivaltime:
            UPDATE timeOne = orderLine[1].arrivaltime
        

        WHILE timeOne <= orderLine[0].coffeetime OR timeTwo <= orderLine[1].coffeetime
            IF timeOne == orderLine[0].coffeetime
                UPDATE stat() + orderLine[0].coffeetime
                UPDATE orderLine[0] = orderLine[1]
                UPDATE timeOne = timeTwo
                BREAK

            IF timeTwo == orderLine[1].coffeetime
                UPDATE stat() + orderLine[1].coffeetime
                REMOVE orderLine[1]
                UPDATE timeTWO = 0
                BREAK

            
            INCREMENT timeOne & timeTwo BY 1


        IF queue IS NOT EMPTY
            UPDATE orderLine[1] = queue.pop()
            
        */
        // Final output
        int returnStudent = 0;


        /* 
        IF timeOne < orderLine[1].arrivaltime:
            UPDATE timeOne = orderLine[1].arrivaltime
        */

        if(orderLine[1] != null && timeOne < getArrivalTime(orderLine[1])) {
            timeOne = getArrivalTime(orderLine[1]);
        }
        
        /*
        WHILE timeOne <= orderLine[0].coffeetime OR timeTwo <= orderLine[1].coffeetime
            IF timeOne == orderLine[0].coffeetime
                UPDATE stat() + orderLine[0].coffeetime
                UPDATE orderLine[0] = orderLine[1]
                UPDATE timeOne = timeTwo
                BREAK

            IF timeTwo == orderLine[1].coffeetime
                UPDATE stat() + orderLine[1].coffeetime
                REMOVE orderLine[1]
                UPDATE timeTWO = 0
                BREAK

            
            INCREMENT timeOne & timeTwo BY 1
        */

        do {
            if(orderLine[0] != null && timeOne == getCoffeeTime(orderLine[0])) {
                // Update stat()
                System.out.println("SERVED student: " + getStudentId(orderLine[0]));
                orderLine[0] = orderLine[1];
                timeOne = timeTwo;

                orderLine[1] = null;
                timeTwo = 0;
                break;
            }

            if(orderLine[1] != null &&timeTwo == getCoffeeTime(orderLine[1])) {
                // Update stat()
                System.out.println("\nSERVED student: " + getStudentId(orderLine[1]));
                orderLine[1] = null;
                timeTwo = 0;
                break;
            }

            timeOne++;
            timeTwo++;
        } while((orderLine[0] != null && timeOne <= getCoffeeTime(orderLine[0])) || (orderLine[1] != null && timeTwo <= getCoffeeTime(orderLine[1])));

        /*
        IF queue IS NOT EMPTY
            UPDATE orderLine[1] = queue.pop()
        */


        if(stack.top != null) {
            orderLine[1] = stack.pop();
        } 

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
