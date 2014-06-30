/**
 * Compilation: javac Percolation.java Execution: java Percolation
 * 
 * Percolation solver using UnionFind data structure
 * 
 * @author Zhongcun Wang
 * @date Feb 14th, 2014
 * 
 */

public class Percolation {

    private int[][] grid;
    private int n;

    private WeightedQuickUnionUF perc;
    private WeightedQuickUnionUF full;

    private final int BEGIN = 0;
    private final int END = 1;

    public Percolation(int N) {
	this.n = N;
	grid = new int[N + 1][N + 1];

	for (int i = 1; i <= N; i++) {
	    for (int j = 1; j <= N; j++) {
		grid[i][j] = 1; // blocked
	    }
	}

	/**
	 * add two node to N * N tile, its location is 0 and N * N + 1
	 */
	full = new WeightedQuickUnionUF(N * N + 2);
	perc = new WeightedQuickUnionUF(N * N + 2);
    }

    public void open(int i, int j) {
	isIndexValid(i, j);
	if (!isOpen(i, j)) {
	    grid[i][j] = 0; // open

	    if (i == 1) {
		perc.union(BEGIN, xyTo1D(i, j));
		full.union(BEGIN, xyTo1D(i, j));
	    }

	    if (i == n) {
		perc.union(xyTo1D(i, j), END);
	    }

	    if (i > 1) {
		if (isOpen(i - 1, j)) {
		    perc.union(xyTo1D(i, j), xyTo1D(i - 1, j));
		    full.union(xyTo1D(i, j), xyTo1D(i - 1, j));
		}

	    }

	    if (i < n) {
		if (isOpen(i + 1, j)) {
		    perc.union(xyTo1D(i, j), xyTo1D(i + 1, j));
		    full.union(xyTo1D(i, j), xyTo1D(i + 1, j));
		}
	    }

	    if (j > 1) {
		if (isOpen(i, j - 1)) {
		    perc.union(xyTo1D(i, j), xyTo1D(i, j - 1));
		    full.union(xyTo1D(i, j), xyTo1D(i, j - 1));
		}
	    }

	    if (j < n) {
		if (isOpen(i, j + 1)) {
		    perc.union(xyTo1D(i, j), xyTo1D(i, j + 1));
		    full.union(xyTo1D(i, j), xyTo1D(i, j + 1));
		}
	    }
	}
    }

    public boolean isOpen(int i, int j) {
	isIndexValid(i, j);
	return grid[i][j] == 0; // o represents the grid is open
    }

    public boolean isFull(int i, int j) {
	isIndexValid(i, j);

	return full.connected(0, xyTo1D(i, j));
    }

    public boolean percolates() {
	return perc.connected(BEGIN, END);
    }

    private void isIndexValid(int i, int j) {
	if (i <= 0 || i > n || j <= 0 || j > n) {
	    throw new IndexOutOfBoundsException();
	}

    }

    private int xyTo1D(int i, int j) {
	return (i - 1) * n + j + 1;
    }

    public static void main(String[] args) {

    }

}
