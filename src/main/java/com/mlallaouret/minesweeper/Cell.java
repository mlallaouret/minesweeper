package com.mlallaouret.minesweeper;

/**
 * Created by mlallaouret on 5/22/15.
 * Represent a Cell on the minesweeper grid
 */
public class Cell {

    boolean containsMine;
    boolean revealed;
    int mineAroundNumber;
    int widthCoordinate;
    int heightCoordinate;

    public Cell(int widthCoordinate, int heightCoordinate) {
        this.widthCoordinate = widthCoordinate;
        this.heightCoordinate = heightCoordinate;
    }

    public boolean isContainsMine() {
        return containsMine;
    }

    public void setContainsMine(boolean containsMine) {
        this.containsMine = containsMine;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    public int getMineAroundNumber() {
        return mineAroundNumber;
    }

    public void setMineAroundNumber(int mineAroundNumber) {
        this.mineAroundNumber = mineAroundNumber;
    }

    public int getWidthCoordinate() {
        return widthCoordinate;
    }

    public int getHeightCoordinate() {
        return heightCoordinate;
    }
}
