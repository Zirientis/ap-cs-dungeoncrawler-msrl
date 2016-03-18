package compsci.finalproject.level;


import java.util.*;
public class LevelBuilder
{
    public static Level build(int difficulty)
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
        int width = (int)(((Math.random() / 2) + 0.75) * dimBase);
        int height = (int)(((Math.random() / 2) + 0.75) * dimBase);
        assert(width > 0);
        assert(height > 0);
        short[][] levelMap = new short[width][height];
        ArrayList<Position> roomsTopLeft = new ArrayList<Position>();
        ArrayList<Position> roomsBottomRight = new ArrayList<Position>();
        // Pass 1
        {
            ArrayList<Position> minHeights = new ArrayList<Position>();
            minHeights.add(new Position(0, 0));
            int atCol = 0;
            while (atCol < width)
            {
                int atRow = 0;
                double rand = Math.random();

                int rRows = 0;
                int rCols = 0;
                if (rand < 0.1) // Small room
                {
                    // Minimum dimension is 6x6 (4x4), due to shrinkage
                    // Maximum dimension is 12x12 (10x10)
                    rRows = (int)(Math.random() * 7 + 6);
                    rCols = (int)(Math.random() * 7 + 6);
                }
                else if (rand > 0.95) // Large room
                {
                    // Minimum dimension is 22x22 (20x20)
                    // Maximum dimension is 32x32 (30x30)
                    rRows = (int)(Math.random() * 11 + 22);
                    rCols = (int)(Math.random() * 11 + 22);
                }
                else // Normal room
                {
                    // Minimum dimension is 12x12 (10x10)
                    // Maximum dimension is 22x22 (20x20)
                    rRows = (int)(Math.random() * 11 + 12);
                    rCols = (int)(Math.random() * 11 + 12);
                }

                if (atRow + rRows > height)
                {
                    int overshoot = height - atRow;
                    rRows -= overshoot;
                    assert(rRows != 0);
                }

                if (atCol + rCols > width)
                {
                    int overshoot = width - atCol;
                    rCols -= overshoot;
                    assert(rCols != 0);
                }
                roomsTopLeft.add(new Position(atRow, atCol));
                atRow += rRows;
                sortedInsert(minHeights, new Position(atRow, atCol));
                atCol += rCols;
                roomsBottomRight.add(new Position(atRow, atCol));
            }
        }
        
        
        int spawnX = (int)(Math.random() * width);
        int spawnY = (int)(Math.random() * height);

        return null;
    }

    private static void sortedInsert(ArrayList<Position> arr, Position pos)
    {
        // Precondition
        for (int i = 0; i < arr.size(); i++)
        {
            Position pAtI = arr.get(i);
            if (pAtI.getRow() > pos.getRow())
            {
                arr.add(i, pos); // Add before pAtI
            }
            else if (pAtI.getRow() == pos.getRow())
            {
                if (pAtI.getCol() > pos.getCol())
                {
                    arr.add(i, pos); // Add before pAtI
                }
                else if (pAtI.getCol() < pos.getCol())
                {
                    arr.add(i + 1, pos); // Add after pAtI
                }
                else
                {
                    assert(false);
                }
            }
            else // pAtI.getRow() < pos.getRow()
            {
                continue;
            }
        }
    }
}
