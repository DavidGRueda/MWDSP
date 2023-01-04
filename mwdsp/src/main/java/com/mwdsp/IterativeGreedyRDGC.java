package com.mwdsp;

import java.util.Iterator;
import java.util.Set;

import com.mwdsp.localSearch.LocalSearch;

public class IterativeGreedyRDGC implements IterativeGreedy{
    
    public Solution execute(Solution solution, float beta, int stopIterations, LocalSearch ls, double alpha) throws CloneNotSupportedException{
        int bestWeight = solution.getTotalWeight();
        int totalNodes = solution.getTotalNodes();
        int localWeight;
        int NOT_IMPROVED_IT = stopIterations;
        
        while(NOT_IMPROVED_IT > 0){
            Solution sol = (Solution) solution.clone();
            sol.setSelectedNodes(solution.getSelectedNodes());
            sol.setNotSelectedNodes(solution.getNotSelectedNodes());

            Set<Integer> selectedNodes = sol.getSelectedNodes();
            Iterator<Integer> it = selectedNodes.iterator();

            // Destroy solution -> Get the number of elements to remove from the solution. 
            int numElementsToDestroy = Math.round(selectedNodes.size() * beta);

            for (int i = 0; i < numElementsToDestroy; i++) {
                int selNodeToRemove = it.next();
                it.remove();
                sol.removeAndUpdateConnections(selNodeToRemove);
            }

            // Build solution again (greedy algorithm)
            int bestNode = -1;
            double bestGreedyFactor = Double.NEGATIVE_INFINITY;
            double localGreedyFactor;

            while (!sol.isFeasible()) {
                bestNode = -1;
                bestGreedyFactor = Double.NEGATIVE_INFINITY;

                for (int i = 0; i < totalNodes; i++) {
                    if (!sol.isSelected(i)) {
                        localGreedyFactor = sol.calculateGreedyFactor(i);
                        if (localGreedyFactor > bestGreedyFactor) {
                            bestNode = i;
                            bestGreedyFactor = localGreedyFactor;
                        }
                    }
                }
                sol.add(bestNode);
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
