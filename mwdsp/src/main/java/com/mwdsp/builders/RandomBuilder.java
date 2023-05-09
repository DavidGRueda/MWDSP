package com.mwdsp.builders;

import com.mwdsp.CustomRandom;
import com.mwdsp.Instance;
import com.mwdsp.Solution;

public class RandomBuilder implements Builder {

    public Solution execute(Instance instance) {
        Solution sol = new Solution(instance);
        int totalNodeCount = instance.getNodeCount();

        // Add nodes randomly (if they are not selected before) to the solution until it's feasible
        while (!sol.isFeasible()) {
            int randomNode = CustomRandom.nextInt(totalNodeCount);

            if (!sol.isSelected(randomNode))
                sol.add(randomNode);
        }

        return sol;
    }
}
