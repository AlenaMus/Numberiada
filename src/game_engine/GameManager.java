package game_engine;

import game_interface.UserInterface;
import game_objects.*;
import jaxb.schema.generated.GameDescriptor;
import jaxb.schema.generated.Players;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

//import javax.xml.bind.Marshaller;


public class GameManager {

    public static final int LOAD_GAME = 1;
    public static final int START_GAME = 2;
    public static final int EXIT_GAME = 3;

    public static final int SHOW_BOARD_AND_CURRENT_PLAYER = 1;
    public static final int MAKE_A_MOVE = 2;
    public static final int SHOW_STATISTICS = 3;
    public static final int LEAVE_GAME = 4;
    public static final int BAD_SQUARE = 100;


    protected boolean isLoadedGame = false;
    protected Player currentPlayer;
    protected String gameFile = " ";
    protected int numOfPlayers;
    protected eGameType gameType;
    protected boolean isEndOfGame = false;
    protected Board gameBoard;
    private Player rowPlayer;
    private Player colPlayer;
    private double totalTime;
    private final long StartTime = System.currentTimeMillis();
    private long endTime;


    public GameManager() {
        startGame();
    }

    public long TotalGameTime()
    {
       long gameTime = System.currentTimeMillis() - StartTime;
        this.totalTime = gameTime / 1000.0;
        return gameTime;
    }

    public void startGame() {
        UserInterface.PrintFirstMenu();
        int userChoise = UserInterface.GetUserInput(1, 3);
        switch (userChoise) {
            case LOAD_GAME:
                //LoadGameFromXmlAndValidate();
                isLoadedGame = true;
                break;
            case START_GAME:
                if (!isLoadedGame) /*****Change it */
                    initGame();
                else
                    UserInterface.PrintUserMessage("Cannot start game. You need to load game file first!");
                break;
            case EXIT_GAME:
                UserInterface.exitGameFromMainMenu();
                break;
        }

    }

    private void setEndOfGame()
    {
        if(isEndOfGame)
        {
            this.endTime = System.currentTimeMillis();
        }


    }



   private void setPlayers()
   {
       UserInterface.PrintPlayerSubMenu();
       int playerChoice = UserInterface.GetUserInput(1,3);
       switch (playerChoice)
       {
           case 1:   rowPlayer = new Player(ePlayerType.HUMAN,"Alona", 1,eTurn.ROW);
                     colPlayer= new Player(ePlayerType.HUMAN,"Or",2,eTurn.COL);
               break;
           case 2:   rowPlayer = new Player(ePlayerType.HUMAN,"Alona", 1,eTurn.ROW);
                     colPlayer = new Player(ePlayerType.COMPUTER,"Computer",2,eTurn.COL);
               break;
           case 3:   rowPlayer = new Player(ePlayerType.COMPUTER,"Computer 1", 1,eTurn.ROW);
                     colPlayer = new Player(ePlayerType.COMPUTER,"Computer 2",2,eTurn.COL);
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

          gameLoop();
    }

    private boolean setGameBoard() // (eBoardType type, int boardSize, BoardRange range)
    {
        boolean isFilledBoard = false;

        BoardRange range = new BoardRange(1,7);
        gameBoard = new Board(5,range,eBoardType.RANDOM);
        isFilledBoard = gameBoard.FillRandomBoard();

        return isFilledBoard;
    }

    private void gameLoop() {

        int option;

        currentPlayer = rowPlayer;
        UserInterface.PrintUserMessage("Lets Start the Game ...\n Choose an option from the menu below :");
        //UserInterface.PrintSecondMenu();
        // gameBoard.setMarker(5,5);
        while (!isEndOfGame) {
            if (currentPlayer.getPlayerType() == ePlayerType.COMPUTER)
            {
                makeMove(currentPlayer.getTurn());
                switchPlayer();
            }
            else { //HUMAN TURN
                UserInterface.PrintSecondMenu();
                option = UserInterface.GetUserInput(SHOW_BOARD_AND_CURRENT_PLAYER, LEAVE_GAME);

                switch (option) {
                    case SHOW_BOARD_AND_CURRENT_PLAYER:
                        UserInterface.PrintBoard(gameBoard.toString());
                        UserInterface.PrintCurrentPlayer(currentPlayer.getTurn());
                        break;
                    case MAKE_A_MOVE:
                        makeMove(currentPlayer.getTurn());
                        switchPlayer();
                        break;
                    case SHOW_STATISTICS:
                        UserInterface.ShowStatistics(rowPlayer.getNumOfMoves() + colPlayer.getNumOfMoves(), TotalGameTime(), rowPlayer.getScore(), colPlayer.getScore());
                        break;
                    case LEAVE_GAME:
                        exitGame();
                        break;

                }
            }
        }

        exitGame();
    }

    private int makeComputerMove()
    {
        int chosenSquare = GameLogic.ComputerMove(gameBoard.GetBoardSize());
        return chosenSquare;
    }

    private void makeMove(eTurn playerTurn)
    {
        int chosenSquare;
        int squareValue = BAD_SQUARE ;
        Point squareLocation = null;
        boolean badInput = true;

        while (badInput) {
            switch (currentPlayer.getTurn()) {
                case ROW:
                    if (rowPlayer.getPlayerType() == ePlayerType.HUMAN) {
                        chosenSquare = UserInterface.GetUserMove(gameBoard.getMarker().getMarkerLocation(), eTurn.ROW, gameBoard.GetBoardSize(), gameBoard.toString());
                        squareLocation = new Point(gameBoard.getMarker().getMarkerLocation().getRow(), chosenSquare);
                        break;
                    }
                    else // COMPUTER
                    {
                        chosenSquare = makeComputerMove();
                        squareLocation = new Point(gameBoard.getMarker().getMarkerLocation().getRow(), chosenSquare);
                        break;
                    }
                case COL:
                    if (colPlayer.getPlayerType() == ePlayerType.HUMAN) {
                        chosenSquare = UserInterface.GetUserMove(gameBoard.getMarker().getMarkerLocation(), eTurn.COL, gameBoard.GetBoardSize(), gameBoard.toString());
                        squareLocation = new Point(chosenSquare, gameBoard.getMarker().getMarkerLocation().getCol());
                        break;
                    }
                    else // COMPUTER
                    {
                        chosenSquare = makeComputerMove();
                        squareLocation = new Point(chosenSquare, gameBoard.getMarker().getMarkerLocation().getCol());
                        break;
                    }
            }

            squareValue = updateBoard(squareLocation); //update 2 squares
            if (squareValue != BAD_SQUARE)
                badInput = false;
            else
                if (currentPlayer.getPlayerType() == ePlayerType.HUMAN)
                    UserInterface.PrintUserMessage("You choose invalid square! you can't select empty squares/marker square.choose another one..!");

        }
        if (currentPlayer.getPlayerType() == ePlayerType.COMPUTER)
        {
            UserInterface.PrintUserMessage("computer play his turn...");
        }
        updateUserData(squareValue); //update score and moves
        gameBoard.getMarker().setMarkerLocation(squareLocation.getRow(),squareLocation.getCol());
        UserInterface.PrintBoard(gameBoard.toString());
    }

    private void switchPlayer()
    {
        if (currentPlayer.equals(rowPlayer)) {
            if (gameBoard.isColPlayerHaveMoves(gameBoard.getMarker().getMarkerLocation()))
                currentPlayer = colPlayer;
            else {
                isEndOfGame = true;
                UserInterface.PrintUserMessage("Col player have no moves ! GAME OVER");
            }
        } else //(currentPlayer.equals(colPlayer))
        {
            if (gameBoard.isRawPlayerHaveMoves(gameBoard.getMarker().getMarkerLocation()))
                currentPlayer = rowPlayer;
            else {
                isEndOfGame = true;
                UserInterface.PrintUserMessage("Raw player have no moves ! GAME OVER");
            }
        }
    }



    private int updateBoard(Point squareLocation) //implement in Board - returns updated value of row/column
    {
        int squareValue = gameBoard.updateBoard(squareLocation);
        return squareValue;
    }


    private void updateUserData(int squareValue) // in Player?
    {
        currentPlayer.setNumOfMoves(currentPlayer.getNumOfMoves()+1); //maybe do totalmoves var in gameManager
        currentPlayer.setScore(currentPlayer.getScore() + squareValue);
    }

    private boolean isGameOver(Point markerLocation)
    {
        boolean isGameOver = false;
        isGameOver = gameBoard.isGameOver(markerLocation);
        //GameLogic.IsGameOver(gameBoard,markerLocation);
        return isGameOver;
    }


   private void exitGame()
   {
       int rowPlayerScore = rowPlayer.getScore();
       int ColPlayerScore = colPlayer.getScore();
       if (rowPlayerScore > ColPlayerScore)
           UserInterface.PrintWinner(rowPlayer.getName());
       else if (ColPlayerScore > rowPlayerScore )
           UserInterface.PrintWinner(colPlayer.getName());
           else //(ColPlayerScore  == rowPlayerScore )
           UserInterface.PrintWinner("TIE");

       UserInterface.PrintBoard(gameBoard.toString());
       UserInterface.ShowStatistics(rowPlayer.getNumOfMoves()+colPlayer.getNumOfMoves(),TotalGameTime(),rowPlayer.getScore(),colPlayer.getScore());

       //startGame();//go back to start game here
   }

    public void SetBoard(eBoardType boardType,int boardSize, BoardRange range)
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
            jaxb.schema.generated.Board loadedBoard = loadedGame.getBoard();
            if(loadedBoard!= null) //check size & structure of board
            {

            }
            else // board ok -- check players num
            {

            }
        }
        jaxb.schema.generated.Players loadedPlayers = loadedGame.getPlayers();

        boolean currentPlayerExists = false;

     //   if (loadedGame.getCurrentPlayer().trim().equals(""))
        //    isValidXMLData = false; // current player name is empty or whitespaces

       // if (loadedBoard.getCell().isEmpty())
         //   isValidXMLData = false;

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
