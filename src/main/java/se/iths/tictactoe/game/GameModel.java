package se.iths.tictactoe.game;

import se.iths.tictactoe.services.MinMax;

import java.util.Random;

public class GameModel {

    private static final int BOARD_SIZE = 3;
    private static final String SERVER_URL = "https://ntfy.sh/ej-tic-tac-toe";
    private int[][] board;
    private int playerScore = 0;
    private int computerScore = 0;
    private Player winner;
    private Player currentPlayer = Player.HUMAN;
    private MinMax minMax = new MinMax(this);



    public GameModel() {
        this.board = new int[BOARD_SIZE][BOARD_SIZE];
        resetPlayer();
    }

    public int[][] getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getWinner() {
        return winner;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public int getComputerScore() {
        return computerScore;
    }

    //region player

    public void resetPlayer() {
        currentPlayer = Player.HUMAN;
    }
    //endregion


    //region computer

    public int[] computerMove(int difficulty) {
        int[] bestMove = minMax.findBestMove(board);
        Random random = new Random();
        int randomNumber = random.nextInt(100);
        if (randomNumber <= difficulty) {
            return bestMove;
        }
        return randomMove();
    }

    public int[] randomMove() {
        Random random = new Random();
        int i = 0;
        int j = 0;
        boolean isFree = true;
        do {
            i = random.nextInt(3);
            j = random.nextInt(3);
            isFree = isBoardSet(board, i, j, Player.COMPUTER);
        } while (isFree == false);
        return new int[] {i ,j };
    }
    //endregion


    //region common
    public boolean isBoardSet(int[][] board, int i, int j, Player player) {
        if (board[i][j] != Player.NONE.ordinal()) return false;
        this.board[i][j] = player.ordinal();
        return true;
    }
    public void changePlayer() {
        if (currentPlayer == Player.HUMAN) currentPlayer = Player.COMPUTER;
        else currentPlayer = Player.HUMAN;
    }

    public boolean isGameOver(int[][] board) {
        Player value = evaluateBoard(board);
        if (value != Player.NONE) {
            winner = value;
            updateScore();
            return true;
        }
        if (boardIsFull(board)) {
            winner = Player.NONE;
            return true;
        }
        return false;
    }

    public boolean boardIsFull(int[][] board) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == Player.NONE.ordinal()) {
                    return false;
                }
            }
        }
        return true;
    }

    public Player evaluateBoard(int[][] board) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != Player.NONE.ordinal() && board[i][0] == board[i][1] && board[i][0] == board[i][2])
                return mapOrdinalToPlayerEnum(board[i][0]);
            if (board[0][i] != Player.NONE.ordinal() && board[0][i] == board[1][i] && board[0][i] == board[2][i])
                return mapOrdinalToPlayerEnum(board[0][i]);
        }
        if (board[0][0] != Player.NONE.ordinal() && board[0][0] == board[1][1] && board[0][0] == board[2][2])
            return mapOrdinalToPlayerEnum(board[0][0]);
        if (board[0][2] != Player.NONE.ordinal() && board[0][2] == board[1][1] && board[0][2] == board[2][0])
            return mapOrdinalToPlayerEnum(board[0][2]);

        return Player.NONE;
    }

     public Player mapOrdinalToPlayerEnum(int ordinal) {

        for (Player value : Player.values()) {
            if(value.ordinal() == ordinal) return value;
        }
        return null;
    }

    private void updateScore() {
        if (winner == Player.HUMAN) playerScore++;
        if (winner == Player.COMPUTER) computerScore++;
    }
    //endregion

    public void resetBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = Player.NONE.ordinal();
            }
        }
    }

}
