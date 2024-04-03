import java.util.*;
public class Player
{
    private int numOfPineCones, totalHabitatScore;
    private int[] score; //score[0] = total score of habitats, rest is each animal score
    private Board board;
    public Player(Board b)
    {
        board = b;
        totalHabitatScore = 0;
        numOfPineCones = 0;
    }

    public Board getBoard()
    {
        return board;
    }

    public void addPineCone()
    {
        numOfPineCones++;
    }

    public void takePineCone()
    {
        numOfPineCones--;
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

    public int getScore()
    {
        int totalScore = 0;
        for (int i = 0; i < score.length; i++)
            totalScore += score[i];
        return totalScore;
    }
}
