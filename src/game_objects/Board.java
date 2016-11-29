/**
 * Created by Alona on 11/7/2016.
 */
package game_objects;
import game_interface.UserInterface;

import java.util.Random;

public class Board {

    private static final int MIN_SIZE = 5;
    private static final int MAX_SIZE = 50;

    private int boardSize;
    private eBoardType boardType;
    private Square[][] gameBoard;
    private BoardRange boardRange;
    private Marker marker;



    public Board(Board gameBoard){}
    public Board(int size, BoardRange range, eBoardType type)
    {
        boardSize = size;
        boardType = type;
        gameBoard = new Square[size][size];
        boardRange = new BoardRange(range.getFrom(),range.getTo());
        marker= new Marker(size-1,size-1);
        InitBoard();
    }

    public int GetBoardSize()
    {
        return boardSize;
    }
    public eBoardType GetBoardType(){return boardType;}
    public void SetBoardType(eBoardType type){boardType = type; }


    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }



    public void InitBoard()
    {

        for (int i=0;i<boardSize;i++)
        {
            for(int j=0;j<boardSize;j++)
            {
                gameBoard[i][j] = new Square(new Point(i,j));
            }
        }

    }


    public void FillExplicitBoard()
    {

    }

    private boolean checkRandomBoardValidity()
    {
        boolean isValidBoard = false;
        int range = 0;

        if(boardRange.getFrom() < boardRange.getTo())
        {
            range = boardRange.RangeSize();

            if((boardSize*boardSize -1) >= range)
            {
                isValidBoard = true;
            }
            else
            {
                UserInterface.ValidationErrors.add(String.format("Random Board Creation Error: Board Size %d < Board Range numbers %d (from %d to %d)",
                        boardSize*boardSize,range,boardRange.getFrom(),boardRange.getTo()));
            }

        }
        else
        {
            UserInterface.ValidationErrors.add(String.format("Random Board Creation Error: Board Range numbers invalid from %d > to %d",
                    boardRange.getFrom(),boardRange.getTo()));
        }

        return isValidBoard;

    }

    public boolean FillRandomBoard() {

        int i = 0;
        int j = 0;
        boolean isValidBoardData = checkRandomBoardValidity();

        if (isValidBoardData) {

            Random rand = new Random();

            // filling our numbers in given range
            int rangeSize = boardRange.RangeSize();
            int printNumCount = (boardSize * boardSize -1) / rangeSize; //49/9=5
            int rangeNumToPrint = boardRange.getFrom();


            for(int m = 0;m < rangeSize && i< boardSize;m++) {
                for (int k = 0; k < printNumCount && i< boardSize; k++) {
                    gameBoard[i][j].setValue(Square.ConvertFromIntToStringValue(rangeNumToPrint));
                    j++;
                    if (j == boardSize) {
                        i++;
                        j = 0;
                    }
                }
                rangeNumToPrint++;
            }


        if (j == boardSize) {
            j = 0;
        }

        for (int m = i; m < boardSize; m++) {
            for (int n = j; n < boardSize; n++) {
                gameBoard[m][n].setValue("");
            }
        }

        gameBoard[boardSize - 1][boardSize - 1].setValue(Marker.markerSign);
        shuffleArray(gameBoard);
    }

        return isValidBoardData;
    }




   private void shuffleArray(Square[][] matrix) {
        Random random = new Random();

        for (int i = matrix.length - 1; i > 0; i--) {
            for (int j = matrix[i].length - 1; j > 0; j--) {
                int m = random.nextInt(i + 1);
                int n = random.nextInt(j + 1);

                String temp = matrix[i][j].getValue();
                matrix[i][j].setValue(matrix[m][n].getValue());
                matrix[m][n].setValue(temp);
            }
        }
    }


private void printMatrix()
{
    for(int i =0 ;i<boardSize;i++)
    {
        for(int j=0;j<boardSize;j++)
        {
            System.out.print(gameBoard[i][j].getValue() +" ");
        }
        System.out.print("\n");
    }
}


    private String setBoardRow(int size)
    {
        String row = "    ";

        for(int fr = 0 ; fr < size; fr++)
        {
           row+=("-");
        }
        row+="\n";

        return row;
    }

    private int digits(int num)
    {
        int digits;
        if(num != 0)
          digits = (int)(Math.log10(num)+1);
        else
            digits=1;

        return digits;

    }

    private String spaces(String value)
    {
        String spaces ="";
        int maxCell = sellSize();
        int size = value.length();

            for(int i=0;i<maxCell-size;i++)
            {
                spaces+=" ";
            }

        return spaces;
    }


    private int sellSize()
    {
        int size =0;
        if(boardRange.getTo()<0 || boardRange.getFrom()<0){
            size++;
        }
        size+= digits(Math.max(Math.abs(boardRange.getFrom()),Math.abs(boardRange.getTo())));
        return size;
    }

    @Override
    public String toString() {
        int j;
        int fr;
        String space;
        int maxDigits = sellSize();
        int size = maxDigits*boardSize + boardSize + 1;

        StringBuilder board = new StringBuilder();
        String boardRow = setBoardRow(size);

        space = spaces("");
        board.append(space+space);
        for( fr = 1 ; fr <= boardSize; fr++)
        {
            space = spaces(Square.ConvertFromIntToStringValue(fr));
            board.append(fr);
            board.append(space+" ");
        }
        board.append("\n");
        board.append(boardRow);


        for(int i = 0; i<boardSize; i++)
        {
              space = spaces(Square.ConvertFromIntToStringValue(i+1));
              board.append(space);
              board.append(i+1);
              board.append(" |");

              space=spaces(gameBoard[i][0].getValue());
              board.append(space);


            for(j = 0; j<boardSize; j++)
            {
               if(j>0)
               {
                    space=spaces(gameBoard[i][j].getValue());
                    board.append(space);
                }
                board.append(gameBoard[i][j].getValue());
                board.append("|");

            }
            board.append("\n");
            board.append(boardRow);
        }

        return board.toString();
    }

    public int updateBoard(Point squareLocation)
    {
        Point oldMarkerPoint = marker.getMarkerLocation();                         //empty old marker location
        gameBoard[oldMarkerPoint.getRow()][oldMarkerPoint.getCol()].setValue("");

        String squareStringValue = gameBoard[squareLocation.getRow()][squareLocation.getCol()].getValue(); //get number
        int squareValue = Square.ConvertFromStringToIntValue(squareStringValue); //return number value

        gameBoard[squareLocation.getRow()][squareLocation.getCol()].setValue(marker.markerSign); //update marker to square

        return squareValue;
    }

     public boolean isGameOver(Point markerLocation)
     {
         int MarkerRow = markerLocation.getRow();
         int MarkerCol = markerLocation.getCol();
         for (int i=0; i < boardSize; i++)
             if ((gameBoard[MarkerRow][i].isEmpty() == false) && (gameBoard[MarkerRow][i].getValue()!= marker.markerSign))
                 return false;
             for (int i=0; i < boardSize; i++)
                 if ((gameBoard[i][MarkerCol].isEmpty() == false)&& (gameBoard[MarkerRow][i].getValue()!= marker.markerSign))
                     return false;
         return true;
     }

}
