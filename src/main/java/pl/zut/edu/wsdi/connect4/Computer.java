/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.zut.edu.wsdi.connect4;

import java.util.Iterator;

/**
 *
 * @author Soltys
 */
public class Computer extends Player {

    @Override
    public int getMove(Connect4State state) {
        Connect4Searcher searcher = new Connect4Searcher(state, true, 2.5);//z glebokoscia przeszukiwania
        searcher.doSearch();
        double tmax = Double.NEGATIVE_INFINITY;
        int AI_move = 0;
        Iterator<String> it = searcher.getMovesMiniMaxes().keySet().iterator();

        //System.out.println(searcher.getMovesMiniMaxes());

        while (it.hasNext()) {
            String key = it.next();
            double max = searcher.getMovesMiniMaxes().get(key);
            if (max > tmax) {
                AI_move = Integer.valueOf(key);
                tmax = max;
            }
        }


        return AI_move;
    }
}
