/**
 * Created by Alona on 11/7/2016.
 */
public class Square {

    private Point location;
    private boolean isEmpty;
    private int value;


    public Square(Point location, boolean isEmpty, int value) {
        this.location = location;
        this.isEmpty = isEmpty;
        this.value = value;
    }

    public Square(){}

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
