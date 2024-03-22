
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PlayerPanel extends JPanel implements MouseListener
{
    private Player player;
    private boolean isVisible;

    public PlayerPanel() {
        player = new Player(null);
        isVisible = false;
    }

    public void paint(Graphics g) {
        if(isVisible) {

        }
    }
    public void drawBoard(Graphics g) {

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

    public void mouseClicked(MouseEvent e) {	}
    public void mouseEntered(MouseEvent e) {	}
    public void mouseExited(MouseEvent e) {		}
    public void mouseReleased(MouseEvent e) {	}
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
    }
}
