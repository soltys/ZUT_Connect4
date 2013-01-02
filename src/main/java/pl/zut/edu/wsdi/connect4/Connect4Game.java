/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.zut.edu.wsdi.connect4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;

/**
 *
 * @author Soltys
 */
public class Connect4Game {

    public static void main(String args[]) {
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in), 1);
        String resp = "";
        System.out.println("Podaj ilosc wierszy: ");
        try {
            resp = keyboard.readLine();
        } catch (Exception e) {
            System.out.println("Blad czytania z klawiatury");
        }


        int rows = Integer.parseInt(resp);
        System.out.println("Podaj ilosc kolumn: ");
        try {
            resp = keyboard.readLine();
        } catch (Exception e) {
            System.out.println("Blad czytania z klawiatury");
        }

        int cols = Integer.parseInt(resp);
        System.out.println("zaczyna gre g/k");
        try {
            resp = keyboard.readLine();
        } catch (Exception e) {
            System.out.println("Blad czytania z klawiatury!");
        }
        Players player;
        //g - gracz(player1), k- komputer(player2)
        if (resp.equals("k")) {
            player = Players.playerTwo;
        } else {
            player = Players.playerOne;
        }

        Connect4State game = new Connect4State(rows, cols);

        if (player == Players.playerOne) {
            game.playersSwitch();
            game.playerMove();
            game.playersSwitch();
        }



        do {
            Computer computer = new Computer();
            int computerMove = computer.getMove(game);
            game.makeMove(computerMove);

            game.playersSwitch();
            if (game.checkWin() != WinStatus.notEnded) {
                break;
            }
            game.playerMove();
            game.playersSwitch();

        } while (game.checkWin() == WinStatus.notEnded);

        System.out.println(game);
        if (game.checkWin() == WinStatus.winnerComputer) {
            System.out.println("Wygrał komputer");
        }
        else
        {
            System.out.println("Wygrał gracz ");
        }


    }
}
