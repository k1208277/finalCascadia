import java.util.*;
public class Player {
    private int numOfPineCones, totalHabitatScore;
    private int[] score;
    /*score[0] is bear score
    score[1] is elk score
    score[2] is salmon score
    score[3] is hawk score
    score[4] is fox score
    score[5] is total wildlife score

    score[6] is biome

     */

    private Board board;


    public Player(Board b)
    {
        score = new int[19];
        board = b;
        totalHabitatScore = 0;
        numOfPineCones = 0;
    }

    public Board getBoard() {
        return board;
    }

    public void addPineCone() {
        numOfPineCones++;
    }

    public void takePineCone() {
        numOfPineCones--;
    }

    public int getPineCones() {
        return numOfPineCones;
    }

//    public int getHabitatScore(int habitatNum)
//    {
//        //Algorithm to find largest habitat score
//        //set score to its place in score arraylist
//        //return score
//    }

//    public int getAnimalScore(int animalNum)
//    {
//        //Algorithm to find the total animal score that satisfies the specific animal scoring card
//        //add to totalHabitat score
//        //return score
//    }


    public void setScore()
    {
        score[0] = board.bearScore();
        score[1] = board.elkScore();
        score[2] = board.salmonScore();
        score[3] = board.hawkScore();
        score[4] = board.foxScore();

        score[5] = score[0] + score[1] + score[2] + score[3] +score[4];


        score[6] = board.getHabitatScore(1);
        score[8] = board.getHabitatScore(2);
        score[10] = board.getHabitatScore(3);
        score[12] = board.getHabitatScore(4);
        score[14] = board.getHabitatScore(5);
        int sum = 0;
        for (int i = 6; i <= 15; i++){
            sum += score[i];
        }
        score[16] = sum;
        sum = 0;
        score[17] = numOfPineCones;
        for (int i = 0; i < 18; i++){
            sum+= score[i];
        }
        sum = sum - score[5] - score[16];
        score[18] = sum;

    }

    public int[] getScore(){
        return score;
    }


    public void setScore(ArrayList<Integer> bonusList)
    {
        score[0] = board.bearScore();
        score[1] = board.elkScore();
        score[2] = board.salmonScore();
        score[3] = board.hawkScore();
        score[4] = board.foxScore();

        score[5] = score[0] + score[1] + score[2] + score[3] +score[4];


        score[6] = board.getHabitatScore(1);
        score[7] = bonusList.get(0);
        score[8] = board.getHabitatScore(2);
        score[9] = bonusList.get(1);
        score[10] = board.getHabitatScore(3);
        score[11] = bonusList.get(2);
        score[12] = board.getHabitatScore(4);
        score[13] = bonusList.get(3);
        score[14] = board.getHabitatScore(5);
        score[15] = bonusList.get(4);
        int sum = 0;
        for (int i = 6; i <= 15; i++){
            sum += score[i];
        }
        score[16] = sum;
        sum = 0;
        score[17] = numOfPineCones;
        for (int i = 0; i < 18; i++){
            sum+= score[i];
        }
        sum = sum - score[5] - score[16];
        score[18] = sum;

    }

    /*
    //CODE FROM GIZMOS
    public void drawWinner(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(600, 801, 1000, 98);
		g.setColor(Color.black);
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 40));
		int temp = 0;
		Iterator<Player> it = game.getAllPlayers().iterator();
		while (it.hasNext()) {
			Player p = it.next();
			if (p.getTotalPoints() > temp) {
				temp = p.getTotalPoints();
			}
		}
		ArrayList<Player> winners = new ArrayList<>();
		it = game.getAllPlayers().iterator();
		while (it.hasNext()) {
			Player p = it.next();
			if (p.getTotalPoints() == temp) {
				winners.add(p);
			}
		}
		if (winners.size() == 1) {
			g.drawString(winners.get(0) + " WINS!", 800, 860);
			return;
		}
		//only for victory points ^^^

		//counting number of cards
		temp = 0;
		for (int i = 0; i < winners.size(); i++) {
			if (winners.get(i).getCardCount() > temp) {
				temp = winners.get(i).getCardCount();
			}
		}

		for (int i = 0; i < winners.size(); i++) {
			if (winners.get(i).getCardCount() < temp) {
				winners.remove(i);
				i--;
			}
		}
		if (winners.size() == 1) {
			g.drawString(winners.get(0) + " WINS!", 800, 860);
			return;
		}

		//marble count
		temp = 0;
		for (int i = 0; i < winners.size(); i++) {
			int marb = winners.get(i).getMarbles().length;
			for (int j = 0; j < winners.get(i).getMarbles().length; j++) {
				if (winners.get(i).getMarbles()[j] == null) {
					marb = j;
					break;
				}
			}
			if (marb > temp) {
				marb = temp;
			}
		} //getting max marble count

		for (int i = 0; i < winners.size(); i++) {
			int marb = winners.get(i).getMarbles().length;
			for (int j = 0; j < winners.get(i).getMarbles().length; j++) {
				if (winners.get(i).getMarbles()[j] == null) {
					marb = j;
					break;
				}
			}
			if (marb < temp) {
				winners.remove(i);
				i--;
			}
		} //removing players that have less than the max

		if (winners.size() == 1) {
			g.drawString(winners.get(0) + " WINS!", 800, 860);
			return;
		}
		else {
			g.drawString(winners.get(winners.size() - 1) + " WINS!", 800, 860);
			return;
		}

	}



     */
}
