package com.wu.homework;

/**
 * Created by xiangwu on 2/27/14.
 */
public enum DirectionEnum {
    UP("upwards"), LEFT("leftwards"), DOWN("downwards"), RIGHT("rightwards");

    private String direction;

    private DirectionEnum(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

}
