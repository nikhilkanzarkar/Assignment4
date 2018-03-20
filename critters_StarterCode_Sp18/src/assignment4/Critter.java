package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.ListIterator;

import static assignment4.Params.*;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;

	private void moveHelper(int speed, int direction)
    {
        if(direction == 0)
        {
            int temp = x_coord + speed;
            if(temp >= world_width)           //check to see if its >=
            {
                x_coord = 0;
            }
            else x_coord = temp;
        }
        if(direction == 1)
        {
            int temp = x_coord + speed;
            int temp2 = y_coord + speed;
            if(temp >= world_width)           //check to see if its >=
            {
                x_coord = 0;
            }
            else x_coord = temp;
            if(temp2 >= world_height)
            {
                y_coord = 0;
            }
            else y_coord = temp2;

        }
        if(direction == 2)
        {
            int temp = y_coord + speed;
            if(temp >= world_height)           //check to see if its >=
            {
                y_coord = 0;
            }
            else y_coord = temp;
        }
        if(direction == 3)
        {
            int temp = x_coord - speed;
            int temp2 = y_coord + speed;
            if(temp < 0)           //check to see if its >=
            {
                x_coord = world_width-1;
            }
            else x_coord = temp;
            if(temp2 >= world_height)
            {
                y_coord = 0;
            }
            else y_coord = temp2;
        }
        if(direction == 4)
        {
            int temp = x_coord - speed;
            if(temp < 0)           //check to see if its >=
            {
                x_coord = world_width-1;
            }
            else x_coord = temp;
        }
        if(direction == 5)
        {
            int temp = x_coord - speed;
            int temp2 = y_coord - speed;
            if(temp < 0)           //check to see if its >=
            {
                x_coord = world_width-1;
            }
            else x_coord = temp;
            if(temp2 < 0)
            {
                y_coord = world_height-1;
            }
            else y_coord = temp2;
        }
        if(direction == 6)
        {
            int temp = y_coord - speed;
            if(temp < 0)           //check to see if its >=
            {
                y_coord = world_height-1;
            }
            else y_coord = temp;
        }
        if(direction == 7)
        {
            int temp = x_coord + speed;
            int temp2 = y_coord - speed;
            if(temp >= world_width)           //check to see if its >=
            {
                x_coord = 0;
            }
            else x_coord = temp;
            if(temp2 < 0)
            {
                y_coord = world_height-1;
            }
            else y_coord = temp2;
        }
    }
	
	protected final void walk(int direction) {
        energy = energy-walk_energy_cost;
	    moveHelper(1,direction);

	}
	private boolean getHasMoved()
    {
        return false;
    }
	protected final void run(int direction) {
        moveHelper(2,direction);
        energy = energy-run_energy_cost;
	}
	
	protected final void reproduce(Critter offspring, int direction) {
	    //cannot reproduce
        if(getEnergy() < min_reproduce_energy) {
            return;
        }
        //divdes the energy by 2 as it is reproducing
        offspring.energy = getEnergy()/2;

        if(energy%2 != 0) {
            energy = energy/2 + 1;
        }
        else energy = energy/2;
        offspring.walk(direction);
        babies.add(offspring);  //adds babies to the List of offspring
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		try{
		    Class loadClass = Class.forName(critter_class_name);
            Constructor makeCrit = loadClass.getConstructor();
            Critter newCrit = (Critter)makeCrit.newInstance();
            newCrit.energy = start_energy;
            newCrit.x_coord = getRandomInt(world_width);
            newCrit.y_coord = getRandomInt(world_height);
            population.add(newCrit);

        }
        catch(ClassNotFoundException e)
        {
		    throw new InvalidCritterException(critter_class_name);
        }
        catch (IllegalAccessException e)
        {
            throw new InvalidCritterException(critter_class_name);
        }
        catch (InstantiationException e)
        {
            throw new InvalidCritterException(critter_class_name);
        }
        catch (NoSuchMethodException e)
        {
            throw new InvalidCritterException(critter_class_name);
        }
        catch (InvocationTargetException e)
        {
            throw new InvalidCritterException(critter_class_name);
        }
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
    public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
        List<Critter> result = new java.util.ArrayList<Critter>();
        try {
            Class loadClass = Class.forName(critter_class_name);
            for (int i = 0; i < population.size(); i++) {
                if (population.get(i).getClass() == loadClass ) {
                    result.add(population.get(i));
                }
            }
            return result;
        }
        catch(ClassNotFoundException e)
        {
            throw new InvalidCritterException(critter_class_name);
        }
    }
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
	    population.clear();
		babies.clear();
	}
	private static boolean alreadyOccupied(int x_coord, int y_coord)
    {
        int same = 0;
        for(int i = 0;i<population.size();i++)
        {
            if(population.get(i).x_coord == x_coord && population.get(i).y_coord == y_coord)
            {
                same++;
            }
        }
        if(same > 1)
        {
            return true;
        }
        else
            return false;
    }
	
	public static void worldTimeStep() {
	    int tempXCoord;
	    int tempYCoord;
		//makes each critter do a timestep
	    for(int i = 0;i<population.size();i++)
        {
            population.get(i).doTimeStep();
        }
        //deletes any critter that has moved but has no/negative energy now.
        ListIterator<Critter> iter= population.listIterator();
       while(iter.hasNext())
       {
           if(iter.next().energy < 0)
           {
               iter.remove();
           }
       }
        //encounters checking
        for(int i =0;i<population.size();i++)
        {
            for(int j =i+1;j<population.size();j++)
            {
                if(population.get(i).x_coord == population.get(j).x_coord && population.get(i).y_coord == population.get(j).y_coord )
                {
                    //checking to see if i will fight j
                    if(population.get(i).fight(population.get(j).toString()))
                    {
                        //i wants to fight j but is waiting on j to respond
                        if(population.get(j).fight(population.get(i).toString()))
                        {
                            //both critters want to fight
                            int iRoll = Critter.getRandomInt(population.get(i).energy);
                            int jRoll = Critter.getRandomInt(population.get(j).energy);

                            if(iRoll > jRoll)
                            {
                                population.get(i).energy += population.get(j).energy/2;
                                population.remove(j);
                            }
                            else {
                                population.get(j).energy += population.get(i).energy/2;
                                population.remove(i);
                            }
                        }
                        else
                        {

                            tempXCoord = population.get(j).x_coord;
                            tempYCoord = population.get(j).y_coord;
                            population.get(j).run(getRandomInt(8));
                            if(alreadyOccupied(population.get(j).x_coord,population.get(j).y_coord))
                            {
                                population.get(j).x_coord = tempXCoord;
                                population.get(j).y_coord = tempYCoord;
                            }
                            if(population.get(j).energy <= 0)
                            {
                                population.remove(population.get(j));
                            }
                        }
                    }
                    if(population.get(j).fight(population.get(i).toString()))
                    {
                        if(population.get(i).getHasMoved())
                        {
                            population.get(j).energy += population.get(i).energy/2;
                            population.remove(i);
                        }
                        else {
                            population.get(i).run(getRandomInt(8));
                        }
                    }

                }
            }
        }

        for (int i = 0; i < refresh_algae_count; i++) {
            TestCritter addAlg = new Algae();
            addAlg.setEnergy(start_energy);
            addAlg.setX_coord(getRandomInt(world_width));
            addAlg.setY_coord(getRandomInt(world_height));
            population.add(addAlg);
        }

        if(babies.size()!=0) {
            for(int i = 0; i<babies.size();i++) {
                population.add(babies.get(i));
                babies.remove(i);
            }
        }
    }

	public static String checkOccupancy(int x, int y){
	    for(int i = 0; i < population.size(); i++){
	        if(population.get(i).x_coord == x && population.get(i).y_coord == y){
	            return population.get(i).toString();
            }
        }
        return " ";
    }




	public static void displayWorld() {
	    System.out.print("+");
	    for(int i = 0; i<world_width;i++){
	        System.out.print("-");
        }
        System.out.println("+");
	    for(int i =0; i < world_height;i++){
	        System.out.print("|");
	        for(int j = 0; j < world_width; j++){
	            System.out.print(checkOccupancy(j,i));
            }
            System.out.println("|");
        }
        System.out.print("+");
        for(int i = 0; i<world_width;i++){
            System.out.print("-");
        }
        System.out.println("+");
	}
}
