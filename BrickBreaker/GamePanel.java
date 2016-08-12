//package dfinal.game.view;

//import dfinal.game.model.ControlCenter;
//import dfinal.game.model.ImMovable;
//import dfinal.game.model.Movable;
//import dfinal.game.model.movableObject;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by dsvid88 on 5/19/15.
 */

public class GamePanel extends Panel{

    // The following "off" vars are used for the off-screen double-bufferred images.
    private Dimension dimBack;
    private Image imgBack;
    private Graphics graphicsBack;

    private  GameFrame gameframe;
    //change the font
    private Font myFont = new Font("SansSerif", Font.BOLD, 12);
    private Font myFontCap = new Font("SansSerif", Font.BOLD + Font.ITALIC, 36);
    private FontMetrics myFontMetrics;


    private int nFontWidth;
    private int nFontHeight;
    private String displayString = "";

    private static final int FRAME_WIDTH = 700;
    private static final int FRAME_HEIGHT = 600;

    public GamePanel(){

        gameframe = new GameFrame();
        gameframe.setVisible(true);
        gameframe.getContentPane().add(this);

        gameframe.setResizable(false);
        gameframe.pack();
        initMenu();
        this.setFocusable(true);

        gameframe.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        gameframe.setVisible(true);
        gameframe.setTitle("BRICK BREAKER");
        gameframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }//end of constructor GamePane

    private void drawScore(Graphics g) {
        g.setColor(Color.white);
        g.setFont(myFont);


        if (ControlCenter.getScore() != 0) {
            g.drawString("SCORE :  " + ControlCenter.getScore(), nFontWidth, nFontHeight);
            g.drawString("NUMBER OF LIVES : " + ControlCenter.getNumPaddles(), 550, 15);
        } else {
            g.drawString("NO SCORE", nFontWidth, nFontHeight);
            g.drawString("NUMBER OF LIVES : " + ControlCenter.getNumPaddles(), 550, 15);
        }

        if (ControlCenter.getLaser() == false){
            g.drawString("LASER : OFF", 550, 30);
        }
        if( ControlCenter.getLaser() == true){
            g.setColor(Color.RED);
            g.drawString("LASER : ON", 550, 30);
        }

    }


    public void update(Graphics g)
    {

        //offscreen double buffering image
        if(graphicsBack == null || gameframe.getWidth() != dimBack.width
                || gameframe.getHeight() != dimBack.height){
            dimBack = new Dimension(gameframe.getWidth(), gameframe.getHeight());
            imgBack = createImage(gameframe.getWidth(), gameframe.getHeight());
            graphicsBack = imgBack.getGraphics();
        }

        //macking the background image black...(TRY TO SET A NICE BACKGROUND INSTEAD)
        graphicsBack.setColor(new Color(50, 121, 213));
        graphicsBack.fillRect(0, 0, gameframe.getWidth(), gameframe.getHeight());

        drawScore(graphicsBack);
        drawMenu(graphicsBack);


        if(ControlCenter.isPlaying()  == false){
            displayGameMenu();
        }


        else {

            //draw them in decreasing level of importance
            //friends will be on top layer and debris on the bottom
			/*iterateMovables(grpOff,
					//CommandCenter.movDebris,
					//CommandCenter.movFloaters,
					CommandCenter.movFoes,
					CommandCenter.movFriends );*/

            iterateMovables(graphicsBack, ControlCenter.movFriends );

            drawMovables(graphicsBack, ControlCenter.getPaddle());
            drawMovables(graphicsBack, ControlCenter.getBall());


            iterateImMovables(graphicsBack,
                    ControlCenter.breakableFoes);

            iterateImMovables(graphicsBack,
                    ControlCenter.unBreakableFoes);

            if (ControlCenter.isGameOver()) {
                ControlCenter.setPlaying(false);
                //bPlaying = false;
            }
        }

        g.drawImage(imgBack, 0, 0, this);
    }

    //Draw objects
    private void  drawMovables(Graphics g, Movable m){
        m.move();
        m.draw(g);
    }

    private void iterateImMovables(Graphics g, CopyOnWriteArrayList<ImMovable> k){

//		for (CopyOnWriteArrayList<ImMovable> movMovs : movMovz){
        for (ImMovable mov : k) {

            //movMovs.move();
            mov.draw(g);
            //	mov.fadeInOut();
            //	mov.expire();
//			}
        }
    }


    private void iterateMovables(Graphics g, CopyOnWriteArrayList<movableObject> k){

        for (movableObject mov : k) {

            mov.move();
            mov.draw(g);
        }

    }

    private void initView() {
        Graphics g = getGraphics();			// get the graphics context for the panel
        g.setFont(myFont);						// take care of some simple font stuff
        myFontMetrics = g.getFontMetrics();
        nFontWidth = myFontMetrics.getMaxAdvance();
        nFontHeight = myFontMetrics.getHeight();
        g.setFont(myFontCap);					// set font info
    }

    //draw gameMenu
    private void drawMenu(Graphics g) {
        g.setColor(Color.white);
        g.setFont(myFont);

    }

    //Setting the font size graphics for the MENU
    // need to add illustrations
    private void initMenu() {
        Graphics g = getGraphics();			// get the graphics context for the panel
        g.setFont(myFont);						// take care of some simple font stuff
        myFontMetrics = g.getFontMetrics();
        nFontWidth = myFontMetrics.getMaxAdvance();
        nFontHeight = myFontMetrics.getHeight();
        g.setFont(myFontCap);					// set font info
    }


    private void displayGameMenu() {

        displayString = "WELCOME TO BRICK BREAKER";
        graphicsBack.drawString(displayString, (gameframe.getWidth() - myFontMetrics.stringWidth(displayString)) / 2, gameframe.getHeight() / 4
                + nFontHeight );

        displayString = "'S' to Start";
        graphicsBack.drawString(displayString, (gameframe.getWidth() - myFontMetrics.stringWidth(displayString)) / 2, gameframe.getHeight() / 4
                + nFontHeight + 120);

        displayString = "'SPACE BAR' to fire a LASER";
        graphicsBack.drawString(displayString, (gameframe.getWidth() - myFontMetrics.stringWidth(displayString)) / 2, gameframe.getHeight() / 4
                + nFontHeight + 160);

        displayString = "'Q' to Start";
        graphicsBack.drawString(displayString, (gameframe.getWidth() - myFontMetrics.stringWidth(displayString)) / 2, gameframe.getHeight() / 4
                + nFontHeight + 200);

    }


}

