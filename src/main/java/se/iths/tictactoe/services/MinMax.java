package se.iths.tictactoe.services;

import se.iths.tictactoe.game.GameModel;
import se.iths.tictactoe.game.Player;

public class MinMax {

    private GameModel gameModel;

    private final int MAX_DEPTH = 10;

    public MinMax(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public int[] findBestMove(int[][] board)
    {
        int bestVal = -1000;
        int[] bestMove = new int[] {-1, -1};

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (board[i][j] == Player.none.ordinal())
                {
                    board[i][j] = Player.computer.ordinal();

                    int moveVal = minimax(board, MAX_DEPTH, false);

                    board[i][j] = Player.none.ordinal();

                    if (moveVal > bestVal)
                    {
                        bestMove[0] = i;
                        bestMove[1] = j;
                        bestVal = moveVal;
                    }
                }
            }

        }
        System.out.printf("The value of the best Move " +
                "is : %d\n\n", bestVal);
        System.out.println(bestMove[0] + " " + bestMove[1]);
        return bestMove;
    }

    private int minimax(int[][] board, int depth, Boolean isMaxComputer)
    {
        Player player = gameModel.evaluateBoard(board);

        // If Maximizer has won the game
        int boardValue = 0;// return his/her evaluated score
        if (player == Player.computer)
            boardValue = 10;

        // If Minimizer has won the game
        // return his/her evaluated score
        if (player == Player.human)
            boardValue = -10;

        if (Math.abs(boardValue) == 10 || depth == 0 || gameModel.boardIsFull(board)) {
            return boardValue;
        }


        // If this maximizer's move
        if (isMaxComputer)
        {
            int best = -1000;

            // Traverse all cells
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    // Check if cell is empty
                    if (board[i][j] == Player.none.ordinal())
                    {
                        // Make the move
                        board[i][j] = Player.computer.ordinal();

                        // Call minimax recursively and choose
                        // the maximum value
                        int current = minimax(board, depth - 1, !isMaxComputer);
                        best = Math.max(best, current);

                        // Undo the move
                        board[i][j] = Player.none.ordinal();
                    }
                }
            }
            return best;
        }

        // If this minimizer's move
        else
        {
            int best = 1000;

            // Traverse all cells
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    // Check if cell is empty
                    if (board[i][j] == Player.none.ordinal())
                    {
                        // Make the move
                        board[i][j] = Player.human.ordinal();

                        // Call minimax recursively and choose
                        // the minimum value
                        int current = minimax(board, depth - 1, !isMaxComputer);
                        best = Math.min(best, current);

                        // Undo the move
                        board[i][j] = Player.none.ordinal();
                    }
                }
            }
            return best;
        }
    }
}
