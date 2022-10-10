package com.mwdsp;

import java.util.ArrayList;

public class Solution {

    // Variables
    private Instance instance;
    private int totalWeight; // Try to minimize this.
    private int totalNodes;
    private int numDomNodes;
    private int[] domNodes;
    private int[] selectedNodes;

    public Solution(Instance instance) {
        this.instance = instance;
        totalWeight = 0;
        totalNodes = instance.getNodes();
        numDomNodes = 0;
        domNodes = new int[totalNodes];
        selectedNodes = new int[totalNodes];
    }

    public void add(int node) {
        selectedNodes[node] = 1; // Add node to selected list.
        if (domNodes[node] == 0) {
            domNodes[node] = 1;
            numDomNodes++;
        }
        totalWeight += instance.getWeight(node); // Add it's weight to the totalWeight.

        ArrayList<Integer> connections = instance.getConnectionList(node); // Mark neighbours as dominated
        for (Integer neighbour : connections) {
            if (domNodes[neighbour] == 0) {
                domNodes[neighbour] = 1;
                numDomNodes++;
            }
        }
    }

    public Boolean isFeasible() {
        return numDomNodes == totalNodes;
    }

    public Boolean isDominated(int node) {
        return domNodes[node] == 1;
    }

    public Boolean isSelected(int node) {
        return selectedNodes[node] == 1;
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    public void printSolution() {
        System.out.println("Total Weight: " + totalWeight);
        System.out.println("Selected nodes: ");
        for (int i = 0; i < selectedNodes.length; i++) {
            if (selectedNodes[i] == 1) {
                /*
                 * System.out.print((i + 1) + " -> ");
                 * for (int connection : instance.getConnectionList(i)) {
                 * System.out.print(connection + " ");
                 * }
                 * System.out.println();
                 */
                System.out.print((i + 1) + " ");
            }
        }
        System.out.println("\n");
    }
}
