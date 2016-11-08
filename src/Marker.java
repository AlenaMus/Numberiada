/**
 * Created by Alona on 11/7/2016.
 */
public class Marker {

    private Point markerLocation;
    public static final char markerSign = '*';

    public Marker(int row,int col)
    {
        markerLocation = new Point(row,col);
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
