package com.mwdsp;

import java.util.Random;
import java.util.Set;

import com.mwdsp.localSearch.LocalSearch;

public class IterativeGreedyGDGC implements IterativeGreedy{

    public Solution execute(Solution solution, float beta, int stopIterations, LocalSearch ls, double alpha) throws CloneNotSupportedException {
        int bestWeight = solution.getTotalWeight();
        int totalNodes = solution.getTotalNodes();
        int localWeight;
        int NOT_IMPROVED_IT = stopIterations;
        
        while(NOT_IMPROVED_IT > 0){
            Solution sol = (Solution) solution.clone();
            sol.setSelectedNodes(solution.getSelectedNodes());
            sol.setNotSelectedNodes(solution.getNotSelectedNodes());

            Set<Integer> selectedNodes = sol.getSelectedNodes();

            // Destroy solution -> Get the number of elements to remove from the solution. 
            int numElementsToDestroy = Math.round(selectedNodes.size() * beta);
            


            for (int i = 0; i < numElementsToDestroy; i++) {
                double worstNodeFactor = Double.POSITIVE_INFINITY;
                int selNodeToRemove = -1;

                // Find the worst node -> Less num of connections with more weight of the selected ones. 
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

            // Build solution again (grasp algorithm)          

            double maxGreedyFactor;
            double minGreedyFactor;

            double greedyFactors[] = new double[totalNodes];
            double localGreedyFactor;

            int[] randomCandidateList = new int[totalNodes];
            int pointer; // Used to know how much elements pass the filter
            double filter;
            int randomNodeSelected;
            
            Random random = new Random();
            long seed = random.nextLong();
            random.setSeed(seed);

            while (!sol.isFeasible()) {
                maxGreedyFactor = Double.NEGATIVE_INFINITY;
                minGreedyFactor = Double.POSITIVE_INFINITY;

                // Calculate the greedy factor of each node if not selected. Else, factor = -1
                for (int i = 0; i < totalNodes; i++) {
                    if (!sol.isSelected(i)) {
                        localGreedyFactor = sol.calculateGreedyFactor(i);
                        greedyFactors[i] = localGreedyFactor;

                        // Update min and max greedyFactors.
                        if (localGreedyFactor < minGreedyFactor)
                            minGreedyFactor = localGreedyFactor;
                        if (localGreedyFactor > maxGreedyFactor)
                            maxGreedyFactor = localGreedyFactor;

                    } else
                        greedyFactors[i] = -1;
                }

                // After calculating factors, calculate μ = gmin - alpha * (gmax - gmin)
                filter = maxGreedyFactor - alpha * (maxGreedyFactor - minGreedyFactor);
                pointer = 0;

                // Generate the random candidate list -> greedyFactor >= filter
                for (int i = 0; i < totalNodes; i++) {
                    if (greedyFactors[i] >= filter) {
                        randomCandidateList[pointer] = i;
                        pointer++;
                    }
                }

                // Pick one of the random candidate list and add it to the sol.
                randomNodeSelected = randomCandidateList[random.nextInt(pointer)];
                sol.add(randomNodeSelected);
            }

            // Execute a purge and a local search
            sol.purgeSolution();
            sol = ls.execute(sol);

            if((localWeight = sol.getTotalWeight()) < bestWeight){
                solution = sol;
                bestWeight = localWeight;
                NOT_IMPROVED_IT = stopIterations;
            } else {
                NOT_IMPROVED_IT--;
            }
        }

        return solution;
    }
    
}
