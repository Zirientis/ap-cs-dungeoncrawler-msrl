package compsci.finalproject.level;

import java.util.ArrayList;
/**
 * Assembles rooms (fragments) into complete levels.
 * 
 * @author Michael Shuen, Ryan Later
 * @version 3/11/2016
 */
public class FragmentAssembler
{
    private Room[] allRooms;
    private ArrayList<Room> safeRooms;
    private int safeRoomsRemaining;
    
    public FragmentAssembler(Room[] rList)
    {
        allRooms = new Room[rList.length];
        safeRooms = new ArrayList<Room>();
        for (int i = 0; i < rList.length; i++)
        {
            allRooms[i] = rList[i];
            if (rList[i].isSafe())
                safeRooms.add(rList[i]);
        }
        safeRoomsRemaining = 0;
    }
    
    //public Level makeLevel with flags
    public Level makeLevel(int difficulty)
    {
        /*
         * Dungeon dimension scaling: n
         * Monster difficulty: n^2
         * Item level: n log(n)
         * Overall: n^2/log(n)
         */
        if (difficulty == Integer.MAX_VALUE)
            for (;;)
                System.out.println("You won!");
                
        int dimBase = 40 * difficulty;
        double monsterScale = difficulty * difficulty;
        double ilvl = difficulty * Math.log10(difficulty);
        int width = (int)(Math.random() / 2.5 + 0.75) * dimBase;
        int height = (int)(Math.random() / 2.5 + 0.75) * dimBase;
        byte[][] levelMap = new byte[width][height];
        int safeRooms = (int)Math.ceil(10.0 / difficulty);
        // Now put rooms on the map Yay T_T
        
        Room firstRoom = getRoom(getIsNextRoomSafe());
        int firstX = (int)(Math.random() * (width - firstRoom.getWidth()));
        int firstY = (int)(Math.random() * (height - firstRoom.getHeight()));
        return null;
    }
    
    private boolean getIsNextRoomSafe()
    {
        return safeRoomsRemaining-- > 0;
    }
    
    private Room getRoom(boolean isSafe)
    {
        return getRoom(Integer.MAX_VALUE, Integer.MAX_VALUE, isSafe);
    }
    
    private Room getRoom(int maxWidth, int maxHeight, boolean isSafe)
    {
        if (isSafe)
        {
            Room r;
            do
            {
                r = safeRooms.get((int)(Math.random() * safeRooms.size()));
            }
            while (r.getWidth() <= maxWidth && r.getHeight() <= maxHeight);
            return r;
            // TODO: Allow rotation of rooms
        }
        while (true)
        {
            Room r = allRooms[(int)(Math.random() * allRooms.length)];
            if (!r.isSafe() && r.getWidth() <= maxWidth && r.getHeight() <= maxHeight)
                return r;
        }
    }
    
    private void copyData(Room from, byte[][] map, int startX, int startY)
    {
        byte[][] data = from.getData();
        for (int w = startX; w < data[0].length; w++)
        {
            for (int h = startY; h < data.length; h++)
            {
                assert(map[w][h] == 0);
                map[w][h] = data[w][h];
            }
        }
    }
}
