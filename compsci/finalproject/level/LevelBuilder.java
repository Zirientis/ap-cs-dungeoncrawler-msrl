package compsci.finalproject.level;


import java.util.*;
public class LevelBuilder
{
    public static Level build(int difficulty, long seed)
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

        Random randomSource = new Random(seed);
        System.out.println(randomSource.nextDouble());
        int dimBase = 25 * difficulty;
        double monsterScale = difficulty * difficulty;
        double ilvl = difficulty * Math.log10(difficulty);
        int width = (int)(((randomSource.nextDouble() / 2) + 0.75) * dimBase);
        int height = (int)(((randomSource.nextDouble() / 2) + 0.75) * dimBase);
        //int width = 25;
        //int height = 50;
        assert(width > 0);
        assert(height > 0);
        System.out.println("Width: " + width);
        System.out.println("Height: " + height);
        short[][] levelMap = new short[height][width];
        for (short[] row : levelMap)
        {
            for (int i = 0; i < row.length; i++)
            {
                row[i] = ' ';
            }
        }
        ArrayList<Position> roomsTopLeft = new ArrayList<Position>();
        ArrayList<Position> roomsBottomRight = new ArrayList<Position>();
        // Pass 1
        {
            char debugMarker = 'a';
            debugPrint("Beginning pass 1");
            ArrayList<Position> nextHeight = new ArrayList<Position>();
            nextHeight.add(new Position(0, 0));
            while (nextHeight.get(0).getRow() < height)
            {
                debugPrint("Outer loop");
                int curWidth = nextHeight.get(0).getCol();
                //if (curWidth > 0)
                //    curWidth--;
                int curHeight = nextHeight.remove(0).getRow();
                if (curHeight > 0)
                    curHeight--;
                debugPrint("curWidth SET to " + curWidth);
                debugPrint("curHeight SET to " + curHeight);
                //int curWidth = 0;
                //int curHeight = 0;
                int roomHeight = 0;
                int roomWidth = 0;
                while (curWidth < width)
                {
                    debugPrint("Inner loop");
                    double rand = randomSource.nextDouble();
                    roomHeight = 0;
                    roomWidth = 0;
                    //curWidth = minHeights.get(0).getCol();
                    //curHeight = minHeights.remove(0).getRow();
                    if (rand > 0.95) // Large room
                    {
                        debugPrint("Large room");
                        // Minimum dimension is 22x22 (20x20)
                        // Maximum dimension is 32x32 (30x30)
                        roomHeight = (int)(randomSource.nextDouble() * 11 + 22);
                        roomWidth = (int)(randomSource.nextDouble() * 11 + 22);
                    }
                    else // Normal room
                    {
                        debugPrint("Normal room");
                        // Minimum dimension is 12x12 (10x10)
                        // Maximum dimension is 22x22 (20x20)
                        roomHeight = (int)(randomSource.nextDouble() * 11 + 12);
                        roomWidth = (int)(randomSource.nextDouble() * 11 + 12);
                    }
    
                    if (curHeight + roomHeight >= height)
                    {
                        debugPrint("Overshot height");
                        int overshoot = (curHeight + roomHeight) - height + 1;
                        assert(overshoot > 0);
                        roomHeight -= overshoot;
                    }
    
                    if (curWidth + roomWidth >= width)
                    {
                        debugPrint("Overshot width");
                        int overshoot = (curWidth + roomWidth) - width + 1;
                        assert(overshoot > 0);
                        roomWidth -= overshoot;
                    }
                    
                    assert(curHeight + roomHeight < height);
                    assert(curWidth + roomWidth < width);
                    
                    /*
                    for (int i = curHeight; i < curHeight + roomHeight; i++)
                    {
                        for (int j = curWidth; j < curWidth + roomWidth; j++)
                        {
                            if (i == curHeight || i == curHeight + roomHeight - 1 || j == curWidth || j == curWidth + roomWidth - 1)
                                levelMap[i][j] = 1;
                        }
                    }
                    */
                    
                    if (roomHeight <= 2 || roomWidth <= 2)
                    {
                        debugPrint("Imaginary room");
                        break;
                    }
                    
                    debugPrint("curHeight " + curHeight);
                    debugPrint("curWidth " + curWidth);
                    for (int r = curHeight; r < curHeight + roomHeight; r++)
                    {
                        for (int c = curWidth; c < curWidth + roomWidth; c++)
                        {
                            //System.out.printf("Now setting (%d, %d) ", r, c);
                            try
                            {
                                if (r == curHeight || r == curHeight + roomHeight - 1 || c == curWidth || c == curWidth + roomWidth - 1)
                                    levelMap[r][c] = (short)debugMarker;
                            }
                            catch (ArrayIndexOutOfBoundsException e)
                            {
                                System.out.print("failed");
                                System.err.printf("Failed setting (%d, %d)%n", r, c);
                            }
                            finally
                            {
                                //System.out.println();
                            }
                            
                        }
                    }
                    //if (curHeight == 0)
                    //    sortedInsert(nextHeight, new Position(curWidth + roomWidth, curHeight));
                    roomsTopLeft.add(new Position(curHeight, curWidth));
                    //curHeight += roomHeight;
                    sortedInsert(nextHeight, new Position(curHeight + roomHeight, curWidth));
                    //if (curHeight == 0)
                    curWidth += (roomWidth - 1);
                    roomsBottomRight.add(new Position(curHeight + roomHeight, curWidth));
                    //if (curHeight != 0)
                    //    break;
                    //curHeight += roomHeight;
                }
                curHeight += roomHeight;
                for (short[] row : levelMap)
                {
                    for (short cell : row)
                    {
                        System.out.print((char)cell);
                    }
                    System.out.println();
                }
                debugMarker++;
                if (curWidth > width)
                    break;
                if (nextHeight.size() == 0)
                    break;
            }
        }
        
        
        int spawnX = (int)(randomSource.nextDouble() * width);
        int spawnY = (int)(randomSource.nextDouble() * height);
        
        System.out.println("roomsTopLeft: " + roomsTopLeft);
        System.out.println("roomsBottomRight: " + roomsBottomRight);

        return new Level(levelMap);
    }

    private static void sortedInsert(ArrayList<Position> arr, Position pos)
    {
        // Precondition
        if (arr.size() == 0)
        {
            arr.add(pos);
            debugPrint("added " + pos + " to nextHeight");
            return;
        }
        
        for (int i = 0; i < arr.size(); i++)
        {
            Position pAtI = arr.get(i);
            if (pos.getRow() < pAtI.getRow())
            {
                arr.add(i, pos);
                debugPrint("added " + pos + " to nextHeight");
                return;
            }
            else if (pos.getRow() == pAtI.getRow())
                if (pos.getCol() < pAtI.getCol())
                {
                    arr.add(i, pos);
                    debugPrint("added " + pos + " to nextHeight");
                    return;
                }
                else
                    continue;
            else
                continue;
        }
        
       //arr.add(pos);
       //arr.sort(null);
    }

    private static void debugPrint(String s)
    {
        System.out.println(s);
        /*
        try
        {
            Thread.sleep(50);
        }
        catch (InterruptedException e)
        {

        }
        */
    }
}
