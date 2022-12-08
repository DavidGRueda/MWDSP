package com.mwdsp.builders;

import com.mwdsp.Instance;
import com.mwdsp.Solution;

public interface Builder {
    Solution execute(Instance instance);
}
