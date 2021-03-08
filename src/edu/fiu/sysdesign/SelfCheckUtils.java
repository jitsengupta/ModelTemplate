/**
 This is a flat package that will includes some classes
 * and interfaces for the Systems Analysis and Design class
 * that anyone can build on.
 * 
 * To use classes/interfaces in this package, you may want to
 * do an
 * 
 * import edu.fiu.sysdesign.*;
 * 
 * At the top of your class.
 */
package edu.fiu.sysdesign;

/**
 * This will essentially be a class of Static methods that you can 
 * use to test anything in your own classes. I will be adding more
 * features to this as time goes on.
 * 
 * @author asengupt
 *
 */
public class SelfCheckUtils {
	
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	/**
	 * Just for testing items in this class. Make sure your application
	 * does not use this main method for running your own applications!
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SelfCheckCapable item1 = new SelfCheckCapable() {
			
			@Override
			public boolean selfCheck() {
				// TODO Auto-generated method stub
				return SelfCheckUtils.randomCheck(0.25);
			}
			
			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "Test component";
			}
		};
		
		System.out.println(SelfCheckUtils.basicSelfCheckRunner(item1));

	}
	
	/**
	 * Runs a self-check and returns a prettified version of the result
	 * Note that the colors may not show correctly in some shells.
	 * 
	 * DO NOT USE THIS AS THE ACTUAL SELF-CHECK! This will call self-check
	 * of the item passed in, so if you call it from the self-check method
	 * you will end up creating an infinite recursion!
	 * 
	 * @param item the item to be tested
	 * @return the colorized version of the run. Colors may not show in all terminals
	 */
	public static String basicSelfCheckRunner(SelfCheckCapable item) {
		if (System.console() != null && System.getenv().get("TERM") != null) {
			return "Checking " + item.getName() + "... " +
					(item.selfCheck() ? ANSI_GREEN + "check ok" + ANSI_RESET
							: ANSI_RED + "FAILED!" + ANSI_RESET);
		} else {
			return "Checking " + item.getName() + "... " +
					(item.selfCheck() ? "check ok" : "FAILED!");
		}
	}


	/**
	 * Super simple random check utility - simply calls a random function
	 * and returns true for half the cases, and false for half.
	 * 
	 * To call this, you can simply do a 
	 * 
	 * return SelfCheckUtils.randomCheck();
	 * 
	 * @return true for half the cases, false otherwise
	 */
	public static boolean randomCheck() {
		return randomCheck(0.5); 
	}
	
	/**
	 * Another check utility - simply calls a random function
	 * and returns true for half the cases, and false for half.
	 * 
	 * To call this, you can simply do a 
	 * return SelfCheckUtils.randomCheck(0.25); // 25% chance of failure
	 * 
	 * @param probability - the probability of a failure - send as a 
	 * value between 0 and 1. 1 means 100% probability of failure, 
	 * 0 means it will always be successful.
	 * @return true for half the cases, false otherwise
	 */
	public static boolean randomCheck(double probability) {
		return Math.random() > probability; 
	}
	
}
