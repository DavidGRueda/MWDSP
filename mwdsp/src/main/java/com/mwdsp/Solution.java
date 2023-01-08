package com.mwdsp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Solution implements Cloneable{

    // Variables
    private Instance instance;
    private int totalWeight;                // Total weight of the solution. To be minimized
    private int totalNodeCount;             // Total node count of the problem instance
    private int numDomNodes;                // Number of dominated nodes of the solution: [0, 1, 2... totalNodes]
    private int[] domNodes;                 // Number of dominations over each of the nodes
    private Set<Integer> selectedNodes;     // Selected nodes of the solution
    private Set<Integer> notSelectedNodes;  // Not selected nodes of the solution
    private int[] numConnections;           // Establish how many connections has each node to non-dom. nodes
    private int[] numConnectionsRO;         // Establish how many connections has each node to non-dom. nodes (read-only)

    // Constructor
    public Solution(Instance instance) {
        this.instance = instance;
        totalWeight = 0;
        totalNodeCount = instance.getNodeCount();
        numDomNodes = 0;
        domNodes = new int[totalNodeCount];
        selectedNodes = new HashSet<>();
        notSelectedNodes = this.instance.getAllNodesSet();
        numConnections = instance.getNumConnections();
        numConnectionsRO = instance.getNumConnectionsRO();
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    public int getTotalNodeCount() {
        return totalNodeCount;
    }

    public Set<Integer> getSelectedNodes(){
        return this.selectedNodes;
    }

    public Set<Integer> getNotSelectedNodes(){
        return this.notSelectedNodes;
    }

    public int getNodeWeight(int node){
        return this.instance.getWeight(node);
    }

    /**
     * Calculates the total weight of a node set.
     * @param nodes - Nodes which total weight is going to be calculated.
     * @return Total weight of the param nodes.
     */
    public int getNodesWeight(Set<Integer> nodes){
        int weight = 0;

        for (Integer node : nodes) {
            weight += this.instance.getWeight(node);
        }

        return weight;
    }

    public void setSelectedNodes(Set<Integer> selNodes){
        Set<Integer> aux = new HashSet<>();
        aux.addAll(selNodes);
        this.selectedNodes = aux;
    }

    public void setNotSelectedNodes(Set<Integer> nonSelNodes){
        Set<Integer> aux = new HashSet<>();
        aux.addAll(nonSelNodes);
        this.notSelectedNodes = aux;
    }

    public void printSolution() {
        System.out.println("Total Weight: " + totalWeight);

        // System.out.println("Selected nodes: ");
        // for (Integer selectedNode : selectedNodes) {
        //     System.out.print((selectedNode + 1) + " ");
        // }
        // System.out.println("\n");

        // System.out.println("Not Selected nodes: ");
        // for (Integer notSelectedNode : notSelectedNodes) {
        //     System.out.print((notSelectedNode + 1) + " ");
        // }
        // System.out.println("\n");

        // System.out.print("Number of dominations: \n[");
        // for (int i = 0; i < domNodes.length - 1; i++) {
        //     System.out.print(domNodes[i] + ", ");
        // }
        // System.out.println(domNodes[domNodes.length - 1] + "]\n");
    }

    public Boolean isFeasible() {
        return numDomNodes == totalNodeCount;
    }

    public Boolean isDominated(int node) {
        return domNodes[node] >= 1;
    }

    public Boolean isSelected(int node) {
       return selectedNodes.contains(node);
    }

    /**
     * Adds a node 'SN' (Selected Node) to the solution. Adds its weight to the total weight and decreases the number of
     * connections to non-dominated nodes of the 'SN' adjacent nodes. 
     * @param node - Node that is going to be added to the solution. 
     */
    public void add(int node) {
        boolean addedNodeWasDominated = true;        

        selectedNodes.add(node);
        notSelectedNodes.remove(node);
        totalWeight += instance.getWeight(node);

        if (domNodes[node] == 0) {
            numDomNodes++;
            addedNodeWasDominated = false;
            numConnections[node]--;
        }
        domNodes[node]++;

        decreaseAdjNodesConnections(node, addedNodeWasDominated);
    }

    /**
     * Decreases the number of connections to non-dominated nodes for every adjacent node of the added node. 
     * If the adjacent node was dominated when adding the node, decreases the adj node connections too.  
     * @param addedNode - Node that was added to the solution. 
     * @param addedNodeWasDominated - True if the added node was dominated before adding it. False otherwise.
     */
    private void decreaseAdjNodesConnections(int addedNode, boolean addedNodeWasDominated) {
        ArrayList<Integer> adjNodes = instance.getAdjNodesList(addedNode);

        for (Integer adjNode : adjNodes) {
            if(!addedNodeWasDominated)
                numConnections[adjNode]--;
            
            if (domNodes[adjNode] == 0) {
                numDomNodes++;
                decreaseNodeConnections(adjNode);
            }            
            domNodes[adjNode]++;
        }
    }

    /**
     * Decreases the number of connections to non-dominated nodes of a node and its adjacent nodes.
     * @param node - Node which connections and adjacent node connections must be decreased.
     */
    private void decreaseNodeConnections(int node) {
        ArrayList<Integer> adjNodes = instance.getAdjNodesList(node);

        numConnections[node]--;
        for (Integer adjNode : adjNodes) {
            numConnections[adjNode]--;
        }
    }
    
    /**
     * Removes a node 'SN' (Selected Node) from the solution. Removes its weight to the total weight. This method does 
     * not increase the number of connections to non-dominated nodes of the 'SN' adjacent nodes. 
     * @param node - Node that needs to be removed.
     */
    public void remove(int node){
        ArrayList<Integer> adjNodes = instance.getAdjNodesList(node);

        selectedNodes.remove(node);
        notSelectedNodes.add(node);
        domNodes[node]--;
        totalWeight -= instance.getWeight(node);

        for (Integer adjNode : adjNodes) {
            domNodes[adjNode]--;
        }
    }

    /**
     * Removes a node 'SN' (Selected Node) from the solution. Removes its weight to the total weight and increases the 
     * number of connections to non-dominated nodes of the 'SN' adjacent nodes.
     * @param node to be removed from the solution.
     */
    public void removeAndUpdateConnections(int node){
        boolean removedNodeCovered = true;

        notSelectedNodes.add(node);                 // Should be removed from 'selectedNodes' outside this function.
        totalWeight -= instance.getWeight(node);
        domNodes[node]--;

        if(domNodes[node] == 0){
            numDomNodes--;
            removedNodeCovered = false;
            numConnections[node]++;
        }

        increaseAdjNodeConnections(node, removedNodeCovered);
    }

    /**
     * Increases the number of connections to non-dominated nodes for every adjacent node of the removed node. If the 
     * adjacent node was also uncovered when removing the node, increases the adj node connections too. 
     * @param removedNode - Node that was removed from the solution. 
     * @param removedNodeCovered - True if the removed node is still covered when removing it. False otherwise. 
     */
    private void increaseAdjNodeConnections(int removedNode, boolean removedNodeCovered){
        ArrayList<Integer> adjNodes = instance.getAdjNodesList(removedNode);

        for (Integer adjNode : adjNodes) {
            domNodes[adjNode]--;

            if(!removedNodeCovered)
                numConnections[adjNode]++;

            if(domNodes[adjNode] == 0){
                numDomNodes--;
                this.increaseNodeConnections(adjNode);
            }            
        }        
    }

    /**
     * Increases the number of connections to non-dominated nodes of a node and its adjacent nodes.
     * @param node - Node which connections and adjacent node connections must be increased.
     */
    private void increaseNodeConnections(int node){
        ArrayList<Integer> adjNodes = instance.getAdjNodesList(node);
        numConnections[node]++;

        for (Integer adjNode : adjNodes) {
            numConnections[adjNode]++;
        }
    }

    /**
     * Calculate factor to add the best node to a greedy-built solution.
     * @param node - Node which factor needs to be calculated. 
     * @return Calculated greedy factor of the node. 
     */
    public double calculateGreedyFactor(int node) {
        return (double) (numConnections[node] + 1) / instance.getWeight(node);
    }

    /**
     * Calculate factor to remove the worst node from a solution in a greedy way. 
     * @param node - Node which factor needs to be calculated. 
     * @return Calculated destroy factor of the node. 
     */
    public double calculateDestroyFactor(int node) {
        return (double) (numConnectionsRO[node] + 1) / instance.getWeight(node);
    }

    /**
     * Deletes the 'CN' (Candidate Nodes) that may be added at the start of the solution and could be purged without 
     * leaving non-dominated nodes. This method should be only called when a solution is already built.
     */
    public void purgeSolution(){
        boolean allNodesCovered;                            // True if all adjacent nodes are covered if 'CN' was purged
        ArrayList<Integer> candNodeAdjNodes;                // Adjacent nodes of the 'CN'
        Set <Integer> finalSelectedNodes = new HashSet<>(); // Final selected nodes
        Stack<Integer> adjNodesCovered;                     // Adjacent nodes that would be dominated if the 'CN' was purged

        for (Integer candNode : selectedNodes) {
            int auxPointer = 0;
            candNodeAdjNodes = this.instance.getAdjNodesList(candNode);
            allNodesCovered = true;
            adjNodesCovered = new Stack<>();

            // Cand node should also be covered by other node if purged. 
            if((domNodes[candNode] - 1) == 0){
                allNodesCovered = false;
            } else {
                adjNodesCovered.push(candNode);
            }

            // Check if all 'CN' adjacent nodes would be covered if it was purged.
            while(auxPointer < candNodeAdjNodes.size() && allNodesCovered){
                int adjNode = candNodeAdjNodes.get(auxPointer);

                // If purging the node is going to leave the adjacent node non-dominated, do not purge the CN.
                if((domNodes[adjNode] - 1) == 0){
                    allNodesCovered = false;
                } else {
                    adjNodesCovered.push(adjNode);
                }
                
                auxPointer++;
            }

            // Purge the node if its adjacent nodes are going to be covered. Else, add it to final selected nodes set.
            if(allNodesCovered){
                totalWeight -= this.instance.getWeight(candNode);
                notSelectedNodes.add(candNode);

                int elements = adjNodesCovered.size();
                for (int i = 0; i < elements; i++) {
                    domNodes[adjNodesCovered.pop()]--;
                }
            } else {
                finalSelectedNodes.add(candNode);
            }
        }
        // Set selected nodes to all the nodes that couldn't be purged.
        selectedNodes = finalSelectedNodes;
    }

    /**
     * Returns the nodes that would be uncovered if a node was removed.
     * @param node - Node that would be removed.
     * @return Node set containing non-dominated nodes if the node was removed.
     */
    public Set<Integer> getNotDomNodesIfRemoved(int node){
        ArrayList<Integer> adjNodes = this.instance.getAdjNodesList(node);
        Set<Integer> notDomNodesIfRemoved = new HashSet<>();

        // Check if the node to be removed would be uncovered.
        if((domNodes[node] - 1) == 0){
            notDomNodesIfRemoved.add(node);
        }

        // Check if the adjacent nodes would be uncovered if the node was removed.
        for (Integer adjNode : adjNodes) {
            if((domNodes[adjNode] - 1) == 0){
                notDomNodesIfRemoved.add(adjNode);
            }
        }

        return notDomNodesIfRemoved;
    }

    /**
     * Returns the nodes that would be uncovered if a set of nodes were removed.
     * @param nodes - Nodes that would be removed.
     * @return Node set containing non-dominated nodes if the nodes were removed.
     */
    public Set<Integer> getNotDomNodesIfRemoved(Set<Integer> nodes){
        Set<Integer> notDomNodesIfRemoved = new HashSet<>();

        // If there are multiple dominations over the same node from the nodes to be removed, the structure saves how 
        // many dominations there are to add them or not to notDomNodesIfRemoved
        Map<Integer, Integer> dominationsCountMap = new HashMap<>();
        int domsToRemove;  // Dominations that should be sub to know if a node would be uncovered if nodes were removed

        for (Integer node : nodes) {
            // Check if the removed node would be uncovered
            domsToRemove = dominationsCountMap.getOrDefault(node, 0) + 1;

            if((domNodes[node] - domsToRemove) == 0){
                notDomNodesIfRemoved.add(node);
            }
            
            dominationsCountMap.put(node, domsToRemove);

            // Check if the adjacent nodes would be uncovered if the nodes were removed
            ArrayList<Integer> adjNodes = this.instance.getAdjNodesList(node);
            for (Integer adjNode : adjNodes) {
                domsToRemove = dominationsCountMap.getOrDefault(adjNode, 0) + 1;
                
                if((domNodes[adjNode] - domsToRemove) == 0){
                    notDomNodesIfRemoved.add(adjNode);
                }
                
                dominationsCountMap.put(adjNode, domsToRemove);
            }

        }

        return notDomNodesIfRemoved;
    }

    /**
     * Removes one node and adds a set of nodes
     * @param nodeToRemove - Node to be removed.
     * @param nodesToAdd - Nodes to be added.
     */
    public void swapNodes(int nodeToRemove, Set<Integer> nodesToAdd){
        for (Integer nodeToAdd : nodesToAdd) {
            this.add(nodeToAdd);
        }

        this.remove(nodeToRemove);
    }

    /**
     * Removes a set of nodes and adds a set of nodes
     * @param nodesToRemove - Nodes to be removed.
     * @param nodesToAdd - Nodes to be added.
     */
    public void swapNodes(Set<Integer> nodesToRemove, Set<Integer> nodesToAdd){
        for (Integer nodeToAdd : nodesToAdd) {
            this.add(nodeToAdd);
        }

        for (Integer nodeToRemove : nodesToRemove) {            
            this.remove(nodeToRemove);
        }
    }
    
    /**
     * Returns the nodes that would be covered (from a node set) if a node was added to the solution.
     * @param node - Node that would be added.
     * @param nodesToCover - Nodes that should be covered.
     * @return Node set containing the nodes from nodesToCover that would be covered if the node was added.
     */
    public Set<Integer> getNodesCoveredIfAdded(int node, Set<Integer> nodesToCover){
        Set<Integer> nodesCovered = new HashSet<>();
        ArrayList<Integer> adjNodes = this.instance.getAdjNodesList(node);

        for (Integer adjNode : adjNodes) {
            if(nodesToCover.contains(adjNode)){
                nodesCovered.add(adjNode);
            }
        }

        return nodesCovered;
    }

    /**
     * Checks if a solution is feasible and well-built.
     */
    public void checkIfFeasible(){
        int[] dominatedNodes = new int[totalNodeCount];
        int totalDomNodes = 0;
        int totalWeight = 0;

        for(int sn: selectedNodes){
            if(dominatedNodes[sn] == 0){
                dominatedNodes[sn] = 1;
                totalDomNodes++;
            }

            ArrayList<Integer> adjNodes = this.instance.getAdjNodesList(sn);
            for (int adjNode : adjNodes) {
                if(dominatedNodes[adjNode] == 0){
                    dominatedNodes[adjNode] = 1;
                    totalDomNodes++;
                }
            }
            totalWeight += this.instance.getWeight(sn);
        }

        Set<Integer> a = new HashSet<>();
        for (int i : dominatedNodes) {
            a.add(i);
        }
        System.out.println("Are all nodes dominated? : " + (a.size() == 1));
        System.out.println("Calc Weight: " + totalWeight + " --- Solution Weight: " + this.totalWeight );
        System.out.println("Calc Number of Dom nodes: " + totalDomNodes + " --- Total Nodes: " + this.totalNodeCount);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
