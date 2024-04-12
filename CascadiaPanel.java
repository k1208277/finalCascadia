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
    private HashMap<Integer, BufferedImage> tokenImages;
    private int gameState;
    private boolean gameStart, roundStart;
    private boolean tileClicked, tokenClicked, twoPlayerCLicked, threePlayerClicked, fourPlayerClicked, usePineConesClicked, chooseTileTokenClicked, clearTokenClicked, okClicked;
    private ArrayList<Color> colors;
    private Tile tileChosen; // only for choosing tile to place to pass to player board to addTile()
    private Token tokenChosen; // only for choosing token to place to pass to player board to set token to that tile;
    private Game game;
    //erica is shrot
    public CascadiaPanel(Game game)
    {
        this.game = game;
        help = new HelpPanel();
        player = new PlayerPanel();
        start = new MainMenuPanel();
        icons = new HashMap<String, BufferedImage>();
        tokenImages = new HashMap<Integer, BufferedImage>();
        //game = new Game();
        colors = new ArrayList<Color>();
        colors.add(new Color(255, 243, 188));
        colors.add(new Color(154, 225, 228));
        colors.add(new Color(255, 144, 173));
        colors.add(new Color(144, 219, 176));

        //flags or indicators of whether a button has been clicked to send to wait methods
        tileClicked = false;
        tokenClicked = false;
        twoPlayerCLicked = false;
        threePlayerClicked = false;
        fourPlayerClicked = false;
        usePineConesClicked = false;
        chooseTileTokenClicked = false;
        clearTokenClicked = false;

        tileChosen = null;
        tokenChosen = null;

        try
        {
            icons.put("background", ImageIO.read(CascadiaPanel.class.getResource("/Images/background.png")));
            icons.put("open", ImageIO.read(CascadiaPanel.class.getResource("/Images/open icon.png")));
            icons.put("title", ImageIO.read(CascadiaPanel.class.getResource("/Images/title.png")));
            icons.put("person", ImageIO.read(CascadiaPanel.class.getResource("/Images/person.png")));
            icons.put("right", ImageIO.read(CascadiaPanel.class.getResource("/Images/right.png")));
            icons.put("left", ImageIO.read(CascadiaPanel.class.getResource("/Images/left.png")));
            icons.put("exit", ImageIO.read(CascadiaPanel.class.getResource("/Images/exit.png")));
            //Elk = 1
            //Salmon = 2
            //Hawk = 3
            //Fox = 4
            //Bear = 5
            tokenImages.put(1, ImageIO.read(CascadiaPanel.class.getResource("/Tokens/elk token.png")));
            tokenImages.put(2, ImageIO.read(CascadiaPanel.class.getResource("/Tokens/fish token.png")));
            tokenImages.put(3, ImageIO.read(CascadiaPanel.class.getResource("/Tokens/hawk token.png")));
            tokenImages.put(4, ImageIO.read(CascadiaPanel.class.getResource("/Tokens/fox token.png")));
            tokenImages.put(5, ImageIO.read(CascadiaPanel.class.getResource("/Tokens/bear token.png")));
        }
        catch(Exception e)
        {
            System.out.println("Error in CascadiaPanel");
        }

        addMouseListener(this);
    }

    public int getGameState()
    {
        return gameState;
    }
    public void setGameState(int gs)
    {
        gameState = gs;
    }

    public Tile getTileChosen()
    {
        return tileChosen;
    }
    public void setTileChosen(Tile tile)
    {
        tileChosen = tile;
    }

    public Token getTokenChosen()
    {
        return tokenChosen;
    }
    public void setTokenChosen(Token token)
    {
        tokenChosen = token;
    }


    //g.drawImage(icons.get(""), (int)(getWidth()), (int)(getHeight()), (int)(getWidth()), (int)(getHeight()), null);
    public void paint(Graphics g)
    {
        g.drawImage(icons.get("background"), 0, 0, getWidth(), getHeight(), null);
        int s = getGameState();
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
                drawPrompt(g, "Choose a tile and token!");
                drawOptions(g);
                drawPlayerBoard(g);
                drawPlayerIcons(g);
                drawScoringCards(g);
                drawShiftButtons(g);
                drawButtons(g);
            }
        }
    }

    public void drawPrompt(Graphics g, String prompt) {
        g.setColor(new Color(0, 0, 0, 102));
        g.fillRect((int)(getWidth()/6.76), (int)(getHeight()/49.09), (int)(getWidth()/1.692), (int)(getHeight()/13.17));
        g.setFont(new Font("he", 1, (int)(getHeight()/28.8)));
        //g.setColor(colors.get(game.getPlayerNum()));
        g.drawString("Player "+game.getPlayerNum()+"'s turn!", (int)(getWidth()/2.659), (int)(getHeight()/19.286));
        int x = 0; int y = 0;
        switch(getGameState()) {
            case 2 : {
                x = (int)(getWidth()/2.954);
                y =  (int)(getHeight()/10.693);
                break;
            }
        }
        g.drawString(prompt, x, y);
    }
    public void drawOptions(Graphics g) {
        g.setColor(new Color(0, 0, 0, 102));
        g.fillRect((int)(getWidth()/6.784), (int)(getHeight()/1.34), (int)(getWidth()/1.69), (int)(getHeight()/4.337));
        drawHighlights(g);
        for(int i = 0; i<game.getAvailableTiles().length; i++) {
            g.drawImage(game.getAvailableTiles()[i].getImage(), (int)(getWidth()/6.316)+i*(int)(getWidth()/14.884), (int)(getHeight()/1.289), (int)(getWidth()/19.01), (int)(getHeight()/9.231), null);
            g.drawImage(tokenImages.get(game.getAvailableTokens()[i].getAnimal()), (int)(getWidth()/6.038)+i*(getWidth()/15), (int)(getHeight()/1.12), (int)(getWidth()/25.946), (int)(getHeight()/14.595), null);
        }
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
    public void drawHighlights(Graphics g) {
        switch(getGameState())  {
            case 2 : {
                //g.setColor(colors.get(game.getPlayerNum()));
                g.setColor(Color.yellow);
                /*
                int[] xC = new int[6];
                xC[0] = (int)(getWidth()/5.408);
                xC[1] = (int)(getWidth()/6.531);
                xC[2] = (int)(getWidth()/6.621);
                xC[3] = (int)(getWidth()/5.408);
                xC[4] = (int)(getWidth()/4.638);
                xC[5] = (int)(getWidth()/4.638);
                int[] yC = new int[6];
                yC[0] = (int)(getWidth()/1.306);
                yC[1] = (int)(getWidth()/1.253);
                yC[2] = (int)(getWidth()/1.159);
                yC[3] = (int)(getWidth()/1.117);
                yC[4] = (int)(getWidth()/1.253);
                yC[5] = (int)(getWidth()/1.159);
                g.fillPolygon(xC, yC, 6);
                 */
                for(int i = 0; i< 4; i++) {
                    g.fillOval((int)(getWidth()), (int)(getHeight()), (int)(getWidth()), (int)(getHeight()));
                }
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
        System.out.println(x +" " +y);
        switch(getGameState())
        {
            case 0 :
            {
                if(start.stateChangeClick(x, y)) {
                    setGameState(1);
                }
                repaint();
                break;
            }
            case 1 :
            {
                int pat = start.playerAmountClick(x, y);
                if(pat>0) {
                    if (pat == 2)
                        twoPlayerCLicked = true;
                    else if (pat == 3)
                        threePlayerClicked = true;
                    else if (pat == 4)
                        fourPlayerClicked = true;
                    game.setNumOfPlayers(pat);
                    setGameState(2);
                    repaint();
                    game.play();
                }
                break;
            }
            case 13 :
            {

            }
        }
    }

    public void waitForPlayerAmountClicked()
    {
        while (!twoPlayerCLicked && !threePlayerClicked && !fourPlayerClicked)
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Error in waitForPlayerAmount method = "+e.getMessage());
            }
        }
    }

    public void waitForChooseTileOrPineconeClicked()
    {
        while (!tileClicked && !usePineConesClicked) //player has not clicked on a tile
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Error in tile or pinecone wait method = "+e.getMessage());
            }
        }
    }

    public void waitForPinecone2options()
    {
        while (!chooseTileTokenClicked && !clearTokenClicked) //player has not clicked on a tile
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Error in pinecone options wait method = "+e.getMessage());
            }
        }
    }

    public void waitForTileClicked()
    {
        while (!tileClicked)
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Error in choose tile wait method = "+e.getMessage());
            }
        }
    }

    public void waitForTokenClicked()
    {
        while (!tokenClicked)
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Error in choose token wait method = "+e.getMessage());
            }
        }
    }

    public void waitForOkClicked()
    {
        while (!okClicked)
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Error in ok wait method = "+e.getMessage());
            }
        }
    }
}
