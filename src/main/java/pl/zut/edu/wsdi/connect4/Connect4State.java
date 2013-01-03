package pl.zut.edu.wsdi.connect4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.*;
import java.util.ArrayList;
import java.util.List;

import pl.zut.edu.wsdi.klesk.math.search.StateImpl;

public final class Connect4State extends StateImpl {

    private Players[][] board = null;
    private int rows;
    private int columns;
    Players turn;

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public Players getTurn() {
        return turn;
    }

    public void setTurn(Players turn) {
        this.turn = turn;
    }

    public Connect4State(int rows, int cols)//konstruktory
    {
        super(null);
        this.rows = rows;
        this.columns = cols;
        board = new Players[rows][cols];
        turn = Players.playerComputer;
        for (int i = 0; i < rows * cols; i++) {
            board[i / cols][i % cols] = Players.empty;
        }

        computeHeuristicGrade();
    }

    public Connect4State(Connect4State parent) {
        super(parent);

        rows = parent.rows;
        columns = parent.columns;
        board = new Players[rows][columns];
        turn = parent.turn;
        for (int i = 0; i < rows * columns; i++) {
            board[i / columns][i % columns] = parent.getBoardValue(i / columns, i % columns);
        }

        computeHeuristicGrade();
    }

    public void playersSwitch() {
        turn = (turn == Players.playerComputer ? Players.playerHuman : Players.playerComputer);
    }

    public boolean makeMove(int x)//dodanie w danej kolumnie w odpowiednim wierszu
    {
        for (int i = 0; i < rows; i++) {
            if (board[i][x] == Players.empty) {
                board[i][x] = turn;
                return true;
            }
        }
        return false;
    }

    public void playerMove() {
        System.out.print(this);
        Human human = new Human();
        int move = human.getMove(this);

        makeMove(move);
    }

    public Players getBoardValue(int i, int j) {
        return board[i][j];
    }

    private WinStatus toBoardStatus(Players Players) {
        if (Players == Players.empty) {
            throw new RuntimeException("toBoardStatus do not handle Players.empty");
        }
        if (Players == Players.playerComputer) {
            return WinStatus.winnerComputer;
        } else {
            return WinStatus.winnerHumanPlayer;
        }

    }

    private void safeSequenceAdd(List<Players> sequence, int row, int column) {
        if (row >= 0 && column >= 0 && row < this.rows && column < this.columns) {
            sequence.add(board[row][column]);
        }
    }

    public WinStatus checkWin() {

        //Check top row
        for (int column = 0; column < this.columns; column++) {
            if (board[this.rows - 1][column] != Players.empty) {
                return toBoardStatus(board[this.rows - 1][column]);
            }
        }

        //Check rows
        for (int row = 0; row < this.rows; row++) {
            List<Players> sequence = new ArrayList<Players>();
            for (int column = 0; column < this.columns; column++) {
                safeSequenceAdd(sequence, row, column);
            }
            Players win = checkSequence(sequence);
            if (win != Players.empty) {
                return toBoardStatus(win);
            }
        }

        //Check columns
        for (int column = 0; column < this.columns; column++) {
            List<Players> sequence = new ArrayList<Players>();
            for (int row = 0; row < this.rows; row++) {
                safeSequenceAdd(sequence, row, column);
            }
            Players win = checkSequence(sequence);
            if (win != Players.empty) {
                return toBoardStatus(win);
            }
        }

        //check diagonals, direction down - \
        for (int row = 0; row < this.rows; row++) {
            List<Players> sequence = new ArrayList<Players>();
            for (int column = 0; column < this.columns; column++) {
                safeSequenceAdd(sequence, column + row, column);

            }
            Players win = checkSequence(sequence);
            if (win != Players.empty) {
                return toBoardStatus(win);
            }
            if (sequence.size() == 4) {
                break;
            }
        }

        //check diagonals, direction up - \
        for (int column = 0; column < this.columns; column++) {
            List<Players> sequence = new ArrayList<Players>();
            for (int row = 0; row < this.rows; row++) {
                safeSequenceAdd(sequence, row, row + column);
            }
            Players win = checkSequence(sequence);
            if (win != Players.empty) {
                return toBoardStatus(win);
            }
            if (sequence.size() == 4) {
                break;
            }
        }

        //check diagonals, direction up /
        for (int column = this.columns - 1; column >= 0; column--) {
            List<Players> sequence = new ArrayList<Players>();
            for (int row = 0; row < this.rows; row++) {
                safeSequenceAdd(sequence, row, column - row);
            }
            Players win = checkSequence(sequence);
            if (win != Players.empty) {
                return toBoardStatus(win);
            }
            if (sequence.size() == 4) {
                break;
            }
        }
        //check diagonals, direction down /
        for (int row = 0; row < this.rows; row++) {
            List<Players> sequence = new ArrayList<Players>();
            for (int column = this.columns - 1; column >= 0; column--) {
                safeSequenceAdd(sequence, row + (this.columns - column), column);
            }
            Players win = checkSequence(sequence);
            if (win != Players.empty) {
                return toBoardStatus(win);
            }
            if (sequence.size() == 4) {
                break;
            }
        }
        return WinStatus.notEnded;
    }

    private Players checkSequence(List<Players> sequence) {
        if (sequence.size() < 4) {
            return Players.empty;
        }

        for (int index = 0; index < sequence.size() - 3; index++) {
            Players win = checkFour(sequence.get(index), sequence.get(index + 1),
                    sequence.get(index + 2), sequence.get(index + 3));
            if (win != Players.empty) {
                return win;
            }
        }
        return Players.empty;
    }

    private Players checkFour(Players one, Players two,
            Players three, Players four) {
        if (one == Players.empty || two == Players.empty
                || three == Players.empty || four == Players.empty) {
            return Players.empty;
        }

        if (one == two && two == three && three == four) {
            return one;
        }
        return Players.empty;
    }

    @Override
    public double computeHeuristicGrade() {
        WinStatus winStatus = checkWin();
        if (winStatus == WinStatus.notEnded) {
            double h = 0;
            double middle = this.columns / 2;
            for (int row = 0; row < this.rows; row++) {
                for (int column = 0; column < this.columns; column++) {
                    if (board[row][column] == Players.playerComputer) {
                        double distance = Math.abs(column - middle);
                        if (distance <= 1.5) {
                            h += 10;
                        }
                        else if (distance <= 2.5) {
                            h += 5;
                        }
                        else if (distance <= 3.5) {
                            h += 1;
                        }
                    }
                }
            }
            setH(h);
            return h;
        }


        double h1;
        double h2;
        h1 = winStatus == WinStatus.winnerComputer ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
        h2 = winStatus == WinStatus.winnerHumanPlayer ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;

        if (h1 == Double.NEGATIVE_INFINITY || h1 == Double.POSITIVE_INFINITY) {
            this.h = h1;
        } else {
            this.h = h2;
        }
        setH(this.h);
        return this.h;
    }

    @Override
    public boolean isAdmissible() {
        return true;
    }

    @Override
    public String getHashCode() {
        String result = "";
        for (int i = 0; i < rows * columns; i++) {
            result += board[i / columns][i % columns] + ",";
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(rows * columns + columns);
        for (int i = rows - 1; i >= 0; i--) {
            for (int j = 0; j < columns; j++) {
                if (board[i][j] == Players.playerComputer) {
                    result.append("O ");
                } else {
                    if (board[i][j] == Players.playerHuman) {
                        result.append("X ");
                    } else {
                        result.append("_ ");
                    }
                }
            }
            result.append("\n");
        }

        for (int i = 1; i <= columns && i != 10; i++) {
            result.append(i).append(" ");
        }
        if (columns == 10) {
            result.append("0 ");
        }
        return result.toString();
    }
}
