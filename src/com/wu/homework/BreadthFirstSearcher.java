package com.wu.homework;

import java.util.LinkedList;

/**
 * Created by xiangwu on 2/27/14.
 */
public class BreadthFirstSearcher {
    //    上左下右四个方向移动时对x和y坐标的影响
    private final int dx[] = {0, -1, 0, 1};
    private final int dy[] = {-1, 0, 1, 0};
    private final int maxAct = 4;
    private final int TABLE_NUM = 200000;
    private final int NUM = 9;
    private final int LIMIT = 100;
    private boolean allComplete = false;
    private int levelNow, act /*移动的方向*/, actNow /*当前的结点*/, actBefore /*当前节结点的父结点*/;
    private int x, y;
    private int levelFoot[] = new int[LIMIT + 2]; /*每一层最后的结点*/
    private int actHead[] = new int[TABLE_NUM]; /*每个结点的父结点*/
    private char[] allAct = new char[TABLE_NUM]; /*每个结点的移动方向*/
    private char[] actX = new char[TABLE_NUM], actY = new char[TABLE_NUM];
    private char[][] statTable;
    private char[] nextPoint = new char[NUM];
    private SearchProcessListener searchProcessListener;

    public BreadthFirstSearcher(SearchProcessListener searchProcessListener) {
        this.searchProcessListener = searchProcessListener;
        statTable = new char[TABLE_NUM][];
        statTable[0] = new char[NUM];
        for (int i = 0; i < searchProcessListener.getLengthOfSideOfGrids(); i++) {
            for (int j = 0; j < searchProcessListener.getLengthOfSideOfGrids(); j++) {
                int tmp = i * searchProcessListener.getLengthOfSideOfGrids() + j;
                statTable[0][tmp] = (char) searchProcessListener.getPrimaryMapArray()[i][j];
                if (searchProcessListener.getPrimaryMapArray()[i][j] == 0) {
                    x = j;
                    y = i;
                    actX[0] = (char)x;
                    actY[0] = (char)y;
                }
            }
        }
    }

    private boolean isActionOK() {
        x = actX[actBefore] + dx[act - 1];
        y = actY[actBefore] + dy[act - 1];
        if (x >= searchProcessListener.getLengthOfSideOfGrids() || x < 0)
            return false;
        if (y >= searchProcessListener.getLengthOfSideOfGrids() || y < 0)
            return false;
        for (int i = 0; i < NUM; i++) {
            nextPoint[i] = statTable[actBefore][i];
        }
        int tmp = actY[actBefore] * searchProcessListener.getLengthOfSideOfGrids() + actX[actBefore];
        char m = nextPoint[tmp];
        nextPoint[tmp] = nextPoint[y * searchProcessListener.getLengthOfSideOfGrids() + x];
        nextPoint[y * searchProcessListener.getLengthOfSideOfGrids() + x] = m;
        boolean isExist;
        for (int i = 0; i <= actNow; i++) {
            isExist = true;
            for (int j = 0; j < NUM - 1; j++) {
                if (statTable[i][j] != nextPoint[j]) {
                    isExist = false;
                    break;
                }
            }
            if (isExist)
                return false;
        }
        return true;
    }

    private boolean isSearchFinished() {
        int tmp;
        for (int i = 0; i < searchProcessListener.getLengthOfSideOfGrids(); i++) {
            for (int j = 0; j < searchProcessListener.getLengthOfSideOfGrids(); j++) {
                tmp = i * searchProcessListener.getLengthOfSideOfGrids() + j;
                if (nextPoint[tmp] != searchProcessListener.getFinalMapArray()[i][j])
                    return false;
            }
        }
        return true;
    }

    public void doSearch() {
        System.out.println("Start searching");
        levelNow = 1;
        levelFoot[1] = 0;
        levelFoot[0] = -1;
        actNow = 0;
        while (!allComplete) {
            levelNow++;
            levelFoot[levelNow] = levelFoot[levelNow - 1];
            for (actBefore = levelFoot[levelNow - 2] + 1; actBefore <= levelFoot[levelNow - 1]; actBefore++) {
                for (act = 1; act <= maxAct; act++) {
                    if (isActionOK() && !allComplete) {
                        levelFoot[levelNow]++;
                        actNow = levelFoot[levelNow];
                        actHead[actNow] = actBefore;
                        allAct[actNow] = (char)act;
                        actX[actNow] = (char)x;
                        actY[actNow] = (char)y;
                        statTable[actNow] = new char[NUM];
                        for (int i = 0; i < NUM; i++) {
                            statTable[actNow][i] = nextPoint[i];
                        }
                        if (isSearchFinished())
                            allComplete = true;
                    }
                }
            }
            if (levelNow > LIMIT) {
                searchProcessListener.setSearchResult(false);
                if (actBefore > levelFoot[levelNow])
                    System.out.println("NOT result");
                break;
            }
        }
        System.out.println("Search Over : " + (actNow + 1) + "  " + levelNow);
        if (levelNow > LIMIT)
            return;
        LinkedList<CharObject> stackList = new LinkedList<CharObject>();
        int tmpAct = actNow;
        while (tmpAct != 0) {
            stackList.addLast(new CharObject(allAct[tmpAct]));
            tmpAct = actHead[tmpAct];
        }
        int startX = actX[0], startY = actY[0];
        int nextX, nextY;
        int action;
        System.out.println("step : " + stackList.size());
        while (!stackList.isEmpty()){
            action = stackList.removeLast().getCh();
            nextX = startX + dx[action - 1];
            nextY = startY + dy[action - 1];
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
    }
}
