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
    private int pageNum, width, height;;
    private boolean isVisible;

    public HelpPanel() {
        manualImages = new HashMap<Integer, BufferedImage>();
        pageNum = 1;
        isVisible = false;

        try {
            for (int i = 1; i < 9; i++) {
                manualImages.put(i, ImageIO.read(HelpPanel.class.getResource("/Manual/pg" + i + ".png")));
            }
        }
        catch(Exception e) {
            System.out.println("Error in help panel");
        }
    }
//(int)(getWidth()), (int)(getHeight()), (int)(getWidth()), (int)(getHeight())
    public void paint(Graphics g, HashMap<String, BufferedImage> icons, int w, int h) {
        setWH(w, h);
        if(isVisible) {
            drawManual(g, icons);
        }
    }

    public void drawManual(Graphics g, HashMap<String, BufferedImage> icons) {
        g.setColor(new Color(0, 0, 0, 153));
        g.fillRect((int)(getWidth()/17.944), (int)(getHeight()/10.093), (int)(getWidth()/1.126), (int)(getHeight()/1.249));

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