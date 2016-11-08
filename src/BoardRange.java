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
