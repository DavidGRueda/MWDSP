package com.mwdsp.localSearch;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.mwdsp.Solution;

public class LocalSearch2xNBI implements LocalSearch{
    public Solution execute(Solution sol){
        boolean changeDone = true; // Used to mark if a 2xN switch has been done / Loop should continue.
        int selectedNodesWeight;   // Weight that can't be surpassed when adding new nodes
        int addedNodesWeight; // Weight from the nodes that would be added to the solution
        Set<Integer> selectedNodes;
        Set<Integer> notSelectedNodes; // Passed as reference. Can't call the remove() method.
        Set<Integer> notDomNodesIfRemoved;  // Get nodes that would not be covered if we removed the sel. nodes
        Set<Integer> nodesToAdd;   // Nodes that would be added if the selected node was removed
        Set<Integer> nodesToRemove;   // Nodes that would be removed from selected nodes.

        double bestFactor;    // Used to determine best notSelectedNode in each it.
        double localFactor;
        Set<Integer> bestNodeCover;   // Best nodes that would be added if the selected node was removed
        Set<Integer> localNodeCover;   
        int bestWeight;                // Best weight of notSelectedNode that would be added to the solution.
        int localWeight;      
        int localNodeCoverWeight;
        int bestNode;                  // Best not selected node
        boolean realNodeSelected;   // Checks if a real 'notSelNode' was found. Else, don't add it and break.

        // Variables to create BI
        Set<Integer> bestNodesToRemove;
        Set<Integer> bestNodesToAdd;
        double localDifference;
        double bestDifference;


        while(changeDone){
            changeDone = false;
            selectedNodes = sol.getSelectedNodes();
            notSelectedNodes = sol.getNotSelectedNodes();

            bestNodesToRemove = new HashSet<>();
            bestNodesToAdd = new HashSet<>();
            bestDifference = Double.POSITIVE_INFINITY;

            // Create iterator to select two nodes. Only one will be selected in case that it's the last one and there  
            // are an odd number of elements in the 'selectedNodes'.
            Iterator<Integer> it = selectedNodes.iterator();


            //for (Integer node : selectedNodes) {
            while(it.hasNext()){
                nodesToRemove = new HashSet<>();
                nodesToRemove.add(it.next());
                if(it.hasNext()){
                    nodesToRemove.add(it.next());
                }

                selectedNodesWeight = sol.getNodesWeight(nodesToRemove);
                notDomNodesIfRemoved = sol.getNotDomNodesIfRemoved(nodesToRemove);
                addedNodesWeight = 0;
                nodesToAdd = new HashSet<>();
                realNodeSelected = true;

                while(notDomNodesIfRemoved.size() > 0 && addedNodesWeight < selectedNodesWeight && realNodeSelected){ 
                    bestFactor = Double.POSITIVE_INFINITY;                
                    bestWeight = 0;    
                    bestNode = -1;  
                    bestNodeCover = new HashSet<>();

                    for (Integer notSelNode : notSelectedNodes) {
                        if(!nodesToAdd.contains(notSelNode)){
                            localNodeCover = sol.getNodesCovered(notSelNode, notDomNodesIfRemoved); 
                            localWeight = sol.getNodeWeight(notSelNode);  
                            localNodeCoverWeight = sol.getNodeCoverWeight(localNodeCover);

                            // Calculate factor
                            localFactor = Double.POSITIVE_INFINITY;
                            if(localNodeCoverWeight != 0){
                                localFactor = localWeight / (double) localNodeCoverWeight;
                            }
                            

                            // Update best if needed
                            if(localFactor < bestFactor){
                                bestFactor = localFactor;
                                bestNodeCover = localNodeCover;
                                bestWeight = localWeight;
                                bestNode = notSelNode;
                            }
                        }
                    }

                    // Update the nodesToAdd, addedNodesWeight and remove nodes from notDomNodesIfRemoved
                    // Only if one notSelectedNode is selected! Else break! 
                    if(bestNode != -1){
                        nodesToAdd.add(bestNode);
                        addedNodesWeight += bestWeight;

                        for (Integer nodeCovered : bestNodeCover) {
                            notDomNodesIfRemoved.remove(nodeCovered);
                        }
                    } else {
                        realNodeSelected = false;
                    }
                }

                // If a change could be done, check then if it's better than the last one.
                if(addedNodesWeight < selectedNodesWeight && realNodeSelected){

                    localDifference = selectedNodesWeight - addedNodesWeight;

                    if(localDifference < bestDifference){
                        bestNodesToRemove = nodesToRemove;
                        bestNodesToAdd = nodesToAdd;
                        changeDone = true;
                    }
                }
            }
            if(changeDone) {
                sol.swapNodes(bestNodesToRemove, bestNodesToAdd);
            }
        }

        return sol;
    }
}
