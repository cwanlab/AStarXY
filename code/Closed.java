import hsvis.*;
import java.util.*;

/*
  // constructors
    public Closed(int n)
    public Closed(int n, int seed)
    public Closed(int n, Random R)
    public Closed(final long[] randvec)

    public boolean add(final Node node) // returns true if !contains()
    public boolean add(final int[] S)  // returns true if  !contains()
    public boolean contains(final Node node)
    public boolean contains(final int[] S)

 */

public class Closed {
    private static final int SEED = 0;

    private long[] RANDVEC;
    private HashSet<Long> H = new HashSet<Long>();

    public int size() { return(H.size()); }
    // constructors

    public Closed(long[] randvec) {
	RANDVEC = randvec;
    }

    public Closed(int n, Random R) {
	RANDVEC = new long[n];
	for(int i = 0 ; i < n ; i++) RANDVEC[i] = R.nextLong();
    }

    public Closed(int n, int seed) {
	Random R = ArgsUtils.myRandom(seed);
	RANDVEC = new long[n];
	for(int i = 0 ; i < n ; i++) RANDVEC[i] = R.nextLong();
    }

    public Closed(int n) {
	this(n, SEED);
    }

    //////// end constructors ////////

    // id assumes that the 0 <= S[i] <= n-1
    private long id(final int[] S) { 
	long id = 0;
	for(int i = 0 ; i < S.length ; i++) id += RANDVEC[S[i]];
	return(id);
    }

    public boolean add(final int[] S) {
	//	Mathematica.println("adding to C", S);
	long id = id(S);
	return(H.add(id));
    }

    public boolean contains(final int[] S) {
	long id = id(S);
	return(H.contains(id));
    }

    public boolean add(final Node node) { return(add(node.selection())); }
    public boolean contains(final Node node)
    { return(contains(node.selection())); }
}

