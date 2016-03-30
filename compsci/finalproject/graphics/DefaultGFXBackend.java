package compsci.finalproject.graphics;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.awt.image.*;
import java.awt.geom.*;
import javax.imageio.*;
//import compsci.finalproject.graphics.poc.GFXCanvas;

public class DefaultGFXBackend extends AbstractGFXBackend
{
    private GFXCanvas canvas;
    public void showTitleScreen()
    {
        try
        {
            SwingUtilities.invokeAndWait(new Runnable() {
                    public void run() {
                        JFrame frame = new JFrame("Game");
                        frame.setResizable(false);
                        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                        JPanel wind = new JPanel(new BorderLayout());
                        if (canvas == null)
                            canvas = new GFXCanvas();
                        Dimension dim = new Dimension(1024, 768);
                        canvas.setPreferredSize(dim);
                        canvas.setMinimumSize(dim);
                        canvas.setMaximumSize(dim);
                        wind.add(canvas, BorderLayout.CENTER);
                        JPanel buttonPanel = new JPanel(new BorderLayout());
                        JButton startSinglePlayer = new JButton("Singleplayer");
                        JButton startMultiPlayer = new JButton("Multiplayer");
                        JButton optionsButton = new JButton("Options...");
                        buttonPanel.add(startSinglePlayer, BorderLayout.NORTH);
                        buttonPanel.add(startMultiPlayer, BorderLayout.CENTER);
                        buttonPanel.add(optionsButton, BorderLayout.SOUTH);
                        wind.add(buttonPanel, BorderLayout.SOUTH);
                        frame.setContentPane(wind);
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
        //System.out.println("Hi!");
    }
    
    private class GFXCanvas extends JPanel
    {
        private static final String ASSETS_FOLDER = "textures";
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
            //g.translate(getWidth() * 0.1 , getHeight() * 0.9); // Move origin to 1/10 over, 9/10 down
            //g.translate(0, getHeight());
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
            for (int i = 0; i < 20; i++)
            {
                for (int j = 0; j < 20; j++)
                {
                    g.setColor(new Color((float)Math.random(), (float)Math.random(), (float)Math.random()));
                    g.fillRect(i * 50, j * -50, 50, 50);
                }
            }
            */
            try
            {
                //drawTile(g, ImageIO.read(new File(ASSETS_FOLDER, "POC.png")), 360, 360);
                //drawTile(g, ImageIO.read(new File(ASSETS_FOLDER, "Hi.png")), 350, 350);
                BufferedImage img = ImageIO.read(new File(ASSETS_FOLDER, "BasicLogo.png"));
                drawTile(g, img, (getWidth() - img.getWidth()) / 2, (getHeight() - img.getHeight()) / 2);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            g.shear(-1, 0);
            g.setTransform(origTransform);
            g.dispose(); // This frees associated system resources, preventing a memory leak.
        }

        public void drawTile(Graphics g, Image img, double tileX, double tileY)
        {
            g.drawImage(img, (int)(tileX * 1), (int)(tileY * 1), null);
        }

        //public void drawObject(Drawable obj, 
    }

}
