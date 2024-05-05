import java.awt.*;
import javax.swing.*;
import java.util.HashMap;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
public class HelpPanel extends JPanel
{
    private HashMap<Integer, BufferedImage> manualImages;
    private int pageNum, width, height;;
    private boolean isVisible;

    public HelpPanel() {
        manualImages = new HashMap<Integer, BufferedImage>();
        pageNum = 1;
        isVisible = false;

        try {
            for (int i = 1; i < 11; i++) {
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
        switch(pageNum) {
            case 1 : {
                g.drawImage(manualImages.get(1), 0, 0, getWidth(), getHeight(), null);
                break;
            }
            case 2: {
                g.drawImage(manualImages.get(2), (int)(getWidth()/5.12), (int)(getHeight()/3.224), (int)(getWidth()/1.642), (int)(getHeight()/2.634), null);
                break;
            }
            case 3: {
                g.drawImage(manualImages.get(3), (int)(getWidth()/4.486), (int)(getHeight()/4.909), (int)(getWidth()/1.806), (int)(getHeight()/1.69), null);
                break;
            }
            case 4: {
                g.drawImage(manualImages.get(4), (int)(getWidth()/4.518), (int)(getHeight()/4.909), (int)(getWidth()/1.796), (int)(getHeight()/1.69), null);
                break;
            }
            case 5: {
                g.drawImage(manualImages.get(5), (int)(getWidth()/5.598), (int)(getHeight()/5.51), (int)(getWidth()/1.557), (int)(getHeight()/1.57), null);
                break;
            }
            case 6: {
                g.drawImage(manualImages.get(6), (int)(getWidth()/4.539), (int)(getHeight()/4.576), (int)(getWidth()/1.791), (int)(getHeight()/1.779), null);
                break;
            }
            case 7: {
                g.drawImage(manualImages.get(7), (int)(getWidth()/5.455), (int)(getHeight()/3.803), (int)(getWidth()/1.58), (int)(getHeight()/2.114), null);
                break;
            }
            case 8: {
                g.drawImage(manualImages.get(8), (int)(getWidth()/4.324), (int)(getHeight()/4.481), (int)(getWidth()/1.862), (int)(getHeight()/1.809), null);
                break;
            }
            case 9: {
                g.drawImage(manualImages.get(9), (int)(getWidth()/6.019), (int)(getHeight()/3.234), (int)(getWidth()/1.499), (int)(getHeight()/2.628), null);
                break;
            }
            case 10: {
                g.drawImage(manualImages.get(10), (int)(getWidth()/5.908), (int)(getHeight()/4.954), (int)(getWidth()/1.513), (int)(getHeight()/1.68), null);
                break;
            }
        }
        if(pageNum>=2) {
            g.drawImage(icons.get("left"), (int)(getWidth()/11.034), (int)(getHeight()/2.416), (int)(getWidth()/45.714), (int)(getHeight()/5.934), null);
        }
        if(pageNum<=9) {
            g.drawImage(icons.get("right"), (int)(getWidth()/1.141), (int)(getHeight()/2.416), (int)(getWidth()/45.714), (int)(getHeight()/5.934), null);
        }
        g.drawImage(icons.get("exit"), (int)(getWidth()/14.769), (int)(getHeight()/8.12), (int)(getWidth()/21.818), (int)(getHeight()/12.273), null);
    }

    public void pageLeft() {
        if(pageNum>=2) {
            pageNum--;
        }
    }
    public void pageRight() {
        if(pageNum<=9) {
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

    public void mouseClicked(int x, int y, int w, int h)
    {
        setWH(w, h);
        if(isVisible) {
            //g.drawImage(icons.get("left"), (int)(getWidth()/11.034), (int)(getHeight()/2.416), (int)(getWidth()/45.714), (int)(getHeight()/5.934), null);
            if(x>=(int)(getWidth()/11.034) && x<=(int)(getWidth()/11.034)+(int)(getWidth()/45.714) && y>=(int)(getHeight()/2.416) && y<=(int)(getHeight()/2.416)+(int)(getHeight()/5.934)) {
                pageLeft();
            }
            else if(x>=(int)(getWidth()/1.141) && x<=(int)(getWidth()/1.141)+(int)(getWidth()/45.714) && y>=(int)(getHeight()/2.416) && y<=(int)(getHeight()/2.416)+(int)(getHeight()/5.934)) {
                pageRight();
            }//        g.drawImage(icons.get("exit"), (int)(getWidth()/14.769), (int)(getHeight()/8.12), (int)(getWidth()/21.818), (int)(getHeight()/12.273), null);
            else if(x>=(int)(getWidth()/14.769) && x<=(int)(getWidth()/14.769)+(int)(getWidth()/21.818) && y>=(int)(getWidth()/14.769) && y<=(int)(getWidth()/14.769)+(int)(getHeight()/12.273)) {
                isVisible = false;
            }
            repaint();
        }
    }

}