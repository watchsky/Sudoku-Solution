package com.wu.homework;

import java.awt.*;

/**
 * Created by xiangwu on 2/27/14.
 */
public class Sudoku implements Direction{
    public static final int LENGTH_OF_SIDE_OF_GRIDS = 3;
    private SquareGrid[][] gridMap;
    private int startIndexX, startIndexY;
    private SearchProcessListener searchProcessListener;

    public Sudoku(SearchProcessListener searchProcessListener, int leftTopX, int leftTopY, int gridLength, int mapArray[][]) {
        this.searchProcessListener = searchProcessListener;
        initGridMap(leftTopX, leftTopY, gridLength, mapArray);
    }

    private void initGridMap(int leftTopX, int leftTopY, int gridLength, int[][] mapArray) {
        gridMap = new SquareGrid[LENGTH_OF_SIDE_OF_GRIDS][];
        for (int i = 0; i < LENGTH_OF_SIDE_OF_GRIDS; i++) {
            gridMap[i] = new SquareGrid[LENGTH_OF_SIDE_OF_GRIDS];
            for (int j = 0; j < LENGTH_OF_SIDE_OF_GRIDS; j++) {
                gridMap[i][j] = new SquareGrid(leftTopX + j * gridLength, leftTopY + i * gridLength, gridLength, mapArray[i][j]);
                if (mapArray[i][j] == 0) {
                    startIndexX = j;
                    startIndexY = i;
                }
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
        searchProcessListener.redraw();
    }

    public void show(SearchedPath path) {
        int nextX, nextY;
        if (path == null || path.getPath() == null)
            return;
        for (Integer dir : path.getPath()) {
            nextX = startIndexX + DX[dir];
            nextY = startIndexY + DY[dir];
            exchange(startIndexY, startIndexX, nextY, nextX);
            try {
                Thread.sleep(500);
            }catch(InterruptedException e) {
                e.printStackTrace();
            }
            startIndexX = nextX;
            startIndexY = nextY;
        }
    }
}
