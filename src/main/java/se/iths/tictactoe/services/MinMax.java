package se.iths.tictactoe.services;

import se.iths.tictactoe.game.GameModel;
import se.iths.tictactoe.game.Player;

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
                if (board[i][j] == Player.none.ordinal()) {
                    board[i][j] = Player.computer.ordinal();

                    int moveVal = minimax(board, MAX_DEPTH, false);

                    board[i][j] = Player.none.ordinal();

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
        if (player == Player.computer)
            boardValue = 10;

        if (player == Player.human)
            boardValue = -10;

        if (Math.abs(boardValue) == 10 || depth == 0 || gameModel.boardIsFull(board)) return boardValue;


        if (isMaxComputerMove) {
            int best = -1000;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {

                    if (board[i][j] == Player.none.ordinal()) {
                        board[i][j] = Player.computer.ordinal();

                        int current = minimax(board, depth - 1, !isMaxComputerMove);
                        best = Math.max(best, current);

                        board[i][j] = Player.none.ordinal();
                    }
                }
            }
            return best;
        }

        else {
            int best = 1000;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {

                    if (board[i][j] == Player.none.ordinal()) {

                        board[i][j] = Player.human.ordinal();

                        int current = minimax(board, depth - 1, !isMaxComputerMove);
                        best = Math.min(best, current);

                        board[i][j] = Player.none.ordinal();
                    }
                }
            }
            return best;
        }
    }
}
