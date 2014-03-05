package com.wu.homework;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-12
 * Time: 下午8:35
 * To change this template use File | Settings | File Templates.
 */
public class ButtonPanel extends JPanel {
    private static final int NUMBER_OF_BUTTONS = 4;
    private static final String NAMES_OF_BUTTONS[] = {"深度搜索", "广度搜索", "启发式搜索", "还原初始状态"};
    private JButton buttons[];
    private MainPanel mainPanel;

    public ButtonPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        this.setLayout(new GridLayout(9,2));
        initializeButtons();
    }

    private void initializeButtons() {
        initializeButtonsView();
        addActionListenerForButtons();
    }

    private void initializeButtonsView() {
        buttons = new JButton[NUMBER_OF_BUTTONS];
        this.add(new JLabel());  //in order to align the buttons
        this.add(new JLabel());
        for (int i = 0; i < NUMBER_OF_BUTTONS; i++) {
            buttons[i] = new JButton(NAMES_OF_BUTTONS[i]);
            buttons[i].setBackground(Color.GREEN);
            this.add(buttons[i]);
            this.add(new JLabel());
            this.add(new JLabel());
            this.add(new JLabel());
        }
    }

    private void addActionListenerForButtons() {
        buttons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Thread.sleep(200);
                    mainPanel.doDepthFirstSearch();
                }catch(InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        });
        buttons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Thread.sleep(200);
                    mainPanel.doBreadthFirstSearch();
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        });
        buttons[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Thread.sleep(200);
                    mainPanel.doIntelligentSearch();
                }catch(InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        });
        buttons[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.restore();
            }
        });
    }

}
