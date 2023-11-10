import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import se.iths.tictactoe.services.MappingService;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MappingServiceTest {

    @Test
    public void boardToStringTest() {
        MappingService mappingService = new MappingService();
        int[][] board = {
                {0, 0, 2},
                {1, 0, 1},
                {0, 2, 0}};

        System.out.println(mappingService.boardToString(board));
    }

    @Test
    public void stringToBoardTest() {
        MappingService mappingService = new MappingService();
        String position = "002101020";
        System.out.println(Arrays.deepToString(mappingService.stringToBoard(position)));
    }

}