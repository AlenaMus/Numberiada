package game_engine;
import game_objects.Board;
import game_objects.Player;
import game_objects.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public  class GameLogic
{
    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 6;

    private double totalTime;
    private final long StartTime = System.currentTimeMillis();
    private long endTime;
    private boolean isEndOfGame = false;
    private List<Player> players = new ArrayList<Player>();
    private int numOfPlayers ;

    public int getNumOfPlayers ()
    {
        return numOfPlayers;
    }
    public void setNumOfPlayers(int num)
    {
        numOfPlayers = num;
    }

    public long TotalGameTime() {
        long gameTime = System.currentTimeMillis() - StartTime;
        this.totalTime = gameTime / 1000.0;
        return gameTime;
    }


    private void setEndOfGame() {
        if (isEndOfGame) {
            this.endTime = System.currentTimeMillis();
        }

    }

    public static int ComputerMove(int boardSize) {
        return (ThreadLocalRandom.current().nextInt(1, boardSize + 1));
    }

    public boolean isGameOver(Point markerLocation) {
        boolean isGameOver = false;
        //isGameOver = gameBoard.isGameOver(markerLocation);
        return isGameOver;
    }



    public List<Player> getPlayers()
    {
        return players;
    }






}



