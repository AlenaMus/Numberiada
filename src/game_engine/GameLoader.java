package game_engine;

import game_interface.UserInterface;
import jaxb.schema.generated.Board;
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

import jaxb.schema.generated.*;

/**
 * Created by Alona on 11/14/2016.
 */
public class GameLoader {

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
        Players loadedPlayers = loadedGame.getPlayers();
        Board loadedBoard = loadedGame.getBoard();
        boolean currentPlayerExists = false;

        if (loadedGame.getCurrentPlayer().trim().equals(""))
            isValidXMLData = false; // current player name is empty or whitespaces

        if (loadedBoard.getCell().isEmpty())
            isValidXMLData = false;

        return isValidXMLData;

    }

    private void loadDataFromJaxbToGame(GameDescriptor loadedGame) {

        //String playerColor;
        String playerName;
        String gameType = loadedGame.getGameType();


        Board loadedBoard = loadedGame.getBoard();


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




    private  GameDescriptor loadGameFromFile(String fileName)
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
