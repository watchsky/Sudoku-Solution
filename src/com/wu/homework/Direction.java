package com.wu.homework;

/**
 * Created by xiangwu on 3/3/14.
 */
public interface Direction {
    static final int COUNT_OF_ACTION = 4;
    //    上左下右四个方向移动时对x和y坐标的影响
    static final int[] DX = {0, -1, 0, 1};
    static final int[] DY = {-1, 0, 1, 0};
}
