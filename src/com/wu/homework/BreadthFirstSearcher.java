package com.wu.homework;

import java.util.LinkedList;

/**
 * Created by xiangwu on 2/27/14.
 */
public class BreadthFirstSearcher extends Searcher{
    private boolean isSearchCompleted = false;
    private int searchingLevel, childNode, parentNode = 0;
    private int lastNodeIndexInEveryLevel[] = new int[MAX_SEARCHING_LEVEL + 2];
    private int parentNodeOf[] = new int[MAX_NUMBER_OF_STAT_TABLE];
    private char[] directionOf = new char[MAX_NUMBER_OF_STAT_TABLE];
    private char[] nodeIndex_X = new char[MAX_NUMBER_OF_STAT_TABLE], nodeIndex_Y = new char[MAX_NUMBER_OF_STAT_TABLE];
    private int lengthOfSideOfGrids;

    public BreadthFirstSearcher(int[][] initialMapArray, int lengthOfSideOfGrids) {
        this.lengthOfSideOfGrids = lengthOfSideOfGrids;
        initStatTable(initialMapArray, lengthOfSideOfGrids);
    }

    private void initStatTable(int[][] initialMapArray, int lengthOfSideOfGrids) {
        statTable[0] = new char[NUMBER_OF_GRIDS];
        for (int i = 0; i < lengthOfSideOfGrids; i++) {
            for (int j = 0; j < lengthOfSideOfGrids; j++) {
                statTable[0][i*lengthOfSideOfGrids + j] = (char) initialMapArray[i][j];
                if (initialMapArray[i][j] == 0) {
                    runtimeIndexOfBlank_X = j;
                    runtimeIndexOfBlank_Y = i;
                    nodeIndex_X[0] = (char) runtimeIndexOfBlank_X;
                    nodeIndex_Y[0] = (char) runtimeIndexOfBlank_Y;
                }
            }
        }
    }

    @Override
    public SearchedPath search(int[][] resultMapArray) {
        System.out.println("Start searching");
        doSearch(resultMapArray);
        if (searchingLevel > MAX_SEARCHING_LEVEL) {
            System.out.println("No Result");
            return null;
        }
        System.out.println("Path length : " + (searchingLevel==0 ? 0 : searchingLevel-1));
        return getPath();
    }

    private void doSearch(int[][] resultMapArray) {
        if (doseSearchFindTheResult(resultMapArray))
            return;
        searchingLevel = 1;
        lastNodeIndexInEveryLevel[1] = 0;
        lastNodeIndexInEveryLevel[0] = -1;
        childNode = 0;
        while (searchIsNotCompleted()) {
            doBreadthFirstSearch(resultMapArray);
            if (searchingLevel > MAX_SEARCHING_LEVEL) {
                break;
            }
        }
    }

    private boolean searchIsNotCompleted() {
        return !isSearchCompleted;
    }

    private void doBreadthFirstSearch(int[][] resultMapArray) {
        int direction;
        searchingLevel++;
        lastNodeIndexInEveryLevel[searchingLevel] = lastNodeIndexInEveryLevel[searchingLevel - 1];
        for (parentNode = lastNodeIndexInEveryLevel[searchingLevel - 2] + 1; parentNode <= lastNodeIndexInEveryLevel[searchingLevel - 1]; parentNode++) {
            for (direction = 0; direction < COUNT_OF_ACTION; direction++) {
                if (isDirectionValid(direction) && searchIsNotCompleted()) {
                    lastNodeIndexInEveryLevel[searchingLevel]++;
                    childNode = lastNodeIndexInEveryLevel[searchingLevel];
                    parentNodeOf[childNode] = parentNode;
                    directionOf[childNode] = (char) direction;
                    nodeIndex_X[childNode] = (char) runtimeIndexOfBlank_X;
                    nodeIndex_Y[childNode] = (char) runtimeIndexOfBlank_Y;
                    saveMapStat();
                    if (doseSearchFindTheResult(resultMapArray))
                        isSearchCompleted = true;
                }
            }
        }
    }

    private boolean isDirectionValid(int direction) {
        runtimeIndexOfBlank_X = nodeIndex_X[parentNode] + DX[direction];
        runtimeIndexOfBlank_Y = nodeIndex_Y[parentNode] + DY[direction];
        if (isMapIndexOutOfBoundary(runtimeIndexOfBlank_X, runtimeIndexOfBlank_Y))
            return false;
        if (isStatAlreadyExistent()) return false;
        return true;
    }

    private boolean isMapIndexOutOfBoundary(int indexX, int indexY) {
        if (indexX >= lengthOfSideOfGrids || indexX < 0)
            return true;
        if (indexY >= lengthOfSideOfGrids || indexY < 0)
            return true;
        return false;
    }

    private boolean isStatAlreadyExistent() {
        char[] nextStepStat = calculateNextStepStat();
        boolean isExist;
        for (int i = 0; i <= childNode; i++) {
            isExist = true;
            for (int j = 0; j < NUMBER_OF_GRIDS - 1; j++) {
                if (statTable[i][j] != nextStepStat[j]) {
                    isExist = false;
                    break;
                }
            }
            if (isExist)
                return true;
        }
        return false;
    }

    private char[] calculateNextStepStat() {
        char[] nextStepStat = new char[NUMBER_OF_GRIDS];
        for (int i = 0; i < NUMBER_OF_GRIDS; i++) {
            nextStepStat[i] = statTable[parentNode][i];
        }
        int index = nodeIndex_Y[parentNode] * lengthOfSideOfGrids + nodeIndex_X[parentNode];
        char m = nextStepStat[index];
        nextStepStat[index] = nextStepStat[runtimeIndexOfBlank_Y * lengthOfSideOfGrids + runtimeIndexOfBlank_X];
        nextStepStat[runtimeIndexOfBlank_Y * lengthOfSideOfGrids + runtimeIndexOfBlank_X] = m;
        return nextStepStat;
    }

    private void saveMapStat() {
        char[] nextStepStat = calculateNextStepStat();
        statTable[childNode] = new char[NUMBER_OF_GRIDS];
        for (int i = 0; i < NUMBER_OF_GRIDS; i++) {
            statTable[childNode][i] = nextStepStat[i];
        }
    }

    private boolean doseSearchFindTheResult(int[][] resultMapArray) {
        char[] nextStepStat = calculateNextStepStat();
        int index;
        for (int i = 0; i < lengthOfSideOfGrids; i++) {
            for (int j = 0; j < lengthOfSideOfGrids; j++) {
                index = i * lengthOfSideOfGrids + j;
                if (nextStepStat[index] != resultMapArray[i][j])
                    return false;
            }
        }
        return true;
    }

    private SearchedPath getPath() {
        SearchedPath path = new SearchedPath();
        LinkedList<DirectionChar> directionList = new LinkedList<DirectionChar>();
        int parent = childNode;
        while (parent != 0) {
            directionList.addLast(new DirectionChar(directionOf[parent]));
            parent = parentNodeOf[parent];
        }
        while (!directionList.isEmpty()) {
            path.addDirection(directionList.removeLast().getDirection());
        }
        return path;
    }

}
