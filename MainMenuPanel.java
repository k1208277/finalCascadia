import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.image.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
public class MainMenuPanel extends JPanel implements MouseListener
{
    private boolean isVisible;
    private int numPlayers, width, height;
    private HelpPanel manual;

    public MainMenuPanel() {
        isVisible = false;
        numPlayers = 3;
        manual = new HelpPanel();
    }
//g.drawImage(icons.get(""), (int)(getWidth()), (int)(getHeight()), (int)(getWidth()), (int)(getHeight()), null);
    public void paint(Graphics g, HashMap<String, BufferedImage> icons, int state, int width, int height) {
        setWH(width, height);
        if(isVisible) {
            if(state == 0) {
                drawBackround(g, icons);
            }
            else if(state==1) {
                drawPlayerNumberOptions(g, icons);
            }
            drawRules(g, icons);
        }
    }
    public void drawBackround(Graphics g, HashMap<String, BufferedImage> icons) {
        g.drawImage(icons.get("title"), (int)(getWidth()/7.59), (int)(getHeight()/3.059), (int)(getWidth()/1.36), (int)(getHeight()/6.667), null);
        g.setColor(new Color(0, 0, 0, 127));
        g.fillRect((int)(getWidth()/3.018), (int)(getHeight()/1.859), (int)(getWidth()/2.967), (int)(getHeight()/8.78));
        g.fillRect((int)(getWidth()/3.018), (int)(getHeight()/1.438), (int)(getWidth()/2.967), (int)(getHeight()/8.78));

        g.setColor(Color.white);
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke((int)(getHeight()/270)));
        g2.drawRect((int)(getWidth()/3.018), (int)(getHeight()/1.859), (int)(getWidth()/2.967), (int)(getHeight()/8.78));
        g2.drawRect((int)(getWidth()/3.018), (int)(getHeight()/1.438), (int)(getWidth()/2.967), (int)(getHeight()/8.78));

        g.setFont(new Font("main", 1, (int)(getHeight()/13.824)));
        //System.out.println((int)(getHeight()/13.824));
        g.drawString("New Game", (int)(getWidth()/2.485), (int)(getHeight()/1.62));
    }
    public void drawRules(Graphics g, HashMap<String, BufferedImage> icons) {
        if(manual.isVisible()) {
            manual.paint(g, icons);
        }
    }
    public void drawPlayerNumberOptions(Graphics g, HashMap<String, BufferedImage> icons) {

    }
    public void setVisible(boolean b) {
        isVisible = b;
    }
    public boolean isVisible() {
        return isVisible;
    }
    public int getNumPlayers() {
        return numPlayers;
    }
    public void setWH(int w, int h) {width = w; height = h;}
    public int getWidth() {return width;}
    public int getHeight() {return height;}
    public void mouseClicked(MouseEvent e) {    }
    public void mouseEntered(MouseEvent e) {    }
    public void mouseExited(MouseEvent e) {    }
    public void mouseReleased(MouseEvent e) {   }
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

    }

}