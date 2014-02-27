package com.wu.homework;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by xiangwu on 2/27/14.
 */
public class DepthFirstSearcherTest {

    @Test
    public void testDoRightSearch() throws Exception {
        MySearchProcessListener searchProcessListener = new MySearchProcessListener();
        DepthFirstSearcher depthFirstSearcher = new DepthFirstSearcher(searchProcessListener);

        int[][] runtimeMapArray = depthFirstSearcher.doSearch();

        int[][] finalMapArray = searchProcessListener.getFinalMapArray();
        int lengthOfSideOfGrids = searchProcessListener.getLengthOfSideOfGrids();
        for (int i = 0; i < lengthOfSideOfGrids; i++) {
            for (int j = 0; j < lengthOfSideOfGrids; j++) {
                assertEquals(runtimeMapArray[i][j], finalMapArray[i][j]);
            }
        }
    }

    private static class MySearchProcessListener implements SearchProcessListener {
        private int[][] finalMapArray = {{1, 2, 3}, {8, 0, 4}, {7, 6, 5}};
        private int[][] primaryMapArray = {{2, 8, 3}, {1, 6, 4}, {7, 0, 5}};
        private Sudoku primaryMap;
        private int startPosX = 50, startPosY = 50;
        private int length = 50;


        public MySearchProcessListener() {
            primaryMap = new Sudoku(this, startPosX, startPosY, length, primaryMapArray);
        }

        @Override
        public int getLengthOfSideOfGrids() {
            return Sudoku.LENGTH_OF_SIDE_OF_GRIDS;
        }

        @Override
        public int[][] getPrimaryMapArray() {
            return primaryMapArray;
        }

        @Override
        public int[][] getFinalMapArray() {
            return finalMapArray;
        }

        @Override
        public Sudoku getPrimaryMap() {
            return primaryMap;
        }

        @Override
        public void setSearchResult(boolean result) {

        }

        @Override
        public void onSearchFinished() {

        }
    }
}
