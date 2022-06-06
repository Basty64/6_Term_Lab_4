package com.company;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame();
        frame.setContentPane(new SuperInterface().getJPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(300,150,500,550);
        frame.setVisible(true);
    }
}

