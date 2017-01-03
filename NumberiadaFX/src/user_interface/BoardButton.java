package user_interface;

import game_objects.Point;
import game_objects.Square;
import javafx.scene.control.Button;

/**
 * Created by Alona on 1/2/2017.
 */
public class BoardButton extends Button {

    private Square boardButton;

public BoardButton(Square buttonSquare)
{
    this.boardButton = buttonSquare;
}

public Point GetLocation()
{
    return boardButton.getLocation();
}

public int GetValue()
{
    return Integer.valueOf(boardButton.getValue());
}



}
