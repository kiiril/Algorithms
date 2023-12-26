package algorithms1.week1.percolation;


import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;

    private double[] trialsResults;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        trialsResults = new double[trials];
        for (int i = 0; i < trials; i++) {
            trialsResults[i] = runTrial(n);
        }
    }

    private double runTrial(int n) {
        Percolation system = new Percolation(n);
        while (!system.percolates()) {
            int i = StdRandom.uniformInt(n) + 1;
            int j = StdRandom.uniformInt(n) + 1;
            system.open(i, j);
        }
        return system.numberOfOpenSites() * 1.0 / (n * n);
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(trialsResults);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(trialsResults);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (CONFIDENCE_95 * stddev() / Math.sqrt(trialsResults.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * stddev() / Math.sqrt(trialsResults.length));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        System.out.printf("mean = %s\n", stats.mean());
        System.out.printf("stddev = %s\n", stats.stddev());
        System.out.printf("95%% confidence interval = [%s, %s]\n", stats.confidenceLo(), stats.confidenceHi());
    }

}
