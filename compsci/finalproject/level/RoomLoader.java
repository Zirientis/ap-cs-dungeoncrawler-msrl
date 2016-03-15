package compsci.finalproject.level;


import java.io.*;
/**
 * Loads rooms from the datastore into the application.
 * 
 * @author Michael Shuen, Ryan Later
 * @version 3/11/2016
 */
public class RoomLoader
{
    public static Room[] loadAllRooms(File dir)
    {
        assert(dir.isDirectory());
        File[] roomFiles = dir.listFiles();
        Room[] rooms = new Room[roomFiles.length];
        // TODO: Should use constructor with FilenameFilter
        // to filter on file extension (.rmf).
        for (int i = 0; i < roomFiles.length; i++)
        {
            try
            {
                rooms[i] = new Room(roomFiles[i]);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
                assert(false);
            }
        }
        return rooms;
    }
}
