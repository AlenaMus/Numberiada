package game_engine;

import game_interface.UserInterface;
import game_objects.*;
import game_objects.Board;
import game_objects.Player;
import game_objects.Square;
import jaxb.schema.generated.*;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//import javax.xml.bind.Marshaller;


public class GameManager {

    public static final int LOAD_GAME = 1;
    public static final int START_GAME = 2;
    public static final int EXIT_GAME = 3;

    public static final int HUMAN_PLAYER = 1;
    public static final int COMPUTER_PLAYER =2;
    public static final int COMPUTERS_GAME =3;


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
    private GameLogic gameLogic;

    List<game_objects.Square> newList = new ArrayList<Square>();

    public GameManager()
    {
        gameLogic = new GameLogic();
        runGame();
    }



    public void startGame() {
        UserInterface.PrintFirstMenu();
        int userChoise = UserInterface.GetUserInput(1, 3);
        isLoadedGame = true;
        switch (userChoise) {
            case LOAD_GAME:
                LoadGameFromXmlAndValidate();
                isLoadedGame = true;
              //  UserInterface.PrintBoard(gameBoard.toString());
                break;
            case START_GAME:
                if (isLoadedGame)
                    initGame();
                else
                    UserInterface.PrintUserMessage("Cannot start game. You need to load game XML file first!");
                break;
            case EXIT_GAME:
                UserInterface.exitGameFromMainMenu();
                break;
        }

    }

    private void runGame()
    {
        isLoadedGame = false;
        UserInterface.PrintFirstMenu();
        int userChoise = UserInterface.GetUserInput(1, 3);

        while(userChoise != EXIT_GAME)
        {
            if(userChoise == LOAD_GAME)
            {
                LoadGameFromXmlAndValidate();
                isLoadedGame = true;
            }
            else if(userChoise == START_GAME)
            {
                if(isLoadedGame)
                {
                    initGame();
                }
                else
                {
                    UserInterface.PrintUserMessage("Cannot start game. You need to load game XML file first!");

                }
            }

            UserInterface.PrintFirstMenu();
            userChoise = UserInterface.GetUserInput(1, 3);
        }

            UserInterface.exitGameFromMainMenu();
    }


    private void initGame()
    {
        gameLogic.setStartTime(System.currentTimeMillis());
        setBasicPlayers();
        gameLoop();
    }



    private void gameLoop() {

        int option;

        currentPlayer = rowPlayer;
        UserInterface.PrintUserMessage("Lets Start the Game ...\n Choose an option from the menu below :");
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
                        UserInterface.ShowStatistics(rowPlayer.getNumOfMoves() + colPlayer.getNumOfMoves(), gameLogic.TotalGameTime(), rowPlayer.getScore(), colPlayer.getScore());
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
            UserInterface.PrintUserMessage("computer play his turn..he chose square (" + squareLocation.getRow()+","+squareLocation.getCol()+")");
        }
        updateUserData(squareValue); //update score and moves
        gameBoard.getMarker().setMarkerLocation(squareLocation.getRow(),squareLocation.getCol());
        UserInterface.PrintBoard(gameBoard.toString());
    }

    private void switchPlayer()
    {
        if (currentPlayer.checkPlayerTurn(rowPlayer)) {
            if (gameBoard.isColPlayerHaveMoves(gameBoard.getMarker().getMarkerLocation()))
            {
                currentPlayer = colPlayer;
            }
            else {
                isEndOfGame = true;
                UserInterface.PrintUserMessage("Col player have no moves ! GAME OVER");
            }
        } else //(currentPlayer.equals(colPlayer))
        {
            if (gameBoard.isRowPlayerHaveMoves(gameBoard.getMarker().getMarkerLocation()))
                currentPlayer = rowPlayer;
            else {
                isEndOfGame = true;
                UserInterface.PrintUserMessage("Row player have no moves ! GAME OVER");
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



   private void exitGame()
   {
       int rowPlayerScore = rowPlayer.getScore();
       int ColPlayerScore = colPlayer.getScore();
       if (rowPlayerScore > ColPlayerScore)
           UserInterface.PrintWinner("Row Player");
       else if (ColPlayerScore > rowPlayerScore )
           UserInterface.PrintWinner("Col Player");
           else //(ColPlayerScore  == rowPlayerScore )
           UserInterface.PrintWinner("TIE");

       UserInterface.PrintBoard(gameBoard.toString());
       UserInterface.ShowStatistics(rowPlayer.getNumOfMoves()+colPlayer.getNumOfMoves(),gameLogic.TotalGameTime(),rowPlayer.getScore(),colPlayer.getScore());

       startGame();//go back to start game here
   }


    private void setBoard(jaxb.schema.generated.Board board)
    {

        eBoardType boardType = eBoardType.valueOf(board.getStructure().getType());
        gameBoard = new Board(board.getSize().intValue(),boardType);

        switch (boardType) {
            case Explicit: Point markerLocation = new Point(board.getStructure().getSquares().getMarker().getRow().intValue(),board.getStructure().getSquares().getMarker().getColumn().intValue());
                          gameBoard.FillExplicitBoard(newList,markerLocation);
                          break;
            case Random:
                        BoardRange range = new BoardRange(board.getStructure().getRange().getFrom(),board.getStructure().getRange().getTo());
                        gameBoard.setBoardRange(range);
                        gameBoard.FillRandomBoard();
                         break;
        }
    }


    private void setBasicPlayers()
    {

        UserInterface.PrintPlayerSubMenu();
        int playerChoice = UserInterface.GetUserInput(1,3);
        switch (playerChoice)
        {
            case HUMAN_PLAYER:
                rowPlayer = new Player(eTurn.ROW,ePlayerType.HUMAN);
                colPlayer = new Player(eTurn.COL,ePlayerType.HUMAN);
                 break;
            case COMPUTER_PLAYER:
                rowPlayer = new Player(eTurn.ROW,ePlayerType.HUMAN);
                colPlayer = new Player(eTurn.COL,ePlayerType.COMPUTER);
                break;
            case COMPUTERS_GAME:
                rowPlayer = new Player(eTurn.ROW,ePlayerType.COMPUTER);
                colPlayer = new Player(eTurn.COL,ePlayerType.COMPUTER);
                break;
        }


    }


    private boolean checkAndSetPlayersXML(jaxb.schema.generated.Players players) //advanced game
    {
        boolean areValidPlayers = true;
        List<jaxb.schema.generated.Player> gamePlayers = players.getPlayer();
        Player newPlayer ;

        if(gamePlayers.size() < gameLogic.MIN_PLAYERS || gamePlayers.size() > gameLogic.MAX_PLAYERS)
        {
            areValidPlayers = false;
            UserInterface.ValidationErrors.add(String.format("Players Validation Error : %d - invalid numbers of players ," +
                                                              "number of players must be minimun 2 and maximum 6 !",gamePlayers.size()));
        }
        else
        {
            gameLogic.setNumOfPlayers(gamePlayers.size());
            for(jaxb.schema.generated.Player player :gamePlayers)
            {
                newPlayer = new Player(ePlayerType.valueOf(player.getType()),player.getName(),player.getId().intValue(),player.getColor());
                if(gameLogic.getPlayers().contains(newPlayer))
                {
                    areValidPlayers =false;
                    UserInterface.ValidationErrors.add(String.format("Player Validation Error: name = %s ,id = %d, color = %s already exists !",player.getName(),player.getId(),player.getColor()));
                    gameLogic.getPlayers().clear();
                    break;
                }
                else
                    {
                       gameLogic.getPlayers().add(newPlayer);
                   }
            }
        }

        return areValidPlayers;
    }

//    private boolean checkAdvanceDynamicXML(jaxb.schema.generated.DynamicPlayers dynamicPlayers) //dynamic advanced game
//    {
//        boolean areValidPlayers = true;
//
//        return areValidPlayers;
//
//    }
//
//    private void setDynamicPlayers(DynamicPlayers dynamicPlayers) //dynamic advanced game
//    {
//
//    }




    public void LoadGameFromXmlAndValidate() {
        boolean  isValidXML = false;
        String filePath = "";
       // File gameFile;
        GameDescriptor loadedGame = null;
        newList.clear();

        while (!isValidXML)
        {
            filePath = UserInterface.getXMLfile();
            loadedGame = loadGameFromFile(filePath);
            isValidXML = checkXMLData(loadedGame);
            if (!isValidXML)
            {
                UserInterface.PrintError("Invalid XML File, please load valid xml file !\n ");
                UserInterface.PrintValidationErrors();
            }
        }

        loadDataFromJaxbToGame(loadedGame); //setBoard in Basic
        UserInterface.PrintUserMessage("The XML Game file Loaded Successfully");
    }


    private boolean checkXMLData(GameDescriptor loadedGame)
    {
        int index;
        boolean isValidXMLData = true;

        String gameType = loadedGame.getGameType();
        isValidXMLData = checkBoardXML(loadedGame);

        if(isValidXMLData && (eGameType.valueOf(gameType) != eGameType.Basic) ) {
            if (eGameType.valueOf(gameType) == eGameType.Advance) {
                isValidXMLData = checkAndSetPlayersXML(loadedGame.getPlayers());
            }
            else if(eGameType.valueOf(gameType) == eGameType.AdvanceDynamic) {
                 // isValidXMLData = checkAdvanceDynamicXML(loadedGame.getDynamicPlayers());
            }
            else {
                isValidXMLData = false;
                UserInterface.ValidationErrors.add(String.format("Game Type Error : No such game type %s !", gameType));
            }
        }
        return isValidXMLData;
    }


    private boolean checkBoardXML(GameDescriptor loadedGame)
    {
        boolean isValidBoard;
        int size;
        eBoardType boardType;
        List<jaxb.schema.generated.Square> squares;

        jaxb.schema.generated.Board gameBoard = loadedGame.getBoard();
        jaxb.schema.generated.Structure structure = gameBoard.getStructure();

        size = gameBoard.getSize().intValue();
        if(size > 0)
        {
            boardType = eBoardType.valueOf(structure.getType());

            if(boardType.equals(eBoardType.Explicit))
            {
                 squares = structure.getSquares().getSquare();
                 isValidBoard = checkExplicitBoard(structure.getRange(),squares,structure.getSquares().getMarker(),size);
            }
            else
            {
                if(boardType.equals(eBoardType.Random))
                {
                    isValidBoard = checkRandomBoard(structure.getRange()); //board range check
                }
                else
                {
                    isValidBoard = false;
                    UserInterface.ValidationErrors.add(String.format("Board type validation error : %s doesn't exist!",boardType));
                }
            }
        }
        else
        {
            isValidBoard = false;
            UserInterface.ValidationErrors.add(String.format("Board size validation error : %d must be positive!",size));
        }

        return isValidBoard;
    }


    private boolean checkExplicitBoard(Range range, List<jaxb.schema.generated.Square> squares, jaxb.schema.generated.Marker marker,int boardSize)
    {
        boolean isValidBoard = true;
        game_objects.Square newSquare;
        int row,col,val,color;

        if(!isInBoardRange(marker.getRow().intValue(),boardSize))
        {
            isValidBoard = false;
            UserInterface.ValidationErrors.add(String.format("Explicit Board validation : invalid row of marker location ! Must be in range  from %d  to %d",1,boardSize));
        }
        if(!isInBoardRange(marker.getColumn().intValue(),boardSize))
        {
            isValidBoard = false;
            UserInterface.ValidationErrors.add(String.format("Explicit Board validation error: invalid column of marker location ! Must be in range  from %d  to %d",1,boardSize));
        }

        for(jaxb.schema.generated.Square square :squares)
        {
            row = square.getRow().intValue();
            col = square.getColumn().intValue();
            val = square.getValue().intValue();
            color = square.getColor();

            if(isInBoardRange(row,boardSize) && isInBoardRange(col,boardSize)) //location is ok
            {
                newSquare = new game_objects.Square(new Point(row,col),String.valueOf(val),color);
                if(newList.contains(newSquare))
                {
                    isValidBoard = false;
                    UserInterface.ValidationErrors.add(String.format("Explicit Board validation error:  square double location [%d,%d] existance!",
                            square.getRow().intValue(),square.getColumn().intValue()));
                    break;
                }
                else
                {
                    newList.add(newSquare);
                }
            }
        }

        return isValidBoard;
    }

    private boolean checkRandomBoard(Range range)
    {
        boolean isValidBoard = true;
        if(range.getFrom() >= range.getTo())
        {
            isValidBoard = false;
            UserInterface.ValidationErrors.add(String.format("Random Board range not valid : %d > = %d",range.getFrom(),range.getTo()));
        }
        return isValidBoard;

    }

    private boolean isInBoardRange(int num, int size)
    {
        boolean isValid = true;
        if(num < 0 || num > size)
        {
            isValid = false;
        }
        return isValid;

    }
    private void loadDataFromJaxbToGame(GameDescriptor loadedGame)
    {
       // String gameType = loadedGame.getGameType();
        jaxb.schema.generated.Board loadedBoard = loadedGame.getBoard();
        setBoard(loadedBoard);


       // switch (eGameType.valueOf(gameType))
       // {
           // case Basic: setBasicPlayers();
                     //    break;
//            case Advance: Players players = loadedGame.getPlayers();
//                            setPlayers(players);
//                             break;
           // case AdvanceDynamic: DynamicPlayers dynamicPlayers = loadedGame.getDynamicPlayers();
                                    // setDynamicPlayers(dynamicPlayers);
                                 //    break;
      //  }

    }


    private GameDescriptor loadGameFromFile(String fileName)
    {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File("C:\\Users\\OR\\IdeaProjects\\Numberiada\\src\\xml_resources\\Numberiada.xsd"));
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
