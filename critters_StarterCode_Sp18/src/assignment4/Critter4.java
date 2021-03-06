package assignment4;
/* CRITTERS Critter4.java
 * EE422C Project 4 submission by
 * Nikhil Kanzarkar
 * nk8357
 * 15466
 * Jack Hammett
 * jdh5529
 * 15466
 * Spring 2018
 */

import assignment4.Critter.TestCritter;
import static assignment4.Params.*;


public class Critter4 extends TestCritter {
	
	private boolean hasMoved;
	/**
	 * Constructor for the MyCritter 7 Critter. Sets hasMoved to false.
	 */
	public Critter4() {
		hasMoved = false;
	}
	
	/**
	 * Getter for the hasMoved boolean variable.
	 * @return hasMoved
	 */
	public boolean gethasMoved() {
		return hasMoved;
	}
	
	
	@Override
	/**
	 * Handles what the critter does when during each time step run.
	 * If enough energy is present, it will try to reproduce twice and then run. If not, it will try to reproduce once or try to run, and if not, it won't move. 
	 */
	public void doTimeStep() {
		
		if(getEnergy() > (2*min_reproduce_energy + run_energy_cost)) {
			Critter4 childOne = new Critter4();
			Critter4 childTwo = new Critter4();
			reproduce(childOne, 0);
			reproduce(childTwo, 1);
			run(4);
			hasMoved = true;
		}
		else if(getEnergy() > min_reproduce_energy) {
			Critter4 child = new Critter4();
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
		run(Critter.getRandomInt(8));
		return false;
	}

	@Override
	/**
	 * The one character representation of this critter which is used to show where it is on the grid map when printed. 
	 */
	public String toString () {
		return "4";
	}
}
