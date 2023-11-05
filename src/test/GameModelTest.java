import org.junit.jupiter.api.Test;
import se.iths.tictactoe.game.GameModel;
import se.iths.tictactoe.game.Player;

public class GameModelTest {

    @Test
    public void intToPlayerEnumTest() {
      GameModel gameModel=new GameModel(3);
        Player e = gameModel.mapOrdinalToPlayerEnum(0);
        System.out.println(e);
        assert (e.ordinal() == Player.human.ordinal());

    }
}
