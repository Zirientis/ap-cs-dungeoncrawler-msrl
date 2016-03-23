package compsci.finalproject.level;

import java.util.*;
/**
 * Write a description of class Position here.
 * 
 * @author Michael Shuen, Ryan Later
 * @version 3/15/2016
 */
public class Position implements Comparable
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
    
    public String toString()
    {
        return String.format("(%d, %d)", row, col);
    }
    
    public int compareTo(Object otherO)
    {
        if (otherO instanceof Position)
        {
            Position other = (Position)otherO;
            if (row == other.row)
                if (col == other.col)
                    return 0;
                else
                    return col - other.col;
            else
                return row - other.row;
        }
        throw new IllegalArgumentException();
   }
    
    public boolean equals(Position other)
    {
        return row == other.row && col == other.col;
    }
}
