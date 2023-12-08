package day8;

import java.util.ArrayList;
import java.util.List;

public class DesertMap {

    List<DesertNode> desertNodes = new ArrayList<>();
    String routeString;
    public void parse(List<String> directions) {
        for (String line : directions) {
            if (line.contains("=")) {
                // This is a node
                String name = line.substring(0, 3);
                String left = line.substring(7, 10);
                String right = line.substring(12, 15);
                desertNodes.add(new DesertNode(name, left, right));
            } else if (line.contains("L") && line.contains("R")) {
                routeString = line;
            }
        }

        // part 1
        DesertNode currentNode = getNode("AAA");
        DesertNode destinationNode = getNode("ZZZ");
        findStepsForRoute(currentNode, List.of(destinationNode));

        // part 2
        List<DesertNode> startingNodes = new ArrayList<>();
        List<DesertNode> destinationNodes = new ArrayList<>();
        // all nodes ending with A are starting nodes
        System.out.println("Starting Nodes: ");
        for (DesertNode node : desertNodes) {
            if (node.name.endsWith("A")) {
                System.out.println(node);
                startingNodes.add(node);
            }
        }

        System.out.println("Destination Nodes:");
        for (DesertNode node : desertNodes) {
            if (node.name.endsWith("Z")) {
                System.out.println(node);
                destinationNodes.add(node);
            }
        }

        long[] stepArray = new long[6];
        int i = 0;
        for (DesertNode node : startingNodes) {
            stepArray[i] = findStepsForRoute(node, destinationNodes);
            i++;
        }

        System.out.println(findLCM(stepArray));

    }

    private Long findStepsForRoute(DesertNode currentNode, List<DesertNode> destinationNodes) {
        List<String> destinationNodeNames = new ArrayList<>();
        for (DesertNode node : destinationNodes) {
            destinationNodeNames.add(node.name);
        }
        char[] route = routeString.toCharArray();
        Long steps = 1L;
        boolean foundZZZ = false;
        while(!foundZZZ) {
            for (int i = 0; i < route.length; i++) {

                char d = route[i];
                if (d == 'L') {
                    currentNode = getNode(currentNode.left);
                } else if (d == 'R') {
                    currentNode = getNode(currentNode.right);
                }

                if (destinationNodeNames.contains(currentNode.name)) {
                    System.out.println("Found destination " + currentNode.name + " in " + steps + " steps.");
                    foundZZZ = true;
                    break;
                }
                steps++;
            }
        }
        return steps;
    }

    private DesertNode getNode(String name) {
        for (DesertNode node : desertNodes) {
            if (name.equals(node.name)) {
                return node;
            }
        }
        return null;
    }

    // Function to find the GCD of two numbers
    public static long gcd(long a, long b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    // Function to find the LCM of an array of numbers
    public static long findLCM(long[] arr) {
        long lcm = arr[0];
        for (int i = 1; i < arr.length; i++) {
            long currentNumber = arr[i];
            lcm = (lcm * currentNumber) / gcd(lcm, currentNumber);
        }
        return lcm;
    }

    class DesertNode {
        final String name;
        final String left;
        final String right;

        DesertNode(String name, String left, String right) {
            this.name = name;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return (this.name + ": " + this.left + ", " + this.right);
        }
    }
}
