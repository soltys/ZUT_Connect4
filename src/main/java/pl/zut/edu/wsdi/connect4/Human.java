/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.zut.edu.wsdi.connect4;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author Soltys
 */
class Human extends Player {

    public Human() {
    }

    @Override
    public int getMove(Connect4State state) {
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in), 1);
        int column = 0;
        do {
            String move = new String();
            System.out.print("\nRuch gracza: ");
            try {
                move = keyboard.readLine();
            } catch (Exception e) {
                System.out.println("Blad czytania z klawiatury...");
            }
            try {
                column = Integer.parseInt(move);
                if (column == 0) {
                    column = 10;
                }
                if (column<=state.getColumns()) {
                return column - 1;    
                }
                column =-1;
            } catch (Exception e) {
                column = -1;
            }
        } while (column < 0);
        return -1;
    }
}
