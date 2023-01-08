package com.mwdsp.iterativeGreedy;

import java.util.Random;
import java.util.Set;

import com.mwdsp.Solution;
import com.mwdsp.localSearch.LocalSearch;

public class IterativeGreedyGDGC implements IterativeGreedy{

    public Solution execute(Solution solution, float beta, int stopIterations, LocalSearch ls, double alpha) 
        throws CloneNotSupportedException {

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


            // Greedy Destruction Phase.
            int numElementsToDestroy = Math.round(selectedNodes.size() * beta);

            for (int i = 0; i < numElementsToDestroy; i++) {
                double worstNodeFactor = Double.POSITIVE_INFINITY;
                int selNodeToRemove = -1;

                // Find the worst node (heaviest selected node with less connections) 
                for (Integer node : selectedNodes) {                    
                    double localNodeFactor = sol.calculateDestroyFactor(node);

                    if(localNodeFactor < worstNodeFactor){
                        selNodeToRemove = node;
                        worstNodeFactor = localNodeFactor;
                    }
                }

                // Eliminate worst node.
                selectedNodes.remove(selNodeToRemove);
                sol.removeAndUpdateConnections(selNodeToRemove);
            }

            // GRASP Build Phase    
            double maxGreedyFactor;                                   // Best greedy factor found in each iteration
            double minGreedyFactor;                                   // Worse greedy factor found in each iteration
            double greedyFactors[] = new double[totalNodeCount];      // Greedy factors for each node in each iteration
            int[] restrictedCandidateList = new int[totalNodeCount];  // RCL to select randomly a node after filtering
            
            Random random = new Random();
            long seed = random.nextLong();
            random.setSeed(seed);

            while (!sol.isFeasible()) {
                maxGreedyFactor = Double.NEGATIVE_INFINITY;
                minGreedyFactor = Double.POSITIVE_INFINITY;

                // Calculate the greedy factor of each node if not selected. Else, factor = -1
                for (int i = 0; i < totalNodeCount; i++) {
                    if (!sol.isSelected(i)) {
                        double localGreedyFactor = sol.calculateGreedyFactor(i);
                        greedyFactors[i] = localGreedyFactor;

                        // Update min and max greedyFactors if necessary
                        if (localGreedyFactor < minGreedyFactor)
                            minGreedyFactor = localGreedyFactor;

                        if (localGreedyFactor > maxGreedyFactor)
                            maxGreedyFactor = localGreedyFactor;
                    } else
                        greedyFactors[i] = -1;
                }

                // After calculating factors, calculate μ = gmin - alpha * (gmax - gmin)
                double filter = maxGreedyFactor - alpha * (maxGreedyFactor - minGreedyFactor);
                int pointer = 0;

                // Generate the Restricted Candidate List
                for (int i = 0; i < totalNodeCount; i++) {
                    if (greedyFactors[i] >= filter) {
                        restrictedCandidateList[pointer] = i;
                        pointer++;
                    }
                }

                // Pick one node randomly from the RCL and add it to the solution
                int nodeSelected = restrictedCandidateList[random.nextInt(pointer)];
                sol.add(nodeSelected);
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
