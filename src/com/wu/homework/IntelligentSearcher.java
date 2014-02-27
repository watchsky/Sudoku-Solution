package com.wu.homework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by xiangwu on 2/27/14.
 */
public class IntelligentSearcher {
    //    上左下右四个方向移动时对x和y坐标的影响
    private final int dx[] = {0, -1, 0, 1};
    private final int dy[] = {-1, 0, 1, 0};
    private final int maxAct = 4;
    private final int TABLE_NUM = 200000;
    private final int NUM = 9;
    private int act /*移动的方向*/, currentNodeIndex /*当前的结点*/;
    private Node fatherNode;
    private int x, y;
    private char[] actX = new char[TABLE_NUM], actY = new char[TABLE_NUM];
    private char[][] statTable;
    private char[] nextPoint = new char[NUM];
    private OpenList openList;
    private HashMap<String, Node> closedList;
    private int lengthOfSideOfGrids;
    private SearchProcessListener searchProcessListener;

    class OpenList extends ArrayList<Node> {
        public Node pollMinFNode() {
            int minF = 100;
            Node minNode = null;
            for (Node e : this) {
                if (e.f < minF) {
                    minF = e.f;
                    minNode = e;
                }
            }
            remove(minNode);
            return minNode;
        }
    }

    class Node {
        final int f, g;
        final int selfIndex;
        final char act;
        final Node parent;

        Node(int selfIndex, char act, Node parent) {
            this.g = (parent != null) ? parent.g + 1 : 0;
            this.f = this.g + calculateH(selfIndex);
            this.selfIndex = selfIndex;
            this.act = act;
            this.parent = parent;
        }

        @Override
        public int hashCode() {
            return selfIndex;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Node))
                return false;
            if (obj == this)
                return true;
            return this.selfIndex == ((Node)obj).selfIndex;
        }
    }

    public IntelligentSearcher(SearchProcessListener searchProcessListener) {
        this.searchProcessListener = searchProcessListener;
        lengthOfSideOfGrids = searchProcessListener.getLengthOfSideOfGrids();
        statTable = new char[TABLE_NUM][];
        statTable[0] = new char[NUM];
        for (int i = 0; i < lengthOfSideOfGrids; i++) {
            for (int j = 0; j < lengthOfSideOfGrids; j++) {
                int tmp = i * lengthOfSideOfGrids + j;
                statTable[0][tmp] = (char) searchProcessListener.getPrimaryMapArray()[i][j];
                if (searchProcessListener.getPrimaryMapArray()[i][j] == 0) {
                    x = j;
                    y = i;
                    actX[0] = (char)x;
                    actY[0] = (char)y;
                }
            }
        }
        openList = new OpenList();
        closedList = new HashMap<String, Node>();
    }

    private int calculateH(int index) {
        int sum = 0;
        int targetX = 0, targetY = 0;
        boolean isFound;
        for (int i = 0; i < lengthOfSideOfGrids; i++) {
            for (int j = 0; j < lengthOfSideOfGrids; j++) {
                int ind = i * lengthOfSideOfGrids + j;
                int tmp = statTable[index][ind];
                if (tmp == 0)
                    continue;
                isFound = false;
                for (int k = 0; k < lengthOfSideOfGrids; k++) {
                    for (int l = 0; l < lengthOfSideOfGrids; l++) {
                        if (tmp == searchProcessListener.getFinalMapArray()[k][l]) {
                            targetX = l;
                            targetY = k;
                            isFound = true;
                            break;
                        }
                    }
                    if (isFound)
                        break;
                }
                sum += Math.abs(targetX - j);
                sum += Math.abs(targetY - i);
            }
        }
        return sum;
    }

    private boolean isSearchFinished(int index) {
        int tmp;
        for (int i = 0; i < lengthOfSideOfGrids; i++) {
            for (int j = 0; j < lengthOfSideOfGrids; j++) {
                tmp = i * lengthOfSideOfGrids + j;
                if (statTable[index][tmp] != searchProcessListener.getFinalMapArray()[i][j])
                    return false;
            }
        }
        return true;
    }

    private boolean isActionOK() {
        x = actX[fatherNode.selfIndex] + dx[act - 1];
        y = actY[fatherNode.selfIndex] + dy[act - 1];
        if (x >= lengthOfSideOfGrids || x < 0)
            return false;
        if (y >= lengthOfSideOfGrids || y < 0)
            return false;
        for (int i = 0; i < NUM; i++) {
            nextPoint[i] = statTable[fatherNode.selfIndex][i];
        }
        int tmp = actY[fatherNode.selfIndex] * lengthOfSideOfGrids + actX[fatherNode.selfIndex];
        char m = nextPoint[tmp];
        nextPoint[tmp] = nextPoint[y * lengthOfSideOfGrids + x];
        nextPoint[y * lengthOfSideOfGrids + x] = m;
        boolean isExist;
        for (int i = 0; i <= currentNodeIndex; i++) {
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

    public void doSearch() {
        System.out.println("Start Searching. And waiting...");
        currentNodeIndex = 0;
        Node rootNode = new Node(currentNodeIndex, (char)0, null);
        openList.add(rootNode);
        while (true) {
            fatherNode = openList.pollMinFNode();
            if (fatherNode == null) {
                searchProcessListener.setSearchResult(false);
                break;
            }
            if (isSearchFinished(fatherNode.selfIndex)) {
                break;
            }
            closedList.put(String.valueOf(fatherNode.selfIndex), fatherNode);
            for (act = 1; act <= maxAct; act++) {
                if (isActionOK()) {
                    currentNodeIndex++;
                    actX[currentNodeIndex] = (char)x;
                    actY[currentNodeIndex] = (char)y;
                    statTable[currentNodeIndex] = new char[NUM];
                    for (int i = 0; i < NUM; i++) {
                        statTable[currentNodeIndex][i] = nextPoint[i];
                    }
                    openList.add(new Node(currentNodeIndex, (char)act, fatherNode));
                }
            }
        }
        LinkedList<CharObject> stackList = new LinkedList<CharObject>();
        int tmpAct = fatherNode.selfIndex;
        while (tmpAct != 0) {
            stackList.addLast(new CharObject(fatherNode.act));
            fatherNode = fatherNode.parent;
            tmpAct = fatherNode.selfIndex;
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
        openList.clear();
        closedList.clear();
    }
}
