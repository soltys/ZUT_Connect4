/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.zut.edu.wsdi.connect4;

/**
 *
 * @author Soltys
 */
public abstract class  Player
{
    BoardType playerType;
    public abstract int getMove(Connect4State state);
    public void setPlayerType(BoardType type){
        this.playerType = type;
    }

    public BoardType getPlayerType() {
        return playerType;
    }
    
}