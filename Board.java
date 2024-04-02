import java.util.*;

public class Board
{
    private Tile startTile;
    private int numTiles;

    public Board(Tile t)
    {
        startTile = t;
    }

    public void addTile(Tile newTile, Tile adjTile, int orientation)
    {

    }

    public void placeTile(Tile t, Token a)
    {
        t.setAnimal(a);
    }

    public String getTilePosition(Tile t)
    {
        return t.getXCoord() + " " + t.getYCoord();
    }

    public ArrayList<Tile> traverse()
    {

    }

    public int elkScore() {}
    public int salmonScore() {}
    public int hawkScore() {}
    public int bearScore() {}
    public int foxScore() {}
}