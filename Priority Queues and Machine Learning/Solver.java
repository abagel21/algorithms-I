import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;

public class Solver {
    private ArrayList<Board> moveset;
    private Board board;
    private boolean solved;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("must supply a board");
        }
        moveset = new ArrayList<Board>();
        this.board = initial;


        MinPQ<SearchNode> moveTree = new MinPQ<SearchNode>();
        MinPQ<SearchNode> moveTreeCopy = new MinPQ<SearchNode>();


        moveTree.insert(new SearchNode(initial, null, 0));
        moveTreeCopy.insert(new SearchNode(initial.twin(), null, 0));


        SearchNode solution = null;


        while (true) {


            SearchNode currNode = moveTree.delMin();
            if (currNode.board.isGoal()) {
                solution = currNode;
                break;
            }

            SearchNode twinCurrNode = moveTreeCopy.delMin();
            if (twinCurrNode.board.isGoal()) {
                break;
            }


            for (Board a : currNode.board.neighbors()) {
//                if (currNode.prev != null) {
//                    System.out.println(a.toString());
//                    System.out.println(currNode.prev.board.toString());
//                    System.out.println(a.equals(currNode.prev.board));
//                }
                if (currNode.prev == null || !a.equals(currNode.prev.board)) {
                    moveTree.insert(new SearchNode(a, currNode, currNode.moves + 1));
                }

            }


            for (Board b : twinCurrNode.board.neighbors()) {
                if (twinCurrNode.prev == null || !b.equals(twinCurrNode.prev.board)) {
                    moveTreeCopy.insert(new SearchNode(b, twinCurrNode, twinCurrNode.moves + 1));
                }
            }
        }
        if (solution != null) {
            while (solution != null) {
                moveset.add(solution.board);
                solution = solution.prev;
            }
            Collections.reverse(moveset);
            solved = true;
        } else {
            solved = false;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solved;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (solved) {
            return moveset.size() - 1;
        }
        return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (solved) {
            return moveset;
        }
        return null;
    }

    private class SearchNode implements Comparable<SearchNode> {
        Board board;
        SearchNode prev;
        int moves;
        int hpriority;
        int mpriority;

        public SearchNode(Board board, SearchNode prev, int moves) {
            this.board = board;
            this.prev = prev;
            this.moves = moves;
            this.hpriority = board.hamming() + moves;
            this.mpriority = board.manhattan() + moves;
        }

        public int compareTo(SearchNode b) {
            if (this.mpriority > b.mpriority) return 1;
            else if (this.mpriority < b.mpriority) return -1;
            else return 0;
        }

    }

//    private class ManhattanComparator implements Comparator<SearchNode> {
//        public int compare(SearchNode a, SearchNode b) {
//            if (a.hpriority > b.hpriority) return 1;
//            else if (a.hpriority < b.hpriority) return -1;
//            else return 0;
//        }
//    }

//    private class HammingComparator implements Comparator<SearchNode> {
//        public int compare(SearchNode a, SearchNode b) {
//            if (a.mpriority > b.mpriority) return 1;
//            else if (a.mpriority < b.mpriority) return -1;
//            else return 0;
//        }
//    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
