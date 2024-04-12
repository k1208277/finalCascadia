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
    private CascadiaPanel panel;
    //GameStates
    //0: main menu
    //1: choose player
    //2: main layout/choose tile options
    //3: choose tile placement
    //4: choose tile orientation
    //5: choose tile token is placed/throwaway
    //6: confirm and go to next player
    //7: choose pinecone options
    //8: choose tokens to clear
    //9: choose specific tile and token
    //10: helpPanel
    //11: playerPanel
    //12: end game
    //13: overpopulation choice to remove



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
                currentPlayer = players.get(i);
                panel.repaint();
                playerTurn(currentPlayer);
            }
            turn++;
        }
        getLeaderBoard();
    }


    public void playerTurn(Player currentPlayer)
    {
        //overpopulation
        if (checkOverpopulation(false) == 4)
        {
               checkOverpopulation(true);
               panel.setGameState(2);
        }
        else if (checkOverpopulation(false) == 3) {
            if (panel.getGameState() == 13)
            {
                checkOverpopulation(true);
                panel.setGameState(2);
            }
        }
        panel.repaint();

        //pinecones
        if (currentPlayer.getPineCones() > 0)
        {
            if (panel.getGameState() == 7)
            {
                //prompt
                panel.repaint();
                panel.waitForChooseTileOrPineconeClicked();

                //Player clicked use pinecone
                if (panel.getGameState() == 7)
                {
                    //clear tokens
                    //setPrompt to choose one of the options
                    //panel.repaint();
                    panel.waitForPinecone2options();
                    if (panel.getGameState()  == 8)
                    {
                        //setPrompt - choose which tokens to clear
                        //panel.repaint();
                        panel.waitForOkClicked();
                        updateTileAndTokens();
                        regularPlayerTurn(currentPlayer); // will write later

                    }
                    //choose a specific tile and token
                    else if (panel.getGameState() == 9)
                    {
                        //setPrompt - choose a tile
                        //panel.repaint();
                        panel.waitForTileClicked(); //not sure about this cuz now we have two separate wait methods with tileClicked in it, but this is only for tile clicked, other is checks for tile clicked or pinecone use clicked
                        //setPrompt - choose where to place the tile
                        //getAvailableTileSpots()
                        //panel.repaint(); - highlight available tiles
                        //if (panel.getGameState() == 3)
                        //waitForTilePlacement method will write later
                        //find spot where player chose
                        //setPrompt - RotateTile, press ok when done
                        //panel.repaint();
                        //waitForTileRotation? will write later
                        //if (panel.getGameState() == 4)
                        //left and right button action idk loop????
                        panel.waitForOkClicked();
                        //currentPlayer.getBoard().addTile(panel.getTileChosen()); i don't know how to put in the adjacent tiles yet in the parameter

                        //setPrompt - choose a token
                        //panel.repaint();
                        panel.waitForTokenClicked();
                        //setPrompt - choose where to place the token
                        //getAvailableTokensSpots()
                        //panel.repaint(); - highlight available tiles
                        //find spot where player chose
                        //setPrompt - choose a tile
                        //if (panel.getGameState() == 4)
                        //panel.repaint();
                        //waitForTileRotation? will write later
                        //currentPlayer.getBoard().addTile(panel.getTileChosen()); i don't know how to put in the adjacent tiles yet in the parameter

                        updateTileAndTokens();
                    }

                }



                if (panel.getGameState() == 3)
                {
                    //setPrompt
                    //panel.repaint();

                }
            }
        }
        panel.repaint();
    }

    public void regularPlayerTurn(Player currentPlayer)
    {

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
            starterTiles.get(1).add(new Tile(4, 3, 2, 4, 0, -999, -999, false, img));
            starterTiles.get(1).get(1).rotateRight();
            starterTiles.get(1).get(2).rotateLeft();

            starterTiles.put(2, new ArrayList<Tile>());
            img = ImageIO.read(Game.class.getResource("/StarterTiles/" + "2a.png"));
            starterTiles.get(2).add(new Tile(3, 3, 4, 0, 0, -999, -999, true, img));
            img = ImageIO.read(Game.class.getResource("/StarterTiles/" + "2b.png"));
            starterTiles.get(2).add(new Tile(4, 5, 2, 3, 4, -999, -999, false, img));
            img = ImageIO.read(Game.class.getResource("/StarterTiles/" + "2c.png"));
            starterTiles.get(2).add(new Tile(2, 1, 1, 5, 0, -999, -999, false, img));
            starterTiles.get(2).get(1).rotateRight();
            starterTiles.get(2).get(2).rotateLeft();

            starterTiles.put(3, new ArrayList<Tile>());
            img = ImageIO.read(Game.class.getResource("/StarterTiles/" + "3a.png"));
            starterTiles.get(3).add(new Tile(4, 4, 3, 0, 0, -999, -999, true, img));
            img = ImageIO.read(Game.class.getResource("/StarterTiles/" + "3b.png"));
            starterTiles.get(3).add(new Tile(5, 2, 2, 3, 1, -999, -999, false, img));
            img = ImageIO.read(Game.class.getResource("/StarterTiles/" + "3c.png"));
            starterTiles.get(3).add(new Tile(3, 1, 5, 4, 0, -999, -999, false, img));
            starterTiles.get(3).get(1).rotateRight();
            starterTiles.get(3).get(2).rotateLeft();

            starterTiles.put(4, new ArrayList<Tile>());
            img = ImageIO.read(Game.class.getResource("/StarterTiles/" + "4a.png"));
            starterTiles.get(4).add(new Tile(1, 1, 5, 0, 0, -999, -999, true, img));
            img = ImageIO.read(Game.class.getResource("/StarterTiles/" + "4b.png"));
            starterTiles.get(4).add(new Tile(2, 4, 3, 1, 4, -999, -999, false, img));
            img = ImageIO.read(Game.class.getResource("/StarterTiles/" + "4c.png"));
            starterTiles.get(4).add(new Tile(5, 3, 2, 5, 0, -999, -999, false, img));
            starterTiles.get(4).get(1).rotateRight();
            starterTiles.get(4).get(2).rotateLeft();

            starterTiles.put(5, new ArrayList<Tile>());
            img = ImageIO.read(Game.class.getResource("/StarterTiles/" + "5a.png"));
            starterTiles.get(5).add(new Tile(5, 5, 2, 0, 0, -999, -999, true, img));
            img = ImageIO.read(Game.class.getResource("/StarterTiles/" + "5b.png"));
            starterTiles.get(5).add(new Tile(3, 3, 2, 1, 5, -999, -999, false, img));
            img = ImageIO.read(Game.class.getResource("/StarterTiles/" + "5c.png"));
            starterTiles.get(5).add(new Tile(1, 4, 3, 4, 0, -999, -999, false, img));
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

    public int getPlayerNum() {
        for(int i = 0; i<players.size(); i++) {
            Player temp = players.get(i);
            if(temp.equals(currentPlayer)) {
                return i;
            }
        }
        return -1;
    }

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

            players.add(new Player(new Board(st.get(0))));
            players.get(i).getBoard().addTile(st.get(1), st.get(0), 4);
            players.get(i).getBoard().addTile(st.get(2), st.get(0), 3);
        }
        System.out.println("reaches play");

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
            if (animals.get(i) > 2)
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

}