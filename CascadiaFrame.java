import java.awt.*;
import javax.swing.*;

public class CascadiaFrame extends JFrame
{
    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;
    private Game g;

    private CascadiaPanel mainGame;

    public CascadiaFrame(String framename)
    {
        super(framename);
        g = new Game();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH,HEIGHT);
        add(g.getPanel());
        setVisible(true);
    }
}