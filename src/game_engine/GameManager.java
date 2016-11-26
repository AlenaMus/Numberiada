package game_engine;

import game_interface.UserInterface;
import game_objects.*;
import game_objects.Board;
import game_objects.Marker;
import game_objects.Player;
import game_objects.Point;
import game_objects.Square;
import jaxb.schema.generated.*;
import org.xml.sax.SAXException;

import javax.jws.soap.SOAPBinding;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.awt.*;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GameManager {

    public static final int LoadGame = 1;
    public static final int StartGame = 2;
    public static final int ExitGame = 3;


    protected boolean isLoadedGame = false;
    protected Player currentPlayer;
    protected String gameFile= " ";
    protected int numOfPlayers;
    protected eGameType gameType;
    protected boolean isEndOfGame = false;
    protected Board gameBoard;
    protected Marker marker;
    private Player rowPlayer;
    private Player colPlayer;

   public GameManager()
   {
      startGame();
   }

   public void startGame()
   {
       UserInterface.PrintFirstMenu();
       int userChoise = UserInterface.GetUserInput(1,3);
       switch (userChoise)
       {
           case LoadGame:  LoadGameFromXmlAndValidate();
                           isLoadedGame = true;
                           break;
           case StartGame: if (!isLoadedGame) /*****Change it */
                                initGame();
                             else
                                 UserInterface.PrintUserMessage("Cannot start game. You need to load file game first!");
                                 break;
           case ExitGame: exitGame();
               break;
       }

   }



   private void setPlayers()
   {
       rowPlayer = new Player(ePlayerType.HUMAN,"Alona", 1,eTurn.ROW);
       UserInterface.PrintPlayerSubMenu();
       int playerChoice = UserInterface.GetUserInput(1,2);
       switch (playerChoice)
       {
           case 1: colPlayer= new Player(ePlayerType.HUMAN,"Or",2,eTurn.COL);
               break;
           case 2: colPlayer = new Player(ePlayerType.COMPUTER,"Computer",2,eTurn.COL);
               break;
       }

   }


    private void initGame()
    {
        setPlayers();
        if(setGameBoard())
        {
            UserInterface.PrintBoard(gameBoard.toString());

        }
        else
        {
            UserInterface.PrintValidationErrors();
        }

        //  gameLoop();
    }

    private boolean setGameBoard() // (eBoardType type, int boardSize, BoardRange range)
    {
        boolean isFilledBoard = false;

        BoardRange range = new BoardRange(-5,10);
        gameBoard = new Board(9,range,eBoardType.RANDOM);
        isFilledBoard = gameBoard.FillRandomBoard();

        return isFilledBoard;
    }

    private void gameLoop()
    {
        int chosenSquare;
        int squareValue;
        Point squareLocation = null;
        currentPlayer = rowPlayer;
        UserInterface.PrintUserMessage("Lets Start the Game ...");
        UserInterface.PrintSecondMenu();


        while(!isGameOver(marker.getMarkerLocation()))
        {
              switch (currentPlayer.getTurn())
            {
                case ROW: chosenSquare=UserInterface.GetUserMove(marker.getMarkerLocation(),eTurn.ROW,gameBoard.GetBoardSize());
                             squareLocation = new Point(chosenSquare,marker.getMarkerLocation().getCol());
                             break;

                case COL: chosenSquare=UserInterface.GetUserMove(marker.getMarkerLocation(),eTurn.COL,gameBoard.GetBoardSize());
                          squareLocation = new Point(marker.getMarkerLocation().getRow(),chosenSquare);
                           break;

            }

            //squareValue=updateBoard(squareLocation); //update 2 squares
           // updateUserData(squareValue); //update score and moves
            marker.setMarkerLocation(squareLocation);
            UserInterface.PrintBoard(gameBoard.toString());
            UserInterface.PrintSecondMenu();
            if(currentPlayer.equals(rowPlayer))
            {
                currentPlayer = colPlayer;
            }
            else
            {
                currentPlayer = rowPlayer;
            }

        }


    }


//    private void updateUserData(int squareValue) // in Player?
//    {
//
//    }
//
//    private int updateBoard(Point squareLocation) //implement in Board
//    {
//
//    }


    private boolean isGameOver(Point markerLocation)
    {
        boolean isGameOver = false;

        return isGameOver;
    }

 private void exitGame()
 {

 }

    public void SetBoard(eBoardType boardType , int boardSize, BoardRange range)
    {
        gameBoard = new Board(boardSize,range,boardType);

        switch (boardType) {
            case EXPLICIT: gameBoard.FillExplicitBoard();
                break;
            case RANDOM: gameBoard.FillRandomBoard();
                break;
        }
    }



    public void LoadGameFromXmlAndValidate() {
        boolean isValidFile = false, isValidXML = false;
        String fileName = "";
        File gameFile;
        GameDescriptor loadedGame = null;

        while (!isValidXML) {
            while (!isValidFile) {
                fileName = UserInterface.getFileName();
                gameFile = new File(fileName);
                if (!gameFile.exists()) {
                    UserInterface.PrintError("Selected save file does not exist");
                } else {
                    isValidFile = true;
                }
            }
            loadedGame = loadGameFromFile(fileName);
            isValidXML = checkXMLData(loadedGame);
            if (!isValidXML)
                UserInterface.PrintError("Invalid XML File, returning to file name entry");
        }

        loadDataFromJaxbToGame(loadedGame);
    }

    private boolean checkXMLData(GameDescriptor loadedGame)
    {
        int index;
        boolean isValidXMLData = true;

        String gameType = loadedGame.getGameType();
        if(!(eGameType.valueOf(gameType).equals(eGameType.BASIC)
                ||eGameType.valueOf(gameType).equals(eGameType.ADVANCED)
                ||eGameType.valueOf(gameType).equals(eGameType.ADVANCED_DYNAMIC )))
        {
            isValidXMLData = false;
        }
        else // game type ok
        {
//            jaxb.schema.generated.Board loadedBoard = loadedGame.getBoard();
//            if(loadedBoard) //check size & structure of board
//            {
//
//            }
//            else // board ok -- check players num
//            {
//
//            }
        }
        jaxb.schema.generated.Players loadedPlayers = loadedGame.getPlayers();

//        boolean currentPlayerExists = false;
//
//        if (loadedGame.getCurrentPlayer().trim().equals(""))
//            isValidXMLData = false; // current player name is empty or whitespaces
//
//        if (loadedBoard.getCell().isEmpty())
//            isValidXMLData = false;

        return isValidXMLData;

    }

    private void loadDataFromJaxbToGame(GameDescriptor loadedGame) {

        //String playerColor;
        String playerName;
        String gameType = loadedGame.getGameType();


        jaxb.schema.generated.Board loadedBoard = loadedGame.getBoard();


        Players loadedPlayers = loadedGame.getPlayers();
    }



//        for (int i = 0; i < loadedPlayers.getPlayer().size(); i++) {
//            playerName = loadedPlayers.getPlayer().get(i).getName();
//            switch (loadedPlayers.getPlayer().get(i).getType()) {
//                case HUMAN:
//                    isHuman = true;
////                    gameLogic.getPlayersList().add(new Player(playerName,true,loadedPlayers.getPlayer().get(index).));
////                    game.addPlayerToTheGame(Player.PlayerType.HUMAN, playerName);
//                    break;
//                case COMPUTER:
//                    isHuman = false;
////                    game.addPlayerToTheGame(Player.PlayerType.COMPUTER, playerName);
//                    break;
//
    //   }
    // for (int j = 0; j < loadedPlayers.getPlayer().get(i).getColorDef().size(); j++) {
//                playerColor=XmlColorToGameColor(loadedPlayers.getPlayer().get(j).getColorDef().get(j).getColor());
    //          gameLogic.getPlayersList().add(new Player(playerName,isHuman,playerColor));
    //  }




    private GameDescriptor loadGameFromFile(String fileName)
    {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File("xml_resources\\Numberiada.xsd"));
            JAXBContext jaxbContext = JAXBContext.newInstance(GameDescriptor.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            unmarshaller.setSchema(schema);
            GameDescriptor JaxbGame;
            JaxbGame = (GameDescriptor) unmarshaller.unmarshal(new File(fileName));
            return JaxbGame;
        }
        catch (JAXBException e) {
            Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
        catch (SAXException e) {
            Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

}