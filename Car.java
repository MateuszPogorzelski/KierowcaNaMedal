package seriousgame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Car {

    GamePanel gp;
    KeyHandler keyH;

    public final int carWidth = 130;
    public final int carHeight = 260;
    public BufferedImage img;

    public int x, y, speed;

    public Car(GamePanel gp, KeyHandler keyH) {

        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getCarImage();

    }

    public void getCarImage() {
        try {
            img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/police130x260.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setDefaultValues() {
        x = 335;
        y = 650;

    }
    public void update() {

        if(keyH.leftPressed) x -= speed;
        if(keyH.rightPressed) x += speed;
        if(x < 25) x+= speed;
        if(x > 780 - carWidth) x-= speed;

    }

    public void draw(Graphics g2D) {

        BufferedImage image;
        image = img;
        g2D.drawImage(image,x,y,carWidth,carHeight, null);
    }

}
