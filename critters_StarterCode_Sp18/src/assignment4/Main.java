package assignment4;
/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Nikhil Kanzarkar
 * nk8357
 * 15466
 * Jack Hammett
 * jdh5529
 * 15466
 * Spring 2018
 */

import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.*;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name,
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) {
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }


        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */

        boolean endGame = false;
        //loop that continuosly asks for inputs from the user until quit is signified
        while(!endGame) {

            //prompts the user for an instruction and then splits it up if it is more than one word
            System.out.print("critters>");
            String input = kb.nextLine();
            String[] multInp = input.split(" ");


            //depending on the first word the user inputs - decides what to do
            if(multInp[0].equals("quit")) {
                endGame = true;
                break;
            }
            else if(multInp[0].equals("show")) {
                Critter.displayWorld();
            }
            else if(multInp[0].equals("step")) {
                if(multInp.length > 1){
                    int times = Integer.parseInt(multInp[1]);				//converts the input into how many time steps to run
                    for(int i = 0; i < times; i++) {
                        Critter.worldTimeStep();
                    }
                }
                else Critter.worldTimeStep();								//will only step once if no number was inputted
            }
            else if(multInp[0].equals("seed")) {
                try {
                    Critter.setSeed(Long.parseLong(multInp[1]));
                }
                catch(ArrayIndexOutOfBoundsException e) {
                    System.out.println("error processing: " + input);
                }
            }
            else if(multInp[0].equals("make")) {
                String className = "";
                try {
                    className = multInp[1];
                }
                catch(ArrayIndexOutOfBoundsException e) {

                }
                if(multInp.length>2) {
                    try {
                        int times = Integer.parseInt(multInp[2]);				//converts the input into how many critters to create of that type

                        for(int i = 0; i < times; i++) {
                            try {
                                Critter.makeCritter(className);
                            }
                            catch(InvalidCritterException e) {
                                System.out.println("error processing: " + input);
                            }
                        }
                    }
                    catch(NumberFormatException e) {
                        System.out.println("error processing: " + input);
                        break;
                    }
                }
                else {														//will only make one critter if no number was inputted
                    try {
                        Critter.makeCritter(className);
                    }
                    catch(InvalidCritterException e) {
                        System.out.println("error processing: " + input);
                    }
                }


            }
            else if(multInp[0].equals("stats")) {
                List<Critter> instance = new ArrayList<Critter>();
                boolean empty = false;
                try {
                    instance = Critter.getInstances(multInp[1]);
                    java.lang.reflect.Method method = null;
                    Class loadClass = null;

                    if(instance.size() == 0) {
                        //have to account for a subclass static runStats method in which there are no instances
                        try {
                            String updatedClass = "assignment4." + multInp[1];
                            loadClass = Class.forName(updatedClass);

                            method = loadClass.getMethod("runStats", List.class);

                        }
                        catch(ClassNotFoundException e) {
                            System.out.println("error processing: " + input);
                        }
                        catch(SecurityException e) {
                            System.out.println("error processing: " + input);
                        }
                        catch(NoSuchMethodException e) {
                            System.out.println("error processing: " + input);
                        }
                        //if statement to make sure if subclass doesn't have a runStats to skip the invoke and continue
                        try {
                            if(method != null && (method.getDeclaringClass() != Critter.class)) {
                                method.invoke(loadClass, instance);
                                empty = true;
                            }

                        }
                        catch (IllegalArgumentException e) {  }
                        catch (IllegalAccessException e) { }
                        catch (InvocationTargetException e) {  }
                    }
                    if(!empty) {
                        Critter.runStats(instance);
                        empty = false;
                    }
                }
                catch(InvalidCritterException e) {
                    System.out.println("error processing: " + input);
                }
                catch(IndexOutOfBoundsException e) {
                    System.out.println("error processing: " + input);
                }
                catch(NoClassDefFoundError e) {
                    System.out.println("error processing: " + input);
                }

            }
            else {
                System.out.println("invalid command: " + input);				//if the input is not recognized it will prompt the user and then ask for another
            }

        }



        System.out.flush();

    }
}
