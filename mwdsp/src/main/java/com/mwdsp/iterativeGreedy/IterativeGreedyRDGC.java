package com.mwdsp.iterativeGreedy;

import java.util.Iterator;
import java.util.Set;

import com.mwdsp.Solution;
import com.mwdsp.localSearch.LocalSearch;

public class IterativeGreedyRDGC implements IterativeGreedy{
    
    public Solution execute(Solution solution, float beta, int stopIterations, LocalSearch ls, double alpha) 
        throws CloneNotSupportedException{
            
        int bestWeight = solution.getTotalWeight();         // Initialize the best weight with the initial solution weight  
        int totalNodeCount = solution.getTotalNodeCount();  // Get total node count (used for GRASP construction)
        int notImprovedIter = stopIterations;               // Iterations left until Iterative Greedy is exited
            
        // While there is an improvement in less executions than the stop iterations passed as parameter
        while(notImprovedIter > 0){

            // Clone the actual best solution into an auxiliar solution that can be modified. Because Java passes 
            // collections with a reference, new Sets must be done to avoid the modification to the best solution found
            Solution sol = (Solution) solution.clone();
            sol.setSelectedNodes(solution.getSelectedNodes());
            sol.setNotSelectedNodes(solution.getNotSelectedNodes());
            Set<Integer> selectedNodes = sol.getSelectedNodes();

            
            // Random Destruction Phase.
            int numElementsToDestroy = Math.round(selectedNodes.size() * beta);
            
            Iterator<Integer> it = selectedNodes.iterator();
            for (int i = 0; i < numElementsToDestroy; i++) {
                int selNodeToRemove = it.next();               
                it.remove();                                      
                sol.removeAndUpdateConnections(selNodeToRemove);  
            }

            // Greedy Build Phase
            int bestNode = -1;
            double bestGreedyFactor = Double.NEGATIVE_INFINITY;

            while (!sol.isFeasible()) {
                bestNode = -1;
                bestGreedyFactor = Double.NEGATIVE_INFINITY;

                // Search for the best node (not already selected) to add to the solution
                for (int i = 0; i < totalNodeCount; i++) {
                    if (!sol.isSelected(i)) {
                        double localGreedyFactor = sol.calculateGreedyFactor(i);

                        if (localGreedyFactor > bestGreedyFactor) {
                            bestNode = i;
                            bestGreedyFactor = localGreedyFactor;
                        }
                    }
                }

                sol.add(bestNode);
            }

            // Execute Purge and Local Search to improve built solution
            sol.purgeSolution();
            sol = ls.execute(sol);

            // Update best solution if necessary. Else, update iterations left until exited
            int localWeight = sol.getTotalWeight();
            if(localWeight < bestWeight){
                solution = sol;
                bestWeight = localWeight;
                notImprovedIter = stopIterations;
            } else {
                notImprovedIter--;
            }
        }

        return solution;
    }
}
