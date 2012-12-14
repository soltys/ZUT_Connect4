/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.zut.edu.wsdi.connect4;

import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Soltys
 */
class Computer extends Player {

    static public BoardType maxPlayer;
    static public BoardType minPlayer;

    @Override
    public void setPlayerType(BoardType type) {
        super.setPlayerType(type);

        maxPlayer = type;
        if (maxPlayer == BoardType.playerOne) {
            minPlayer = BoardType.playerTwo;
        } else {
            minPlayer = BoardType.playerOne;
        }
    }

    public Computer() {
    }

    @Override
    public int getMove(Connect4State state) {
        Connect4Searcher searcher = new Connect4Searcher(state, true, 2.5);//z glebokoscia przeszukiwania
        searcher.doSearch();
        double tmax = Double.NEGATIVE_INFINITY;

        int move = 0;
        Map<String, Double> map = searcher.getMovesMiniMaxes();
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            if (entry.getValue() > tmax) {
                move = Integer.valueOf(entry.getKey());
                tmax = entry.getValue();
            }
        }
        return move;
    }
}
