package game_interface;

import java.io.File;

/**
 * Created by Alona on 11/7/2016.
 */
public final class UserInterface {


    private UserInterface(){}

//    public static Point GetUserMove()
//    {
//        //Point loc = new Point()
//       // return loc;
//    }

    public static void PrintMenu()
    {

    }

    public static void PrintBoard()
    {

    }

    public static String GetUserChoice()
    {
        return "choice";
    }

    public static void PrintWinner()
    {

    }

    public static String GetUserInput()
    {
        String userInput = " ";
        return userInput;

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
        String fileName;
        String filePath=" ";
        String fullFileName;
        boolean isValidFolder = false;

        while (!isValidFolder)
        {
            PrintUserMessage("Please enter the path of the folder");
            filePath = UserInterface.GetUserInput();
            File f = new File(filePath);
            if (f.exists() && f.isDirectory())
                isValidFolder = true;
            else
               PrintUserMessage("Invalid folder, please re-enter the folder path");
        }

        PrintUserMessage("Please enter file name");
        fileName = UserInterface.GetUserInput();

        fullFileName = filePath + "\\" + fileName;
        return fullFileName;

    }


}
