package com.mwdsp;

public class GreedyBuilder implements Builder {
    public Solution execute(Instance instance) {
        Solution sol = new Solution(instance);
        int totalNodes = instance.getNodes();
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

        return sol;
    }
}