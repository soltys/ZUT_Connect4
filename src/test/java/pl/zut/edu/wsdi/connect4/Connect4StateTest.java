package pl.zut.edu.wsdi.connect4;

import java.util.Random;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Unit test for simple App.
 */
@RunWith(JUnit4.class)
public class Connect4StateTest {

    @Test
    public void moveTest() {
        Connect4State state = new Connect4State(2, 2);
        state.move(BoardType.playerOne, 1);
        assertEquals(BoardType.playerOne, state.getBoardValue(1, 1));
    }
    // <editor-fold desc="Constuctors tests">

    @Test
    public void normal_Constuctor() {
        final int rows = 4;
        final int columns = 4;
        Connect4State state = new Connect4State(rows, columns);
        assertNull(state.getParent());
        assertEquals(rows, state.getRows());
        assertEquals(columns, state.getColumns());
        assertEquals(rows, state.getBoard().length);
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                assertEquals(state.getBoard()[row][column], BoardType.empty);
            }
        }
    }

    @Test
    public void copy_constructor() {
        final int rows = 4;
        final int columns = 4;
        Connect4State parentState = new Connect4State(rows, columns);
        Random randomGenerator = new Random();
        for (int index = 0; index < 3; index++) {
            parentState.setBoardValue(randomGenerator.nextInt(rows),
                                      randomGenerator.nextInt(columns),
                                      BoardType.playerOne);
            parentState.setBoardValue(randomGenerator.nextInt(rows),
                                      randomGenerator.nextInt(columns),
                                      BoardType.playerTwo);
        }

        Connect4State childState = new Connect4State(parentState);
        assertSame(parentState, childState.getParent());
        assertEquals(rows, childState.getRows());
        assertEquals(columns, childState.getColumns());
        assertEquals(rows, childState.getBoard().length);
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                assertEquals(parentState.getBoard()[row][column],
                             childState.getBoard()[row][column]);
            }
        }
    }
    // </editor-fold>

    // <editor-fold desc="Check Win tests">
    @Test
    public void checkWin_empty_board() {
        Connect4State state = new Connect4State(4, 4);

        assertEquals(BoardStatus.notEnded, state.checkWin());
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(state);
    }

      @Test
    public void checkWin_one_move_not_ended_7x7() {
        Connect4State state = new Connect4State(4, 4);
        state.move(BoardType.playerOne, 3);
        assertEquals(BoardStatus.notEnded, state.checkWin());
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(state);
    }

    @Test
    public void checkWin_one_move_not_ended() {
        Connect4State state = new Connect4State(4, 4);
        state.move(BoardType.playerOne, 2);
        assertEquals(BoardStatus.notEnded, state.checkWin());
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(state);
    }

    @Test
    public void checkWin_two_moves_not_ended() {
        Connect4State state = new Connect4State(4, 4);
        state.move(BoardType.playerOne, 2);
        state.move(BoardType.playerTwo, 2);
        assertEquals(BoardStatus.notEnded, state.checkWin());
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(state);
    }

    @Test
    public void checkWin_horizontalLine() {
        Connect4State state = new Connect4State(4, 4);

        for (int i = 0; i < 4; i++) {
            state.move(BoardType.playerOne, i);
        }

        assertEquals(BoardStatus.winnerPlayerOne, state.checkWin());
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(state);
    }

    @Test
    public void checkWin_verticalLine() {
        Connect4State state = new Connect4State(4, 4);
        for (int i = 0; i < 4; i++) {
            state.move(BoardType.playerOne, 0);
        }
        assertEquals(BoardStatus.winnerPlayerOne, state.checkWin());
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(state);
    }

    @Test
    public void checkWin_diagonal_line_up_left_to_down_right() {
        Connect4State state = new Connect4State(4, 4);
        for (int i = 0; i < 4; i++) {
            state.setBoardValue(i, i, BoardType.playerOne);
        }
        assertEquals(BoardStatus.winnerPlayerOne, state.checkWin());
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(state);
    }

    @Test
    public void checkWin_diagonal_line_up_left_to_down_right_up_one() {
        Connect4State state = new Connect4State(5, 5);
        for (int i = 0; i < 4; i++) {
            state.setBoardValue(i, i + 1, BoardType.playerOne);
        }
        assertEquals(BoardStatus.winnerPlayerOne, state.checkWin());
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(state);
    }

    @Test
    public void checkWin_diagonal_line_up_left_to_down_right_down_one() {
        Connect4State state = new Connect4State(5, 5);
        for (int i = 0; i < 4; i++) {
            state.setBoardValue(i + 1, i, BoardType.playerOne);
        }
        assertEquals(BoardStatus.winnerPlayerOne, state.checkWin());
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(state);
    }

    @Test
    public void checkWin_diagonal_line_up_right_to_down_left() {
        Connect4State state = new Connect4State(4, 4);
        for (int i = 3; i >= 0; i--) {
            state.setBoardValue(3 - i, i, BoardType.playerOne);
        }
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(state);
        assertEquals(BoardStatus.winnerPlayerOne, state.checkWin());
    }

    @Test
    public void checkWin_diagonal_line_up_right_to_down_left_one_up() {
        Connect4State state = new Connect4State(5, 5);
        for (int i = 3; i >= 0; i--) {
            state.setBoardValue(3 - i, i, BoardType.playerOne);
        }
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(state);
        assertEquals(BoardStatus.winnerPlayerOne, state.checkWin());
    }

    @Test
    public void checkWin_diagonal_line_up_right_to_down_left_one_down() {
        Connect4State state = new Connect4State(5, 5);
        for (int i = 3; i >= 0; i--) {
            state.setBoardValue(4 - i, i + 1, BoardType.playerOne);
        }
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(state);
        BoardStatus status = state.checkWin();
        assertEquals(BoardStatus.winnerPlayerOne, status);
    }
    // </editor-fold>
}
