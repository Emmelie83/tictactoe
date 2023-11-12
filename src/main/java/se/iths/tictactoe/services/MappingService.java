package se.iths.tictactoe.services;

import javafx.scene.paint.Color;
import se.iths.tictactoe.enums.Command;
import se.iths.tictactoe.enums.Player;

import java.util.Objects;

public class MappingService {


    private static final Color[] playerToColor = new Color[Player.COMPUTER.ordinal() + 1];
    private static final String[] playerToValue = new String[Player.COMPUTER.ordinal() + 1];

    public MappingService() {
        initPlayerToValue();
        initPlayerToColor();
    }

    public String getValueFromPlayerEnum(Player player) {
        try {
            return playerToValue[player.ordinal()];
        } catch (IndexOutOfBoundsException e) {
            System.out.println("getValueFromPlayerEnum() Unable to map enum to value");
            return "E";
        }
    }

    public Color getColorFromPlayerEnum(Player player) {
        try {
            return playerToColor[player.ordinal()];
        } catch (IndexOutOfBoundsException e) {
            System.out.println("getColorFromPlayerEnum() Unable to map enum to value");
            return Color.BLACK;
        }
    }


    private void initPlayerToValue() {
        playerToValue[Player.NONE.ordinal()] = " ";
        playerToValue[Player.PLAYER1.ordinal()] = "X";
        playerToValue[Player.PLAYER2.ordinal()] = "O";
        playerToValue[Player.COMPUTER.ordinal()] = "O";
    }

    private void initPlayerToColor() {
        playerToColor[Player.NONE.ordinal()] = Color.TRANSPARENT;
        playerToColor[Player.PLAYER1.ordinal()] = Color.RED;
        playerToColor[Player.PLAYER2.ordinal()] = Color.GREEN;
        playerToColor[Player.COMPUTER.ordinal()] = Color.GREEN;
    }

    public String boardToString(int[][] board) {
        String result = "";
        for (int[] ints : board) {
            for (int anInt : ints) {
                result += anInt;
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

    public Player mapOrdinalToPlayerEnum(int ordinal) {
        for (Player value : Player.values()) {
            if(value.ordinal() == ordinal) return value;
        }
        return null;
    }

    public Command mapStringToCommand(String stringCommand) {
        for (Command value : Command.values()) {
            if(Objects.equals(value.toString(), stringCommand)) return value;
        }
        return null;
    }
}
