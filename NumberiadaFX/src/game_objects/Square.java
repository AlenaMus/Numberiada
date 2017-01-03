/**
 * Created by Alona on 11/7/2016.
 */

package game_objects;


public class Square {

    private Point location;
    private boolean isEmpty;
   // private int value;
    private String value;
    private int color;


    public Square(Point location) {
        this.location = location;
        this.isEmpty = true;
        this.value = "";
    }

    public Square(){
        this.isEmpty = true;
    }

    public Square(Point location, String value, int color)
    {
        this.isEmpty = false;
        this.location = new Point(location.getRow(),location.getCol());
        this.value = value;
        this.color = color;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public String getValue(){return value;}
    public void setValue(String value)
    {
        this.value = value;
        if(value.equals(""))
        {
            this.setEmpty(true);
        }
        else
        {
            this.setEmpty(false);
        }
    }


    public static int ConvertFromStringToIntValue(String value)
    {
        int num = 0;
        if(value != null && !value.equals(Marker.markerSign))
        {
            num = Integer.parseInt(value);
        }

        return num;

    }

    public static String ConvertFromIntToStringValue(int value)
    {
        String valueS = "";
        if(value > 0)
        {
            valueS=Integer.toString(value);
        }
        else
        {
            valueS =Integer.toString(value);
        }

        return valueS;
    }

    public boolean isMarker(String value)
    {
        boolean isMarker = value.equals(Marker.markerSign)?true:false;
        return isMarker;
    }


    @Override
    public boolean equals(Object square)
    {
        boolean isEqual = false;
        if(square != null) {
            if (square instanceof Square) {
                  Square square1 = (Square) square;
                if (this.getLocation().getCol() == square1.getLocation().getCol() && this.getLocation().getRow() == square1.getLocation().getRow()) {
                    isEqual = true;
                }
            }
        }

        return isEqual;
    }

    @Override
    public int hashCode() {
        return super.hashCode();

    }




}
