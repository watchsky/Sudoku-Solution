package com.wu.homework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by xiangwu on 2/27/14.
 */
public class IntelligentSearcher extends Searcher{
    private int runtimeNodeIndex;
    private int lengthOfSideOfGrids;
    private Node fatherNode;
    private char[] nodeIndex_X = new char[MAX_NUMBER_OF_STAT_TABLE];
    private char[] nodeIndex_Y = new char[MAX_NUMBER_OF_STAT_TABLE];
    private WaitingSearchList waitingSearchList;
    private HashMap<String, Node> finishedSearchList;

    public IntelligentSearcher(int[][] initialMapArray, int lengthOfSideOfGrids) {
        this.lengthOfSideOfGrids = lengthOfSideOfGrids;
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
        waitingSearchList = new WaitingSearchList();
        finishedSearchList = new HashMap<String, Node>();
    }

    @Override
    public SearchedPath search(int[][] resultMapArray) {
        System.out.println("Start searching");
        doSearch(resultMapArray);
        if (fatherNode == null) {
            System.out.println("No Result");
            return null;
        }
        SearchedPath path = getPath();
        System.out.println("Path length : " + path.getPath().size());
        waitingSearchList.clear();
        finishedSearchList.clear();
        return path;
    }

    private void doSearch(int[][] resultMapArray) {
        int direction;
        runtimeNodeIndex = 0;
        Node rootNode = new Node(runtimeNodeIndex, (char)-1, null, resultMapArray);
        waitingSearchList.add(rootNode);
        while (true) {
            fatherNode = waitingSearchList.pollNodeWithMinimumEstimatedValue();
            if (fatherNode == null) {
                break;
            }
            if (doseSearchFindTheResult(resultMapArray, fatherNode.index)) {
                break;
            }
            finishedSearchList.put(String.valueOf(fatherNode.index), fatherNode);
            for (direction = 0; direction < COUNT_OF_ACTION; direction++) {
                if (isDirectionValid(direction)) {
                    runtimeNodeIndex++;
                    nodeIndex_X[runtimeNodeIndex] = (char) runtimeIndexOfBlank_X;
                    nodeIndex_Y[runtimeNodeIndex] = (char) runtimeIndexOfBlank_Y;
                    saveMapStat();
                    waitingSearchList.add(new Node(runtimeNodeIndex, (char) direction, fatherNode, resultMapArray));
                }
            }
        }
    }

    private boolean doseSearchFindTheResult(int[][] resultMapArray, int index) {
        int tmp;
        for (int i = 0; i < lengthOfSideOfGrids; i++) {
            for (int j = 0; j < lengthOfSideOfGrids; j++) {
                tmp = i * lengthOfSideOfGrids + j;
                if (statTable[index][tmp] != resultMapArray[i][j])
                    return false;
            }
        }
        return true;
    }

    private boolean isDirectionValid(int direction) {
        runtimeIndexOfBlank_X = nodeIndex_X[fatherNode.index] + DX[direction];
        runtimeIndexOfBlank_Y = nodeIndex_Y[fatherNode.index] + DY[direction];
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
        for (int i = 0; i <= runtimeNodeIndex; i++) {
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
            nextStepStat[i] = statTable[fatherNode.index][i];
        }
        int index = nodeIndex_Y[fatherNode.index] * lengthOfSideOfGrids + nodeIndex_X[fatherNode.index];
        char m = nextStepStat[index];
        nextStepStat[index] = nextStepStat[runtimeIndexOfBlank_Y * lengthOfSideOfGrids + runtimeIndexOfBlank_X];
        nextStepStat[runtimeIndexOfBlank_Y * lengthOfSideOfGrids + runtimeIndexOfBlank_X] = m;
        return nextStepStat;
    }

    private void saveMapStat() {
        char[] nextStepStat = calculateNextStepStat();
        statTable[runtimeNodeIndex] = new char[NUMBER_OF_GRIDS];
        for (int i = 0; i < NUMBER_OF_GRIDS; i++) {
            statTable[runtimeNodeIndex][i] = nextStepStat[i];
        }
    }

    private SearchedPath getPath() {
        SearchedPath path = new SearchedPath();
        LinkedList<DirectionChar> directionList = new LinkedList<DirectionChar>();
        int index = fatherNode.index;
        while (index != 0) {
            directionList.addLast(new DirectionChar(fatherNode.direction));
            fatherNode = fatherNode.parent;
            index = fatherNode.index;
        }
        while (!directionList.isEmpty()) {
            path.addDirection(directionList.removeLast().getDirection());
        }
        return path;
    }

    class WaitingSearchList extends ArrayList<Node> {

        public Node pollNodeWithMinimumEstimatedValue() {
            int minimumValue = 1000;
            Node nodeWithMinimumEstimatedValue = null;
            for (Node node : this) {
                if (node.estimatedValue < minimumValue) {
                    minimumValue = node.estimatedValue;
                    nodeWithMinimumEstimatedValue = node;
                }
            }
            remove(nodeWithMinimumEstimatedValue);
            return nodeWithMinimumEstimatedValue;
        }
    }
    class Node {

        final int index;
        final char direction;
        final Node parent;
        final int estimatedValue;
        final int currentValue;

        Node(int index, char direction, Node parent, int[][] resultMapArray) {
            this.index = index;
            this.direction = direction;
            this.parent = parent;
            this.currentValue = (parent != null) ? parent.currentValue + 1 : 0;
            this.estimatedValue = this.currentValue + calculateCost(index, resultMapArray);
        }

        private int calculateCost(int index, int[][] resultMapArray) {
            int cost = 0;
            int targetX = 0, targetY = 0;
            boolean findGridWithSameNumber;
            for (int y = 0; y < lengthOfSideOfGrids; y++) {
                for (int x = 0; x < lengthOfSideOfGrids; x++) {
                    int gridNumber = statTable[index][y*lengthOfSideOfGrids + x];
                    if (gridNumber == 0)
                        continue;
                    findGridWithSameNumber = false;
                    for (int i = 0; i < lengthOfSideOfGrids; i++) {
                        for (int j = 0; j < lengthOfSideOfGrids; j++) {
                            if (gridNumber == resultMapArray[i][j]) {
                                targetX = j;
                                targetY = i;
                                findGridWithSameNumber = true;
                                break;
                            }
                        }
                        if (findGridWithSameNumber)
                            break;
                    }
                    cost += Math.abs(targetX - x);
                    cost += Math.abs(targetY - y);
                }
            }
            return cost;
        }

        @Override
        public int hashCode() {
            return index;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Node))
                return false;
            if (obj == this)
                return true;
            return this.index == ((Node)obj).index;
        }
    }
}
