package com.mwdsp;

import java.util.Random;

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

        for (int i = 0; i < filenames.length; i++) {
            Instance ins = new Instance(filenames[i]);
            System.out.print(filenames[i]);
            greedyBuilderMethod(ins, true);
        }

    }

    public static void randomBuilderMethod(Instance i, boolean purge) {
        int N_IT = 100000;

        RandomBuilder r = new RandomBuilder();
        double bestWeight = Double.POSITIVE_INFINITY;
        double localWeight;
        Solution bestSol = null;

        long start = System.currentTimeMillis();
        for (int j = 0; j < N_IT; j++) {
            Solution sol = r.execute(i);
            if(purge){
                sol.purgeSolution();
            }
            if ((localWeight = sol.getTotalWeight()) < bestWeight) {
                bestWeight = localWeight;
                bestSol = sol;
            }
        }
        long finish = System.currentTimeMillis();

        System.out.println("\nTime: " + (finish - start) + " ms");
        bestSol.printSolution();
    }

    public static void greedyBuilderMethod(Instance i, boolean purge) {
        GreedyBuilder builder = new GreedyBuilder();
        Solution sol = null;

        long start = System.currentTimeMillis();
        sol = builder.execute(i);
        if(purge){
            sol.purgeSolution();
        }
        long finish = System.currentTimeMillis();

        System.out.println("\nTime: " + (finish - start) + " ms");
        sol.printSolution();
    }

    public static void graspBuilderMethod(Instance i, boolean purge) {
        GraspBuilder builder;
        Solution sol;
        Solution bestSol = null;
        double bestWeight = Double.POSITIVE_INFINITY;
        double alpha;
        double bestAlpha = -1;

        Random random = new Random();
        long seed = random.nextLong();
        random.setSeed(seed);

        long start = System.currentTimeMillis();
        for (int j = 0; j < 100; j++) {
            alpha = random.nextDouble();
            // Get new alpha while alpha == 0
            while (alpha == 0.0)
                alpha = random.nextDouble();

            builder = new GraspBuilder(alpha);
            sol = builder.execute(i);
            if(purge){
                sol.purgeSolution();
            }
            if (sol.getTotalWeight() < bestWeight) {
                bestSol = sol;
                bestWeight = bestSol.getTotalWeight();
                bestAlpha = alpha;
            }
        }
        long finish = System.currentTimeMillis();

        System.out.println("\nAlpha: " + bestAlpha);
        System.out.println("\nTime: " + (finish - start) + " ms");
        bestSol.printSolution();
    }

    public static void graspBuilderMethod(Instance i, boolean purge, double alpha) {
        GraspBuilder builder = new GraspBuilder(alpha);
        Solution sol = null;
        Solution bestSol = null;
        double bestWeight = Double.POSITIVE_INFINITY;

        long start = System.currentTimeMillis();
        for (int j = 0; j < 100; j++) {
            sol = builder.execute(i);
            if(purge){
                sol.purgeSolution();
            }
            if (sol.getTotalWeight() < bestWeight) {
                bestSol = sol;
                bestWeight = bestSol.getTotalWeight();
            }
        }
        long finish = System.currentTimeMillis();

        System.out.println("\nTime: " + (finish - start) + " ms");
        bestSol.printSolution();
    }
}
