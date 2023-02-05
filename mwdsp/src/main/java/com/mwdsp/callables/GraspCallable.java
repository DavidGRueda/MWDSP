package com.mwdsp.callables;

import java.util.concurrent.Callable;

import com.mwdsp.Instance;
import com.mwdsp.Solution;
import com.mwdsp.builders.GraspBuilder;
import com.mwdsp.localSearch.LocalSearch;

public class GraspCallable implements Callable<Solution>{

    //Variables
    private Instance _instance;
    private boolean _purge;
    private LocalSearch _localSearch;
    private double _alpha;

    // Constructor
    public GraspCallable(Instance instance,boolean purge, LocalSearch localSearch, double alpha){
        _instance = instance;
        _purge = purge;
        _localSearch = localSearch;
        _alpha = alpha;
    }

    // For each thread, create an initial solution, purge it and execute a local search.
    public Solution call() throws Exception {
        GraspBuilder builder = new GraspBuilder(_alpha);
        Solution sol = builder.execute(_instance);
        if(_purge) {
            sol.purgeSolution();
        }
        if(_localSearch != null){
            sol = _localSearch.execute(sol);
        }
        return sol;
    }
    
}
