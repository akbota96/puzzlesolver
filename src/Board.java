import java.util.ArrayList;

public class Board {
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    private int[][] tiles;
    private int row0, col0;
    private int hamming, manhattan;

    public Board(int[][] tiles) {
        this.tiles = tiles;

        int count = 1;
        int tiles_len = tiles.length;

        for (int i=0; i < tiles.length; i++) {
            for (int j=0; j < tiles.length; j++) {

                // hamming distance
                if ((tiles[i][j] - count != 0) && (tiles[i][j]!=0)) {
                    hamming += 1;
                }
                count += 1;

                // manhattan distance
                int tiles_val = tiles[i][j] - 1;
//                System.out.println(tiles[i][j]);
                if (tiles[i][j] != 0) {
                    int row =  tiles_val/ tiles_len;
                    int col = tiles_val % tiles_len;
                    manhattan += Math.abs(row - i) + Math.abs(col - j) ;
//                    System.out.println(manhattan);
                }
//                System.out.println("\n");


                // tile with value zero
                if (tiles[i][j] == 0) {
                    row0 = i;
                    col0 = j;
                }
            }
        }
    }


    // string representation of this board
    public String toString(){
        String s = String.valueOf(tiles.length) + "\n";

        for (int i = 0; i < tiles.length; i ++) {
            for (int j = 0; j < tiles.length; j++) {

                s += String.valueOf(tiles[i][j]);
                s += " ";
            }
            s += "\n";
        }
        return s;
    }

    // tile at (row, col) or 0 if blank
    // IllegalArgumentException
    public int tileAt(int row, int col) {

        if ((row >= 0) && (tiles.length - 1 >= row) && (col >= 0) && (tiles.length - 1 >= col) ) {
            return tiles[row][col];
        } else {
            throw new IllegalArgumentException();
        }
    }
    // board size n
    public int size() {
        return tiles.length;
    }


    // number of tiles out of place
    public int hamming(){
        return this.hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return this.manhattan;
    }


    // is this board the goal board?
    public boolean isGoal() {
        if (this.hamming() == 0) {
            return true;

        } else {
            return false;
        }
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }

        if (y == null) {
            return false;
        }

        Board board_y = (Board) y;
        boolean size_match = board_y.size() == this.size();

        if (size_match == true) {

            for (int i=0; i < tiles.length; i++) {
                for (int j=0; j < tiles.length; j++) {
                    if (tiles[i][j] != board_y.tileAt(i, j)) {
                        return false;
                    }
                }
            }
            return true;

        } else {
            return false;
        }

    }

    public int[][] copyTiles() {
        int[][] copy_tiles = new int[tiles.length][tiles.length];

        for (int i=0; i < tiles.length; i++) {
            copy_tiles[i] = tiles[i].clone();
        }

        return copy_tiles;
    }


    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList <Board> list = new ArrayList<>();


//        for (int i=0; i < tiles.length; i++) {
//            for (int j=0; j < tiles.length; j++) {
//                if (tiles[i][j] == 0) {
//                    row0 = i;
//                    col0 = j;
//                }
//            }
//        }

        if (col0 - 1 >=0) {
            int[][] copy_tiles = this.copyTiles();

            copy_tiles[row0][col0] = this.tileAt(row0, col0 - 1);
            copy_tiles[row0][col0 - 1] = 0;
            Board board = new Board(copy_tiles);
            list.add(board);

        }

        if (col0 + 1 < tiles.length) {
            int[][] copy_tiles = this.copyTiles();

            copy_tiles[row0][col0] = this.tileAt(row0, col0 + 1);
            copy_tiles[row0][col0 + 1] = 0;
            Board board = new Board(copy_tiles);
            list.add(board);
        }

        if (row0 - 1 >= 0) {
            int[][] copy_tiles = this.copyTiles();

            copy_tiles[row0][col0] = this.tileAt(row0 - 1, col0);
            copy_tiles[row0 - 1][col0] = 0;
            Board board = new Board(copy_tiles);
            list.add(board);
        }

        if (row0 + 1 < tiles.length) {
            int[][] copy_tiles = this.copyTiles();

            copy_tiles[row0][col0] = this.tileAt(row0 + 1, col0);
            copy_tiles[row0 + 1][col0] = 0;
            Board board = new Board(copy_tiles);
            list.add(board);
        }

        return list;

    }

    public int inversions(int i, int j, int val) {
        int inversions = 0;

        for (int k=i; k < tiles.length; k++) {

            int p = 0;

            if (k == i) {
                p = j;
            }


            for ( ;p< tiles.length; p++) {

                if ((tiles[k][p] < val) && (tiles[k][p] != 0)) {
                    inversions++;
                }
            }
        }

        return inversions;

    }


    // is this board solvable?
    public boolean isSolvable() {
        int total_inversions = 0;

        for (int i=0; i < tiles.length; i++) {
            for (int j=0; j < tiles.length; j++) {

                int val = tiles[i][j];

                total_inversions += this.inversions(i, j, val);

            }
        }

        if (this.size() % 2 == 1) {
            if (total_inversions % 2 == 1) {
                return false;
            } else {
                return true;
            }
        } else {
            if ((total_inversions + this.row0) % 2 == 1) {
                return true;
            } else {
                return false;
            }
        }
    }


    public static void main(String[] args) {
        int[][] tiles = {{1, 2 , 3, 4}, {5, 6 , 0, 8}, {9, 10, 7, 11}, {13, 14, 15, 12}};
        int[][] tiles_y = {{8, 1 , 3}, {4, 0 , 2}, {7, 6, 5}};


        Board board = new Board(tiles);
        Board board_y = new Board(tiles_y);

        System.out.println(board.toString());
        System.out.println(board.tileAt(1, 2));
        System.out.println(board.size());
        System.out.println(board.hamming());
        System.out.println(board.manhattan());
        System.out.println(board.equals(board_y));

        for (Board b: board.neighbors() ) {
            System.out.println(b);
        }
        System.out.println(board.isSolvable());

    }
}

