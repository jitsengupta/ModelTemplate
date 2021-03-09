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

import java.util.ArrayList;
import java.util.List;

/**
 * This will essentially be a class of Static methods that you can 
 * use to test anything in your own classes. I will be adding more
 * features to this as time goes on.
 * 
 * @author asengupt (Arijit Sengupta - FIU)
 *
 */
public class SelfCheckUtils {
	
	/** reset color */ public static final String ANSI_RESET = "\u001B[0m"; 
	/** ansi black */ public static final String ANSI_BLACK = "\u001B[30m"; 
	/** ansi red */ public static final String ANSI_RED = "\u001B[31m"; 
	/** ansi green */ public static final String ANSI_GREEN = "\u001B[32m"; 
	/** ansi yellow */ public static final String ANSI_YELLOW = "\u001B[33m"; 
	/** ansi blue */ public static final String ANSI_BLUE = "\u001B[34m"; 
	/** ansi purple */ public static final String ANSI_PURPLE = "\u001B[35m";
	/** ansi cyan */ public static final String ANSI_CYAN = "\u001B[36m"; 
	/** ansi white */ public static final String ANSI_WHITE = "\u001B[37m"; 

	/**
	 * Just for testing items in this class. Make sure your application
	 * does not use this main method for running your own applications!
	 * 
	 * See examples of how to use the methods of this class.
	 * 
	 * @param args n/a
	 */
	public static void main(String[] args) {
		SelfCheckCapable item1 = new SimpleComponent("Test component 1");
		SelfCheckCapable item2 = new SimpleComponent("Test component 2");
		SimpleComponent[] l1 = new SimpleComponent[5];
		l1[0] =(new SimpleComponent("Test component 3"));
		l1[1] =(new SimpleComponent("Test component 4"));
		l1[2] =(new SimpleComponent("Test component 5"));
		l1[3] =(new SimpleComponent("Test component 6"));
		l1[4] = (new SimpleComponent("Test component 7"));

		List<SimpleComponent> l2 = new ArrayList<SimpleComponent>();
		l2.add(new SimpleComponent("Test component 8"));
		l2.add(new SimpleComponent("Test component 9"));
		l2.add(new SimpleComponent("Test component 10"));
		l2.add(new SimpleComponent("Test component 11"));
		checkComponents(item1, item2, l1, l2);
	}
	
	/**
	 * Runs a self-check and pretty-print the result of the check.
	 * Note that the colors may not show correctly in some terminals.
	 * 
	 * DO NOT USE THIS AS THE ACTUAL SELF-CHECK method! 
	 * This will call self-check method
	 * of the item passed in, so if you call it from the self-check method
	 * you will end up creating an infinite recursion!
	 * 
	 * However, this can be called from the runSelfCheck implementation
	 * 
	 * To use this, create an instance of an object that implements SelfCheckCapable
	 * Then call this method from runSelfCheck (or elsewhere) as follows:
	 * 
	 * public class Test implements SelfCheckCapable ....
	 * 
	 * Test t = new new Test(...);
	 * 
	 * From outside the Test class: 
	 *    SelfCheckUtils.basicSelfCheckRunner(t);
	 *    
	 * From inside the Test class (e.g. from runSelfCheck):
	 *    SelfCheckUtils.basicSelfCheckRunner(this);
	 *    
	 * @param item the item to be tested
	 * @return the colorized version of the run. Colors may not show in all terminals
	 */
	public static boolean basicSelfCheckRunner(SelfCheckCapable item) {
		boolean result = item.selfCheck();
		System.out.println("Checking " + item.getComponentName() + "... " + prettify(result));
		return result;
	}

	/**
	 * A basic checkComponents method that tests all componets that
	 * are provided as parameters.
	 * 
	 * Again, do not use this from the selfCheck method, but can be used
	 * from the runSelfCheck method to check all components in the class.
	 * 
	 * SelfCheckUtils.checkComponents(wheels, sensors, camera);
	 * 
	 * @param items A comma separated list of items - can be any object, but
	 * only those that implement SelfCheckCapable will be tested.
	 * Supports Arrays of SelfCheckCapable objects
	 * Supports Lists of SelfCheckCapable objects
	 * @return a single boolean result telling you if the final test was successful
	 */
	public static boolean checkComponents(Object ...items) {
		int count = 0;
		boolean result = true;
		for (Object i:items) {
			if (i instanceof SelfCheckCapable) {
				count++;
				result &= basicSelfCheckRunner((SelfCheckCapable)i);
			} else if (i instanceof List) {
				for (Object l:(List<?>)i) {
					if (l instanceof SelfCheckCapable) {
						count++;
						result &= basicSelfCheckRunner((SelfCheckCapable)l);
					}
				}
			} else if (i instanceof Object[]) {
				for (Object l:(Object[])i) {
					if (l instanceof SelfCheckCapable) {
						count++;
						result &= basicSelfCheckRunner((SelfCheckCapable)l);
					}
				}				
			}
		}
		System.out.println("Tested " + count + " components... " + prettify(count > 0 && result));
		return (count > 0 && result);
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
	 * @param failprobability - the probability of a failure - send as a 
	 * value between 0 and 1. 1 means 100% probability of failure, 
	 * 0 means it will always be successful.
	 * @return true for half the cases, false otherwise
	 */
	public static boolean randomCheck(double failprobability) {
		return Math.random() > failprobability; 
	}
	
	/**
	 * An internal method to "prettify" the test result
	 * If the test result is false, a red FAILED string is returned
	 * If the test result is true, a green check ok string is returned
	 * If terminal is not set, no colors are applied.
	 * @param result the result of the self-check
	 * @return prettified string (check ok / FAILED)
	 */
	private static String prettify(boolean result) {
		if (System.console() != null && System.getenv().get("TERM") != null) {
			return ( result ? ANSI_GREEN + "check ok" + ANSI_RESET
							: ANSI_RED + "FAILED!" + ANSI_RESET);
		} else return (result ? "check ok" : "FAILED!");
	}
	
	/**
	 * A simple internal class showing how to use the Self-check process
	 * @author asengupt
	 *
	 */
	private static class SimpleComponent implements SelfCheckCapable {
		/** name of the component */ String name;
		/**
		 * Simple constructor to create a component
		 * @param name name of the component
		 */
		public SimpleComponent(String name) {
			this.name = name;
		}
		
		@Override
		public boolean selfCheck() {
			// TODO Auto-generated method stub
			return SelfCheckUtils.randomCheck(0.1);
		}
		
		@Override
		public String getComponentName() {
			// TODO Auto-generated method stub
			return name;
		}

		@Override
		public boolean runSelfCheck() {
			// TODO Auto-generated method stub
			return SelfCheckUtils.basicSelfCheckRunner(this);
		}
	}
	
}
