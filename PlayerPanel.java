import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PlayerPanel extends JPanel
{
    private Player player;
    private boolean isVisible;
    private int width, height;

    public PlayerPanel() {
        player = new Player(null);
        isVisible = false;
    }

    public void paint(Graphics g, HashMap<String, BufferedImage> icons, HashMap<Integer, BufferedImage> scoringCards, int w, int h) {
        setWH(w, h);
        if(isVisible) {
            drawOthers(g, icons, scoringCards);
            drawBoard(g, icons);
            drawShift(g, icons);
        }
    }
    public void drawBoard(Graphics g,  HashMap<String, BufferedImage> icons) {

    }
    public void drawShift(Graphics g,  HashMap<String, BufferedImage> icons) {
        g.setColor(Color.white);
        g.fillRect((int)(getWidth()/1.176), (int)(getHeight()/1.26), (int)(getWidth()/25.6), (int)(getHeight()/14.4));
        g.drawImage(icons.get("arrow"), (int)(getWidth()/1.176), (int)(getHeight()/1.401), (int)(getWidth()/25.6), (int)(getHeight()/14.4), null);
        int[] xp = {(int)(getWidth()/1.117), (int)(getWidth()/1.076), (int)(getWidth()/1.117)};
        int[] yp = {(int)(getHeight()/1.26), (int)(getHeight()/1.212), (int)(getHeight()/1.26)+(int)(getHeight()/14.4)};
        g.fillPolygon(xp, yp, 3);
        xp = new int[]{(int) (getWidth() / 1.176), (int) (getWidth() / 1.15), (int) (getWidth() / 1.124)};
        yp = new int[]{(int)(getHeight()/1.145), (int)(getHeight()/1.065), (int)(getHeight()/1.145)};
        g.fillPolygon(xp, yp, 3);
        xp = new int[]{(int) (getWidth() / 1.235), (int) (getWidth() / 1.184), (int) (getWidth() / 1.184)};
        yp = new int[]{(int)(getHeight()/1.212), (int)(getHeight()/1.26), (int)(getHeight()/1.26)+(int)(getHeight()/14.4)};
        g.fillPolygon(xp, yp, 3);
    }
    public void drawOthers(Graphics g,  HashMap<String, BufferedImage> icons, HashMap<Integer, BufferedImage> scoringCards) {
        g.setColor(new Color(0,0,0,102));
        g.fillRect((int)(getWidth()/1.328), (int)(getHeight()/49.091), (int)(getWidth()/4.286), (int)(getHeight()/1.044));
        g.fillRect((int)(getWidth()/83.478), (int)(getHeight()/49.091), (int)(getWidth()/8.24), (int)(getHeight()/1.044));
        g.setColor(Color.white);
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke((int)(getHeight()/270)));
        g2.drawRect((int)(getWidth()/41.739), (int)(getHeight()/30), (int)(getWidth()/10.105), (int)(getHeight()/17.143));
        g.setColor(Color.white);
        g.setFont(new Font("he", 1, (int)(getHeight()/28.8)));
        g.drawString("Exit", (int)(getWidth()/18.78), (int)(getHeight()/13.846));
        for(int i = 0; i<5; i++) {
            g.drawImage(scoringCards.get(i), (int)(getWidth()/41.739), (int)(getHeight()/10.286)+i*(int)(getHeight()/5.714), (int)(getWidth()/10.105), (int)(getHeight()/5.967), null);
        }
    }


    public void setPlayer(Player p) {
        player = p;
    }
    public Player getPlayer() {
        return player;
    }
    public void setVisible(boolean b) {
        isVisible = b;
    }
    public boolean isVisible() {
        return isVisible;
    }
    public void setWH(int w, int h) {
        width = w;
        height = h;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    public void mouseClicked(int x, int y) {

    }

}
