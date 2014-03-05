package com.wu.homework;

/**
 * Created by xiangwu on 2/27/14.
 */
public class DepthFirstSearcher extends Searcher {
    private char[][] runtimeMapArray;
    private int searchingDirectionAmount = 0;
    private int searchingLevel;
    private int lengthOfSideOfGrids;
    private boolean isLevelSearchCompleted = false, isSearchCompleted = false;
    private int[] directions = new int[110];

    public DepthFirstSearcher(Layout layout) {
        this.lengthOfSideOfGrids = layout.getLengthOfSideOfGrids();
        int lengthOfSideOfGrids1 = layout.getLengthOfSideOfGrids();
        runtimeMapArray = new char[lengthOfSideOfGrids1][];
        int[][] initialMapArray1 = layout.getValueArray();
        for (int i = 0; i < lengthOfSideOfGrids1; i++) {
            runtimeMapArray[i] = new char[lengthOfSideOfGrids1];
            for (int j = 0; j < lengthOfSideOfGrids1; j++) {
                runtimeMapArray[i][j] = (char) initialMapArray1[i][j];
                if (initialMapArray1[i][j] == 0) {
                    runtimeIndexOfBlank_X = j;
                    runtimeIndexOfBlank_Y = i;
                }
            }
        }
    }

    @Override
    public SearchedPath search(int[][] resultMapArray) {
        System.out.println("Start searching");
        doSearch(resultMapArray);
        if (!doseSearchFindTheResult(resultMapArray)) {
            System.out.println("No Result");
            return null;
        }
        System.out.println("Path length : " + (searchingLevel + 1));
        return getPath();
    }

    private void doSearch(int[][] resultMapArray) {
        searchingLevel = -1;
        statTable[0] = new char[NUMBER_OF_GRIDS];
        for (int i = 0; i < lengthOfSideOfGrids; i++) {
            for (int j = 0; j < lengthOfSideOfGrids; j++) {
                statTable[0][i*lengthOfSideOfGrids + j] = runtimeMapArray[i][j];
            }
        }
        searchingDirectionAmount++;
        while (searchIsNotCompleted()) {
            searchNextLevel(resultMapArray);
        }
    }

    private boolean searchIsNotCompleted() {
        return !isSearchCompleted;
    }

    private void searchNextLevel(int[][] resultMapArray) {
        searchingLevel++;
        isLevelSearchCompleted = false;
        while (currentLevelSearchIsNotCompleted()) {
            changeDirectionInCurrentLevel();
            if (isDirectionValid()) {
                saveMapStat();
                if (doseSearchFindTheResult(resultMapArray)) {
                    isSearchCompleted = true;
                }
                isLevelSearchCompleted = true;
            }
            else {
                if (hasSearchedAllDirectionsInCurrentLevel())
                    goBackToTheLastLevel();
                if (doNotHaveResult()) {
                    isLevelSearchCompleted = true;
                    isSearchCompleted = true;
                }
            }
        }
    }

    private boolean currentLevelSearchIsNotCompleted() {
        return !isLevelSearchCompleted;
    }

    private void changeDirectionInCurrentLevel() {
        directions[searchingLevel]++;
    }

    private boolean doseSearchFindTheResult(int[][] resultMapArray) {
        for (int i = 0; i < lengthOfSideOfGrids; i++) {
            for (int j = 0; j < lengthOfSideOfGrids; j++) {
                if ((char)resultMapArray[i][j] != runtimeMapArray[i][j])
                    return false;
            }
        }
        return true;
    }

    private boolean isDirectionValid() {
        if (hasSearchedAllDirectionsInCurrentLevel())
            return false;
        if (searchLevelIsTooMuch())
            return false;
        int nextX = runtimeIndexOfBlank_X + DX[directions[searchingLevel] - 1];
        int nextY = runtimeIndexOfBlank_Y + DY[directions[searchingLevel] - 1];
        if (isMapIndexOutOfBoundary(nextX, nextY))
            return false;
        if (isStatAlreadyExistent(nextX, nextY)) return false;
        return true;
    }

    private boolean hasSearchedAllDirectionsInCurrentLevel() {
        return directions[searchingLevel] > COUNT_OF_ACTION;
    }

    private boolean searchLevelIsTooMuch() {
        return searchingLevel > MAX_SEARCHING_LEVEL;
    }

    private boolean isMapIndexOutOfBoundary(int indexX, int indexY) {
        if (indexX >= lengthOfSideOfGrids || indexX < 0)
            return true;
        if (indexY >= lengthOfSideOfGrids || indexY < 0)
            return true;
        return false;
    }

    private boolean isStatAlreadyExistent(int indexX, int indexY) {
        char temp;
        temp = runtimeMapArray[runtimeIndexOfBlank_Y][runtimeIndexOfBlank_X];
        runtimeMapArray[runtimeIndexOfBlank_Y][runtimeIndexOfBlank_X] = runtimeMapArray[indexY][indexX];
        runtimeMapArray[indexY][indexX] = temp;
        for (int i = 0; i < searchingDirectionAmount; i++) {
            if (isStatSame(i)) {
                temp = runtimeMapArray[runtimeIndexOfBlank_Y][runtimeIndexOfBlank_X];
                runtimeMapArray[runtimeIndexOfBlank_Y][runtimeIndexOfBlank_X] = runtimeMapArray[indexY][indexX];
                runtimeMapArray[indexY][indexX] = temp;
                return true;
            }
        }
        runtimeIndexOfBlank_X = indexX;
        runtimeIndexOfBlank_Y = indexY;
        return false;
    }

    private boolean isStatSame(int index) {
        for (int i = 0; i < lengthOfSideOfGrids; i++) {
            for (int j = 0; j < lengthOfSideOfGrids; j++) {
                if (statTable[index][i*lengthOfSideOfGrids + j] != runtimeMapArray[i][j])
                    return false;
            }
        }
        return true;
    }

    private void saveMapStat() {
        statTable[searchingDirectionAmount] = new char[NUMBER_OF_GRIDS];
        for (int i = 0; i < lengthOfSideOfGrids; i++) {
            for (int j = 0; j < lengthOfSideOfGrids; j++) {
                statTable[searchingDirectionAmount][i*lengthOfSideOfGrids + j] = runtimeMapArray[i][j];
            }
        }
        searchingDirectionAmount++;
    }

    private void goBackToTheLastLevel() {
        if (searchingLevel <= 0)
        {
            searchingLevel--;
            return;
        }
        int backX = runtimeIndexOfBlank_X - DX[directions[searchingLevel - 1] - 1];
        int backY = runtimeIndexOfBlank_Y - DY[directions[searchingLevel - 1] - 1];
        char temp;
        temp = runtimeMapArray[runtimeIndexOfBlank_Y][runtimeIndexOfBlank_X];
        runtimeMapArray[runtimeIndexOfBlank_Y][runtimeIndexOfBlank_X] = runtimeMapArray[backY][backX];
        runtimeMapArray[backY][backX] = temp;
        runtimeIndexOfBlank_X = backX;
        runtimeIndexOfBlank_Y = backY;
        directions[searchingLevel] = 0;
        searchingLevel--;
    }

    private boolean doNotHaveResult() {
        return searchingLevel < 0;
    }

    private SearchedPath getPath() {
        SearchedPath path = new SearchedPath();
        for (int i = 0; i <= searchingLevel; i++) {
            path.addDirection(directions[i] - 1);
        }
        return path;
    }

    public char[][] getRuntimeMapArray() {
        return runtimeMapArray;
    }

}
