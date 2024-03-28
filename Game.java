import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Game
{
    private int gameState;
    private ArrayList<Player> players;
    private int turn;
    private ArrayList<Tile> tileDeck;
    private HashMap<Integer, ArrayList<Tile>> starterTiles;
    private ArrayList<Token> tokenDeck;
    private Tile[] availableTiles;
    private Token[] availableTokens;
    //GameStates
    // 1 = Start Game (Player clicks start game)
    // 2 = Help Panel
    // 3 = clicking on player panel to enlarge
    // 4 = clicking on help panel
    // 5 = start of player turn ??
    // 6 = player uses pinecone ??
    // 7 = GameEnd (start scoring and display leaderboard)
    // 8 = x buttons to exit player and help panel
    // 9 = arrows for board adjustment or help panel


    public Game()
    {
        //Intatization of attributes
        tileDeck = new ArrayList<Tile>();
        starterTiles = new HashMap<Integer, ArrayList<Tile>>(); //5 starterTiles so we have an int to represent each tile. StarterTiles are an arraylist of 3 tiles
        tokenDeck = new ArrayList<Token>();

        //Create all tiles and tokens
        try {
            createGame();
        } catch (IOException e) {
            System.out.println("Error with exception of creating game");
        }

        //Shuffle tiles and tokens and remove extra tiles
        shuffleTiles();
        int totalTiles = players.size() * 20 + 3;
        for (int i = 0; i < tileDeck.size() - totalTiles; i++)
            tileDeck.remove(i);
        shuffleTokens();



    }

    public void createGame() throws IOException
    {
        //tiles
        Scanner s = new Scanner(new File("Cascadia.txt"));
        BufferedImage img;
        while(s.hasNext())
        {
            String t = s.nextLine().trim();
            String[] a = t.split(".");
            try
            {
                if(Integer.parseInt(a[5]) < 21)
                {
                    img = ImageIO.read(Game.class.getResource("/KeyStonetiles/" + a[5] + ".png"));
                    tileDeck.add(new Tile(Integer.parseInt(a[0]), Integer.parseInt(a[1]), Integer.parseInt(a[2]), Integer.parseInt(a[3]), Integer.parseInt(a[4]), -999, -999, true, img));
                }
                else
                {
                    img = ImageIO.read(Game.class.getResource("/KeyStonetiles/" + a[5] + ".png"));
                    tileDeck.add(new Tile(Integer.parseInt(a[0]), Integer.parseInt(a[1]), Integer.parseInt(a[2]), Integer.parseInt(a[3]), Integer.parseInt(a[4]), -999, -999, false, img));
                }
            }
            catch(Exception E)
            {
                System.out.println("Error in tile image number " + a[5]);
            }
        }

        //starterTiles


        //tokens
        for(int i = 1; i <= 5; i++)
        {
            for(int j = 0; i < 25; i++)
            {
                tokenDeck.add(new Token(i));
            }
        }

        //starterTiles

        //tokens
    }

    public void play()
    {


    }
    public int getGameState()
    {
        return gameState;
    }
    public void setGameState(int gs)
    {
        gameState = gs;
    }

    public void getLeaderBoard()
    {


    }

    public boolean checkGameEnd()
    {
        return turn > 20;
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
        {
            if(availableTokens[i] == null)
                availableTokens[i] = getToken(0);
        }


        for(int i = 0; i < availableTiles.length; i++)
        {
            if(availableTiles[i] == null)
                availableTiles[i] = getTile(0);
        }
    }

    public boolean checkOverpopulation()
    {
        for(int i = 0; i < availableTokens.length; i++)
        {

        }

        return false;
    }

}