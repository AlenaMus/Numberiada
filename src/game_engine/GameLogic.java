package game_engine;
import game_objects.Board;
import game_objects.Point;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Alona on 11/7/2016.
 */
public final class GameLogic {

    private GameLogic(){}

    //private ArrayList<Point> possibleMoves;

    public static int ComputerMove(int boardSize)
    {
        return (ThreadLocalRandom.current().nextInt(1, boardSize + 1));
    }

    public static boolean IsValidMove(Point move,Point MarkerLocation)
    {
       return true;
    }

    public static boolean IsGameOver(Board gameBoard,Point MarkerLocation)
    {
        return true;
    }

//    public static Player FindTheWinner()
//    {
//
//    }







}
