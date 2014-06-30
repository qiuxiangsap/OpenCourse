/**
 * Compilation: javac PercolationStats.java Execution: java PercolationStats
 * 
 * Percolator probability simulator
 * 
 * @author Zhongcun Wang
 * @date Feb 14th, 2014
 * 
 */

public class PercolationStats {

    public static void main(String[] args) {
	if (args.length < 2) {
	    System.out.println("Please Input Paramenter N, T");
	} else {
	    int n = Integer.parseInt(args[0]);
	    int t = Integer.parseInt(args[1]);

	    PercolationStats pStats = new PercolationStats(n, t);
	    StdOut.printf("mean                    = %f\n", pStats.mean());
	    StdOut.printf("stddev                  = %f\n", pStats.stddev());
	    StdOut.printf("95%% confidence interval = %f,%f\n",
		    pStats.confidenceLo(), pStats.confidenceHi());
	}

    }

    private double[] percentage;

    public PercolationStats(int N, int T) // perform T independent computational
					  // // experiments on an N-by-N grid
    {
	if (N <= 0 || T <= 0) {
	    throw new IllegalArgumentException();
	}

	percentage = new double[T];

	for (int i = 0; i < T; i++) {
	    Percolation perco = new Percolation(N);

	    int numOfOpen = 0;
	    while (!perco.percolates()) {
		int row = randInt(1, N);
		int column = randInt(1, N);
		if (!perco.isOpen(row, column)) {
		    perco.open(row, column);
		    numOfOpen++;
		}
	    }
	    percentage[i] = 1.0 * numOfOpen / (N * N);
	}

    }

    private int randInt(int begin, int end) {
	return StdRandom.uniform(end - begin + 1) + begin;
    }

    public double mean() // sample mean of percolation threshold
    {
	double sum = 0.0;
	for (int i = 0; i < percentage.length; i++) {
	    sum += percentage[i];
	}
	return sum / percentage.length;
    }

    public double stddev() // sample standard deviation of percolation threshold
    {
	double miu = mean();
	double sum = 0.0;

	for (int i = 0; i < percentage.length; i++) {
	    sum += (percentage[i] - miu) * (percentage[i] - miu);
	}
	return Math.sqrt(sum / (percentage.length - 1));
    }

    public double confidenceLo() // returns lower bound of the 95% confidence //
				 // interval
    {
	double miu = mean();
	double delta = stddev();

	return miu - (1.96 * delta) / (Math.sqrt(percentage.length));
    }

    public double confidenceHi() // returns upper bound of the 95% confidence
	                         // interval
    {
	double miu = mean();
	double delta = Math.sqrt(stddev());

	return miu + (1.96 * delta) / (Math.sqrt(percentage.length));
    }

}
