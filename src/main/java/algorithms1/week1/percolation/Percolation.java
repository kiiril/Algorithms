package algorithms1.week1.percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] grid;
    private int topVirtualSite;
    private int bottomVirtualSite;

    private int openCounter;
    private final WeightedQuickUnionUF weightedQuickUnionUF;
    private final WeightedQuickUnionUF wqfFull;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();

        weightedQuickUnionUF = new WeightedQuickUnionUF(n * n + 2);
        // resolve backwash problem
        wqfFull = new WeightedQuickUnionUF(n * n + 1);
        grid = new boolean[n + 1][n + 1];
        openCounter = 0;

        topVirtualSite = n * n;
        bottomVirtualSite = n * n + 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if (isOpen(row, col)) {
            return;
        }

        grid[row][col] = true;
        openCounter++;

        if (row == 1) {
            int index = convertToIndex(row, col);
            weightedQuickUnionUF.union(topVirtualSite, index);
            wqfFull.union(topVirtualSite, index);
        }
        if (row == grid.length - 1) {
            weightedQuickUnionUF.union(bottomVirtualSite, convertToIndex(row, col));
        }

        try {
            // left cell
            int rowIndex = row;
            int colIndex = col - 1;
            validate(rowIndex, colIndex);
            if (isOpen(rowIndex, colIndex)) {
                weightedQuickUnionUF.union(convertToIndex(row, col), convertToIndex(rowIndex, colIndex));
                wqfFull.union(convertToIndex(row, col), convertToIndex(rowIndex, colIndex));
            }
        } catch (IllegalArgumentException e) {
            // ignore
        }

        try {
            // right cell
            int rowIndex = row;
            int colIndex = col + 1;
            validate(rowIndex, colIndex);
            if (isOpen(rowIndex, colIndex)) {
                weightedQuickUnionUF.union(convertToIndex(row, col), convertToIndex(rowIndex, colIndex));
                wqfFull.union(convertToIndex(row, col), convertToIndex(rowIndex, colIndex));
            }
        } catch (IllegalArgumentException e) {
            // ignore
        }

        try {
            // top cell
            int rowIndex = row - 1;
            int colIndex = col;
            validate(rowIndex, colIndex);
            if (isOpen(rowIndex, colIndex)) {
                weightedQuickUnionUF.union(convertToIndex(row, col), convertToIndex(rowIndex, colIndex));
                wqfFull.union(convertToIndex(row, col), convertToIndex(rowIndex, colIndex));
            }
        } catch (IllegalArgumentException e) {
           // ignore
        }

        try {
            // bottom cell
            int rowIndex = row + 1;
            int colIndex = col;
            validate(rowIndex, colIndex);
            if (isOpen(rowIndex, colIndex)) {
                weightedQuickUnionUF.union(convertToIndex(row, col), convertToIndex(rowIndex, colIndex));
                wqfFull.union(convertToIndex(row, col), convertToIndex(rowIndex, colIndex));
            }
        } catch (IllegalArgumentException e) {
            // ignore
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return wqfFull.find(convertToIndex(row, col)) == wqfFull.find(topVirtualSite);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCounter;
    }

    // does the system percolate?
    public boolean percolates() {
        return weightedQuickUnionUF.find(topVirtualSite) == weightedQuickUnionUF.find(bottomVirtualSite);
    }

    // test client (optional)
    public static void main(String[] args) {

    }

    private void validate(int row, int col) {
        if (row <= 0 || row > grid.length - 1 || col <= 0 || col > grid[0].length - 1) {
            throw new IllegalArgumentException();
        }
    }

    private int convertToIndex(int row, int col) {
        validate(row, col);
        return (row - 1) * (grid[0].length - 1) + (col - 1);
    }
}
