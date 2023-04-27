package com.mwdsp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import com.mwdsp.builders.GraspBuilder;
import com.mwdsp.builders.GreedyBuilder;
import com.mwdsp.builders.RandomBuilder;
import com.mwdsp.iterativeGreedy.IterativeGreedy;
import com.mwdsp.iterativeGreedy.IterativeGreedyGDGC;
import com.mwdsp.iterativeGreedy.IterativeGreedyRDGC;
import com.mwdsp.localSearch.LocalSearch;

public class Algorithm {

    /**
     * Executes and returns a random built solution from a problem instance. 
     * @param i - Instance of the problem.
     * @param purge - True if the solution should be purged. False otherwise. 
     * @param localSearch - LocalSearch that should be executed. Null if no local search is desired.
     * @param N_IT - Number of iterations performed to find a better solution.
     * @return Solution - Built solution.
     */
    public Solution executeRandom(Instance i, boolean purge, LocalSearch localSearch, int N_IT) {
        // Solution to be returned. Best one found 
        Solution solution = null;
        
        // Variables used to take times
        long start, finish, builderTime, purgeTime, localSearchTime;        
        builderTime = 0;
        purgeTime = 0;
        localSearchTime = 0;
        
        // Variables used to update best solution if needed
        double bestWeight = Double.POSITIVE_INFINITY;
        double localWeight;

        RandomBuilder r = new RandomBuilder();

        //Create and open a file to store all Random builder step results
        BufferedWriter wr = createNewFile(i.getFilename());
        String line = "Random builder:\n\n";

        // Execute the random builder, purge and do local search (if required), and update best solution found if needed
        for (int j = 0; j < N_IT; j++) {

            //Builder stage
            start = System.currentTimeMillis();
            Solution sol = r.execute(i);
            finish = System.currentTimeMillis();
            builderTime += finish - start;
            line += sol.getTotalWeight() + " - ";

            // Purge stage
            if(purge){
                start = System.currentTimeMillis();
                sol.purgeSolution();
                finish = System.currentTimeMillis();
                purgeTime += finish - start;
                line += sol.getTotalWeight() + " - ";
            }

            // Local Search stage
            if(localSearch != null){
                start = System.currentTimeMillis();
                sol = localSearch.execute(sol);
                finish = System.currentTimeMillis();
                localSearchTime += finish - start;
                line += sol.getTotalWeight() + " - ";
            }

            // Update solution stage
            if ((localWeight = sol.getTotalWeight()) < bestWeight) {
                bestWeight = localWeight;
                solution = sol;
                line += "UPDATED";
            }
            line += "\n";
        }

        // Write all the results in the corresponding file
        try {
            wr.write(line);
            wr.close();
        } catch (IOException e) {}

        printBuilderTimes(builderTime, purgeTime, localSearchTime);
        return solution;
    }

    /**
     * Executes and returns a greedy built solution from a problem instance. 
     * @param i - Instance of the problem.
     * @param purge - True if the solution should be purged. False otherwise. 
     * @param localSearch - LocalSearch that should be executed. Null if no local search is desired.
     * @return Solution - Built solution.
     */
    public Solution executeGreedy(Instance i, boolean purge, LocalSearch localSearch) {
        // Variables used to take times
        long start, finish, builderTime, purgeTime, localSearchTime;
        builderTime = 0;
        purgeTime = 0;
        localSearchTime = 0;

        GreedyBuilder builder = new GreedyBuilder();

        //Create and open a file to store Greedy step results
        BufferedWriter wr = createNewFile(i.getFilename());
        String line = "Greedy builder:\n\n";

        // Builder stage
        start = System.currentTimeMillis();
        Solution solution = builder.execute(i);
        finish = System.currentTimeMillis();
        builderTime += finish - start;
        line += solution.getTotalWeight() + " - ";

        // Purge stage
        if(purge){
            start = System.currentTimeMillis();
            solution.purgeSolution();
            finish = System.currentTimeMillis();
            purgeTime += finish - start;
            line += solution.getTotalWeight() + " - ";
        }

        // Local Search stage
        if(localSearch != null){
            start = System.currentTimeMillis();
            solution = localSearch.execute(solution);
            finish = System.currentTimeMillis();
            localSearchTime += finish - start;
            line += solution.getTotalWeight();
        }

        // Write all the results in the corresponding file
        try {
            wr.write(line);
            wr.close();
        } catch (IOException e) {}

        printBuilderTimes(builderTime, purgeTime, localSearchTime);
        return solution;
    }

    /**
     * Executes and returns a random GRASP built solution from a problem instance. 
     * @param i - Instance of the problem.
     * @param purge - True if the solution should be purged. False otherwise. 
     * @param localSearch - LocalSearch that should be executed. Null if no local search is desired.
     * @return Solution - Built solution.
     */
    public Solution executeRandomGrasp(Instance i, boolean purge, LocalSearch localSearch) {
        // Solution to be returned. Best one found 
        Solution solution = null;

        // Variables used to take times
        long start, finish, builderTime, purgeTime, localSearchTime;
        builderTime = 0;
        purgeTime = 0;
        localSearchTime = 0;

        // Variables used to update best solution if needed
        double bestWeight = Double.POSITIVE_INFINITY;
        double localWeight;

        // Variables to generate a new Grasp builder with a random alpha for each iteration
        GraspBuilder builder;
        double alpha;

        // Variables to generate a randomized alpha
        Random random = new Random();
        long seed = random.nextLong();
        random.setSeed(seed);

        //Create and open a file to store all Random GRASP step results
        BufferedWriter wr = createNewFile(i.getFilename());
        String line = "GRASP with RANDOM alpha\n\n";

        // Execute the GRASP builder, purge and do local search (if required), and update best solution found if needed 
        for (int j = 0; j < 100; j++) {
            alpha = random.nextDouble();

            // Get new alpha while alpha == 0
            while (alpha == 0.0)
                alpha = random.nextDouble();
            line += String.format("%.2f", alpha) + " -> ";

            // Builder stage
            start = System.currentTimeMillis();
            builder = new GraspBuilder(alpha);
            Solution sol = builder.execute(i);
            finish = System.currentTimeMillis();
            builderTime += finish - start;
            line += sol.getTotalWeight() + " - ";

             // Purge stage
             if(purge){
                start = System.currentTimeMillis();
                sol.purgeSolution();
                finish = System.currentTimeMillis();
                purgeTime += finish - start;
                line += sol.getTotalWeight() + " - ";
            }

            // Local Search stage
            if(localSearch != null){
                start = System.currentTimeMillis();
                sol = localSearch.execute(sol);
                finish = System.currentTimeMillis();
                localSearchTime += finish - start;
                line += sol.getTotalWeight() + " - ";
            }

            // Update solution stage
            if ((localWeight = sol.getTotalWeight()) < bestWeight) {
                bestWeight = localWeight;
                solution = sol;
                line += "UPDATED";
            }
            line += "\n";
        }

        // Write all the results in the corresponding file
        try {
            wr.write(line);
            wr.close();
        } catch (IOException e) {}

        printBuilderTimes(builderTime, purgeTime, localSearchTime);
        return solution;
    }

    /**
     * Executes and returns a GRASP built solution with a defined alpha from a problem instance.
     * @param i - Instance of the problem.
     * @param purge - True if the solution should be purged. False otherwise. 
     * @param localSearch - LocalSearch that should be executed. Null if no local search is desired.
     * @param alpha - Defines the restrictiveness of the RCL (Restricted Candidate List) when adding a new node. 
     *                Domain: [0.0 - 1.0]
     * @return Solution - Built solution.
     */
    public Solution executeGrasp(Instance i, boolean purge, LocalSearch localSearch, double alpha) {
        // Solution to be returned. Best one found 
        Solution solution = null;        

        // Variables used to take times
        long start, finish, builderTime, purgeTime, localSearchTime;        
        builderTime = 0;
        purgeTime = 0;
        localSearchTime = 0;
        
        // Variables used to update best solution if needed
        double bestWeight = Double.POSITIVE_INFINITY;
        double localWeight;

        GraspBuilder builder = new GraspBuilder(alpha);

        //Create and open a file to store all GRASP step results
        BufferedWriter wr = createNewFile(i.getFilename());
        String line = "GRASP with alpha " + alpha + "\n\n";

        // Execute the GRASP builder, purge and do local search (if required), and update best solution found if needed
        for (int j = 0; j < 100; j++) {
            
            //Builder stage
            start = System.currentTimeMillis();
            builder = new GraspBuilder(alpha);
            Solution sol = builder.execute(i);
            finish = System.currentTimeMillis();
            builderTime += finish - start;
            line += sol.getTotalWeight() + " - ";

            // Purge stage
            if(purge){
                start = System.currentTimeMillis();
                sol.purgeSolution();
                finish = System.currentTimeMillis();
                purgeTime += finish - start;
                line += sol.getTotalWeight() + " - ";
            }

            // Local Search stage
            if(localSearch != null){
                start = System.currentTimeMillis();
                sol = localSearch.execute(sol);
                finish = System.currentTimeMillis();
                localSearchTime += finish - start;
                line += sol.getTotalWeight() + " - ";
            }

            // Update solution stage
            if ((localWeight = sol.getTotalWeight()) < bestWeight) {
                bestWeight = localWeight;
                solution = sol;
                line += "UPDATED";
            }            
            line += "\n";
        }

        // Write all the results in the corresponding file
        try {
            wr.write(line);
            wr.close();
        } catch (IOException e) {}
       
        printBuilderTimes(builderTime, purgeTime, localSearchTime);
        return solution;
    }

    /**
     * Executes a Random Destroy Greedy Construction Iterative Greedy algorithm and returns best solution found.
     * @param solution - Previously built solution.
     * @param beta - Defines the percentage of destruction of the actual solution.
     * @param stopIterations - Defines how many iterations without an improvement should be done before stopping.
     * @param ls - Local search that will be used to improve the candidate solutions. 
     * @return Solution - Best solution found.
     */
    public Solution executeIterativeGreedyRDGC(Solution solution, float beta, int stopIterations, LocalSearch ls){
        long start = System.currentTimeMillis();
        try{
            IterativeGreedy it = new IterativeGreedyRDGC();
            solution = it.execute(solution, beta, stopIterations, ls, 0);
        } catch (CloneNotSupportedException e) {}
        long finish = System.currentTimeMillis();

        System.out.println("Iterative Greedy time: " + (finish - start) + "ms");
        return solution;
    }

    /**
     * Executes a Greedy Destroy GRASP Construction Iterative Greedy algorithm and returns best solution found.
     * @param solution - Previously built solution.
     * @param beta - Defines the percentage of destruction of the actual solution.
     * @param stopIterations - Defines how many iterations without an improvement should be done before stopping.
     * @param ls - Local search that will be used to improve the candidate solutions. 
     * @param alpha - Defines the restrictiveness of the RCL (Restricted Candidate List) for the GRASP construcion. 
     *                Domain: [0.0 - 1.0]
     * @return Solution - Best solution found.
     */
    public Solution executeIterativeGreedyGDGC(Solution solution, float beta, int stopIterations, LocalSearch ls, double alpha){
        long start = System.currentTimeMillis();
        try{
            IterativeGreedy it = new IterativeGreedyGDGC();
            solution = it.execute(solution, beta, stopIterations, ls, alpha);
        } catch (CloneNotSupportedException e) {}
        long finish = System.currentTimeMillis();

        System.out.println("Iterative Greedy time: " + (finish - start) + "ms");
        return solution;
    }

    /**
     * Prints the different stage time and the total of the build phase. 
     * @param builderTime - Builder stage time.
     * @param purgeTime - Purge stage time.
     * @param localSearchTime - Local Search stage time. 
     */
    public void printBuilderTimes(long builderTime, long purgeTime, long localSearchTime){
        System.out.println("Builder time: " + builderTime + " ms");
        System.out.println("Purge time: " + purgeTime + " ms");
        System.out.println("LocalSearch time: " + localSearchTime + " ms");
        System.out.println("Total build time: " + (builderTime + purgeTime + localSearchTime) + " ms");        
    }

    /**
     * Creates a new file and returns a writer for that file.
     * @param filename - Name of the file.
     * @return BufferedWriter - Writer for the file
     */
    private BufferedWriter createNewFile(String filename){
        // Deletes the previous file and creates a new one
        String filepath = System.getProperty("user.dir") + "/mwdsp/src/logs/" + filename + ".txt";
        File fold = new File(filepath);
        fold.delete();
        File fnew = new File(filepath);

        try{
            return new BufferedWriter(
                new FileWriter(fnew, false)
            );
        } catch(IOException exception){
            System.out.println(exception);
            return null;
        }
    }
}
