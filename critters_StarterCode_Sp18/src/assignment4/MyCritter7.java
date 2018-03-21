package assignment4;

import static assignment4.Params.*;


public class MyCritter7 extends Critter {

	private boolean hasMoved;
	/**
	 * Constructor for the MyCritter 7 Critter. Sets hasMoved to false.
	 */
	public MyCritter7() {
		hasMoved = false;
	}

	/**
	 * Getter for the hasMoved boolean variable.
	 * @return hasMoved
	 */
	public boolean hasMoved() {
		return hasMoved;
	}


	@Override
	/**
	 * Handles what the critter does when during each time step run.
	 * If enough energy is present, it will try to reproduce twice and then run. If not, it will try to reproduce once or try to run, and if not, it won't move.
	 */
	public void doTimeStep() {

		if(getEnergy() > (2*min_reproduce_energy + run_energy_cost)) {
			MyCritter7 childOne = new MyCritter7();
			MyCritter7 childTwo = new MyCritter7();
			reproduce(childOne, 0);
			reproduce(childTwo, 1);
			run(4);
			hasMoved = true;
		}
		else if(getEnergy() > min_reproduce_energy) {
			MyCritter7 child = new MyCritter7();
			reproduce(child,0);
			hasMoved = false;
		}
		else if(getEnergy() > run_energy_cost) {
			run(0);
			hasMoved = true;
		}
		else hasMoved = false;

	}

	@Override
	/**
	 * Handles what the critter does when it has to fight.
	 * This critter is extremely passive and will not fight, rather it will try to run.
	 * @return always false
	 */
	public boolean fight(String opponent) {
		if(!hasMoved) {
			run(Critter.getRandomInt(8));
			hasMoved = true;
		}
		return false;
	}

	@Override
	/**
	 * The one character representation of this critter which is used to show where it is on the grid map when printed.
	 */
	public String toString () {
		return "7";
	}
}
