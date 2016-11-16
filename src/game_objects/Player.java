package game_objects;

import game_objects.ePlayerType;
import game_objects.eTurn;

/**
 * Created by Alona on 11/7/2016.
 */
public class Player {

    private String name;
    private int id;
    private ePlayerType playerType;
    private eTurn turn;
    private int score;
    private int numOfMoves;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




}
