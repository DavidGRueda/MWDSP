package com.mwdsp;

public class App {
    public static void main(String[] args) {
        int N_IT = 1000;

        Instance i = new Instance("frb59-26-2.mtx");
        RandomBuilder r = new RandomBuilder();
        double elapsed = 0;
        double avgWeight = 0;

        for (int j = 0; j < N_IT; j++) {
            long start = System.currentTimeMillis();
            Solution sol = r.execute(i);
            long finish = System.currentTimeMillis();
            elapsed += finish - start;
            avgWeight += sol.getTotalWeight();
            sol.printSolution();
        }

        System.out.println("\nTime: " + elapsed / N_IT + " ms");
        System.out.println("Weight:" + avgWeight / N_IT);
    }
}
