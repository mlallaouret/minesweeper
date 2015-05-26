package com.mlallaouret.minesweeper;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by mlallaouret on 5/22/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GameTest {


    @InjectMocks
    public Game game;

    @Mock
    public Grid grid;


    @Mock
    public GameUtil gameUtil;

    @Test
    public void testAskPlayerForNumberOfMinesNominal() {
        int gridWidth = 5;
        int gridHeight = 5;
        String playerChoice = "4";
        int expectedMineNumber = 4;

        Mockito.when(gameUtil.getPlayerChoice()).thenReturn(playerChoice);
        Mockito.when(gameUtil.parsePlayerChoice(playerChoice)).thenReturn(4);

        int mineNumber = game.askPlayerForNumberOfMines(gridWidth, gridHeight);

        assertEquals(expectedMineNumber, mineNumber);
    }

    @Test
    public void testAskPlayerForNumberOfMinesFailedTooHighMineNumber() {
        int gridWidth = 5;
        int gridHeight = 5;
        String firstPlayerChoice = "50";
        String secondPlayerChoice = "4";
        int firstParseChoice = 50;
        int secondParseChoice = 4;
        int expectedMineNumber = 4;

        Mockito.when(gameUtil.getPlayerChoice()).thenReturn(firstPlayerChoice).thenReturn(secondPlayerChoice);
        Mockito.when(gameUtil.parsePlayerChoice(firstPlayerChoice)).thenReturn(firstParseChoice);
        Mockito.when(gameUtil.parsePlayerChoice(secondPlayerChoice)).thenReturn(secondParseChoice);

        int mineNumber = game.askPlayerForNumberOfMines(gridWidth, gridHeight);

        Mockito.verify(gameUtil, Mockito.times(2)).printMessageToPlayer(Game.CHOOSE_MINE_NUMBER);
        Mockito.verify(gameUtil, Mockito.times(1)).printMessageToPlayer(Game.NOT_VALID_INPUT);

        assertEquals(expectedMineNumber, mineNumber);
    }

    @Test
    public void testAskPlayerForNumberOfMinesFailedMineNumberUnderZero() {
        int gridWidth = 5;
        int gridHeight = 5;
        String firstPlayerChoice = "-3";
        String secondPlayerChoice = "4";
        int firstParseChoice = -3;
        int secondParseChoice = 4;
        int expectedMineNumber = 4;

        Mockito.when(gameUtil.getPlayerChoice()).thenReturn(firstPlayerChoice).thenReturn(secondPlayerChoice);
        Mockito.when(gameUtil.parsePlayerChoice(firstPlayerChoice)).thenReturn(firstParseChoice);
        Mockito.when(gameUtil.parsePlayerChoice(secondPlayerChoice)).thenReturn(secondParseChoice);

        int mineNumber = game.askPlayerForNumberOfMines(gridWidth, gridHeight);

        Mockito.verify(gameUtil, Mockito.times(2)).printMessageToPlayer(Game.CHOOSE_MINE_NUMBER);
        Mockito.verify(gameUtil, Mockito.times(1)).printMessageToPlayer(Game.NOT_VALID_INPUT);

        assertEquals(expectedMineNumber, mineNumber);
    }

    @Test
    public void testAskPlayerForNumberOfMinesFailedMineNumberNotANumber() {
        int gridWidth = 5;
        int gridHeight = 5;
        String firstPlayerChoice = "jifhzj";
        String secondPlayerChoice = "4";
        int secondParseChoice = 4;
        int expectedMineNumber = 4;

        Mockito.when(gameUtil.getPlayerChoice()).thenReturn(firstPlayerChoice).thenReturn(secondPlayerChoice);
        Mockito.when(gameUtil.parsePlayerChoice(firstPlayerChoice)).thenReturn(null);
        Mockito.when(gameUtil.parsePlayerChoice(secondPlayerChoice)).thenReturn(secondParseChoice);

        int mineNumber = game.askPlayerForNumberOfMines(gridWidth, gridHeight);

        Mockito.verify(gameUtil, Mockito.times(2)).printMessageToPlayer(Game.CHOOSE_MINE_NUMBER);
        Mockito.verify(gameUtil, Mockito.times(1)).printMessageToPlayer(Game.NOT_VALID_INPUT);

        assertEquals(expectedMineNumber, mineNumber);
    }

    @Test
    public void testAskPlayerForCoordinatesNominal() {
        Pair<Integer, Integer> expectedCoordinates = Pair.of(2, 2);
        String userInput = "2,2";
        String splitUserInput = "2";
        int gridDimension = 6;


        Mockito.when(gameUtil.getPlayerChoice()).thenReturn(userInput);
        Mockito.when(gameUtil.parsePlayerChoice(splitUserInput)).thenReturn(2);
        Mockito.when(grid.getGridHeight()).thenReturn(gridDimension);
        Mockito.when(grid.getGridWidth()).thenReturn(gridDimension);

        Pair<Integer, Integer> coordinates = game.askPlayerForCoordinate();

        Mockito.verify(gameUtil, Mockito.times(1)).printMessageToPlayer(Game.CHOOSE_COORDINATE);

        assertEquals(expectedCoordinates, coordinates);
    }

    @Test
    public void testAskPlayerForCoordinatesFailedWrongFormat() {
        Pair<Integer, Integer> expectedCoordinates = Pair.of(2, 2);
        String wrongUserInput = "gjhjg";
        String goodUserInput = "2,2";
        String splitUserInput = "2";
        int gridDimension = 6;


        Mockito.when(gameUtil.getPlayerChoice()).thenReturn(wrongUserInput, goodUserInput);
        Mockito.when(gameUtil.parsePlayerChoice(splitUserInput)).thenReturn(2);
        Mockito.when(grid.getGridHeight()).thenReturn(gridDimension);
        Mockito.when(grid.getGridWidth()).thenReturn(gridDimension);

        Pair<Integer, Integer> coordinates = game.askPlayerForCoordinate();

        Mockito.verify(gameUtil, Mockito.times(2)).printMessageToPlayer(Game.CHOOSE_COORDINATE);
        Mockito.verify(gameUtil, Mockito.times(1)).printMessageToPlayer(Game.NOT_VALID_INPUT);

        assertEquals(expectedCoordinates, coordinates);
    }

    @Test
    public void testAskPlayerForCoordinatesFailedWithTooHigh() {
        Pair<Integer, Integer> expectedCoordinates = Pair.of(2, 2);
        String wrongUserInput = "6,4";
        String goodUserInput = "2,2";
        String wrongWidthUserInput = "6";
        String wrongHeightUserInput = "4";
        String goodDimensionUserInput = "2";
        int gridDimension = 6;


        Mockito.when(gameUtil.getPlayerChoice()).thenReturn(wrongUserInput, goodUserInput);
        Mockito.when(gameUtil.parsePlayerChoice(wrongWidthUserInput)).thenReturn(6);
        Mockito.when(gameUtil.parsePlayerChoice(wrongHeightUserInput)).thenReturn(4);
        Mockito.when(gameUtil.parsePlayerChoice(goodDimensionUserInput)).thenReturn(2);
        Mockito.when(grid.getGridHeight()).thenReturn(gridDimension);
        Mockito.when(grid.getGridWidth()).thenReturn(gridDimension);

        Pair<Integer, Integer> coordinates = game.askPlayerForCoordinate();

        Mockito.verify(gameUtil, Mockito.times(2)).printMessageToPlayer(Game.CHOOSE_COORDINATE);
        Mockito.verify(gameUtil, Mockito.times(1)).printMessageToPlayer(Game.NOT_VALID_INPUT);

        assertEquals(expectedCoordinates, coordinates);
    }


    @Test
    public void testAskPlayerForGridSizeNominal() {
        Pair<Integer, Integer> expectedCoordinates = Pair.of(5, 5);
        String userInput = "5x5";
        String splitUserInput = "5";

        Mockito.when(gameUtil.getPlayerChoice()).thenReturn(userInput);
        Mockito.when(gameUtil.parsePlayerChoice(splitUserInput)).thenReturn(5);

        Pair<Integer, Integer> gridSize = game.askPlayerForGridSize();

        Mockito.verify(gameUtil, Mockito.times(1)).printMessageToPlayer(Game.CHOOSE_GRID_SIZE);

        assertEquals(expectedCoordinates, gridSize);
    }

    @Test
    public void testAskPlayerForGridSizeFailedDimensionNegative() {
        Pair<Integer, Integer> expectedCoordinates = Pair.of(5, 5);
        String wrongUserInput = "-5x-5";
        String goodUserInput = "5x5";
        String wrongSplitUserInput = "-5";
        String goodSplitUserInput = "5";

        Mockito.when(gameUtil.getPlayerChoice()).thenReturn(wrongUserInput, goodUserInput);
        Mockito.when(gameUtil.parsePlayerChoice(wrongSplitUserInput)).thenReturn(-5);
        Mockito.when(gameUtil.parsePlayerChoice(goodSplitUserInput)).thenReturn(5);

        Pair<Integer, Integer> gridSize = game.askPlayerForGridSize();

        Mockito.verify(gameUtil, Mockito.times(2)).printMessageToPlayer(Game.CHOOSE_GRID_SIZE);
        Mockito.verify(gameUtil, Mockito.times(1)).printMessageToPlayer(Game.NOT_VALID_INPUT);

        assertEquals(expectedCoordinates, gridSize);
    }

}
