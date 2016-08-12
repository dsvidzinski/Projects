//package dfinal.controller;

//import dfinal.game.model.*;
//import dfinal.game.view.GamePanel;
//import dfinal.sounds.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;


/**
 * Created by dsvid88 on 5/19/15.
 */
public class Game implements KeyListener, Runnable {


    private GamePanel gamePanel;
    private boolean laserOn;

    public final static int ANI_DELAY = 45; // milliseconds between screen
    public static Random R = new Random();
    private Thread thrAnim;
    private int nLevel = 0;
    private int nTick = 0;
    private static long theScore = 0;


    //game control keys
    private final int PAUSE = 80, // p key
            QUIT = 81, // q key
            LEFT = 37, //  move left; left arrow
            RIGHT = 39, // move right; right arrow
            START = 83, // s key
            FIRE = 32; // space key


    public Game() {
        //creating a game panel and game frame
        gamePanel = new GamePanel();
        //adding a key listener to the PANEL;
        gamePanel.addKeyListener(this);
    }


    public static void main(String args[]) {

        EventQueue.invokeLater(new Runnable() { // uses the Event dispatch thread from Java 5 (refactored)
            public void run() {
                try {
                    Game game = new Game(); // construct itself
                    game.fireUpAnimThread();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //This creates animations
    private void fireUpAnimThread() { // called initially
        if (thrAnim == null) {
            thrAnim = new Thread(this); // pass the thread a runnable object (this)
            thrAnim.start();
        }
    }

    @Override //runnable interface method required by thread
    public void run() {

        long lStartTime = System.currentTimeMillis();

        thrAnim.setPriority(Thread.MIN_PRIORITY);

        while (Thread.currentThread() == thrAnim) {

            tick();

            gamePanel.update(gamePanel.getGraphics()); // update takes the graphics context we must

            //this might be a good place to check for collisions
            checkCollisions();

            checkGameOver();

            checkNewLevel();

            try {

                lStartTime += ANI_DELAY;
                Thread.sleep(Math.max(0,
                        lStartTime - System.currentTimeMillis()));
            } catch (InterruptedException e) {
                // just skip this frame -- no big deal
                continue;
            }
        } // end while

    }//end of run method


    private void checkCollisions() {

        // Point pntFriendCenter, pntFoeCenter;
        // int nFriendRadiux, nFoeRadiux;


        for (ImMovable movFoe : ControlCenter.breakableFoes) {

            int[] pntBrickX = movFoe.getXcoords();
            int[] pntBrickY = movFoe.getYcoords();

            Point pntBall = ControlCenter.getBall().getCenter();

            //This is perfect for the top
            if (pntBrickX[3] - 5 < pntBall.x && pntBall.x < pntBrickX[0] + 5 &&
                    pntBrickY[0] - 5 < pntBall.y && pntBall.y < pntBrickY[1] + 5) {

                Sound.playSound("Drop_Fork.wav");

                theScore = theScore + 10;
                ControlCenter.setScore(theScore);
                ControlCenter.breakableFoes.remove(movFoe);

                Random rand = new Random();

                if (laserOn != true) {
                    Random rand1 = new Random();
                    int laserNumber = rand1.nextInt(10) + 1;

                    System.out.println("The laserNumber is " + laserNumber);
                    if (laserNumber == 7) {
                        laserOn = true;
                        ControlCenter.setLaser(true);
                        Sound.playSound("laserPowerUp.wav");

                    }
                }

                int pickedNumber = rand.nextInt(4) + 1;


                if (pickedNumber == 1) {
                    //always to the left
                    if (ControlCenter.getBall().getDeltaX() > 0) {
                        ControlCenter.getBall().setDeltaX(-10);
                    } else {
                        ControlCenter.getBall().setDeltaX(10);
                    }

                    if (ControlCenter.getBall().getDeltaY() > 0) {
                        ControlCenter.getBall().setDeltaY(-10);
                    } else {
                        ControlCenter.getBall().setDeltaY(10);
                    }
                } else if (pickedNumber == 2) {

                    //switching from left to right
                    if (ControlCenter.getBall().getDeltaX() > 0) {
                        ControlCenter.getBall().setDeltaX(-5);
                    } else {
                        ControlCenter.getBall().setDeltaX(5);
                    }

                    if (ControlCenter.getBall().getDeltaY() > 0) {
                        ControlCenter.getBall().setDeltaY(-5);
                    } else {
                        ControlCenter.getBall().setDeltaY(5);
                    }
                } else if (pickedNumber == 3) {

                    //always to the right
                    if (ControlCenter.getBall().getDeltaX() > 0) {
                        ControlCenter.getBall().setDeltaX(10);
                    } else {
                        ControlCenter.getBall().setDeltaX(10);
                    }

                    if (ControlCenter.getBall().getDeltaY() > 0) {
                        ControlCenter.getBall().setDeltaY(-10);
                    } else {
                        ControlCenter.getBall().setDeltaY(10);
                    }
                } else if (pickedNumber == 4) {

                    //always to the right
                    if (ControlCenter.getBall().getDeltaX() > 0) {
                        ControlCenter.getBall().setDeltaX(5);
                    } else {
                        ControlCenter.getBall().setDeltaX(5);
                    }

                    if (ControlCenter.getBall().getDeltaY() > 0) {
                        ControlCenter.getBall().setDeltaY(-10);
                    } else {
                        ControlCenter.getBall().setDeltaY(10);
                    }
                }

            }

        }


        //shooting at breakables
        for (movableObject movFriend : ControlCenter.movFriends) {

            for (ImMovable movFoe : ControlCenter.breakableFoes) {

                int[] pntBrickX = movFoe.getXcoords();
                int[] pntBrickY = movFoe.getYcoords();

                Point laser = movFriend.getCenter();

                if (pntBrickX[3] - 5 < laser.x && laser.x < pntBrickX[0] + 5 &&
                        pntBrickY[0] - 5 < laser.y && laser.y < pntBrickY[1] + 5) {

                    Sound.playSound("breakingBricks.wav");
                    //  Sound.playSound("Drop_Fork.wav");
                    theScore = theScore + 10;
                    ControlCenter.setScore(theScore);
                    ControlCenter.breakableFoes.remove(movFoe);
                    movFriend.expire();
                    ControlCenter.movFriends.remove(movFriend);

                }
            }

        }


        //shooting at solid bricks
        for (movableObject movFriend : ControlCenter.movFriends) {

            for (ImMovable movFoe : ControlCenter.unBreakableFoes) {

                int[] pntBrickX = movFoe.getXcoords();
                int[] pntBrickY = movFoe.getYcoords();

                Point laser = movFriend.getCenter();

                if (pntBrickX[3] - 5 < laser.x && laser.x < pntBrickX[0] + 5 &&
                        pntBrickY[0] - 5 < laser.y && laser.y < pntBrickY[1] + 5) {

                    Sound.playSound("breakingBricks.wav");
                    theScore = theScore + 10;
                    ControlCenter.setScore(theScore);
                    ControlCenter.unBreakableFoes.remove(movFoe);
                    movFriend.expire();
                    ControlCenter.movFriends.remove(movFriend);


                }
            }

        }


        for (ImMovable movFoe : ControlCenter.unBreakableFoes) {

            if (movFoe instanceof UnBreakable) {
                int[] pntBrickX = movFoe.getXcoords();
                int[] pntBrickY = movFoe.getYcoords();

                Point pntBall = ControlCenter.getBall().getCenter();


                //This is perfect for the top
                if (pntBrickX[3] - 10 < pntBall.x && pntBall.x < pntBrickX[0] + 10 &&
                        pntBrickY[0] - 5 < pntBall.y && pntBall.y < pntBrickY[1] + 5) {

                    //System.out.println("We HAVE A Collision");
                    Sound.playSound("Drop_Fork.wav");
                    Random rand = new Random();
                    int pickedNumber = rand.nextInt(4) + 1;


                    if (pickedNumber == 1) {
                        //always to the left
                        if (ControlCenter.getBall().getDeltaX() > 0) {
                            ControlCenter.getBall().setDeltaX(-10);
                        } else {
                            ControlCenter.getBall().setDeltaX(10);
                        }

                        if (ControlCenter.getBall().getDeltaY() > 0) {
                            ControlCenter.getBall().setDeltaY(-10);
                        } else {
                            ControlCenter.getBall().setDeltaY(10);
                        }
                    } else if (pickedNumber == 2) {

                        //switching from left to right
                        if (ControlCenter.getBall().getDeltaX() > 0) {
                            ControlCenter.getBall().setDeltaX(-5);
                        } else {
                            ControlCenter.getBall().setDeltaX(5);
                        }

                        if (ControlCenter.getBall().getDeltaY() > 0) {
                            ControlCenter.getBall().setDeltaY(-5);
                        } else {
                            ControlCenter.getBall().setDeltaY(5);
                        }
                    } else if (pickedNumber == 3) {

                        //always to the right
                        if (ControlCenter.getBall().getDeltaX() > 0) {
                            ControlCenter.getBall().setDeltaX(10);
                        } else {
                            ControlCenter.getBall().setDeltaX(10);
                        }

                        if (ControlCenter.getBall().getDeltaY() > 0) {
                            ControlCenter.getBall().setDeltaY(-10);
                        } else {
                            ControlCenter.getBall().setDeltaY(10);
                        }
                    } else if (pickedNumber == 4) {

                        //always to the right
                        if (ControlCenter.getBall().getDeltaX() > 0) {
                            ControlCenter.getBall().setDeltaX(5);
                        } else {
                            ControlCenter.getBall().setDeltaX(5);
                        }

                        if (ControlCenter.getBall().getDeltaY() > 0) {
                            ControlCenter.getBall().setDeltaY(-10);
                        } else {
                            ControlCenter.getBall().setDeltaY(10);
                        }
                    }
                }

            }

        }


        //check for collisions between falcon and floaters
        if (ControlCenter.getPaddle() != null) {


            int[] pntPaddleX = ControlCenter.getPaddle().getXcoords();
            int[] pntPaddleY = ControlCenter.getPaddle().getYcoords();

            Sound.playSound("Blop.wav");

            Point pntBall = ControlCenter.getBall().getCenter();
            //	System.out.println("The balls' x coordinates are " + pntBall.x);

            if (pntPaddleX[3] - 5 < pntBall.x && pntBall.x < pntPaddleX[0] + 5 &&
                    pntPaddleY[3] < pntBall.y + 10) {

                Sound.playSound("GolfClub Swing.wav");

                Random rand = new Random();

                int pickedNumber = rand.nextInt(4) + 1;

                if (pickedNumber == 1) {
                    //always to the left
                    if (ControlCenter.getBall().getDeltaX() > 0) {
                        ControlCenter.getBall().setDeltaX(-10);
                    } else {
                        ControlCenter.getBall().setDeltaX(10);
                    }

                    if (ControlCenter.getBall().getDeltaY() > 0) {
                        ControlCenter.getBall().setDeltaY(-10);
                    } else {
                        ControlCenter.getBall().setDeltaY(10);
                    }
                } else if (pickedNumber == 2) {

                    //switching from left to right
                    if (ControlCenter.getBall().getDeltaX() > 0) {
                        ControlCenter.getBall().setDeltaX(-5);
                    } else {
                        ControlCenter.getBall().setDeltaX(5);
                    }

                    if (ControlCenter.getBall().getDeltaY() > 0) {
                        ControlCenter.getBall().setDeltaY(-5);
                    } else {
                        ControlCenter.getBall().setDeltaY(5);
                    }
                } else if (pickedNumber == 3) {

                    //always to the right
                    if (ControlCenter.getBall().getDeltaX() > 0) {
                        ControlCenter.getBall().setDeltaX(10);
                    } else {
                        ControlCenter.getBall().setDeltaX(10);
                    }

                    if (ControlCenter.getBall().getDeltaY() > 0) {
                        ControlCenter.getBall().setDeltaY(-10);
                    } else {
                        ControlCenter.getBall().setDeltaY(10);
                    }
                } else if (pickedNumber == 4) {

                    //always to the right
                    if (ControlCenter.getBall().getDeltaX() > 0) {
                        ControlCenter.getBall().setDeltaX(5);
                    } else {
                        ControlCenter.getBall().setDeltaX(5);
                    }

                    if (ControlCenter.getBall().getDeltaY() > 0) {
                        ControlCenter.getBall().setDeltaY(-10);
                    } else {
                        ControlCenter.getBall().setDeltaY(10);
                    }
                }

            }

        }//end if

    }//end inner for

    //such as the appearance of UFOs, floaters (power-ups), etc.
    public void tick() {
        if (nTick == Integer.MAX_VALUE)
            nTick = 0;
        else
            nTick++;
    }

    public int getTick() {
        return nTick;
    }

    private void startGame() {

        ControlCenter.initGame();
        ControlCenter.setLevel(nLevel);
        ControlCenter.setPlaying(true);
        theScore = 0;
        ControlCenter.setScore(theScore);

    }


    private void spawnBall(int nNum) {

        Ball ball = new Ball(5);
        //	CommandCenter.movFoes.add(ball);
        ControlCenter.setBall(ball);

    }


    private boolean isLevelClear() {
        //if there are no more Bricks on the screen

        boolean bBrickFree = true;
        for (ImMovable movFoe : ControlCenter.breakableFoes) {
            if (movFoe instanceof ImMovable) {
                bBrickFree = false;
                break;
            }
        }

        return bBrickFree;
    }


    private void checkGameOver() {

        if (ControlCenter.getPaddle() != null) {
            Point pntPaddleCenter = ControlCenter.getPaddle().getCenter();


            int[] pntPaddleX = ControlCenter.getPaddle().getXcoords();
            int[] pntPaddleY = ControlCenter.getPaddle().getYcoords();


            Point pntBall = ControlCenter.getBall().getCenter();


            if (((pntPaddleX[3] - 5 > pntBall.x) || (pntBall.x > pntPaddleX[0] + 5)) &&
                    ControlCenter.getBall().getCenter().getY() > 571) {

                int numLives = ControlCenter.getNumPaddles();
                numLives = numLives - 1;
                ControlCenter.setNumPaddles(numLives);
                if (numLives > 0) {
                    JOptionPane.showMessageDialog(null, "The number of lives left is " +
                            ControlCenter.getNumPaddles());

                    ControlCenter.setLaser(false);
                    laserOn = false;

                    Ball ball = new Ball(5);
                    ControlCenter.getBall().setCenter(new Point(300, 300));
                    ControlCenter.getBall().setDeltaY(-10);
                } else {

                    numLives = 0;
                    ControlCenter.setNumPaddles(numLives);
                    ControlCenter.getBall().setCenter(new Point(300, 300));
                    ControlCenter.getBall().setDeltaY(-10);

                }
            }

        }
    }

    private void checkNewLevel() {

        if (isLevelClear()) {
            if (ControlCenter.getPaddle() != null)

                nLevel = nLevel + 1;
                //nLevel = ;
            ControlCenter.setLaser(false);
            ControlCenter.setLevel(nLevel);
            spawnBall(1);

        }
    }

/////////*****OVERRIDING KEY LISTENER INTERFACE*****/////////

    @Override
    public void keyPressed(KeyEvent e) {
        Paddle pad = ControlCenter.getPaddle();
        int nKey = e.getKeyCode();

        if (nKey == START && !ControlCenter.isPlaying())
            startGame();


        if (pad != null) {

            switch (nKey) {

                case QUIT:
                    System.exit(0);
                    break;

                case LEFT:
                    pad.moveLeft();
                    break;

                case RIGHT:
                    pad.moveRight();
                    break;

                default:
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Paddle pad = ControlCenter.getPaddle();
        int nKey = e.getKeyCode();
        System.out.println(nKey);

        if (pad != null) {

            if (laserOn) {
                switch (nKey) {
                    case FIRE:
                        ControlCenter.movFriends.add(new Laser(pad));
                        Sound.playSound("laser.wav");
                        break;
                }
            }

            switch (nKey) {

                case LEFT:
                    pad.stopLeft();
                    break;

                case RIGHT:
                    pad.stopRight();
                    break;

                default:
                    break;
            }
        }
    }

    @Override
    // Just need it b/c of KeyListener implementation
    public void keyTyped(KeyEvent e) {

    }
}


////// ***** END OF KEY LISTENER INTERFACE ***** //////


