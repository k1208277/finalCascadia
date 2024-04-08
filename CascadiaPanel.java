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
    private boolean tileClicked, tokenClicked;
    private ArrayList<Color> colors;
    //erica is shrot
    public CascadiaPanel()
    {
        help = new HelpPanel();
        player = new PlayerPanel();
        start = new MainMenuPanel();
        icons = new HashMap<String, BufferedImage>();
        game = new Game();
        colors = new ArrayList<Color>();
        colors.add(new Color(255, 243, 188));
        colors.add(new Color(154, 225, 228));
        colors.add(new Color(255, 144, 173));
        colors.add(new Color(144, 219, 176));

        try
        {
            icons.put("background", ImageIO.read(CascadiaPanel.class.getResource("/Images/background.png")));
            icons.put("open", ImageIO.read(CascadiaPanel.class.getResource("/Images/open icon.png")));
            icons.put("title", ImageIO.read(CascadiaPanel.class.getResource("/Images/title.png")));
            icons.put("person", ImageIO.read(CascadiaPanel.class.getResource("/Images/person.png")));
            icons.put("right", ImageIO.read(CascadiaPanel.class.getResource("/Images/right.png")));
            icons.put("left", ImageIO.read(CascadiaPanel.class.getResource("/Images/left.png")));
            icons.put("exit", ImageIO.read(CascadiaPanel.class.getResource("/Images/exit.png")));
        }
        catch(Exception e)
        {
            System.out.println("Error in CascadiaPanel");
        }

        addMouseListener(this);
    }

//g.drawImage(icons.get(""), (int)(getWidth()), (int)(getHeight()), (int)(getWidth()), (int)(getHeight()), null);
    public void paint(Graphics g)
    {
        g.drawImage(icons.get("background"), 0, 0, getWidth(), getHeight(), null);
        int s = game.getGameState();
        //System.out.println(s);
        switch(s) {
            //main menu
            case 0: {
                start.setVisible(true);
                start.paint(g, icons, 0, getWidth(), getHeight());
                break;
            }
            //choose player amount
            case 1: {
                start.setVisible(true);
                start.paint(g, icons, 1, getWidth(), getHeight());
                break;
            }
            //main layout - player chooses options
            case 2 : {
                drawPrompt(g);
                drawOptions(g);
                drawPlayerBoard(g);
                drawPlayerIcons(g);
                drawScoringCards(g);
                drawShiftButtons(g);
                drawButtons(g);
            }
        }
    }

    public void drawPrompt(Graphics g) {
        g.setColor(new Color(0, 0, 0, 102));
        g.fillRect((int)(getWidth()/6.76), (int)(getHeight()/49.09), (int)(getWidth()/1.692), (int)(getHeight()/13.17));
        g.setFont(new Font("he", 1, 24));
        g.setColor(colors.get(game.getPlayerNum()));
        g.drawString("Player "+game.getPlayerNum()+"'s turn!", (int)(getWidth()/2.659), (int)(getHeight()/19.286));

    }
    public void drawOptions(Graphics g) {

    }
    public void drawPlayerBoard(Graphics g) {

    }
    public void drawPlayerIcons(Graphics g) {

    }
    public void drawScoringCards(Graphics g) {

    }
    public void drawShiftButtons(Graphics g) {

    }
    public void shift(int i){}
    public void drawButtons(Graphics g) {}
    public void drawHighlights(Graphics g) {}

    public void mouseClicked(MouseEvent e) {    }
    public void mouseEntered(MouseEvent e) {    }
    public void mouseExited(MouseEvent e) {    }
    public void mouseReleased(MouseEvent e) {   }
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        switch(game.getGameState()) {
            case 0 :
            {
                if(start.stateChangeClick(x, y)) {
                    game.setGameState(1);
                }
                repaint();
                break;
            }
            case 1 :
            {
                int pat = start.playerAmountClick(x, y);
                if(pat>0) {
                    game.setNumOfPlayers(pat);
                    game.setGameState(2);
                    repaint();
                }
                break;
            }
            case 13 :
            {
                while () //player has not clicked on a tile
                {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        System.out.println("Error in threat.sleep method = "+e.getMessage());
                    }
                }
            }
        }
    }
}
