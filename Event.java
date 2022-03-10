package seriousgame;

public class Event {

    GamePanel gp;
    Car car;
    Sign sign;

    boolean[] collision = new boolean[4];

    public int counterPoints = 0;
    public int counterHearts = 3;
    public int levelVictoryPoints = 1;



    public Event(GamePanel gp, Car car, Sign sign){

        this.gp = gp;
        this.car = car;
        this.sign = sign;

    }

    public void collision() {

        for(int i=0; i<3; i++) {

            collision[i] = false;

            collision[i] =  car.x + car.carWidth > sign.xSign[i]    &&
                    sign.xSign[i] + sign.width > car.x              &&
                    car.y + car.carHeight > sign.ySign[i]           &&
                    sign.ySign[i] + sign.height > car.y;

            if (collision[i]) {
                sign.ySign[i] = 1000;

                if (!sign.losePoint[i]) {
                    counterPoints++;
                    gp.UI.progress += 350/levelVictoryPoints;
                    if(gp.level == 3 || gp.level == 4) sign.speed += 0.15;
                }
                else {
                    if(gp.level == 4) counterHearts--;
                    else {
                        counterPoints = 0;
                        gp.UI.progress = 0;
                    }
                }

            }

            if (sign.ySign[i] > 1000) {

                sign.generateNextNumber(i);
                sign.getSignImage(i);
                sign.setDefaultValues(i);
            }
        }
    }

    public void victory() {
        if(counterPoints == levelVictoryPoints || counterHearts == 0) {
            gp.gameState = gp.waitingState;
        }
    }

}

