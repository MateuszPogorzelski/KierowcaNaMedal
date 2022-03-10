package seriousgame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Sign{

    GamePanel gp;

    public int[] seq = new int[4];
    public boolean[] losePoint = new boolean[4];
    public double speed = 6;
    public int[] xSign = new int[4];
    public int[] ySign = new int[4];
    public BufferedImage[] signImage = new BufferedImage[15];
    public int width, height;
    public int counterErr = 0;

    public Sign(GamePanel gp) {

        this.gp = gp;

        generateNextSequence();

        for(int i=0; i<3; i++) {
            getSignImage(i);
            setDefaultValues(i);
        }

    }

    public void setDefaultValues(int wayNumber) {

        width = 150;
        height = 150;

        Random rand = new Random();

        switch (wayNumber) {
            case 0 -> {
                xSign[0] = 65;
                ySign[0] = -200 - rand.nextInt(500);
            }
            case 1-> {
                xSign[1] = 325;
                ySign[1] = -200 - rand.nextInt(500);
            }
            case 2 -> {
                xSign[2] = 585;
                ySign[2] = -200 - rand.nextInt(500);
            }
        }

    }

    public void generateNextSequence() {

        for (int i = 0; i < 3; i++) {
            generateNextNumber(i);
            if (counterErr == 3) {
                counterErr--;
                i--;
            }

        }
    }

    public void generateNextNumber(int wayNumber) {

        Random rand = new Random();
        boolean check = true;

        // CHECK FREE WAY
        while(check) {
            seq[wayNumber] = rand.nextInt(17);


            if (seq[wayNumber] == 10 || seq[wayNumber] == 11 || seq[wayNumber] == 12 || seq[wayNumber] == 13
                    || seq[wayNumber] == 14 || seq[wayNumber] == 15 || seq[wayNumber] == 16) {
                counterErr++;
                losePoint[wayNumber] = true;
            } else losePoint[wayNumber] = false;

            check = losePoint[0] && losePoint[1] && losePoint[2];
        }
    }

    public void getSignImage(int wayNumber){

        try {
            switch (seq[wayNumber]) {

                // RIGHT ROAD SIGNS
                case 0 -> signImage[wayNumber] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/signR1.png")));
                case 1 -> signImage[wayNumber] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/signR2.png")));
                case 2 -> signImage[wayNumber] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/signR3.png")));
                case 3 -> signImage[wayNumber] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/signR4.png")));
                case 4 -> signImage[wayNumber] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/signR5.png")));
                case 5 -> signImage[wayNumber] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/signR6.png")));
                case 6 -> signImage[wayNumber] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/signR7.png")));
                case 7 -> signImage[wayNumber] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/light_green.png")));
                case 8 -> signImage[wayNumber] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/signR8.png")));
                case 9 -> signImage[wayNumber] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/signR9.png")));

                // WRONG ROAD SIGNS
                case 10 -> signImage[wayNumber] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/signW1.png")));
                case 11 -> signImage[wayNumber] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/signW2.png")));
                case 12 -> signImage[wayNumber] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/signW3.png")));
                case 13 -> signImage[wayNumber] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/signW4.png")));
                case 14 -> signImage[wayNumber] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/light_red.png")));
                case 15 -> signImage[wayNumber] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/light_red_yellow.png")));
                case 16 -> signImage[wayNumber] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/light_yellow.png")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void update() {

        for(int i=0; i<3; i++) {
            ySign[i] += speed;
        }
    }

    BufferedImage img = signImage[0];
    boolean cover1 = false;
    boolean cover2 = false;

    public void draw(Graphics g2D) {

        BufferedImage image = null;


        for (int i=0; i<3; i++) {
            image = signImage[i];
            g2D.drawImage(image, xSign[i], ySign[i], width, height, null);

            // SHOW SIGN AFTER COLLISION
            if(gp.event.collision[i]) {
                img = signImage[i];
                if(seq[i] == 5) {cover1 = true; cover2 = false;}
                else if(seq[i] == 7 || seq[i] == 14 || seq[i] == 15 || seq[i] == 16) {cover1 = false; cover2 = true;}
                else {cover1 = false; cover2 = false;}
            }

        }

        g2D.drawImage(img, 940, 650, width, height, null);
        g2D.setColor(GamePanel.myLightGray);

        if (cover1) g2D.fillRect(940,650,width,58);

        else if (cover2) {
            g2D.fillRect(940,650,width/4,height);
            g2D.fillRect(1055,625,width/4,200);
        }

    }

}