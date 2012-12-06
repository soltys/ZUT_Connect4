/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.zut.edu.wsdi.connect4;

/**
 *
 * @author Soltys
 */
public class Connect4Game {

    private Connect4State currentState;
    private final IPlayer playerOne;
    private final IPlayer playerTwo;
    private IPlayer currentPlayer;
    private BoardType turn;

    public Connect4Game(int rows, int columns, IPlayer playerOne, IPlayer playerTwo) {

        currentState = new Connect4State(rows, columns);
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        currentPlayer = playerOne;
    }

    private void switchPlayers() {
        if (currentPlayer == this.playerOne) {
            currentPlayer = playerTwo;
        } else {
            currentPlayer = playerOne;
        }
    }

    public void playGame() {
        BoardStatus gameStatus = BoardStatus.notEnded;
        while (gameStatus == BoardStatus.notEnded) {
            System.out.println(currentState);
            currentPlayer.getMove(currentState);
            gameStatus = currentState.checkWin();
            switchPlayers();
        }
        printResult(gameStatus);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Connect4Game game = new Connect4Game(7, 7, new Human(), new Computer());
        game.playGame();
    }

    private void printResult(BoardStatus gameStatus) {
        System.out.println();
        System.out.print("Result:");
        if (gameStatus == BoardStatus.winnerPlayerOne) {
            System.out.print("Player One Wins");
        } else {
            if (gameStatus == BoardStatus.winnerPlayerTwo) {
                System.out.print("Player Two Wins");
            }
        }
    }
}
