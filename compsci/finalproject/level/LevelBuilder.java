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
        // Pass 1
        {
            ArrayList<Position> minHeights = new ArrayList<Position>();
            minHeights.add(new Position(0, 0));
            int atRow = 0;
            while (atRow < height)
            {
                int atCol = 0;
                double rand = Math.random();
                if (rand < 0.1) // Small room
                {
                    // Minimum dimension is 6x6 (4x4), due to shrinkage
                    // Maximum dimension is 12x12 (10x10)
                }
                else if (rand > 0.95) // Large room
                {
                    // Minimum dimension is 22x22 (20x20)
                    // Maximum dimension is 32x32 (30x30)
                }
                else // Normal room
                {
                    // Minimum dimension is 12x12 (10x10)
                    // Maximum dimension is 22x22 (20x20)
                }
            }
        }
        
        
        int spawnX = (int)(Math.random() * width);
        int spawnY = (int)(Math.random() * height);
        
        return null;
    }
}
