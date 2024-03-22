import java.awt.*;
import javax.swing.*;

public class CascadiaFrame extends JFrame
{
    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;

    private CascadiaPanel mainGame;

    public CascadiaFrame(String framename)
    {
        super(framename);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH,HEIGHT);
        add(new CascadiaPanel());
        setVisible(true);
    }
}