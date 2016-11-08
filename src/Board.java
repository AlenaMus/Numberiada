/**
 * Created by Alona on 11/7/2016.
 */
public class Board {
    private int boardSize;
    private eBoardType boardType;
    private Square[][] gameBoard;
    private BoardRange boardRange;




    public Board(int size)
    {
        boardSize = size;
    }

    public void CreateBoard(eBoardType boardType) {
        this.boardType = boardType;
        gameBoard = new Square[boardSize][boardSize];
        switch (boardType) {
            case EXPLICIT: createExplicitBoard();
                break;
            case RANDOM: createRandomBoard();
                break;
        }
    }

    private void createExplicitBoard()
    {

    }

    private void createRandomBoard()
    {



    }



}
