import java.util.*;


public class Board
{
    private Tile startTile;
    private int numTiles;
    private int boardWidth, boardHeight;

    public Board(Tile t1, Tile t2, Tile t3, int w, int h) //three more Tile constructors?
    {
        setBoardWidthandHeight(w, h);
        startTile = t1;
        setCoordinatesOfStartTile1();
        /*for (int i = 0; i <= 5; i++){
            addTile(new Tile(), startTile,i);
        }*/
        addTile(t2, t1, 4); //:
        addTile(t3, t1, 3);
        addTile(t2, t3, 5);

    }


    public void addTile(Tile newTile, Tile adjTile, int sideNum) //orientation is the side number of the adjacentTile -> newTile
    {

       /* ArrayList<Tile> traversal = traverse();
        for (Tile t: traversal){
            if (t == adjTile){
                t.getAdjacentTiles().set(sideNum, newTile);
                int temp = sideNum + 3;
                if (temp > 5) temp -= 6;
                newTile.getAdjacentTiles().set(temp, adjTile);
                setCoordinates(newTile, adjTile, sideNum);
                break;
            }
        }*/
        adjTile.getAdjacentTiles().set(sideNum, newTile);
        int temp = sideNum + 3;
        if (temp > 5) temp -= 6;
        newTile.getAdjacentTiles().set(temp, adjTile);
        setCoordinates(newTile, adjTile, sideNum);



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
        //ArrayList<Tile> end = new ArrayList<>();

        for (int i = 0; i < allTiles.size(); i++) {
            Tile temp = allTiles.get(i);
            a:
            for (int j = 0; j <= 5; j++){
                if (temp.getHabitat(j) == h){
                    habitatTiles.add(temp);
                    break a;
                }
            }
        }
        int max = 0;

        for (int i = 0; i < habitatTiles.size(); i++) {
            Tile temp = habitatTiles.get(i);
            HashSet<Tile> group = new HashSet<>();
            Queue<Tile> q = new LinkedList<>();
            if (!temp.isChecked()){
                q.add(temp);
            }
            while (!q.isEmpty()){
                Tile ht = q.poll();
                //end.add(ht);
                group.add(ht);
                ht.setChecker(true);
                for (int j = 0; j <= 5; j++){
                    Tile t = ht.getAdjacent(j);
                    if (t != null) {
                        int otherBiome = j + 3;
                        if (otherBiome > 5) {
                            otherBiome -= 6;
                        }
                        if (ht.getHabitat(j) == t.getHabitat(otherBiome) && ht.getHabitat(j) == h && !t.isChecked()) {
                            q.add(t);
                        }
                    }
                }
            }
            max = Math.max(max, group.size());
            System.out.println(group);
        }

        for(int i = 0; i < habitatTiles.size(); i++){
            habitatTiles.get(i).setChecker(false);
        }

        return max;
    }



    public int elkScore()
    { //Scoring #2 - formations
        ArrayList<Tile> allTiles = traverse();
        ArrayList<Tile> allElk = new ArrayList<>();
        for (int i = 0; i < allTiles.size(); i++){
            if (allTiles.get(i).getAnimal() == 1){
                allElk.add(allTiles.get(i));
            }
        }
        int score= 0;
        for (int i = 0; i < allElk.size(); i++){
            Tile st = allElk.get(i);
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
                    if (temp.getAdjacent(j) != null && !temp.getAdjacent(j).isChecked() && temp.getAdjacent(j).getAnimal() == 1){
                        temp.getAdjacent(j).setChecker(true);
                        q.add(temp.getAdjacent(j));
                    }
                }
            }


            switch(run.size()){
                case 0: {score += 0; break;}
                case 1: {score += 2; break;}
                case 2: {score += 4; break;}
                case 3: {score += 7; break;}
                case 4: {score += 10; break;}
                case 5: {score += 14; break;}
                case 6: {score += 18; break;}
                case 7: {score += 23; break;}
                default: {score += 28; break;}

            }
        }

        for(int i = 0; i < allElk.size(); i++){
            allElk.get(i).setChecker(false);
        }
        return score;
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
                    if (temp.getAdjacent(k) != null && temp.getAdjacent(k).getAnimal() == 2){
                        adjSalmon++;
                    }
                }
                if (adjSalmon > 2){
                    validRun = false;
                    run = new ArrayList<>();
                }
            }

            if (validRun) {
                switch(run.size()){
                    case 0: {score += 0; break;}
                    case 1: {score += 2; break;}
                    case 2: {score += 5; break;}
                    case 3: {score += 8; break;}
                    case 4: {score += 12; break;}
                    case 5: {score += 16; break;}
                    case 6: {score += 20; break;}
                    default: {score += 25; break;}

                }
            }
            else {
                score += 0;
            }
        }

        for(int i = 0; i < allSalmon.size(); i++){
            allSalmon.get(i).setChecker(false);
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
                if (ht.getAdjacent(j) != null && ht.getAdjacent(j).getAnimal() == 3){
                    isAlone = false;
                }
            }
            if (isAlone){
                aloneHawks++;
            }
        }
        for(int i = 0; i < allHawks.size(); i++){
            allHawks.get(i).setChecker(false);
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


        for(int i = 0; i < allBears.size(); i++){
            allBears.get(i).setChecker(false);
        }


        switch(numPairs){
            case 0: return 0;
            case 1: return 4;
            case 2: return 11;
            case 3: return 19;
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
                if (ft.getAdjacent(j) != null)
                uniques.add(ft.getAdjacent(j).getAnimal());
                uniques.remove(0);
            }
            score += uniques.size();
        }
        for(int i = 0; i < allFoxes.size(); i++){
            allFoxes.get(i).setChecker(false);
        }

        return score;


    }

    public void setCoordinatesOfStartTile1(){
        startTile.setXCoord((int)(boardWidth/ 2.46));
        startTile.setYCoord((int)(boardHeight / 4.5));
    }

    public void setCoordinates(Tile tile, Tile prev, int orientation){
        if (orientation == 0){
            tile.setYCoord(prev.getYCoord() - (int)(boardHeight / 8.64));
            tile.setXCoord(prev.getXCoord() - (int)(boardWidth / 27.04));
        }
        if (orientation == 1){
            tile.setYCoord(prev.getYCoord() - (int)(boardHeight/ 8.64));
            tile.setXCoord(prev.getXCoord() + (int)(boardWidth / 27.04));
        }
        if (orientation == 2){
            tile.setXCoord(prev.getXCoord() + (int)(boardWidth / 13.52));
            tile.setYCoord(prev.getYCoord());
        }
        if (orientation == 3){
            tile.setXCoord(prev.getXCoord() + (int)(boardWidth / 27.04));
            tile.setYCoord(prev.getYCoord() + (int)(boardHeight/ 8.64));
        }
        if (orientation == 4){
            tile.setXCoord(prev.getXCoord() - (int)(boardWidth / 27.04));
            tile.setYCoord(prev.getYCoord() + (int)(boardHeight / 8.64));
        }
        if (orientation == 5){
            tile.setXCoord(prev.getXCoord() - (int)(boardWidth / 13.52));
            tile.setYCoord(prev.getYCoord());
        }
    }

    public void updateTileCoords(int x, int y, int width, int height){
        ArrayList<Tile> allTiles = traverse();
        for(int i = 0; i < allTiles.size(); i++){
            allTiles.get(i).setChecker(false);
        }

        setBoardWidthandHeight(width, height);
        startTile.setXCoord((int)(x));
        startTile.setYCoord((int)(y));
        Queue<Tile> q = new LinkedList<>();
        ArrayList<Tile> end =  new ArrayList<>();
        q.add(startTile);
        startTile.setChecker(true);
        while (!q.isEmpty()){
            Tile tile = q.poll();
            end.add(tile);
            //System.out.println(tile.getAdjacentTiles());
            for (int i = 0; i <= 5; i++){
                Tile t = tile.getAdjacent(i);
                if (t != null && !t.isChecked()) {
                    if (i == 0) {
                        t.setYCoord(tile.getYCoord() - (int) (boardHeight / 8.64));
                        t.setXCoord(tile.getXCoord() - (int) (boardWidth / 27.04));
                    }
                    if (i == 1) {
                        t.setYCoord(tile.getYCoord() - (int) (boardHeight / 8.64));
                        t.setXCoord(tile.getXCoord() + (int) (boardWidth / 27.04));
                    }
                    if (i == 2) {
                        t.setXCoord(tile.getXCoord() + (int) (boardWidth / 13.52));
                        t.setYCoord(tile.getYCoord());
                    }
                    if (i == 3) {
                        t.setXCoord(tile.getXCoord() + (int) (boardWidth / 27.04));
                        t.setYCoord(tile.getYCoord() + (int) (boardHeight / 8.64));
                    }
                    if (i == 4) {
                        t.setXCoord(tile.getXCoord() - (int) (boardWidth / 27.04));
                        t.setYCoord(tile.getYCoord() + (int) (boardHeight / 8.64));
                    }
                    if (i == 5) {
                        t.setXCoord(tile.getXCoord() - (int) (boardWidth / 13.52));
                        t.setYCoord(tile.getYCoord());
                    }
                    t.setChecker(true);
                    q.add(t);
                }
            }
        }
        //System.out.println("starter tiles adjacents: " + startTile.getAdjacentTiles());

        for (int i = 0; i < end.size(); i++){
            end.get(i).setChecker(false);
        }

    }

    public void updateTileCoordsSmall(int x, int y, int width, int height){
        ArrayList<Tile> allTiles = traverse();
        for(int i = 0; i < allTiles.size(); i++){
            allTiles.get(i).setChecker(false);
        }

        setBoardWidthandHeight(width, height);
        startTile.setXCoord((int)(x));
        startTile.setYCoord((int)(y));
        Queue<Tile> q = new LinkedList<>();
        ArrayList<Tile> end =  new ArrayList<>();
        q.add(startTile);
        startTile.setChecker(true);
        while (!q.isEmpty()){
            Tile tile = q.poll();
            end.add(tile);
            //System.out.println(tile.getAdjacentTiles());
            for (int i = 0; i <= 5; i++){
                Tile t = tile.getAdjacent(i);
                if (t != null && !t.isChecked()) {
                    if (i == 0) {
                        t.setYCoord(tile.getYCoord() - (int) (boardHeight / 37.241));
                        t.setXCoord(tile.getXCoord() - (int) (boardWidth / 106.667));
                    }
                    if (i == 1) {
                        t.setYCoord(tile.getYCoord() - (int) (boardHeight / 37.241));
                        t.setXCoord(tile.getXCoord() + (int) (boardWidth / 106.667));
                    }
                    if (i == 2) {
                        t.setXCoord(tile.getXCoord() + (int) (boardWidth / 54.857));
                        t.setYCoord(tile.getYCoord());
                    }
                    if (i == 3) {
                        t.setXCoord(tile.getXCoord() + (int) (boardWidth / 106.667));
                        t.setYCoord(tile.getYCoord() + (int) (boardHeight / 37.241));
                    }
                    if (i == 4) {
                        t.setXCoord(tile.getXCoord() - (int) (boardWidth / 106.667));
                        t.setYCoord(tile.getYCoord() + (int) (boardHeight / 37.241));
                    }
                    if (i == 5) {
                        t.setXCoord(tile.getXCoord() - (int) (boardWidth / 54.857));
                        t.setYCoord(tile.getYCoord());
                    }
                    t.setChecker(true);
                    q.add(t);
                }
            }
        }
        //System.out.println("starter tiles adjacents: " + startTile.getAdjacentTiles());

        for (int i = 0; i < end.size(); i++){
            end.get(i).setChecker(false);
        }

    }

    public HashMap<Tile, ArrayList<Integer>> allNullTiles(){
        ArrayList<Tile> allTiles = traverse();
        HashMap<Tile, ArrayList<Integer>> nullMap = new HashMap<>();
        for (Tile t: allTiles){
            ArrayList<Integer> nullSideNum = new ArrayList<>();
            for (int i = 0; i <= 5; i++){
                if (t.getAdjacent(i) == null){
                    nullSideNum.add(i);
                }
            }
            if(!nullSideNum.isEmpty()){
                nullMap.put(t, nullSideNum);
            }
        }
        return nullMap;
    }

    public Tile getStartTile() {return startTile;}

    public void setBoardWidthandHeight(int w, int h) {
        boardWidth = w;
        boardHeight = h;
    }

    public void updateTileCoords(){
        ArrayList<Tile> allTiles = traverse();
        for (int i = 0; i < allTiles.size(); i++){
            allTiles.get(i).setChecker(false);
        }
        startTile.setXCoord((int)(boardWidth/ 2.46));
        startTile.setYCoord((int)(boardHeight / 4.5));
        Queue<Tile> q = new LinkedList<>();
        q.add(startTile);
        startTile.setChecker(true);
        while (!q.isEmpty()){
            Tile tile = q.poll();

            for (int i = 0; i <= 5; i++){
                Tile t = tile.getAdjacent(i);
                if (t != null && !t.isChecked()) {
                    if (i == 0) {
                        t.setYCoord(tile.getYCoord() - (int) (boardHeight / 8.64));
                        t.setXCoord(tile.getXCoord() - (int) (boardWidth / 27.04));
                    }
                    if (i == 1) {
                        t.setYCoord(tile.getYCoord() - (int) (boardHeight / 8.64));
                        t.setXCoord(tile.getXCoord() + (int) (boardWidth / 27.04));
                    }
                    if (i == 2) {
                        t.setXCoord(tile.getXCoord() + (int) (boardWidth / 13.52));
                        t.setYCoord(tile.getYCoord());
                    }
                    if (i == 3) {
                        t.setXCoord(tile.getXCoord() + (int) (boardWidth / 27.04));
                        t.setYCoord(tile.getYCoord() + (int) (boardHeight / 8.64));
                    }
                    if (i == 4) {
                        t.setXCoord(tile.getXCoord() - (int) (boardWidth / 27.04));
                        t.setYCoord(tile.getYCoord() + (int) (boardHeight / 8.64));
                    }
                    if (i == 5) {
                        t.setXCoord(tile.getXCoord() - (int) (boardWidth / 13.52));
                        t.setYCoord(tile.getYCoord());
                    }
                    t.setChecker(true);
                    q.add(t);
                }
            }
        }
        ArrayList<Tile> traversal = traverse();
        for (int i = 0; i < traversal.size(); i++){
            traversal.get(i).setChecker(false);
        }

    }

}
