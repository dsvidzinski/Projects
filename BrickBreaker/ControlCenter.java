//package dfinal.game.model;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by dsvid88 on 5/21/15.
 */
public class ControlCenter {

    private static boolean bPlaying;
    private static boolean bPaused;

    private static boolean lLaser;
    private static int nNumPaddle;
    private static int nLevel;
    private static long lScore;
    private static ImMovable firstImmovalbe;
    private static Paddle thePaddle;
    private static Ball ball;

    public static CopyOnWriteArrayList<ImMovable> breakableFoes = new CopyOnWriteArrayList<ImMovable>();
    public static CopyOnWriteArrayList<ImMovable> unBreakableFoes = new CopyOnWriteArrayList<ImMovable>();
    public static CopyOnWriteArrayList<movableObject> movFriends = new CopyOnWriteArrayList<movableObject>();


    private ControlCenter() {
    }

    //this is where we create all inital game settings
    public static void initGame() {
        setScore(0);
        setNumPaddles(10);
        spawnPaddle(true);
    }

    // When you spawn a new falcon, you need to decrement its number
    public static void spawnPaddle(boolean bFirst) {
        thePaddle = new Paddle();
        ControlCenter.setPaddle(thePaddle);
    }

    public static void clearAll() {
        breakableFoes.clear();
        unBreakableFoes.clear();
    }


    //mehods to get the menue going
    public static boolean isPlaying() {
        return bPlaying;
    }

    public static void setPlaying(boolean bPlaying) {
        ControlCenter.bPlaying = bPlaying;
    }

    public static boolean isPaused() {
        return bPaused;
    }

    public static void setPaused(boolean bPaused) {
        ControlCenter.bPaused = bPaused;
    }

    public static boolean isGameOver() {        //if the number of falcons is zero, then game over
        return getNumPaddles() == 0;
    }

    public static int getLevel() {
        return nLevel;
    }

    public static long getScore() {
        return lScore;
    }

    public static void setScore(long lParam) {
        lScore = lParam;
    }

    public static CopyOnWriteArrayList<movableObject> getMovFriends() {
        return movFriends;
    }


    public static void setLaser(boolean lParam) {
        lLaser = lParam;
    }

    public static boolean getLaser() {
        return lLaser;
    }


    public static void setLevel(int n) {
        nLevel = n;


        //LEVEL 1
        if (nLevel == 1) {


            int x = 0;
            int j = 0;
            for (int i = 0; i < 4; i++) {
                //brickArray.add(new ImMovable(200, 200+x));
                ControlCenter.breakableFoes.add(new ImMovable(250 + x + j, 40));
                j = j + 1;
                x = x + 50;
            }

            x = 0;
            j = 0;
            for (int i = 0; i < 6; i++) {
                //brickArray.add(new ImMovable(200, 200+x));
                ControlCenter.breakableFoes.add(new ImMovable(200 + x + j, 52));
                x = x + 50;
                j = j + 1;
            }

            j = 0;
            x = 0;
            for (int i = 0; i < 8; i++) {
                //brickArray.add(new ImMovable(200, 200+x));
                ControlCenter.breakableFoes.add(new ImMovable(150 + x + j, 64));
                j = j + 1;
                x = x + 50;
            }

            j = 0;
            x = 0;
            for (int i = 0; i < 10; i++) {
                //brickArray.add(new ImMovable(200, 200+x));
                ControlCenter.breakableFoes.add(new ImMovable(100 + x + j, 76));
                j = j + 1;
                x = x + 50;
            }

            j = 0;
            x = 0;
            for (int i = 0; i < 12; i++) {
                //brickArray.add(new ImMovable(200, 200+x));
                ControlCenter.breakableFoes.add(new ImMovable(50 + x + j, 88));
                x = x + 50;
                j = j + 1;
            }

            j = 0;
            x = 0;
            for (int i = 0; i < 12; i++) {
                //brickArray.add(new ImMovable(200, 200+x));
                ControlCenter.breakableFoes.add(new ImMovable(50 + x + j, 100));
                x = x + 50;
                j = j + 1;
            }

            j = 0;
            x = 0;
            for (int i = 0; i < 10; i++) {
                //brickArray.add(new ImMovable(200, 200+x));
                ControlCenter.breakableFoes.add(new ImMovable(100 + x + j, 112));
                x = x + 50;
                j = j + 1;
            }

            j = 0;
            x = 0;
            for (int i = 0; i < 8; i++) {
                //brickArray.add(new ImMovable(200, 200+x));
                ControlCenter.breakableFoes.add(new ImMovable(150 + x + j, 124));
                j = j + 1;
                x = x + 50;
            }

            j = 0;
            x = 0;
            for (int i = 0; i < 6; i++) {
                //brickArray.add(new ImMovable(200, 200+x));
                ControlCenter.breakableFoes.add(new ImMovable(200 + x + j, 136));
                j = j + 1;
                x = x + 50;
            }

            j = 0;
            x = 0;
            for (int i = 0; i < 4; i++) {
                //brickArray.add(new ImMovable(200, 200+x));
                ControlCenter.breakableFoes.add(new ImMovable(250 + x + j, 148));
                j = j + 1;
                x = x + 50;
            }

        }
        ///////////////////////////////////////////

        //LEVEL 2
        if (nLevel == 2) {

            int x;
            int k = 0;
            int j = 0;

            for (int y = 0; y < 5; y++) {
                x = 0;
                for (int i = 0; i < 7; i++) {

                    ControlCenter.breakableFoes.add(new ImMovable(0 + x, 45 + k + j));
                    x = x + 100;
                }

                j = j + 2;
                x = 0;
                for (int i = 0; i < 7; i++) {
                    //brickArray.add(new ImMovable(200, 200+x));
                    ControlCenter.breakableFoes.add(new ImMovable(50 + x, 55 + k + j));
                    x = x + 100;
                }

                j = j + 2;
                k = k + 20;

            }
        }

        if (nLevel == 3) {


            int x;
            int k = 0;
            int j = 0;

            //drawing the outside wall
            for (int i = 0; i < 17; i++) {
                ControlCenter.unBreakableFoes.add(new UnBreakable(150, 100 + k + j));
                k = k + 10;
                j = j + 1;
            }


            //drawing the bottom wall
            k = 0;
            j = 0;
            for (int i = 0; i < 11; i++) {

                //brickArray.add(new ImMovable(200, 200+x));
                ControlCenter.unBreakableFoes.add(new UnBreakable(150 + j + k, 288));
                k = k + 50;
                j = j + 1;
            }


            //drawing a bunch of breakables
            k = 0;
            j = 0;
            for (int i = 0; i < 1; i++) {
                //brickArray.add(new ImMovable(200, 200+x));
                ControlCenter.breakableFoes.add(new ImMovable(420 + j + k, 120));
                k = k + 1;
                j = j + 1;
            }


            k = 0;
            j = 0;
            for (int i = 0; i < 2; i++) {
                //brickArray.add(new ImMovable(200, 200+x));
                ControlCenter.breakableFoes.add(new ImMovable(400 + j + k, 132));
                k = k + 50;
                j = j + 1;
            }


            k = 0;
            j = 0;
            for (int i = 0; i < 3; i++) {
                //brickArray.add(new ImMovable(200, 200+x));
                ControlCenter.breakableFoes.add(new ImMovable(375 + j + k, 144));
                k = k + 50;
                j = j + 1;
            }


            k = 0;
            j = 0;
            for (int i = 0; i < 3; i++) {
                //brickArray.add(new ImMovable(200, 200+x));
                ControlCenter.breakableFoes.add(new ImMovable(375 + j + k, 156));
                k = k + 50;
                j = j + 1;
            }


            k = 0;
            j = 0;
            for (int i = 0; i < 3; i++) {
                //brickArray.add(new ImMovable(200, 200+x));
                ControlCenter.breakableFoes.add(new ImMovable(375 + j + k, 168));
                k = k + 50;
                j = j + 1;
            }


            k = 0;
            j = 0;
            for (int i = 0; i < 2; i++) {
                //brickArray.add(new ImMovable(200, 200+x));
                ControlCenter.breakableFoes.add(new ImMovable(400 + j + k, 180));
                k = k + 50;
                j = j + 1;
            }


            k = 0;
            j = 0;
            for (int i = 0; i < 1; i++) {
                //brickArray.add(new ImMovable(200, 200+x));
                ControlCenter.breakableFoes.add(new ImMovable(425 + j + k, 192));
                k = k + 1;
                j = j + 1;
            }


        }


        //start of level 4
        if (nLevel == 4) {

            int k = 0;
            int j = 0;
            //drawing the outside wall
            for (int i = 0; i < 9; i++) {
                ControlCenter.unBreakableFoes.add(new UnBreakable(330, 100 + k + j));
                k = k + 10;
                j = j + 1;
            }

            //drawing the bottom wall
            k = 0;
            j = 0;
            for (int i = 0; i < 8; i++) {

                //brickArray.add(new ImMovable(200, 200+x));
                ControlCenter.unBreakableFoes.add(new UnBreakable(150 + j + k, 200));
                k = k + 50;
                j = j + 1;
            }


            k = 0;
            j = 0;
            //drawing the outside wall
            for (int i = 0; i < 9; i++) {
                ControlCenter.unBreakableFoes.add(new UnBreakable(330, 213 + k + j));
                k = k + 10;
                j = j + 1;
            }


            //top right quadrant
            int l = 0;
            int w = 0;
            for (int h = 0; h < 6; h++) {

                k = 0;
                j = 0;
                for (int i = 0; i < 3; i++) {
                    //brickArray.add(new ImMovable(200, 200+x));
                    ControlCenter.breakableFoes.add(new ImMovable(382 + j + k, 133 + l + w));
                    k = k + 50;
                    j = j + 1;
                }
                w = w + 1;
                l = l + 10;
            }


            //top left quadrant
            l = 0;
            w = 0;
            for (int h = 0; h < 6; h++) {

                k = 0;
                j = 0;
                for (int i = 0; i < 3; i++) {
                    //brickArray.add(new ImMovable(200, 200+x));
                    ControlCenter.breakableFoes.add(new ImMovable(176 + j + k, 133 + l + w));
                    k = k + 50;
                    j = j + 1;
                }
                w = w + 1;
                l = l + 10;
            }

            //bottom left quadrant
            l = 0;
            w = 0;
            for (int h = 0; h < 6; h++) {

                k = 0;
                j = 0;
                for (int i = 0; i < 3; i++) {
                    //brickArray.add(new ImMovable(200, 200+x));
                    ControlCenter.breakableFoes.add(new ImMovable(176 + j + k, 212 + l + w));
                    k = k + 50;
                    j = j + 1;
                }
                w = w + 1;
                l = l + 10;
            }


            //bottom right quadrant
            l = 0;
            w = 0;
            for (int h = 0; h < 6; h++) {

                k = 0;
                j = 0;
                for (int i = 0; i < 3; i++) {
                    //brickArray.add(new ImMovable(200, 200+x));
                    ControlCenter.breakableFoes.add(new ImMovable(382 + j + k, 212 + l + w));
                    k = k + 50;
                    j = j + 1;
                }
                w = w + 1;
                l = l + 10;
            }//end of level 4

        }


        if (nLevel == 5) {

            int k = 0;
            int j = 0;
            //drawing the outside wall
            for (int i = 0; i < 14; i++) {
                ControlCenter.unBreakableFoes.add(new UnBreakable(130, 150 + k + j));
                k = k + 10;
                j = j + 1;
            }

            //drawing the bottom wall
            k = 0;
            j = 0;
            for (int i = 0; i < 8; i++) {

                //brickArray.add(new ImMovable(200, 200+x));
                ControlCenter.unBreakableFoes.add(new UnBreakable(130 + j + k, 306));
                k = k + 50;
                j = j + 1;
            }


            k = 0;
            j = 0;
            //drawing the outside wall
            for (int i = 0; i < 14; i++) {
                ControlCenter.unBreakableFoes.add(new UnBreakable(487, 150 + k + j));
                k = k + 10;
                j = j + 1;
            }

            //top right quadrant
            int l = 0;
            int w = 0;
            for (int h = 0; h < 8; h++) {

                k = 0;
                j = 0;
                for (int i = 0; i < 5; i++) {
                    //brickArray.add(new ImMovable(200, 200+x));
                    ControlCenter.breakableFoes.add(new ImMovable(208 + j + k, 193 + l + w));
                    k = k + 50;
                    j = j + 1;
                }
                w = w + 1;
                l = l + 10;
            }

        }

    }


    public static int getNumPaddles() {
        return nNumPaddle;
    }

    public static void setNumPaddles(int nParam) {
        nNumPaddle = nParam;
    }


    public static Ball getBall() {
        return ball;
    }

    public static void setBall(Ball a) {
        ball = a;
    }


    public static Paddle getPaddle() {
        return thePaddle;
    }

    public static void setPaddle(Paddle padParam) {
        thePaddle = padParam;
    }

}
