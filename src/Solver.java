import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {

    Stack <Board> nodesPath;
    int moves;


    public class Node implements Comparable<Node>{
        Node parent;
        Board board;
        int moves;

        Node(Node parent, Board board, int moves) {
            this.parent = parent;
            this.board = board;
            this.moves = moves;

        }

        public int compareTo(Node nodeOther) {
            int movesOther = nodeOther.moves;
            int manhattanOther = nodeOther.board.manhattan();

            int movesThis = this.moves;
            int manhattanThis = this.board.manhattan();


            if (movesOther + manhattanOther > movesThis + manhattanThis) {
                return -1;
            } else if (movesOther + manhattanOther == movesThis + manhattanThis) {
                return 0;
            } else {
                return 1;
            }
        }
    }

        // find a solution to the initial board (using the A* algorithm)
        public Solver(Board initial) {
            Node node = new Node(null, initial, 0);

            MinPQ<Node> pq = new MinPQ<>();

            this.nodesPath = new Stack<Board>();


            pq.insert(node);

            while (!pq.isEmpty()) {

                Node node1 = pq.delMin();

                if (node1.board.isGoal()) {
                    this.moves = node1.moves;

                    while (node1 != null) {
                        nodesPath.push(node1.board);
                        node1 = node1.parent;

                    }

                    break;
                }

                for (Board b: node1.board.neighbors()) {
                    Node nodeChild = new Node(node1, b, node1.moves + 1);
                    pq.insert(nodeChild);
                }
            }
        }


        // min number of moves to solve initial board
        public int moves() {
            return this.moves;
        }

        // sequence of boards in a shortest solution
        public Iterable<Board> solution() {
            return this.nodesPath;
        }


        // test client (see below)
        public static void main(String[] args) {
            int[][] tiles_y = {{1, 2 , 3, 4}, {5, 6 , 0, 8}, {9, 10, 7, 11}, {13, 14, 15, 12}};
            Board board_y = new Board(tiles_y);

            Solver solver = new Solver(board_y);

            for (Board b: solver.nodesPath) {
                System.out.println(b);
            }
//
            System.out.println(solver.moves);


        }
}
