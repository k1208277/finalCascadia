import java.util.*;
public class Player {
    private int numOfPineCones, totalHabitatScore;
    private int[] score;
    /*
    score[0] is total score
    score[1] is bear score
    score[2] is elk score
    score[3] is salmon score
    score[4] is hawk score
    score[5] is fox score
    score[6] is





     */
    private Board board;

    public Player(Board b) {
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


//    public int[] getScore() {
//        score[0] = board.bearScore();
//        score[1] = board.elkScore();
//        score[2] = board.salmonScore();
//        score[3] = board.hawkScore();
//        score[4] = board.foxScore();
//
//        score[5] = score[0] + score[1] + score[2] + score[3] + score[4];
//
//
//        score[6] = board.getHabitatScore(1);
//        score[8] = board.getHabitatScore(2);
//        score[10] = board.getHabitatScore(3);
//        score[12] = board.getHabitatScore(4);
//        score[14] = board.getHabitatScore(5);
//        int sum = 0;
//        for (int i = 6; i <= 15; i++) {
//            sum += score[i];
//        }
//        score[16] = sum;
//        sum = 0;
//        score[17] = numOfPineCones;
//        for (int i = 0; i < 18; i++) {
//            sum += score[i];
//        }
//        sum = sum - score[5] - score[16];
//
//        return
//    }


}
