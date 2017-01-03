package user_interface;

import game_objects.Board;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GameController {

    Stage gameStage;

    public GameController(Stage primaryStage)throws Exception{

        gameStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("NumberidaGUI.fxml"));
        primaryStage.setTitle("Numberiada");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public void Execute()
    {

    }

    public void CreateGameBoardView() ///Board gameBoard,
        {
        GridPane board = new GridPane();
        board.setPadding(new Insets(10, 10, 10, 10));
        board.setVgap(8);
        board.setHgap(10);
        BorderPane.setAlignment(board, Pos.CENTER);


    }



    public void MakeAMoveButtonClicked()
    {

    }

    public void RetireGameButtonClicked()
    {

    }

    public void PrevButtonClicked()
    {

    }

    public void NextButtonClicked()
    {

    }

    public void LoadXmlFileButtonClicked() //throws IOException
    {

//            try{
//                GameLogic logic = new GameLogic();
//                FileChooser fileChooser = new FileChooser();
//                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
//                fileChooser.getExtensionFilters().add(extFilter);
//                fileChooser.setTitle("Open Resource File");
//                File loadedFile =fileChooser.showOpenDialog(myStage);
//                if (loadedFile != null)
//                {
//
//                    logic.loadGame(loadedFile);
//                    FXMLLoader loader = new FXMLLoader(getClass().getResource("GameBoard.fxml"));
//                    Parent gameBoard = (Parent)loader.load();
//                    GameController controller = (GameController)loader.getController();
//                    ((Node)event.getSource()).getScene().getWindow().hide();
//                    controller.logic=logic;
//                    controller.logic.setCurrPlayer(controller.logic.getPlayersList().get(logic.getPlayerIndex()));
//                    controller.printPlayersTiles();
//                    controller.printBoard();
//                    Scene scene=new Scene(gameBoard);
//                    Stage stage=new Stage();
//                    stage.setScene(scene);
//                    stage.show();
//                    Button b= new Button();
//                    //controller.computer();
//                    throw new IOException("your message");
//                }
//            }
//            catch(IOException e)
//            {
//
//            };
//

    }


   public void StartGameButtonClicked()
   {

   }


}
