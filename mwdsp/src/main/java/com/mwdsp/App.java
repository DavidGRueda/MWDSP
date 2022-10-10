package com.mwdsp;

public class App {
    public static void main(String[] args) {
        int N_IT = 100;

        Instance i = new Instance("frb59-26-2.mtx");
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
}
