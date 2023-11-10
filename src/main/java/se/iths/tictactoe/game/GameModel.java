package se.iths.tictactoe.game;

import se.iths.tictactoe.services.MinMax;

import java.util.Random;

public class GameModel {


    private int[][] board;

    private int playerScore = 0;

    private int computerScore = 0;


    private Player winner;

    private Player currentPlayer = Player.human;

    private MinMax minMax = new MinMax(this);



    public GameModel(int size) {
        this.board = new int[size][size];
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
        currentPlayer = Player.human;
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
            System.out.println(i);
            j = random.nextInt(3);
            System.out.println(j);
            isFree = isBoardSet(board, i, j, Player.computer);
        } while (isFree == false);
        return new int[] {i ,j };
    }
    //endregion


    //region common
    public boolean isBoardSet(int[][] board, int i, int j, Player player) {
        if (board[i][j] != Player.none.ordinal()) return false;
        this.board[i][j] = player.ordinal();
        return true;
    }
    public void changePlayer() {
        if (currentPlayer == Player.human) currentPlayer = Player.computer;
        else currentPlayer = Player.human;
    }

    public boolean isGameOver(int[][] board) {
        Player value = evaluateBoard(board);
        if (value != Player.none) {
            winner = value;
            updateScore();
            return true;
        }
        if (boardIsFull(board)) {
            winner = Player.none;
            return true;
        }
        return false;
    }

    public boolean boardIsFull(int[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == Player.none.ordinal()) {
                    return false;
                }
            }
        }
        return true;
    }

    public Player evaluateBoard(int[][] board) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != Player.none.ordinal() && board[i][0] == board[i][1] && board[i][0] == board[i][2])
                return mapOrdinalToPlayerEnum(board[i][0]);
            if (board[0][i] != Player.none.ordinal() && board[0][i] == board[1][i] && board[0][i] == board[2][i])
                return mapOrdinalToPlayerEnum(board[0][i]);
        }
        if (board[0][0] != Player.none.ordinal() && board[0][0] == board[1][1] && board[0][0] == board[2][2])
            return mapOrdinalToPlayerEnum(board[0][0]);
        if (board[0][2] != Player.none.ordinal() && board[0][2] == board[1][1] && board[0][2] == board[2][0])
            return mapOrdinalToPlayerEnum(board[0][2]);

        return Player.none;
    }

     public Player mapOrdinalToPlayerEnum(int ordinal) {

        for (Player value : Player.values()) {
            if(value.ordinal() == ordinal) return value;
        }
        return null;
    }

    private void updateScore() {
        if (winner == Player.human) playerScore++;
        if (winner == Player.computer) computerScore++;
    }
    //endregion

    public void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = Player.none.ordinal();
            }
        }
    }

}
