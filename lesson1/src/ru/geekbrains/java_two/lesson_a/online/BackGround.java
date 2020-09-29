package ru.geekbrains.java_two.lesson_a.online;

import java.awt.*;

public class BackGround extends Sprite {

    private Color color;

    BackGround(){
        color = new Color(255,255,255);
    }

    @Override
    void update(GameCanvas canvas, float deltaTime) {
        color = GameCanvas.increaseColor(color,1,50,255);
    }

    @Override
    void render(GameCanvas canvas, Graphics g) {
        canvas.setBackground(color);
    }

}
