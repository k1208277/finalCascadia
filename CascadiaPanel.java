import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
public class CascadiaPanel extends JPanel {
    private HelpPanel help;
    private PlayerPanel player;
    private MainMenuPanel start;
    private HashMap<String, BufferedImage> icons;
    private Game game;
    private boolean gameStart, roundStart;

    public CascadiaPanel()
    {
        help = new HelpPanel();
        player = new PlayerPanel();
        start = new MainMenuPanel();
        icons = new HashMap<String, BufferedImage>();
        game = new Game();

        try
        {

        }
        catch(Exception e)
        {
            System.out.println("Error in CascadiaPanel");
        }
        play();

    }
    public void play()
    {

    }

    public void paint(Graphics g)
    {

    }
}
