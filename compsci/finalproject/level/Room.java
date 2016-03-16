package compsci.finalproject.level;

import java.util.*;
import java.io.*;

import compsci.finalproject.gameobject.*;
/**
 * Represents a room.
 * 
 * @author Michael Shuen, Ryan Later
 * @version 3/11/2016
 */
public class Room
{
    private short[][] constData;
    private boolean safe;
    
    private Room(Scanner s)
    {
        // set safe in constructor
        try
        {
            int rRows = s.nextInt();
            int rCols = s.nextInt();
            String flags = s.next();
            if (flags.indexOf('s') >= 0)
                safe = true;
            constData = new short[rRows][rCols];
            for (int row = 0; row < rRows; row++)
            {
                for (int col = 0; col < rCols; col++)
                {
                    short itemCode = s.nextShort(36);
                    constData[row][col] = itemCode;
                    //constData[w][h] = GameObject.gameObjectFromCode(itemCode);
                }
            }
        }
        catch (InputMismatchException e)
        {
            // File format error
            System.out.println("Error parsing room file");
            System.out.println("Next token was: " + s.next());
            throw e;
        }
        catch (NoSuchElementException e)
        {
            // File does not have claimed amount of data
            System.out.println("Room file truncated");
            throw e;
        }
    }
    
    public Room(File f) throws FileNotFoundException
    {
        this(new Scanner(f));
    }
    
    public Room(String s)
    {
        this(new Scanner(s));
    }
    
    public boolean isSafe()
    {
        return safe;
    }
    
    public int getWidth()
    {
        return constData.length;
    }
    
    public int getHeight()
    {
        return constData[0].length;
    }
    
    public short[][] getData()
    {
        short[][] out = new short[getWidth()][getHeight()];
        for (int w = 0; w < getWidth(); w++)
        {
            for (int h = 0; h < getHeight(); h++)
            {
                out[w][h] = constData[w][h];
            }
        }
        return out;
    }
}
