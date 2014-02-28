package com.wu.homework;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-12
 * Time: 下午8:01
 * To change this template use File | Settings | File Templates.
 */
public class MainPanel extends JPanel implements SearchProcessListener{
    private int[][] primaryMapArray = {{2, 8, 3}, {1, 6, 4}, {7, 0, 5}};
    private int[][] finalMapArray = {{1, 2, 3}, {8, 0, 4}, {7, 6, 5}};
    private Sudoku primaryMap;
    private Sudoku finalMap;
    private int startPosX = 50, startPosY = 50;
    private int length = 50;
    private int lengthOfSideOfGrids;
    private boolean doTheSearchHaveResult = true;

    public MainPanel() {
//  初始状态图
        primaryMap = new Sudoku(this, startPosX, startPosY, length, primaryMapArray);
//  最终状态图
        finalMap = new Sudoku(this, startPosX, startPosY+200, length, finalMapArray);
        lengthOfSideOfGrids = Sudoku.LENGTH_OF_SIDE_OF_GRIDS;

        this.addMouseMotionListener(new MouseMotionAction());
        this.addMouseListener(new MouseAction());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);    //To change body of overridden methods use File | Settings | File Templates.
        Graphics2D g2D = (Graphics2D)g;
        primaryMap.drawMap(g2D);
        g2D.drawString("目标：", 20, 240);
        finalMap.drawMap(g2D);
        if (doTheSearchHaveResult == false)
            g2D.drawString("没有结果", 20, 20);
    }

    @Override
    public int getLengthOfSideOfGrids() {
        return lengthOfSideOfGrids;
    }

    @Override
    public int[][] getInitMapArray() {
        return primaryMapArray;
    }

    @Override
    public int[][] getExpectedResultMapArray() {
        return finalMapArray;
    }

    @Override
    public Sudoku getPrimaryMap() {
        return primaryMap;
    }

    @Override
    public void setSearchResult(boolean result) {
        doTheSearchHaveResult = result;
    }

    @Override
    public void onSearchFinished() {
        repaint();
    }

    public void doDepthFirstSearch() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new DepthFirstSearcher(MainPanel.this).doSearch(primaryMapArray, finalMapArray).printDirection();
            }
        }).start();
    }

    public void doBreadthFirstSearch() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new BreadthFirstSearcher(MainPanel.this).doSearch();
            }
        }).start();
    }

    public void doIntelligentSearch() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new IntelligentSearcher(MainPanel.this).doSearch();
            }
        }).start();
    }

    public void restore() {
        doTheSearchHaveResult = true;
        primaryMap = new Sudoku(this, startPosX, startPosY, length, primaryMapArray);
        repaint();
    }

    private int pressedPosX, pressedPosY;
    private int mapIndexX, mapIndexY;

    class MouseMotionAction extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent e) {
            super.mouseDragged(e);    //To change body of overridden methods use File | Settings | File Templates.
            dragSquareGrid(e);
        }

        private void dragSquareGrid(MouseEvent e) {
            int cuurrentPosX = e.getX(), currentPosY = e.getY();
            int currentIndexX = (cuurrentPosX - startPosX) / length;
            int currentIndexY;
            if (isPressedAtTheFinalMap(currentPosY))
                currentIndexY = (currentPosY - startPosY - 200) / length;
            else
                currentIndexY = (currentPosY - startPosY) / length;
            if (mapIndexX >= lengthOfSideOfGrids || mapIndexX < 0 || mapIndexY >= lengthOfSideOfGrids || mapIndexY < 0) {
                if (currentIndexX >= lengthOfSideOfGrids || currentIndexX < 0 || currentIndexY >= lengthOfSideOfGrids || currentIndexY < 0)
                    return;
                pressedPosX = cuurrentPosX;
                pressedPosY = currentPosY;
                mapIndexX = currentIndexX;
                mapIndexY = currentIndexY;
            }
            doDrag(currentIndexX, currentIndexY);
            pressedPosX = cuurrentPosX;
            pressedPosY = currentPosY;
            mapIndexX = currentIndexX;
            mapIndexY = currentIndexY;
        }

        private void doDrag(int currentIndexX, int currentIndexY) {
            if (currentIndexX >= lengthOfSideOfGrids || currentIndexX < 0 || currentIndexY >= lengthOfSideOfGrids || currentIndexY < 0)
                return;
            if (isPressedAtTheFinalMap(pressedPosY)) {
                int tmp = finalMapArray[currentIndexY][currentIndexX];
                finalMapArray[currentIndexY][currentIndexX] = finalMapArray[mapIndexY][mapIndexX];
                finalMapArray[mapIndexY][mapIndexX] = tmp;
                finalMap.exchange(mapIndexY, mapIndexX, currentIndexY, currentIndexX);
            }else {
                int tmp = primaryMapArray[currentIndexY][currentIndexX];
                primaryMapArray[currentIndexY][currentIndexX] = primaryMapArray[mapIndexY][mapIndexX];
                primaryMapArray[mapIndexY][mapIndexX] = tmp;
                primaryMap.exchange(mapIndexY, mapIndexX, currentIndexY, currentIndexX);
            }
        }

        private boolean isPressedAtTheFinalMap(int pressedPosY) {
            return pressedPosY >= 200;
        }
    }

    class MouseAction implements MouseListener {
        @Override
        public void mousePressed(MouseEvent e) {
            setMapIndex(e);
        }

        private void setMapIndex(MouseEvent e) {
            pressedPosX = e.getX();
            pressedPosY = e.getY();
            mapIndexX = (pressedPosX - startPosX) / length;
            if (isPressedAtTheFinalMap(pressedPosY))
                mapIndexY = (pressedPosY - startPosY - 200) / length;
            else
                mapIndexY = (pressedPosY - startPosY) / length;
        }

        private boolean isPressedAtTheFinalMap(int pressedPosY) {
            return pressedPosY >= 200;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void mouseExited(MouseEvent e) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }

}

