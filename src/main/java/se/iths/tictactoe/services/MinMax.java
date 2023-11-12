package se.iths.tictactoe.services;

import se.iths.tictactoe.game.GameModel;
import se.iths.tictactoe.enums.Player;

public class MinMax {

    private GameModel gameModel;

    private final int MAX_DEPTH = 10;

    public MinMax(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public int[] findBestMove(int[][] board) {
        int bestVal = -1000;
        int[] bestMove = new int[]{-1, -1};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                if (board[i][j] == Player.NONE.ordinal()) {
                    board[i][j] = Player.COMPUTER.ordinal();

                    int moveVal = minimax(board, MAX_DEPTH, false);

                    board[i][j] = Player.NONE.ordinal();

                    if (moveVal > bestVal) {
                        bestMove[0] = i;
                        bestMove[1] = j;
                        bestVal = moveVal;
                    }
                }

        }
        return bestMove;
    }

    private int minimax(int[][] board, int depth, Boolean isMaxComputerMove) {
        Player player = gameModel.evaluateBoard(board);

        int boardValue = 0;
        if (player == Player.COMPUTER) {
            boardValue = 10;
            if (player == Player.PLAYER1)
                boardValue = -10;
        } else if (player == Player.PLAYER1) boardValue = -10;

        if (Math.abs(boardValue) == 10 || depth == 0 || gameModel.isBordFull(board)) return boardValue;


        if (isMaxComputerMove) {
            int best = -1000;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {

                    if (board[i][j] == Player.NONE.ordinal()) {
                        board[i][j] = Player.COMPUTER.ordinal();

                        int current = minimax(board, depth - 1, !isMaxComputerMove);
                        best = Math.max(best, current);

                        board[i][j] = Player.NONE.ordinal();
                    }
                }
            }
            return best;
        }

        else {
            int best = 1000;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {

                    if (board[i][j] == Player.NONE.ordinal()) {

                        board[i][j] = Player.PLAYER1.ordinal();

                        int current = minimax(board, depth - 1, !isMaxComputerMove);
                        best = Math.min(best, current);

                        board[i][j] = Player.NONE.ordinal();
                    }
                }
            }
            return best;
        }
    }
}
