package com.mwdsp;

import com.mwdsp.localSearch.LocalSearch;
import com.mwdsp.localSearch.LocalSearch1xNFI;
import com.mwdsp.localSearch.LocalSearch1xNBI;
import com.mwdsp.localSearch.LocalSearch2xNFI;
import com.mwdsp.localSearch.LocalSearch2xNBI;

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
            // Initialize the CustomRandom class
            CustomRandom.init();

            // Create Instance
            Instance ins = new Instance(filename);
            System.out.println("\n" + filename);

            // Create Local Search
            LocalSearch ls = new LocalSearch1xNFI();

            // Execute Algorithm
            Algorithm alg = new Algorithm();
            //Solution solution = alg.executeGreedy(ins, true, ls);
            Solution solution = alg.executeGraspParallelized(ins, true, ls, 0.25);

            // Execute Iterative Greedy
            solution = alg.executeIterativeGreedyGDGC(solution, 0.3f, 10, ls, 0.25);

            // Print Solution
            solution.printSolution();
            System.out.println("-----------------------------------------------------------------------------------");
        }
    }
}