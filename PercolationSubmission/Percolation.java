import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] grid;
    private final WeightedQuickUnionUF rgrid;
    private final WeightedQuickUnionUF full;
    private final int n;
    private int opened;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Input must be greater than 0");
        }
        this.n = n;
        grid = new int[n][n];
        rgrid = new WeightedQuickUnionUF(n*n + 2);
        full = new WeightedQuickUnionUF(n*n+1);
    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        inputCheck(row, col);
        if (isOpen(row, col)) {
            return;
        }
        int a = (row-1)*n + col - 1;
        if (boundsCheck(row + 1, col) && isOpen(row + 1, col)) {
            rgrid.union(a, (row)*n + col - 1);
            full.union(a, (row)*n + col - 1);
        }
        if (boundsCheck(row - 1, col) && isOpen(row - 1, col)) {
            rgrid.union(a, (row-2)*n + col - 1);
            full.union(a, (row-2)*n + col - 1);
        }
        if (boundsCheck(row, col + 1) && isOpen(row, col + 1)) {
            rgrid.union(a, (row - 1)*n + col);
            full.union(a, (row - 1)*n + col);
        }
        if (boundsCheck(row, col - 1) && isOpen(row, col - 1)) {
            rgrid.union(a, (row-1)*n + col - 2);
            full.union(a, (row-1)*n + col - 2);
        }
        if (row == 1) {
            rgrid.union(n*n, a);
            full.union(n*n, a);
        }
        if (row == n) {
            rgrid.union(n*n + 1, a);
        }
        grid[row - 1][col - 1] = 1;
        opened++;
    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        inputCheck(row, col);
        return grid[row-1][col-1] == 1;
    }


    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        inputCheck(row, col);
        return full.find(n*n) == full.find((row-1)*n + col - 1);
    }


    // returns the number of open sites
    public int numberOfOpenSites() {
        return opened;
    }


    // does the system percolate?
    public boolean percolates() {
        return rgrid.find(n*n) == rgrid.find(n*n+1);
    }

    // test client (optional)
    public static void main(String[] args) {
        // decided against implementing test client
    }

    private boolean boundsCheck(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            return false;
        }
        return true;
    }
    private void inputCheck(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("Value is not within correct range (" + row + ", " + col + ") with size of " + n);
        }
    }
}
