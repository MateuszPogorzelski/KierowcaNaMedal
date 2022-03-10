package seriousgame;

import java.awt.*;
import java.text.DecimalFormat;

public class UI{

    GamePanel gp;
    Font boldFont, normalFont, levelFont;
    String description = "Aktualny znak";

    public int progress;
    double playTime;

    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp){

        this.gp = gp;



        // FONTS
        boldFont = new Font("myFont", Font.BOLD, 50);
        levelFont = new Font("myFont", Font.BOLD, 70);
        normalFont = new Font("myFont", Font.BOLD, 28);

    }

    public void draw(Graphics2D g2D) {

        g2D.setFont(normalFont);
        g2D.setColor(Color.black);

        // POINTS
        int l;
        if(gp.event.counterPoints < 10) l = 1065;
        else l = 1050;
        g2D.drawString("Punkty: " + gp.event.counterPoints, l, 550);

        // LEVEL
        g2D.drawString("Poziom " + gp.level, 840, 550);

        // PROGRESS BAR
        if(gp.level != 4) {
            g2D.setColor(Color.GREEN);
            if (gp.event.counterPoints == gp.event.levelVictoryPoints) progress = 350;
            g2D.fillRect(840,580,progress,30);
            g2D.setColor(Color.black);
            g2D.drawRect(840,580,350,30);
        }

        // PLAY TIMER
        g2D.setColor(Color.black);
        playTime +=(double) 1/60;
        g2D.setFont(normalFont);
        drawCenteredString("Czas gry: " + dFormat.format(playTime) + " s",860, 410, 325, 50, g2D);

        // SIGNS DESCRIPTIONS

        g2D.setFont(normalFont);
        g2D.setColor(Color.black);
        for(int i = 0; i<3; i++) {
            if(gp.event.collision[i]){
                switch (gp.sign.seq[i]) {
                    case 0 -> description = "Znak D-1";
                    case 1 -> description = "Znak C-7";
                    case 2 -> description = "Znak D-6";
                    case 3 -> description = "Znak A-6c";
                    case 4 -> description = "Znak A-7";
                    case 5 -> description = "Znak D-42";
                    case 6 -> description = "Znak T-06b";
                    case 7 -> description = "Znak S-1 jedź";
                    case 8 -> description = "Znak C-5 jedź";
                    case 9 -> description = "Znak A-29";
                    case 10 -> description = "Znak B-20";
                    case 11 -> description = "Znak B-2";
                    case 12 -> description = "Znak B-1";
                    case 13 -> description = "Znak B-3";
                    case 14 -> description = "Znak S-1 stój";
                    case 15 -> description = "Znak S-1 uwaga";
                    case 16 -> description = "Znak S-1 czekaj";
                }
            }
        }

        drawCenteredString(description,850, 800, 325, 100, g2D);

    }


    public void drawCenteredString(String p, int X, int Y, int width, int height, Graphics2D g2D) {
        FontMetrics fm = g2D.getFontMetrics();
        int x = (width - fm.stringWidth(p)) / 2 + X;
        int y = (fm.getAscent() + (height - (fm.getAscent() + fm.getDescent())) / 2) + Y;
        g2D.drawString(p, x, y);
    }
}
