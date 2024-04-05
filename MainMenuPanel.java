import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.image.*;
public class MainMenuPanel extends JPanel
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
        if(isVisible && !manual.isVisible()) {
            if(state == 0) {
                drawBackround(g, icons, state);
            }
            else {
                drawPlayerNumberOptions(g, icons, state);
            }
        }
        drawRules(g, icons);
    }
    public void drawBackround(Graphics g, HashMap<String, BufferedImage> icons, int state) {
        if(state == 0) {
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
            g.drawString("Help", (int)(getWidth()/2.167), (int)(getHeight()/1.292));
        }
    }
    public void drawRules(Graphics g, HashMap<String, BufferedImage> icons) {
        if(manual.isVisible()) {
            manual.paint(g, icons);
        }
    }
    public void drawPlayerNumberOptions(Graphics g, HashMap<String, BufferedImage> icons, int state) {
        if(state == 1) {
            System.out.println(state);
            g.setColor(new Color(0, 0, 0, 127));
            g.fillRect((int)(getWidth()/5.517), (int)(getHeight()/2), (int)(getWidth()/5.944), (int)(getHeight()/3.333));
            g.fillRect((int)(getWidth()/2.406), (int)(getHeight()/2), (int)(getWidth()/5.944), (int)(getHeight()/3.333));
            g.fillRect((int)(getWidth()/1.538), (int)(getHeight()/2), (int)(getWidth()/5.944), (int)(getHeight()/3.333));

            g.setColor(Color.WHITE);
            g.drawRect((int)(getWidth()/5.517), (int)(getHeight()/2), (int)(getWidth()/5.944), (int)(getHeight()/3.333));
            g.drawRect((int)(getWidth()/2.406), (int)(getHeight()/2), (int)(getWidth()/5.944), (int)(getHeight()/3.333));
            g.drawRect((int)(getWidth()/1.538), (int)(getHeight()/2), (int)(getWidth()/5.944), (int)(getHeight()/3.333));

            g.drawImage(icons.get("person"), (int)(getWidth()/4.812), (int)(getHeight()/1.662), (int)(getWidth()/21.33), (int)(getHeight()/10.485), null);
            g.drawImage(icons.get("person"), (int)(getWidth()/3.636), (int)(getHeight()/1.662), (int)(getWidth()/21.33), (int)(getHeight()/10.485), null);

            g.drawImage(icons.get("person"), (int)(getWidth()/2.259), (int)(getHeight()/1.812), (int)(getWidth()/21.33), (int)(getHeight()/10.485), null);
            g.drawImage(icons.get("person"), (int)(getWidth()/1.961), (int)(getHeight()/1.812), (int)(getWidth()/21.33), (int)(getHeight()/10.485), null);
            g.drawImage(icons.get("person"), (int)(getWidth()/2.1), (int)(getHeight()/1.494), (int)(getWidth()/21.33), (int)(getHeight()/10.485), null);

            g.drawImage(icons.get("person"), (int)(getWidth()/1.478), (int)(getHeight()/1.812), (int)(getWidth()/21.33), (int)(getHeight()/10.485), null);
            g.drawImage(icons.get("person"), (int)(getWidth()/1.345), (int)(getHeight()/1.812), (int)(getWidth()/21.33), (int)(getHeight()/10.485), null);
            g.drawImage(icons.get("person"), (int)(getWidth()/1.478), (int)(getHeight()/1.494), (int)(getWidth()/21.33), (int)(getHeight()/10.485), null);
            g.drawImage(icons.get("person"), (int)(getWidth()/1.345), (int)(getHeight()/1.494), (int)(getWidth()/21.33), (int)(getHeight()/10.485), null);

            g.setFont(new Font("extra", 1, (int)(getHeight()/8.13176)));
            g.drawString("Choose Player Amount", (int)(getWidth()/5.581), (int)(getHeight()/2.318));
        }
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

    public int playerAmountClick(int x, int y) {
        int i = 0;
        /*
        g.fillRect((int)(getWidth()/5.517), (int)(getHeight()/2), (int)(getWidth()/5.944), (int)(getHeight()/3.333));
            g.fillRect((int)(getWidth()/2.406), (int)(getHeight()/2), (int)(getWidth()/5.944), (int)(getHeight()/3.333));
            g.fillRect((int)(getWidth()/1.538), (int)(getHeight()/2), (int)(getWidth()/5.944), (int)(getHeight()/3.333));
         */
        if(y>=getHeight()/2 && y<= getHeight()/2+(int)(getHeight()/3.333) ) {
            if(x>=(int)(getWidth()/5.517) && x<=(int)(getWidth()/5.517)+(int)(getWidth()/5.944) ) {
                return 2;
            }
            else if(x>=(int)(getWidth()/2.406) && x<=(int)(getWidth()/2.406)+(int)(getWidth()/5.944)) {
                return 3;
            }
            else if(x>= (int)(getWidth()/1.538) && x<= (int)(getWidth()/1.538) +(int)(getWidth()/5.944)) {
                return 4;
            }
        }
        return i;
    }
    public boolean stateChangeClick(int x, int y) {
        /*
        g.fillRect((int)(getWidth()/3.018), (int)(getHeight()/1.859), (int)(getWidth()/2.967), (int)(getHeight()/8.78));
            g.fillRect((int)(getWidth()/3.018), (int)(getHeight()/1.438), (int)(getWidth()/2.967), (int)(getHeight()/8.78));

         */
        if(!manual.isVisible()) {
            if(x>=(int)(getWidth()/3.018) && x<= (int)(getWidth()/3.018)+(int)(getWidth()/2.967)) {
                if (y>=(int)(getHeight()/1.859) && y<=(int)(getHeight()/1.859)+(int)(getHeight()/8.78)) {
                    return true;
                }
                else if(y>=(int)(getHeight()/1.438) && y<=(int)(getHeight()/1.438) +(int)(getHeight()/8.78)) {
                    manual.setVisible(true);
                }
            }
        }
        return false;
    }

}