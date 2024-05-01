import java.awt.*;
import javax.swing.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PlayerPanel extends JPanel
{
    private Player player;
    private boolean isVisible, endGame, lastPlayer;
    private int width, height, shiftX, shiftY;

    public PlayerPanel() {
        player = new Player(null);
        isVisible = false;
    }

    public void paint(Graphics g, HashMap<String, BufferedImage> icons, HashMap<Integer, BufferedImage> scoringCards, HashMap<Integer, BufferedImage> tokenImages, ArrayList<Player> players, int w, int h) {
        setWH(w, h);
        if(isVisible) {
            drawBoard(g, icons, tokenImages);
            drawOthers(g, icons, scoringCards, tokenImages, players);
            drawShift(g, icons);
        }
    }
    public void drawBoard(Graphics g,  HashMap<String, BufferedImage> icons, HashMap<Integer, BufferedImage> tokenImages) {
        player.getBoard().updateTileCoords((int)(getWidth()/2.46)+shiftX*(int)(getWidth()/13.714), (int)(getHeight()/4.5)+shiftY*(int)(getHeight()/6.545), getWidth(), getHeight());
        //System.out.println(game.getCurrentPlayer().getBoard().traverse());
        g.setColor(new Color(0, 0, 0, 153));
        g.fillRect((int) (getWidth() / 6.784), (int) (getHeight() / 8.571), (int) (getWidth() / 1.69), (int) (getHeight() / 1.636));
        ArrayList<Tile> temp = player.getBoard().traverse();
        player.getBoard().setBoardWidthandHeight(getWidth(), getHeight());
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getImage() != null) {
                rotateImage(g, temp.get(i).getImage(), temp.get(i).getXCoord(), temp.get(i).getYCoord(), (int) (getWidth() / 13.714), (int) (getHeight() / 6.545), 60 * temp.get(i).getOrientation());
                if (temp.get(i).hasAnimal()) {
                    g.drawImage(tokenImages.get(temp.get(i).getAnimal()), temp.get(i).getXCoord() + (int) (getWidth() / 106.667), temp.get(i).getYCoord() + (int) (getHeight() / 37.241), (int) (getWidth() / 18.462), (int) (getHeight() / 10.385), null);
                }
            }
        }
        g.drawImage(icons.get("b2"), 0, 0, getWidth(), getHeight(), null);
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

    public void drawOthers(Graphics g, HashMap<String, BufferedImage> icons, HashMap<Integer, BufferedImage> scoringCards, HashMap<Integer, BufferedImage> tokenImages, ArrayList<Player> players) {
        ArrayList<Color> colors = new ArrayList<Color>();
        colors.add(new Color(255, 243, 188));
        colors.add(new Color(154, 225, 228));
        colors.add(new Color(255, 144, 173));
        colors.add(new Color(144, 219, 176));

        g.setColor(new Color(0,0,0,102));
        g.fillRect((int)(getWidth()/1.328), (int)(getHeight()/49.091), (int)(getWidth()/4.286), (int)(getHeight()/1.044));
        g.fillRect((int)(getWidth()/83.478), (int)(getHeight()/49.091), (int)(getWidth()/8.24), (int)(getHeight()/1.044));
        g.fillRect((int)(getWidth()/6.784), (int)(getHeight()/1.34), (int)(getWidth()/1.69), (int)(getHeight()/4.337));
        g.fillRect((int)(getWidth()/6.76), (int)(getHeight()/49.09), (int)(getWidth()/1.692), (int)(getHeight()/13.17));

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
        g.drawImage(icons.get("pinecone"), (int)(getWidth()/1.693), (int)(getHeight()/1.196), (int)(getWidth()/18.113), (int)(getHeight()/12.706), null);
        g.setColor(Color.white);
        g.setFont(new Font("h", 1, (int)(getHeight()/12.695)));
        //System.out.println((int)(getHeight()/12.695) + " - 66");

        g.drawString("x "+player.getPineCones(), (int)(getWidth()/1.526), (int)(getHeight()/1.108));

        if(endGame) {
            drawEndScores(g, icons, tokenImages, players, colors);
        }
        else {
            int e = 0;
            for(int i = 0; i<players.size()-1; i++) {
                if(player.equals(players.get(e))) {
                    //System.out.println(e+" = player");
                    g.setFont(new Font("he", 1, (int)(getHeight()/21.0432)));
                    g.setColor(colors.get(e));
                    g.drawString("Player "+(e+1)+"'s Board", (int)(getWidth()/3.052), (int)(getHeight()/13.333));
                    e++;
                }

                g.setColor(Color.white);
                g2.drawRect((int)(getWidth()/1.306), (int)(getHeight()/27)+i*(int)(getHeight()/4.576), (int)(getWidth()/4.788), (int)(getHeight()/5.023));
                g.setColor(colors.get(e));
                g.setFont(new Font("j", 1, (int)(getHeight()/30.72)));
                //System.out.println((int)(getHeight()/30.72) + " - 27");
                g.drawString("Player "+(e+1), (int)(getWidth()/1.199), (int)(getHeight()/11.134)+i*(int)(getHeight()/4.576));
                g.drawImage(icons.get("pinecone"), (int)(getWidth()/1.153), (int)(getHeight()/6.879)+i*(int)(getHeight()/4.576), (int)(getWidth()/28.657), (int)(getHeight()/20), null);
                g.setColor(Color.white);
                g.drawString("x "+players.get(e).getPineCones(), (int)(getWidth()/1.097), (int)(getHeight()/5.4)+i*(int)(getHeight()/4.576));
                rotateImage(g, players.get(e).getBoard().getStartTile().getImage(), (int)(getWidth()/1.253), (int)(getHeight()/7.297)+i*(int)(getHeight()/4.576), (int)(getWidth()/56.471), (int)(getHeight()/27.692), 60*players.get(e).getBoard().getStartTile().getOrientation());
                rotateImage(g, players.get(e).getBoard().getStartTile().getAdjacent(3).getImage(), (int)(getWidth()/1.24), (int)(getHeight()/6.102)+i*(int)(getHeight()/4.576), (int)(getWidth()/56.471), (int)(getHeight()/27.692), 60*players.get(e).getBoard().getStartTile().getAdjacent(3).getOrientation());
                rotateImage(g, players.get(e).getBoard().getStartTile().getAdjacent(4).getImage(), (int)(getWidth()/1.266), (int)(getHeight()/6.102)+i*(int)(getHeight()/4.576), (int)(getWidth()/56.471), (int)(getHeight()/27.692), 60*players.get(e).getBoard().getStartTile().getAdjacent(4).getOrientation());

                e++;

            }
            if(lastPlayer) {
                g.setFont(new Font("he", 1, 40));
                g.setColor(colors.get(e));
                g.drawString("Player "+(players.size())+"'s Board", (int)(getWidth()/3.052), (int)(getHeight()/13.333));
            }
        }
    }
    public void drawEndScores(Graphics g, HashMap<String, BufferedImage> icons, HashMap<Integer, BufferedImage> tokenImages, ArrayList<Player> players, ArrayList<Color> colors) {
        for(int i = 0; i<players.size(); i++) {
            if(player.equals(players.get(i))) {
                //System.out.println(e+" = player");
                g.setFont(new Font("he", 1, (int)(getHeight()/21.0432)));
                g.setColor(colors.get(i));
                g.drawString("Player "+(i+1)+"'s Board", (int)(getWidth()/3.052), (int)(getHeight()/13.333));
            }
        }
        g.setColor(Color.white);
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke((int)(getHeight()/270)));

        for(int i = 0; i<5; i++) {
            g2.drawLine((int)(getWidth()/1.327), (int)(getHeight()/8.372)+i*(int)(getHeight()/10.693), (int)(getWidth()/1.014), (int)(getHeight()/8.372)+i*(int)(getHeight()/10.693));
            g2.drawLine((int)(getWidth()/1.068), (int)(getHeight()/9.818)+i*(int)(getHeight()/10.693), (int)(getWidth()/1.031), (int)(getHeight()/27)+i*(int)(getHeight()/10.693));
            g.drawImage(tokenImages.get(i+1), (int)(getWidth()/1.31), (int)(getHeight()/27)+i*(int)(getHeight()/10.8), (int)(getWidth()/23.704), (int)(getHeight()/13.333), null);
            g.drawImage(icons.get("t"+(i+1)), (int)(getWidth()/1.15), (int)(getHeight()/27)+i*(int)(getHeight()/10.8), (int)(getWidth()/20.211), (int)(getHeight()/13.333), null);
        }
        g2.drawLine((int)(getWidth()/1.327), (int)(getHeight()/1.701), (int)(getWidth()/1.014), (int)(getHeight()/1.701));
        g2.drawLine((int)(getWidth()/1.155), (int)(getHeight()/1.496), (int)(getWidth()/1.014), (int)(getHeight()/1.496));
        g2.drawLine((int)(getWidth()/1.235), (int)(getHeight()/49.091), (int)(getWidth()/1.235), (int)(getHeight()/1.695));
        g2.drawLine((int)(getWidth()/1.157), (int)(getHeight()/49.091), (int)(getWidth()/1.157), (int)(getHeight()/1.383));
        g2.drawLine((int)(getWidth()/1.084), (int)(getHeight()/49.091), (int)(getWidth()/1.084), (int)(getHeight()/1.383));

        g.drawImage(icons.get("pinecone"), (int)(getWidth()/1.148), (int)(getHeight()/1.674), (int)(getWidth()/22.588), (int)(getHeight()/16.119), null);

        g.setFont(new Font("50", 1, (int)(getHeight()/16.187)));
        g.drawString("W", (int)(getWidth()/1.299), (int)(getHeight()/1.736));
        g.drawString("H", (int)(getWidth()/1.138), (int)(getHeight()/1.736));
        g.drawString("T", (int)(getWidth()/1.138), (int)(getHeight()/1.385));

        //animal scoring
        for(int i = 0; i<players.size(); i++) {
            if(player.equals(players.get(i))) {
                //System.out.println(e+" = player");
                g.setFont(new Font("j", 1, (int)(getHeight()/18.500)));
                g.setColor(colors.get(i));
            }
        }
        for (int j = 0; j < 6; j++)
        {
            g.drawString(""+ player.getScore()[j], (int)(getWidth()/1.217), (int)(getHeight()/18) + j*(int)(getHeight()/10.286));
        }



        //habitat scoring


    }

    public void rotateImage(Graphics g, BufferedImage image, int x, int y, int width, int height, int degree)
    {
        Graphics2D g2d = (Graphics2D)g;
        double rotationRequired = Math.toRadians (degree);
        double locationX = image.getWidth() / 2;
        double locationY = image.getHeight() / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

        if(degree%180 == 0 || degree%90==0) {
            g2d.drawImage(op.filter(image, null), x, y, width, height,null);
        }
        else {
            g2d.drawImage(op.filter(image, null), x, y, (int)(width*1.26), (int)(height*1.11),null);
        }
    }
    public void shift(int i)
    {
        player.getBoard().updateTileCoords((int)(getWidth()/2.46)+shiftX*(int)(getWidth()/13.714), (int)(getHeight()/4.5)+shiftY*(int)(getHeight()/6.545), getWidth(), getHeight());
        switch(i) {//g.fillRect((int)(getWidth()/6.784), (int)(getHeight()/8.571), (int)(getWidth()/1.69), (int)(getHeight()/1.636));
            case 1: {
                ArrayList<Tile> temp = player.getBoard().traverse();
                boolean b = false;
                for(int e = 0; e<temp.size(); e++) {
                    Tile t = temp.get(e);
                    if(t.getYCoord()-(int)(getHeight()/6.545)>=(int)(getHeight()/8.571)) {
                        b = true;
                    }
                }
                if(b)
                    shiftY--;
                break;
            }
            case 2: {
                ArrayList<Tile> temp = player.getBoard().traverse();
                boolean b = false;
                for(int e = 0; e<temp.size(); e++) {
                    Tile t = temp.get(e);
                    if(t.getXCoord()+(int)(getWidth()/13.714)<=(int)(getWidth()/6.784)+(int)(getWidth()/1.69)) {
                        b = true;
                    }
                }
                if(b)
                    shiftX++;
                break;
            }
            case 3: {
                ArrayList<Tile> temp = player.getBoard().traverse();
                boolean b = false;
                for(int e = 0; e<temp.size(); e++) {
                    Tile t = temp.get(e);
                    if(t.getYCoord()+(int)(getHeight()/6.545)<=(int)(getHeight()/8.571)+(int)(getHeight()/1.636)) {
                        b = true;
                    }
                }
                if(b)
                    shiftY++;
                break;
            }
            case 4: {
                ArrayList<Tile> temp = player.getBoard().traverse();
                boolean b = false;
                for(int e = 0; e<temp.size(); e++) {
                    Tile t = temp.get(e);
                    if(t.getXCoord()-(int)(getWidth()/13.714)>=(int)(getWidth()/6.784)) {
                        b = true;
                    }
                }
                if(b)
                    shiftX--;
                break;
            }
        }
    }

    public void setPlayer(Player p, boolean b) {
        player = p;
        shiftX = 0;
        shiftY = 0;
        lastPlayer = b;
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
    public void setEndGame(boolean b){endGame = b;}
    public boolean endGame(){return endGame;}
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    public void mouseClicked(int x, int y) {
        if(x>=(int)(getWidth()/41.739) && x<=(int)(getWidth()/41.739)+(int)(getWidth()/10.105) && y>=(int)(getHeight()/30) && y<=(int)(getHeight()/30)+(int)(getHeight()/17.143)) {
            isVisible = false;
        }
        if(x>=(int) (getWidth() / 1.235) && x<=(int)(getWidth()/1.076) && y>=(int)(getHeight()/1.401) && y<=(int)(getHeight()/1.065)) {
            if(x>=(int)(getWidth()/1.176) && x<=(int)(getWidth()/1.176)+(int)(getWidth()/25.6) && y>=(int)(getHeight()/1.401) && y<=(int)(getHeight()/1.401)+(int)(getHeight()/14.4)) {
                shift(1);
                repaint();
            }
            else if(x>=(int)(getWidth()/1.117) && x<=(int)(getWidth()/1.076) && y>=(int)(getHeight()/1.26) && y<=(int)(getHeight()/1.26)+(int)(getHeight()/14.4)) {
                shift(2);
                repaint();
            }
            else if(x>=(int) (getWidth() / 1.176) && x<=(int) (getWidth() / 1.124) && y>=(int)(getHeight()/1.145) && y<=(int)(getHeight()/1.065)) {
                shift(3);
                repaint();
            }
            else if(x>=(int) (getWidth() / 1.235) && x<=(int) (getWidth() / 1.184) && y>=(int)(getHeight()/1.26) && y<=(int)(getHeight()/1.26)+(int)(getHeight()/14.4)) {
                shift(4);
                repaint();
            }
        }
    }

}
