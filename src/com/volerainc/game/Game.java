package com.volerainc.game;


import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable
{
    public Boolean running = false;

    private static final long serialVersionUID = 1L;

    public static final int WIDTH = 160;

    public static final int HEIGTH = WIDTH / 12 * 9;

    public static final int SCALE = 3;

    public static String NAME = "Game";

    private JFrame frame;

    public Game()
    {
        setMinimumSize(new Dimension(HEIGHT * SCALE, WIDTH * SCALE));
        setMaximumSize(new Dimension(HEIGHT * SCALE, WIDTH * SCALE));
        setPreferredSize(new Dimension(HEIGHT * SCALE, WIDTH * SCALE));

        frame  = new JFrame(NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);

        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void run()
    {
        while(running)
        {
            System.out.println("Hello");
        }
    }

    public synchronized void start()
    {
        running = true;
        new Thread(this).start();
    }

    public synchronized void stop()
    {
        running = false;
    }

    public static void main(String[] args)
    {
        new Game().start();
    }
}