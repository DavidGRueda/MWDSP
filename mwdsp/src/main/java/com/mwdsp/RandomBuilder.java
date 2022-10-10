package com.mwdsp;

import java.util.Random;

public class RandomBuilder implements Builder {

    public Solution execute(Instance i) {
        Solution sol = new Solution(i);

        Random random = new Random();
        long seed = random.nextLong();
        random.setSeed(seed);

        int totalNodes = i.getNodes();

        // Add nodes (not selected before) to the solution until it's factible.
        int randomNode;
        while (!sol.isFeasible()) {
            randomNode = random.nextInt(totalNodes);
            if (!sol.isSelected(randomNode))
                sol.add(randomNode);
        }

        return sol;
    }
}
