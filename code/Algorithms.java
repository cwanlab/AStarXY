import hsvis.*;
import java.util.*;


/*
  search_info = {goal_node, post_bound}

  public static String known_algorithms()

  // public static Object[] run
	(String alg,
	 int k,
	 int n;
	 int[] range;
	 double eps_wAStar,
	 final int[] iselect,
	 Heuristic H,
	 boolean p
	 )
 */

public class Algorithms {
    
    public static String known_algorithms() {
		String algs ="xy, xy_g";
		return(algs);
    }
	
    private static boolean infinite_epsilon(String alg) {
	    return(alg.contains("_g"));
    }

    // return value:
    //  search_info ={goal_node, post_bound}
    private static Object[]  astar_variant
	(String alg,
	 int k,
	 int n,
	 int[] range,
	 double eps_wAStar,
	 final int[] iselect,
	 Heuristic H,
	 boolean p
	 )
    {
		// set epsilon for wastar
		if(infinite_epsilon(alg)) eps_wAStar = -1; // viewed as infinity
		Node dummy = new Node(eps_wAStar); // set epsilon

		// search
		AStarAlgorithm astar = new AStarAlgorithm(H,k,n,range,p); 
		Object[] search_info = astar.search_iteratively(iselect);
		
		return(search_info);
    }

    // return search_info =
    //         {goal_node, post_bound}
    public static Object[] run
	(String alg,
	 int k,
	 int n,
	 int[] range,
	 double eps_wAStar,
	 final int[] iselect,
	 Heuristic H,
	 boolean p
	 )
    {
		// k < X.rows
		Object[] search_info = astar_variant(alg, k, n, range, eps_wAStar, iselect, H, p);
		return(search_info);
    }
}
