package com.mlallaouret.minesweeper;

import java.util.Scanner;

/**
 * Created by mlallaouret on 5/22/15.
 * Utils method for the game
 */
public class GameUtil {

    private static final Scanner input = new Scanner(System.in);


    public String getPlayerChoice() {
        return input.nextLine();
    }

    public void printMessageToPlayer(String message) {
        System.out.println(message);
    }

    public Integer parsePlayerChoice(String playerChoice) {
        Integer playerNumberChoice = null;
        try {
            playerNumberChoice = Integer.parseInt(playerChoice);
        } catch (NumberFormatException e) {
        }
        return playerNumberChoice;
    }
}
