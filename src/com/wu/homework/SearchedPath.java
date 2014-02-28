package com.wu.homework;

import java.util.ArrayList;

/**
 * Created by xiangwu on 2/27/14.
 */
public class SearchedPath {
    private ArrayList<DirectionEnum> path;

    public SearchedPath() {
        path = new ArrayList<DirectionEnum>();
    }

    public SearchedPath(ArrayList<Integer> directionList) {
        this();
        if (directionList.size() == 0) return;
        for (Integer dir : directionList) {
            addDirection(dir);
        }
    }

    public void addDirection(int direction) {
        switch (direction) {
            case 0:
                path.add(DirectionEnum.UP);
                break;
            case 1:
                path.add(DirectionEnum.LEFT);
                break;
            case 2:
                path.add(DirectionEnum.DOWN);
                break;
            case 3:
                path.add(DirectionEnum.RIGHT);
                break;
        }
    }

    public void printDirection() {
        System.out.println(getDirectionString());
    }

    private String getDirectionString() {
        String pathString = "Search Result :\n";
        if (getPath().size() == 0) {
            pathString += "No Result";
            return pathString;
        }
        for (DirectionEnum dir : getPath()) {
            pathString += dir.getDirection();
            pathString += ",";
        }
        return pathString;
    }

    public ArrayList<DirectionEnum> getPath() {
        return path;
    }
}
