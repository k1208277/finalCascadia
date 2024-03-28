import java.awt.*;
import javax.swing.*;
import java.util.HashMap;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
public class HelpPanel extends JPanel implements MouseListener
{
    private HashMap<Integer, BufferedImage> manualImages;
    private int pageNum;
    private boolean isVisible;

    public HelpPanel() {
        manualImages = new HashMap<Integer, BufferedImage>();
        pageNum = 1;
        isVisible = false;

        try {

        }
        catch(Exception e) {
            System.out.println("Error in help panel");
        }
    }

    public void paint(Graphics g) {
        if(isVisible) {
            drawManual(g);
        }
    }

    public void drawManual(Graphics g) {


    }

    public void pageLeft() {
        if(pageNum>=2) {
            pageNum--;
        }
    }
    public void pageRight() {
        if(pageNum<=8) {
            pageNum++;
        }
    }
    public void setVisible(boolean b) {
        isVisible = b;
    }
    public boolean isVisible() {
        return isVisible;
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