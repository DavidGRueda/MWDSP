package com.mwdsp;

import java.util.Random;

import com.mwdsp.builders.GraspBuilder;
import com.mwdsp.builders.GreedyBuilder;
import com.mwdsp.builders.RandomBuilder;
import com.mwdsp.localSearch.LocalSearch;

public class Algorithm {

    public Solution executeRandom(Instance i, boolean purge, LocalSearch localSearch, int N_IT) {

        // Variables to take times
        Solution solution = null;
        long start, finish, builderTime, purgeTime, localSearchTime;

        builderTime = 0;
        purgeTime = 0;
        localSearchTime = 0;

        RandomBuilder r = new RandomBuilder();
        double bestWeight = Double.POSITIVE_INFINITY;
        double localWeight;

        for (int j = 0; j < N_IT; j++) {

            //Builder Time
            start = System.currentTimeMillis();
            Solution sol = r.execute(i);
            finish = System.currentTimeMillis();
            builderTime += finish - start;

            // Purge Time
            if(purge){
                start = System.currentTimeMillis();
                sol.purgeSolution();
                finish = System.currentTimeMillis();
                purgeTime += finish - start;
            }

            // Local Search Time
            if(localSearch != null){
                start = System.currentTimeMillis();
                sol = localSearch.execute(sol);
                finish = System.currentTimeMillis();
                localSearchTime += finish - start;
            }

            if ((localWeight = sol.getTotalWeight()) < bestWeight) {
                bestWeight = localWeight;
                solution = sol;
            }
        }

        printBuilderTimes(builderTime, purgeTime, localSearchTime);
        return solution;
    }

    public Solution executeGreedy(Instance i, boolean purge, LocalSearch localSearch) {
        long start, finish, builderTime, purgeTime, localSearchTime;

        builderTime = 0;
        purgeTime = 0;
        localSearchTime = 0;

        GreedyBuilder builder = new GreedyBuilder();

        //Builder Time
        start = System.currentTimeMillis();
        Solution solution = builder.execute(i);
        finish = System.currentTimeMillis();
        builderTime += finish - start;

        // Purge Time
        if(purge){
            start = System.currentTimeMillis();
            solution.purgeSolution();
            finish = System.currentTimeMillis();
            purgeTime += finish - start;
        }

        // Local Search Time
        if(localSearch != null){
            start = System.currentTimeMillis();
            solution = localSearch.execute(solution);
            finish = System.currentTimeMillis();
            localSearchTime += finish - start;
        }

        printBuilderTimes(builderTime, purgeTime, localSearchTime);
        return solution;
    }

    public Solution executeRandomGrasp(Instance i, boolean purge, LocalSearch localSearch) {
        Solution solution = null;
        long start, finish, builderTime, purgeTime, localSearchTime;

        builderTime = 0;
        purgeTime = 0;
        localSearchTime = 0;


        GraspBuilder builder;
        Solution sol;
        double bestWeight = Double.POSITIVE_INFINITY;
        double localWeight;
        double alpha;

        Random random = new Random();
        long seed = random.nextLong();
        random.setSeed(seed);

        for (int j = 0; j < 100; j++) {
            alpha = random.nextDouble();

            // Get new alpha while alpha == 0
            while (alpha == 0.0)
                alpha = random.nextDouble();

            //Builder Time
            start = System.currentTimeMillis();
            builder = new GraspBuilder(alpha);
            sol = builder.execute(i);
            finish = System.currentTimeMillis();
            builderTime += finish - start;

             // Purge Time
             if(purge){
                start = System.currentTimeMillis();
                sol.purgeSolution();
                finish = System.currentTimeMillis();
                purgeTime += finish - start;
            }

            // Local Search Time
            if(localSearch != null){
                start = System.currentTimeMillis();
                sol = localSearch.execute(sol);
                finish = System.currentTimeMillis();
                localSearchTime += finish - start;
            }

            if ((localWeight = sol.getTotalWeight()) < bestWeight) {
                bestWeight = localWeight;
                solution = sol;
            }
        }

        printBuilderTimes(builderTime, purgeTime, localSearchTime);
        return solution;
    }

    public Solution executeGrasp(Instance i, boolean purge, LocalSearch localSearch, double alpha) {
        Solution solution = null;        
        long start, finish, builderTime, purgeTime, localSearchTime;
        
        builderTime = 0;
        purgeTime = 0;
        localSearchTime = 0;
        
        double bestWeight = Double.POSITIVE_INFINITY;
        double localWeight;
        GraspBuilder builder = new GraspBuilder(alpha);
        Solution sol;

        for (int j = 0; j < 100; j++) {

            //Builder Time
            start = System.currentTimeMillis();
            builder = new GraspBuilder(alpha);
            sol = builder.execute(i);
            finish = System.currentTimeMillis();
            builderTime += finish - start;

            // Purge Time
            if(purge){
                start = System.currentTimeMillis();
                sol.purgeSolution();
                finish = System.currentTimeMillis();
                purgeTime += finish - start;
            }

            // Local Search Time
            if(localSearch != null){
                start = System.currentTimeMillis();
                sol = localSearch.execute(sol);
                finish = System.currentTimeMillis();
                localSearchTime += finish - start;
            }

            if ((localWeight = sol.getTotalWeight()) < bestWeight) {
                bestWeight = localWeight;
                solution = sol;
            }
        }

        printBuilderTimes(builderTime, purgeTime, localSearchTime);
        return solution;
    }

    public void printBuilderTimes(long builderTime, long purgeTime, long localSearchTime){
        System.out.println("Builder time: " + builderTime + " ms");
        System.out.println("Purge time: " + purgeTime + " ms");
        System.out.println("LocalSearch time: " + localSearchTime + " ms");
        System.out.println("Total time: " + (builderTime + purgeTime + localSearchTime) + " ms");        
    }
}
