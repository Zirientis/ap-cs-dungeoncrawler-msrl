package compsci.finalproject.level;


/**
 * Represents a Level.
 * 
 * @author Michael Shuen, Ryan Later 
 * @version 3/13/2016
 */
public class Level
{
    private short[][] data;
    
    public Level(Room single)
    {
        data = single.getData();
    }

    // This expects levelMap to not be changed after calling.
    public Level(short[][] levelMap)
    {
        data = levelMap;
    }
}
