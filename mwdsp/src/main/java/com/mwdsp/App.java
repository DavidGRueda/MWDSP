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

        // Variables to take times
        Solution solution = null;
        long start, finish, ellapsed, totalTime;

        for (int i = 0; i < filenames.length; i++) {
            totalTime = 0;
            Instance ins = new Instance(filenames[i]);
            System.out.println(filenames[i]);

            // Create solution (Builder method)
            start = System.currentTimeMillis();
            solution = graspBuilderMethod(ins, 0.25);
            finish = System.currentTimeMillis();
            ellapsed = finish - start;
            totalTime += ellapsed;
            System.out.println("Builder time: " + ellapsed + " ms");
            solution.printSolution();


            // Purge not needed nodes 
            start = System.currentTimeMillis();
            solution.purgeSolution();
            finish = System.currentTimeMillis();
            ellapsed = finish - start;
            totalTime += ellapsed;
            System.out.println("Purge time: " + ellapsed + " ms");
            solution.printSolution();

            // Do the local search
            start = System.currentTimeMillis();
            LocalSearch localSearch = new LocalSearch1xNFI();
            solution = localSearch.execute(solution);
            finish = System.currentTimeMillis();
            ellapsed = finish - start;
            totalTime += ellapsed;
            System.out.println("Local search time: " + ellapsed + " ms");
            solution.printSolution();

            // Final status:
            System.out.println("Total time: " + totalTime + " ms");
            System.out.println("-------------------------------------------------------------------------------------");
        }

    }

    public static Solution randomBuilderMethod(Instance i) {
        int N_IT = 100000;

        RandomBuilder r = new RandomBuilder();
        double bestWeight = Double.POSITIVE_INFINITY;
        double localWeight;
        Solution bestSol = null;

        for (int j = 0; j < N_IT; j++) {
            Solution sol = r.execute(i);
            if ((localWeight = sol.getTotalWeight()) < bestWeight) {
                bestWeight = localWeight;
                bestSol = sol;
            }
        }

        return bestSol;
    }

    public static Solution greedyBuilderMethod(Instance i) {
        GreedyBuilder builder = new GreedyBuilder();
        Solution sol = null;
        sol = builder.execute(i);
        return sol;
    }

    public static Solution graspBuilderMethod(Instance i) {
        GraspBuilder builder;
        Solution sol;
        Solution bestSol = null;
        double bestWeight = Double.POSITIVE_INFINITY;
        double alpha;

        Random random = new Random();
        long seed = random.nextLong();
        random.setSeed(seed);

        for (int j = 0; j < 100; j++) {
            alpha = random.nextDouble();

            // Get new alpha while alpha == 0
            while (alpha == 0.0)
                alpha = random.nextDouble();

            builder = new GraspBuilder(alpha);
            sol = builder.execute(i);

            if (sol.getTotalWeight() < bestWeight) {
                bestSol = sol;
                bestWeight = bestSol.getTotalWeight();
            }
        }

       return bestSol;
    }

    public static Solution graspBuilderMethod(Instance i, double alpha) {
        GraspBuilder builder = new GraspBuilder(alpha);
        Solution sol = null;
        Solution bestSol = null;
        double bestWeight = Double.POSITIVE_INFINITY;

        for (int j = 0; j < 100; j++) {
            sol = builder.execute(i);

            if (sol.getTotalWeight() < bestWeight) {
                bestSol = sol;
                bestWeight = bestSol.getTotalWeight();
            }
        }

        return bestSol;
    }
}
