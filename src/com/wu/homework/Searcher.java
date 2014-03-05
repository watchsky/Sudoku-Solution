package com.wu.homework;

import java.util.ArrayList;

/**
 * Created by xiangwu on 3/3/14.
 */
public abstract class Searcher implements Direction {

    protected static final int MAX_NUMBER_OF_STAT_TABLE = 200000;
    protected static final int NUMBER_OF_GRIDS = 9;
    protected static final int MAX_SEARCHING_LEVEL = 30;
    protected int runtimeIndexOfBlank_X;
    protected int runtimeIndexOfBlank_Y;
    protected char[][] statTable = new char[MAX_NUMBER_OF_STAT_TABLE][];

    public abstract SearchedPath search(int[][] resultMapArray);
}