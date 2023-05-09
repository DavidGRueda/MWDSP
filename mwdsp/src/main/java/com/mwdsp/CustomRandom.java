package com.mwdsp;
import java.util.Random;

/**
 *  Abstract static class for random generation with testing purposes
 */
public abstract class CustomRandom {
    private static final long seed = 2110059654845314774L;
    private static Random rand;

    public static void init(){
        rand = new Random(seed);
    }
    
    public static int nextInt() {
        return rand.nextInt();
    }

    public static int nextInt(int bound){
        return rand.nextInt(bound);
    }

    public static double nextDouble(){
        return rand.nextDouble();
    }
}
