package se.iths.tictactoe.game;

public class GameModel {


    private int[][] board;



    public GameModel(int size) {

        this.board = new int[size][size];
    }

    public int[][] getBoard() {
        return board;
    }

    public boolean setBoard(int i, int j, BoardValue e) {
        if (board[i][j] != 0) return false;
        this.board[i][j] = e.ordinal();
        return true;
    }


}
