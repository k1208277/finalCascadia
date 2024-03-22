import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainMenuPanel extends JPanel implements MouseListener
{
    private boolean isVisible;
    private int numPlayers;
    private HelpPanel manual;

    public MainMenuPanel() {
        isVisible = false;
        numPlayers = 0;
        manual = new HelpPanel();
    }

    public void paint(Graphics g) {
        if(isVisible) {
            drawBackround(g);
            drawRules(g);
            drawPlayerNumberOptions(g);
        }
    }
    public void drawBackround(Graphics g) {

    }
    public void drawRules(Graphics g) {
        if(manual.isVisible()) {

        }
    }
    public void drawPlayerNumberOptions(Graphics g) {

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

    public void mouseClicked(MouseEvent e) {	}
    public void mouseEntered(MouseEvent e) {	}
    public void mouseExited(MouseEvent e) {		}
    public void mouseReleased(MouseEvent e) {	}
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
    }

}