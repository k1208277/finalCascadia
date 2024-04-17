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

    private boolean emptyTile;

    public Tile(){
        adjacentTiles = new ArrayList<>();
        for (int i = 0; i <= 5; i++){
            adjacentTiles.add(null);
        }
        emptyTile = true;
    }

    public Tile(int hab1, int hab2, int animal1, int animal2, int animal3, int x, int y, boolean isKey, BufferedImage b) //animal habitats
    {
        habitats = new ArrayList<Integer>();
        possibleAnimals = new ArrayList<Integer>();
        adjacentTiles = new ArrayList<Tile>();

        isKeyStone = isKey;

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


        emptyTile = false;
    }

    public ArrayList<Tile> getAdjacentTiles(){
        return adjacentTiles;
    }

    public boolean isKeyStone()   {return isKeyStone;}
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


    //there is an issue with is clicked, smth with the math idk
    public boolean isClicked(int x, int y, int width, int height) {
        if(x>=xCoord && x<=xCoord+width && y>=yCoord && y<=yCoord+height) {
            if(y>=yCoord+(int)(height/4.074) && y<=yCoord+height-(int)(height/4.074)) {
                return true;
            }
            double totalArea = area(xCoord, yCoord+(int)(height/4.074), xCoord+width/2, yCoord, xCoord+width, yCoord+(int)(height/4.074));
            double a1 = area(xCoord, yCoord-(int)(height/4.074), xCoord+width/2, yCoord, x, y);
            double a2 = area(xCoord, yCoord-(int)(height/4.074), x, y, xCoord+width, yCoord-(int)(height/4.074));
            double a3 = area(x, y, xCoord+width/2, yCoord, xCoord+width, yCoord-(int)(height/4.074));
            if(a1+a2+a3==totalArea) {
                return true;
            }
            totalArea = area(xCoord, yCoord+(int)(height/1.325), xCoord+width/2, yCoord+height, xCoord+width, yCoord+(int)(1.325));
            a1 = area(xCoord, yCoord+(int)(height/1.325), xCoord+width/2, yCoord+height, x, y);
            a2 = area(xCoord, yCoord+(int)(height/1.325), x, y, xCoord+width, yCoord+(int)(1.325));
            a3 = area(x, y, xCoord+width/2, yCoord+height, xCoord+width, yCoord+(int)(1.325));
            if(a1+a2+a3==totalArea) {
                return true;
            }
            return false;
        }
        return false;
    }
    public double area(int x1, int y1, int x2, int y2, int x3, int y3)
    {
        return Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2.0);
    }
    public boolean isClicked(int adj, int x, int y, int width, int height)
    {
      switch(adj)
      {
          case 0:
          {
              if(x>=xCoord - width && x<=xCoord && y>=yCoord + height && y<=yCoord+height * 2) {
                  if(y>=yCoord+height+(int)(height/4.074) && y<=yCoord+2*height-(int)(height/4.074)) {
                      return true;
                  }
                  double totalArea = area(xCoord-width, yCoord+(int)(height/4.074)+height, xCoord+width/2 - width, yCoord+height, xCoord, yCoord+height+(int)(height/4.074));
                  double a1 = area(xCoord-width, yCoord-(int)(height/4.074)+height, xCoord+width/2-width, yCoord+height, x, y);
                  double a2 = area(xCoord-width, yCoord-(int)(height/4.074)+height, x, y, xCoord, yCoord-(int)(height/4.074)+height);
                  double a3 = area(x, y, xCoord+width/2-width, yCoord+height, xCoord, yCoord-(int)(height/4.074))+height;
                  if(a1+a2+a3==totalArea) {
                      return true;
                  }
                  totalArea = area(xCoord-width, yCoord+(int)(height/1.325)+height, xCoord+width/2-width, yCoord, xCoord, yCoord+(int)(1.325)+height);
                  a1 = area(xCoord-width, yCoord+(int)(height/1.325)+height, xCoord+width/2-width, yCoord+height*2, x, y);
                  a2 = area(xCoord-width, yCoord+(int)(height/1.325)+height, x, y, xCoord, yCoord+(int)(1.325)+height);
                  a3 = area(x, y, xCoord+width/2-width, yCoord+height*2, xCoord, yCoord+(int)(1.325)+height);
                  if(a1+a2+a3==totalArea) {
                      return true;
                  }
                  return false;
              }
              return false;
          }
          case 1:
          {
//              if(x>=xCoord && x<=xCoord+width && y>=yCoord && y<=yCoord+height) {
//                  if(y>=yCoord+(int)(height/4.074) && y<=yCoord+height-(int)(height/4.074)) {
//                      return true;
//                  }
//                  double totalArea = area(xCoord, yCoord+(int)(height/4.074), xCoord+width/2, yCoord, xCoord+width, yCoord+(int)(height/4.074));
//                  double a1 = area(xCoord, yCoord-(int)(height/4.074), xCoord+width/2, yCoord, x, y);
//                  double a2 = area(xCoord, yCoord-(int)(height/4.074), x, y, xCoord+width, yCoord-(int)(height/4.074));
//                  double a3 = area(x, y, xCoord+width/2, yCoord, xCoord+width, yCoord-(int)(height/4.074));
//                  if(a1+a2+a3==totalArea) {
//                      return true;
//                  }
//                  totalArea = area(xCoord, yCoord+(int)(height/1.325), xCoord+width/2, yCoord+height, xCoord+width, yCoord+(int)(1.325));
//                  a1 = area(xCoord, yCoord+(int)(height/1.325), xCoord+width/2, yCoord+height, x, y);
//                  a2 = area(xCoord, yCoord+(int)(height/1.325), x, y, xCoord+width, yCoord+(int)(1.325));
//                  a3 = area(x, y, xCoord+width/2, yCoord+height, xCoord+width, yCoord+(int)(1.325));
//                  if(a1+a2+a3==totalArea) {
//                      return true;
//                  }
//                  return false;
//              }
//              return false;
//              }
          }
          case 2:
          {
                if(x >= xCoord + width && x <= xCoord + width * 2 && y >= yCoord && y <= yCoord)
                {
                    if(y>=yCoord+(int)(height/4.074) && y<=yCoord+height-(int)(height/4.074))
                    {
                        return true;
                    }

                    double totalArea = area(xCoord + width, yCoord+(int)(height/4.074), xCoord+width/2 + width, yCoord, xCoord+width*2, yCoord+(int)(height/4.074));
                    double a1 = area(xCoord + width, yCoord-(int)(height/4.074), xCoord+width/2 + width, yCoord, x, y);
                    double a2 = area(xCoord +width, yCoord-(int)(height/4.074), x, y, xCoord+width*2, yCoord-(int)(height/4.074));
                    double a3 = area(x, y, xCoord+width/2 + width, yCoord, xCoord+width*2, yCoord-(int)(height/4.074));
                    if(a1+a2+a3==totalArea) {
                        return true;
                    }

                    totalArea = area(xCoord + width, yCoord+(int)(height/1.325), xCoord+width/2 + width, yCoord+height, xCoord+width*2, yCoord+(int)(1.325));
                    a1 = area(xCoord + width, yCoord+(int)(height/1.325), xCoord+width/2 + width, yCoord+height, x, y);
                    a2 = area(xCoord + width, yCoord+(int)(height/1.325), x, y, xCoord+width*2, yCoord+(int)(1.325));
                    a3 = area(x, y, xCoord+width/2 + width, yCoord+height, xCoord+width*2, yCoord+(int)(1.325));
                    if(a1+a2+a3==totalArea) {
                        return true;
                    }
                    return false;
                }
                return false;
          }
          case 3:
          {
//              if(x>=xCoord && x<=xCoord+width && y>=yCoord && y<=yCoord+height) {
//                  if(y>=yCoord+(int)(height/4.074) && y<=yCoord+height-(int)(height/4.074)) {
//                      return true;
//                  }
//                  double totalArea = area(xCoord, yCoord+(int)(height/4.074), xCoord+width/2, yCoord, xCoord+width, yCoord+(int)(height/4.074));
//                  double a1 = area(xCoord, yCoord-(int)(height/4.074), xCoord+width/2, yCoord, x, y);
//                  double a2 = area(xCoord, yCoord-(int)(height/4.074), x, y, xCoord+width, yCoord-(int)(height/4.074));
//                  double a3 = area(x, y, xCoord+width/2, yCoord, xCoord+width, yCoord-(int)(height/4.074));
//                  if(a1+a2+a3==totalArea) {
//                      return true;
//                  }
//                  totalArea = area(xCoord, yCoord+(int)(height/1.325), xCoord+width/2, yCoord+height, xCoord+width, yCoord+(int)(1.325));
//                  a1 = area(xCoord, yCoord+(int)(height/1.325), xCoord+width/2, yCoord+height, x, y);
//                  a2 = area(xCoord, yCoord+(int)(height/1.325), x, y, xCoord+width, yCoord+(int)(1.325));
//                  a3 = area(x, y, xCoord+width/2, yCoord+height, xCoord+width, yCoord+(int)(1.325));
//                  if(a1+a2+a3==totalArea) {
//                      return true;
//                  }
//                  return false;
//              }
//              return false;
//              }
          }
          case 4:
          {
//              if(x>=xCoord && x<=xCoord+width && y>=yCoord && y<=yCoord+height) {
//                  if(y>=yCoord+(int)(height/4.074) && y<=yCoord+height-(int)(height/4.074)) {
//                      return true;
//                  }
//                  double totalArea = area(xCoord, yCoord+(int)(height/4.074), xCoord+width/2, yCoord, xCoord+width, yCoord+(int)(height/4.074));
//                  double a1 = area(xCoord, yCoord-(int)(height/4.074), xCoord+width/2, yCoord, x, y);
//                  double a2 = area(xCoord, yCoord-(int)(height/4.074), x, y, xCoord+width, yCoord-(int)(height/4.074));
//                  double a3 = area(x, y, xCoord+width/2, yCoord, xCoord+width, yCoord-(int)(height/4.074));
//                  if(a1+a2+a3==totalArea) {
//                      return true;
//                  }
//                  totalArea = area(xCoord, yCoord+(int)(height/1.325), xCoord+width/2, yCoord+height, xCoord+width, yCoord+(int)(1.325));
//                  a1 = area(xCoord, yCoord+(int)(height/1.325), xCoord+width/2, yCoord+height, x, y);
//                  a2 = area(xCoord, yCoord+(int)(height/1.325), x, y, xCoord+width, yCoord+(int)(1.325));
//                  a3 = area(x, y, xCoord+width/2, yCoord+height, xCoord+width, yCoord+(int)(1.325));
//                  if(a1+a2+a3==totalArea) {
//                      return true;
//                  }
//                  return false;
//              }
//              return false;
//              }
          }
          case 5:
          {
              if(x>=xCoord - width && x<=xCoord && y>=yCoord && y<=yCoord+height) {
                  if(y>=yCoord+(int)(height/4.074) && y<=yCoord+height-(int)(height/4.074)) {
                      return true;
                  }
                  double totalArea = area(xCoord - width, yCoord+(int)(height/4.074), xCoord+width/2 - width, yCoord, xCoord, yCoord+(int)(height/4.074));
                  double a1 = area(xCoord - width, yCoord-(int)(height/4.074), xCoord+width/2 - width, yCoord, x, y);
                  double a2 = area(xCoord - width, yCoord-(int)(height/4.074), x, y, xCoord, yCoord-(int)(height/4.074));
                  double a3 = area(x, y, xCoord+width/2 - width, yCoord, xCoord, yCoord-(int)(height/4.074));
                  if(a1+a2+a3==totalArea) {
                      return true;
                  }
                  totalArea = area(xCoord - width, yCoord+(int)(height/1.325), xCoord+width/2 - width, yCoord+height, xCoord, yCoord+(int)(1.325));
                  a1 = area(xCoord - width, yCoord+(int)(height/1.325), xCoord+width/2 - width, yCoord+height, x, y);
                  a2 = area(xCoord - width, yCoord+(int)(height/1.325), x, y, xCoord, yCoord+(int)(1.325));
                  a3 = area(x, y, xCoord+width/2 - width, yCoord+height, xCoord, yCoord+(int)(1.325));
                  if(a1+a2+a3==totalArea) {
                      return true;
                  }
                  return false;
              }
              return false;
          }
      }

      return false;
    }


    public boolean isEmptyTile(){
        return emptyTile;
    }
}