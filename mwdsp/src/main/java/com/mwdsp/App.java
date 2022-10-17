package com.mwdsp;

public class App {
    public static void main(String[] args) {

        Instance i = new Instance("Problem.dat_1000_1000_1");
        i.printInstance();
        greedyBuilderMethod(i);

    }

    public static void randomBuilderMethod(Instance i) {
        int N_IT = 100000;

        RandomBuilder r = new RandomBuilder();
        double bestWeight = Double.POSITIVE_INFINITY;
        double localWeight;
        Solution bestSol = null;

        long start = System.currentTimeMillis();
        for (int j = 0; j < N_IT; j++) {
            Solution sol = r.execute(i);
            if ((localWeight = sol.getTotalWeight()) < bestWeight) {
                bestWeight = localWeight;
                bestSol = sol;
            }
        }
        long finish = System.currentTimeMillis();

        System.out.println("\nTime: " + (finish - start) + " ms");
        bestSol.printSolution();
    }

    public static void greedyBuilderMethod(Instance i) {
        GreedyBuilder builder = new GreedyBuilder();
        Solution sol = null;

        long start = System.currentTimeMillis();
        sol = builder.execute(i);
        long finish = System.currentTimeMillis();

        System.out.println("\nTime: " + (finish - start) + " ms");
        sol.printSolution();
    }
}
