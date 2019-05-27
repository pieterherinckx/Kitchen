import greenfoot.*;

import java.util.List;
import java.util.ArrayList;

/**
 * Mover. This is the base class for all other actors. In addition to the standard Actor
 * methods, it provides the ability to move and turn.
 * 
 * @author Pieter Herinckx
 * @version 1.0
 */
public class Mover extends Actor
{
    private static final int EAST = 0;
    private static final int WEST = 1;
    private static final int NORTH = 2;
    private static final int SOUTH = 3;

    private int direction;

    private static final double WALKING_SPEED = 1.0;
    private boolean movingRight = true;
    
    /**
     * Act - empty method. Actors have no default action.
     */
    public void act()
    {
    }
    
    /**
     * Return true if we can see an object of class 'clss' right where we are. 
     * False if there is no such object here.
     */
    public boolean canSee(Class clss)
    {
        Actor actor = getOneObjectAtOffset(0, 0, clss);
        return actor != null;        
    }
   
    /**
     * Try to eat an object of class 'clss'. This is only successful if there
     * is such an object where we currently are. Otherwise this method does
     * nothing.
     */
    public void eat(Class clss)
    {
        Actor actor = getOneObjectAtOffset(0, 0, clss);
        if(actor != null) {
            getWorld().removeObject(actor);
        }
    } 
    
    /**
     * Check whether a given key is currently pressed down.
     */
    public boolean isKeyDown(String keyName)
    {
        return Greenfoot.isKeyDown(keyName);
    }

    /**
     * Move one cell forward in the current direction.
     */
    public void move()
    {
        if (!canMove()) {
            return;
        }
        switch(direction) {
            case SOUTH :
                setLocation(getX(), getY() + 1);
                break;
            case EAST :
                setLocation(getX() + 1, getY());
                break;
            case NORTH :
                setLocation(getX(), getY() - 1);
                break;
            case WEST :
                setLocation(getX() - 1, getY());
                break;
        }
    }

    /**
     * Test if we can move forward. Return true if we can, false otherwise.
     */
    public boolean canMove()
    {
        World myWorld = getWorld();
        int x = getX();
        int y = getY();
        switch(direction) {
            case SOUTH :
                y++;
                break;
            case EAST :
                x++;
                break;
            case NORTH :
                y--;
                break;
            case WEST :
                x--;
                break;
        }
        // test for outside border
        if (x >= myWorld.getWidth() || y >= myWorld.getHeight()) {
            return false;
        }
        else if (x < 0 || y < 0) {
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * Turn in a random direction.
     */
    public void turnRandom()
    {
        // get a random number between 0 and 3...
        int turns = Greenfoot.getRandomNumber(4);
        
        // ...an turn left that many times.
        for(int i=0; i<turns; i++) {
            turnLeft();
        }
    }

    /**
     * Turns towards the left.
     */
    public void turnLeft()
    {
        switch(direction) {
            case SOUTH :
                setDirection(EAST);
                break;
            case EAST :
                setDirection(NORTH);
                break;
            case NORTH :
                setDirection(WEST);
                break;
            case WEST :
                setDirection(SOUTH);
                break;
        }
    }

    /**
     * Sets the direction we're facing.
     */
    public void setDirection(int direction)
    {
        this.direction = direction;
        switch(direction) {
            case SOUTH :
                setRotation(90);
                break;
            case EAST :
                setRotation(0);
                break;
            case NORTH :
                setRotation(270);
                break;
            case WEST :
                setRotation(180);
                break;
            default :
                break;
        }
    }

    /**
     * Makes the actor move across the field automatically.
     */
    public void autoMove()
    {
        if(canMove()) {
            move();
            if(Greenfoot.getRandomNumber(100)/3==0){
                turnRandom();
            }
        }
        else {
            turnRandom();
        }
    }
    
}