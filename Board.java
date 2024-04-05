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


                }
            }
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
        for(int i = 0; i < allTiles.size(); i++){
            allTiles.get(i).setChecker(false);
        }


        switch(aloneHawks){
            case 0: return 0;break;
            case 1: return 2;break;
            case 2: return 5;break;
            case 3: return 8;break;
            case 4: return 11;break;
            case 5: return 14;break;
            case 6: return 18;break;
            case 7: return 22;break;
            default: return 26;break;
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


        return score;


    }
}
