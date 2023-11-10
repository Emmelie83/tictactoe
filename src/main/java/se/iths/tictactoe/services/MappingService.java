package se.iths.tictactoe.services;

import se.iths.tictactoe.game.Player;

public class MappingService {


    private String[] playerToValue = new String[4];

    public MappingService() {
        initPlayerToValue();
    }

    public String getValueFromPlayerEnum(Player player) {
        try {
            return playerToValue[player.ordinal()];
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Unable to map enum to value");
            return "E";
        }
    }

    private void initPlayerToValue() {
        playerToValue[Player.NONE.ordinal()] = " ";
        playerToValue[Player.PLAYER1.ordinal()] = "X";
        playerToValue[Player.PLAYER2.ordinal()] = "O";
        playerToValue[Player.COMPUTER.ordinal()] = "O";
    }

    public String boardToString(int[][] board) {
        String result = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                result += board[i][j];
            }

        }
        return result;
    }

    public int[][] stringToBoard(String position) {
        char[] chars = position.toCharArray();

        int size = chars.length;
        int[][] board = new int[3][3];

        for (int i = 0; i < size; i++) {
            int row = i / 3;
            int col = i % 3;
            board[row][col] = Character.getNumericValue(chars[i]);
        }

        return board;
    }
}
