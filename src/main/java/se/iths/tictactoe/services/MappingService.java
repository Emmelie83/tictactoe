package se.iths.tictactoe.services;

import se.iths.tictactoe.game.Player;

public class MappingService {


    private String[] playerToValue = new String[3];

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
        playerToValue[Player.HUMAN.ordinal()] = "X";
        playerToValue[Player.COMPUTER.ordinal()] = "O";
    }
}
