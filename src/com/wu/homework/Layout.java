package com.wu.homework;

/**
 * Created by xiangwu on 3/3/14.
 */
public class Layout {

    private int lengthOfSideOfGrids;
    private int[][] valueArray;
    private int indexX, indexY;

    public Layout(int[][] initMapArray, int lengthOfSideOfGrids) {
        this.lengthOfSideOfGrids = lengthOfSideOfGrids;
        this.valueArray = new int[lengthOfSideOfGrids][];
        int blankAmount = 0;
        for (int i = 0; i < lengthOfSideOfGrids; i++) {
            this.valueArray[i] = new int[lengthOfSideOfGrids];
            for (int j = 0; j < lengthOfSideOfGrids; j++) {
                this.valueArray[i][j] = initMapArray[i][j];
                if (initMapArray[i][j] == 0) {
                    indexX = j;
                    indexY = i;
                    blankAmount++;
                }
            }
        }
        if (blankAmount != 1)
            throw new IllegalArgumentException("More than one blank");
    }

    public int getLengthOfSideOfGrids() {
        return lengthOfSideOfGrids;
    }

    public int[][] getValueArray() {
        return valueArray;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof Layout))
            return false;
        Layout other = (Layout) obj;
        int[][] otherValueArray = other.getValueArray();
        for (int i = 0; i < lengthOfSideOfGrids; i++) {
            for (int j = 0; j < lengthOfSideOfGrids; j++) {
                if (valueArray[i][j] != otherValueArray[i][j])
                    return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < lengthOfSideOfGrids; i++) {
            for (int j = 0; j < lengthOfSideOfGrids; j++) {
                result += valueArray[i][j];
                result += " ";
            }
        }
        return result;
    }

    public Layout moveLeft() throws MovingOutOfBoundaryException {
        if (indexX == 0)
            throw new MovingOutOfBoundaryException("Out of Boundary Exception");
        int nextX = indexX - 1;
        return horizontallyMove(nextX);
    }

    public Layout moveRight() throws MovingOutOfBoundaryException {
        if (indexX == lengthOfSideOfGrids - 1)
            throw new MovingOutOfBoundaryException("Out of Boundary Exception");
        int nextX = indexX + 1;
        return horizontallyMove(nextX);
    }

    private Layout horizontallyMove(int nextX) {
        int temp;
        temp = valueArray[indexY][indexX];
        valueArray[indexY][indexX] = valueArray[indexY][nextX];
        valueArray[indexY][nextX] = temp;
        indexX = nextX;
        return this;
    }

    public Layout moveUp() throws MovingOutOfBoundaryException {
        if (indexY == 0)
            throw new MovingOutOfBoundaryException("Out of Boundary Exception");
        int nextY = indexY - 1;
        return verticallyMove(nextY);
    }

    public Layout moveDown() throws MovingOutOfBoundaryException {
        if (indexY == lengthOfSideOfGrids - 1)
            throw new MovingOutOfBoundaryException("Out of Boundary Exception");
        int nextY = indexY + 1;
        return verticallyMove(nextY);
    }

    private Layout verticallyMove(int nextY) {
        int temp;
        temp = valueArray[indexY][indexX];
        valueArray[indexY][indexX] = valueArray[nextY][indexX];
        valueArray[nextY][indexX] = temp;
        indexY = nextY;
        return this;
    }

    public Layout step(SearchedPath path) throws MovingOutOfBoundaryException {
        for (Integer integer : path.getPath()) {
            move(integer);
        }
        return this;
    }

    private void move(Integer direction) throws MovingOutOfBoundaryException {
        switch (direction) {
            case 0:
                moveUp();
                break;
            case 1:
                moveLeft();
                break;
            case 2:
                moveDown();
                break;
            case 3:
                moveRight();
                break;
        }
    }
}
