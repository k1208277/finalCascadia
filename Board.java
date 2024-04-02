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
        if (startTile == null){
            return null;
        }
        Queue<Tile> q = new LinkedList<>();
        ArrayList<Tile> traversal = new ArrayList<>();
        q.add(startTile);
        startTile.setChecker(true);
        while (!q.isEmpty()){
            Tile temp = q.poll();
            traversal.add(temp);
            for (int i = 0; i <= 5; i++){
                if (temp.getAdjacent(i) != null && !temp.getAdjacent(i).isChecked()){
                    temp.getAdjacent(i).setChecker(true);
                    q.add(temp.getAdjacent(i));
                }
            }
        }
        for (int i = 0; i < traversal.size(); i++){
            traversal.get(i).setChecker(false);
        }
        return traversal;
    }

    public int elkScore() {

    }
    public int salmonScore() {

    }
    public int hawkScore() {

    }
    public int bearScore() {

    }
    public int foxScore() {

    }
}