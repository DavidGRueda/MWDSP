package com.mwdsp.builders;

import com.mwdsp.Instance;
import com.mwdsp.Solution;

public class GreedyBuilder implements Builder {
    
    public Solution execute(Instance instance) {
        Solution sol = new Solution(instance);
        int totalNodes = instance.getNodeCount();

        // Variables to add the best node in each iteration
        int bestNode = -1;
        double bestGreedyFactor = Double.NEGATIVE_INFINITY;
        
        while (!sol.isFeasible()) {
            bestNode = -1;
            bestGreedyFactor = Double.NEGATIVE_INFINITY;

            // Search for the best node (not already selected) to add to the solution
            for (int i = 0; i < totalNodes; i++) {
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

        return sol;
    }
}