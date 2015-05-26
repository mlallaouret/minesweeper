package com.mlallaouret.minesweeper;

import javafx.util.Pair;

/**
 * Created by mlallaouret on 5/22/15.
 */
public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        Pair<Integer, Integer> gridSize = game.askPlayerForGridSize();
        int minesNumber = game.askPlayerForNumberOfMines(gridSize.getKey(), gridSize.getValue());

        game.initializeGame(gridSize.getKey(), gridSize.getValue(), minesNumber);
        game.playGame();

    }
}
