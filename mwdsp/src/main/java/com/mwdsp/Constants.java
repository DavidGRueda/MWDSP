package com.mwdsp;

public final class Constants {
    private Constants() {}

    public static final String RESULTS_FILENAME = "ALL_RESULTS";

    // Problem subset for preliminary experiments
    public static final String[] PROBLEM_SUBSET_FILENAMES = {
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

    // All T1 instances for final experiment
    public static final String[] T1_ALL_FILENAMES = {
        "Problem.dat_50_50_0",
        "Problem.dat_50_50_1",
        "Problem.dat_50_50_2",
        "Problem.dat_50_50_3",
        "Problem.dat_50_50_4",
        "Problem.dat_50_50_5",
        "Problem.dat_50_50_6",
        "Problem.dat_50_50_7",
        "Problem.dat_50_50_8",
        "Problem.dat_50_50_9",
        "Problem.dat_50_100_0",
        "Problem.dat_50_100_1",
        "Problem.dat_50_100_2",
        "Problem.dat_50_100_3",
        "Problem.dat_50_100_4",
        "Problem.dat_50_100_5",
        "Problem.dat_50_100_6",
        "Problem.dat_50_100_7",
        "Problem.dat_50_100_8",
        "Problem.dat_50_100_9",
        "Problem.dat_50_250_0",
        "Problem.dat_50_250_1",
        "Problem.dat_50_250_2",
        "Problem.dat_50_250_3",
        "Problem.dat_50_250_4",
        "Problem.dat_50_250_5",
        "Problem.dat_50_250_6",
        "Problem.dat_50_250_7",
        "Problem.dat_50_250_8",
        "Problem.dat_50_250_9",
        "Problem.dat_50_500_0",
        "Problem.dat_50_500_1",
        "Problem.dat_50_500_2",
        "Problem.dat_50_500_3",
        "Problem.dat_50_500_4",
        "Problem.dat_50_500_5",
        "Problem.dat_50_500_6",
        "Problem.dat_50_500_7",
        "Problem.dat_50_500_8",
        "Problem.dat_50_500_9",
        "Problem.dat_50_750_0",
        "Problem.dat_50_750_1",
        "Problem.dat_50_750_2",
        "Problem.dat_50_750_3",
        "Problem.dat_50_750_4",
        "Problem.dat_50_750_5",
        "Problem.dat_50_750_6",
        "Problem.dat_50_750_7",
        "Problem.dat_50_750_8",
        "Problem.dat_50_750_9",
        "Problem.dat_50_1000_0",
        "Problem.dat_50_1000_1",
        "Problem.dat_50_1000_2",
        "Problem.dat_50_1000_3",
        "Problem.dat_50_1000_4",
        "Problem.dat_50_1000_5",
        "Problem.dat_50_1000_6",
        "Problem.dat_50_1000_7",
        "Problem.dat_50_1000_8",
        "Problem.dat_50_1000_9",
        "Problem.dat_100_100_0",
        "Problem.dat_100_100_1",
        "Problem.dat_100_100_2",
        "Problem.dat_100_100_3",
        "Problem.dat_100_100_4",
        "Problem.dat_100_100_5",
        "Problem.dat_100_100_6",
        "Problem.dat_100_100_7",
        "Problem.dat_100_100_8",
        "Problem.dat_100_100_9",
        "Problem.dat_100_250_0",
        "Problem.dat_100_250_1",
        "Problem.dat_100_250_2",
        "Problem.dat_100_250_3",
        "Problem.dat_100_250_4",
        "Problem.dat_100_250_5",
        "Problem.dat_100_250_6",
        "Problem.dat_100_250_7",
        "Problem.dat_100_250_8",
        "Problem.dat_100_250_9",
        "Problem.dat_100_500_0",
        "Problem.dat_100_500_1",
        "Problem.dat_100_500_2",
        "Problem.dat_100_500_3",
        "Problem.dat_100_500_4",
        "Problem.dat_100_500_5",
        "Problem.dat_100_500_6",
        "Problem.dat_100_500_7",
        "Problem.dat_100_500_8",
        "Problem.dat_100_500_9",
        "Problem.dat_100_750_0",
        "Problem.dat_100_750_1",
        "Problem.dat_100_750_2",
        "Problem.dat_100_750_3",
        "Problem.dat_100_750_4",
        "Problem.dat_100_750_5",
        "Problem.dat_100_750_6",
        "Problem.dat_100_750_7",
        "Problem.dat_100_750_8",
        "Problem.dat_100_750_9",
        "Problem.dat_100_1000_0",
        "Problem.dat_100_1000_1",
        "Problem.dat_100_1000_2",
        "Problem.dat_100_1000_3",
        "Problem.dat_100_1000_4",
        "Problem.dat_100_1000_5",
        "Problem.dat_100_1000_6",
        "Problem.dat_100_1000_7",
        "Problem.dat_100_1000_8",
        "Problem.dat_100_1000_9",
        "Problem.dat_100_2000_0",
        "Problem.dat_100_2000_1",
        "Problem.dat_100_2000_2",
        "Problem.dat_100_2000_3",
        "Problem.dat_100_2000_4",
        "Problem.dat_100_2000_5",
        "Problem.dat_100_2000_6",
        "Problem.dat_100_2000_7",
        "Problem.dat_100_2000_8",
        "Problem.dat_100_2000_9",
        "Problem.dat_150_150_0",
        "Problem.dat_150_150_1",
        "Problem.dat_150_150_2",
        "Problem.dat_150_150_3",
        "Problem.dat_150_150_4",
        "Problem.dat_150_150_5",
        "Problem.dat_150_150_6",
        "Problem.dat_150_150_7",
        "Problem.dat_150_150_8",
        "Problem.dat_150_150_9",
        "Problem.dat_150_250_0",
        "Problem.dat_150_250_1",
        "Problem.dat_150_250_2",
        "Problem.dat_150_250_3",
        "Problem.dat_150_250_4",
        "Problem.dat_150_250_5",
        "Problem.dat_150_250_6",
        "Problem.dat_150_250_7",
        "Problem.dat_150_250_8",
        "Problem.dat_150_250_9",
        "Problem.dat_150_500_0",
        "Problem.dat_150_500_1",
        "Problem.dat_150_500_2",
        "Problem.dat_150_500_3",
        "Problem.dat_150_500_4",
        "Problem.dat_150_500_5",
        "Problem.dat_150_500_6",
        "Problem.dat_150_500_7",
        "Problem.dat_150_500_8",
        "Problem.dat_150_500_9",
        "Problem.dat_150_750_0",
        "Problem.dat_150_750_1",
        "Problem.dat_150_750_2",
        "Problem.dat_150_750_3",
        "Problem.dat_150_750_4",
        "Problem.dat_150_750_5",
        "Problem.dat_150_750_6",
        "Problem.dat_150_750_7",
        "Problem.dat_150_750_8",
        "Problem.dat_150_750_9",
        "Problem.dat_150_1000_0",
        "Problem.dat_150_1000_1",
        "Problem.dat_150_1000_2",
        "Problem.dat_150_1000_3",
        "Problem.dat_150_1000_4",
        "Problem.dat_150_1000_5",
        "Problem.dat_150_1000_6",
        "Problem.dat_150_1000_7",
        "Problem.dat_150_1000_8",
        "Problem.dat_150_1000_9",
        "Problem.dat_150_2000_0",
        "Problem.dat_150_2000_1",
        "Problem.dat_150_2000_2",
        "Problem.dat_150_2000_3",
        "Problem.dat_150_2000_4",
        "Problem.dat_150_2000_5",
        "Problem.dat_150_2000_6",
        "Problem.dat_150_2000_7",
        "Problem.dat_150_2000_8",
        "Problem.dat_150_2000_9",
        "Problem.dat_150_3000_0",
        "Problem.dat_150_3000_1",
        "Problem.dat_150_3000_2",
        "Problem.dat_150_3000_3",
        "Problem.dat_150_3000_4",
        "Problem.dat_150_3000_5",
        "Problem.dat_150_3000_6",
        "Problem.dat_150_3000_7",
        "Problem.dat_150_3000_8",
        "Problem.dat_150_3000_9",
        "Problem.dat_200_250_0",
        "Problem.dat_200_250_1",
        "Problem.dat_200_250_2",
        "Problem.dat_200_250_3",
        "Problem.dat_200_250_4",
        "Problem.dat_200_250_5",
        "Problem.dat_200_250_6",
        "Problem.dat_200_250_7",
        "Problem.dat_200_250_8",
        "Problem.dat_200_250_9",
        "Problem.dat_200_500_0",
        "Problem.dat_200_500_1",
        "Problem.dat_200_500_2",
        "Problem.dat_200_500_3",
        "Problem.dat_200_500_4",
        "Problem.dat_200_500_5",
        "Problem.dat_200_500_6",
        "Problem.dat_200_500_7",
        "Problem.dat_200_500_8",
        "Problem.dat_200_500_9",
        "Problem.dat_200_750_0",
        "Problem.dat_200_750_1",
        "Problem.dat_200_750_2",
        "Problem.dat_200_750_3",
        "Problem.dat_200_750_4",
        "Problem.dat_200_750_5",
        "Problem.dat_200_750_6",
        "Problem.dat_200_750_7",
        "Problem.dat_200_750_8",
        "Problem.dat_200_750_9",
        "Problem.dat_200_1000_0",
        "Problem.dat_200_1000_1",
        "Problem.dat_200_1000_2",
        "Problem.dat_200_1000_3",
        "Problem.dat_200_1000_4",
        "Problem.dat_200_1000_5",
        "Problem.dat_200_1000_6",
        "Problem.dat_200_1000_7",
        "Problem.dat_200_1000_8",
        "Problem.dat_200_1000_9",
        "Problem.dat_200_2000_0",
        "Problem.dat_200_2000_1",
        "Problem.dat_200_2000_2",
        "Problem.dat_200_2000_3",
        "Problem.dat_200_2000_4",
        "Problem.dat_200_2000_5",
        "Problem.dat_200_2000_6",
        "Problem.dat_200_2000_7",
        "Problem.dat_200_2000_8",
        "Problem.dat_200_2000_9",
        "Problem.dat_200_3000_0",
        "Problem.dat_200_3000_1",
        "Problem.dat_200_3000_2",
        "Problem.dat_200_3000_3",
        "Problem.dat_200_3000_4",
        "Problem.dat_200_3000_5",
        "Problem.dat_200_3000_6",
        "Problem.dat_200_3000_7",
        "Problem.dat_200_3000_8",
        "Problem.dat_200_3000_9",
        "Problem.dat_250_250_0",
        "Problem.dat_250_250_1",
        "Problem.dat_250_250_2",
        "Problem.dat_250_250_3",
        "Problem.dat_250_250_4",
        "Problem.dat_250_250_5",
        "Problem.dat_250_250_6",
        "Problem.dat_250_250_7",
        "Problem.dat_250_250_8",
        "Problem.dat_250_250_9",
        "Problem.dat_250_500_0",
        "Problem.dat_250_500_1",
        "Problem.dat_250_500_2",
        "Problem.dat_250_500_3",
        "Problem.dat_250_500_4",
        "Problem.dat_250_500_5",
        "Problem.dat_250_500_6",
        "Problem.dat_250_500_7",
        "Problem.dat_250_500_8",
        "Problem.dat_250_500_9",
        "Problem.dat_250_750_0",
        "Problem.dat_250_750_1",
        "Problem.dat_250_750_2",
        "Problem.dat_250_750_3",
        "Problem.dat_250_750_4",
        "Problem.dat_250_750_5",
        "Problem.dat_250_750_6",
        "Problem.dat_250_750_7",
        "Problem.dat_250_750_8",
        "Problem.dat_250_750_9",
        "Problem.dat_250_1000_0",
        "Problem.dat_250_1000_1",
        "Problem.dat_250_1000_2",
        "Problem.dat_250_1000_3",
        "Problem.dat_250_1000_4",
        "Problem.dat_250_1000_5",
        "Problem.dat_250_1000_6",
        "Problem.dat_250_1000_7",
        "Problem.dat_250_1000_8",
        "Problem.dat_250_1000_9",
        "Problem.dat_250_2000_0",
        "Problem.dat_250_2000_1",
        "Problem.dat_250_2000_2",
        "Problem.dat_250_2000_3",
        "Problem.dat_250_2000_4",
        "Problem.dat_250_2000_5",
        "Problem.dat_250_2000_6",
        "Problem.dat_250_2000_7",
        "Problem.dat_250_2000_8",
        "Problem.dat_250_2000_9",
        "Problem.dat_250_3000_0",
        "Problem.dat_250_3000_1",
        "Problem.dat_250_3000_2",
        "Problem.dat_250_3000_3",
        "Problem.dat_250_3000_4",
        "Problem.dat_250_3000_5",
        "Problem.dat_250_3000_6",
        "Problem.dat_250_3000_7",
        "Problem.dat_250_3000_8",
        "Problem.dat_250_3000_9",
        "Problem.dat_250_5000_0",
        "Problem.dat_250_5000_1",
        "Problem.dat_250_5000_2",
        "Problem.dat_250_5000_3",
        "Problem.dat_250_5000_4",
        "Problem.dat_250_5000_5",
        "Problem.dat_250_5000_6",
        "Problem.dat_250_5000_7",
        "Problem.dat_250_5000_8",
        "Problem.dat_250_5000_9",
        "Problem.dat_300_300_0",
        "Problem.dat_300_300_1",
        "Problem.dat_300_300_2",
        "Problem.dat_300_300_3",
        "Problem.dat_300_300_4",
        "Problem.dat_300_300_5",
        "Problem.dat_300_300_6",
        "Problem.dat_300_300_7",
        "Problem.dat_300_300_8",
        "Problem.dat_300_300_9",
        "Problem.dat_300_500_0",
        "Problem.dat_300_500_1",
        "Problem.dat_300_500_2",
        "Problem.dat_300_500_3",
        "Problem.dat_300_500_4",
        "Problem.dat_300_500_5",
        "Problem.dat_300_500_6",
        "Problem.dat_300_500_7",
        "Problem.dat_300_500_8",
        "Problem.dat_300_500_9",
        "Problem.dat_300_750_0",
        "Problem.dat_300_750_1",
        "Problem.dat_300_750_2",
        "Problem.dat_300_750_3",
        "Problem.dat_300_750_4",
        "Problem.dat_300_750_5",
        "Problem.dat_300_750_6",
        "Problem.dat_300_750_7",
        "Problem.dat_300_750_8",
        "Problem.dat_300_750_9",
        "Problem.dat_300_1000_0",
        "Problem.dat_300_1000_1",
        "Problem.dat_300_1000_2",
        "Problem.dat_300_1000_3",
        "Problem.dat_300_1000_4",
        "Problem.dat_300_1000_5",
        "Problem.dat_300_1000_6",
        "Problem.dat_300_1000_7",
        "Problem.dat_300_1000_8",
        "Problem.dat_300_1000_9",
        "Problem.dat_300_2000_0",
        "Problem.dat_300_2000_1",
        "Problem.dat_300_2000_2",
        "Problem.dat_300_2000_3",
        "Problem.dat_300_2000_4",
        "Problem.dat_300_2000_5",
        "Problem.dat_300_2000_6",
        "Problem.dat_300_2000_7",
        "Problem.dat_300_2000_8",
        "Problem.dat_300_2000_9",
        "Problem.dat_300_3000_0",
        "Problem.dat_300_3000_1",
        "Problem.dat_300_3000_2",
        "Problem.dat_300_3000_3",
        "Problem.dat_300_3000_4",
        "Problem.dat_300_3000_5",
        "Problem.dat_300_3000_6",
        "Problem.dat_300_3000_7",
        "Problem.dat_300_3000_8",
        "Problem.dat_300_3000_9",
        "Problem.dat_300_5000_0",
        "Problem.dat_300_5000_1",
        "Problem.dat_300_5000_2",
        "Problem.dat_300_5000_3",
        "Problem.dat_300_5000_4",
        "Problem.dat_300_5000_5",
        "Problem.dat_300_5000_6",
        "Problem.dat_300_5000_7",
        "Problem.dat_300_5000_8",
        "Problem.dat_300_5000_9",
        "Problem.dat_500_500_0",
        "Problem.dat_500_500_1",
        "Problem.dat_500_500_2",
        "Problem.dat_500_500_3",
        "Problem.dat_500_500_4",
        "Problem.dat_500_500_5",
        "Problem.dat_500_500_6",
        "Problem.dat_500_500_7",
        "Problem.dat_500_500_8",
        "Problem.dat_500_500_9",
        "Problem.dat_500_1000_0",
        "Problem.dat_500_1000_1",
        "Problem.dat_500_1000_2",
        "Problem.dat_500_1000_3",
        "Problem.dat_500_1000_4",
        "Problem.dat_500_1000_5",
        "Problem.dat_500_1000_6",
        "Problem.dat_500_1000_7",
        "Problem.dat_500_1000_8",
        "Problem.dat_500_1000_9",
        "Problem.dat_500_2000_0",
        "Problem.dat_500_2000_1",
        "Problem.dat_500_2000_2",
        "Problem.dat_500_2000_3",
        "Problem.dat_500_2000_4",
        "Problem.dat_500_2000_5",
        "Problem.dat_500_2000_6",
        "Problem.dat_500_2000_7",
        "Problem.dat_500_2000_8",
        "Problem.dat_500_2000_9",
        "Problem.dat_500_5000_0",
        "Problem.dat_500_5000_1",
        "Problem.dat_500_5000_2",
        "Problem.dat_500_5000_3",
        "Problem.dat_500_5000_4",
        "Problem.dat_500_5000_5",
        "Problem.dat_500_5000_6",
        "Problem.dat_500_5000_7",
        "Problem.dat_500_5000_8",
        "Problem.dat_500_5000_9",
        "Problem.dat_500_10000_0",
        "Problem.dat_500_10000_1",
        "Problem.dat_500_10000_2",
        "Problem.dat_500_10000_3",
        "Problem.dat_500_10000_4",
        "Problem.dat_500_10000_5",
        "Problem.dat_500_10000_6",
        "Problem.dat_500_10000_7",
        "Problem.dat_500_10000_8",
        "Problem.dat_500_10000_9",
        "Problem.dat_800_0_0",
        "Problem.dat_800_0_1",
        "Problem.dat_800_0_2",
        "Problem.dat_800_0_3",
        "Problem.dat_800_0_4",
        "Problem.dat_800_0_5",
        "Problem.dat_800_0_6",
        "Problem.dat_800_0_7",
        "Problem.dat_800_0_8",
        "Problem.dat_800_0_9",
        "Problem.dat_800_1000_0",
        "Problem.dat_800_1000_1",
        "Problem.dat_800_1000_2",
        "Problem.dat_800_1000_3",
        "Problem.dat_800_1000_4",
        "Problem.dat_800_1000_5",
        "Problem.dat_800_1000_6",
        "Problem.dat_800_1000_7",
        "Problem.dat_800_1000_8",
        "Problem.dat_800_1000_9",
        "Problem.dat_800_2000_0",
        "Problem.dat_800_2000_1",
        "Problem.dat_800_2000_2",
        "Problem.dat_800_2000_3",
        "Problem.dat_800_2000_4",
        "Problem.dat_800_2000_5",
        "Problem.dat_800_2000_6",
        "Problem.dat_800_2000_7",
        "Problem.dat_800_2000_8",
        "Problem.dat_800_2000_9",
        "Problem.dat_800_5000_0",
        "Problem.dat_800_5000_1",
        "Problem.dat_800_5000_2",
        "Problem.dat_800_5000_3",
        "Problem.dat_800_5000_4",
        "Problem.dat_800_5000_5",
        "Problem.dat_800_5000_6",
        "Problem.dat_800_5000_7",
        "Problem.dat_800_5000_8",
        "Problem.dat_800_5000_9",
        "Problem.dat_800_10000_0",
        "Problem.dat_800_10000_1",
        "Problem.dat_800_10000_2",
        "Problem.dat_800_10000_3",
        "Problem.dat_800_10000_4",
        "Problem.dat_800_10000_5",
        "Problem.dat_800_10000_6",
        "Problem.dat_800_10000_7",
        "Problem.dat_800_10000_8",
        "Problem.dat_800_10000_9",
        "Problem.dat_1000_1000_0",
        "Problem.dat_1000_1000_1",
        "Problem.dat_1000_1000_2",
        "Problem.dat_1000_1000_3",
        "Problem.dat_1000_1000_4",
        "Problem.dat_1000_1000_5",
        "Problem.dat_1000_1000_6",
        "Problem.dat_1000_1000_7",
        "Problem.dat_1000_1000_8",
        "Problem.dat_1000_1000_9",
        "Problem.dat_1000_5000_0",
        "Problem.dat_1000_5000_1",
        "Problem.dat_1000_5000_2",
        "Problem.dat_1000_5000_3",
        "Problem.dat_1000_5000_4",
        "Problem.dat_1000_5000_5",
        "Problem.dat_1000_5000_6",
        "Problem.dat_1000_5000_7",
        "Problem.dat_1000_5000_8",
        "Problem.dat_1000_5000_9",
        "Problem.dat_1000_10000_0",
        "Problem.dat_1000_10000_1",
        "Problem.dat_1000_10000_2",
        "Problem.dat_1000_10000_3",
        "Problem.dat_1000_10000_4",
        "Problem.dat_1000_10000_5",
        "Problem.dat_1000_10000_6",
        "Problem.dat_1000_10000_7",
        "Problem.dat_1000_10000_8",
        "Problem.dat_1000_10000_9",
        "Problem.dat_1000_15000_0",
        "Problem.dat_1000_15000_1",
        "Problem.dat_1000_15000_2",
        "Problem.dat_1000_15000_3",
        "Problem.dat_1000_15000_4",
        "Problem.dat_1000_15000_5",
        "Problem.dat_1000_15000_6",
        "Problem.dat_1000_15000_7",
        "Problem.dat_1000_15000_8",
        "Problem.dat_1000_15000_9",
        "Problem.dat_1000_20000_0",
        "Problem.dat_1000_20000_1",
        "Problem.dat_1000_20000_2",
        "Problem.dat_1000_20000_3",
        "Problem.dat_1000_20000_4",
        "Problem.dat_1000_20000_5",
        "Problem.dat_1000_20000_6",
        "Problem.dat_1000_20000_7",
        "Problem.dat_1000_20000_8",
        "Problem.dat_1000_20000_9"
    };
}
