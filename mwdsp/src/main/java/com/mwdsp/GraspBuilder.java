package com.mwdsp;

import java.util.Random;

public class GraspBuilder implements Builder {
    private double alpha; // alpha => [0, 1]

    public GraspBuilder(double alpha) {
        this.alpha = alpha;
    }

    public Solution execute(Instance instance) {
        Solution sol = new Solution(instance);
        int totalNodes = instance.getNodes();

        double maxGreedyFactor;
        double minGreedyFactor;

        double greedyFactors[] = new double[totalNodes];
        double localGreedyFactor;

        int[] randomCandidateList = new int[totalNodes];
        int pointer; // Used to know how much elements pass the filter
        double filter;
        int randomNodeSelected;

        // Get a random first node and add it to the solution.
        Random random = new Random();
        long seed = random.nextLong();
        random.setSeed(seed);
        int firstNode = random.nextInt(totalNodes);
        sol.add(firstNode);

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

        return sol;
    }
}
