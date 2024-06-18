package Utils;

import javax.swing.*;
import java.awt.*;

// Credits: https://tips4java.wordpress.com/2008/10/12/background-panel/
public class BasicBackgroundPanel extends JPanel
{
    private Image background;

    public BasicBackgroundPanel(Image background)
    {
        this.background = background;
        setLayout( new BorderLayout() );
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        //g.drawImage(background, 0, 0, null); // image full size
        int size = Math.max(getWidth(), getHeight());
        g.drawImage(background, 0, 0, size, size, null); // image scaled
    }
}
