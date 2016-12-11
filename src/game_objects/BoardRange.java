package game_objects;

/**
 * Created by Alona on 11/7/2016.
 */
public class BoardRange {

    private int from;
    private int to;

    public BoardRange(int from,int to)
    {
        this.from = from;
        this.to = to;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public int RangeSize()
    {
       int range = to - from +1;
        return range;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public boolean IsValidRange(int from, int to)
    {
        boolean isValid = true;
        if(from > to || from == to)
        {
            isValid = false;
        }
        return  isValid;
    }
}
