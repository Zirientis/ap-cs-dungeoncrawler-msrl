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
    private byte[][] constData;
    private boolean safe;
    
    private Room(Scanner s)
    {
        // set safe in constructor
        try
        {
            int roomWidth = s.nextInt();
            int roomHeight = s.nextInt();
            String flags = s.next();
            if (flags.indexOf('s') >= 0)
                safe = true;
            constData = new byte[roomWidth][roomHeight];
            for (int w = 0; w < roomWidth; w++)
            {
                for (int h = 0; h < roomHeight; h++)
                {
                    byte itemCode = s.nextByte();
                    constData[w][h] = itemCode;
                    //constData[w][h] = GameObject.gameObjectFromCode(itemCode);
                }
            }
        }
        catch (InputMismatchException e)
        {
            // File format error
            System.out.println("Error parsing room file");
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
        return constData[0].length;
    }
    
    public int getHeight()
    {
        return constData.length;
    }
    
    public byte[][] getData()
    {
        byte[][] out = new byte[getWidth()][getHeight()];
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
