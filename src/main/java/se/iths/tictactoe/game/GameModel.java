package se.iths.tictactoe.game;

import se.iths.tictactoe.enums.GameMode;
import se.iths.tictactoe.enums.Player;
import se.iths.tictactoe.services.MappingService;
import se.iths.tictactoe.services.MinMax;

import java.util.Random;

public class GameModel {

    private static final int BOARD_SIZE = 3;
    public String currentBoardString = "";
    private int[][] board;
    private int playerXScore = 0;
    private int playerOScore = 0;
    private Player winner;
    private Player currentPlayer = Player.NONE;
    private GameMode gameMode = GameMode.NONE;
    private final MinMax minMax = new MinMax(this);
    private final MappingService mappingService = new MappingService();

    public GameModel() {
        this.board = new int[BOARD_SIZE][BOARD_SIZE];
        resetPlayer();
    }

    public int[][] getBoard() {
        return board;
    }

    public void setNewBoard(int[][] board) {
        this.board = board;
    }


    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    void vsPlayerSetPlayer1() {
        if(currentPlayer == Player.NONE) {
            currentPlayer = Player.PLAYER1;
        }
    }

    public void vsPlayerSetPlayer2() {
        if(currentPlayer == Player.NONE) {
            currentPlayer = Player.PLAYER2;
        }
    }

    public Player getWinner() {
        return winner;
    }

    public int getPlayerXScore() {
        return playerXScore;
    }

    public int getPlayerOScore() {
        return playerOScore;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
        if (gameMode == GameMode.PLAYERVSCOMPUTER) currentPlayer = Player.PLAYER1;
    }

    public void resetCurrentBoardString() {
        currentBoardString = "";
    }

    public int[] computerMove(int difficulty) {
        int[] bestMove = minMax.findBestMove(board);
        Random random = new Random();
        int randomNumber = random.nextInt(100);
        if (randomNumber <= difficulty) return bestMove;
        return randomMove();
    }

    public int[] randomMove() {
        Random random = new Random();
        int i;
        int j;
        boolean isFree;
        do {
            i = random.nextInt(3);
            j = random.nextInt(3);
            isFree = isBoardSet(board, i, j, Player.COMPUTER);
        } while (!isFree);
        return new int[] {i ,j };
    }


    public void changePlayer() {
            if (currentPlayer == Player.PLAYER1) currentPlayer = Player.COMPUTER;
            else currentPlayer = Player.PLAYER1;
    }

    public void resetPlayer() {
        if (gameMode == GameMode.PLAYERVSCOMPUTER) currentPlayer = Player.PLAYER1;
    }

    public boolean isBoardSet(int[][] board, int i, int j, Player player) {
        if (board[i][j] != Player.NONE.ordinal()) return false;
        this.board[i][j] = player.ordinal();
        return true;
    }

    public boolean isGameOver(int[][] board) {
        Player value = evaluateBoard(board);
        if (value != Player.NONE) {
            winner = value;
            updateScore();
            return true;
        }
        if (isBordFull(board)) {
            winner = Player.NONE;
            return true;
        }
        return false;
    }

    public boolean isBordFull(int[][] board) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == Player.NONE.ordinal()) return false;
            }
        }
        return true;
    }

    public Player evaluateBoard(int[][] board) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != Player.NONE.ordinal() && board[i][0] == board[i][1] && board[i][0] == board[i][2])
                return mappingService.mapOrdinalToPlayerEnum(board[i][0]);
            if (board[0][i] != Player.NONE.ordinal() && board[0][i] == board[1][i] && board[0][i] == board[2][i])
                return mappingService.mapOrdinalToPlayerEnum(board[0][i]);
        }
        if (board[0][0] != Player.NONE.ordinal() && board[0][0] == board[1][1] && board[0][0] == board[2][2])
            return mappingService.mapOrdinalToPlayerEnum(board[0][0]);
        if (board[0][2] != Player.NONE.ordinal() && board[0][2] == board[1][1] && board[0][2] == board[2][0])
            return mappingService.mapOrdinalToPlayerEnum(board[0][2]);

        return Player.NONE;
    }

    private void updateScore() {
        if (winner == Player.PLAYER1) playerXScore++;
        if (winner == Player.PLAYER2) playerOScore++;
        if (winner == Player.COMPUTER) playerOScore++;
    }

    public void resetBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = Player.NONE.ordinal();
            }
        }
    }
}
