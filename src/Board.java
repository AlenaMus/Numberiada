/**
 * Created by Alona on 11/7/2016.
 */

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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

    private void initBoard()
    {
        for (int i=0;i<boardSize;i++)
        {
            for(int j=0;j<boardSize;j++)
            {
                gameBoard[i][j] = new Square(new Point(i,j));
            }
        }

    }
//
//    public String BoardToPrint()
//    {
//
//
//    }

    private void fillExplicitBoard()
    {

    }

    private void fillRandomBoard()
    {
        int i,j=0;
        Random rand = new Random();
        // filling our numbers in given range
        int size = boardRange.getTo() - boardRange.getFrom();
        int rangeNum = boardRange.getFrom();
        for(i=0;i<boardSize && 0 <size; i++)
        {
          for(j=0;j<boardSize;j++)
          {
            gameBoard[i][j].setValue(rangeNum);
              rangeNum++;
          }
          size--;
        }


        for(int m=i; m<boardSize; m++)
        {
            for(int n=j; n<boardSize; n++)
            {
                gameBoard[m][n].setValue(rand.nextInt(boardRange.getTo()) + boardRange.getFrom());
            }

        }

        shuffleArray(gameBoard);

    }




   private void shuffleArray(Square[][] matrix) {
        Random random = new Random();

        for (int i = matrix.length - 1; i > 0; i--) {
            for (int j = matrix[i].length - 1; j > 0; j--) {
                int m = random.nextInt(i + 1);
                int n = random.nextInt(j + 1);

                int temp = matrix[i][j].getValue();
                matrix[i][j].setValue( matrix[m][n].getValue());
                matrix[m][n].setValue(temp);
            }
        }
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
