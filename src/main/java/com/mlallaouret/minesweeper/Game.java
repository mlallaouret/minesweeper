package com.mlallaouret.minesweeper;


import javafx.util.Pair;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mlallaouret on 5/22/15.
 * Control board of the game
 */
public class Game {

    public static final String CHOOSE_GRID_SIZE = "Choose the size of the grid: (width x height)";
    public static final String CHOOSE_COORDINATE = "Choose the coordinate of the cell to uncover: (x, y)";
    public static final String NOT_VALID_INPUT = "This is not a valid input";
    public static final String CHOOSE_MINE_NUMBER = "Choose the number of mines:";
    public static final Pattern GRID_SIZE_PATTERN = Pattern.compile("\\d+\\s*x\\s*\\d+");
    public static final Pattern COORDINATE_PATTERN = Pattern.compile("\\d+\\s*,\\s*\\d+");

    private Grid playGrid;

    private GameUtil gameUtil = new GameUtil();

    public void initializeGame(int gridWidth, int gridHeight, int mineNumbers) {

        playGrid = new Grid(gridWidth, gridHeight, mineNumbers);
        //Place Mine
        playGrid.placeMines();
        //Calculate mine around
        playGrid.calculateMineAroundNumber();

    }

    public void playGame() {
        boolean victory = false;
        //play loop
        while (true) {
            //draw grid
            playGrid.drawGrid();
            // Ask Player choice
            Pair<Integer, Integer> coordinates = askPlayerForCoordinate();
            // Reveal cell
            playGrid.revealCells(coordinates.getValue(), coordinates.getKey());
            // Verify no mine
            if (playGrid.isMineInCell(coordinates.getValue(), coordinates.getKey())) {
                break;
            }
            // Decrease number of cell without mine
            if (playGrid.getRemainingEmptyCell() == 0) {
                victory = true;
                break;
            }
        }
        playGrid.drawGrid();
        // Print end message
        if (victory) {
            gameUtil.printMessageToPlayer("You win !");
        } else {
            gameUtil.printMessageToPlayer("You lose !");
        }

    }

    public int askPlayerForNumberOfMines(int gridWidth, int gridHeight) {
        while (true) {
            gameUtil.printMessageToPlayer(CHOOSE_MINE_NUMBER);
            String playerChoice = gameUtil.getPlayerChoice();
            Integer mineNumber = gameUtil.parsePlayerChoice(playerChoice);
            if (checkMineNumber(mineNumber, gridWidth, gridHeight)) {
                return mineNumber;
            }
        }
    }

    public Pair<Integer, Integer> askPlayerForGridSize() {
        while (true) {
            gameUtil.printMessageToPlayer(CHOOSE_GRID_SIZE);
            String playerChoice = gameUtil.getPlayerChoice();
            Matcher m = GRID_SIZE_PATTERN.matcher(playerChoice);
            if (m.find()) {
                String[] splitGridSize = playerChoice.split("x");
                Integer width = gameUtil.parsePlayerChoice(splitGridSize[0].trim());
                Integer height = gameUtil.parsePlayerChoice(splitGridSize[1].trim());

                if (checkGridDimensionNumber(width) && checkGridDimensionNumber(height)) {
                    return new Pair<Integer, Integer>(width, height);
                }

            } else {
                gameUtil.printMessageToPlayer(NOT_VALID_INPUT);
            }
        }
    }

    public Pair<Integer, Integer> askPlayerForCoordinate() {
        while (true) {
            gameUtil.printMessageToPlayer(CHOOSE_COORDINATE);
            String playerChoice = gameUtil.getPlayerChoice();
            Matcher m = COORDINATE_PATTERN.matcher(playerChoice);
            if (m.matches()) {
                String[] splitGridSize = playerChoice.split(",");
                Integer width = gameUtil.parsePlayerChoice(splitGridSize[0].trim());
                Integer height = gameUtil.parsePlayerChoice(splitGridSize[1].trim());

                if (checkWidthCoordinate(width) && checkHeightCoordinate(height)) {
                    return new Pair<Integer, Integer>(width, height);
                }

            } else {
                gameUtil.printMessageToPlayer(NOT_VALID_INPUT);
            }
        }
    }

    private boolean checkMineNumber(Integer mineNumber, int gridWidth, int gridHeight) {
        if (mineNumber != null && mineNumber > 0 && mineNumber < gridWidth * gridHeight) {
            return true;
        } else {
            gameUtil.printMessageToPlayer(NOT_VALID_INPUT);
        }
        return false;
    }

    private boolean checkGridDimensionNumber(Integer gridDimension) {
        if (gridDimension != null && gridDimension > 0) {
            return true;
        } else {
            gameUtil.printMessageToPlayer(NOT_VALID_INPUT);
        }
        return false;
    }

    private boolean checkWidthCoordinate(Integer widthCoordinate) {
        if (widthCoordinate != null && widthCoordinate >= 0 && widthCoordinate < playGrid.getGridWidth()) {
            return true;
        } else {
            gameUtil.printMessageToPlayer(NOT_VALID_INPUT);
        }
        return false;
    }

    private boolean checkHeightCoordinate(Integer heightCoordinate) {
        if (heightCoordinate != null && heightCoordinate >= 0 && heightCoordinate < playGrid.getGridHeight()) {
            return true;
        } else {
            gameUtil.printMessageToPlayer(NOT_VALID_INPUT);
        }
        return false;
    }
}
