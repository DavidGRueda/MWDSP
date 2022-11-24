package com.mwdsp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Solution {

    // Variables
    private Instance instance;
    private int totalWeight; // Try to minimize this.
    private int totalNodes;
    private int numDomNodes;
    private int[] domNodes;
    private Set<Integer> selectedNodes;
    private int[] numConnections; // Establish how many connections has each node to not dom. nodes.

    // Constructor
    public Solution(Instance instance) {
        this.instance = instance;
        totalWeight = 0;
        totalNodes = instance.getNodes();
        numDomNodes = 0;
        domNodes = new int[totalNodes];
        selectedNodes = new HashSet<>();
        numConnections = instance.getNumConnections();
        //selectedNodesAL = new ArrayList<>();
    }

    /**
     * Adds a node 'SN' to the solution. Follows the steps:
     *      1. Gets the connection list (the nodes connected to the SN)
     *      2. Adds SN to the selectedNodes set
     *      3. If it was not already dominated, adds 1 to the number of dom nodes and sets selectedNodeWasDominated to 
     *         true, used to update the neighbours numConnections (greedy and GRASP builder)
     *      4. Adds to the total solution weight the SN weight
     * @param node that is going to be added to the solution
     */
    public void add(int node) {
        boolean selectedNodeWasDominated = false;
        ArrayList<Integer> connections = instance.getConnectionList(node);

        selectedNodes.add(node);

        if (domNodes[node] == 0) {
            numDomNodes++;
            selectedNodeWasDominated = true;
            numConnections[node]--;
        }

        domNodes[node]++;

        totalWeight += instance.getWeight(node);
        updateNeighbours(connections, selectedNodeWasDominated);
    }

    /**
     * Updates the number of connections to non-dominated nodes each of the nodes connected to the SN has.  
     * If it was dominated, the number of connections of the neighbour must be also decreased by one. Else, just update
     * the neighbour' neighbours. 
     * 
     * @param connections of the selected node that is added to the solution
     * @param selectedNodeWasDominated true if it was not dominated before. False otherwise
     */
    private void updateNeighbours(ArrayList<Integer> connections, boolean selectedNodeWasDominated) {
        if (selectedNodeWasDominated) {
            for (Integer neighbour : connections) {

                numConnections[neighbour]--;
                
                if (domNodes[neighbour] == 0) {
                    numDomNodes++;
                    updateNodeConnections(neighbour);
                }

                domNodes[neighbour]++;
            }
        } else {
            for (Integer neighbour : connections) {   

                if (domNodes[neighbour] == 0) {
                    numDomNodes++;
                    updateNodeConnections(neighbour);
                }

                domNodes[neighbour]++;
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
       //return selectedNodes[node] == 1;
       return selectedNodes.contains(node);
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    public void printSolution() {
        System.out.println("Total Weight: " + totalWeight);

        System.out.println("Selected nodes: ");
        for (Integer selectedNode : selectedNodes) {
            System.out.print((selectedNode + 1) + " ");
        }
        System.out.println("\n");

        System.out.print("Number of dominations: \n[");
        for (int i = 0; i < domNodes.length - 1; i++) {
            System.out.print(domNodes[i] + ", ");
        }
        System.out.println(domNodes[domNodes.length - 1] + "]\n");
    }

    /**
     * If the node wasn't dominated, this method is called to update the number of connections of non-dominated nodes 
     * of each of the nodes connected to the param node (and the param node connections too)
     * @param node which connections must be updated
     */
    private void updateNodeConnections(int node) {
        numConnections[node]--;
        ArrayList<Integer> connections = instance.getConnectionList(node);
        for (Integer neighbour : connections) {
            numConnections[neighbour]--;
        }
    }

    /**
     * Deletes the nodes that may be added at the start of the solution and could be purged without leaving non-dominated
     * nodes. Only call this method when the builder is finished.
     * @return
     */
    public void purgeSolution(){ 
        Set <Integer> finalSelectedNodes = new HashSet<>(); // Saves the state of final selected nodes.
        ArrayList<Integer> candNodeConnections;
        Stack<Integer> nodeConnectionsCovered; // Saves the node connections that would be dominated if the CN was purged
        boolean allNodesCovered;
        int auxPointer;
        int nodeCon;

        for (Integer candNode : selectedNodes) {
            candNodeConnections = this.instance.getConnectionList(candNode);
            candNodeConnections.add(candNode);  // Cand node should also be covered by other node if purged.
            auxPointer = 0;
            allNodesCovered = true;
            nodeConnectionsCovered = new Stack<>();

            while(auxPointer < candNodeConnections.size() && allNodesCovered){
                nodeCon = candNodeConnections.get(auxPointer);

                // If purging the node is going to leave the connection non dominated, do not purge the CN.
                if((domNodes[nodeCon] - 1) == 0){
                    allNodesCovered = false;
                } else {
                    nodeConnectionsCovered.push(nodeCon);
                }
                
                auxPointer++;
            }

            //Purge the node if it's connections are going to be covered. Else, add it to final list.
            if(allNodesCovered){
                totalWeight -= this.instance.getWeight(candNode);

                for (int i = 0; i < auxPointer; i++) {
                    domNodes[nodeConnectionsCovered.pop()]--;
                }

            } else {
                finalSelectedNodes.add(candNode);
            }
        }
        // Set selected nodes to all the nodes that couldn't be purged.
        selectedNodes = finalSelectedNodes;
    }
}
