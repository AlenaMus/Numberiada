package game_interface;

import java.io.File;
import java.util.*;
import java.util.stream.Stream;

import game_engine.eTurn;
import game_objects.Board;
import game_objects.Point;

public final class UserInterface {


    private UserInterface(){}

    public static List<String> ValidationErrors = new ArrayList<>();
    private static Scanner userInputScan = new Scanner(System.in);



    public static void PrintValidationErrors()
    {
        for(int i =0;i<ValidationErrors.size();i++)
        {
            System.out.println(ValidationErrors.get(i));
        }
    }

    public static void PrintFirstMenu()
    {
        System.out.println("    Welcome To Numberiada!");
        System.out.println("============================");
        System.out.println("|            MENU           |");
        System.out.println("============================");
        System.out.println("| Options:                  |");
        System.out.println("|        1. Load level      |");
        System.out.println("|        2. Start game      |");
        System.out.println("|        3. Exit game       |");
        System.out.println("============================");
    }

    public static void PrintPlayerSubMenu()
    {

        System.out.println("Choose your partner :");
        System.out.println("=====================");
        System.out.println("| 1. Human Player    |");
        System.out.println("| 2. Computer Player |");
        System.out.println("=====================");

    }


    public static void PrintSecondMenu()
    {
        System.out.println("============================");
        System.out.println("|           MENU           |");
        System.out.println("============================");
        System.out.println("| Options:                 |");
        System.out.println("|        1. Show board and current player ");
        System.out.println("|        2. Make a move        ");
        System.out.println("|        3. Show statistics        ");
        System.out.println("|        4. Exit game        ");
        System.out.println("============================");
    }

    public static void PrintBoard(String gameBoard)
    {
        System.out.print(gameBoard);
    }

    public static void PrintCurrentPlayer(eTurn turn,Point markerLocation)
    {
        if (turn == eTurn.ROW) {
            System.out.println("Row Player ,please enter number of square in row "+ markerLocation.getRow());
        }
        else if (turn == eTurn.COL){
            System.out.println("Column Player,please enter number of square in col "+ markerLocation.getCol());
        }
    }

    public static int GetUserMove(Point markerLocation,eTurn turn,int boardSize) //we need to know if the user need to choose raw or col
    {
        int userMove;
        Scanner userInputScan = new Scanner(System.in);
        PrintCurrentPlayer(turn,markerLocation);

        if (userInputScan.hasNextInt()) {
            userMove = userInputScan.nextInt();
        } else {
            userInputScan.next();   // get the inputted non integer from scanner
            userMove = 0;
        }

// If the board input is below 1 or greater than Boardsize, prompt for another value
        while (userMove < 1 || userMove > boardSize)
        {
            System.out.println("Invalid value! Enter square number (valid values are from 1 to " + boardSize + ")");
            if (userInputScan.hasNextInt()) {
                userMove = userInputScan.nextInt();
            } else {
                userInputScan.next();
                userMove = 0;
            }
        }
        userInputScan.close();
        return userMove;
    }

    public static void ShowStatistics(int totalMoves, double gameTime,int Player1score,int player2Score)
    {
        System.out.println("Total moves in the game : " + totalMoves);
        System.out.println("Game time : " + gameTime);
        System.out.println("Player1NAME score : " + Player1score);
        System.out.println("Player2NAME score : " + player2Score);
    }





    public static int GetUserInput(int firstOption,int lastOption)
    {
        int userChoise;
        String message = String.format("Invalid value! Enter square number (valid options are from %d  to %d)",firstOption,lastOption);

        if (userInputScan.hasNextInt()) {
            userChoise = userInputScan.nextInt();
        } else {
            userInputScan.next();   // get the inputted non integer from scanner
            userChoise = 0;
        }

        while (userChoise < firstOption || userChoise > lastOption)
        {
            System.out.println(" " );
            if (userInputScan.hasNextInt()) {
                userChoise = userInputScan.nextInt();
            } else {
                userInputScan.next();
                userChoise = 0;
            }
        }

        return userChoise;

    }

    public static void PrintWinner()
    {
        userInputScan.close();
    }


    public static void PrintError(String error)
    {
        System.out.println(error);
    }

    public static void PrintUserMessage(String message)
    {
        System.out.println(message);
    }


    public static String getFileName()
    {
        String fileName = " ";
        String filePath=" ";
        String fullFileName = " ";
        boolean isValidFolder = false;

        while (!isValidFolder)
        {
            PrintUserMessage("Please enter the path of the folder");
            filePath = getString();
            File f = new File(filePath);
            if (f.exists() && f.isDirectory())
                isValidFolder = true;
            else
               PrintUserMessage("Invalid folder, please re-enter the folder path");
        }

        PrintUserMessage("Please enter file name");
        fileName = getString();

        fullFileName = filePath + "\\" + fileName;
        return fullFileName;

    }

    public static String getString()
    {
        String str;
        Scanner sc = new Scanner(System.in);
        str= sc.nextLine();
        return str;
    }

}
