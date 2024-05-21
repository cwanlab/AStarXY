import hsvis.*;
import java.util.*;

/*
    public static Node f_min_node(final Fringe fringe)
    public static double post_bound(double g_ss, final Fringe fringe)
    public static double post_bound(double g_ss, final Node f_min_node)
 */

public class Bounds {

    public static Node f_min_node(final Fringe fringe) {
	if(fringe == null) return(null);
	Iterator<Node> iterator = fringe.iterator();
	double f_min = Double.POSITIVE_INFINITY;
	Node f_min_node = null;
	while(iterator.hasNext()) {
	    Node n = iterator.next();
	    double[] fg = n.fg();
	    double f = fg[0];
	    if(f < f_min) {
		f_min = f;
		f_min_node = n;
	    }
	}
	return(f_min_node);
    }

    // bound = g_ss - f_min
    public static double post_bound(double g_ss, final Fringe fringe) {
	Node f_min_node = f_min_node(fringe);
	return(post_bound(g_ss, f_min_node));
    }

    public static double post_bound(double g_ss, final Node f_min_node) {
	double post_bound;
	if(f_min_node == null) post_bound = 0;
	else {
	    double f_min_value = f_min_node.fg()[0];
	    post_bound = g_ss - f_min_value;
	    if(post_bound < 0) post_bound = 0;
	}
	return(post_bound);
    }
    
}
