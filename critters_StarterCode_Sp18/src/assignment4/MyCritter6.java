package assignment4;

public class MyCritter6 extends Critter {
	private boolean hasMoved= false;

	/**
	 * Constructor for the MyCritter1 critter. Sets the hasMoved variable to false.
	 */
	public MyCritter6()
	{
		hasMoved = false;
	}
	
	@Override
	/**
	 *
	 */
	public void doTimeStep() {
		run(getRandomInt(8));
	}

	@Override
	/**
	 *
	 */
	public boolean fight(String opponent) {
		if(!hasMoved) {
			run(getRandomInt(8));
		}
		return false;
	}

	@Override
	/**
	 * The one character representation of this critter which is used to show where it is on the grid map when printed.
	 */
	public String toString () {
		return "5";
	}
}


