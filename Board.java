import java.util.*;


public class Board
{
    private Tile startTile;
    private int numTiles;
    private int boardWidth, boardHeight;

    public Board(Tile t)
    {
        startTile = t;
    }


    public void addTile(Tile newTile, Tile adjTile, int orientation)
    {

        ArrayList<Tile> traversal = traverse();
        for (Tile t: traversal){
            if (t == adjTile){
                t.getAdjacentTiles().set(orientation, newTile);
                setCoordinates(newTile, adjTile, orientation);
                break;
            }
        }

    }


    public void placeToken(Tile t, Token a)
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
    public int getHabitatScore(int h) {
        ArrayList<Tile> allTiles = traverse();
        ArrayList<Tile> habitatTiles = new ArrayList<Tile>();
        for (int i = 0; i < allTiles.size(); i++) {
            Tile temp = allTiles.get(i);
            if (temp.getAdjacentTiles().contains(h)) {
                habitatTiles.add(temp);
            }
        }
        int max = 0;

        for (int i = 0; i < habitatTiles.size(); i++) {
            Tile temp = habitatTiles.get(i);
            ArrayList<Tile> group = new ArrayList<>();
            Queue<Tile> q = new LinkedList<>();
        }


        return max;
    }


    public int elkScore() {

        return 0;
    }
    public int salmonScore() {
        ArrayList<Tile> allTiles = traverse();
        ArrayList<Tile> allSalmon = new ArrayList<>();
        for (int i = 0; i < allTiles.size(); i++){
            if (allTiles.get(i).getAnimal() == 2){
                allSalmon.add(allTiles.get(i));
            }
        }
        int score= 0;
        for (int i = 0; i < allSalmon.size(); i++){
            Tile st = allSalmon.get(i);
            Queue<Tile> q = new LinkedList<>();
            ArrayList<Tile> run = new ArrayList<>();
            if (!st.isChecked()){
                q.add(st);
                st.setChecker(true);
            }
            while (!q.isEmpty()){
                Tile temp = q.poll();
                run.add(temp);
                for (int j = 0; j <= 5; j++){
                    if (temp.getAdjacent(j) != null && !temp.getAdjacent(j).isChecked() && temp.getAdjacent(j).getAnimal() == 2){
                        temp.getAdjacent(j).setChecker(true);
                        q.add(temp.getAdjacent(j));
                    }
                }
            }
            boolean validRun = true;
            for (int j = 0; j < run.size(); j++){
                int adjSalmon = 0;
                Tile temp = run.get(j);
                for (int k = 0; k <= 5; k++){
                    if (temp.getAdjacent(k).getAnimal() == 2){
                        adjSalmon++;
                    }
                }
                if (adjSalmon > 2){
                    validRun = false;
                }
            }


            if (validRun) {
                switch(run.size()){
                    case 1: score += 2; break;
                    case 2: score += 5; break;
                    case 3: score += 8; break;
                    case 4: score += 12; break;
                    case 5: score += 16; break;
                    case 6: score += 20; break;
                    default: score += 25; break;

                }
            }
        }

        for(int i = 0; i < allTiles.size(); i++){
            allTiles.get(i).setChecker(false);
        }
        return score;
    }
    public int hawkScore() {
        int aloneHawks = 0;
        ArrayList<Tile> allTiles = traverse();
        ArrayList<Tile> allHawks = new ArrayList<>();
        for (int i = 0; i < allTiles.size(); i++){
            if (allTiles.get(i).getAnimal() == 3){
                allHawks.add(allTiles.get(i));
            }
        }


        for (int i = 0; i < allHawks.size(); i++){
            Tile ht = allHawks.get(i);
            boolean isAlone = true;
            for(int j = 0; j <= 5; j++){
                if (ht.getAdjacent(i).getAnimal() == 3){
                    isAlone = false;
                }
            }
            if (isAlone){
                aloneHawks++;
            }
        }
        for(int i = 0; i < allTiles.size(); i++){
            allTiles.get(i).setChecker(false);
        }
        switch(aloneHawks){
            case 0: return 0;
            case 1: return 2;
            case 2: return 5;
            case 3: return 8;
            case 4: return 11;
            case 5: return 14;
            case 6: return 18;
            case 7: return 22;
            default: return 26;
        }


    }
    public int bearScore() {
        ArrayList<Tile> allTiles = traverse();
        ArrayList<Tile> allBears = new ArrayList<>();
        for (int i = 0; i < allTiles.size(); i++){
            if (allTiles.get(i).getAnimal() == 5){
                allBears.add(allTiles.get(i));
            }
        }
        int numPairs = 0;
        for (int i = 0; i < allBears.size(); i++){
            Tile bt = allBears.get(i);
            Queue<Tile> q = new LinkedList<>();
            ArrayList<Tile> group = new ArrayList<>();
            if (!bt.isChecked()){
                q.add(bt);
                bt.setChecker(true);
            }
            while (!q.isEmpty()){
                Tile temp = q.poll();
                group.add(temp);
                for (int j = 0; j <= 5; j++){
                    if (temp.getAdjacent(j) != null && !temp.getAdjacent(j).isChecked() && temp.getAdjacent(j).getAnimal() == 5){
                        temp.getAdjacent(j).setChecker(true);
                        q.add(temp.getAdjacent(j));
                    }
                }
            }
            if (group.size() == 2) {
                numPairs++;
            }
        }


        for(int i = 0; i < allTiles.size(); i++){
            allTiles.get(i).setChecker(false);
        }


        switch(numPairs){
            case 0: return 0;
            case 1: return 4;
            case 2: return 11;
            case 3: return 18;
            default: return 25;
        }






    }
    public int foxScore() {
        ArrayList<Tile> allTiles = traverse();
        ArrayList<Tile> allFoxes = new ArrayList<>();
        for (int i = 0; i < allTiles.size(); i++) {
            if (allTiles.get(i).getAnimal() == 4) {
                allFoxes.add(allTiles.get(i));
            }
        }
        int score = 0;
        for(int i = 0; i < allFoxes.size(); i++){
            Tile ft = allFoxes.get(i);
            TreeSet<Integer> uniques = new TreeSet<>();
            for (int j = 0; j <= 5; j++){
                uniques.add(ft.getAdjacent(j).getAnimal());
            }
            score += uniques.size();
        }
        for(int i = 0; i < allTiles.size(); i++){
            allTiles.get(i).setChecker(false);
        }

        return score;


    }

    public void setCoordinates(){
        startTile.setXCoord((int)(boardWidth/ 2.46));
        startTile.setXCoord((int)(boardHeight / 4.5));
    }

    public void setCoordinates(Tile tile, Tile prev, int orientation){
        if (orientation == 1){
            tile.setYCoord(prev.getYCoord() - (int)(boardWidth / 8.64));
            tile.setXCoord(prev.getXCoord() - (int)(boardWidth / 27.04));
        }
        if (orientation == 2){
            tile.setYCoord(prev.getYCoord() - (int)(boardWidth / 8.64));
            tile.setXCoord(prev.getXCoord() + (int)(boardWidth / 27.04));
        }
        if (orientation == 3){
            tile.setXCoord(prev.getXCoord() + (int)(boardWidth / 13.52));
        }
    }
}
