package assignment4;

import java.util.List;

public class MyCritter3 extends Critter{
    private boolean hasMoved = false;
    @Override
    public void doTimeStep() {
        if(getRandomInt(2) == 0) {
            run(0);
            hasMoved = true;
        }
        else {

            hasMoved = false;
        }
    }
    public boolean getHasMoved()
    {
        return hasMoved;
    }
    @Override
    public boolean fight(String opponent)
    {
        if (getEnergy() > 0) return true;
        return false;
    }
    public String toString() {
        return "3";
    }
}
