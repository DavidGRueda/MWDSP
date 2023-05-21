package com.mwdsp;

import java.io.BufferedWriter;
import java.io.IOException;

import com.mwdsp.localSearch.LocalSearch;
import com.mwdsp.localSearch.LocalSearch1xNFI;
import com.mwdsp.localSearch.LocalSearch1xNBI;
import com.mwdsp.localSearch.LocalSearch2xNFI;
import com.mwdsp.localSearch.LocalSearch2xNBI;

public class App {
    public static void main(String[] args) {
        Algorithm alg = new Algorithm();
        BufferedWriter wr = alg.createNewFile(Constants.RESULTS_FILENAME);
        String results = "RESULTS\n";

        for (String filename : Constants.T1_ALL_FILENAMES) {
            // Initialize the CustomRandom class
            CustomRandom.init();

            // Create Instance
            Instance ins = new Instance(filename);
            System.out.println("\n" + filename);

            // Create Local Search
            LocalSearch ls = new LocalSearch1xNBI();

            // Execute Algorithm            
            Solution solution = alg.executeGrasp(ins, true, ls, 0.25);

            // Execute Iterative Greedy
            solution = alg.executeIterativeGreedyGDGC(solution, 0.4f, 40, ls, 0.25);

            // Print Solution and save it in the results file
            solution.printSolution();
            System.out.println("-----------------------------------------------------------------------------------");
            results += solution.getSolutionString(filename);
        }

        // Write all the results in the output file
        try {
            wr.write(results);
            wr.close();
        } catch (IOException e) {}
    }
}