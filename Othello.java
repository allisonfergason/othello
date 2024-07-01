import java.util.Scanner;

public class Othello {
    static int player = 2;
    static int computerPoints;
    static Player p1;
    static Player p2;

    /**
     * Main method to facilitate gameplay for either one or two players. 
     * 
     * @param args 
     */
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        GameBoard gb = new GameBoard();
        
        // ask the player if they want to play in single or double player mode
        System.out.println();
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
            String player1 = scn.nextLine();
            p1 = new Player(player1);
            String player2 = "Baba Burtis";
            p2 = new Player(player2);
            System.out.println("Welcome, "+player1+"! You will be playing against "+player2+".");
            System.out.println();
            System.out.println("Press enter to continue onto the game.");
            scn.nextLine();
        }
        else {
            System.out.println();
            System.out.println("Please enter the name of the first player: ");
            String player1 = scn.nextLine();
            p1 = new Player(player1);
            System.out.println("Please enter the name of the second player: ");
            String player2 = scn.nextLine();
            p2 = new Player(player2);
            System.out.println();
        }

        // show starting board
        System.out.println();
        System.out.println("Here is the starting board:");
        gb.drawBoard();
        System.out.println(); 

        // counter for the number of turns there has been without a possible move
        int impossibleTurns = 0;

        // one player mode
        while (mode.equals("one")) {

            // show whose turn it is
            whoseTurn();

            // check if a turn is possible
            if (!possibleTurn(gb)) {

                // increment counter 
                impossibleTurns++;
                if (impossibleTurns == 1) {
                    System.out.println("There are currently no possible moves and the turn will be skipped.");
                    continue;
                }
                else if (impossibleTurns == 2) {
                    System.out.println("There are no possible moves for either player and the game is over.");
                    break;
                }
            }
            else {
                impossibleTurns = 0;
            }

            // for the user's turn
            if (player==1) {
                System.out.println();
                System.out.println
                    ("Where would you like to place your piece? Enter a row and column separated by a space, or 'help' for the rules.");
                String move = scn.nextLine();
                while (move.length() != 3) {
                    if (move.equals("help")) {
                        printRules(gb);
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
                while (!makeMove(move, gb)) {
                    System.out.println("Invalid move, please enter another move, or 'help' for the rules.");
                    move = scn.nextLine();
                    if (move.equals("help")) {
                        printRules(gb);
                        System.out.println("Please enter your move.");
                        move = scn.nextLine();
                    }
                }
            }
            else {
                System.out.println("The computer will now make a move.");
                computerMove(gb);
            }

            // print current board and score
            System.out.println();
            gb.drawBoard();
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
            if (!possibleTurn(gb)) {
                impossibleTurns++;
                if (impossibleTurns == 1) {
                    System.out.println("There are currently no possible moves and the turn will be skipped.");
                    continue;
                }
                else if (impossibleTurns == 2) {
                    System.out.println("There are no possible moves for either player and the game is over.");
                    break;
                }
            }
            else {
                impossibleTurns = 0;
            }
            
            // get the player's move
            System.out.println();
            System.out.println("Where would you like to place your piece? Enter a row and column separated by a space, or 'help' for the rules.");
            String move = scn.nextLine();
            while (move.length() != 3) {
                if (move.equals("help")) {
                    printRules(gb);
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
            while (!makeMove(move, gb)) {
                System.out.println("Invalid move, please enter another move, or 'help' for the rules.");
                move = scn.nextLine();
                if (move.equals("help")) {
                    printRules(gb);
                    System.out.println("Please enter your move.");
                    move = scn.nextLine();
                }
            }

            // print current board and score
            System.out.println();
            gb.drawBoard();
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
        System.out.println("The final score was "+p1.getPoints()+" to "+p2.getPoints()+".");
        if (p1.getPoints() > p2.getPoints()) {
            System.out.println(p1.getName()+" won!");
        }
        else if (p1.getPoints() < p2.getPoints()) {
            System.out.print(p2.getName()+" won! ");
            if (p2.getName().equals("Baba Burtis")) {
                System.out.println("-500 aura for "+p1.getName());
            }
        }
        else {
            System.out.print("There was a tie! ");
        }
        System.out.println("Thanks for playing!");
    }

    /**
     * Print the number of points for each player to the screen, using their names and
     * the point counters. 
     */
    public static void showPoints() {
        String pone = " points";
        String ptwo = " points.";

        // account for the possibility of having one point
        if (p1.getPoints() == 1) {
            pone = " point";
        }
        if (p2.getPoints() == 1) {
            ptwo = " point.";
        }
        System.out.println(p1.getName()+" has "+p1.getPoints()+pone+" and "+p2.getName()+ " has "+p2.getPoints()+ptwo);
    }

    /**
     * Makes the player's move. First checks if the move is valid and then edits the gameboard
     * to reflect that.
     * 
     * @param move String version of the move
     * @return whether the move was successful
     */
    public static boolean makeMove(String move, GameBoard gBoard) {
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
        boolean success = validMove(row, col, gBoard);
        if (success) {
            gBoard.changeSpot(row, col, player);
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
    public static boolean validMove(int row, int col, GameBoard gb) {
        if (!onBoard(row, col)) {
            return false;
        }

        // check if the space is empty and then check if there are tiles flipped
        if (gb.checkSpot(row, col) != 0 || !flipTiles(row, col, true, gb)) {
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
    public static boolean flipTiles(int row, int col, boolean flip, GameBoard gBoard) {

        // 2D array representing eight possible directions to move in
        int[][] directions = {{0,1}, {1,1}, {1,0}, {1,-1}, {0,-1}, {-1,-1}, {-1,0}, {-1,1}};
        
        // find the opposing player 
        int other;
        if (player==1) {
            other = 2;
        }
        else { other = 1;}

        // flag will change to true if pieces that can be flipped are found
        boolean flag = false;
        // points tracker
        int points = 0;

        // note starting row and column 
        int rstart = row;
        int cstart = col;

        // loop through each possible direction to test for tiles to flip
        for (int[] dir : directions) {

            // edit the current coordinate in the desired direction
            row += dir[0];
            col += dir[1];

            // if the spot next to the intended move is occupied by an opposing tile continue
            if (onBoard(row, col) && gBoard.checkSpot(row, col)==other) {
                row += dir[0];
                col += dir[1];
                if (!onBoard(row, col)) {
                    continue;
                }
                // keep moving in this direction until the current spot is not
                // occupied by an opposing tile
                while (gBoard.checkSpot(row, col)==other) {
                    row += dir[0];
                    col += dir[1];
                    if (!onBoard(row, col)) {
                        break;
                    }
                }
                // once stopped, check if still on the board
                if (!onBoard(row, col)) {
                    continue;
                }
                // if stopped on the current player's tile, flips can be made
                // backtrack, flip tiles to current player's color, and count points 
                if (gBoard.checkSpot(row, col)==player) {
                    while (true) {
                        row -= dir[0];
                        col -= dir[1];
                        if (row == rstart && col == cstart) {
                            break;
                        }
                        if (flip==true) {
                            gBoard.changeSpot(row, col, player);

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
        if (flip==true) {
            updatePoints(points);
        }
        return flag;  
    }

    /**
     * Updates points if the move was actually made. Added to current player
     * and subtracted from other player. 
     * 
     * @param points number of tiles flipped.
     * @param flip whether the tiles are actually flipped
     */
    public static void updatePoints(int points) {
        if (player==1) {
            p1.changePoints(points + 1);
            p2.changePoints(points*-1);
        }
        else {
            p2.changePoints(points + 1);
            p1.changePoints(points*-1);
        }
    }

    /**
     * Checks if a move is within the 8x8 board
     * 
     * @param row row index
     * @param col column index
     * @return whether the move is valid
     */
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

    /**
     * Goes through all possible moves, checks which will flip the most tiles,
     * and makes that move
     */
    public static void computerMove(GameBoard gb) {
        int max = -1;
        String move = "";

        // run through all possible moves and check which flips most opposing tiles
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                if (gb.checkSpot(i,j)==0 && flipTiles(i, j, false, gb)) {
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
        makeMove(move, gb);
    }

    /**
     * Prints whose turn it is currently and switches to the next player
     */
    public static void whoseTurn() {
        if (player == 1) {
            player = 2;
            System.out.println("It is "+p2.getName()+"'s turn.");
        }
        else {
            player = 1;
            System.out.println("It is "+p1.getName()+"'s turn.");
        }
    }

    /*
     * checks if there is a possible turn for the current player by interating
     * through the board
     */
    public static boolean possibleTurn(GameBoard gb) {
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                // check if the spot is empty and has tiles that could be flipped
                if (gb.checkSpot(i, j)==0 && flipTiles(i, j, false, gb)) {
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
    public static void printRules(GameBoard gb) {
        System.out.println();
        System.out.println();
        System.out.println("Rules sourced from World Othello Federation.");
        System.out.println("Each player takes 32 discs and chooses one colour to use throughout the game. Black places two black discs");
        System.out.println("and White places two white discs as shown below.");
        System.out.println();
        gb.drawBoard();
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
