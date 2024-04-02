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
    //erica is shrot
    public CascadiaPanel()
    {
        help = new HelpPanel();
        player = new PlayerPanel();
        start = new MainMenuPanel();
        icons = new HashMap<String, BufferedImage>();
        game = new Game();

        try
        {
            icons.add("background", ImageIO.read(CascadiaPanel.class.getResource("/Images/backround.png")));
            icons.add("open", ImageIO.read(CascadiaPanel.class.getResource("/Images/open icon.png")));
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
