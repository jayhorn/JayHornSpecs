package jhspec;

import java.util.HashMap;
import java.util.Collection;

/*
 * @author rodykers
 */
public class RC {

  public static HashMap<Object,Integer> costMap = 
    new HashMap<Object,Integer>();

  public static int MethodCost(Object o) {
    return costMap.get(o);
  }

  // ... similar cost methods for other scopes

  public static int size(Iterable<?> i) {
    int size = 0;
    for (Object o : i)
      size++;
    return size;
  }

  public static int size(String s) {
    return s.length();
  }

  public static int size(boolean ar[]) {
    return ar.length;
  }

  public static int size(char ar[]) {
    return ar.length;
  }

  public static int size(byte ar[]) {
    return ar.length;
  }

  public static int size(short ar[]) {
    return ar.length;
  }

  public static int size(int ar[]) {
    return ar.length;
  }

  public static int size(long ar[]) {
    return ar.length;
  }

  public static int size(float ar[]) {
    return ar.length;
  }

  public static int size(double ar[]) {
    return ar.length;
  }

  public static int size(Object ar[]) {
    return ar.length;
  }

  public static int size(Collection<?> c) {
    return c.size();
  }

  // ... more size methods

  public static int inc(Object o, int cost) {
    return costMap.put(o, costMap.get(o) + cost);
  }
  
  public static int inc(Object o) {
	    return inc(o, 1);
	  }

  public static int dec(Object o, int cost) {
    return costMap.put(o, costMap.get(o) - cost);
  }
  
  public static int dec(Object o) {
	    return dec(o, 1);
  }

  public static int getCurrentCost(Object o) {
    return costMap.get(o);
  }

  public static int setCost(Object o, int cost) {
    return costMap.put(o, cost);
  }

  // ... more cost update methods

  public static void contract(boolean b) {
	  assert b;
  }
}
