
package game_objects;

public class Marker {

    private Point markerLocation;

    public static final String markerSign = "#";

    public Marker(int row,int col)
    {
        markerLocation = new Point(row,col);
    }

    public  String getMarkerSign() {
       return markerSign;
   }
    public Point getMarkerLocation() {
        return markerLocation;
    }

    public void setMarkerLocation(Point markerLocation) {
        this.markerLocation = markerLocation;
    }

    public void setMarkerLocation(int row,int col)
    {
        markerLocation.setRow(row);
        markerLocation.setCol(col);
    }





}
