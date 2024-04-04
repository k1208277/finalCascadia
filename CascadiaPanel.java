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
        game = new Game(start.getNumPlayers());

        try
        {
            icons.put("background", ImageIO.read(CascadiaPanel.class.getResource("/Images/background.png")));
            icons.put("open", ImageIO.read(CascadiaPanel.class.getResource("/Images/open icon.png")));
            icons.put("title", ImageIO.read(CascadiaPanel.class.getResource("/Images/title.png")));
            icons.put("person", ImageIO.read(CascadiaPanel.class.getResource("/Images/person.png")));
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
//g.drawImage(icons.get(""), (int)(getWidth()), (int)(getHeight()), (int)(getWidth()), (int)(getHeight()), null);
    public void paint(Graphics g)
    {
        g.drawImage(icons.get("background"), 0, 0, getWidth(), getHeight(), null);
        System.out.println(game.getGameState());
        switch(game.getGameState()) {
            case 0: {
                start.setVisible(true);
                start.paint(g, icons, 0, getWidth(), getHeight());
            }
            case 1: {
                start.setVisible(true);
                start.paint(g, icons, 1, getWidth(), getHeight());
            }
        }
    }
}
