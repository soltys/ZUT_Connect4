/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.zut.edu.wsdi.connect4;

import java.util.Iterator;
import java.util.Map.Entry;

/**
 *
 * @author Soltys
 */
public class Computer extends Player {

    @Override
    public int getMove(Connect4State state) {
        Connect4Searcher searcher = new Connect4Searcher(state, true, 2.5);
        searcher.doSearch();
        double tmax = Double.NEGATIVE_INFINITY;
        int AI_move = 0;

        Iterator<String> it = searcher.getMovesMiniMaxes().keySet().iterator();

        for (Entry<String, Double> entry : searcher.getMovesMiniMaxes().entrySet()) {
            String key = entry.getKey();
            Double max = entry.getValue();
            if (max > tmax) {
                AI_move = Integer.valueOf(key);
                tmax = max;
            }
        }


        return AI_move;
    }
}
