package assignment4;

import java.util.*;
import static assignment4.Params.*;

public class MyCritter1 extends Critter.TestCritter {

    private boolean hasMoved;

    /**
     * Constructor for the MyCritter1 critter. Sets the hasMoved variable to false.
     */
    public MyCritter1() {
        hasMoved = false;
    }

    @Override
    /**
     * Handles what the critter does when during each time step run.
     * If the critter has a lot of energy it will try to run. Otherwise it will try to use its energy to reproduce.
     * If it doesn't have enough energy to run or to reproduce then it won't move.
     */
    public void doTimeStep() {

        if(getEnergy()> 70) {
            run(Critter.getRandomInt(8));
            hasMoved = true;
        }
        else if(getEnergy() > min_reproduce_energy){
            MyCritter1 child = new MyCritter1();
            reproduce(child, Critter.getRandomInt(8));
            hasMoved = false;
        }
        else hasMoved = false;
    }

    /**
     * Getter for the hasMoved boolean variable.
     * @return hasMoved
     */
    public boolean getHasMoved() {
        return hasMoved;
    }

    @Override
    /**
     * Handles what the critter does when it has to fight.
     * This critter is very cannabalistic as it will fight another of it's own kind if it has any energy.
     * If it isn't it's own kind it will still fight if it has a lot of energy. Otherwise it will try to walk.
     * @return always false
     */
    public boolean fight(String opponent) {
        try {
            if((Class.forName(opponent) == this.getClass()) && getEnergy() > 0) {
                return true;
            }
            else if(getEnergy() > 80) {
                return true;
            }
            else {
                if(!hasMoved) {
                    walk(Critter.getRandomInt(8));
                    hasMoved = true;
                }
                return false;
            }
        }
        catch(ClassNotFoundException e)
        {
            if(!hasMoved) {
                walk(Critter.getRandomInt(8));
                hasMoved = true;
            }
            return false;
        }

    }

    /**
     * The one character representation of this critter which is used to show where it is on the grid map when printed.
     */
    public String toString() {
        return "1";
    }

}
