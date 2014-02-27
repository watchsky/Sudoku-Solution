package com.wu.homework;

import java.awt.*;

/**
 * Created by xiangwu on 2/27/14.
 */
public class Sudoku {
    public static final int LENGTH_OF_SIDE_OF_GRIDS = 3;
    private SquareGrid[][] gridMap;
    private int startPosX, startPosY;
    private int length;
    private SearchProcessListener searchProcessListener;

    public Sudoku(SearchProcessListener searchProcessListener, int startPosX, int startPosY, int length, int mapArray[][]) {
        this.searchProcessListener = searchProcessListener;
        this.startPosX = startPosX;
        this.startPosY = startPosY;
        this.length = length;
        gridMap = new SquareGrid[LENGTH_OF_SIDE_OF_GRIDS][];
        for (int i = 0; i < LENGTH_OF_SIDE_OF_GRIDS; i++) {
            gridMap[i] = new SquareGrid[LENGTH_OF_SIDE_OF_GRIDS];
            for (int j = 0; j < LENGTH_OF_SIDE_OF_GRIDS; j++) {
                gridMap[i][j] = new SquareGrid(startPosX + j * length, startPosY + i * length, length, mapArray[i][j]);
            }
        }
    }

    public void drawMap(Graphics2D g) {
        for (int i = 0; i < LENGTH_OF_SIDE_OF_GRIDS; i++) {
            for (int j = 0; j < LENGTH_OF_SIDE_OF_GRIDS; j++) {
                gridMap[i][j].draw(g);
            }
        }
    }

    public void exchange(int posY1, int posX1, int posY2, int posX2) {
        SquareGrid temp;
        boolean isValid = false;

        if ((posX1 < posX2) && (posX1 + 1 == posX2)) {
            if (posY1 == posY2) {
                gridMap[posY1][posX1].rightWards();
                gridMap[posY2][posX2].leftWards();
                isValid = true;
            }
        }
        if ((posX1 > posX2) && (posX1 - 1 == posX2)) {
            if (posY1 == posY2) {
                gridMap[posY1][posX1].leftWards();
                gridMap[posY2][posX2].rightWards();
                isValid = true;
            }
        }
        if (posY1 < posY2 && (posY1 + 1 == posY2)) {
            if (posX1 == posX2) {
                gridMap[posY1][posX1].downWards();
                gridMap[posY2][posX2].upWards();
                isValid = true;
            }
        }
        if (posY1 > posY2 && (posY1 - 1 == posY2)) {
            if (posX1 == posX2) {
                gridMap[posY1][posX1].upWards();
                gridMap[posY2][posX2].downWards();
                isValid = true;
            }
        }
        if (!isValid)
            return ;
        temp = gridMap[posY1][posX1];
        gridMap[posY1][posX1] = gridMap[posY2][posX2];
        gridMap[posY2][posX2] = temp;
        searchProcessListener.onSearchFinished();
    }
}
