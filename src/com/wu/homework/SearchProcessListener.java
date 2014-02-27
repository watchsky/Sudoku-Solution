package com.wu.homework;

/**
 * Created by xiangwu on 2/27/14.
 */
public interface SearchProcessListener {
    public int getLengthOfSideOfGrids();
    public int[][] getPrimaryMapArray();
    public int[][] getFinalMapArray();
    public Sudoku getPrimaryMap();
    public void setSearchResult(boolean result);
    public void onSearchFinished();
}
