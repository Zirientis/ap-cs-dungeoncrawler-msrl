package compsci.finalproject.level;


/**
 * Write a description of class Position here.
 * 
 * @author Michael Shuen, Ryan Later
 * @version 3/15/2016
 */
public class Position
{
    private int row;
    private int col;
    
    public Position(int theRow, int theColumn)
    {
        row = theRow;
        col = theColumn;
    }
    
    public int getRow()
    {
        return row;
    }
    
    public int getCol()
    {
        return col;
    }
}
