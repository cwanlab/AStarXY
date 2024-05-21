import hsvis.*;
import java.util.*;

/*
  // each node has fg, f_prime.
  // f' is computed by f' = f + epsilon g
  // epsilon is static
  // epsilon == -1 means epsilon == infinity (f' = g)
  // f is used for comparing bound

  public static double f_prime(final double[]fg)
  public double f_prime()


  // constructors
  
  // must be called before any other constructor
  public Node(double epsilon)
  
  public Node(final int[]S, final double[] fg)

  // access:
  public int[] selection()
  public int selection_length()
  public double f_prime()
  public double[] fg()
  public double epsilon()

  // printing for debugging
  public void println()
  public void println(String text)
  public static void println(String comment, final ArrayList<Node> L)

 */

public class Node 
    implements Comparator<Node>, Comparable<Node> 
{

    private static double EPSILON = 0; // value -1 means f'=g

    private int[] S; // indices of selection
    private double[] fg;
    private double f_prime;

    public static double f_prime(final double[]fg) {
	    if(EPSILON==-1) return(fg[1]);
	    else return(fg[0] + EPSILON * fg[1]);
    }

    // constructors
    public Node(double epsilon) {this.EPSILON = epsilon;}

    public Node(final int[]S, final double[] fg) {
	    this.S = (S == null) ? new int[0] : S;
	    this.fg = fg;
	    this.f_prime = f_prime(fg);
    }

    // acces
    public int selection_length() {return(this.S.length);}
    public int[] selection(){return(this.S);}
    public double f_prime() { return(f_prime); }
    public double[] fg() { return(fg); }
    public double epsilon() { return(this.EPSILON); }

    // comparator
    public int compare(Node n1, Node n2) {
	    double v1 = n1.f_prime();
	    double v2 = n2.f_prime();
	    // the smaller f' the better
	    if(v1 < v2) return(-1);
	    else if(v1 > v2) return(1);
	    // v1 == v2
	    // the larger |S| the better
	    else if(n1.S.length > n2.S.length) return(-1);
	    else if(n1.S.length < n2.S.length) return(1);
	    // length n1.S == length n2.S
	    // the smaller g the better
	    else if(n1.fg[1] < n2.fg[1]) return(-1);
	    else if(n1.fg[1] > n2.fg[1]) return(1);
	    else return(0);
    }
    public int compareTo(Node n2) {
	    return(compare(this, n2));
    }

    /////////////////////////////////////////////////////

    // printing function

    private static void print(String comment, final int[] S){
	    if( comment != null) System.out.print(comment + " ");
	    System.out.print("(");
	    int size = S.length;
	    for(int i = 0 ; i < size-1 ; i++) 
	      System.out.print(S[i] + ", ");
	    if(size > 0) System.out.print(S[size-1]);
	    System.out.print(")");
    }

    public void println() {
    	print("",this.S);
    	System.out.printf(", f=%g, g=%g, f'=%g\n",
    			  this.fg[0], this.fg[1], f_prime()); 
    }

    public void println(String comment) {
    	print(comment, this.S);
    	System.out.printf(", f=%g, g=%g, f'=%g\n",
    			  this.fg[0], this.fg[1], f_prime()); 
    }

    public static void println(String comment, final ArrayList<Node> L) {
    	System.out.println(comment);
    	if(L != null)
    	    for(Node n : L) n.println();
    }
}
