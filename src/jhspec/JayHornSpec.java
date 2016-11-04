package jhspec;

/**
 * @author Rody Kersten
 *
 * Library for writing specs for JayHorn. Has methods to havoc various types, 
 * plus to assume a boolean expression. The idea is that calls to these
 * methods are replaced by actual havocs and assumptions by JayHorn.
 */
public class JayHornSpec {

	public static boolean havocBool() {
		return false; 
	}
	
	public static int havocInt() {
		return 0; 
	}
	
	public static long havocLong() {
		return 0L; 
	}
	
	public static short havocShort() {
		return 0; 
	}
	
	public static byte havocByte() {
		return 0; 
	}
	
	public static char havocChar() {
		return '\u0000';
	}	
	
	public static float havocFloat() {
		return 0.0f; 
	}
	
	public static double havocDouble() {
		return 0.0d;
	}
	
	public static Object havocRef() {
		return null;
	}
	
	public static void assume(boolean b) {
	}
}
