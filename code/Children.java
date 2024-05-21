import hsvis.*;
import java.util.*;

/*
    // return subset_xi such that {parent,xi} are not in C
    // also insert all candidates to C 
    public static int[] pruned_subset_xi
	(final int[] initial_range, final int[] parent, Closed C)
 */

public class Children {
    // return subset_xi such that {parent,xi} are not in C
    // also insert all candidates to C
    // i can be only from initial_range.
    public static int[] pruned_subset_xi
	(final int[] initial_range, final int[] parent, Closed C)
    {
		int[] p = (parent == null) ? new int[0] : parent;
		// p != null
		int[] range_p = Subsets.setminus_sorted_unsorted(initial_range, p);
		// xi are the elements of range_p
		ArrayList<Integer> not_in_C = new ArrayList<Integer>();
		for(int i = 0 ; i < range_p.length ; i++) {
		    int xi = range_p[i];
		    int[] child_subset = Subsets.append(p, xi);
		    boolean open = C.add(child_subset);
		    if(open) not_in_C.add(xi);
		}
		return(Subsets.intArray(not_in_C));
    }
}
