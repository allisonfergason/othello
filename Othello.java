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
        }
        else {
            System.out.print("There was a tie! ");
        }
        System.out.println("Thanks for playing!");
    }

    // create new board 
    public static void createBoard() {
        gameBoard = new int[8][8];

        gameBoard[3][4] = 1;
        gameBoard[4][3] = 1;

        gameBoard[3][3] = 2;
        gameBoard[4][4] = 2;
    }

    // draw board
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
        // insert stuff to interate through each space and replace X 
        // with B or W
        int rowCount = 0;
        int colCount = 0;
        for (int j=1; j<16; j+=2) {
            for (int i=2; i<31; i+=4 ) {
                int value = gameBoard[rowCount][colCount];
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
        // print board
        for (char[] row : board){
            String stringrow = String.valueOf(row);
            System.out.println(stringrow);
        } 
    }

    // reset board
    public void clearBoard() {
        Arrays.fill(gameBoard, 0);
    }

    // show points method
    public static void showPoints() {
        String pone = " points";
        String ptwo = " points.";
        if (onepoints == 1) {
            pone = " point";
        }
        if (twopoints == 1) {
            ptwo = " point.";
        }
        System.out.println(player1+" has "+onepoints+pone+" and "+player2+ " has "+twopoints+ptwo);
    }

    // player move method 
    public static boolean makeMove(String move) {
        int row;
        int col;
        try {
            String[] moveArr = move.split(" ");
            row = Integer.valueOf(moveArr[0])-1;
            col = Integer.valueOf(moveArr[1])-1;
        }
        catch (NumberFormatException e) {
            return false;
        }
        boolean success = validMove(row, col);
        if (success) {
            gameBoard[row][col] = player;
        }
        return success;
    }

    // is valid move method
    public static boolean validMove(int row, int col) {
        if (!onBoard(row, col)) {
            return false;
        }
        if (gameBoard[row][col] != 0 || !flipTiles(row, col, true)) {
            return false;
        }
        return true;
    } 

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

    // print rules moethod
    public static void printRules() {
        System.out.println("rules");
    }
}