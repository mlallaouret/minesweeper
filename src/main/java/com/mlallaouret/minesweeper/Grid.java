package com.mlallaouret.minesweeper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mlallaouret on 5/22/15.
 * Represent the play grid
 */
public class Grid {
    private int gridHeight;
    private int gridWidth;
    private int numberOfMines;
    private int remainingEmptyCell;
    private Cell[][] cells;

    public Grid() {
    }

    public Grid(int gridWidth, int gridHeight, int numberOfMines) {
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
        this.numberOfMines = numberOfMines;
        remainingEmptyCell = this.gridHeight * this.gridWidth - numberOfMines;
        cells = new Cell[this.gridHeight][this.gridWidth];
        for (int i = 0; i < this.gridHeight; i++) {
            for (int j = 0; j < this.gridWidth; j++) {
                cells[i][j] = new Cell(j, i);
            }
        }
    }

    /**
     * Decrease the number of cell without mine remaining on the grid
     *
     * @throws java.lang.IllegalStateException if remainingEmptyCell is under 0, should not happen
     */
    public void decreaseRemainingEmptyCell() {
        remainingEmptyCell--;
        if (remainingEmptyCell < 0) {
            throw new IllegalStateException("The remaining mines number can't be under 0");
        }
    }

    /**
     * Verify is the cell is a mine
     *
     * @param height the height coordinate of the cell to verify
     * @param width  the width coordinate of the cell to verify
     * @return true if there is a mine in the cell, else false
     */
    public boolean isMineInCell(int height, int width) {
        if (height < 0 || height > this.gridHeight || width < 0 || width > this.gridWidth) {
            throw new IllegalArgumentException("Wrong grid Coordinates");
        }
        return cells[height][width].isContainsMine();
    }

    /**
     * Reveal a cell to the player
     *
     * @param height the height coordinate of the cell to reveal
     * @param width  the width coordinate of the cell to reveal
     */
    public void revealCells(int height, int width) {
        Cell cell = cells[height][width];
        if (!cell.isRevealed()) {
            cell.setRevealed(true);
            if (!cell.isContainsMine()) {
                //If it is not a mine we decrease the number of empty cell remaining on the grid
                decreaseRemainingEmptyCell();
                //If 0 mine around, we discover the cells around
                if (cell.getMineAroundNumber() == 0) {
                    List<Cell> neighbourCells = getNeighbourCells(cell);
                    for (Cell c : neighbourCells) {
                        revealCells(c.getHeightCoordinate(), c.getWidthCoordinate());
                    }
                }
            }
        }
    }

    /**
     * Place mine randomly ont the grid
     */
    public void placeMines() {
        List<Cell> cellsList = new ArrayList<Cell>();
        for (int i = 0; i < this.gridHeight; i++) {
            for (int j = 0; j < this.gridWidth; j++) {
                cellsList.add(cells[i][j]);
            }
        }

        for (int i = 0; i < numberOfMines; i++) {
            int random = (int) (Math.random() * cellsList.size());
            cellsList.get(random).setContainsMine(true);
            cellsList.remove(random);
        }

    }

    /**
     * Calculate all the mine indicator numbers
     */
    public void calculateMineAroundNumber() {

        for (int i = 0; i < this.gridHeight; i++) {
            for (int j = 0; j < this.gridWidth; j++) {
                int count = 0;

                List<Cell> neighbourCells = this.getNeighbourCells(cells[i][j]);
                for (Cell c : neighbourCells) {
                    if (c.isContainsMine()) {
                        count++;
                    }
                }
                cells[i][j].setMineAroundNumber(count);
            }
        }

    }

    /**
     * Retrieve the neighbour cells of a cell in the grid
     *
     * @param cell the cell which we want the neighbours
     * @return A list containing the neighbour cells
     */
    public List<Cell> getNeighbourCells(Cell cell) {
        List<Cell> neighbourCells = new ArrayList<Cell>();

        int cellHeight = cell.getHeightCoordinate();
        int cellWidth = cell.getWidthCoordinate();

        if (cellHeight > 0 && cellWidth > 0) {
            neighbourCells.add(cells[cellHeight - 1][cellWidth - 1]);
        }
        if (cellWidth > 0) {
            neighbourCells.add(cells[cellHeight][cellWidth - 1]);
        }
        if (cellHeight < gridWidth - 1 && cellWidth > 0) {
            neighbourCells.add(cells[cellHeight + 1][cellWidth - 1]);
        }

        if (cellHeight > 0) {
            neighbourCells.add(cells[cellHeight - 1][cellWidth]);
        }
        if (cellHeight < gridWidth - 1) {
            neighbourCells.add(cells[cellHeight + 1][cellWidth]);
        }

        if (cellHeight > 0 && cellWidth < gridHeight - 1) {
            neighbourCells.add(cells[cellHeight - 1][cellWidth + 1]);
        }
        if (cellWidth < gridHeight - 1) {
            neighbourCells.add(cells[cellHeight][cellWidth + 1]);
        }
        if (cellHeight < gridHeight - 1 && cellWidth < gridHeight - 1) {
            neighbourCells.add(cells[cellHeight + 1][cellWidth + 1]);
        }

        return neighbourCells;
    }

    /**
     * Draw the grid
     */
    public void drawGrid() {
        drawHeader();
        for (int i = 0; i < gridHeight; i++) {
            drawLine(i);
        }
        drawFooter();
    }

    private void drawFooter() {
        for (int i = 0; i < this.gridWidth; i++) {
            System.out.print("--");
        }
        System.out.println("--");
    }

    private void drawLine(int lineNumber) {
        System.out.print(lineNumber);
        String icon;
        for (int j = 0; j < this.gridWidth; j++) {
            if (!cells[lineNumber][j].isRevealed()) {
                icon = "X";
            } else if (cells[lineNumber][j].isContainsMine()) {
                icon = "*";
            } else {
                icon = String.valueOf(cells[lineNumber][j].getMineAroundNumber());
            }
            System.out.print("|" + icon);
        }
        System.out.println("|");
    }

    private void drawHeader() {
        System.out.print(" ");
        for (int i = 0; i < this.gridWidth; i++) {
            System.out.print("|" + i);
        }
        System.out.println("|");
        for (int i = 0; i < this.gridWidth; i++) {
            System.out.print("--");
        }
        System.out.println("--");

    }

    public int getRemainingEmptyCell() {
        return remainingEmptyCell;
    }

    public void setRemainingEmptyCell(int remainingEmptyCell) {
        this.remainingEmptyCell = remainingEmptyCell;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public void setGridHeight(int gridHeight) {
        this.gridHeight = gridHeight;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public void setGridWidth(int gridWidth) {
        this.gridWidth = gridWidth;
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }

    public void setNumberOfMines(int numberOfMines) {
        this.numberOfMines = numberOfMines;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }
}
