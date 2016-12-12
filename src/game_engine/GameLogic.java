package game_engine;
import game_objects.Player;
import game_objects.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GameLogic
{
    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 6;

    private long StartTime;
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

    public void setStartTime(long startTime) {
        StartTime = startTime;
    }

    public String TotalGameTime() {
        long milliseconds  = System.currentTimeMillis() - StartTime;
        int seconds = (int) (milliseconds / 1000) % 60 ;
        int minutes = (int) ((milliseconds / (1000*60)) % 60);
        return(minutes + ":" + seconds);
    }

    /*public String  TotalGameTime() {
        long millis = System.currentTimeMillis() - StartTime;
        return String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))

        );
    }*/

    /*private void setEndOfGame() {
        if (isEndOfGame) {
            this.endTime = System.currentTimeMillis();
        }

    }*/

    public static int ComputerMove(int boardSize) {
        return (ThreadLocalRandom.current().nextInt(1, boardSize + 1));
    }

    public List<Player> getPlayers()
    {
        return players;
    }






}



