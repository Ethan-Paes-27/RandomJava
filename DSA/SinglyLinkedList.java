package DSA;

import FactorProductOf2Primes.PollardRhoAlgorithm;

public class SinglyLinkedList {
    private Node head;

    public SinglyLinkedList() {
        head = null;
    }

    public SinglyLinkedList(int n) {
        head = new Node(n);
    }

    public SinglyLinkedList(Node n) {
        head = n;
    }

    public void insertAtEnd(int data) {
        Node add = new Node(data);

        if (head == null) {
            head = add;
        } else {
            Node temp = head;

            while (temp.next != null) {
                temp = temp.next;
            }
       
            temp.next = add;
        }
    }

    public void deleteByValue(int data) {
        if (head == null) {
            throw new IllegalCallerException("Head node is null!");
        }

        if (head.data == data) {
            if (head.next != null) {
                head = head.next;
            }
            else head = null;
            return;
        }

        Node temp = head;

        while (temp.next != null) {
            if (temp.next.data == data) {
                if (temp.next.next != null) {
                    temp.next = temp.next.next;
                    return;
                }
            }
            temp = temp.next;
        }

        System.out.println(data + " not found in the list");
    }

    public void printList() {
        String toPrint = "";

        Node temp = head;

        if (head == null) {
            System.out.println("null");
        }

        while (temp != null) {
            toPrint += temp.data + " -> ";
            temp = temp.next;
        }

        toPrint += "null";

        System.out.println(toPrint);
    }

    public Node findNode(int data) {
        if (head == null) {
            throw new IllegalCallerException("Head node is null!");
        }

        Node temp = head;

        while (temp.data != data && temp.next != null) {
            temp = temp.next;
        }

        if (temp.next == null) {
            return null;
        }

        return temp;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public static void main(String[] args) {
        SinglyLinkedList list = new SinglyLinkedList();

        // Test 1: Check if the list is empty
        System.out.println("Test 1: Is the list empty?");
        System.out.println("Expected: true");
        System.out.println("Actual: " + list.isEmpty());  // Expected: true

        // Insert some elements
        list.insertAtEnd(10);
        list.insertAtEnd(20);
        list.insertAtEnd(30);

        // Test 2: Check if the list is empty after inserting elements
        System.out.println("\nTest 2: Is the list empty after inserting elements?");
        System.out.println("Expected: false");
        System.out.println("Actual: " + list.isEmpty());  // Expected: false

        // Test 3: Find a node with a specific value
        System.out.println("\nTest 3: Find node with value 20");
        Node node = list.findNode(20);
        if (node != null) {
            System.out.println("Found node: " + node.data);  // Expected: Found node: 20
        } else {
            System.out.println("Node not found.");
        }

        // Test 4: Try to find a node with a value that doesn't exist
        System.out.println("\nTest 4: Find node with value 40");
        node = list.findNode(40);
        if (node != null) {
            System.out.println("Found node: " + node.data);
        } else {
            System.out.println("Node not found.");  // Expected: Node not found.
        }
    }
}

class Node {
    int data;
    Node next; // Points to the next node in the list

    // Constructor
    public Node(int data) {
        this.data = data;
        this.next = null; // Next is initially null
    }
}