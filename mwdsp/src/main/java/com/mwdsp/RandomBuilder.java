package com.mwdsp;

import java.util.Random;
import com.mwdsp.*;

public class RandomBuilder implements Builder {

    public Solution execute(Instance i) {
        Solution sol = new Solution(i);

        Random random = new Random();
        long seed = random.nextLong();
        random.setSeed(seed);

        int totalNodes = i.getNodes();

        // Add nodes to the solution until it's factible.
        int randomNode;
        while (!sol.isFeasible()) {
            randomNode = random.nextInt(totalNodes);
            sol.add(randomNode);
        }

        return sol;
    }
}
