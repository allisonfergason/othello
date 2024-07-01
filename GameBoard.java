import java.util.Arrays;

/*
 * Creates gameBoard objects to store and alter the current placement of pieces
 */
public class GameBoard {
    int[][] gameBoard;

     /**
     * Creates a new board with the starting configuration of two white pieces
     * and two black pieces in the middle of the board. Gameboard is represented as a 
     * 2D integer array, where 1 is black and 2 is white, as player 1 will play with 
     * black and player 2 will play with white. 
     */
    public GameBoard() {
        gameBoard = new int[8][8];

        // black pieces added
        gameBoard[3][4] = 1;
        gameBoard[4][3] = 1;

        // white pieces added
        gameBoard[3][3] = 2;
        gameBoard[4][4] = 2;
    }

    /**
     * Clear the board by changing all values in the gameboard to zero. 
     */
    public void clearBoard() {
        Arrays.fill(gameBoard, 0);
    }

    /*
     * Alters the given spot on the board to have the tile of the given player.
     */
    public void changeSpot(int row, int col, int player) {
        gameBoard[row][col] = player;
    }

    /*
     * Returns the value at a spot in the board.
     */
    public int checkSpot(int row, int col) {
        return gameBoard[row][col];
    }

    /**
     * Prints the gameboard to the screen using character arrays. Replaces the Xs with spaces if
     * there is no piece in a space yet, B for black, or W for white. 
     * 
     */
    public void drawBoard() {
        char[][] board = {
            "╔═══════════════════════════════╗".toCharArray(),
            "║ X │ X │ X │ X │ X │ X │ X │ X ║".toCharArray(),
            "║───┼───┼───┼───┼───┼───┼───┼───║".toCharArray(),
            "║ X │ X │ X │ X │ X │ X │ X │ X ║".toCharArray(),
            "║───┼───┼───┼───┼───┼───┼───┼───║".toCharArray(),
            "║ X │ X │ X │ X │ X │ X │ X │ X ║".toCharArray(),
            "║───┼───┼───┼───┼───┼───┼───┼───║".toCharArray(),
            "║ X │ X │ X │ X │ X │ X │ X │ X ║".toCharArray(),
            "║───┼───┼───┼───┼───┼───┼───┼───║".toCharArray(),
            "║ X │ X │ X │ X │ X │ X │ X │ X ║".toCharArray(),
            "║───┼───┼───┼───┼───┼───┼───┼───║".toCharArray(),
            "║ X │ X │ X │ X │ X │ X │ X │ X ║".toCharArray(),
            "║───┼───┼───┼───┼───┼───┼───┼───║".toCharArray(),
            "║ X │ X │ X │ X │ X │ X │ X │ X ║".toCharArray(),
            "║───┼───┼───┼───┼───┼───┼───┼───║".toCharArray(),
            "║ X │ X │ X │ X │ X │ X │ X │ X ║".toCharArray(),
            "╚═══════════════════════════════╝".toCharArray()};

        // loop to iterate through each space and replace it with the proper
        // piece representation
        int rowCount = 0;
        int colCount = 0;
        for (int j=1; j<16; j+=2) {
            for (int i=2; i<31; i+=4 ) {

                // retrieves value (either 0, 1, or 2) from the input array
                int value = this.gameBoard[rowCount][colCount];

                // 2 is white, 1 is black, and 0 is blank
                if (value == 2) {
                    board[j][i] = 'W';
                }
                else if (value==1) {
                    board[j][i] = 'B';
                }
                else {
                    board[j][i] = ' ';
                }
                colCount++;
            }
            colCount = 0;
            rowCount++;
        }

        // print board to screen
        for (char[] row : board){
            String stringrow = String.valueOf(row);
            System.out.println(stringrow);
        } 
    }
}
