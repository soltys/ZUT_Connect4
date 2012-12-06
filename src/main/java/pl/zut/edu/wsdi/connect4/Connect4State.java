package pl.zut.edu.wsdi.connect4;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import pl.zut.edu.wsdi.klesk.math.search.StateImpl;

/**
 *
 * @author Soltys
 */
public class Connect4State extends StateImpl {

    private BoardType[][] board;
    private int rows;
    private int columns;

    public Connect4State(int rows, int columns) {
        super(null);

        this.rows = rows;
        this.columns = Math.min(columns, 10);

        board = new BoardType[this.rows][this.columns];

        for (int row = 0; row < this.rows; row++) {
            for (int column = 0; column < this.columns; column++) {
                board[row][column] = BoardType.empty;
            }
        }
    }

    public BoardType getBoardValue(int row, int column) {
        return board[row][column];
    }

    public void setBoardValue(int row, int column, BoardType value) {
        board[row][column] = value;
    }

    /**
     * Make move in column
     *
     * @param player
     * @param column
     */
    public void Move(BoardType player, int column) {
        for (int row = this.rows - 1; row >= 0; row--) {
            if (board[row][column] == BoardType.empty) {
                board[row][column] = player;
            }
        }
    }

    private BoardStatus toBoardStatus(BoardType boardType) {
        if (boardType == BoardType.empty) {
            throw new RuntimeException("toBoardStatus do not handle BoardType.empty");
        }
        if (boardType == boardType.playerOne) {
            return BoardStatus.winnerPlayerOne;
        } else {
            return BoardStatus.winnerPlayerTwo;
        }

    }

    public BoardStatus checkWin() {

        //Check rows
        for (int row = 0; row < this.rows; row++) {
            List<BoardType> sequence = new ArrayList<BoardType>();
            for (int column = 0; column < this.columns; column++) {
                sequence.add(board[row][column]);
            }
            BoardType win = checkSequence(sequence);
            if (win != BoardType.empty) {
                return toBoardStatus(win);
            }
        }

        //Check columns
        for (int column = 0; column < this.columns; column++) {
            List<BoardType> sequence = new ArrayList<BoardType>();
            for (int row = 0; row < this.rows; row++) {
                sequence.add(board[row][column]);
            }
            BoardType win = checkSequence(sequence);
            if (win != BoardType.empty) {
                return toBoardStatus(win);
            }
        }

        //check diagonals, direction down - \
        for (int row = 0; row < this.rows; row++) {
            List<BoardType> sequence = new ArrayList<BoardType>();
            for (int index = 0; index < this.rows; index++) {
                if (index + row < this.rows && index < this.columns) {
                    sequence.add(board[index + row][index]);
                }
            }
            BoardType win = checkSequence(sequence);
            if (win != BoardType.empty) {
                return toBoardStatus(win);
            }
            if (sequence.size() == 4) {
                break;
            }
        }

        //check diagonals, direction up - \
        for (int column = 0; column < this.columns; column++) {
            List<BoardType> sequence = new ArrayList<BoardType>();
            for (int index = 0; index < this.columns; index++) {
                if (index < this.rows && index + column < this.columns) {
                    sequence.add(board[index][index + column]);
                }
            }
            BoardType win = checkSequence(sequence);
            if (win != BoardType.empty) {
                return toBoardStatus(win);
            }
            if (sequence.size() == 4) {
                break;
            }
        }

        return BoardStatus.notEnded;
    }

    private BoardType checkSequence(List<BoardType> sequence) {
        if (sequence.size() < 4) {
            return BoardType.empty;
        }
        int startIndex = 0;
        int endIndex = 3;
        while (Math.abs(endIndex - startIndex) == 3) {
            BoardType win = checkFour(sequence.get(startIndex), sequence.get(startIndex + 1),
                                      sequence.get(startIndex + 2), sequence.get(startIndex + 3));
            if (win != BoardType.empty) {
                return win;
            }
            startIndex = endIndex;
            endIndex = Math.min(endIndex + 3, sequence.size());
        }
        return BoardType.empty;
    }

    private BoardType checkFour(BoardType one, BoardType two,
                                BoardType three, BoardType four) {
        if (one == BoardType.empty || two == BoardType.empty
                || three == BoardType.empty || four == BoardType.empty) {
            return BoardType.empty;
        }

        if (one == two && two == three && three == four) {
            return one;
        }
        return BoardType.empty;
    }

    @Override
    public String getHashCode() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < rows * columns; i++) {
            result.append(board[i / columns][i % columns]).append(",");
        }
        return result.toString();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (board[row][column] == BoardType.playerOne) {
                    result.append("O|");
                } else {
                    if (board[row][column] == BoardType.playerTwo) {
                        result.append("X|");
                    } else {
                        result.append("-|");
                    }
                }
            }
            result.append("\n");
        }
        result.append("\n");
        for (int i = 1; i < columns; i++) {
            result.append(i).append(" ");

        }
        if (columns == 10) {
            result.append("0 ");
            result.append("\n");
        }

        return result.toString();
    }
}
