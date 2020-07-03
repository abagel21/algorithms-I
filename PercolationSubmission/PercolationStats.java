import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private final double[] percolatedThreshold;
    private final int trials;
    private final double ninetyFive;


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Input must be greater than 0");
        }
        ninetyFive = 1.96;
        percolatedThreshold = new double[trials];
        this.trials = trials;
        for (int i = 0; i < trials; i++) {
            Percolation x = new Percolation(n);
            while (!x.percolates()) {
                x.open((int) (Math.floor(StdRandom.uniform(1, n + 1))), (int) (Math.floor(StdRandom.uniform(1, n + 1))));
            }
            percolatedThreshold[i] = (x.numberOfOpenSites() / (n * n * 1.0));
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percolatedThreshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percolatedThreshold);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (stddev() * ninetyFive) / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (stddev() * ninetyFive) / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats y = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean = " + y.mean());
        System.out.println("stddev = " + y.stddev());
        System.out.println("95% confidence interval = [" + y.confidenceLo() + ", " + y.confidenceHi() + "]");
    }
}
