package com.mwdsp;

import java.util.Random;

import com.mwdsp.builders.GraspBuilder;
import com.mwdsp.builders.GreedyBuilder;
import com.mwdsp.builders.RandomBuilder;
import com.mwdsp.localSearch.LocalSearch;
import com.mwdsp.localSearch.LocalSearch1xNFI;
import com.mwdsp.localSearch.LocalSearch1xNBI;

public class App {
    public static void main(String[] args) {

        String[] filenames = {
                "Problem.dat_50_50_0",
                "Problem.dat_50_50_1",
                "Problem.dat_50_100_0",
                "Problem.dat_50_100_1",
                "Problem.dat_50_250_0",
                "Problem.dat_50_250_1",
                "Problem.dat_50_1000_0",
                "Problem.dat_50_1000_1",
                "Problem.dat_100_100_0",
                "Problem.dat_100_100_1",
                "Problem.dat_100_250_0",
                "Problem.dat_100_250_1",
                "Problem.dat_100_500_0",
                "Problem.dat_100_500_1",
                "Problem.dat_100_750_0",
                "Problem.dat_100_750_1",
                "Problem.dat_250_750_0",
                "Problem.dat_250_750_1",
                "Problem.dat_250_1000_0",
                "Problem.dat_250_1000_1",
                "Problem.dat_300_300_0",
                "Problem.dat_300_300_1",
                "Problem.dat_1000_1000_0",
                "Problem.dat_1000_1000_1",
        };

        for (String filename : filenames) {

            Instance ins = new Instance(filename);
            System.out.println("\n" + filename);

            LocalSearch ls = new LocalSearch1xNBI();
            //randomBuilderMethod(ins, true, ls, 100);
            //greedyBuilderMethod(ins, true, ls);
            graspBuilderMethod(ins, true, ls);
        }
    }

    public static void randomBuilderMethod(Instance i, boolean purge, LocalSearch localSearch, int N_IT) {

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

        printTimesAndSolution(solution, builderTime, purgeTime, localSearchTime);
    }

    public static void greedyBuilderMethod(Instance i, boolean purge, LocalSearch localSearch) {
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

        printTimesAndSolution(solution, builderTime, purgeTime, localSearchTime);
    }

    public static void graspBuilderMethod(Instance i, boolean purge, LocalSearch localSearch) {
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

        printTimesAndSolution(solution, builderTime, purgeTime, localSearchTime);
    }

    public static void graspBuilderMethod(Instance i, boolean purge, LocalSearch localSearch, double alpha) {
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

        printTimesAndSolution(solution, builderTime, purgeTime, localSearchTime);
    }

    private static void printTimesAndSolution(Solution solution, long builderTime, long purgeTime, long localSearchTime){
        System.out.println("Builder time: " + builderTime + " ms");
        System.out.println("Purge time: " + purgeTime + " ms");
        System.out.println("LocalSearch time: " + localSearchTime + " ms");
        System.out.println("Total time: " + (builderTime + purgeTime + localSearchTime) + " ms");
        solution.printSolution();
        //solution.checkIfFeasible();
        System.out.println("-----------------------------------------------------------------------------------------");
    }
}
