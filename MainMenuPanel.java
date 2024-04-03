import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.image.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
public class MainMenuPanel extends JPanel implements MouseListener
{
    private boolean isVisible;
    private int numPlayers;
    private HelpPanel manual;

    public MainMenuPanel() {
        isVisible = false;
        numPlayers = 3;
        manual = new HelpPanel();
    }
//g.drawImage(icons.get(""), (int)(getWidth()), (int)(getHeight()), (int)(getWidth()), (int)(getHeight()), null);
    public void paint(Graphics g, HashMap<String, BufferedImage> icons, int state) {
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
        g.setColor(Color.white);
        //g.drawRect((int)(getWidth()/3.018), (int)(getHeight()/1.859), (int)(getWidth()/2.967), (int)(getHeight()));
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

    public void mouseClicked(MouseEvent e) {    }
    public void mouseEntered(MouseEvent e) {    }
    public void mouseExited(MouseEvent e) {    }
    public void mouseReleased(MouseEvent e) {   }
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

    }

}