
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
    private ArrayList<BufferedImage> tempTokenImages;
    private int gameState;
    private boolean tileClicked, tokenClicked, twoPlayerCLicked, threePlayerClicked, fourPlayerClicked, usePineConesClicked, chooseTileTokenClicked, clearTokenClicked, okClicked, throwAwayClicked;
    private boolean nextPlayerClicked;
    private boolean tilePlaced, tokenPlaced;
    private boolean overpop3AlrUsed;
    private ArrayList<Color> colors;
    private int tileChosenNum; // only for choosing tile to place to pass to player board to addTile()
    private Tile tileTokenPlacement; //only for mouseclicker when player has clicked on it to add a token to it
    private int tokenChosenNum; // only for choosing token to place to pass to player board to set token to that tile;
    private Token[] tempClearedTokens;
    private Tile chosenTile;
    private Token chosenToken;
    private Game game;
    private int turn;
    private int shiftX, shiftY;
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
        tempTokenImages = new ArrayList<BufferedImage>();
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
        overpop3AlrUsed = false;

        tileChosenNum = -1;
        tokenChosenNum = -1;
        tileTokenPlacement = null;
        tilePlaced = false;
        tokenPlaced = false;
        tempClearedTokens = new Token[4];
        chosenTile = null;
        chosenToken = null;
        turn = 1;

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
            icons.put("b2", ImageIO.read(CascadiaPanel.class.getResource("/Images/background2.png")));
            icons.put("1", ImageIO.read(CascadiaPanel.class.getResource("/Images/gold.png")));
            icons.put("2", ImageIO.read(CascadiaPanel.class.getResource("/Images/silver.png")));
            icons.put("3", ImageIO.read(CascadiaPanel.class.getResource("/Images/bronze.png")));
            icons.put("t1", ImageIO.read(CascadiaPanel.class.getResource("/Images/mountain.png")));
            icons.put("t2", ImageIO.read(CascadiaPanel.class.getResource("/Images/forest.png")));
            icons.put("t3", ImageIO.read(CascadiaPanel.class.getResource("/Images/prairie.png")));
            icons.put("t4", ImageIO.read(CascadiaPanel.class.getResource("/Images/wetland.png")));
            icons.put("t5", ImageIO.read(CascadiaPanel.class.getResource("/Images/river.png")));
            //Mountain = 1
            //Forest = 2
            //Prairie = 3
            //Wetland = 4
            //River = 5

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

            scoringCards.put(0, ImageIO.read(CascadiaPanel.class.getResource("/ScoringCards/elk - herds card.png")));
            scoringCards.put(1, ImageIO.read(CascadiaPanel.class.getResource("/ScoringCards/fish - long run card.png")));
            scoringCards.put(2, ImageIO.read(CascadiaPanel.class.getResource("/ScoringCards/hawk - solidarity card.png")));
            scoringCards.put(3, ImageIO.read(CascadiaPanel.class.getResource("/ScoringCards/fox - nearby animals card.png")));
            scoringCards.put(4, ImageIO.read(CascadiaPanel.class.getResource("/ScoringCards/bear - mating pairs card.png")));

            tempTokenImages.add(ImageIO.read(CascadiaPanel.class.getResource("/Tokens/bear token.png")));
            tempTokenImages.add(ImageIO.read(CascadiaPanel.class.getResource("/Tokens/elk token.png")));
            tempTokenImages.add(ImageIO.read(CascadiaPanel.class.getResource("/Tokens/fish token.png")));
            tempTokenImages.add(ImageIO.read(CascadiaPanel.class.getResource("/Tokens/hawk token.png")));
            tempTokenImages.add(ImageIO.read(CascadiaPanel.class.getResource("/Tokens/fox token.png")));
        }
        catch(Exception e)
        {
            System.out.println("Error in CascadiaPanel");
        }

        addMouseListener(this);
    }

    public void resetGameFlags()
    {
        tileClicked = false;
        tokenClicked = false;
        usePineConesClicked = false;
        chooseTileTokenClicked = false;
        clearTokenClicked = false;
        okClicked = false;
        throwAwayClicked = false;
        nextPlayerClicked = false;
        overpop3AlrUsed = false;

        tileChosenNum = -1;
        tokenChosenNum = -1;
        tileTokenPlacement = null;
        tilePlaced = false;
        tokenPlaced = false;
        tempClearedTokens[0] = null;
        tempClearedTokens[1] = null;
        tempClearedTokens[2] = null;
        tempClearedTokens[3] = null;
        chosenTile = null;
        chosenToken = null;

        game.setIfTookTurn(false);
        game.setTileChose(null);
        game.setTokenChose(null);

        shiftX = 0;
        shiftY = 0;
    }

    public void resetForNewGame()
    {
        resetGameFlags();
        help = new HelpPanel();
        player = new PlayerPanel();
        start = new MainMenuPanel();
        turn = 1;
        game.resetGame();


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
//        System.out.println(s);
        if(help.isVisible()){
            help.paint(g, icons, getWidth(), getHeight());
        }
        else if(player.isVisible() && gameState!=10) {
            player.paint(g, icons, scoringCards, tempTokenImages, tokenImages, game.getPlayers(), getWidth(), getHeight());
        }
        else {
            switch (s) {
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
                case 2: {
                    drawPlayerBoard(g);
                    if(game.checkOverpopulation(false) == 4) {
                        drawPrompt(g, "Overpopulation! Click anywhere to clear all tokens!");
                    }
                    else {
                        drawPrompt(g, "Choose a tile and token!");
                    }
                    drawOptions(g);
                    drawPlayerIcons(g);
                    drawScoringCards(g);
                    drawShiftButtons(g);
                    drawButtons(g);
                    break;
                }
                case 3: {
                    drawPlayerBoard(g);
                    drawPrompt(g, "Choose placement of tile!");
                    drawOptions(g);
                    drawPlayerIcons(g);
                    drawScoringCards(g);
                    drawShiftButtons(g);
                    drawButtons(g);
                    break;
                }
                case 4: {
                    drawPlayerBoard(g);
                    drawPrompt(g, "Rotate tile! Press OK when done!");
                    drawOptions(g);
                    drawPlayerIcons(g);
                    drawScoringCards(g);
                    drawShiftButtons(g);
                    drawButtons(g);
                    break;
                }
                case 5: {
                    drawPlayerBoard(g);
                    drawPrompt(g, "Press tile to place a token on!");
                    drawOptions(g);
                    drawPlayerIcons(g);
                    drawScoringCards(g);
                    drawShiftButtons(g);
                    drawButtons(g);
                    break;
                }
                case 6: {
                    drawPlayerBoard(g);
                    drawPrompt(g, "Confirm and go to next player!");
                    drawOptions(g);
                    drawPlayerIcons(g);
                    drawScoringCards(g);
                    drawShiftButtons(g);
                    drawButtons(g);
                    break;
                }
                case 7: {
                    drawPlayerBoard(g);
                    drawPrompt(g, "Choose an option");
                    drawOptions(g);
                    drawPlayerIcons(g);
                    drawScoringCards(g);
                    drawShiftButtons(g);
                    drawButtons(g);
                    break;
                }
                case 8: {
                    drawPlayerBoard(g);
                    drawPrompt(g, "Choose animal tokens to clear! Press OK when done!");
                    drawOptions(g);
                    drawPlayerIcons(g);
                    drawScoringCards(g);
                    drawShiftButtons(g);
                    drawButtons(g);
                    break;
                }
                case 9: {
                    drawPlayerBoard(g);
                    drawPrompt(g, "Choose a specific tile and token! Press OK when done!");
                    drawOptions(g);
                    drawPlayerIcons(g);
                    drawScoringCards(g);
                    drawShiftButtons(g);
                    drawButtons(g);
                    break;
                }
                case 10: {
                    if(player.isVisible()) {
                        player.paint(g, icons, scoringCards, tempTokenImages, tokenImages, game.getPlayers(), getWidth(), getHeight());
                    }
                    else {
                        drawScoreBoard(g);
                        drawLeaderboard(g);
                    }
                    break;
                }
            }
        }
    }

    public void drawPrompt(Graphics g, String prompt) {
        g.setColor(new Color(0, 0, 0, 102));
        g.fillRect((int)(getWidth()/6.76), (int)(getHeight()/49.09), (int)(getWidth()/1.692), (int)(getHeight()/13.17));
        g.setFont(new Font("he", 1, (int)(getHeight()/28.8)));
        g.setColor(colors.get(game.getPlayerNum()));
        g.drawString("Player "+(game.getPlayerNum()+1)+"'s turn!", (int)(getWidth()/2.659), (int)(getHeight()/19.286));
        int x = 0; int y = 0;
        switch(getGameState()) {
            case 2 : { //choose tile
                if(game.checkOverpopulation(false) == 4) {
                    x = (int)(getWidth()/4.334);
                }
                else {
                    x = (int) (getWidth() / 2.954);
                }
                y = (int) (getHeight() / 10.693);
                break;
            }
            case 3 : {//choose tile placement
                x = (int)(getWidth()/3.009);
                y =  (int)(getHeight()/10.800);
                break;
            }
            case 4 : {//choose tile orientation
                x = (int)(getWidth()/3.282);
                y =  (int)(getHeight()/10.800);
                break;
            }
            case 5 : {//choose token placement
                x = (int)(getWidth()/3.028);
                y =  (int)(getHeight()/10.800);
                break;
            }
            case 6 : {//replace tile and next turn
                x = (int)(getWidth()/3.200);
                y =  (int)(getHeight()/10.800);
                break;
            }
            case 7 : {//choose pinecone options
                x = (int)(getWidth()/2.767);
                y =  (int)(getHeight()/10.800);
                break;
            }
            case 8 : {//choose tokens to clear
                x = (int)(getWidth()/4.384);
                y =  (int)(getHeight()/10.800);
                break;
            }
            case 9 : {//choose specific tile and token
                x = (int)(getWidth()/4.561);
                y =  (int)(getHeight()/10.800);
                break;
            }
            case 10 : {
//                game.bonusesAndScores(); //sets the scores and bonuses of all players i think i hope i wish i pray
                drawScoreBoard(g);
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
            if(game.getAvailableTiles()[i] != null) {
                g.drawImage(game.getAvailableTiles()[i].getImage(), (int)(getWidth()/6.316)+i*(int)(getWidth()/14.95), (int)(getHeight()/1.289), (int)(getWidth()/19.01), (int)(getHeight()/9.231), null);
            }
            if(gameState==2) {
                game.getAvailableTiles()[i].setXCoord((int)(getWidth()/6.316)+i*(int)(getWidth()/14.95));
                game.getAvailableTiles()[i].setYCoord((int)(getHeight()/1.289));
            }
            if (game.getAvailableTokens()[i] != null)
                g.drawImage(tokenImages.get(game.getAvailableTokens()[i].getAnimal()), (int)(getWidth()/6.038)+i*(getWidth()/15), (int)(getHeight()/1.12), (int)(getWidth()/25.946), (int)(getHeight()/14.595), null);
        }
        g.setColor(Color.white);
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke((int)(getHeight()/270)));
        g.setFont(new Font("h", 1, (int)(getHeight()/31.697)));
        //System.out.println((int)(getHeight()/31.697));

        if(gameState==2) {
            //pinecones
            if (game.checkOverpopulation(false) != 4 && game.getCurrentPlayer().getPineCones() > 0) {
                g2.drawRect((int) (getWidth() / 2.333), (int) (getHeight() / 1.251), (int) (getWidth() / 6.784), (int) (getHeight() / 15.652));
                g.drawString("Use pinecones", (int) (getWidth() / 2.261), (int) (getHeight() / 1.186));
            }
            //3 of the same pinecones
            if (!overpop3AlrUsed && game.checkOverpopulation(false) == 3) {
                g2.drawRect((int) (getWidth() / 2.333), (int) (getHeight() / 1.121), (int) (getWidth() / 6.784), (int) (getHeight() / 15.652));
                g.drawString("Clear tokens", (int) (getWidth() / 2.22), (int) (getHeight() / 1.069));
            }
        }
        if(gameState==5 || gameState==6) {
            g2.drawRect((int) (getWidth() / 1.731), (int) (getHeight() / 1.325), (int) (getWidth() / 6.906), (int) (getHeight() / 15.652));
            //g.setFont(new Font("", 1, (int)(getHeight()/26.585)));
            //System.out.println((int)(getHeight()/26.585));
            if(gameState==5) {
                g.drawString("Throw away", (int)(getWidth()/1.67), (int)(getHeight()/1.254));
            }
            else if (turn == game.getPlayers().size() * 20) {
                g.drawString("End game", (int)(getWidth()/1.654), (int)(getHeight()/1.254));
            }
            else {
                g.drawString("Next player", (int)(getWidth()/1.654), (int)(getHeight()/1.254));
            }
        }
        if(gameState == 7) {
            g2.drawRect((int) (getWidth() / 2.333), (int) (getHeight() / 1.308), (int) (getWidth() / 6.784), (int) (getHeight() / 9.231));
            g2.drawRect((int) (getWidth() / 2.333), (int) (getHeight() / 1.121), (int) (getWidth() / 6.784), (int) (getHeight() / 15.652));
            g.drawString("Choose tile and", (int) (getWidth() / 2.295), (int) (getHeight() / 1.236));
            g.drawString("token", (int) (getWidth() / 2.105), (int) (getHeight() / 1.173));
            g.drawString("Clear tokens", (int) (getWidth() / 2.225), (int) (getHeight() / 1.069));
        }
    }

    public void drawPlayerBoard(Graphics g) {
        //System.out.println(game.getCurrentPlayer().getBoard().traverse());
        //shift(1);
        game.getCurrentPlayer().getBoard().updateTileCoords((int)(getWidth()/2.46)+shiftX*(int)(getWidth()/13.714), (int)(getHeight()/4.5)+shiftY*(int)(getHeight()/6.545), getWidth(), getHeight());
        //System.out.println(game.getCurrentPlayer().getBoard().traverse());
        g.setColor(new Color(0, 0, 0, 153));
        g.fillRect((int)(getWidth()/6.784), (int)(getHeight()/8.571), (int)(getWidth()/1.69), (int)(getHeight()/1.636));
        if(gameState==3) {
            HashMap<Tile, ArrayList<Integer>> map = game.getCurrentPlayer().getBoard().allNullTiles();
            Iterator<Tile> it = map.keySet().iterator();
            while(it.hasNext()) {
                Tile t = it.next();
                ArrayList<Integer> temp = map.get(t);
                int[] xc = new int[6];
                int[] yc = new int[6];
                for(int i = 0; i<temp.size(); i++) {
                    switch (temp.get(i)) {
                        case 0 : {
                            xc = new int[]{t.getXCoord(), t.getXCoord() + (int) (getWidth() / 27.428), t.getXCoord() + (int) (getWidth() / 27.428), t.getXCoord(), t.getXCoord() - (int) (getWidth() / 27.428), t.getXCoord() - (int) (getWidth() / 27.428)};
                            yc = new int[]{t.getYCoord()-(int)(getHeight()/8.675), t.getYCoord()-(int)(getHeight()/12.857), t.getYCoord(), t.getYCoord()+(int)(getHeight()/26.667), t.getYCoord(), t.getYCoord()-(int)(getHeight()/12.857)};
                            break;
                        }//(int) (getWidth() / 13.714), (int) (getHeight() / 6.545)
                        case 1 : {
                            xc = new int[]{t.getXCoord()+(int)(getWidth()/13.714), t.getXCoord()+(int)(getWidth()/13.714)+(int)(getWidth()/27.428), t.getXCoord()+(int)(getWidth()/13.714)+(int)(getWidth()/27.428), t.getXCoord()+(int)(getWidth()/13.714), t.getXCoord()+(int)(getWidth()/27.428), t.getXCoord()+(int)(getWidth()/27.428)};
                            yc = new int[]{t.getYCoord()-(int)(getHeight()/8.675), t.getYCoord()-(int)(getHeight()/12.857), t.getYCoord(), t.getYCoord()+(int)(getHeight()/26.667), t.getYCoord(), t.getYCoord()-(int)(getHeight()/12.857)};
                            break;
                        }
                        case 2 : {
                            xc = new int[]{t.getXCoord()+(int)(getWidth()/13.714)+(int)(getWidth()/27.428), t.getXCoord()+2*(int)(getWidth()/13.714), t.getXCoord()+2*(int)(getWidth()/13.714), t.getXCoord()+(int)(getWidth()/13.714)+(int)(getWidth()/27.428), t.getXCoord()+(int)(getWidth()/13.714), t.getXCoord()+(int)(getWidth()/13.714)};
                            yc = new int[]{t.getYCoord(), t.getYCoord()+(int)(getHeight()/26.667), t.getYCoord()+(int)(getHeight()/8.675), t.getYCoord()+(int)(getHeight()/6.545), t.getYCoord()+(int)(getHeight()/8.675), t.getYCoord()+(int)(getHeight()/26.667)};
                            break;
                        }
                        case 3 : {
                            xc = new int[]{t.getXCoord()+(int)(getWidth()/13.714), t.getXCoord()+(int)(getWidth()/13.714)+(int)(getWidth()/27.428), t.getXCoord()+(int)(getWidth()/13.714)+(int)(getWidth()/27.428), t.getXCoord()+(int)(getWidth()/13.714), t.getXCoord()+(int)(getWidth()/27.428), t.getXCoord()+(int)(getWidth()/27.428)};
                            yc = new int[]{t.getYCoord()+(int)(getHeight()/8.71), t.getYCoord()+(int)(getHeight()/6.545), t.getYCoord()+(int)(getHeight()/6.545)+(int)(getHeight()/12.857), t.getYCoord()+(int)(getHeight()/6.545)+(int)(getHeight()/8.71), t.getYCoord()+(int)(getHeight()/6.545)+(int)(getHeight()/12.857), t.getYCoord()+(int)(getHeight()/6.545)};
                            break;
                        }
                        case 4 : {
                            xc = new int[]{t.getXCoord(), t.getXCoord() + (int) (getWidth() / 27.428), t.getXCoord() + (int) (getWidth() / 27.428), t.getXCoord(), t.getXCoord() - (int) (getWidth() / 27.428), t.getXCoord() - (int) (getWidth() / 27.428)};
                            yc = new int[]{t.getYCoord()+(int)(getHeight()/8.71), t.getYCoord()+(int)(getHeight()/6.545), t.getYCoord()+(int)(getHeight()/6.545)+(int)(getHeight()/12.857), t.getYCoord()+(int)(getHeight()/6.545)+(int)(getHeight()/8.71), t.getYCoord()+(int)(getHeight()/6.545)+(int)(getHeight()/12.857), t.getYCoord()+(int)(getHeight()/6.545)};
                            break;
                        }
                        case 5 : {
                            xc = new int[]{t.getXCoord()-(int)(getWidth()/27.428), t.getXCoord(), t.getXCoord(), t.getXCoord()-(int)(getWidth()/27.428), t.getXCoord()-(int)(getWidth()/13.714), t.getXCoord()-(int)(getWidth()/13.714)};
                            yc = new int[]{t.getYCoord(), t.getYCoord()+(int)(getHeight()/26.667), t.getYCoord()+(int)(getHeight()/8.675), t.getYCoord()+(int)(getHeight()/6.545), t.getYCoord()+(int)(getHeight()/8.675), t.getYCoord()+(int)(getHeight()/26.667)};
                            break;
                        }
                    }
                    g.setColor(colors.get(game.getPlayerNum()));
                    g.fillPolygon(xc, yc, 6);
                    g.setColor(Color.black);
                    g.drawPolygon(xc, yc, 6);
                }
            }
        }
        ArrayList<Tile> temp = game.getCurrentPlayer().getBoard().traverse();
        game.getCurrentPlayer().getBoard().setBoardWidthandHeight(getWidth(), getHeight());
//        game.getCurrentPlayer().getBoard().setCoordinates();
        for(int i = 0; i<temp.size(); i++) {
            if(temp.get(i).getImage() != null) {
                rotateImage(g, temp.get(i).getImage(), temp.get(i).getXCoord(), temp.get(i).getYCoord(), (int) (getWidth() / 13.714), (int) (getHeight() / 6.545), 60 * (6-temp.get(i).getOrientation()));
                if(temp.get(i).hasAnimal()) {
                    g.drawImage(tokenImages.get(temp.get(i).getAnimal()), temp.get(i).getXCoord()+(int)(getWidth()/106.667), temp.get(i).getYCoord()+(int)(getHeight()/37.241), (int)(getWidth()/18.462), (int)(getHeight()/10.385), null);
                }
            }
        }
        if(gameState==4) {
            g.setColor(colors.get(game.getPlayerNum()));
            int[] xc = {chosenTile.getXCoord()+(int)(getWidth()/27.428), chosenTile.getXCoord()+(int)(getWidth()/13.714), chosenTile.getXCoord()+(int)(getWidth()/13.714), chosenTile.getXCoord()+(int)(getWidth()/27.428), chosenTile.getXCoord(), chosenTile.getXCoord()};
            int[] yc = {chosenTile.getYCoord(), chosenTile.getYCoord()+(int)(getHeight()/26.667), chosenTile.getYCoord()+(int)(getHeight()/8.675), chosenTile.getYCoord()+(int)(getHeight()/6.545), chosenTile.getYCoord()+(int)(getHeight()/8.675), chosenTile.getYCoord()+(int)(getHeight()/26.667)};
            Graphics2D g2 = (Graphics2D)g;
            g2.setStroke(new BasicStroke((int)(getHeight()/270)));
            g2.drawPolygon(xc, yc, 6);
        }
        if(gameState == 5) {
            g.setColor(colors.get(game.getPlayerNum()));
            Graphics2D g2 = (Graphics2D)g;
            g2.setStroke(new BasicStroke((int)(getHeight()/270)));
            ArrayList<Tile> a = game.getCurrentPlayer().getBoard().traverse();
            for(int i = 0; i <a.size(); i++) {
                if(!a.get(i).hasAnimal()) {
                    for(int e:a.get(i).getPossibleAnimals()) {
                        if(e == chosenToken.getAnimal()) {
                            int[] xc = {a.get(i).getXCoord()+(int)(getWidth()/27.428), a.get(i).getXCoord()+(int)(getWidth()/13.714), a.get(i).getXCoord()+(int)(getWidth()/13.714), a.get(i).getXCoord()+(int)(getWidth()/27.428), a.get(i).getXCoord(), a.get(i).getXCoord()};
                            int[] yc = {a.get(i).getYCoord(), a.get(i).getYCoord()+(int)(getHeight()/26.667), a.get(i).getYCoord()+(int)(getHeight()/8.675), a.get(i).getYCoord()+(int)(getHeight()/6.545), a.get(i).getYCoord()+(int)(getHeight()/8.675), a.get(i).getYCoord()+(int)(getHeight()/26.667)};
                            g2.drawPolygon(xc, yc, 6);
                        }
                    }
                }
            }
        }
        g.drawImage(icons.get("b2"), 0, 0, getWidth(), getHeight(), null);
    }

    public void drawPlayerIcons(Graphics g) {
        g.setColor(new Color(0,0,0,102));
        g.fillRect((int)(getWidth()/1.328), (int)(getHeight()/49.091), (int)(getWidth()/4.286), (int)(getHeight()/1.044));
        g.drawImage(icons.get("pinecone"), (int)(getWidth()/1.693), (int)(getHeight()/1.196), (int)(getWidth()/18.113), (int)(getHeight()/12.706), null);
        g.setColor(Color.white);
        g.setFont(new Font("h", 1, (int)(getHeight()/12.695)));
        //System.out.println((int)(getHeight()/12.695) + " - 66");

        g.drawString("x "+game.getCurrentPlayer().getPineCones(), (int)(getWidth()/1.526), (int)(getHeight()/1.108));

        g.setFont(new Font("hi", 1, (int)(getHeight()/30.72)));
        g.drawString("Turn: "+((turn-1)/game.getPlayers().size()+1), (int)(getWidth()/1.12), (int)(getHeight()/1.072));
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
            g.setFont(new Font("j", 1, (int)(getHeight()/30.72)));
            //System.out.println((int)(getHeight()/30.72) + " - 27");
            g.drawString("Player "+(e+1), (int)(getWidth()/1.199), (int)(getHeight()/11.134)+i*(int)(getHeight()/4.576));
            g.drawImage(icons.get("pinecone"), (int)(getWidth()/1.153), (int)(getHeight()/6.879)+i*(int)(getHeight()/4.576), (int)(getWidth()/28.657), (int)(getHeight()/20), null);
            g.setColor(Color.white);
            g.drawString("x "+game.getPlayers().get(e).getPineCones(), (int)(getWidth()/1.097), (int)(getHeight()/5.4)+i*(int)(getHeight()/4.576));
            //rotateImage(g, game.getPlayers().get(e).getBoard().getStartTile().getImage(), (int)(getWidth()/1.253), (int)(getHeight()/7.297)+i*(int)(getHeight()/4.576), (int)(getWidth()/56.471), (int)(getHeight()/27.692), 60*(6-game.getPlayers().get(e).getBoard().getStartTile().getOrientation()));
            //rotateImage(g, game.getPlayers().get(e).getBoard().getStartTile().getAdjacent(3).getImage(), (int)(getWidth()/1.24), (int)(getHeight()/6.102)+i*(int)(getHeight()/4.576), (int)(getWidth()/56.471), (int)(getHeight()/27.692), 60*(6-game.getPlayers().get(e).getBoard().getStartTile().getAdjacent(3).getOrientation()));
            //rotateImage(g, game.getPlayers().get(e).getBoard().getStartTile().getAdjacent(4).getImage(), (int)(getWidth()/1.266), (int)(getHeight()/6.102)+i*(int)(getHeight()/4.576), (int)(getWidth()/56.471), (int)(getHeight()/27.692), 60*(6-game.getPlayers().get(e).getBoard().getStartTile().getAdjacent(4).getOrientation()));
            ArrayList<Tile> t = game.getPlayers().get(e).getBoard().traverse();
            game.getPlayers().get(e).getBoard().updateTileCoordsSmall((int)(getWidth()/1.24), (int)(getHeight()/7.297)+i*(int)(getHeight()/4.576), getWidth(), getHeight());
            for(int j = 0; j<t.size(); j++) {
                //System.out.println(j+" is j and /t"+t.size()+" is the size");
                if (t.get(j).getXCoord() >= (int) (getWidth() / 1.303) && t.get(j).getXCoord()+(int) (getWidth() / 54.857)<= (int) (getWidth() / 1.159) && t.get(j).getYCoord() >= (int) (getHeight() / 10.485)+i*(int)(getHeight()/4.576) && t.get(j).getYCoord() + (int) (getHeight() / 27.692) <= (int) (getHeight() / 4.32)+i*(int)(getHeight()/4.576)) {
                    rotateImage(g, t.get(j).getImage(), t.get(j).getXCoord(), t.get(j).getYCoord(), (int) (getWidth() / 54.857), (int) (getHeight() / 27.692), 60 * (6 - t.get(j).getOrientation()));
                    if(t.get(j).hasAnimal()) {
                        g.drawImage(tokenImages.get(t.get(j).getAnimal()), t.get(j).getXCoord()+(int)(getWidth()/384), t.get(j).getYCoord()+(int)(getHeight()/154.286), (int)(getWidth()/76.8), (int)(getHeight()/43.2), null);
                    }
                }
            }
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
            g.setFont(new Font("h", 1, (int)(getHeight()/31.902)));
            //System.out.println((int)(getHeight()/31.902) + " - 26");
            g.drawString("OK", (int)(getWidth()/1.168), (int)(getHeight()/1.195));
            if(gameState == 4) {
                g2.drawRect((int)(getWidth()/1.306), (int)(getHeight()/1.444), (int)(getWidth()/12.715), (int)(getHeight()/11.739));
                g2.drawRect((int)(getWidth()/1.116), (int)(getHeight()/1.444), (int)(getWidth()/12.715), (int)(getHeight()/11.739));
                g.setFont(new Font("h", 1, (int)(getHeight()/41.472)));
                //System.out.println((int)(getHeight()/41.472) + " - 20");
                g.drawString("Rotate", (int)(getWidth()/1.276), (int)(getHeight()/1.378));
                g.drawString("Left", (int)(getWidth()/1.263), (int)(getHeight()/1.315));
                g.drawString("Rotate", (int)(getWidth()/1.095), (int)(getHeight()/1.383));
                g.drawString("Right", (int)(getWidth()/1.088), (int)(getHeight()/1.319));

            }
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
        game.getCurrentPlayer().getBoard().updateTileCoords((int)(getWidth()/2.46)+shiftX*(int)(getWidth()/13.714), (int)(getHeight()/4.5)+shiftY*(int)(getHeight()/6.545), getWidth(), getHeight());
        switch(i) {//g.fillRect((int)(getWidth()/6.784), (int)(getHeight()/8.571), (int)(getWidth()/1.69), (int)(getHeight()/1.636));
            case 1: {
                ArrayList<Tile> temp = game.getCurrentPlayer().getBoard().traverse();
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
                ArrayList<Tile> temp = game.getCurrentPlayer().getBoard().traverse();
                boolean b = false;
                for(int e = 0; e<temp.size(); e++) {
                    Tile t = temp.get(e);
                    if(t.getXCoord()+(int)(getWidth()/12.5)<=(int)(getWidth()/6.784)+(int)(getWidth()/1.69)) {
                        b = true;
                    }
                }
                if(b)
                    shiftX++;
                break;
            }
            case 3: {
                ArrayList<Tile> temp = game.getCurrentPlayer().getBoard().traverse();
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
                ArrayList<Tile> temp = game.getCurrentPlayer().getBoard().traverse();
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
        g.setColor(colors.get(game.getPlayerNum()));
        switch(getGameState())  {
            case 2 : {
                for(int i = 0; i< 4; i++) {
                    int[] xC = {(int)(getWidth()/5.424), (int)(getWidth()/6.508), (int)(getWidth()/6.508), (int)(getWidth()/5.424), (int)(getWidth()/4.638), (int)(getWidth()/4.638)};
                    int[] yC = {(int)(getHeight()/1.306), (int)(getHeight()/1.306)+(int)(getHeight()/30.857), (int)(getHeight()/1.306)+(int)(getHeight()/10.286), (int)(getHeight()/1.306)+(int)(getHeight()/7.777), (int)(getHeight()/1.306)+(int)(getHeight()/10.286), (int)(getHeight()/1.306)+(int)(getHeight()/30.857)};
                    if(i>0) {
                        for(int e = 0; e<6; e++) {
                            xC[e] = xC[e]+i*(getWidth()/15);
                        }
                    }
                    g.fillPolygon(xC, yC, 6);
                    g.fillOval((int)(getWidth()/6.174)+i*(getWidth()/15), (int)(getHeight()/1.13), (int)(getWidth()/21.818), (int)(getHeight()/12.273));
                }
                break;
            }
            case 3: {
                for(int i = 0; i<4; i++) {
                    if (i==tileChosenNum) {
                        int[] xC = {(int)(getWidth()/5.424), (int)(getWidth()/6.508), (int)(getWidth()/6.508), (int)(getWidth()/5.424), (int)(getWidth()/4.638), (int)(getWidth()/4.638)};
                        int[] yC = {(int)(getHeight()/1.306), (int)(getHeight()/1.306)+(int)(getHeight()/30.857), (int)(getHeight()/1.306)+(int)(getHeight()/10.286), (int)(getHeight()/1.306)+(int)(getHeight()/7.777), (int)(getHeight()/1.306)+(int)(getHeight()/10.286), (int)(getHeight()/1.306)+(int)(getHeight()/30.857)};
                        if(i>0) {
                            for(int e = 0; e<6; e++) {
                                xC[e] = xC[e]+i*(getWidth()/15);
                            }
                        }
                        g.fillPolygon(xC, yC, 6);
                    }
                    if(i == tokenChosenNum) {
                        g.fillOval((int)(getWidth()/6.174)+i*(getWidth()/15), (int)(getHeight()/1.13), (int)(getWidth()/21.818), (int)(getHeight()/12.273));
                    }
                }

                break;
            }
            case 4, 5:{
                for(int i = 0; i<4; i++) {
                    if(i == tokenChosenNum) {
                        g.fillOval((int)(getWidth()/6.174)+i*(getWidth()/15), (int)(getHeight()/1.13), (int)(getWidth()/21.818), (int)(getHeight()/12.273));
                    }
                }
                break;
            }
            case 8: {
                for(int i = 0; i<4; i++) {
                    if(tempClearedTokens[i]!=null) {
                        g.fillOval((int)(getWidth()/6.174)+i*(getWidth()/15), (int)(getHeight()/1.13), (int)(getWidth()/21.818), (int)(getHeight()/12.273));
                    }
                }
            }
            case 9: {
                for(int i = 0; i<4; i++) {
                    if (i==tileChosenNum) {
                        int[] xC = {(int)(getWidth()/5.424), (int)(getWidth()/6.508), (int)(getWidth()/6.508), (int)(getWidth()/5.424), (int)(getWidth()/4.638), (int)(getWidth()/4.638)};
                        int[] yC = {(int)(getHeight()/1.306), (int)(getHeight()/1.306)+(int)(getHeight()/30.857), (int)(getHeight()/1.306)+(int)(getHeight()/10.286), (int)(getHeight()/1.306)+(int)(getHeight()/7.777), (int)(getHeight()/1.306)+(int)(getHeight()/10.286), (int)(getHeight()/1.306)+(int)(getHeight()/30.857)};
                        if(i>0) {
                            for(int e = 0; e<6; e++) {
                                xC[e] = xC[e]+i*(getWidth()/15);
                            }
                        }
                        g.fillPolygon(xC, yC, 6);
                    }
                    if(i == tokenChosenNum) {
                        g.fillOval((int)(getWidth()/6.174)+i*(getWidth()/15), (int)(getHeight()/1.13), (int)(getWidth()/21.818), (int)(getHeight()/12.273));
                    }
                }
            }
        }
    }

    public void drawScoreBoard(Graphics g){

        g.setColor(new Color(0,0,0,103));
        g.fillRect((int)(getWidth() / 76.15), (int)(getHeight() / 49.09),(int)(getWidth() / 3.72) ,(int)(getHeight() / 1.0465));
        g.fillRect((int)(getWidth() / 3.3), (int)(getHeight() / 49.09), (int)(getWidth() / 2.31), (int)(getHeight() / 1.0465));
        g.fillRect((int)(getWidth() / 1.327), (int)(getHeight() / 49.09), (int)(getWidth() / 4.295), (int)(getHeight() / 1.0465));
        g.setColor(Color.white);

        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke((int)(getHeight()/270)));
        //Animals vertical lines
        g2.drawLine((int)(getWidth() / 14.545), (int)(getHeight() / 10.909), (int)(getWidth() / 14.545), (int)(getHeight() / 1.069));
        g2.drawLine((int)(getWidth() / 8.384), (int)(getHeight() / 10.909), (int)(getWidth() / 8.384),(int)(getHeight() / 1.069));
        g2.drawLine((int)(getWidth() / 5.836), (int)(getHeight() / 10.909), (int)(getWidth() / 5.836), (int)(getHeight() / 1.069));
        g2.drawLine((int)(getWidth() / 4.507), (int)(getHeight() / 10.909), (int)(getWidth() / 4.507), (int)(getHeight() / 1.069));
        //animals horizontal lines
        g2.drawLine((int)(getWidth() / 23.133), (int)(getHeight() / 7.770), (int)(getWidth() / 3.840), (int)(getHeight() / 7.770));
        g2.drawLine((int)(getWidth() / 23.133), (int)(getHeight() / 4.138), (int)(getWidth() / 3.840), (int)(getHeight() / 4.138));
        g2.drawLine((int)(getWidth() / 23.133), (int)(getHeight() / 2.707), (int)(getWidth() / 3.840), (int)(getHeight() / 2.707));
        g2.drawLine((int)(getWidth() / 23.133), (int)(getHeight() / 1.974), (int)(getWidth() / 3.840), (int)(getHeight() / 1.974));
        g2.drawLine((int)(getWidth() / 23.133), (int)(getHeight() / 1.565), (int)(getWidth() / 3.840), (int)(getHeight() / 1.565));
        g2.drawLine((int)(getWidth() / 23.133), (int)(getHeight() / 1.287), (int)(getWidth() / 3.840), (int)(getHeight() / 1.287));


        //habitats vertical
        g2.drawLine((int)(getWidth() / 2.763), (int)(getHeight() / 11.613), (int)(getWidth() / 2.763), (int)(getHeight() / 1.068));
        g2.drawLine((int)(getWidth() / 2.243), (int)(getHeight() / 11.613), (int)(getWidth() / 2.243), (int)(getHeight() / 1.068));
        g2.drawLine((int)(getWidth() / 1.870), (int)(getHeight() / 11.613), (int)(getWidth() / 1.870), (int)(getHeight() / 1.068));
        g2.drawLine((int)(getWidth() / 1.585), (int)(getHeight() / 11.613), (int)(getWidth() / 1.585), (int)(getHeight() / 1.068));

        //habitats horizontal
        g2.drawLine((int)(getWidth() / 3.014), (int)(getHeight() / 7.660), (int)(getWidth() / 1.400), (int)(getHeight() / 7.660));
        g2.drawLine((int)(getWidth() / 3.014), (int)(getHeight() / 4.286), (int)(getWidth() / 1.400), (int)(getHeight() / 4.286));
        g2.drawLine((int)(getWidth() / 3.014), (int)(getHeight() / 2.872), (int)(getWidth() / 1.400), (int)(getHeight() / 2.872));
        g2.drawLine((int)(getWidth() / 3.014), (int)(getHeight() / 2.173), (int)(getWidth() / 1.400), (int)(getHeight() / 2.173));
        g2.drawLine((int)(getWidth() / 3.014), (int)(getHeight() / 1.725), (int)(getWidth() / 1.400), (int)(getHeight() / 1.725));
        g2.drawLine((int)(getWidth() / 3.014), (int)(getHeight() / 1.415), (int)(getWidth() / 1.400), (int)(getHeight() / 1.415));
        g2.drawLine((int)(getWidth() / 3.014), (int)(getHeight() / 1.204), (int)(getWidth() / 1.400), (int)(getHeight() / 1.204));
        //habitats diagonal
        g2.drawLine((int)(getWidth() / 2.763), (int)(getHeight() / 7.606), (int)(getWidth() / 2.243), (int)(getHeight() / 29.189));
        g2.drawLine((int)(getWidth() / 2.243), (int)(getHeight() / 7.606), (int)(getWidth() / 1.870), (int)(getHeight() / 29.189));
        g2.drawLine((int)(getWidth() / 1.870), (int)(getHeight() / 7.606), (int)(getWidth() / 1.585), (int)(getHeight() / 29.189));
        g2.drawLine((int)(getWidth() / 1.585), (int)(getHeight() / 7.606), (int)(getWidth() / 1.400), (int)(getHeight() / 29.189));

        g2.drawLine((int)(getWidth() / 2.763), (int)(getHeight() / 4.269), (int)(getWidth() / 2.243), (int)(getHeight() / 7.606));
        g2.drawLine((int)(getWidth() / 2.243), (int)(getHeight() / 4.269), (int)(getWidth() / 1.870), (int)(getHeight() / 7.606));
        g2.drawLine((int)(getWidth() / 1.870), (int)(getHeight() / 4.269), (int)(getWidth() / 1.585), (int)(getHeight() / 7.606));
        g2.drawLine((int)(getWidth() / 1.585), (int)(getHeight() / 4.269), (int)(getWidth() / 1.400), (int)(getHeight() / 7.606));

        g2.drawLine((int)(getWidth() / 2.763), (int)(getHeight() / 4.252), (int)(getWidth() / 2.243), (int)(getHeight() / 4.269));
        g2.drawLine((int)(getWidth() / 2.243), (int)(getHeight() / 4.252), (int)(getWidth() / 1.870), (int)(getHeight() / 4.269));
        g2.drawLine((int)(getWidth() / 1.870), (int)(getHeight() / 4.252), (int)(getWidth() / 1.585), (int)(getHeight() / 4.269));
        g2.drawLine((int)(getWidth() / 1.585), (int)(getHeight() / 4.252), (int)(getWidth() / 1.400), (int)(getHeight() / 4.269));

        g2.drawLine((int)(getWidth() / 2.763), (int)(getHeight() / 2.865), (int)(getWidth() / 2.243), (int)(getHeight() / 4.252));
        g2.drawLine((int)(getWidth() / 2.243), (int)(getHeight() / 2.865), (int)(getWidth() / 1.870), (int)(getHeight() / 4.252));
        g2.drawLine((int)(getWidth() / 1.870), (int)(getHeight() / 2.865), (int)(getWidth() / 1.585), (int)(getHeight() / 4.252));
        g2.drawLine((int)(getWidth() / 1.585), (int)(getHeight() / 2.865), (int)(getWidth() / 1.400), (int)(getHeight() / 4.252));

        g2.drawLine((int)(getWidth() / 2.763), (int)(getHeight() / 2.160), (int)(getWidth() / 2.243), (int)(getHeight() / 2.865));
        g2.drawLine((int)(getWidth() / 2.243), (int)(getHeight() / 2.160), (int)(getWidth() / 1.870), (int)(getHeight() / 2.865));
        g2.drawLine((int)(getWidth() / 1.870), (int)(getHeight() / 2.160), (int)(getWidth() / 1.585), (int)(getHeight() / 2.865));
        g2.drawLine((int)(getWidth() / 1.585), (int)(getHeight() / 2.160), (int)(getWidth() / 1.400), (int)(getHeight() / 2.865));

        g2.drawLine((int)(getWidth() / 2.763), (int)(getHeight() / 1.722), (int)(getWidth() / 2.243), (int)(getHeight() / 2.160));
        g2.drawLine((int)(getWidth() / 2.243), (int)(getHeight() / 1.722), (int)(getWidth() / 1.870), (int)(getHeight() / 2.160));
        g2.drawLine((int)(getWidth() / 1.870), (int)(getHeight() / 1.722), (int)(getWidth() / 1.585), (int)(getHeight() / 2.160));
        g2.drawLine((int)(getWidth() / 1.585), (int)(getHeight() / 1.722), (int)(getWidth() / 1.400), (int)(getHeight() / 2.160));

        //icons
        for(int i = 0; i<5; i++) {
            g.drawImage(tempTokenImages.get(i), (int)(getWidth()/41.739), (int)(getHeight()/6.75)+i*(int)(getHeight()/7.8), (int)(getWidth()/23.704), (int)(getHeight()/13.333), null);
            g.drawImage(icons.get("t"+(i+1)), (int)(getWidth()/3.232), (int)(getHeight()/23.478)+i*(int)(getHeight()/9.1), (int)(getWidth()/20.211), (int)(getHeight()/13.333), null);
        }
        g.drawImage(icons.get("person"), (int)(getWidth() / 32.266), (int)(getHeight() / 18), (int)(getWidth() / 28.657), (int)(getHeight() / 14.4), null);
        g.setFont(new Font("j", 1, (int)(getHeight()/18.500)));
        for(int i = 0; i<game.getPlayers().size(); i++) {
            g.setColor(colors.get(i));
            g.drawString(i+1+"", (int)(getWidth() / 11.636)+i*(int)(getWidth()/20.645), (int)(getHeight() / 9.31));
        }
        g.setColor(Color.white);
        g.drawString("W",(int)(getWidth() / 32.542), (int)(getHeight() / 1.138));
        g.drawString("H",(int)(getWidth() / 3.102), (int)(getHeight() / 1.506));
        g.drawString("T",(int)(getWidth() / 3.102), (int)(getHeight() / 1.098));
        g.drawImage(icons.get("pinecone"), (int)(getWidth() / 3.232), (int)(getHeight() / 1.355), (int)(getWidth() / 22.857), (int)(getHeight() / 15.882), null);

        //Animal scores
        int x = 0;
        g.setFont(new Font("j", 1, (int)(getHeight()/18.500)));
        for (int i = 0; i < game.getPlayers().size();i++)
        {
            g.setColor(colors.get(i));
            for (int j = 0; j < 6; j++)
            {
                g.drawString(""+ game.getPlayers().get(i).getScore()[j], (int)(getWidth()/12.075) + x, (int)(getHeight()/5.000) + j*(int)(getHeight()/7.714));
            }
            x += (int)(getWidth()/20.000);
        }


        //Habitat scores
        int x2 = 0;
        int count = 0;
        if (game.getPlayers().size() >= 1) {
            g.setColor(colors.get(0));

            for (int j = 6; j < 16; j += 2)//
            {
                g.drawString("" +game.getPlayers().get(0).getScore()[j], (int) (getWidth() / 2.7) + x2, (int) (getHeight() / 11.868) + count * (int) (getHeight() / 9));
                count++;
            }
        }

        if (game.getPlayers().size() >= 2) {
            x2 += (int) (getWidth() / 12);
            g.setColor(colors.get(1));
            count = 0;
            for (int j = 6; j < 16; j += 2)//game.getPlayers().get(i).getScore()[j]
            {
                g.drawString("" + game.getPlayers().get(1).getScore()[j], (int) (getWidth() / 2.7) + x2, (int) (getHeight() / 11.868) + count * (int) (getHeight() / 9));
                count++;
            }
        }

        if (game.getPlayers().size() >= 3) {
            x2 += (int) (getWidth() / 11.5);
            g.setColor(colors.get(2));
            count = 0;
            for (int j = 6; j < 16; j += 2)//game.getPlayers().get(i).getScore()[j]
            {
                g.drawString("" + game.getPlayers().get(2).getScore()[j], (int) (getWidth() / 2.7) + x2, (int) (getHeight() / 11.868) + count * (int) (getHeight() / 9));
                count++;
            }
        }

        if (game.getPlayers().size() == 4) {
            x2 += (int) (getWidth() / 10.5);
            g.setColor(colors.get(3));
            count = 0;
            for (int j = 6; j < 16; j += 2)//game.getPlayers().get(i).getScore()[j]
            {
                g.drawString("" + game.getPlayers().get(3).getScore()[j], (int) (getWidth() / 2.7) + x2, (int) (getHeight() / 11.868) + count * (int) (getHeight() / 9));
                count++;
            }
        }

        //total animals



        //bonues
        g.setFont(new Font("j", Font.PLAIN, (int)(getHeight()/18.500)));
        g.setColor(Color.white);
        x2 = 0;
        count = 0;
        //Habitat scores
        if (game.getPlayers().size() >= 1) {
            g.setColor(Color.white);

            for (int j = 7; j < 17; j += 2)//game.getPlayers().get(0).getScore()[j],
            {
                g.drawString("" + game.getPlayers().get(0).getScore()[j], (int) (getWidth() / 2.45) + x2, (int) (getHeight() / 8.5) + count * (int) (getHeight() / 9));
                count++;
            }
        }

        if (game.getPlayers().size() >= 2) {
            x2 += (int) (getWidth() / 11.5);
            g.setColor(Color.white);
            count = 0;
            for (int j = 7; j < 17; j += 2)//game.getPlayers().get(0).getScore()[j],
            {
                g.drawString("" + game.getPlayers().get(1).getScore()[j], (int) (getWidth() / 2.45) + x2, (int) (getHeight() / 8.5) + count * (int) (getHeight() / 9));
                count++;
            }
        }

        if (game.getPlayers().size() >= 3) {
            x2 += (int) (getWidth() / 11.1);
            g.setColor(Color.white);
            count = 0;
            for (int j = 7; j < 17; j += 2)//game.getPlayers().get(0).getScore()[j],
            {
                g.drawString("" + game.getPlayers().get(2).getScore()[j], (int) (getWidth() / 2.45) + x2, (int) (getHeight() / 8.5) + count * (int) (getHeight() / 9));
                count++;
            }
        }

        if (game.getPlayers().size() == 4) {
            x2 += (int) (getWidth() / 11);
            g.setColor(Color.white);
            count = 0;
            for (int j = 7; j < 17; j += 2)//game.getPlayers().get(0).getScore()[j],
            {
                g.drawString("" + game.getPlayers().get(3).getScore()[j], (int) (getWidth() / 2.45) + x2, (int) (getHeight() / 8.5) + count * (int) (getHeight() / 9));
                count++;
            }
        }

        //total habitats
        x = 0;
        g.setFont(new Font("j", 1, (int)(getHeight()/18.500)));
        for (int i = 0; i < game.getPlayers().size();i++)
        {
            g.setColor(colors.get(i));
            g.drawString(""+game.getPlayers().get(i).getScore()[16], (int) (getWidth() / 2.609) + (int) (getWidth() / 10.667) * i, (int) (getHeight() / 1.5));
            //x += (int)(getWidth()/5.838);
        }
        //game.getPlayers();


        //pine cones
        x = 0;
        g.setFont(new Font("j", 1, (int)(getHeight()/18.500)));
        for (int i = 0; i < game.getPlayers().size();i++)
        {
            g.setColor(colors.get(i));
            g.drawString(""+game.getPlayers().get(i).getScore()[17], (int) (getWidth() / 2.609) + (int) (getWidth() / 10.667) * i, (int) (getHeight() / 1.265));
            //x += (int)(getWidth()/5.838);
        }
        //game.getPlayers();

        //final score
        x = 0;
        g.setFont(new Font("j", 1, (int)(getHeight()/18.500)));
        for (int i = 0; i < game.getPlayers().size();i++)
        {
            g.setColor(colors.get(i));
            g.drawString(""+game.getPlayers().get(i).getScore()[18], (int) (getWidth() / 2.609) + (int) (getWidth() / 10.667) * i, (int) (getHeight() / 1.102));
            //x += (int)(getWidth()/5.838);
        }
        //game.getPlayers();

    }
    public void drawLeaderboard(Graphics g)
    {
        g.setColor(Color.white);
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke((int)(getHeight()/270)));

        for(int i = 0; i<game.getPlayers().size(); i++) {
            g.setColor(Color.white);
            g2.drawRect((int)(getWidth()/1.306), (int)(getHeight()/27)+i*(int)(getHeight()/4.576), (int)(getWidth()/4.788), (int)(getHeight()/5.023));
            g.drawImage(icons.get("open"), (int)(getWidth()/1.06), (int)(getHeight()/27)+i*(int)(getHeight()/4.576), (int)(getWidth()/32.542), (int)(getHeight()/18.305), null);
            g.setColor(colors.get(i));
            g.setFont(new Font("j", 1, (int)(getHeight()/30.72)));
            //System.out.println((int)(getHeight()/30.72) + " - 27");
            g.drawString("Player "+(i+1), (int)(getWidth()/1.199), (int)(getHeight()/11.134)+i*(int)(getHeight()/4.576));
            g.drawImage(icons.get("pinecone"), (int)(getWidth()/1.153), (int)(getHeight()/6.879)+i*(int)(getHeight()/4.576), (int)(getWidth()/28.657), (int)(getHeight()/20), null);
            g.setColor(Color.white);
            g.drawString("x "+game.getPlayers().get(i).getPineCones(), (int)(getWidth()/1.097), (int)(getHeight()/5.4)+i*(int)(getHeight()/4.576));
            //rotateImage(g, game.getPlayers().get(i).getBoard().getStartTile().getImage(), (int)(getWidth()/1.253), (int)(getHeight()/7.297)+i*(int)(getHeight()/4.576), (int)(getWidth()/56.471), (int)(getHeight()/27.692), 60*(6-game.getPlayers().get(i).getBoard().getStartTile().getOrientation()));
            //rotateImage(g, game.getPlayers().get(i).getBoard().getStartTile().getAdjacent(3).getImage(), (int)(getWidth()/1.24), (int)(getHeight()/6.102)+i*(int)(getHeight()/4.576), (int)(getWidth()/56.471), (int)(getHeight()/27.692), 60*(6-game.getPlayers().get(i).getBoard().getStartTile().getAdjacent(3).getOrientation()));
            //rotateImage(g, game.getPlayers().get(i).getBoard().getStartTile().getAdjacent(4).getImage(), (int)(getWidth()/1.266), (int)(getHeight()/6.102)+i*(int)(getHeight()/4.576), (int)(getWidth()/56.471), (int)(getHeight()/27.692), 60*(6-game.getPlayers().get(i).getBoard().getStartTile().getAdjacent(4).getOrientation()));
            ArrayList<Tile> t = game.getPlayers().get(i).getBoard().traverse();
            game.getPlayers().get(i).getBoard().updateTileCoordsSmall((int)(getWidth()/1.24), (int)(getHeight()/7.297)+i*(int)(getHeight()/4.576), getWidth(), getHeight());
            for(int j = 0; j<t.size(); j++) {
                //System.out.println(j+" is j and /t"+t.size()+" is the size");
                if (t.get(j).getXCoord() >= (int) (getWidth() / 1.303) && t.get(j).getXCoord()+(int) (getWidth() / 54.857)<= (int) (getWidth() / 1.159) && t.get(j).getYCoord() >= (int) (getHeight() / 10.485)+i*(int)(getHeight()/4.576) && t.get(j).getYCoord() + (int) (getHeight() / 27.692) <= (int) (getHeight() / 4.32)+i*(int)(getHeight()/4.576)) {
                    rotateImage(g, t.get(j).getImage(), t.get(j).getXCoord(), t.get(j).getYCoord(), (int) (getWidth() / 54.857), (int) (getHeight() / 27.692), 60 * (6 - t.get(j).getOrientation()));
                    if(t.get(j).hasAnimal()) {
                        g.drawImage(tokenImages.get(t.get(j).getAnimal()), t.get(j).getXCoord()+(int)(getWidth()/384), t.get(j).getYCoord()+(int)(getHeight()/154.286), (int)(getWidth()/76.8), (int)(getHeight()/43.2), null);
                    }
                }
            }
            //medals
        }
        TreeMap<Integer, ArrayList<Integer>> temp = game.getRanking();
        System.out.println(temp.keySet().size());
        Iterator<Integer> it = temp.keySet().iterator();
        while(it.hasNext()) {
            int num = it.next();
            ArrayList<Integer> a = temp.get(num);
            System.out.println(num+" = "+a);
            for(int i = 0; i<a.size(); i++) {
                g.drawImage(icons.get(num+""), (int)(getWidth()/1.311), (int)(getHeight()/32.727)+a.get(i)*(int)(getHeight()/4.635), (int)(getWidth()/23.133), (int)(getHeight()/9.474), null);
            }
        }

        //main menu button
        g2.drawRect((int)(getWidth()/1.305), (int)(getHeight()/1.117), (int)(getWidth()/4.788), (int)(getHeight()/16.119));
        g.drawString("Main menu", (int)(getWidth()/1.211), (int)(getHeight()/1.067));
    }
    public void mouseClicked(MouseEvent e) {    }
    public void mouseEntered(MouseEvent e) {    }
    public void mouseExited(MouseEvent e) {    }
    public void mouseReleased(MouseEvent e) {   }
    public void mousePressed(MouseEvent e)
    {
        int x = e.getX();
        int y = e.getY();
//        System.out.println(x +" " +y);


        if(getGameState()!=0 && getGameState() !=1 && getGameState() !=10 && !player.isVisible()) {
            if(x>=(int) (getWidth() / 1.235) && x<=(int)(getWidth()/1.076) && y>=(int)(getHeight()/1.401) && y<=(int)(getHeight()/1.065)) {
                if(x>=(int)(getWidth()/1.176) && x<=(int)(getWidth()/1.176)+(int)(getWidth()/25.6) && y>=(int)(getHeight()/1.401) && y<=(int)(getHeight()/1.401)+(int)(getHeight()/14.4)) {
                    shift(3);
                    repaint();
                }
                else if(x>=(int)(getWidth()/1.117) && x<=(int)(getWidth()/1.076) && y>=(int)(getHeight()/1.26) && y<=(int)(getHeight()/1.26)+(int)(getHeight()/14.4)) {
                    shift(4);
                    repaint();
                }
                else if(x>=(int) (getWidth() / 1.176) && x<=(int) (getWidth() / 1.124) && y>=(int)(getHeight()/1.145) && y<=(int)(getHeight()/1.065)) {
                    shift(1);
                    repaint();
                }
                else if(x>=(int) (getWidth() / 1.235) && x<=(int) (getWidth() / 1.184) && y>=(int)(getHeight()/1.26) && y<=(int)(getHeight()/1.26)+(int)(getHeight()/14.4)) {
                    shift(2);
                    repaint();
                }
            }
        }
        if(help.isVisible()) {
            help.mouseClicked(x, y, getWidth(), getHeight());
            repaint();
        }
        else if(player.isVisible()) {
            player.mouseClicked(x, y);
            repaint();
        }//(int)(getWidth()/41.739), (int)(getHeight()/30), (int)(getWidth()/10.105), (int)(getHeight()/17.143)
        else if(getGameState()!=0 && getGameState() !=1 && getGameState() !=10 && x>=(int)(getWidth()/41.739) && x<=(int)(getWidth()/41.739)+(int)(getWidth()/10.105) && y>=(int)(getHeight()/30) && y<=(int)(getHeight()/30)+(int)(getHeight()/17.143)) {
            help.setVisible(true);
            repaint();
        }
        else if(getGameState()!=0 && getGameState() !=1 && getGameState() !=10 && x>=(int)(getWidth()/1.06) && x<=(int)(getWidth()/1.06)+(int)(getWidth()/32.542)&& y <= (int) (getHeight() / 1.765)) {
            //System.out.println("Im stupid");
            int a = 0;
            for (int i = 0; i < game.getPlayers().size() - 1; i++) {
                if (a == game.getPlayerNum()) {
                    a++;
                }//(int)(getWidth()/1.06), (int)(getHeight()/27)+i*(int)(getHeight()/4.576), (int)(getWidth()/32.542), (int)(getHeight()/18.305)
                if (y >= (int) (getHeight() / 27) + i * (int) (getHeight() / 4.576) && y <= (int) (getHeight() / 27) + i * (int) (getHeight() / 4.576) + (int) (getHeight() / 18.305)) {
                    player.setPlayer(game.getPlayers().get(a), a==game.getPlayers().size() - 1);
                    player.setVisible(true);
                    player.setEndGame(false);
                    repaint();
                }
                a++;
            }
        }
        else {
            switch (getGameState()) {
                case 0: {
                    if (start.stateChangeClick(x, y)) {
                        setGameState(1);
                    }
                    repaint();
                    break;
                }
                case 1: {
                    int pat = start.playerAmountClick(x, y);
                    if (pat > 0) {
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
                case 2: {
                    //4 animal overpopulation
                    if (game.checkOverpopulation(false) == 4)
                        while (game.checkOverpopulation(false) == 4) {
                            game.checkOverpopulation(true);
                            repaint();
                        }

                    //pinecones clicked
                    //(int) (getWidth() / 2.333), (int) (getHeight() / 1.251), (int) (getWidth() / 6.784), (int) (getHeight() / 15.652)
                    else if (x >= (int) (getWidth() / 2.333) && x <= (int) (getWidth() / 2.333) + (int) (getWidth() / 6.784) && y >= (int) (getHeight() / 1.251) && y <= (int) (getHeight() / 1.251) + (int) (getHeight() / 15.652) && game.getCurrentPlayer().getPineCones() > 0) //coordinates for use pinecone button
                    {
                        usePineConesClicked = true;
                        setGameState(7);
                    }


                    //clear tokens button clicked              IDK ABOUT THIS NOT SURE
                    //(int) (getWidth() / 2.333), (int) (getHeight() / 1.121), (int) (getWidth() / 6.784), (int) (getHeight() / 15.652)
                    else if (!overpop3AlrUsed && game.checkOverpopulation(false) == 3 && x >= (int) (getWidth() / 2.333) && x <= (int) (getWidth() / 2.333) + (int) (getWidth() / 6.784) && y >= (int) (getHeight() / 1.121) && y <= (int) (getHeight() / 1.121) + (int) (getHeight() / 15.652)) //coordinates for clear token button
                    {
                        System.out.println("Before");
                        for (int i = 0; i < 4; i++)
                            System.out.print(game.getAvailableTiles()[i] + " ");
                        game.checkOverpopulation(true);
                        System.out.println("After");
                        for (int i = 0; i < 4; i++)
                            System.out.print(game.getAvailableTiles()[i] + " ");
                        overpop3AlrUsed = true;
                        System.out.print("tokens 3 clearewd");
                        repaint();
                    }


                    //tile clicked
                    else {
                        for (int i = 0; i < 4; i++) {
                            //System.out.println("reached");
                            if (game.getAvailableTiles()[i].isClicked(x, y, (int) (getWidth() / 19.01), (int) (getHeight() / 9.231))) {
                                tileChosenNum = i;
                                tokenChosenNum = i;
                                chosenTile = game.getAvailableTiles()[i];
                                chosenToken = game.getAvailableTokens()[i];
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
                    HashMap<Tile, ArrayList<Integer>> tilesWithNullSides = game.getCurrentPlayer().getBoard().allNullTiles();
                    Set<Tile> set = tilesWithNullSides.keySet();
                    Iterator<Tile> iter = set.iterator(); //all tiles


                    while (iter.hasNext()) {
                        Tile temp = iter.next(); // each tile
                        ArrayList<Integer> nullSidesOfTile = tilesWithNullSides.get(temp); //each tile's null sides
                        for (int j = 0; j < nullSidesOfTile.size(); j++) {
                            game.getCurrentPlayer().getBoard().setBoardWidthandHeight(getWidth(), getHeight());
                            if (temp.ifNullTileClicked(nullSidesOfTile.get(j), x, y, (int) (getWidth() / 13.714), (int) (getHeight() / 6.545))) {
//                            System.out.println("Tile chosen's adjacent tiles: "+ getChosenTile().getAdjacentTiles());
//                            System.out.println("Tile chosen's x and y: ("+ getChosenTile().getXCoord() +", "+ getChosenTile().getYCoord() +")");

                                game.getCurrentPlayer().getBoard().addTile(getChosenTile(), temp, nullSidesOfTile.get(j));
//                                for(Tile t: game.getCurrentPlayer().getBoard().traverse()){
//                                    System.out.println(t.getAdjacentTiles());
//                                }
                                tilePlaced = true;
                                //System.out.println("Starting tile's habitats = "+ getChosenTile().getAllHabitats());
                                game.getAvailableTiles()[tileChosenNum] = null;
                                setGameState(4);
                                repaint();
                            }
                        }
                    }

                    break;
                }
                case 4: //tile orientation
                {
                    //left
                    if (x >= (int) (getWidth() / 1.306) && x <= (int) (getWidth() / 1.306) + (int) (getWidth() / 12.715) && y >= (int) (getHeight() / 1.444) && y <= (int) (getHeight() / 1.444) + (int) (getHeight() / 11.739)) {
                        chosenTile.rotateLeft();
                        repaint();
                    }
                    //System.out.println(x+" >= "+(int)(getWidth()/1.116)+" && "+x+" <= "+((int) (getWidth() / 1.116) + (int) (getWidth() / 12.715)));
                    //right
                    if (x >= (int) (getWidth() / 1.116) && x <= (int) (getWidth() / 1.116) + (int) (getWidth() / 12.715) && y >= (int) (getHeight() / 1.444) && y <= (int) (getHeight() / 1.444) + (int) (getHeight() / 11.739)) {
                        chosenTile.rotateRight();
                        repaint();
                    }//(int)(getWidth()/1.116), (int)(getHeight()/1.444), (int)(getWidth()/12.715), (int)(getHeight()/11.739)

                    //ok clicked
                    if (x >= (int) (getWidth() / 1.176) && x <= (int) (getWidth() / 1.176) + (int) (getWidth() / 25.6) && y >= (int) (getHeight() / 1.26) && y <= (int) (getHeight() / 1.26) + (int) (getHeight() / 14.4)/*ok button coordinates */) {
                        okClicked = true;
                        setGameState(5);
                    }
                    repaint();
                    break;
                }
                case 5: //choose token placement or throw away
                {
                    //token is thrown away

                    if (x >= (int) (getWidth() / 1.731) && x <= (int) (getWidth() / 1.731) + (int) (getWidth() / 6.906) && y >= (int) (getHeight() / 1.325) && y <= (int) (getHeight() / 1.325) + (int) (getHeight() / 15.652)) //throw away button clicked coordinates
                    {
                        throwAwayClicked = true;
                        tileTokenPlacement = null;
                        game.getTokenDeck().add(game.getAvailableTokens()[tokenChosenNum]);
                        game.getAvailableTokens()[tokenChosenNum] = null;
                        setGameState(6);
                    } else {
                        //get traversal of player tiles and use isClicked to see if player clicked on tile
                        ArrayList<Tile> temp = game.getCurrentPlayer().getBoard().traverse();
                        for (Tile t : temp) {
                            if (t.isClicked(x, y, (int) (getWidth() / 13.714), (int) (getHeight() / 6.545)) && !t.hasAnimal()) {
                                //token is placed on the tile they chose if animal matches
                                for (int i : t.getPossibleAnimals()) {
                                    if (i == chosenToken.getAnimal()) {
                                        tileTokenPlacement = t;
                                        tileTokenPlacement.setAnimal(chosenToken);
                                        tokenPlaced = true;
                                        if (tileTokenPlacement.isKeyStone()) {
                                            game.getCurrentPlayer().addPineCone();
                                        }
                                        game.getAvailableTokens()[tokenChosenNum] = null;
                                        setGameState(6);
                                        repaint();
                                    }
                                }
                            }
                        }
                    }
                    repaint();
                    break;
                }
                case 6: //check if next player button is clicked
                {
                    if (x >= (int) (getWidth() / 1.731) && x <= (int) (getWidth() / 1.731) + (int) (getWidth() / 6.906) && y >= (int) (getHeight() / 1.325) && y <= (int) (getHeight() / 1.325) + (int) (getHeight() / 15.652) && turn < game.getPlayers().size() * 20) {  // ONLY USED FOR TESTING SCORING REMEMBER TO CHANGE BACK TO 20!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                        nextPlayerClicked = true;
                        game.setCurrentPlayer((game.getPlayerNum() + 1) % game.getPlayers().size());
                        game.updateTileAndTokens();
                        setGameState(2);
                        System.out.println("Turn count = " + turn);
                        resetGameFlags();
                        turn++;
                    } else if (turn >= game.getPlayers().size() * 20) {
                        System.out.println("END GAME");
                        game.bonusesAndScores();
                        setGameState(10);
                    }
                    repaint();

                    break;
                }
                case 7: //use pinecone button
                {
                    if (x >= (int) (getWidth() / 2.333) && x <= (int) (getWidth() / 2.333) + (int) (getWidth() / 6.784) && y >= (int) (getHeight() / 1.308) && y <= (int) (getHeight() / 1.308) + (int) (getHeight() / 9.231)) // coordinates for clear token button
                    {
                        game.getCurrentPlayer().takePineCone();
                        chooseTileTokenClicked = true;
                        setGameState(9);
                    }
                    //x >= (int) (getWidth() / 2.333) && x <= (int) (getWidth() / 2.333)+(int) (getWidth() / 6.784) && y >= (int) (getHeight() / 1.121) && y <= (int) (getHeight() / 1.121) + (int) (getHeight() / 15.652)
                    else if (x >= (int) (getWidth() / 2.333) && x <= (int) (getWidth() / 2.333) + (int) (getWidth() / 6.784) && y >= (int) (getHeight() / 1.121) && y <= (int) (getHeight() / 1.121) + (int) (getHeight() / 15.652))// coordinates for specific tile and token button
                    {
                        game.getCurrentPlayer().takePineCone();
                        clearTokenClicked = true;
                        setGameState(8);
                    }
                    repaint();
                    break;
                }
                case 8: //clear tokens
                {//(int)(getWidth()/6.038)+i*(getWidth()/15), (int)(getHeight()/1.12), (int)(getWidth()/25.946), (int)(getHeight()/14.595)
                    for (int i = 0; i < 4; i++) {
                    /*
                    System.out.println(x+" "+y);
                    System.out.println(Math.pow((x - ((int) (getWidth() / 6.038) + i * (getWidth() / 15) - ((int) (getWidth() / 25.946) / 2))), 2)+" - x of i = "+i);
                    System.out.println(Math.pow((y - (int) (getHeight() / 1.12) - ((int) (getWidth() / 25.946)) / 2), 2)+" - y of i = "+i);
                    System.out.println(Math.pow(((int) (getWidth() / 25.946)) / 2, 2)+" - r of i = "+i);
                    Math.pow((x - ((int) (getWidth() / 6.038) + i * (getWidth() / 15) - ((int) (getWidth() / 25.946) / 2))), 2) + Math.pow((y - (int) (getHeight() / 1.12) - ((int) (getWidth() / 25.946)) / 2), 2) <= Math.pow(((int) (getWidth() / 25.946)) / 2, 2)
                    */
                        if (x >= (int) (getWidth() / 6.038) + i * (getWidth() / 15) && x <= (int) (getWidth() / 6.038) + i * (getWidth() / 15) + (int) (getWidth() / 25.946) && y >= (int) (getHeight() / 1.12) && y <= (int) (getHeight() / 1.12) + (int) (getHeight() / 14.595)) {
                            if (tempClearedTokens[i] != null) {
                                tempClearedTokens[i] = null;
                            } else {
                                tempClearedTokens[i] = game.getAvailableTokens()[i];
                            }
                        }
                    }
                    //ok button clicked
                    if (x >= (int) (getWidth() / 1.176) && x <= (int) (getWidth() / 1.176) + (int) (getWidth() / 25.6) && y >= (int) (getHeight() / 1.26) && y <= (int) (getHeight() / 1.26) + (int) (getHeight() / 14.4)) // ok button clicked coordinates
                    {
                        for (int i = 0; i < tempClearedTokens.length; i++) {
                            if (tempClearedTokens[i] != null) {
                                game.getTokenDeck().add(tempClearedTokens[i]);
                                game.getAvailableTokens()[i] = null;
                            }
                        }
                        game.updateTileAndTokens();
                        okClicked = true;
                        setGameState(2);
                    }
                    repaint();
                    break;


                }
                case 9: //choose specific tile and token
                {
                    //Check which tile and token has been clicked
                    for (int i = 0; i < 4; i++) {
                        if (game.getAvailableTiles()[i].isClicked(x, y, (int) (getWidth() / 19.01), (int) (getHeight() / 9.231))) {
                            tileChosenNum = i;
                            chosenTile = game.getAvailableTiles()[i];
                            tileClicked = true;
                        }
                        if (x >= (int) (getWidth() / 6.038) + i * (getWidth() / 15) && x <= (int) (getWidth() / 6.038) + i * (getWidth() / 15) + (int) (getWidth() / 25.946) && y >= (int) (getHeight() / 1.12) && y <= (int) (getHeight() / 1.12) + (int) (getHeight() / 14.595)) {
                            tokenChosenNum = i;
                            chosenToken = game.getAvailableTokens()[i];
                            tokenClicked = true;
                        }
                    }
                    //Check if ok button clicked
                    if (tokenClicked && tileClicked && x >= (int) (getWidth() / 1.176) && x <= (int) (getWidth() / 1.176) + (int) (getWidth() / 25.6) && y >= (int) (getHeight() / 1.26) && y <= (int) (getHeight() / 1.26) + (int) (getHeight() / 14.4)) {
                        okClicked = true;
                        setGameState(3);
                    }
                    repaint();
                    break;
                }
                case 10:
                {

                    if ( x >= (int)(getWidth()/1.305) && y >= (int)(getHeight()/1.117) && x <= (int)(getWidth()/4.800)+(int)(getWidth()/1.305) && y <= (int)(getHeight()/16.119) +(int)(getHeight()/1.117)) //mainMenu button clicking
                    {
                        resetGameFlags();
                        resetForNewGame();
                        setGameState(0);
                        System.out.println("CLick on main menu button");
                        repaint();

                    }
                    else if(x>=(int)(getWidth()/1.06) && x<=(int)(getWidth()/1.06)+(int)(getWidth()/32.542)) {
                        for(int i = 0; i<game.getPlayers().size(); i++) {
                            if(y >= (int)(getHeight()/27)+i*(int)(getHeight()/4.576) && y<= (int)(getHeight()/27)+i*(int)(getHeight()/4.576)+(int)(getHeight()/18.305)) {
                                player.setPlayer(game.getPlayers().get(i), i==game.getPlayers().size()-1);
                                player.setEndGame(true);
                                player.setVisible(true);
                                repaint();
                            }
                        }
                    }
                }
            }
        }
    }


    public Tile getChosenTile()
    {
        return chosenTile;
    }

    public void setChosenTile(Tile t)
    {
        chosenTile = t;
    }

    public Token getChosenToken()
    {
        return chosenToken;
    }

    public void setChosenToken(Token t)
    {
        chosenToken = t;
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
