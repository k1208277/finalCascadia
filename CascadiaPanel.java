import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
public class CascadiaPanel extends JPanel implements MouseListener{
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
            icons.put("background", ImageIO.read(CascadiaPanel.class.getResource("/Images/background.png")));
            icons.put("open", ImageIO.read(CascadiaPanel.class.getResource("/Images/open icon.png")));
            icons.put("title", ImageIO.read(CascadiaPanel.class.getResource("/Images/title.png")));
            icons.put("person", ImageIO.read(CascadiaPanel.class.getResource("/Images/person.png")));
        }
        catch(Exception e)
        {
            System.out.println("Error in CascadiaPanel");
        }


    }

//g.drawImage(icons.get(""), (int)(getWidth()), (int)(getHeight()), (int)(getWidth()), (int)(getHeight()), null);
    public void paint(Graphics g)
    {
        g.drawImage(icons.get("background"), 0, 0, getWidth(), getHeight(), null);
        int s = game.getGameState();
        //System.out.println(s);
        switch(s) {
            case 0: {
                start.setVisible(true);
                start.paint(g, icons, 0, getWidth(), getHeight());
                break;
            }
            case 1: {
                start.setVisible(true);
                start.paint(g, icons, 1, getWidth(), getHeight());
                break;
            }
        }
    }

    public void mouseClicked(MouseEvent e) {    }
    public void mouseEntered(MouseEvent e) {    }
    public void mouseExited(MouseEvent e) {    }
    public void mouseReleased(MouseEvent e) {   }
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        switch(game.getGameState()) {
            case 0 : {
                if(start.stateChangeClick(x, y)) {
                    game.setGameState(1);
                }
                repaint();
                break;
            }
            case 1 : {
                int pat = start.playerAmountClick(x, y);
                if(pat>0) {
                    game.setNumOfPlayers(pat);

                    game.setGameState(2);
                    repaint();
                }
                break;
            }
        }
    }
}
