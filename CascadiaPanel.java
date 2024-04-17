import java.awt.*;
import javax.swing.*;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
public class CascadiaPanel extends JPanel implements MouseListener{
    private HelpPanel help;
    private PlayerPanel player;
    private MainMenuPanel start;
    private HashMap<String, BufferedImage> icons;
    private HashMap<Integer, BufferedImage> tokenImages, scoringCards;
    private int gameState;
    private boolean gameStart, roundStart;
    private boolean tileClicked, tokenClicked, twoPlayerCLicked, threePlayerClicked, fourPlayerClicked, usePineConesClicked, chooseTileTokenClicked, clearTokenClicked, okClicked, throwAwayClicked;
    private boolean nextPlayerClicked;
    private boolean tilePlaced, tokenPlaced;
    private ArrayList<Color> colors;
    private int tileChosenNum; // only for choosing tile to place to pass to player board to addTile()
    private Tile tileTokenPlacement; //only for mouseclicker when player has clicked on it to add a token to it
    private int tokenChosenNum; // only for choosing token to place to pass to player board to set token to that tile;
    private Token[] tempClearedTokens;
    private Game game;
    //erica is shrot
    public CascadiaPanel(Game game)
    {
        this.game = game;
        help = new HelpPanel();
        player = new PlayerPanel();
        start = new MainMenuPanel();
        icons = new HashMap<String, BufferedImage>();
        tokenImages = new HashMap<Integer, BufferedImage>();
        scoringCards = new HashMap<Integer, BufferedImage>();
        //game = new Game();
        colors = new ArrayList<Color>();
        colors.add(new Color(255, 243, 188));
        colors.add(new Color(154, 225, 228));
        colors.add(new Color(255, 144, 173));
        colors.add(new Color(144, 219, 176));

        //flags or indicators of whether a button has been clicked to send to wait methods
        tileClicked = false;
        tokenClicked = false;
        twoPlayerCLicked = false;
        threePlayerClicked = false;
        fourPlayerClicked = false;
        usePineConesClicked = false;
        chooseTileTokenClicked = false;
        clearTokenClicked = false;
        okClicked = false;
        throwAwayClicked = false;
        nextPlayerClicked = false;

        tileChosenNum = -1;
        tokenChosenNum = -1;
        tileTokenPlacement = null;
        tilePlaced = false;
        tokenPlaced = false;
        tempClearedTokens = new Token[4];

        try
        {
            icons.put("background", ImageIO.read(CascadiaPanel.class.getResource("/Images/background.png")));
            icons.put("open", ImageIO.read(CascadiaPanel.class.getResource("/Images/open icon.png")));
            icons.put("title", ImageIO.read(CascadiaPanel.class.getResource("/Images/title.png")));
            icons.put("person", ImageIO.read(CascadiaPanel.class.getResource("/Images/person.png")));
            icons.put("right", ImageIO.read(CascadiaPanel.class.getResource("/Images/right.png")));
            icons.put("left", ImageIO.read(CascadiaPanel.class.getResource("/Images/left.png")));
            icons.put("exit", ImageIO.read(CascadiaPanel.class.getResource("/Images/exit.png")));
            icons.put("pinecone", ImageIO.read(CascadiaPanel.class.getResource("/Images/pinecone.png")));
            icons.put("arrow", ImageIO.read(CascadiaPanel.class.getResource("/Images/arrow.png")));

            //Elk = 1
            //Salmon = 2
            //Hawk = 3
            //Fox = 4
            //Bear = 5
            tokenImages.put(1, ImageIO.read(CascadiaPanel.class.getResource("/Tokens/elk token.png")));
            tokenImages.put(2, ImageIO.read(CascadiaPanel.class.getResource("/Tokens/fish token.png")));
            tokenImages.put(3, ImageIO.read(CascadiaPanel.class.getResource("/Tokens/hawk token.png")));
            tokenImages.put(4, ImageIO.read(CascadiaPanel.class.getResource("/Tokens/fox token.png")));
            tokenImages.put(5, ImageIO.read(CascadiaPanel.class.getResource("/Tokens/bear token.png")));

            scoringCards.put(0, ImageIO.read(CascadiaPanel.class.getResource("/ScoringCards/elk - lines card.png")));
            scoringCards.put(1, ImageIO.read(CascadiaPanel.class.getResource("/ScoringCards/fish - long run card.png")));
            scoringCards.put(2, ImageIO.read(CascadiaPanel.class.getResource("/ScoringCards/hawk - solidarity card.png")));
            scoringCards.put(3, ImageIO.read(CascadiaPanel.class.getResource("/ScoringCards/fox - nearby animals card.png")));
            scoringCards.put(4, ImageIO.read(CascadiaPanel.class.getResource("/ScoringCards/bear - mating pairs card.png")));
        }
        catch(Exception e)
        {
            System.out.println("Error in CascadiaPanel");
        }

        addMouseListener(this);
    }

    public int getGameState()
    {
        return gameState;
    }
    public void setGameState(int gs)
    {
        gameState = gs;
    }

    public int getTileChosenNum()  {    return tileChosenNum;  }
    public void setTileChosenNum(int tileNum)  {   tileChosenNum = tileNum;  }

    public int getTokenChosenNum()   {  return tokenChosenNum;  }

    public void setTokenChosenNum(int tokenNum)  {   tokenChosenNum = tokenNum;  }

    public Tile getTileForTokenPlacementChosen()   {       return tileTokenPlacement;    }

    public void setTileForTokenPlacement(Tile tile)  {   tileTokenPlacement = tile;  }


    //g.drawImage(icons.get(""), (int)(getWidth()), (int)(getHeight()), (int)(getWidth()), (int)(getHeight()), null);
    public void paint(Graphics g)
    {
        g.drawImage(icons.get("background"), 0, 0, getWidth(), getHeight(), null);
        int s = getGameState();
        System.out.println(s);
        switch(s) {
            //main menu
            case 0: {
                start.setVisible(true);
                start.paint(g, icons, 0, getWidth(), getHeight());
                break;
            }
            //choose player amount
            case 1: {
                start.setVisible(true);
                start.paint(g, icons, 1, getWidth(), getHeight());
                break;
            }
            //main layout - player chooses options
            case 2 : {
                drawPrompt(g, "Choose a tile and token!");
                drawOptions(g);
                drawPlayerBoard(g);
                drawPlayerIcons(g);
                drawScoringCards(g);
                drawShiftButtons(g);
                drawButtons(g);
                break;
            }
            case 3:
            {
                drawPrompt(g, "Choose where to place the tile!");
                drawOptions(g);
                drawPlayerBoard(g);
                drawPlayerIcons(g);
                drawScoringCards(g);
                drawShiftButtons(g);
                drawButtons(g);
                break;
            }
            case 4: {

                break;
            }
            case 5: {

                break;
            }
            case 6: {

                break;
            }
            case 7: {

                break;
            }
            case 8: {

                break;
            }
            case 9:
            {

                break;
            }
            case 10:
            {

                break;
            }
            case 11:
            {

                break;
            }
            case 12:
            {

                break;
            }
        }
    }

    public void drawPrompt(Graphics g, String prompt) {
        g.setColor(new Color(0, 0, 0, 102));
        g.fillRect((int)(getWidth()/6.76), (int)(getHeight()/49.09), (int)(getWidth()/1.692), (int)(getHeight()/13.17));
        g.setFont(new Font("he", 1, (int)(getHeight()/28.8)));
        g.setColor(colors.get(game.getPlayerNum()));
        g.drawString("Player "+game.getPlayerNum()+"'s turn!", (int)(getWidth()/2.659), (int)(getHeight()/19.286));
        int x = 0; int y = 0;
        switch(getGameState()) {
            case 2 : {
                x = (int)(getWidth()/2.954);
                y =  (int)(getHeight()/10.693);
                break;
            }
            case 3 : {
                break;
            }
        }
        g.drawString(prompt, x, y);
    }
    public void drawOptions(Graphics g) {
        g.setColor(new Color(0, 0, 0, 102));
        g.fillRect((int)(getWidth()/6.784), (int)(getHeight()/1.34), (int)(getWidth()/1.69), (int)(getHeight()/4.337));
        drawHighlights(g);
        for(int i = 0; i<game.getAvailableTiles().length; i++) {
            g.drawImage(game.getAvailableTiles()[i].getImage(), (int)(getWidth()/6.316)+i*(int)(getWidth()/14.884), (int)(getHeight()/1.289), (int)(getWidth()/19.01), (int)(getHeight()/9.231), null);
            game.getAvailableTiles()[i].setXCoord((int)(getWidth()/6.316)+i*(int)(getWidth()/14.884));
            game.getAvailableTiles()[i].setYCoord((int)(getHeight()/1.289));
            g.drawImage(tokenImages.get(game.getAvailableTokens()[i].getAnimal()), (int)(getWidth()/6.038)+i*(getWidth()/15), (int)(getHeight()/1.12), (int)(getWidth()/25.946), (int)(getHeight()/14.595), null);
        }
        g.setColor(Color.white);
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke((int)(getHeight()/270)));
        g.setFont(new Font("h", 1, (int)(getHeight()/31.697)));
        //System.out.println((int)(getHeight()/31.697));

        //pinecones
        if(game.getCurrentPlayer().getPineCones()>0) {
            g2.drawRect((int)(getWidth()/2.333), (int)(getHeight()/1.251), (int)(getWidth()/6.784), (int)(getHeight()/15.652));
            g.drawString("Use pinecones", (int)(getWidth()/2.261), (int)(getHeight()/1.186));
        }
        //3 of the same pinecones
        if(game.checkOverpopulation(false) == 3) {
            g2.drawRect((int)(getWidth()/2.333), (int)(getHeight()/1.121), (int)(getWidth()/6.784), (int)(getHeight()/15.652));
            g.drawString("Clear tokens", (int)(getWidth()/2.22), (int)(getHeight()/1.069));
        }


    }

    public void drawPlayerBoard(Graphics g) {
        g.setColor(new Color(0, 0, 0, 153));
        g.fillRect((int)(getWidth()/6.784), (int)(getHeight()/8.571), (int)(getWidth()/1.69), (int)(getHeight()/1.636));
        ArrayList<Tile> temp = game.getCurrentPlayer().getBoard().traverse();
        for(int i = 0; i<temp.size(); i++) {
            rotateImage(g, temp.get(i).getImage(), temp.get(i).getXCoord(), temp.get(i).getYCoord(), (int)(getWidth()/13.714), (int)(getHeight()/6.545), 60*temp.get(i).getOrientation());
        }
    }

    public void drawPlayerIcons(Graphics g) {
        g.setColor(new Color(0,0,0,102));
        g.fillRect((int)(getWidth()/1.328), (int)(getHeight()/49.091), (int)(getWidth()/4.286), (int)(getHeight()/1.044));
        g.drawImage(icons.get("pinecone"), (int)(getWidth()/1.693), (int)(getHeight()/1.196), (int)(getWidth()/18.113), (int)(getHeight()/12.706), null);
        g.setColor(Color.white);
        g.setFont(new Font("h", 1, 66));
        g.drawString("x "+game.getCurrentPlayer().getPineCones(), (int)(getWidth()/1.526), (int)(getHeight()/1.108));

        g.setColor(Color.white);
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke((int)(getHeight()/270)));

        int e = 0;
        for(int i = 0; i<game.getPlayers().size()-1; i++) {
            if(e==game.getPlayerNum()) {
                e++;
            }

                g.setColor(Color.white);
                g2.drawRect((int)(getWidth()/1.306), (int)(getHeight()/27)+i*(int)(getHeight()/4.576), (int)(getWidth()/4.788), (int)(getHeight()/5.023));
                g.drawImage(icons.get("open"), (int)(getWidth()/1.06), (int)(getHeight()/27)+i*(int)(getHeight()/4.576), (int)(getWidth()/32.542), (int)(getHeight()/18.305), null);
                g.setColor(colors.get(e));
                g.setFont(new Font("j", 1, 27));
                g.drawString("Player "+e, (int)(getWidth()/1.199), (int)(getHeight()/11.134)+i*(int)(getHeight()/4.576));
                g.drawImage(icons.get("pinecone"), (int)(getWidth()/1.153), (int)(getHeight()/6.879)+i*(int)(getHeight()/4.576), (int)(getWidth()/28.657), (int)(getHeight()/20), null);
                g.setColor(Color.white);
                g.drawString("x "+game.getPlayers().get(e).getPineCones(), (int)(getWidth()/1.097), (int)(getHeight()/5.4)+i*(int)(getHeight()/4.576));

                e++;

        }


    }
    public void drawScoringCards(Graphics g) {
        g.setColor(new Color(0,0,0,102));
        g.fillRect((int)(getWidth()/83.478), (int)(getHeight()/49.091), (int)(getWidth()/8.24), (int)(getHeight()/1.044));
        g.setColor(Color.white);
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke((int)(getHeight()/270)));
        g2.drawRect((int)(getWidth()/41.739), (int)(getHeight()/30), (int)(getWidth()/10.105), (int)(getHeight()/17.143));
        g.setColor(Color.white);
        g.setFont(new Font("he", 1, (int)(getHeight()/28.8)));
        g.drawString("Help", (int)(getWidth()/18.78), (int)(getHeight()/13.846));
        for(int i = 0; i<5; i++) {
            g.drawImage(scoringCards.get(i), (int)(getWidth()/41.739), (int)(getHeight()/10.286)+i*(int)(getHeight()/5.714), (int)(getWidth()/10.105), (int)(getHeight()/5.967), null);
        }
    }
    public void drawShiftButtons(Graphics g)
    {
        g.setColor(Color.white);
        if(gameState == 4 || gameState == 8 || gameState == 9) {
            Graphics2D g2 = (Graphics2D)g;
            g2.setStroke(new BasicStroke((int)(getHeight()/270)));
            g2.drawRect((int)(getWidth()/1.176), (int)(getHeight()/1.26), (int)(getWidth()/25.6), (int)(getHeight()/14.4));
            g.setFont(new Font("h", 1, 24));
            g.drawString("OK", (int)(getWidth()/1.166), (int)(getHeight()/1.195));
        }
        else {
           g.fillRect((int)(getWidth()/1.176), (int)(getHeight()/1.26), (int)(getWidth()/25.6), (int)(getHeight()/14.4));
        }
        g.drawImage(icons.get("arrow"), (int)(getWidth()/1.176), (int)(getHeight()/1.401), (int)(getWidth()/25.6), (int)(getHeight()/14.4), null);
//        rotateImage(g, icons.get("arrow"), (int)(getWidth()/1.118), (int)(getHeight()/1.26), (int)(getWidth()/25.6), (int)(getHeight()/14.4), 90);
//        rotateImage(g, icons.get("arrow"), (int)(getWidth()/1.176), (int)(getHeight()/1.153), (int)(getWidth()/25.6), (int)(getHeight()/14.4), 180);
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
    public void shift(int i)
    {

    }
    public void drawButtons(Graphics g)
    {

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

    public void drawHighlights(Graphics g) {
        switch(getGameState())  {
            case 2 : {
                g.setColor(colors.get(game.getPlayerNum()));
                //g.setColor(Color.yellow);
                /*
                int[] xC = new int[6];
                xC[0] = (int)(getWidth()/5.408);
                xC[1] = (int)(getWidth()/6.531);
                xC[2] = (int)(getWidth()/6.621);
                xC[3] = (int)(getWidth()/5.408);
                xC[4] = (int)(getWidth()/4.638);
                xC[5] = (int)(getWidth()/4.638);
                int[] yC = new int[6];
                yC[0] = (int)(getWidth()/1.306);
                yC[1] = (int)(getWidth()/1.253);
                yC[2] = (int)(getWidth()/1.159);
                yC[3] = (int)(getWidth()/1.117);
                yC[4] = (int)(getWidth()/1.253);
                yC[5] = (int)(getWidth()/1.159);
                g.fillPolygon(xC, yC, 6);
                 */
                for(int i = 0; i< 4; i++) {
                    g.fillOval((int)(getWidth()/6.174)+i*(getWidth()/15), (int)(getHeight()/1.13), (int)(getWidth()/21.818), (int)(getHeight()/12.273));
                }
            }
        }
    }

    public void mouseClicked(MouseEvent e) {    }
    public void mouseEntered(MouseEvent e) {    }
    public void mouseExited(MouseEvent e) {    }
    public void mouseReleased(MouseEvent e) {   }
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        System.out.println(x +" " +y);
        switch(getGameState())
        {
            case 0 :
            {
                if(start.stateChangeClick(x, y)) {
                    setGameState(1);
                }
                repaint();
                break;
            }
            case 1 :
            {
                int pat = start.playerAmountClick(x, y);
                if(pat>0) {
//                    if (pat == 2)
//                        twoPlayerCLicked = true;
//                    else if (pat == 3)
//                        threePlayerClicked = true;
//                    else if (pat == 4)
//                        fourPlayerClicked = true;
                    game.setNumOfPlayers(pat);
                    setGameState(2);
                    repaint();
                    game.play();
                }
                repaint();
                break;
            }
            case 2:
            {
                //pinecones clicked
                if(x == getWidth() && game.getCurrentPlayer().getPineCones() > 0) //coordinates for use pinecone button
                {
                    usePineConesClicked = true;
                    setGameState(7);
                }


                //clear tokens button clicked              IDK ABOUT THIS NOT SURE
                else if (game.checkOverpopulation(false) == 3 && x == getWidth()) //coordinates for clear token button
                {
                    clearTokenClicked = true;
                    setGameState(8);
                }


                //tile clicked
                else {
                    for (int i = 0; i < 4; i++) {
                        //System.out.println("reached");
                        if (game.getAvailableTiles()[i].isClicked(x, y, (int) (getWidth() / 19.01), (int) (getHeight() / 9.231))) {
                            tileChosenNum = i;
                            tileClicked = true;
                            setGameState(3);
                            repaint();
                        }
                    }
                }
                repaint();
                break;
            }
            case 3: //choose tile placement
            {
                //chose where to place the tile on highlight available tiles
                //code
                tilePlaced = true;
                setGameState(4);
                repaint();
                break;
            }
            case 4:
            {
                //left button clicked
                //rotate tile 60 degrees left
                //shift habitats leftwards in arraylist
                //repaint();

                //right button clicked
                //rotate tile 60 degrees right
                //shift habitats rightwards in arraylist
                //repaint();

                if (x <= getWidth()/*ok button coordinates */)
                {
                    okClicked = true;
                    setGameState(5);
                }
                repaint();
                break;
            }
            case 5: //choose token placement or throw away
            {
                //token is thrown away
                if (x <= getWidth()) //throw away button clicked coordinates
                {
                    throwAwayClicked = true;
                    tileTokenPlacement = null;
                }
                else if (x<=getWidth())//tile coordinates that they chose to place the tile
                {
                    //token is placed on the tile they chose
                    tokenPlaced = true;
                    //get traversal of player tiles and use isClicked to see if player clicked on tile
                }
                setGameState(6);
                repaint();
                break;
            }
            case 6: //check if next player button is clicked
            {
                if (x <= getWidth()) //coordinates for clicking next turn button
                    nextPlayerClicked = true;

            }
            case 7: //use pinecone button
            {
                if (x <= getWidth()) // coordinates for clear token button
                {
                    clearTokenClicked = true;
                    setGameState(8);
                }
                else if (x <= getWidth()) // coordinates for specific tile and token button
                {
                    chooseTileTokenClicked = true;
                    setGameState(9);
                }
                repaint();
                break;
            }
            case 8: //clear tokens
            {
                ArrayList<Token> temp = new ArrayList<Token>();
                for(int i = 0; i<4; i++)
                    if (Math.pow((x - (int)(getWidth()/6.038)+i*(getWidth()/15)-((int)(getWidth()/25.946)/2)), 2) + Math.pow((y - (int)(getHeight()/1.12)-((int)(getWidth()/25.946))/2), 2) <= Math.pow(((int)(getWidth()/25.946))/2, 2))
                        tempClearedTokens[i] = game.getAvailableTokens()[i];

                if (x <= getWidth()) // ok button clicked coordinates
                {
                    for (int i = 0; i < tempClearedTokens.length; i++) {
                        if (tempClearedTokens[i] != null) {
                            game.getTokenDeck().add(tempClearedTokens[i]);
                            game.getAvailableTokens()[i] = null;
                        }
                    }
                    okClicked = true;
                    setGameState(6);
                }
                repaint();
                break;
            }
            case 9: //choose specific tile and token
            {
                //Check which tile and token has been clicked
                for(int i = 0; i<4; i++) {
                    if (Math.pow((x - (int)(getWidth()/6.038)+i*(getWidth()/15)-((int)(getWidth()/25.946)/2)), 2) + Math.pow((y - (int)(getHeight()/1.12)-((int)(getWidth()/25.946))/2), 2) <= Math.pow(((int)(getWidth()/25.946))/2, 2))
                    {
                        tokenChosenNum = i;
                        tokenClicked = true;
                    }
                    else if (game.getAvailableTiles()[i].isClicked(x, y, (int)(getWidth()/19.01), (int)(getHeight()/9.231))){
                        tileChosenNum = i;
                        tileClicked = true;

                    }
                }
                //Check if ok button clicked
                if(x<=getWidth()/*OkButton coordinates*/) {
                    okClicked = true;
                    setGameState(3);
                }
                repaint();
                break;
            }
            case 10: {}
            case 11: {}
            case 12: {}
        }
    }

    public void waitForPlayerAmountClicked()
    {
        while (!twoPlayerCLicked && !threePlayerClicked && !fourPlayerClicked)
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Error in waitForPlayerAmount method = "+e.getMessage());
            }
        }
    }

    public void waitForChooseTileOrPineconeClicked()
    {
        while (!tileClicked && !usePineConesClicked) //player has not clicked on a tile
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Error in tile or pinecone wait method = "+e.getMessage());
            }
        }
    }

    public void waitForPinecone2options()
    {
        while (!chooseTileTokenClicked && !clearTokenClicked) //player has not clicked on a tile
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Error in pinecone options wait method = "+e.getMessage());
            }
        }
    }

    public void waitForTileClicked()
    {
        while (!tileClicked)
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Error in choose tile wait method = "+e.getMessage());
            }
        }
    }

    public void waitForTokenClicked()
    {
        while (!tokenClicked)
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Error in choose token wait method = "+e.getMessage());
            }
        }
    }

    public void waitForOkClicked()
    {
        while (!okClicked)
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Error in ok wait method = "+e.getMessage());
            }
        }
    }

    public void waitForTilePlaced()
    {
        while (!tilePlaced)
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Error in tilePlaced method = "+e.getMessage());
            }
        }
    }

    public void waitForTokenPlacedOrThrowAway()
    {
        while (!tokenPlaced && !throwAwayClicked)
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Error in tokenPlaced method = "+e.getMessage());
            }
        }
    }

    public void waitForNextPlayer()
    {
        while (!nextPlayerClicked)
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Error in tokenPlaced method = "+e.getMessage());
            }
        }
    }

    public void waitForChooseTileOrClearTokensOrUsePinecones()
    {
        while (!tileClicked && !clearTokenClicked && !usePineConesClicked)
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Error in tokenPlaced method = "+e.getMessage());
            }
        }
    }

    public void waitForChooseTileOrClearTokens()
    {
        while (!tileClicked && !clearTokenClicked && !usePineConesClicked)
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Error in tokenPlaced method = "+e.getMessage());
            }
        }
    }

}
