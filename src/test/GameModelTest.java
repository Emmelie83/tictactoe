import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import se.iths.tictactoe.game.GameModel;
import se.iths.tictactoe.enums.Player;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class GameModelTest {

    GameModel gameModel = new GameModel();


    @ParameterizedTest
    @MethodSource("validTestMoves")
    void testThatMovesAreValid(int[] move) {
        int[][] board = {
                {0, 0, 2},
                {1, 0, 1},
                {0, 2, 0}};
        int row = move[0];
        int col = move[1];
        boolean result = gameModel.isBoardSet(board, row, col, Player.PLAYER1);
        assertThat(result).isTrue();
    }

    static Collection<int[]> validTestMoves() {
        return Arrays.asList(new int[][]{
                {0, 0},
                {1, 1},
                {2, 2},
                {0, 1},
                {2, 0}
        });
    }

    @ParameterizedTest
    @MethodSource("nonValidTestMoves")
    void testThatMovesAreNotValid(int[] move) {
        int[][] board = {
                {0, 0, 2},
                {1, 0, 1},
                {0, 2, 0}};
        int row = move[0];
        int col = move[1];
        boolean result = gameModel.isBoardSet(board, row, col, Player.PLAYER1);
        assertThat(result).isFalse();
    }

    static Collection<int[]> nonValidTestMoves() {
        return Arrays.asList(new int[][]{
                {0, 2},
                {1, 0},
                {1, 2},
                {2, 1}
        });
    }


    @ParameterizedTest
    @MethodSource("testBoardsWithWin")
    void testThatBoardWithWinGivesGameOver(int[][] testBoard) {
        boolean result = gameModel.isGameOver(testBoard);
        assertThat(result).isTrue();
    }

    static Collection<int[][]> testBoardsWithWin() {
        return Arrays.asList(new int[][][]{
                {
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
                        {1, 2, 2},
                        {2, 1, 1},
                        {1, 1, 2}
                }
        });
    }

    @ParameterizedTest
    @MethodSource("testBoardWithDraw")
    void testThatBoardWithDrawGivesGameOver(int[][] testBoard) {
        boolean result = gameModel.isGameOver(testBoard);
        assertThat(result).isTrue();
    }

    static Collection<int[][]> testBoardWithDraw() {
        return Arrays.asList(new int[][][]{
                {
                        {1, 2, 1},
                        {2, 2, 1},
                        {1, 1, 2}

                },
                {
                        {1, 2, 2},
                        {2, 1, 1},
                        {1, 1, 2}
                }
        });
    }

    @ParameterizedTest
    @MethodSource("testBoardWithMovesLeft")
    void testThatBoardWithMovesLeftDoesntGiveGameOver(int[][] testBoard) {
        boolean result = gameModel.isGameOver(testBoard);
        assertThat(result).isFalse();
    }

    static Collection<int[][]> testBoardWithMovesLeft() {
        return Arrays.asList(new int[][][]{
                {
                        {2, 1, 0},
                        {0, 0, 0},
                        {0, 1, 2}
                },
                {
                        {1, 0, 2},
                        {2, 1, 1},
                        {0, 1, 2}
                }
        });
    }
}
