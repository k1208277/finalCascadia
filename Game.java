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
    //0: main menu
    //1: choose player
    //2: main layout/choose options
    //3: choose tile placement
    //4: choose tile orientation
    //5: choose tile token is placed/throwaway
    //6: confirm and go to next player
    //7: choose pinecone options
    //8: choose tiles to clear
    //9: choose specific tile and token
    //10: helpPanel
    //11: playerPanel
    //12: end game


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

    public Game(int numOfPlayers)
    {
        //Intatization of attributes
        players = new ArrayList<Player>();
        tileDeck = new ArrayList<Tile>();
        starterTiles = new HashMap<Integer, ArrayList<Tile>>(); //5 starterTiles so we have an int to represent each tile. StarterTiles are an arraylist of 3 tiles
        tokenDeck = new ArrayList<Token>();

        //Create all tiles and tokens
        try {
            createGame();
        } catch (IOException e) {
            System.out.println("Error with exception of creating game");
        }
        
        //Create Number of players
        for (int i = 0; i < numOfPlayers; i++) {
            try {
                players.add(new Player(new Board(new Tile(2, 2, 5, 5, 0, 10, 10, false, ImageIO.read(Game.class.getResource("/StarterTiles/1a.png"))))));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        //SetGameState
        setGameState(0);

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
                    System.out.println(t);
                    img = ImageIO.read(Game.class.getResource("/KeyStonetiles/" + a[5] + ".png"));
                    tileDeck.add(new Tile(Integer.parseInt(a[0]), Integer.parseInt(a[1]), Integer.parseInt(a[2]), Integer.parseInt(a[3]), Integer.parseInt(a[4]), -999, -999, true, img));
                }
                else
                {
                    System.out.println(t +" empty");
                    img = ImageIO.read(Game.class.getResource("/KeyStonetiles/" + a[5] + ".png"));
                    tileDeck.add(new Tile(Integer.parseInt(a[0]), Integer.parseInt(a[1]), Integer.parseInt(a[2]), Integer.parseInt(a[3]), Integer.parseInt(a[4]), -999, -999, false, img));
                }
            }
            catch(Exception E)
            {
                System.out.print("Error in tile image number ");
                for (int i = 0; i < a.length; i++)
                    System.out.print(a[i] + " ");
                System.out.println(" "+ a.length);
//                System.out.println("Error in tile image number "+ );
            }
        }

        //starterTiles


        //tokens
        for(int i = 1; i <= 5; i++)
        {
            for(int j = 0; j < 25; j++)
            {
                tokenDeck.add(new Token(i));
            }
        }
    }

    public void play()
    {
        //GAME LOOP

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

}