package com.mwdsp.builders;

import java.util.Random;

import com.mwdsp.Instance;
import com.mwdsp.Solution;

public class RandomBuilder implements Builder {

    public Solution execute(Instance instance) {
        Solution sol = new Solution(instance);
        int totalNodeCount = instance.getNodeCount();

        // Variables needed to get nodes randomly
        Random random = new Random();
        long seed = random.nextLong();
        random.setSeed(seed);

        // Add nodes randomly (if they are not selected before) to the solution until it's feasible
        while (!sol.isFeasible()) {
            int randomNode = random.nextInt(totalNodeCount);

            if (!sol.isSelected(randomNode))
                sol.add(randomNode);
        }

        return sol;
    }
}
