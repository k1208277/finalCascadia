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
        for (int i = 0; i <= 5; i++){
            adjacentTiles.add(null);
        }
    }

    public ArrayList<Tile> getAdjacentTiles(){
        return adjacentTiles;
    }

    public Tile getAdjacent(int i){
        return adjacentTiles.get(i);
    }
    public void rotateLeft()
    {
        habitats.add(habitats.remove(5));
    }
    public void rotateRight()
    {
        habitats.add(habitats.remove(0));
    }
    public int getOrientation()
    {
        return orientation;
    }
    public void setAnimal(Token t)
    {
        animal = t;
    }

    public int getAnimal(){ return animal.getAnimal();}
    public int getHabitat(int habitat)
    {
        return habitats.get(habitat);
    }
    public BufferedImage getImage()
    {
        return image;
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