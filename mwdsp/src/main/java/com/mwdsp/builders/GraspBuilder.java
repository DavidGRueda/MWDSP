package com.mwdsp.builders;

import java.util.Random;

import com.mwdsp.Instance;
import com.mwdsp.Solution;

public class GraspBuilder implements Builder {
    private double alpha; // alpha => [0, 1]

    public GraspBuilder(double alpha) {
        this.alpha = alpha;
    }

    public Solution execute(Instance instance) {
        Solution sol = new Solution(instance);        
        int totalNodeCount = instance.getNodeCount();

        // Variables to create RCL and its filter
        double maxGreedyFactor;                                   // Best greedy factor found in each iteration
        double minGreedyFactor;                                   // Worse greedy factor found in each iteration                        
        double greedyFactors[] = new double[totalNodeCount];      // Greedy factors for each node in each iteration
        int[] restrictedCandidateList = new int[totalNodeCount];  // RCL to select randomly a node after filtering

        // Get a random first node and add it to the solution
        Random random = new Random();
        long seed = random.nextLong();
        random.setSeed(seed);
        int firstNode = random.nextInt(totalNodeCount);
        sol.add(firstNode);

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

        return sol;
    }
}
