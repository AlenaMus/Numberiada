/**
 * Created by Alona on 11/7/2016.
 */
import java.util.Scanner;

public final class UserInterface {


    private UserInterface(){}

//    public static Point GetUserMove()
//    {
//        //Point loc = new Point()
//       // return loc;
//    }

    public static void PrintFirstMenu()
    {
        System.out.println("Welcome To Numberiada!");
        System.out.println("============================");
        System.out.println("|   MENU   |");
        System.out.println("============================");
        System.out.println("| Options:                 |");
        System.out.println("|        1. Load level       |");
        System.out.println("|        2. Start game        |");
        System.out.println("|        3. Exit game        |");
        System.out.println("============================");
    }

    public static void PrintSecondMenu()
    {
        System.out.println("============================");
        System.out.println("|   MENU     |");
        System.out.println("============================");
        System.out.println("| Options:                 |");
        System.out.println("|        1.Show board and current player       ");
        System.out.println("|        2. Make a move        ");
        System.out.println("|        3. Show statistics        ");
        System.out.println("|        4. Exit game        ");
        System.out.println("============================");
    }

    public static void PrintBoard()
    {

    }

    public static int GetUserChoice(int currentLocation,eTurn turn) //we need to know if the user need to choose raw or col
    {
        int userChoise;
        Scanner userInputScan = new Scanner(System.in);
        if (turn == eTurn.ROW) {
            System.out.println("Please enter number of square in col "+ currentLocation);
        }
        else if (turn == eTurn.COL){
            System.out.println("Please enter number of square in row "+ currentLocation);
        }
        if (userInputScan.hasNextInt()) {
            userChoise = userInputScan.nextInt();
        } else {
            userInputScan.next();   // get the inputted non integer from scanner
            userChoise = 0;
        }

// If the board input is below 1 or greater than Boardsize, prompt for another value
        while (userChoise < 1 || userChoise > 6)//getBoardSize())
        {
            System.out.println("Invalid value! Enter square number (valid values are from 1 to8 " + 6 + ")");//getBoardSize() ) //boardsize);
            if (userInputScan.hasNextInt()) {
                userChoise = userInputScan.nextInt();
            } else {
                userInputScan.next();
            userChoise = 0;
        }
        }
        userInputScan.close();
        return userChoise;
    }

    public static void ShowStatistics(int totalMoves, double gameTime,int Player1score,int player2Score)
    {
        System.out.println("Total moves in the game : " + totalMoves);
        System.out.println("Game time : " + gameTime);
        System.out.println("Player1NAME score : " + Player1score);
        System.out.println("Player2NAME score : " + player2Score);
    }

    public static void PrintWinner()
    {

    }





}
