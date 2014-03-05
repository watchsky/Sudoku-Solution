package com.wu.homework;

import java.util.ArrayList;

/**
 * Created by xiangwu on 2/27/14.
 */
public class SearchedPath {
    private static final String[] DIRECTIONS = {"up", "left", "down", "right"};
    private ArrayList<Integer> path;

    public SearchedPath() {
        path = new ArrayList<Integer>();
    }

    public void addDirection(int direction) {
        path.add(direction);
    }

    public void printDirection() {
        System.out.println(getDirectionString());
    }

    private String getDirectionString() {
        String pathString = "Search Result :\n";
        if (path.size() == 0) {
            pathString += "No Result";
            return pathString;
        }
        for (Integer dir : path) {
            pathString += DIRECTIONS[dir];
            pathString += ",";
        }
        return pathString;
    }

    public ArrayList<Integer> getPath() {
        return path;
    }

    public int getLength() {
        return path.size();

    }
}
