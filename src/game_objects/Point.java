/**
 * Created by Alona on 11/7/2016.
 */

package game_objects;

public class Point {
    private int row;
    private int col;

    public Point(int row1,int col1)
    {
        this.row = row1;
        this.col = col1;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col1) {
        this.col = col1;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row1) {
        this.row = row1;
    }
}
