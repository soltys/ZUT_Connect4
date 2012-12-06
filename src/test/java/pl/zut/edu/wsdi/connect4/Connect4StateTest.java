package pl.zut.edu.wsdi.connect4;

import static org.junit.Assert.assertEquals;

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
        state.Move(BoardType.playerOne, 1);
        assertEquals(BoardType.playerOne, state.getBoardValue(1, 1));
    }

    @Test
    public void checkWin_horizontalLine() {
        Connect4State state = new Connect4State(4, 4);
        for (int i = 0; i < 4; i++) {
            state.Move(BoardType.playerOne, i);
        }

        assertEquals(BoardStatus.winnerPlayerOne, state.checkWin());
    }

    @Test
    public void checkWin_verticalLine() {
        Connect4State state = new Connect4State(4, 4);
        for (int i = 0; i < 4; i++) {
            state.Move(BoardType.playerOne, 0);
        }

        assertEquals(BoardStatus.winnerPlayerOne, state.checkWin());
    }
}
