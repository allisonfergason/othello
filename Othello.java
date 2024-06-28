import java.util.Arrays;
import java.util.Scanner;

public class Othello {
    static int[][] gameBoard;
    static int player = 2;
    static String player1;
    static String player2;
    static int onepoints = 2;
    static int twopoints = 2;
    static int impossible = 0;
    static int computerPoints;


    /**
     * Main method to facilitate gameplay for both single and double player. 
     * 
     * @param args 
     */
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        createBoard();
        
        // decide mode
        System.out.println("Welcome to Othello!");
        System.out.println();
        System.out.println
            ("Would you like to play with one or two players? (enter 'one' or 'two')");
        String mode = scn.nextLine();
        while (!mode.equals("one") && !mode.equals("two")) {
            System.out.println("Invalid input, please enter 'one' or 'two'");
            mode = scn.nextLine();
        }

        // get player name(s)
        if (mode.equals("one")) {
            System.out.println();
            System.out.println("Please enter your name: ");
            player1 = scn.nextLine();
            player2 = "Baba Burtis";
            System.out.println("Welcome, "+player1+"! You will be playing against "+player2+".");
            System.out.println();
            System.out.println("Press enter to continue onto the game.");
            scn.nextLine();
        }
        else if (mode.equals("two")) {
            System.out.println();
            System.out.println("Please enter the name of the first player: ");
            player1 = scn.nextLine();
            System.out.println("Please enter the name of the second player: ");
            player2 = scn.nextLine();
            System.out.println();
        }

        // show starting board
        System.out.println();
        System.out.println("Here is the starting board:");
        drawBoard(gameBoard);
        System.out.println(); 

        // one player mode
        while (mode.equals("one")) {

            // show whose turn it is
            whoseTurn();

            // check if a turn is possible
            if (!possibleTurn()) {
                impossible++;
                if (impossible == 1) {
                    System.out.println("There are currently no possible moves and the turn will be skipped.");
                    continue;
                }
                else if (impossible == 2) {
                    System.out.println("There are no possible moves for either player and the game is over.");
                    break;
                }
            }
            else {
                impossible = 0;
            }

            if (player==1) {
                System.out.println();
                System.out.println("Where would you like to place your piece? Enter a row and column separated by a space, or 'help' for the rules.");
                String move = scn.nextLine();
                while (move.length() > 3 || move.length() < 3) {
                    if (move.equals("help")) {
                        printRules();
                        System.out.println();
                        System.out.println("Please enter your move.");
                        move = scn.nextLine();
                    }
                    else {
                        System.out.println("Invalid input, please enter a row and column separated by a space.");
                        move = scn.nextLine();
                    }
                }
                // check for validity and make the move
                while (!makeMove(move)) {
                    System.out.println("Invalid move, please enter another move, or 'help' for the rules.");
                    move = scn.nextLine();
                    if (move.equals("help")) {
                        printRules();
                        System.out.println("Please enter your move.");
                        move = scn.nextLine();
                    }
                }
            }
            else {
                System.out.println("The computer will now make a move.");
                computerMove();
            }

            // print current board and score
            System.out.println();
            drawBoard(gameBoard);
            System.out.println();
            showPoints();

            // option to end the game early 
            System.out.println();
            System.out.println("If you would like to exit, type 'quit'. Otherwise, press enter to continue.");
            String q = scn.nextLine();
            if (q.equals("quit")) {
                break;
            }
        }

        // two player mode
        while (mode.equals("two")) {

            // show whose turn it is
            whoseTurn();

            // check if a turn is possible
            if (!possibleTurn()) {
                impossible++;
                if (impossible == 1) {
                    System.out.println("There are currently no possible moves and the turn will be skipped.");
                    continue;
                }
                else if (impossible == 2) {
                    System.out.println("There are no possible moves for either player and the game is over.");
                    break;
                }
            }
            else {
                impossible = 0;
            }
            
            // get the player's move
            System.out.println();
            System.out.println("Where would you like to place your piece? Enter a row and column separated by a space, or 'help' for the rules.");
            String move = scn.nextLine();
            while (move.length() > 3 || move.length() < 3) {
                if (move.equals("help")) {
                    printRules();
                    System.out.println();
                    System.out.println("Please enter your move.");
                    move = scn.nextLine();
                }
                else {
                    System.out.println("Invalid input, please enter a row and column separated by a space.");
                    move = scn.nextLine();
                }
            }
            // check for validity and make the move
            while (!makeMove(move)) {
                System.out.println("Invalid move, please enter another move, or 'help' for the rules.");
                move = scn.nextLine();
                if (move.equals("help")) {
                    printRules();
                    System.out.println("Please enter your move.");
                    move = scn.nextLine();
                }
            }

            // print current board and score
            System.out.println();
            drawBoard(gameBoard);
            System.out.println();
            showPoints();

            // option to end the game early 
            System.out.println();
            System.out.println("If you would like to exit, type 'quit'. Otherwise, press enter to continue.");
            String q = scn.nextLine();
            if (q.equals("quit")) {
                break;
            }
        }

        scn.close();
        // end statement 
        System.out.println();
        System.out.println("The final score was "+onepoints+" to "+twopoints+".");
        if (onepoints > twopoints) {
            System.out.println(player1+" won!");
        }
        else if (onepoints < twopoints) {
            System.out.print(player2+" won! ");
            if (player2.equals("Baba Burtis")) {
                System.out.println("-500 aura for "+player1);
            }
        }
        else {
            System.out.print("There was a tie! ");
        }
        System.out.println("Thanks for playing!");
    }

    
    /**
     * Creates a new board with the starting configuration of two white pieces
     * and two black pieces in the middle of the board. Gameboard is represented as a 
     * 2D integer array, where 1 is black and 2 is white, as player 1 will play with 
     * black and player 2 will play with white. 
     */
    public static void createBoard() {
        gameBoard = new int[8][8];

        // black pieces added
        gameBoard[3][4] = 1;
        gameBoard[4][3] = 1;

        // white pieces added
        gameBoard[3][3] = 2;
        gameBoard[4][4] = 2;
    }

    /**
     * Prints the gameboard to the screen using character arrays. Replaces the Xs with spaces if
     * there is no piece in a space yet, B for black, or W for white. 
     * 
     * @param gameBoard 2D array containing the current configuration of the pieces. 
     */
    public static void drawBoard(int[][] gameBoard) {
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
                int value = gameBoard[rowCount][colCount];

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

    /**
     * Clear the board by changing all values in the gameboard to zero. 
     */
    public void clearBoard() {
        Arrays.fill(gameBoard, 0);
    }

    /**
     * Print the number of points for each player to the screen, using their names and
     * the point counters. 
     */
    public static void showPoints() {
        String pone = " points";
        String ptwo = " points.";

        // account for the possibility of having one point
        if (onepoints == 1) {
            pone = " point";
        }
        if (twopoints == 1) {
            ptwo = " point.";
        }
        System.out.println(player1+" has "+onepoints+pone+" and "+player2+ " has "+twopoints+ptwo);
    }

    /**
     * Makes the player's move. First checks if the move is valid and then edits the gameboard
     * to reflect that.
     * 
     * @param move String version of the move
     * @return whether the move was successful
     */
    public static boolean makeMove(String move) {
        int row;
        int col;
        // try to convert the String to a row and col integer,
        // if unsuccessful return
        try {
            String[] moveArr = move.split(" ");
            // the row and column are given from 1-8 instead of 0-7, so 1 is subtracted
            // to get the array index
            row = Integer.valueOf(moveArr[0])-1;
            col = Integer.valueOf(moveArr[1])-1;
        }
        catch (NumberFormatException e) {
            return false;
        }

        // check if the move is valid and change the board if so
        boolean success = validMove(row, col);
        if (success) {
            gameBoard[row][col] = player;
        }
        return success;
    }

    /**
     * Checks if a move is valid by checking if the coordinates are on the board,
     * if the given space is empty, and if any tiles will be flipped by the move. 
     * Flipping at least one tile is required for a valid move.
     * 
     * @param row row where the move is to be made
     * @param col column where the move is to be made
     * @return whether the move is valid
     */
    public static boolean validMove(int row, int col) {
        if (!onBoard(row, col)) {
            return false;
        }

        // check if the space is empty and then check if there are tiles flipped
        if (gameBoard[row][col] != 0 || !flipTiles(row, col, true)) {
            return false;
        }
        return true;
    } 

    /**
     * Checks if the given move will flip any tiles, and flips those tiles if the flip parameter
     * is true. If it is false it will only check that those tiles may be flipped if the move is made. 
     * 
     * @param row row where the move is to be made
     * @param col column where the move is to be made
     * @param flip whether or not to actually flip the tiles, i.e. is the move actually being made
     * @return whether or not there were tiles flipped with the given move
     */
    public static boolean flipTiles(int row, int col, boolean flip) {
        int[][] directions = {{0,1}, {1,1}, {1,0}, {1,-1}, {0,-1}, {-1,-1}, {-1,0}, {-1,1}};
        int other;
        if (player==1) {
            other = 2;
        }
        else { other = 1;}

        boolean flag = false;
        int points = 0;

        int rstart = row;
        int cstart = col;

        for (int[] dir : directions) {
            row += dir[0];
            col += dir[1];
            if (onBoard(row, col) && gameBoard[row][col]==other) {
                row += dir[0];
                col += dir[1];
                if (!onBoard(row, col)) {
                    continue;
                }
                while (gameBoard[row][col]==other) {
                    row += dir[0];
                    col += dir[1];
                    if (!onBoard(row, col)) {
                        break;
                    }
                }
                if (!onBoard(row, col)) {
                    continue;
                }
                if (gameBoard[row][col]==player) {
                    while (true) {
                        row -= dir[0];
                        col -= dir[1];
                        if (row == rstart && col == cstart) {
                            break;
                        }
                        if (flip==true) {
                            gameBoard[row][col] = player;
                        }
                        points++;
                        flag = true;
                    }
                }
            }
            row = rstart;
            col = cstart;
        }

        computerPoints = points;
        updatePoints(points, flip);
        return flag;  
    }

    public static void updatePoints(int points, boolean flip) {
        if (player==1 && flip==true) {
            onepoints += points + 1;
            twopoints -= points;
        }
        else if (player==2 && flip==true) {
            twopoints += points + 1;
            onepoints -= points;
        }
    }

    // check if a coordinate is on the board
    public static boolean onBoard(int row, int col) {
        if (row < 0 || col < 0) {
            return false;
        }
        else if (row > 7 || col > 7) {
            return false;
        }
        else {
            return true;
        }
    }

    // computer move method 
    public static void computerMove() {
        int max = -1;
        String move = "";

        // run through all possible moves and check which flips most opposing tiles
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                if (gameBoard[i][j]==0 && flipTiles(i, j, false)) {
                    if (computerPoints > max) {
                        max = computerPoints;
                        int r = i + 1;
                        int c = j + 1;
                        move = r+" "+c;
                    }
                }
            }
        }

        // make move
        makeMove(move);
    }

    // two players who's turn is it method
    public static void whoseTurn() {
        if (player == 1) {
            player = 2;
            System.out.println("It is "+player2+"'s turn.");
        }
        else {
            player = 1;
            System.out.println("It is "+player1+"'s turn.");
        }
    }

    // is there a possible turn
    public static boolean possibleTurn() {
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                if (gameBoard[i][j]==0 && flipTiles(i, j, false)) {
                    return true;
                }
            }
        }
        return false;
    } 

    /**
     * Prints the rules of the game when requested. These are directly sourced from the World Othello Federation's
     * website. 
     */
    public static void printRules() {
        System.out.println();
        System.out.println();
        System.out.println("Rules sourced from World Othello Federation.");
        System.out.println("Each player takes 32 discs and chooses one colour to use throughout the game. Black places two black discs");
        System.out.println("and White places two white discs as shown below.");
        System.out.println();
        drawBoard(gameBoard);
        System.out.println();
        System.out.println("A move consists of 'outflanking' your opponent's disc(s), then flipping the outflanked disc(s)to your colour.");
        System.out.println("To outflank means to place a disc on the board so that your opponent's row (or rows) of disc(s) is bordered at each");
        System.out.println("end by a disc of your colour. (A 'row' may be made up of one or more discs).");

        System.out.println();
        System.out.println("Rules");
        System.out.println();
        System.out.println("1. Black always moves first.");
        System.out.println("2. If on your turn you cannot outflank and flip at least one opposing disc, your turn is forfeited and your opponent moves\n" + //
                "again. However, if a move is available to you, you may not forfeit your turn. ");
        System.out.println("4. Players may not skip over their own colour disc(s) to outflank an opposing disc.");
        System.out.println("5. Disc(s) may only be outflanked as a direct result of a move and must fall in the direct line of the disc placed down.");
        System.out.println("6. All discs outflanked in any one move must be flipped, even if it is to the player's advantage not to flip them at all. ");
        System.out.println("7. Once a disc is placed on a square, it can never be moved to another square later in the game. ");
        System.out.println("8. When it is no longer possible for either player to move, the game is over. Discs are counted and the player with the majority of their colour showing is the winner.");
        System.out.println("Note: It is possible for a game to end before all 64 squares are filled.");

    }
}
