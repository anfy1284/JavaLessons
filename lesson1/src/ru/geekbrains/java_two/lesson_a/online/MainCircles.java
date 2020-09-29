package ru.geekbrains.java_two.lesson_a.online;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainCircles extends JFrame {
    private static final int POS_X = 400;
    private static final int POS_Y = 200;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private static final int START_BALLS_COUNT = 10;

    MyStack sprites = new MyStack();

    BackGround backGround;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainCircles();
            }
        });
    }

    private MainCircles() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
        GameCanvas canvas = new GameCanvas(this);
        initApplication();
        add(canvas);
        //setResizable(false);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setTitle("Circles");
        setVisible(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if(e.getButton() == 1)
                    sprites.push(new Ball());
                else if(e.getButton() == 3 && sprites.getLength() > 1)
                    sprites.pop();
            }
        });
    }

    private void initApplication() {
        backGround = new BackGround();

        for (int i = 0; i < START_BALLS_COUNT; i++) {
            sprites.push(new Ball());
        }
    }

    void onDrawFrame(GameCanvas canvas, Graphics g, float deltaTime) {
        update(canvas, deltaTime); // obnovlenie // S = v * t
        render(canvas, g);         // otrisovka

    }

    private void update(GameCanvas canvas, float deltaTime) {
        backGround.update(canvas, deltaTime);
        sprites.reset();
        while (sprites.next()){
            ((Sprite)sprites.getCurrentValue()).update(canvas, deltaTime);
        }
    }

    private void render(GameCanvas canvas, Graphics g) {
        backGround.render(canvas, g);
        sprites.reset();
        while (sprites.next()){
            ((Sprite)sprites.getCurrentValue()).render(canvas, g);
        }
    }

}
