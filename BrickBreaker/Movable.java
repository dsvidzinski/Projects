//package dfinal.game.model;

import java.awt.*;

public interface Movable {
    //for the game to draw
    void move();

    void draw(Graphics g);

    //for collisions
    //if two objects are both friends or both foes, then nothing happens, otherwise exlode both
    //the friend type may be DEBRIS, in which case it is inert
    //public byte getFriend();
    //when a foe explodes, your points increase
    int points();


    Point getCenter();

    int getRadius();

    //for short-lasting objects only like powerUps, bullets, special weapons and UFOs
    //controls nExpiry that clicks down to expire, then the object should remove itself
    //see Bullet class for how this works.
    void expire();

    //for fading objects
    void fadeInOut();

} //end Movable
