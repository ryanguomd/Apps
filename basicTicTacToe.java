// Text-based game of Tic-Tac-Toe
// Players chose their index based on inputing its row and column into the console

import java.util.Scanner;

public class basicTicTacToe {
    
    static char[][] board = new char[3][3];
    static Scanner input = new Scanner(System.in);
    
    // Creates a blank board
    public static void createBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col] = '-';
            }
        }
    }
    
    // Prints the board in the interactions plane
    public static void printBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                System.out.print(board[row][col]);
            }
            // Starts new line before printing the next row
            System.out.println();
        }
    }
    
    // Checks if all entries have already been chosen
    public static boolean isBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == '-') {
                    return false;
                }
            }
        }
        return true;
    }
    
    // Returns true if one of the winning conditions is met
    public static boolean hasWon() {
        return (rowWin() || columnWin() || diagonalWin());
    }
    
    // Checks the row win condition
    public static boolean rowWin() {
        for (int row = 0; row < 3; row++) {
            if ((board[row][0] != '-') && (board[row][0] == board[row][1]) && (board[row][0] == board[row][2])) {
                return true;
            }
        }
        return false;
    }
    
    // Checks the column win condition
    public static boolean columnWin() {
        for (int col = 0; col < 3; col++) {
            if ((board[0][col] != '-') && (board[0][col] == board[1][col]) && (board[0][col] == board[2][col])) {
                return true;
            }
        }
        return false;
    }
    
    // Checks the diagonal win condition
    public static boolean diagonalWin() {
        return (board[1][1] != '-' && board[0][0] == board[1][1] && board[0][0] == board[2][2]) ||
            (board[1][1] != '-' && board[0][2] == board[1][1] && board[0][2] == board[2][0]);
    }
    
    public static void main(String[] args) {
        
        // Initialize the game
        createBoard();
        printBoard();
        
        // Player 1 starts first
        boolean player1Turn = true;
        boolean gameEnd = false;
        
        // Runs game until finish
        while (!gameEnd) {
            if (player1Turn) {
                System.out.println("Player 1 (X): ");
            } else {
                System.out.println("Player 2 (O): ");
            }
            
            int row = -1;
            int col = -1;
            
            // Row and column conditions ensures the player inputs a valid row or column
            // Checks if the chosen entry has already been taken previously
            while (row < 1 || col < 1 || row > 3 || col > 3 || board[row - 1][col - 1] != '-') {
                System.out.println("Enter a row (1, 2, or 3): ");
                row = input.nextInt();
                System.out.println("Enter a column (1, 2, or 3): ");
                col = input.nextInt();
            }
            
            // Updates the board based on whose turn it is
            if (player1Turn) {
                board[row - 1][col - 1] = 'X';
            } else {
                board[row - 1][col - 1] = 'O';
            }
            
            if (hasWon()) {
                // Determines which player has won based on who last chose an entry
                gameEnd = true;
                printBoard();
                if (player1Turn) {
                    System.out.println("Player 1 Wins!");
                } else {
                    System.out.println("Player 2 Wins!");
                }
            } else if (isBoardFull()) {
                // Game ends in tie if the board is full
                gameEnd = true;
                printBoard();
                System.out.println("Tie! Better Luck Next Time!");
            } else {
                // Switch players and update board if the game is not finished
                player1Turn = !player1Turn;
                printBoard();
            }
        }
    }
}