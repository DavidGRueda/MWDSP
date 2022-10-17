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
    private int[] numConnections; // Establish how many connections has each node to not dom. nodes.

    public Solution(Instance instance) {
        this.instance = instance;
        totalWeight = 0;
        totalNodes = instance.getNodes();
        numDomNodes = 0;
        domNodes = new int[totalNodes];
        selectedNodes = new int[totalNodes];
        numConnections = instance.getNumConnections();
    }

    public void add(int node) {
        boolean selectedNodeWasDominated = false;
        ArrayList<Integer> connections = instance.getConnectionList(node); // Used to mark neighbours as dominated and
                                                                           // update numConnections

        selectedNodes[node] = 1; // Add node to selected list.
        if (domNodes[node] == 0) {
            domNodes[node] = 1;
            numDomNodes++;
            selectedNodeWasDominated = true;
            numConnections[node]--;
        }
        totalWeight += instance.getWeight(node); // Add it's weight to the totalWeight.
        updateNeighbours(connections, selectedNodeWasDominated);
    }

    private void updateNeighbours(ArrayList<Integer> connections, boolean selectedNodeWasDominated) {
        if (selectedNodeWasDominated) {
            for (Integer neighbour : connections) {
                numConnections[neighbour]--;
                if (domNodes[neighbour] == 0) {
                    domNodes[neighbour] = 1;
                    numDomNodes++;
                    updateNodeConnections(neighbour);
                }
            }
        } else {
            for (Integer neighbour : connections) {
                if (domNodes[neighbour] == 0) {
                    domNodes[neighbour] = 1;
                    numDomNodes++;
                    updateNodeConnections(neighbour);
                }
            }
        }
    }

    public double calculateGreedyFactor(int node) {
        return (double) (numConnections[node] + 1) / instance.getWeight(node);
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

    private void updateNodeConnections(int node) {
        numConnections[node]--;
        ArrayList<Integer> connections = instance.getConnectionList(node);
        for (Integer neighbour : connections) {
            numConnections[neighbour]--;
        }
    }
}
