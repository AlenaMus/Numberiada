/**
 * Created by Alona on 11/7/2016.
 */
public class Board {

    private static final int MIN_SIZE = 5;
    private static final int MAX_SIZE = 50;

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
            case EXPLICIT: fillExplicitBoard();
                break;
            case RANDOM: fillRandomBoard();
                break;
        }
    }


    private void fillExplicitBoard()
    {

    }

    private void fillRandomBoard()
    {


    }

    /**
     * fills values to the board matrix
     */
    private void fillBoard()
    {

    }

    @Override
    public String toString() {
        return super.toString();
    }



}
