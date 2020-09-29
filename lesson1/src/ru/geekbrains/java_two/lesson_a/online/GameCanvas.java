package ru.geekbrains.java_two.lesson_a.online;

import javax.swing.*;
import java.awt.*;

public class GameCanvas extends JPanel {

    static public Color increaseColor(Color color, int step, int lightValue, int darkValue) {

        //Глючная функция, не успел отладить :(

        int max = 0, min = 0, preMax, afterMax, afterMin;
        int[] colors = new int[3];
        colors[0] = color.getRed();
        colors[1] = color.getGreen();
        colors[2] = color.getBlue();

        for (int i = 1; i < 3; i++) {
            if (colors[max] < colors[i]) max = i;
            if (colors[min] > colors[i]) min = i;
        }

        preMax = max - 1;
        if(preMax < 0) preMax = 2;

        afterMax = max + 1;
        if(afterMax > 2) afterMax = 0;

        afterMin = min + 1;
        if(afterMin > 2) afterMin = 0;

        /*
        if (colors[max] < lightValue) {
            int diff = lightValue - colors[max];
            if (diff > step) diff = step;
            colors[max] += diff;
            step -= diff;
            if (step == 0) return new Color(colors[0], colors[1], colors[2]);
        }
*/
        if (colors[min] < lightValue) {
            int diff = lightValue - colors[min];
            if (diff > step) diff = step;
            colors[min] += diff;
            step -= diff;
            if (step == 0) return new Color(colors[0], colors[1], colors[2]);
        }

        if (colors[max] > darkValue) {
            int diff = colors[max] - darkValue;
            if (diff > step) diff = step;
            colors[max] -= diff;
            step -= diff;
            if (step == 0) return new Color(colors[0], colors[1], colors[2]);
        }

        colors[preMax] -= step;
        if (colors[preMax] < lightValue){
            step = lightValue - colors[preMax];
            colors[preMax] = lightValue;
        }

        colors[afterMax] += step;
        if (colors[afterMax] > darkValue){
            step = colors[afterMax] - darkValue;
            colors[afterMax] = darkValue;
        }
/*
        colors[afterMin] -= step;
        if (colors[afterMin] < lightValue){
            colors[afterMin] = lightValue;
        }


        colors[max] -= step;
        if (colors[max] < lightValue){
            colors[max] = lightValue;
        }
*/

        return new Color(colors[0], colors[1], colors[2]);

    }

    long lastFrameTime;
    MainCircles gameController;

    GameCanvas(MainCircles gameController) {
        lastFrameTime = System.nanoTime();
        this.gameController = gameController;
    }

    @Override
    protected void paintComponent(Graphics g) {  // while (true) {
        super.paintComponent(g);
        long currentTime = System.nanoTime();
        float deltaTime = (currentTime - lastFrameTime) * 0.000000001f;
        lastFrameTime = currentTime;
        gameController.onDrawFrame(this, g, deltaTime);
        try {
            Thread.sleep(16);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        repaint();                               // }
    }

    public int getLeft() {
        return 0;
    }

    public int getRight() {
        return getWidth() - 1;
    }

    public int getTop() {
        return 0;
    }

    public int getBottom() {
        return getHeight() - 1;
    }

}
