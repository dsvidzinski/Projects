//package dfinal.game.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

/**
 * Created by dsvid88 on 5/19/15.
 */

public class GameFrame extends JFrame {

    private JPanel contentPane;
    private BorderLayout borderLayout1 = new BorderLayout();


    //NOTE : my consturctor is a little different
    public GameFrame() {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
            //	initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Component initialization
    private void initialize() throws Exception {
        contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(borderLayout1);
    }

    @Override
    //Overridden so we can exit when window is closed (I dont need this)
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            System.exit(0);
        }
    }



}