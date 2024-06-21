package Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

// Credits: https://tips4java.wordpress.com/2008/10/12/background-panel/
public class BasicBackgroundPanel extends JPanel
{
    private Image background;

    public BasicBackgroundPanel(String imagePath)
    {
        setLayout( new BorderLayout() );
        this.setBackgroundImage(imagePath);
    }

    public BasicBackgroundPanel()
    {
        setLayout( new BorderLayout() );
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if (background != null) {
            int panelWidth = getWidth();
            int panelHeight = getHeight();

            int imageWidth = background.getWidth(this);
            int imageHeight = background.getHeight(this);

            double scaleX = (double) panelWidth / imageWidth;
            double scaleY = (double) panelHeight / imageHeight;

            // Use the smaller scale factor to maintain aspect ratio
            double scale = Math.min(scaleX, scaleY);

            int scaledWidth = (int) (scale * imageWidth);
            int scaledHeight = (int) (scale * imageHeight);

            // Center the image in the panel
            int x = (panelWidth - scaledWidth) / 2;
            int y = (panelHeight - scaledHeight) / 2;

            g.drawImage(background, x, y, scaledWidth, scaledHeight, this);
        }
    }

    public Image getBackgroundImage() {
        return background;
    }

    public void setBackgroundImage(String imagePath) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(imagePath);
        Image image = null;
        try {
            image = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("BasicBackgroundPanel::setBackgroundImage() -> Couldn't find " + imagePath + " in resources/");
        }
        this.background = image;
    }
}
