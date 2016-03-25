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
        int dimBase = 40 * difficulty;
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
        ArrayList<Position> roomsTopLeft = new ArrayList<Position>();
        ArrayList<Position> roomsBottomRight = new ArrayList<Position>();
        // Pass 1
        {
            debugPrint("Beginning pass 1");
            ArrayList<Position> minHeights = new ArrayList<Position>();
            minHeights.add(new Position(0, 0));
            while (minHeights.get(0).getRow() < height)
            {
                debugPrint("Outer loop");
                int curWidth = minHeights.get(0).getCol();
                int curHeight = minHeights.remove(0).getRow();
                while (curWidth < width)
                {
                    debugPrint("Inner loop");
                    double rand = randomSource.nextDouble();
    
                    int roomHeight = 0;
                    int roomWidth = 0;
                    if (rand < 0.1) // Small room
                    {
                        debugPrint("Small room");
                        // Minimum dimension is 6x6 (4x4), due to shrinkage
                        // Maximum dimension is 12x12 (10x10)
                        roomHeight = (int)(randomSource.nextDouble() * 7 + 6);
                        roomWidth = (int)(randomSource.nextDouble() * 7 + 6);
                    }
                    else if (rand > 0.95) // Large room
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
                    
                    if (roomHeight <= 0 || roomWidth <= 0)
                    {
                        debugPrint("Imaginary room");
                        break;
                    }
                    
                    for (int r = curHeight; r < curHeight + roomHeight; r++)
                    {
                        for (int c = curWidth; c < curWidth + roomWidth; c++)
                        {
                            //System.out.printf("Now setting (%d, %d) ", r, c);
                            try
                            {
                                if (r == curHeight || r == curHeight + roomHeight - 1 || c == curWidth || c == curWidth + roomWidth - 1)
                                    levelMap[r][c] = 1;
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
                    roomsTopLeft.add(new Position(curHeight, curWidth));
                    //curHeight += roomHeight;
                    sortedInsert(minHeights, new Position(curHeight + roomHeight, curWidth));
                    curWidth += roomWidth;
                    roomsBottomRight.add(new Position(curHeight + roomHeight, curWidth));
                    curHeight += roomHeight;
                }

                for (short[] row : levelMap)
                {
                    for (short cell : row)
                    {
                        System.out.print(cell);
                    }
                    System.out.println();
                }
                if (curWidth > width)
                    break;
                if (minHeights.size() == 0)
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
            return;
        }
        
        for (int i = 0; i < arr.size(); i++)
        {
            Position pAtI = arr.get(i);
            if (pos.getRow() < pAtI.getRow())
                arr.add(i, pos);
            else if (pos.getRow() == pAtI.getRow())
                if (pos.getCol() < pAtI.getCol())
                    arr.add(i, pos);
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
        try
        {
            Thread.sleep(50);
        }
        catch (InterruptedException e)
        {

        }
    }
}
