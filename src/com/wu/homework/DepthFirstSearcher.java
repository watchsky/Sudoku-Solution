package com.wu.homework;

/**
 * Created by xiangwu on 2/27/14.
 */
public class DepthFirstSearcher {
    //    上左下右四个方向移动时对x和y坐标的影响
    private final int DX[] = {0, -1, 0, 1};
    private final int DY[] = {-1, 0, 1, 0};
    private final int maxAct = 4; //移动方向总数
    private char[] grid_0_0 = new char[200000];
    private char[] grid_0_1 = new char[200000];
    private char[] grid_0_2 = new char[200000];
    private char[] grid_1_0 = new char[200000];
    private char[] grid_1_2 = new char[200000];
    private char[] grid_2_0 = new char[200000];
    private char[] grid_2_1 = new char[200000];
    private char[] grid_2_2 = new char[200000];
    private int count = 0;
    private int level = -1;
    private boolean levelComplete = false, allComplete = false;
    private char[] act = new char[110];
    private int x, y;
    private int startX, startY;
    private int[][] runtimeMapArray;    //搜索时用来表示当前的状态
    private SearchProcessListener searchProcessListener;

    public DepthFirstSearcher(SearchProcessListener searchProcessListener) {
        this.searchProcessListener = searchProcessListener;
        runtimeMapArray = new int[searchProcessListener.getLengthOfSideOfGrids()][];
        for (int i = 0; i < searchProcessListener.getLengthOfSideOfGrids(); i++) {
            runtimeMapArray[i] = new int[searchProcessListener.getLengthOfSideOfGrids()];
            for (int j = 0; j < searchProcessListener.getLengthOfSideOfGrids(); j++) {
                runtimeMapArray[i][j] = searchProcessListener.getPrimaryMapArray()[i][j];
                if (searchProcessListener.getPrimaryMapArray()[i][j] == 0) {
                    startX = x = j;
                    startY = y = i;
                }
            }
        }
    }

    private boolean isSearchFinished() {
        for (int i = 0; i < searchProcessListener.getLengthOfSideOfGrids(); i++) {
            for (int j = 0; j < searchProcessListener.getLengthOfSideOfGrids(); j++) {
                if (searchProcessListener.getFinalMapArray()[i][j] != runtimeMapArray[i][j])
                    return false;
            }
        }
        return true;
    }

    private boolean isActionOK() {
        if (act[level] > maxAct)
            return false;
        if (level > 30)
            return false;
        int nextX = x + DX[act[level] - 1];
        int nextY = y + DY[act[level] - 1];
        if (nextX >= searchProcessListener.getLengthOfSideOfGrids() || nextX < 0)
            return false;
        if (nextY >= searchProcessListener.getLengthOfSideOfGrids() || nextY < 0)
            return false;
        int tmp;
        tmp = runtimeMapArray[y][x];
        runtimeMapArray[y][x] = runtimeMapArray[nextY][nextX];
        runtimeMapArray[nextY][nextX] = tmp;
        for (int i = 0; i < count; i++) {
            if ((grid_0_0[i] == (char) runtimeMapArray[0][0]) && (grid_0_1[i] == (char) runtimeMapArray[0][1]) && (grid_0_2[i] == (char) runtimeMapArray[0][2]) &&
                    (grid_1_0[i] == (char) runtimeMapArray[1][0]) && (grid_1_2[i] == (char) runtimeMapArray[1][2]) &&
                    (grid_2_0[i] == (char) runtimeMapArray[2][0]) && (grid_2_1[i] == (char) runtimeMapArray[2][1]) && (grid_2_2[i] == (char) runtimeMapArray[2][2])) {
                tmp = runtimeMapArray[y][x];
                runtimeMapArray[y][x] = runtimeMapArray[nextY][nextX];
                runtimeMapArray[nextY][nextX] = tmp;
                return false;
            }
        }
        x = nextX;
        y = nextY;
        grid_0_0[count] = (char) runtimeMapArray[0][0];
        grid_0_1[count] = (char) runtimeMapArray[0][1];
        grid_0_2[count] = (char) runtimeMapArray[0][2];
        grid_1_0[count] = (char) runtimeMapArray[1][0];
        grid_1_2[count] = (char) runtimeMapArray[1][2];
        grid_2_0[count] = (char) runtimeMapArray[2][0];
        grid_2_1[count] = (char) runtimeMapArray[2][1];
        grid_2_2[count] = (char) runtimeMapArray[2][2];
        count++;
        return true;
    }

    private void back() {
        if (level <= 0)
        {
            level--;
            return;
        }
        int backX = x - DX[act[level - 1] - 1];
        int backY = y - DY[act[level - 1] - 1];
        int tmp;
        tmp = runtimeMapArray[y][x];
        runtimeMapArray[y][x] = runtimeMapArray[backY][backX];
        runtimeMapArray[backY][backX] = tmp;
        x = backX;
        y = backY;
        act[level] = 0;
        level--;
    }

    public int[][] doSearch() {
        System.out.println("Start searching");
        grid_0_0[count] = (char) runtimeMapArray[0][0];
        grid_0_1[count] = (char) runtimeMapArray[0][1];
        grid_0_2[count] = (char) runtimeMapArray[0][2];
        grid_1_0[count] = (char) runtimeMapArray[1][0];
        grid_1_2[count] = (char) runtimeMapArray[1][2];
        grid_2_0[count] = (char) runtimeMapArray[2][0];
        grid_2_1[count] = (char) runtimeMapArray[2][1];
        grid_2_2[count] = (char) runtimeMapArray[2][2];
        count++;
        while (!allComplete) {
            level++;
            levelComplete = false;
            while (!levelComplete) {
                act[level]++;
                if (isActionOK()) {
                    if (isSearchFinished()) {
                        allComplete = true;
                    }
                    levelComplete = true;
                }
                else {
                    if (act[level] > maxAct)
                        back();
                    if (level < 0) {
                        levelComplete = true;
                        allComplete = true;
                        searchProcessListener.setSearchResult(false);
                    }
                }
            }
        }
        System.out.println("Searching Over : " + (level+1));
        System.out.println("hash table : " + count);
        int nextX, nextY;
        for (int i = 0; i <= level; i++) {
            nextX = startX + DX[act[i] - 1];
            nextY = startY + DY[act[i] - 1];
            searchProcessListener.getPrimaryMap().exchange(startY, startX, nextY, nextX);
            try {
                Thread.sleep(500);
            }catch(InterruptedException e) {
                e.printStackTrace();
            }
            startX = nextX;
            startY = nextY;
        }
        searchProcessListener.onSearchFinished();
        return runtimeMapArray;
    }

    public int[][] getRuntimeMapArray() {
        return runtimeMapArray;
    }

}
