package com.mwdsp.localSearch;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.mwdsp.Solution;

public class LocalSearch2xNBI implements LocalSearch{

    public Solution execute(Solution sol){

        // Variables used to determine the best alternative nodes to the selected node in each iteration
        int bestNode;                 // Best not selected alternative node to add in each iteration
        int bestWeight;               // Best weight of the best alterative node in each iteration
        double bestFactor;            // Used to determine best alternative node in each iteration
        Set<Integer> bestNodeCover;   // Best nodes that would be added if the selected node was removed
        
        // Best not selected node
        boolean changeDone; // Used to mark if a 2xN switch has been done / Loop should continue.
        boolean realNodeSelected;   // Checks if a real 'notSelNode' was found. Else, don't add it and break.
        
         // Variables used to determine the best possible node swap for every Local Search iteration
        Set<Integer> bestNodesToRemove;
        double bestDifference;
        Set<Integer> bestNodesToAdd;
        

        changeDone = true;
        while(changeDone){
            changeDone = false;
            Set<Integer> selectedNodes = sol.getSelectedNodes();
            Set<Integer> notSelectedNodes = sol.getNotSelectedNodes();

            bestNodesToRemove = new HashSet<>();
            bestNodesToAdd = new HashSet<>();
            bestDifference = Double.POSITIVE_INFINITY;

            // Create iterator to select two nodes. Only one will be selected in case that it's the last one and there  
            // is an odd number of selected nodes.
            Iterator<Integer> it = selectedNodes.iterator();


            // Check if two already selected nodes could be substituted for another one(s) not selected.
            while(it.hasNext()){
                Set<Integer> nodesToRemove = new HashSet<>();  // Nodes that would be removed from selected nodes.
                nodesToRemove.add(it.next());
                if(it.hasNext()){
                    nodesToRemove.add(it.next());
                }

                int selectedNodesWeight = sol.getNodesWeight(nodesToRemove);                     // Weight that can't be surpassed when adding new nodes
                int addedNodesWeight = 0;                                                        // Weight from the nodes that would be added to the solution
                Set<Integer> nodesToAdd = new HashSet<>();                                       // Nodes that would be added if the selected node was removed
                Set<Integer> notDomNodesIfRemoved = sol.getNotDomNodesIfRemoved(nodesToRemove);  // Nodes that wouldn't be covered if the selected node was removed
                
                // While not surpassing the weight, there are nodes not covered if removed and there is a real node as
                // the best node to add, keep adding best alternative nodes.
                realNodeSelected = true;
                while(notDomNodesIfRemoved.size() > 0 && addedNodesWeight < selectedNodesWeight && realNodeSelected){ 
                    bestFactor = Double.POSITIVE_INFINITY;                
                    bestWeight = 0;    
                    bestNode = -1;  
                    bestNodeCover = new HashSet<>();

                    // Determine the best not selected alternative node for the selected node
                    for (Integer notSelNode : notSelectedNodes) {
                        if(!nodesToAdd.contains(notSelNode)){
                            Set<Integer> localNodeCover = sol.getNodesCoveredIfAdded(notSelNode, notDomNodesIfRemoved); 
                            int localWeight = sol.getNodeWeight(notSelNode);  
                            int localNodeCoverWeight = sol.getNodesWeight(localNodeCover);

                            // Calculate factor
                            double localFactor = Double.POSITIVE_INFINITY;
                            if(localNodeCoverWeight != 0){
                                localFactor = localWeight / (double) localNodeCoverWeight;
                            }
                            
                            // Update best if necessary
                            if(localFactor < bestFactor){
                                bestFactor = localFactor;
                                bestNodeCover = localNodeCover;
                                bestWeight = localWeight;
                                bestNode = notSelNode;
                            }
                        }
                    }

                    // If a real node was selected, add the alternative node to the nodes that would be added if the 
                    // selected node was removed, add its weight and remove the nodes that would be covered if the 
                    // alternative node was added from those the selected node would leave uncovered if removed.
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

                /// If the alternative nodes to be added are better than the selected node 'SN' (they covered all the not 
                // dominated nodes if it was removed and their weight is better than the 'SN' weight), check if the 
                // change is better than the last best one. 
                if(addedNodesWeight < selectedNodesWeight && realNodeSelected){
                    double localDifference = selectedNodesWeight - addedNodesWeight;

                    if(localDifference < bestDifference){
                        bestNodesToRemove = nodesToRemove;
                        bestNodesToAdd = nodesToAdd;
                        changeDone = true;
                    }
                }
            }

            // If there was at least one change that could be done, do the best change possible.
            if(changeDone) {
                sol.swapNodes(bestNodesToRemove, bestNodesToAdd);
            }
        }

        return sol;
    }
}
