import java.io.File;
import java.util.*;
import java.io.*;

public class Game
{
    private int gameState;
    private ArrayList<Player> players;
    private int turn;
    private ArrayList<Tile> tileDeck;
    private ArrayList<Token> tokenDeck;
    private Tile[] availableTiles;
    private Token[] availableTokens;

    public Game()
    {
        int totalTiles = players.size() * 20 + 3;
        for (int i = 0; i < tileDeck.size() - totalTiles; i++)
        {
            tileDeck.remove(i);
        }

    }

    public void createGame() throws IOException
    {

        Scanner s = new Scanner(new File("CascadiaText.txt"));

        while(s.hasNext())
        {
            int t = s.nextInt();
        }
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