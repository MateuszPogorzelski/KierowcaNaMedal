package seriousgame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Road{

    GamePanel gp;

    public BufferedImage[] roadImg = new BufferedImage[7];

    private int moveRoadCounter = 0;
    private int moveRoadNumber = 1;
    public int x, y, width, height;
    public double speed;

    public Road(GamePanel gp) {

        this.gp = gp;
        setDefaultValues();
        getRoadAnimation();
    }

    public void getRoadAnimation() {
        try {
            roadImg[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/road1.png")));
            roadImg[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/road2.png")));
            roadImg[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/road3.png")));
            roadImg[3] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/road4.png")));
            roadImg[4] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/road5.png")));
            roadImg[5] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/road6.png")));
            roadImg[6] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/road7.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setDefaultValues() {
        x = 0;
        y = 0;
        width = 800;
        height = 930;
    }
    public void update() {

        moveRoadCounter++;

        if(moveRoadCounter > speed) {
            if (moveRoadNumber == 1) moveRoadNumber = 2;
            else if (moveRoadNumber == 2) moveRoadNumber = 3;
            else if (moveRoadNumber == 3) moveRoadNumber = 4;
            else if (moveRoadNumber == 4) moveRoadNumber = 5;
            else if (moveRoadNumber == 5) moveRoadNumber = 6;
            else if (moveRoadNumber == 6) moveRoadNumber = 7;
            else if (moveRoadNumber == 7) moveRoadNumber = 1;
            moveRoadCounter = 0;
        }

    }

    public void draw(Graphics g2D) {

        BufferedImage image = null;

        if(moveRoadNumber == 1) image = roadImg[0];
        if(moveRoadNumber == 2) image = roadImg[1];
        if(moveRoadNumber == 3) image = roadImg[2];
        if(moveRoadNumber == 4) image = roadImg[3];
        if(moveRoadNumber == 5) image = roadImg[4];
        if(moveRoadNumber == 6) image = roadImg[5];
        if(moveRoadNumber == 7) image = roadImg[6];

        g2D.drawImage(image,x,y,width,height, null);
    }

}
