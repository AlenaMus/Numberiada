package game_objects;

import game_engine.eTurn;

/**
 * Created by Alona on 11/7/2016.
 */
public class Player {

    protected String name;
    protected int id;
    private ePlayerType playerType;
    protected eTurn turn;
    protected int score;
    protected int numOfMoves;
    private int color;

    public Player(ePlayerType playerType,String playerName, int playerId,int color)
    {
        this.name = playerName;
        this.id = playerId;
        this.playerType = playerType;
        //this.turn = playerTurn;
        this.color =color;
        score = 0;
        numOfMoves = 0;

    }

    public Player(eTurn turn,ePlayerType playerType)
    {
        this.turn = turn;
        this.playerType = playerType;
        score = 0;
        numOfMoves = 0;
        name = "";

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ePlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(ePlayerType playerType) {
        this.playerType = playerType;
    }

    public eTurn getTurn() {
        return turn;
    }

    public void setTurn(eTurn turn) {
        this.turn = turn;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score+= score;
    }

    public int getNumOfMoves() {
        return numOfMoves;
    }

    public void setNumOfMoves(int numOfMoves) {
        this.numOfMoves = numOfMoves;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object player) {
        boolean isEqual = false;
        Player newPlayer;

        if(player instanceof Player)
        {
            newPlayer = (Player)player;
            if(newPlayer.getName() == this.name || newPlayer.getId() == this.id || newPlayer.color == this.color)
            {
                isEqual = true;
            }

        }

        return isEqual;
    }

    public boolean checkPlayerTurn(Player currentPlayer) //only for basic game use //
    {
       boolean isCurrentPlayer = false;

        if(currentPlayer != null)
        {
            if(this.turn == currentPlayer.turn)
            {
                isCurrentPlayer = true;
            }
        }

        return isCurrentPlayer;

    }
}
