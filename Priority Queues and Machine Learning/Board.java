import java.util.ArrayList;

public class Board {
    private int[][] tiles;
    private Board twinInstance;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = copyArr(tiles);
    }

    // string representation of this board
    public String toString() {
        StringBuilder board = new StringBuilder();
        board.append(tiles.length + "\n");
        for (int[] row : tiles) {
            for (int tile : row) {
                board.append(String.format(" %2d", tile));
            }
            board.append("\n");
        }
        return board.toString();
    }

    // board dimension n
    public int dimension() {
        return tiles[0].length;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] != 0 && tiles[i][j] - i * tiles[i].length - 1 != j) {
                    hamming++;
                }
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (!(tiles[i][j] == 0)) {
                    manhattan += Math.abs((tiles[i][j] - 1) / tiles.length - i);
                    manhattan += Math.abs((tiles[i][j] - 1) % tiles.length - j);
                }
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (!(y instanceof Board)) return false;
        Board temp = (Board) y;
        if (temp.tiles.length != tiles.length) return false;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] != temp.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int zeroRow = 0;
        int zeroCol = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                }
            }
        }
        ArrayList<Board> neighboringBoards = new ArrayList<Board>();
        if (zeroRow > 0) {
            int[][] tempTilesA = copyArr(tiles);
            int temp = tempTilesA[zeroRow][zeroCol];
            tempTilesA[zeroRow][zeroCol] = tempTilesA[zeroRow - 1][zeroCol];
            tempTilesA[zeroRow - 1][zeroCol] = temp;
            neighboringBoards.add(new Board(tempTilesA));
        }
        if (zeroRow < tiles.length - 1) {
            int[][] tempTilesB = copyArr(tiles);
            int temp = tempTilesB[zeroRow][zeroCol];
            tempTilesB[zeroRow][zeroCol] = tempTilesB[zeroRow + 1][zeroCol];
            tempTilesB[zeroRow + 1][zeroCol] = temp;
            neighboringBoards.add(new Board(tempTilesB));
        }
        if (zeroCol > 0) {
            int[][] tempTilesC = copyArr(tiles);
            int temp = tempTilesC[zeroRow][zeroCol];
            tempTilesC[zeroRow][zeroCol] = tempTilesC[zeroRow][zeroCol - 1];
            tempTilesC[zeroRow][zeroCol - 1] = temp;
            neighboringBoards.add(new Board(tempTilesC));
        }
        if (zeroCol < tiles.length - 1) {
            int[][] tempTilesD = copyArr(tiles);
            int temp = tempTilesD[zeroRow][zeroCol];
            tempTilesD[zeroRow][zeroCol] = tempTilesD[zeroRow][zeroCol + 1];
            tempTilesD[zeroRow][zeroCol + 1] = temp;
            neighboringBoards.add(new Board(tempTilesD));
        }
        return neighboringBoards;
    }

    private int[][] copyArr(int[][] arr) {
        int[][] newArr = new int[arr.length][];
        for (int i = 0; i < arr.length; i++) {
            newArr[i] = arr[i].clone();
        }
        return newArr;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] tempTiles = copyArr(tiles);
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length - 1; j++) {
                if (tiles[i][j] > 0 && tiles[i][j + 1] > 0) {
                    int temp = tempTiles[i][j];
                    tempTiles[i][j] = tempTiles[i][j + 1];
                    tempTiles[i][j + 1] = temp;
                    return new Board(tempTiles);
                }
            }
        }
        throw new RuntimeException("Couldn't find two tiles to swap");
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] randInt = new int[3][3];
        randInt[0][0] = 5;
        randInt[0][1] = 1;
        randInt[0][2] = 4;
        randInt[1][0] = 8;
        randInt[1][1] = 0;
        randInt[1][2] = 2;
        randInt[2][0] = 6;
        randInt[2][1] = 3;
        randInt[2][2] = 7;
        Board a = new Board(randInt);
        Iterable<Board> b = a.neighbors();
    }

}
