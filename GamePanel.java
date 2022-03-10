package seriousgame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class GamePanel extends JPanel implements Runnable {

    Main m;
    JFrame frame;
    BufferedImage logoLarge, logoSmall, previous, next, heart;


    // SCREEN SETTINGS
    double FPS = 60;
    int widthFrame = 1280;
    int heightFrame = 1024;

    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int ruleState = 3;
    public final int waitingState = 4;

    // COLORS
    public static final Color myLightGray = new Color(217,217,217);
    public static final Color myYellow = new Color(253, 221, 4);

    UI UI = new UI(this);
    KeyHandler keyH = new KeyHandler(this);
    Thread gameThread = new Thread(this);
    Road road = new Road(this);
    Car car = new Car(this, keyH);
    Sign sign = new Sign(this);
    Event event = new Event(this, car, sign);

    String text;
    String medal;
    public int level = 1;



    public GamePanel(Main m) {

        this.m = m;

        this.setBounds(25,25,1200,930);
        this.setLayout(null);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.setBackground(myLightGray);

        createFrame();
        frame.add(this);
        getImage();
        setupGame();

        this.StartGameThread();
    }

    public void createFrame() {
        frame = new JFrame();
        frame.setTitle("Kierowca na medal - Mateusz Pogorzelski - 176809");
        frame.setSize(widthFrame, heightFrame);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(myLightGray);
        frame.setVisible(true);
    }

    public void setupGame() {

        gameState = titleState;

    }

    public void StartGameThread(){
        gameThread.start();

    }

    public void setLevel(Graphics2D g2D) {

        createButton("",myYellow,910,700,50,50,g2D);
        createButton("",myYellow,1085,700,50,50,g2D);
        g2D.drawImage(previous,910,700,null);
        g2D.drawImage(next,1085,700,null);
        g2D.setColor(Color.black);
        g2D.setFont(UI.normalFont);
        UI.drawCenteredString("Wybierz poziom",860,620,325,50,g2D);

        switch(level) {
            case 1 -> text = "Easy";
            case 2 -> text = "Medium";
            case 3 -> text = "Hard";
            case 4 -> text = "Infinity";
        }
        UI.drawCenteredString(text,860,780,325,50,g2D);

        g2D.setFont(UI.levelFont);
        UI.drawCenteredString(""+level,860,700,325,50,g2D);

    }

    public void setDefaultLevel() {
        switch (level) {
            case 1 -> event.levelVictoryPoints = 10;
            case 2 -> event.levelVictoryPoints = 15;
            case 3 -> event.levelVictoryPoints = 20;
            case 4 -> event.levelVictoryPoints = 1000;
        }
        switch (level) {
            case 1 -> medal = "brązowy";
            case 2 -> medal = "srebrny";
            case 3 -> medal = "złoty";
        }
        switch (level) {
            case 1 -> road.speed = 1;
            case 2 -> road.speed = 1;
            case 3 -> road.speed = 0.9;
            case 4 -> road.speed = 0.9;
        }
        switch (level) {
            case 1 -> car.speed = 7;
            case 2 -> car.speed = 8;
            case 3 -> car.speed = 9;
            case 4 -> car.speed = 9;
        }
        switch (level) {
            case 1 -> sign.speed = 7;
            case 2 -> sign.speed = 8;
            case 3 -> sign.speed = 10;
            case 4 -> sign.speed = 8;
        }

    }

    public void createButton(String p, Color color, int x, int y, int width, int height, Graphics2D g2D) {

        JLabel label = new JLabel();

        g2D.setColor(color);
        g2D.fillRect(x,y,width,height);

        g2D.setColor(Color.black);
        g2D.drawRect(x,y,width,height);
        g2D.setFont(g2D.getFont().deriveFont(Font.BOLD,28));
        text = p;
        UI.drawCenteredString(text,x,y,width,height,g2D);

        label.setBounds(x,y,width,height);
        this.add(label);

        label.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(Objects.equals(p, "START GRY")) {
                    gameState = playState;
                    setDefaultLevel();
                }
                if(Objects.equals(p, "ZASADY GRY")) gameState = ruleState;
                if(Objects.equals(p, "KONIEC GRY")) {
                    frame.dispose();
                    System.exit(0);
                }
                if(Objects.equals(p, "POWRÓT DO MENU")) {
                    gameState = titleState;
                    restart();

                }
                if(Objects.equals(p, "RESTART")) {
                    restart();
                    if(gameState == waitingState){
                        level++;
                        if(level > 4) level = 1;
                        setDefaultLevel();
                        restart();
                        gameState = playState;
                    }
                }

                if(Objects.equals(p, "") && x == 910) {
                    level--;
                    if(level < 1) level = 4;
                }
                if(Objects.equals(p, "") && x == 1085) {
                    level++;
                    if(level > 4) level = 1;
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}

        });
    }

    public void restart(){
        UI.playTime = 0;
        event.counterPoints = 0;
        event.counterHearts = 3;
        UI.progress = 0;
        setDefaultLevel();
        car.setDefaultValues();
        sign.generateNextSequence();

        for(int i=0; i<3; i++) {
            sign.getSignImage(i);
            sign.setDefaultValues(i);
        }
    }

    public void createMenu(Graphics2D g2D) {
        if(gameState == titleState) {
            createButton("START GRY", myYellow, 860, 380, 325, 50, g2D);
            createButton("ZASADY GRY", myYellow, 860, 460, 325, 50, g2D);
            createButton("KONIEC GRY", myYellow, 860, 540, 325, 50, g2D);
        }
        else if(gameState == playState || gameState == pauseState) {
            createButton("POWRÓT DO MENU", myYellow, 860, 250, 325, 50, g2D);
            createButton("RESTART", myYellow, 860, 330, 325, 50, g2D);
        }
        else if(gameState == ruleState) {
            createButton("POWRÓT DO MENU", myYellow, 860, 250, 325, 50, g2D);
        }
        else if(gameState == waitingState) {
            createButton("POWRÓT DO MENU", myYellow, 860, 250, 325, 50, g2D);
            if(level != 4) createButton("NASTĘPNY POZIOM", myYellow, 860, 330, 325, 50, g2D);
        }

    }

    public void getImage() {
        try {
            logoSmall = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/logo.png")));
            logoLarge = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/menu.png")));
            previous = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/previous-button.png")));
            next = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/next-button.png")));
            heart = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/heart.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void basicDraw(Graphics2D g2D) {
        g2D.drawImage(logoSmall, 840,25, null);
        road.draw(g2D);
        car.draw(g2D);
        sign.draw(g2D);
        UI.draw(g2D);
    }

    public void hearts(Graphics2D g2D) {
        if(level == 4) {
            if(event.counterHearts > 0){
                g2D.drawImage(heart, 910,580, null);
                if(event.counterHearts > 1) {
                    g2D.drawImage(heart, 990,580, null);
                    if(event.counterHearts > 2) g2D.drawImage(heart, 1070,580, null);
                }
            }
        }
    }

    @Override
    public void run() {
        double drawInterval = 1000/FPS;
        long lastTime = System.currentTimeMillis();
        long currentTime;

        double delta = 0;
        while (gameThread != null) {

            currentTime = System.currentTimeMillis();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1){
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {

        if(gameState == playState) {
            road.update();
            car.update();
            sign.update();
            event.collision();
            event.victory();
        }
        if (event.counterPoints == event.levelVictoryPoints) gameState = waitingState;


    }

    public void paintComponent(Graphics g) {


        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D)g;
        createMenu(g2D);


        // TITLE STATE
        if(gameState == titleState) {

            g2D.drawImage(logoLarge, 25,25, null);
            setLevel(g2D);
        }

        if(gameState == playState) {

            basicDraw(g2D);
            hearts(g2D);

        }

        // PAUSE STATE
        if(gameState == pauseState) {

            hearts(g2D);

            basicDraw(g2D);

            g2D.setFont(g2D.getFont().deriveFont(Font.BOLD,80F));
            g2D.setColor(Color.orange);
            UI.drawCenteredString("PAUSED",0,0,800,930, g2D);
            UI.playTime -=(double) 1/60;
        }

        // RULE STATE
        if(gameState == ruleState) {

            g2D.setFont(UI.normalFont);
            String text = "ZASADY GRY";
            g2D.drawString(text,100,285);
            text= "STEROWANIE";
            g2D.drawString(text,100,550);

            g2D.setFont(g2D.getFont().deriveFont(Font.PLAIN,20F));
            text = "Celem gry jest zostanie Kierowcą na Medal. Żeby to zrealizować należy zdobyć";
            g2D.drawString(text,100,330);
            text = "odpowiednią ilość punktów w poziomie. Punkty zdobywa się wjeżdżając autem";
            g2D.drawString(text,100,360);
            text = "w znaki i sygnały świetlne, które pozwalają kontynuowac jazdę według Kodeksu";
            g2D.drawString(text,100,390);
            text = "Ruchu Drogowego. Wjechanie w znak zabraniający dalszej jazdypowoduje";
            g2D.drawString(text,100,420);
            text = "wyzerowanie się punktów lub utratę życia w trybie Infinity.";
            g2D.drawString(text,100,450);

            text = "[strzałka w lewo] - zmiana położenia pojazdu w lewo, zmiana pasa";
            g2D.drawString(text,100,600);
            text = "[strzałka w prawo] - zmiana położenia pojazdu w prawo, zmiana pasa";
            g2D.drawString(text,100,630);
            text = "[spacja] - zatrzymanie gry";
            g2D.drawString(text,100,660);

       }

        // WAITING STATE
        if(gameState == waitingState) {

            basicDraw(g2D);

            g2D.setFont(g2D.getFont().deriveFont(Font.BOLD,50F));
            g2D.setColor(Color.orange);
            UI.playTime -=(double) 1/60;
            if(level == 4) {
                UI.drawCenteredString("Game over!",0,0,800,830, g2D);
                if(event.counterPoints == 1) text = " punkt";
                else if(event.counterPoints > 1 && event.counterPoints < 5) text = " punkty";
                else text = " punktów";
                UI.drawCenteredString("Zdobyto " + event.counterPoints + text,0,0,800,980, g2D);
                UI.drawCenteredString("w czasie  " + UI.dFormat.format(UI.playTime) + " s.",0,0,800,1080, g2D);
                //level = 1;
                event.counterHearts = 3;
            }
            else {
                UI.drawCenteredString("Brawo!",0,0,800,880, g2D);
                UI.drawCenteredString("Zdobywasz " + medal + " medal!",0,0,800,980, g2D);
            }

        }

        g2D.dispose();

    }
}
