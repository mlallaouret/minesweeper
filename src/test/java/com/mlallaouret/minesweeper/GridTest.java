package com.mlallaouret.minesweeper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mlallaouret on 5/22/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GridTest {

    @InjectMocks
    public Grid grid;


    @Test
    public void testDecreaseRemainingEmptyCellNominal() {
        int initialRemainingEmptyCell = 3;
        int expectedRemainingEmptyCell = 2;

        grid.setRemainingEmptyCell(initialRemainingEmptyCell);

        grid.decreaseRemainingEmptyCell();

        Assert.assertEquals(expectedRemainingEmptyCell, grid.getRemainingEmptyCell());
    }

    @Test(expected = IllegalStateException.class)
    public void testDecreaseRemainingEmptyCellFailedUnderZero() {
        grid.setRemainingEmptyCell(1);

        grid.decreaseRemainingEmptyCell();
        grid.decreaseRemainingEmptyCell();
    }

    @Test
    public void testIsMineInCellNominalTrue() {
        boolean expectedIsMineInCell = true;

        Cell minedCell = new Cell(0, 0);
        minedCell.setContainsMine(true);

        Cell[][] cells = {{minedCell}};
        grid.setCells(cells);

        boolean mineInCell = grid.isMineInCell(0, 0);

        Assert.assertEquals(expectedIsMineInCell, mineInCell);
    }

    @Test
    public void testIsMineInCellNominalFalse() {
        boolean expectedIsMineInCell = false;

        Cell minedCell = new Cell(0, 0);
        minedCell.setContainsMine(false);

        Cell[][] cells = {{minedCell}};
        grid.setCells(cells);

        boolean mineInCell = grid.isMineInCell(0, 0);

        Assert.assertEquals(expectedIsMineInCell, mineInCell);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsMineInCellFailedWrongCoordinates() {
        grid.setGridHeight(4);
        grid.setGridWidth(4);

        grid.isMineInCell(5, 1);
    }

    @Test
    public void testGetNeighbourCellsInCenter() {
        Cell[][] squareGrid = createSquareGrid();
        grid.setCells(squareGrid);
        Cell centerCell = new Cell(1, 1);
        List<Cell> neighbourCells = grid.getNeighbourCells(centerCell);

        Assert.assertEquals(8, neighbourCells.size());
    }

    @Test
    public void testGetNeighbourCellsInUpperLeft() {
        Cell[][] squareGrid = createSquareGrid();
        grid.setCells(squareGrid);
        Cell centerCell = new Cell(0, 0);
        List<Cell> neighbourCells = grid.getNeighbourCells(centerCell);

        Assert.assertEquals(3, neighbourCells.size());
    }

    @Test
    public void testGetNeighbourCellsInMidTop() {
        Cell[][] squareGrid = createSquareGrid();
        grid.setCells(squareGrid);
        Cell centerCell = new Cell(0, 1);
        List<Cell> neighbourCells = grid.getNeighbourCells(centerCell);

        Assert.assertEquals(5, neighbourCells.size());
    }

    @Test
    public void testGetNeighbourCellsInUpperRight() {
        Cell[][] squareGrid = createSquareGrid();
        grid.setCells(squareGrid);
        Cell centerCell = new Cell(0, 2);
        List<Cell> neighbourCells = grid.getNeighbourCells(centerCell);

        Assert.assertEquals(3, neighbourCells.size());
    }

    @Test
    public void testGetNeighbourCellsInMidRight() {
        Cell[][] squareGrid = createSquareGrid();
        grid.setCells(squareGrid);
        Cell centerCell = new Cell(1, 2);
        List<Cell> neighbourCells = grid.getNeighbourCells(centerCell);

        Assert.assertEquals(5, neighbourCells.size());
    }

    @Test
    public void testGetNeighbourCellsInLowerLeft() {
        Cell[][] squareGrid = createSquareGrid();
        grid.setCells(squareGrid);
        Cell centerCell = new Cell(2, 0);
        List<Cell> neighbourCells = grid.getNeighbourCells(centerCell);

        Assert.assertEquals(3, neighbourCells.size());
    }

    @Test
    public void testGetNeighbourCellsInMidLower() {
        Cell[][] squareGrid = createSquareGrid();
        grid.setCells(squareGrid);
        Cell centerCell = new Cell(2, 1);
        List<Cell> neighbourCells = grid.getNeighbourCells(centerCell);

        Assert.assertEquals(5, neighbourCells.size());
    }

    @Test
    public void testGetNeighbourCellsInLowerRight() {
        Cell[][] squareGrid = createSquareGrid();
        grid.setCells(squareGrid);
        Cell centerCell = new Cell(2, 2);
        List<Cell> neighbourCells = grid.getNeighbourCells(centerCell);

        Assert.assertEquals(3, neighbourCells.size());
    }


    @Test
    public void testCalculateMineAroundNumbers() {
        Cell[][] squareGrid = createSquareGrid();
        grid.setCells(squareGrid);
        grid.getCells()[0][0].setContainsMine(true);

        grid.calculateMineAroundNumber();

        Cell[][] cells = grid.getCells();

        Assert.assertEquals(0, cells[0][0].getMineAroundNumber());
        Assert.assertEquals(1, cells[0][1].getMineAroundNumber());
        Assert.assertEquals(1, cells[1][0].getMineAroundNumber());
        Assert.assertEquals(1, cells[1][1].getMineAroundNumber());
        Assert.assertEquals(0, cells[0][2].getMineAroundNumber());
        Assert.assertEquals(0, cells[1][2].getMineAroundNumber());
        Assert.assertEquals(0, cells[2][0].getMineAroundNumber());
        Assert.assertEquals(0, cells[2][1].getMineAroundNumber());
        Assert.assertEquals(0, cells[2][2].getMineAroundNumber());

    }

    @Test
    public void testRevealOnlyOneCell() {
        Cell[][] squareGrid = createSquareGrid();
        grid.setCells(squareGrid);
        grid.getCells()[0][0].setContainsMine(true);
        grid.setRemainingEmptyCell(8);

        grid.calculateMineAroundNumber();
        grid.revealCells(0, 1);

        Cell[][] cells = grid.getCells();

        Assert.assertEquals(false, cells[0][0].isRevealed());
        Assert.assertEquals(true, cells[0][1].isRevealed());
        Assert.assertEquals(false, cells[1][0].isRevealed());
        Assert.assertEquals(false, cells[1][1].isRevealed());
        Assert.assertEquals(false, cells[0][2].isRevealed());
        Assert.assertEquals(false, cells[1][2].isRevealed());
        Assert.assertEquals(false, cells[2][0].isRevealed());
        Assert.assertEquals(false, cells[2][1].isRevealed());
        Assert.assertEquals(false, cells[2][2].isRevealed());

    }

    @Test
    public void testRevealMoreThanOneCell() {
        Cell[][] squareGrid = createSquareGrid();
        grid.setCells(squareGrid);
        grid.getCells()[0][0].setContainsMine(true);
        grid.setRemainingEmptyCell(8);

        grid.calculateMineAroundNumber();
        grid.revealCells(2, 2);

        Cell[][] cells = grid.getCells();

        Assert.assertEquals(false, cells[0][0].isRevealed());
        Assert.assertEquals(true, cells[0][1].isRevealed());
        Assert.assertEquals(true, cells[1][0].isRevealed());
        Assert.assertEquals(true, cells[1][1].isRevealed());
        Assert.assertEquals(true, cells[0][2].isRevealed());
        Assert.assertEquals(true, cells[1][2].isRevealed());
        Assert.assertEquals(true, cells[2][0].isRevealed());
        Assert.assertEquals(true, cells[2][1].isRevealed());
        Assert.assertEquals(true, cells[2][2].isRevealed());

    }


    @Test
    public void testPlaceMines() {
        Cell[][] squareGrid = createSquareGrid();
        grid.setCells(squareGrid);
        grid.setNumberOfMines(2);

        grid.placeMines();

        List<Cell> cellListFromCellArray = getCellListFromCellArray(grid.getCells(), 3, 3);

        int mineCount = 0;

        for (Cell c : cellListFromCellArray) {
            if (c.isContainsMine()) {
                mineCount++;
            }
        }

        Assert.assertEquals(2, mineCount);
    }

    private List<Cell> getCellListFromCellArray(Cell[][] array, int height, int width) {
        List<Cell> cellsList = new ArrayList<Cell>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cellsList.add(array[i][j]);
            }
        }

        return cellsList;
    }

    private Cell[][] createSquareGrid() {
        int gridHeight = 3;
        int gridWidth = 3;
        grid.setGridWidth(3);
        grid.setGridHeight(3);

        Cell[][] cells = new Cell[gridHeight][gridWidth];
        for (int i = 0; i < gridHeight; i++) {
            for (int j = 0; j < gridWidth; j++) {
                cells[i][j] = new Cell(j, i);
            }
        }
        return cells;

    }

}
