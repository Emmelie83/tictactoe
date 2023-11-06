import org.junit.jupiter.api.Test;
import se.iths.tictactoe.game.GameModel;
import se.iths.tictactoe.game.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class GameModelTest {

    GameModel gameModel = new GameModel(3);

    @Test
    public void intToPlayerEnumTest() {
        Player e = gameModel.mapOrdinalToPlayerEnum(0);
        assert (e.ordinal() == Player.none.ordinal());
    }

    @Test
    public void isBoardSetTest() {
        int[][] testMoves = {{0, 0}, {1, 1}, {2, 2}, {1, 1}};
        ArrayList<Boolean> results = new ArrayList<Boolean>();
        Arrays.stream(testMoves).forEach(move -> results.add(gameModel.isBoardSet(move[0], move[1], Player.human)));
        for (int j = 0; j < results.size(); j++)
            switch (j) {
                case 0:
                    assert (results.get(j));
                    break;
                case 1:
                    assert (results.get(j));
                    break;
                case 2:
                    assert (results.get(j));
                    break;
                case 3:
                    assert (results.get(j) == false);
                    break;
            }
    }

    @Test
    public void isGameOverTest() {
        int[][][] testBoards = {{
                {0, 2, 0},
                {1, 1, 1},
                {0, 2, 0}
        },
        {
                {2, 0, 1},
                {0, 2, 1},
                {1, 0, 2}
        },
        {
                {2, 1, 0},
                {0, 1, 0},
                {0, 1, 2}
        },
        {
                {2, 1, 0},
                {0, 0, 0},
                {0, 1, 2}
        },
        {
                {1, 2, 2},
                {2, 1, 1},
                {1, 1, 2}
        }};
        ArrayList<Boolean> results = new ArrayList<Boolean>();
        Arrays.stream(testBoards).forEach(testBoard -> results.add(gameModel.isGameOver(testBoard)));
        for (int j = 0; j < results.size(); j++)
            switch (j) {
                case 0:
                    assert (results.get(j));
                    break;
                case 1:
                    assert (results.get(j));
                    break;
                case 2:
                    assert (results.get(j));
                    break;
                case 3:
                    assert (results.get(j) == false);
                    break;
                case 4:
                    assert (results.get(j));
                    break;
            }
    }
}
