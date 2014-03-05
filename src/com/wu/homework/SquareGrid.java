package com.wu.homework;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-12
 * Time: 下午2:06
 * To change this template use File | Settings | File Templates.
 */
public class SquareGrid {
    private static final int NUMBER_OF_BLANK_SPACE = 0;
    private int leftTopX, leftTopY;
    private int length;
    private int num;

    public SquareGrid(int leftTopX, int leftTopY, int length, int num) {
        this.leftTopX = leftTopX;
        this.leftTopY = leftTopY;
        this.length = length;
        this.num = num;
    }

    public void draw(Graphics2D g) {
        drawBorder(g);
        drawBackground(g);
        drawNumber(g);
    }

    private void drawBorder(Graphics2D g) {
        g.setPaint(Color.BLACK);
        g.drawRect(leftTopX, leftTopY, length, length);
    }

    private void drawBackground(Graphics2D g) {
        setPaint(g);
        g.fillRect(leftTopX, leftTopY, length, length);
    }

    private void setPaint(Graphics2D g) {
        if (num != NUMBER_OF_BLANK_SPACE)
            g.setPaint(Color.RED);
        else
            g.setPaint(Color.WHITE);
    }

    private void drawNumber(Graphics2D g) {
        if (num == NUMBER_OF_BLANK_SPACE)
            return ;
        g.setPaint(Color.BLACK);
        g.drawString(String.valueOf(num), leftTopX + length / 2, leftTopY + length / 2);
    }

    public void upWards() {
        leftTopY -= length;
    }

    public void downWards() {
        leftTopY += length;
    }

    public void rightWards() {
        leftTopX += length;
    }

    public void leftWards() {
        leftTopX -= length;
    }

}
