package compsci.finalproject.graphics.poc;

import java.awt.*;
import javax.swing.*;
import java.util.*;

/**
 * Proof of concept for using the Java Graphics API.
 * 
 * @author Michael Shuen, Ryan Later
 * @version 3/10/2016
 */
public class GFX
{
    private GFXCanvas canvas;
    public GFX()
    {
        // Needs to be on event thread!
        try
        {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    JFrame frame = new JFrame("Proof of Concept");
                    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    canvas = new GFXCanvas();
                    Dimension dim = new Dimension(1024, 768);
                    canvas.setPreferredSize(dim);
                    canvas.setMinimumSize(dim);
                    canvas.setMaximumSize(dim);
                    frame.setContentPane(canvas);
                    frame.pack();
                    frame.setVisible(true);
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Error setting up!");
        }
    }
    
    public static void main()
    {
        GFX g = new GFX();
    }
}
