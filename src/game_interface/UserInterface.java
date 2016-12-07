package game_interface;

import game_engine.eTurn;
import game_objects.Point;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        System.out.println("|        ================   |");
        System.out.println("|        O.A 2016 ©         |");
        System.out.println("============================");
    }

    public static void PrintPlayerSubMenu()
    {

        System.out.println("Choose your game partner :");
        System.out.println("=====================");
        System.out.println("| 1. Human Player    |");
        System.out.println("| 2. Computer Player |");
        System.out.println("| 3. Both Players are Computers (automatic game) |");
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

    public static void PrintCurrentPlayer(eTurn turn)
    {
        if (turn == eTurn.ROW) {
            System.out.println("current player is Row Player");
        }
        else if (turn == eTurn.COL){
            System.out.println(" current player is Column Player");
        }
    }

    public static int GetUserMove(Point markerLocation,eTurn turn,int boardSize,String gameBoard) //we need to know if the user need to choose raw or col
    {
        int userMove;
        if (turn == eTurn.ROW) {
            System.out.println("Row Player ,please enter number of square in row "+ markerLocation.getRow());
        }
        else if (turn == eTurn.COL){
            System.out.println("Column Player,please enter number of square in col "+ markerLocation.getCol());
        }
        PrintBoard(gameBoard);
        if (userInputScan.hasNextInt()) {
            userMove = userInputScan.nextInt();
        } else {
            userInputScan.next();   // get the inputted non integer from scanner
            userMove = 0;
        }

// If the board input is below 1 or greater than Boardsize, prompt for another value
        while (userMove < 1 || userMove > boardSize)
        {
            System.out.println("Invalid value! Enter square number again (valid values are from 1 to " + boardSize + ")");
            if (userInputScan.hasNextInt()) {
                userMove = userInputScan.nextInt();
            } else {
                userInputScan.next();
                userMove = 0;
            }
        }
        //userInputScan.close();
        return userMove;
    }

    public static void ShowStatistics(int totalMoves, double gameTime,int RawPlayerscore,int ColumnPlayerScore)
    {
        System.out.println("============================");
        System.out.println("| Game Statistics |");
        System.out.println("============================");
        System.out.println("Total moves in the game : " + totalMoves);
        System.out.println("Game time : " + gameTime);
        System.out.println("Raw player score : " + RawPlayerscore);
        System.out.println("Column player score : " + ColumnPlayerScore);
    }





    public static int GetUserInput(int firstOption,int lastOption)
    {
        int userChoise;
        String message = String.format("Invalid value! (valid options are from %d  to %d)",firstOption,lastOption);

        if (userInputScan.hasNextInt()) {
            userChoise = userInputScan.nextInt();
        } else {
            userInputScan.next();   // get the inputted non integer from scanner
            userChoise = 0;
        }

        while (userChoise < firstOption || userChoise > lastOption)
        {
            System.out.println(message);
            if (userInputScan.hasNextInt()) {
                userChoise = userInputScan.nextInt();
            } else {
                userInputScan.next();
                userChoise = 0;
            }
        }

        return userChoise;

    }

    public static void exitGameFromMainMenu()
    {
        System.out.println("bye bye!!\nO.A 2016 © ");
    }

    public static void PrintWinner(String playerName)
    {
        if (playerName != "TIE") {
            System.out.println("THE WINNER IS " + playerName);
        }
        else
        {
            System.out.println("Unbelievable there is a tie!!! no winner here...");
        }

    }


    public static void PrintError(String error)
    {
        System.out.println(error);
    }

    public static void PrintUserMessage(String message)
    {
        System.out.println(message);
    }



    public static String getXMLfile()
    {
        String filePath = " ";
        boolean isValidFile = false;

        while (!isValidFile) {
            PrintUserMessage("Please enter the path of the file");
            filePath = getString();
            filePath=filePath.replaceAll("\\s","");
            File f = new File(filePath);
            if(f.exists() && !f.isDirectory()) {
                isValidFile = true;
            } else {
                PrintUserMessage("File doesn't exist, please re-enter the folder path");
            }

        }
        return filePath;
    }




    public static String getString()
    {
        String str;
        Scanner sc = new Scanner(System.in);
        str= sc.nextLine();
        return str;
    }

}
