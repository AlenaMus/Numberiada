package game_engine;

import game_interface.UserInterface;
import game_objects.*;

//import javax.xml.bind.Marshaller;


public class GameManager {

    private static final int LOAD_GAME = 1;
    private static final int START_GAME = 2;
    private static final int EXIT_GAME = 3;

    private static final int SHOW_BOARD_AND_CURRENT_PLAYER = 1;
    private static final int MAKE_A_MOVE = 2;
    private static final int SHOW_STATISTICS = 3;
    private static final int LEAVE_GAME = 4;
    public static final int BAD_SQUARE = 100;

    private static int gameRound = 0;
    private boolean isLoadedGame = false;
   // protected eGameType gameType;
    private GameLogic gameLogic;




    public GameManager()
    {
        gameLogic = new GameLogic();
        gameLogic.setLoadedGame(null);
        runGame();
    }


    private void runGame()
    {
        UserInterface.PrintFirstMenu();
        int userChoise = UserInterface.GetUserInput(1, 3);

        while(userChoise != EXIT_GAME)
        {
            if(userChoise == LOAD_GAME)
            {
                isLoadedGame=gameLogic.LoadGameFromXmlAndValidate();
                gameRound = 0;
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
            exitGame();
    }


    private void initGame()
    {
        if(gameRound >= 1)
        {
            gameLogic.loadDataFromJaxbToGame(gameLogic.getLoadedGame());
        }
        gameLogic.setBasicPlayers();
        gameLogic.isEndOfGame = false;
        gameLoop();
    }


    private void gameLoop() {

        int option;

        gameLogic.setCurrentPlayer(gameLogic.getRowPlayer());
        if (!gameLogic.InitMoveCheck())
        {
            return;

        }
        else {
            UserInterface.PrintUserMessage("Lets Start the Game ...\n Choose an option from the menu below :");
            gameLogic.setStartTime(System.currentTimeMillis());
            while (!gameLogic.isEndOfGame) {
                if (gameLogic.getCurrentPlayer().getPlayerType() == ePlayerType.COMPUTER) {
                    gameLogic.makeMove();
                    gameLogic.switchPlayer();
                } else { //HUMAN TURN
                    UserInterface.PrintSecondMenu();
                    option = UserInterface.GetUserInput(SHOW_BOARD_AND_CURRENT_PLAYER, LEAVE_GAME);

                    switch (option) {
                        case SHOW_BOARD_AND_CURRENT_PLAYER:
                            UserInterface.PrintBoard(gameLogic.getGameBoard().toString());
                            UserInterface.PrintCurrentPlayer(gameLogic.getCurrentPlayer().getTurn());
                            break;
                        case MAKE_A_MOVE:
                            gameLogic.makeMove();
                            gameLogic.switchPlayer();
                            break;
                        case SHOW_STATISTICS:
                            UserInterface.ShowStatistics(gameLogic.getRowPlayer().getNumOfMoves() + gameLogic.getColPlayer().getNumOfMoves(), gameLogic.TotalGameTime(), gameLogic.getRowPlayer().getScore(), gameLogic.getColPlayer().getScore());
                            break;
                        case LEAVE_GAME: //go To main Menu
                            gameLogic.gameOver();
                            gameLogic.isEndOfGame = true;
                            break;

                    }
                }
            }

            setEndOfGame();
        }
    }

   private void exitGame()
   {
     UserInterface.exitGameFromMainMenu();
   }

   private void setEndOfGame()
   {
       gameRound++;
       runGame();
   }




}
