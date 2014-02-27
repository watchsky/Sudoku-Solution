package com.wu.homework;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-12
 * Time: 下午8:52
 * To change this template use File | Settings | File Templates.
 */
public class MainFrame extends JFrame {
    private MainPanel mainPanel;
    private ButtonPanel buttonPanel;

    public MainFrame(String title) throws HeadlessException {
        super(title);
        createMainPanel();
        createButtonPanel();
        addPanelsToContainer();
        this.setSize(600, 500);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void createMainPanel() {
        mainPanel = new MainPanel();
        mainPanel.setSize(200, 200);
    }

    private void createButtonPanel() {
        buttonPanel = new ButtonPanel(mainPanel);
        buttonPanel.setSize(150, 200);
    }

    private void addPanelsToContainer() {
        Container container = this.getContentPane();
        container.add(mainPanel, BorderLayout.CENTER);
        container.add(buttonPanel, BorderLayout.EAST);
    }

    public static void main(String args[]) {
        MainFrame frame = new MainFrame("九宫格搜索");
    }

}
