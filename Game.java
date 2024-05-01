import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Game
{
    //    private int gameState;
    private ArrayList<Player> players;
    private Player currentPlayer;
    private int turn;
    private ArrayList<Tile> tileDeck;
    private HashMap<Integer, ArrayList<Tile>> starterTiles;
    private ArrayList<Token> tokenDeck;
    private Tile[] availableTiles;
    private Token[] availableTokens;
    private Tile tileChose;
    private Token tokenChose;
    private CascadiaPanel panel;
    private boolean alreadyTookTurn;
    //GameStates
    //0: main menu
    //1: choose player
    //2: main layout/choose tile options
    //3: choose tile placement
    //4: choose tile orientation
    //5: choose token placement or throw away
    //6: confirm and go to next player
    //7: choose pinecone options
    //8: choose tokens to clear
    //9: choose specific tile and token
    //10: end game



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
    //Not on Tile = 0

    public Game()
    {
        //Intatization of attributes
        players = new ArrayList<Player>();
        tileDeck = new ArrayList<Tile>();
        starterTiles = new HashMap<Integer, ArrayList<Tile>>(); //5 starterTiles so we have an int to represent each tile. StarterTiles are an arraylist of 3 tiles
        tokenDeck = new ArrayList<Token>();
        availableTiles = new Tile[4];
        availableTokens = new Token[4];
        tileChose = null;
        tokenChose = null;
        alreadyTookTurn = false;
        turn = 0;

        //create panel
        panel = new CascadiaPanel(this);

        //Create all tiles and tokens
        try {
            createGame();
        } catch (IOException e) {
            System.out.println("Error with exception of creating game");
        }

        //SetGameState
        panel.setGameState(0);
    }

    public void resetGame()
    {
        players = new ArrayList<Player>();
        tileDeck = new ArrayList<Tile>();
        starterTiles = new HashMap<Integer, ArrayList<Tile>>(); //5 starterTiles so we have an int to represent each tile. StarterTiles are an arraylist of 3 tiles
        tokenDeck = new ArrayList<Token>();
        availableTiles = new Tile[4];
        availableTokens = new Token[4];
        tileChose = null;
        tokenChose = null;
        alreadyTookTurn = false;
        turn = 0;
        try {
            createGame();
        } catch (IOException e) {
            System.out.println("Error in creating new game from main menu button resetting game");
            throw new RuntimeException(e);
        }
    }

    public void play()
    {
        //Shuffle tiles and tokens and remove extra tiles
        shuffleTiles();
        int totalTiles = players.size() * 20 + 3;
        for (int j = 0; j < tileDeck.size() - totalTiles; j++)
            tileDeck.remove(j);
        shuffleTokens();

        //Filling in the starting tiles and tokens
        updateTileAndTokens();



        //player turn loop
        while (!checkGameEnd())
        {
            for(int i = 0; i < 4; i++)
            {
//                currentPlayer = players.get(i);
                updateTileAndTokens();
                panel.setGameState(2);
                panel.repaint();
                playerTurn(currentPlayer);
                tileChose = null;
                tokenChose = null;
            }
            turn++;
        }
    }


    public void playerTurn(Player currentPlayer)
    {
        //overpopulation
        if (panel.getGameState() == 2)
        {

            if (checkOverpopulation(false) == 4)
                while (checkOverpopulation(false) == 4) {
                    checkOverpopulation(true);
                }

            else if (checkOverpopulation(false) == 3) {
                while (checkOverpopulation(false) == 3)
                {
                    alreadyTookTurn = true;
                    //has pinecones and can also clear tokens
                    if (currentPlayer.getPineCones() > 0)
                    {
                        //setPrompt - choose a tile or clear tokens or use pinecones
                        panel.repaint();
                        panel.waitForChooseTileOrClearTokensOrUsePinecones();

                        //clear tokens
                        if (panel.getGameState() == 8) {
                            //setPrompt - choose which tokens to clear
                            //panel.repaint();
                            panel.waitForOkClicked();
                            updateTileAndTokens();
                        }

                        //use pinecones
                        else if (panel.getGameState() == 7)
                            pinecone2OptionsTurn(currentPlayer);

                            //chose to click on the tile
                        else
                            regularPlayerTurn(currentPlayer);
                    }
                    //does not have pinecones but can still clear tokens
                    else
                    {
                        //setPrompt - choose a tile or clear tokens
                        panel.repaint();
                        panel.waitForChooseTileOrClearTokens();

                        //clear tokens
                        if (panel.getGameState() == 8) {
                            //setPrompt - choose which tokens to clear
                            //panel.repaint();
                            panel.waitForOkClicked();
                            updateTileAndTokens();
                        }

                        //chose to click on the tile
                        else
                            regularPlayerTurn(currentPlayer);
                    }
                }
            }
            panel.repaint();

            if (!alreadyTookTurn) {
                //use pinecones path
                if (currentPlayer.getPineCones() > 0) {
                    panel.waitForChooseTileOrPineconeClicked(); //after this line, player will have either clicked on a tile or chosen to use a pinecone

                    //Chose to use pinecone
                    if (panel.getGameState() == 7)
                        pinecone2OptionsTurn(currentPlayer);

                        //has pinecones but chose regular game turn
                    else {
                        //already chose a tile
                        tileChose = availableTiles[panel.getTileChosenNum()];
                        tokenChose = availableTokens[panel.getTileChosenNum()];
                        regularPlayerTurn(currentPlayer);
                    }

                } else {
                    //Does not have pinecones and does regular game turn
                    //setPrompt - choose where to place the tile
                    panel.repaint();
                    panel.waitForTileClicked();
                    tileChose = availableTiles[panel.getTileChosenNum()];
                    tokenChose = availableTokens[panel.getTileChosenNum()];
                    regularPlayerTurn(currentPlayer);
                }
                panel.repaint();
            }
        }
        panel.repaint();
    }


    public void pinecone2OptionsTurn(Player currentPlayer)
    {
        //panel.setPrompt() - choose one of the options below
        panel.repaint();
        panel.waitForPinecone2options();

        //clear tokens
        if (panel.getGameState() == 8) {
            //setPrompt - choose which tokens to clear
            //panel.repaint();
            panel.waitForOkClicked();
            updateTileAndTokens();
            regularPlayerTurn(currentPlayer); // will write later
        }

        //choose a specific tile and token
        else if (panel.getGameState() == 9) {
            //setPrompt - choose a tile
            //panel.repaint();
            panel.waitForTileClicked();
            tileChose = availableTiles[panel.getTileChosenNum()];

            //setPrompt - choose a token
            //panel.repaint();
            panel.waitForTokenClicked();
            tokenChose = availableTokens[panel.getTokenChosenNum()];

            //setPrompt - click ok to confirm
            //panel.repaint();
            panel.waitForOkClicked();

            //tile placement
            if (panel.getGameState() == 3) {
                //placement
                //setPrompt - choose where to place the tile
                //panel.repaint();
                panel.waitForTilePlaced();//in mouselistener, once player has clicked where to place it, will have to set tilePlaced to true and gamestate to 4
//                availableTiles[panel.getTileChosenNum()] = null;
                panel.repaint();


                //rotation/orientation
                //setPrompt - Rotate tile! press ok when done
                //panel.repaint();
                if (panel.getGameState() == 4) {
                    panel.waitForOkClicked();
                    panel.repaint();


                }
            }

            //place token
            //setPrompt - choose is where to place the token or throw away
            //panel.repaint(); - will highlight available tokens
            panel.waitForTokenPlacedOrThrowAway();
            //chose to keep and place or throw away
            if (panel.getGameState() == 5)
            {
                if(panel.getTileForTokenPlacementChosen() !=null) {
                    panel.getTileForTokenPlacementChosen().setAnimal(tokenChose);
                    //award pinecones
                    if (panel.getTileForTokenPlacementChosen().isKeyStone())
                        currentPlayer.addPineCone();
                }
                else {
                    tokenDeck.add(tokenChose); //adds the chosen token back to token deck
                    availableTokens[panel.getTokenChosenNum()] = null; //sets that same spot in available tokens to empty
                }
                panel.repaint();
            }

        }

        //panel.setPrompt() - Confirm turn end and click next player to go the next turn
//            panel.repaint();
        panel.waitForNextPlayer();
        panel.resetGameFlags();


    }


    public void regularPlayerTurn(Player currentPlayer)
    {
        //has already chosen a tile

        if (panel.getGameState() == 3) {
            //setPrompt - choose where to place the tile
            panel.repaint();

            panel.waitForTilePlaced(); //in mouselistener, once player has clicked where to place it, will have to set tilePlaced to true and gamestate to 4
//            availableTiles[panel.getTileChosenNum()] = null;
            panel.repaint();

            //rotation/orientation
            //setPrompt - Rotate tile! press ok when done
            //panel.repaint();
            if (panel.getGameState() == 4) {
                panel.waitForOkClicked();
                panel.repaint();

            }

            //Token placing or throwing away
            //setPrompt - choose is where to place the token or throw away
            //panel.repaint();
            panel.waitForTokenPlacedOrThrowAway();
            //chose to keep and place or throw away
            if (panel.getGameState() == 5)
            {


                //award pinecones
                if (panel.getTileForTokenPlacementChosen().isKeyStone())
                    currentPlayer.addPineCone();

                else {
                    tokenDeck.add(tokenChose); //adds the chosen token back to token deck
                    //sets that same spot in available tokens to empty
                }
                panel.repaint();
            }

            //panel.setPrompt() - Confirm turn end and click next player to go the next turn
//                panel.repaint();
            panel.waitForNextPlayer();
            panel.resetGameFlags();

        }
    }



    public void createGame() throws IOException
    {
        //tiles
        Scanner s = new Scanner(new File("Cascadia.txt"));
        BufferedImage img;
        while(s.hasNext())
        {
            String t = s.nextLine().trim();
//            System.out.println(t);
            String[] a = t.split(" ");
//            System.out.println(Arrays.toString(a));

            try
            {
                if(Integer.parseInt(a[5]) < 21)
                {
                    img = ImageIO.read(Game.class.getResource("/KeyStonetiles/" + a[5] + ".png"));
                    tileDeck.add(new Tile(Integer.parseInt(a[0]), Integer.parseInt(a[1]), Integer.parseInt(a[2]), Integer.parseInt(a[3]), Integer.parseInt(a[4]), -999, -999, true, img));
                }
                else
                {
                    img = ImageIO.read(Game.class.getResource("/RegularTiles/" + a[5] + ".png"));
                    tileDeck.add(new Tile(Integer.parseInt(a[0]), Integer.parseInt(a[1]), Integer.parseInt(a[2]), Integer.parseInt(a[3]), Integer.parseInt(a[4]), -999, -999, false, img));
                }
            }
            catch(Exception E)
            {
                System.out.print("Error in tile image number");
                System.out.println("Error in tile image number "+ a[5]);
            }
            getLeaderBoard();
        }

        //starterTiles
        //int hab1, int hab2, int animal1, int animal2, int animal3, int x, int y, boolean , image
        try
        {
            starterTiles.put(1, new ArrayList<Tile>());
            img = ImageIO.read(Game.class.getResource("/StarterTiles/" + "1a.png"));
            starterTiles.get(1).add(new Tile(2, 2, 1, 0, 0, -999, -999, true, img));
            img = ImageIO.read(Game.class.getResource("/StarterTiles/" + "1b.png"));
            starterTiles.get(1).add(new Tile(1, 5, 1, 3, 5, -999, -999, false, img));
            img = ImageIO.read(Game.class.getResource("/StarterTiles/" + "1c.png"));
            starterTiles.get(1).add(new Tile(3, 4, 2, 4, 0, -999, -999, false, img));
            starterTiles.get(1).get(1).rotateRight();
            starterTiles.get(1).get(2).rotateLeft();

            starterTiles.put(2, new ArrayList<Tile>());
            img = ImageIO.read(Game.class.getResource("/StarterTiles/" + "2a.png"));
            starterTiles.get(2).add(new Tile(3, 3, 4, 0, 0, -999, -999, true, img));
            img = ImageIO.read(Game.class.getResource("/StarterTiles/" + "2b.png"));
            starterTiles.get(2).add(new Tile(4, 5, 2, 3, 4, -999, -999, false, img));
            img = ImageIO.read(Game.class.getResource("/StarterTiles/" + "2c.png"));
            starterTiles.get(2).add(new Tile(1, 2, 1, 5, 0, -999, -999, false, img));
            starterTiles.get(2).get(1).rotateRight();
            starterTiles.get(2).get(2).rotateLeft();

            starterTiles.put(3, new ArrayList<Tile>());
            img = ImageIO.read(Game.class.getResource("/StarterTiles/" + "3a.png"));
            starterTiles.get(3).add(new Tile(4, 4, 3, 0, 0, -999, -999, true, img));
            img = ImageIO.read(Game.class.getResource("/StarterTiles/" + "3b.png"));
            starterTiles.get(3).add(new Tile(5, 2, 2, 3, 1, -999, -999, false, img));
            img = ImageIO.read(Game.class.getResource("/StarterTiles/" + "3c.png"));
            starterTiles.get(3).add(new Tile(1, 3, 5, 4, 0, -999, -999, false, img));
            starterTiles.get(3).get(1).rotateRight();
            starterTiles.get(3).get(2).rotateLeft();

            starterTiles.put(4, new ArrayList<Tile>());
            img = ImageIO.read(Game.class.getResource("/StarterTiles/" + "4a.png"));
            starterTiles.get(4).add(new Tile(1, 1, 5, 0, 0, -999, -999, true, img));
            img = ImageIO.read(Game.class.getResource("/StarterTiles/" + "4b.png"));
            starterTiles.get(4).add(new Tile(2, 4, 3, 1, 4, -999, -999, false, img));
            img = ImageIO.read(Game.class.getResource("/StarterTiles/" + "4c.png"));
            starterTiles.get(4).add(new Tile(2, 5, 2, 5, 0, -999, -999, false, img));
            starterTiles.get(4).get(1).rotateRight();
            starterTiles.get(4).get(2).rotateLeft();

            starterTiles.put(5, new ArrayList<Tile>());
            img = ImageIO.read(Game.class.getResource("/StarterTiles/" + "5a.png"));
            starterTiles.get(5).add(new Tile(5, 5, 2, 0, 0, -999, -999, true, img));
            img = ImageIO.read(Game.class.getResource("/StarterTiles/" + "5b.png"));
            starterTiles.get(5).add(new Tile(3, 3, 2, 1, 5, -999, -999, false, img));
            img = ImageIO.read(Game.class.getResource("/StarterTiles/" + "5c.png"));
            starterTiles.get(5).add(new Tile(4, 1, 3, 4, 0, -999, -999, false, img));
            starterTiles.get(5).get(1).rotateRight();
            starterTiles.get(5).get(2).rotateLeft();
        }
        catch(Exception E)
        {
            System.out.println("error with starter tiles");
        }

        //tokens
        for(int i = 1; i <= 5; i++)
        {
            for(int j = 0; j < 25; j++)
            {
                tokenDeck.add(new Token(i));
            }
        }
//sfiwegfeugfiewhfoihewoi[fhweq[ho[fgewhf[wehf
//        for(int j = 0; j < 25; j++)
//        {
//            tokenDeck.add(new Token(1));
//        }
    }

//    public int getGameState()
//    {
//        return gameState;
//    }
//    public void setGameState(int gs)
//    {
//        gameState = gs;
//    }

    public Player getCurrentPlayer()
    {
        return currentPlayer;
    }

    public void setCurrentPlayer(int p)
    {
        currentPlayer = players.get(p);
    }

    public ArrayList<Tile> getTileDeck()
    {
        return tileDeck;
    }

    public ArrayList<Token> getTokenDeck()
    {
        return tokenDeck;
    }

    public boolean getIfTookTurn()
    {
        return alreadyTookTurn;
    }

    public void setIfTookTurn(boolean b)
    {
        alreadyTookTurn = b;
    }

    public Tile getTileChose()
    {
        return tileChose;
    }

    public void setTileChose(Tile t)
    {
        tileChose = t;
    }

    public Token getTokenChose()
    {
        return tokenChose;
    }

    public void setTokenChose(Token t)
    {
        tokenChose = t;
    }



    public int getPlayerNum() {
        for(int i = 0; i<players.size(); i++) {
            Player temp = players.get(i);
            if(temp.equals(currentPlayer)) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<Player> getPlayers() {return players;}

    public void setNumOfPlayers(int x)
    {
        //Create Number of players
        for (int i = 0; i < x; i++)
        {
//            try {             //HARDCODED PLAYER AND TILE
//                players.add(new Player(new Board(new Tile(2, 2, 5, 5, 0, 10, 10, false, ImageIO.read(Game.class.getResource("/StarterTiles/1a.png"))))));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
            ArrayList<Tile> st = new ArrayList<>();
            int ind = (int)(Math.random() * 5 + 1);
            boolean added = false;

            while (!added)
            {
                if (starterTiles.containsKey(ind)) {
                    st = starterTiles.remove(ind);
                    added = true;
                }
                else {
                    ind = (int) (Math.random() * 5 + 1);
                }
            }

            players.add(new Player(new Board(st.get(0), st.get(1), st.get(2), panel.getWidth(), panel.getHeight())));
//            players.get(i).getBoard().addTile(st.get(1), st.get(0), 4);
//            players.get(i).getBoard().addTile(st.get(2), st.get(0), 3);
        }
//        System.out.println("reaches play");
        currentPlayer = players.get(0);

    }



    public void getLeaderBoard()
    {

    }

    public boolean checkGameEnd()
    {
        return turn < 20;
    }

    public void shuffleTiles()
    {
        for(int i = 0; i < tileDeck.size(); i++)
        {
            int k = (int)(Math.random() * tileDeck.size());
            Tile temp = tileDeck.get(k);
            tileDeck.set(k, tileDeck.get(i));
            tileDeck.set(i, temp);
        }
    }

    public void shuffleTokens()
    {
        for(int i = 0; i < tokenDeck.size(); i++)
        {
            int k = (int)(Math.random() * tokenDeck.size());
            Token temp = tokenDeck.get(k);
            tokenDeck.set(k, tokenDeck.get(i));
            tokenDeck.set(i, temp);
        }
    }

    public Token getToken(int i)
    {
        return tokenDeck.get(i);
    }

    public Tile getTile(int i)
    {
        return tileDeck.get(i);
    }

    public void updateTileAndTokens()
    {
        for(int i = 0; i < availableTokens.length; i++)
            if(availableTokens[i] == null)
                availableTokens[i] = tokenDeck.remove(0);

        for(int i = 0; i < availableTiles.length; i++)
            if(availableTiles[i] == null)
                availableTiles[i] = tileDeck.remove(0);
    }

    public int checkOverpopulation(boolean b)
    {
        TreeMap<Integer, Integer> animals = new TreeMap<>();
        for(int i = 1; i < 6; i++)
            animals.put(i, 0);
        for(int i = 0; i < availableTokens.length; i++)
        {
            int key = availableTokens[i].getAnimal();
            animals.put(key, animals.get(key) + 1);
        }

        int num = 0;
        for(int i = 1; i < 6; i++)
        {
            if (animals.get(i) > 2) {
                num = animals.get(i);

                if(b)
                {
                    for(int j = 0; j < availableTokens.length; j++)
                    {
                        if(availableTokens[j].getAnimal() == i)
                            availableTokens[j] = null;
                    }
                    updateTileAndTokens();
                }
            }
        }

        return num;
    }

    public void waitForSeconds(double seconds)
    {
        try {
            Thread.sleep((int) (seconds * 1000));
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public CascadiaPanel getPanel()
    {
        return panel;
    }

    public Tile[] getAvailableTiles() {
        return availableTiles;
    }
    public Token[] getAvailableTokens(){
        return availableTokens;
    }

    public void bonusesAndScores() {
        HashMap<Integer, ArrayList<Integer>> bonus = new HashMap<>(); //bonuses for habitats
        for (int i = 0; i < players.size(); i++) {
            bonus.put(i, new ArrayList<>());
        }
        for (int habitat = 1; habitat <= 5; habitat++) {
            ArrayList<Integer> playerScores = new ArrayList<>();
            for (int i = 0; i < players.size(); i++) {
                playerScores.add(players.get(i).getBoard().getHabitatScore(habitat));
            }
            int maxScore = 0;
            for (int x : playerScores) {
                maxScore = Math.max(x, maxScore);
            }
            ArrayList<Integer> temp = new ArrayList<>();
            for (int i = 0; i < playerScores.size(); i++) {
                if (playerScores.get(i) == maxScore) {
                    temp.add(i);
                }
            }
            //temp is the list of players with the highest score
            if (players.size() == 2) {
                if (temp.size() == 2) {
                    bonus.get(0).add(1);
                    bonus.get(1).add(1);
                } else {
                    for (int i = 0; i < players.size(); i++) {
                        if (temp.contains(i)) {
                            bonus.get(i).add(2);
                        } else {
                            bonus.get(i).add(0);
                        }
                    }
                }
            } else {
                if (temp.size() == 1) {
                    for (int i = 0; i < players.size(); i++) {
                        if (temp.contains(i)) {
                            bonus.get(i).add(3);
                        } else {
                            bonus.get(i).add(0);
                        }
                    }
                    playerScores.set(temp.getFirst(), -1);
                    maxScore = 0; //start to calculate second largest
                    for (int i = 0; i < playerScores.size(); i++) {
                        maxScore = Math.max(maxScore, playerScores.get(i));
                    }
                    temp.clear();
                    for (int i = 0; i < playerScores.size(); i++) {
                        if (playerScores.get(i) == maxScore) {
                            temp.add(i);
                        }
                    }
                    if (temp.size() > 1) {

                    } else {
                        bonus.get(temp.get(0)).set(bonus.get(temp.get(0)).size() - 1, 1);
                    }

                } else if (temp.size() == 2) {
                    for (int i = 0; i < players.size(); i++) {
                        if (temp.contains(i)) {
                            bonus.get(i).add(2);
                        } else {
                            bonus.get(i).add(0);
                        }
                    }
                } else {
                    for (int i = 0; i < players.size(); i++) {
                        if (temp.contains(i)) {
                            bonus.get(i).add(1);
                        } else {
                            bonus.get(i).add(0);
                        }
                    }
                }

            }

        }
        Iterator<Integer> it = bonus.keySet().iterator();
        while (it.hasNext()){
            int x = it.next();
            players.get(x).setScore(bonus.get(x));
        }
    }

    public TreeMap<Integer, ArrayList<Integer>> getRanking()
    {

        TreeMap<Integer, ArrayList<Integer>> ranks = new TreeMap<>();
        for(int i = 0; i < players.size(); i++)
        {
            ranks.put(i, new ArrayList<Integer>());
        }
        ArrayList<Integer> playerScores = new ArrayList<>();
        int maxScore = 0;
        for (int i = 0; i < players.size(); i++){
            int score = players.get(i).getScore()[18];
            playerScores.add(score);
            maxScore = Math.max(score, maxScore);
        }
        ArrayList<Integer> firstPlace = ranks.get(1);
        for(int i = 0; i < playerScores.size(); i++)
        {
            if (playerScores.get(i) == maxScore){
                firstPlace.add(i);
            }
        }

        if (firstPlace.size() > 1){
            //checks for max PineCone size
            int maxPineCone = 0;
            for (int i = 0; i < firstPlace.size(); i++){
                maxPineCone = Math.max(players.get(firstPlace.get(i)).getScore()[17], maxScore);
            }
            for (int i = 0; i < firstPlace.size(); i++) {
                if (players.get(firstPlace.get(i)).getScore()[17] != maxPineCone) {
                    firstPlace.remove(i);
                    i--;
                }
            }
        }
        for (int i = 0; i < playerScores.size(); i++){
            playerScores.set(firstPlace.get(i), -1);
        }
        maxScore = 0;

        for (int i = 0; i < players.size(); i++){
            int score = players.get(i).getScore()[18];
            maxScore = Math.max(score, maxScore);
        }
        ArrayList<Integer> secondPlace = ranks.get(2);
        for(int i = 0; i < playerScores.size(); i++)
        {
            if (playerScores.get(i) == maxScore){
                secondPlace.add(i);
            }
        }

        if (secondPlace.size() > 1){
            //checks for max PineCone size
            int maxPineCone = 0;
            for (int i = 0; i < secondPlace.size(); i++){
                maxPineCone = Math.max(players.get(secondPlace.get(i)).getScore()[17], maxScore);
            }
            for (int i = 0; i < secondPlace.size(); i++) {
                if (players.get(secondPlace.get(i)).getScore()[17] != maxPineCone) {
                    secondPlace.remove(i);
                    i--;
                }
            }
        }
        for (int i = 0; i < playerScores.size(); i++){
            playerScores.set(secondPlace.get(i), -1);
        }

        for (int i = 0; i < players.size(); i++){
            int score = players.get(i).getScore()[18];
            maxScore = Math.max(score, maxScore);
        }
        ArrayList<Integer> thirdPlace = ranks.get(3);
        for(int i = 0; i < playerScores.size(); i++)
        {
            if (playerScores.get(i) == maxScore){
                thirdPlace.add(i);
            }
        }

        if (thirdPlace.size() > 1) {
            //checks for max PineCone size
            int maxPineCone = 0;
            for (int i = 0; i < thirdPlace.size(); i++) {
                maxPineCone = Math.max(players.get(thirdPlace.get(i)).getScore()[17], maxScore);
            }
            for (int i = 0; i < thirdPlace.size(); i++) {
                if (players.get(thirdPlace.get(i)).getScore()[17] != maxPineCone) {
                    thirdPlace.remove(i);
                    i--;
                }
            }
        }
        return ranks;
    }

}