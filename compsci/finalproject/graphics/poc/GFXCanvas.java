package compsci.finalproject.graphics.poc;

import javax.swing.*;
import java.awt.*;
import java.io.*;


import java.awt.image.*;
import java.awt.geom.*;
import javax.imageio.*;

/**
 * Proof of concept Canvas-like object for rendering
 * 
 * @author Michael Shuen, Ryan Later
 * @version 3/10/2016
 */
class GFXCanvas extends JPanel // NOT PUBLIC
{
    private static String ASSETS_FOLDER = "textures";
    public GFXCanvas()
    {
        super();
    }
    
    public void setPreferredSize(Dimension d)
    {
        super.setPreferredSize(d);
        //data = new byte[d.width][d.height];
    }
    
    public boolean isOpaque()
    {
        return true;
    }
    
    protected void paintComponent(Graphics orig)
    {
        Graphics gc = orig.create();
        Graphics2D g = (Graphics2D)gc;
        AffineTransform origTransform = g.getTransform();
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.translate(getWidth() * 0.1 , getHeight() * 0.9); // Move origin to 1/10 over, 9/10 down
        g.shear(-1, 0);
        /*
        g.setColor(Color.BLUE);
        g.drawRect(50, 50, 50, 50);
        WritableRaster randImgData = Raster.createInterleavedRaster(
            DataBuffer.TYPE_BYTE,
            250,
            250,
            4,
            null
        );
        int[] data = new int[250 * 250 * 4];
        for (int i = 0; i < data.length; i++)
            data[i] = (int)((2 * Math.random() - 1) * Integer.MAX_VALUE);
        randImgData.setPixels(0, 0, 250, 250, data);
        BufferedImage b = new BufferedImage(250, 250, BufferedImage.TYPE_4BYTE_ABGR);
        b.setData(randImgData);
        g.drawImage(b, 100, 100, null);
        */
        for (int i = 0; i < 20; i++)
        {
            for (int j = 0; j < 20; j++)
            {
                g.setColor(new Color((float)Math.random(), (float)Math.random(), (float)Math.random()));
                g.fillRect(i * 50, j * -50, 50, 50);
            }
        }
        try
        {
            drawTile(g, ImageIO.read(new File(ASSETS_FOLDER, "POC.png")), 360, 360);
            drawTile(g, ImageIO.read(new File(ASSETS_FOLDER, "Hi.png")), 350, 350);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        g.setTransform(origTransform);
        g.dispose(); // This frees associated system resources, preventing a memory leak.
    }
    
    public void drawTile(Graphics g, Image img, double tileX, double tileY)
    {
        g.drawImage(img, (int)(tileX * 1), (int)(tileY * -1), null);
    }
    
    //public void drawObject(Drawable obj, 
}
