import java.util.Scanner;

public class TicTacToe {
    // ANSI color codes for beautiful output
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";

    private char[][] board;
    private char currentPlayer;
    private String player1Name;
    private String player2Name;
    private int player1Score;
    private int player2Score;
    private int draws;
    private Scanner scanner;

    public TicTacToe() {
        board = new char[3][3];
        currentPlayer = 'X';
        player1Score = 0;
        player2Score = 0;
        draws = 0;
        scanner = new Scanner(System.in);
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void printHeader() {
        System.out.println(CYAN + "╔═══════════════════════════════════════╗");
        System.out.println("║       🎮 TIC TAC TOE GAME 🎮        ║");
        System.out.println("╚═══════════════════════════════════════╝" + RESET);
    }

    private void printScoreboard() {
        System.out.println("\n" + YELLOW + "┌─────────── SCOREBOARD ───────────┐");
        System.out.println("│ " + player1Name + " (X): " + player1Score + " | " + 
                          player2Name + " (O): " + player2Score + " | Draws: " + draws + " │");
        System.out.println("└──────────────────────────────────┘" + RESET);
    }

    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    private void printBoard() {
        System.out.println("\n" + BLUE + "     1   2   3");
        System.out.println("   ┌───┬───┬───┐");
        for (int i = 0; i < 3; i++) {
            System.out.print(" " + (i + 1) + " │");
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 'X') {
                    System.out.print(RED + " " + board[i][j] + " " + BLUE);
                } else if (board[i][j] == 'O') {
                    System.out.print(GREEN + " " + board[i][j] + " " + BLUE);
                } else {
                    System.out.print(" " + board[i][j] + " ");
                }
                System.out.print("│");
            }
            System.out.println();
            if (i < 2) {
                System.out.println("   ├───┼───┼───┤");
            }
        }
        System.out.println("   └───┴───┴───┘" + RESET);
    }

    private void getPlayerNames() {
        clearScreen();
        printHeader();
        System.out.print("\n" + CYAN + "Enter Player 1 name (X): " + RESET);
        player1Name = scanner.nextLine().trim();
        if (player1Name.isEmpty()) player1Name = "Player 1";

        System.out.print(CYAN + "Enter Player 2 name (O): " + RESET);
        player2Name = scanner.nextLine().trim();
        if (player2Name.isEmpty()) player2Name = "Player 2";
    }

    private int[] getMove() {
        while (true) {
            try {
                String currentPlayerName = (currentPlayer == 'X') ? player1Name : player2Name;
                String color = (currentPlayer == 'X') ? RED : GREEN;
                
                System.out.print("\n" + color + currentPlayerName + "'s turn (" + 
                               currentPlayer + ")" + RESET);
                System.out.print(" - Enter move (row col) e.g., '1 2': ");
                
                String input = scanner.nextLine().trim();
                String[] parts = input.split(" ");
                
                if (parts.length != 2) {
                    System.out.println(RED + "❌ Invalid format! Use: row col (e.g., 1 2)" + RESET);
                    continue;
                }
                
                int row = Integer.parseInt(parts[0]) - 1;
                int col = Integer.parseInt(parts[1]) - 1;
                
                if (row < 0 || row > 2 || col < 0 || col > 2) {
                    System.out.println(RED + "❌ Numbers must be between 1 and 3!" + RESET);
                    continue;
                }
                
                if (board[row][col] != ' ') {
                    System.out.println(RED + "❌ That spot is already taken!" + RESET);
                    continue;
                }
                
                return new int[]{row, col};
                
            } catch (NumberFormatException e) {
                System.out.println(RED + "❌ Please enter numbers only!" + RESET);
            }
        }
    }

    private boolean checkWinner() {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && 
                board[i][2] == currentPlayer) return true;
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && 
                board[2][i] == currentPlayer) return true;
        }
        
        // Check diagonals
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && 
            board[2][2] == currentPlayer) return true;
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && 
            board[2][0] == currentPlayer) return true;
        
        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') return false;
            }
        }
        return true;
    }

    private void printWinner() {
        String winnerName = (currentPlayer == 'X') ? player1Name : player2Name;
        String color = (currentPlayer == 'X') ? RED : GREEN;
        
        System.out.println("\n" + color + "╔═══════════════════════════════════════╗");
        System.out.println("║           🎉 " + winnerName + " WINS! 🎉          ║");
        System.out.println("╚═══════════════════════════════════════╝" + RESET);
    }

    private void printDraw() {
        System.out.println("\n" + YELLOW + "╔═══════════════════════════════════════╗");
        System.out.println("║           🤝 IT'S A DRAW! 🤝          ║");
        System.out.println("╚═══════════════════════════════════════╝" + RESET);
    }

    private void play() {
        getPlayerNames();
        
        boolean playAgain = true;
        while (playAgain) {
            initializeBoard();
            currentPlayer = 'X';
            boolean gameOver = false;
            
            while (!gameOver) {
                clearScreen();
                printHeader();
                printScoreboard();
                printBoard();
                
                int[] move = getMove();
                board[move[0]][move[1]] = currentPlayer;
                
                if (checkWinner()) {
                    clearScreen();
                    printHeader();
                    printScoreboard();
                    printBoard();
                    printWinner();
                    
                    if (currentPlayer == 'X') {
                        player1Score++;
                    } else {
                        player2Score++;
                    }
                    gameOver = true;
                } else if (isBoardFull()) {
                    clearScreen();
                    printHeader();
                    printScoreboard();
                    printBoard();
                    printDraw();
                    draws++;
                    gameOver = true;
                } else {
                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                }
            }
            
            System.out.print("\n" + CYAN + "Play again? (y/n): " + RESET);
            String response = scanner.nextLine().trim().toLowerCase();
            playAgain = response.equals("y") || response.equals("yes");
        }
        
        System.out.println("\n" + PURPLE + "╔═══════════════════════════════════════╗");
        System.out.println("║      Thanks for playing! Goodbye! 👋   ║");
        System.out.println("╚═══════════════════════════════════════╝" + RESET);
        scanner.close();
    }

    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        game.play();
    }
}