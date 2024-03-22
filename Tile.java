
import java.awt.image.*;
import java.util.*;

public class Tile
{
    private ArrayList<Integer> habitats;
    private ArrayList<Integer> possibleAnimals;
    private ArrayList<Tile> adjacentTiles;
    private Token animal;
    private int orientation, xCoord, yCoord;
    private boolean isKeyStone, ifTraversed;
    private BufferedImage image;
    public Tile(int hab1, int hab2, int animal1, int animal2, int animal3, int x, int y, boolean isKey, BufferedImage b) //animal habitats
    {
        habitats = new ArrayList<Integer>();
        possibleAnimals = new ArrayList<Integer>();
        adjacentTiles = new ArrayList<Tile>();

        habitats.add(hab1); habitats.add(hab2); habitats.add(hab2); habitats.add(hab2); habitats.add(hab1); habitats.add(hab1);
        if (animal1 != 0)
            possibleAnimals.add(animal1);
        if (animal2 != 0)
            possibleAnimals.add(animal2);
        if (animal3 != 0)
            possibleAnimals.add(animal3);



        xCoord = x;
        yCoord = y;
        orientation = 0;
        ifTraversed = false;
        image = b;
    }
    public void rotateLeft()
    {
    }
    public void rotateRight()
    {
    }
    public int getOrientation()
    {
        return orientation;
    }
    public void setAnimal(Token t)
    {
        animal = t;
    }
    public int getHabitat(int habitat)
    {
    }
    public BufferedImage getImage()
    {
    }
    public int getXCoord()
    {
        return xCoord;
    }
    public int getYCoord()
    {
        return yCoord;
    }
    public void setXCoord(int x)
    {
        xCoord = x;
    }
    public void setYCoord(int y)
    {
        yCoord = y;
    }
    public boolean isChecked()
    {
        return ifTraversed;
    }
    public void setChecker(boolean b)
    {
        ifTraversed = b;
    }
}
