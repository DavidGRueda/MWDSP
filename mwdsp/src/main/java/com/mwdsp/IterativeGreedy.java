package com.mwdsp;

import com.mwdsp.localSearch.LocalSearch;

public interface IterativeGreedy {
    public Solution execute(Solution solution, float beta, int stopIterations, LocalSearch ls) throws CloneNotSupportedException;
}
