import hsvis.*;
import java.io.*;
import java.util.*;

 /*
    Approximate matrix Y in terms of k selected columns from matrix X.
    iselect - force these to be the initial selection
    
    @paper: Heuristic Search for Approximating One Matrix in Terms of Another Matrix, IJCAI 2021
    @authors: Guihong Wan, Haim Schweitzer, 9/10/2020.
 */

public class AStar {
    private static long START_TIME;

    public static void main(String[] args) {
	try {
	    StandardArgs Args = new StandardArgs();

	    Args.add("help", "flag", "", "Prints usage help");
	    Args.add("algorithm", "String", "xy", 
		     "one of: " + Algorithms.known_algorithms());
	    Args.add("eps", "String", "0", 
		     "epsilon value for wastar. f = l + e u. 0:f=l, -1: f=u");
	    Args.add("X", "String", null, DataFile_cmdline.explain("X"));
	    Args.add("Y", "String", null, DataFile_cmdline.explain("Y"));
	    Args.add("k", "int", null,
		     "the desired  number of columns to be selected.");
	    Args.add("iselect", 
		     "String", "null", "RangeList of initial selection");
	    Args.add("progress", "boolean", "yes", "yes or no");

	    Args.setArgs("AStar", args);
        
        String alg = Args.stringValue("algorithm");
	    double eps_wAStar = getepsilon(Args.stringValue("eps"));
	    
	    // read X
	    Object[] X_info = DataFile_cmdline.read_transpose(Args.stringValue("X"),null);
	    DataFile X = (DataFile)X_info[0];
	    int nX = X.rows();
	    int mX = X.cols();
	    
	    // read Y
	    Object[] Y_info = DataFile_cmdline.read_transpose(Args.stringValue("Y"), null);
	    DataFile Y = (DataFile)Y_info[0];
	    int nY = Y.rows();
	    int mY = Y.cols();

	    System.out.println("mX:nX="+mX+":"+nX);
	    System.out.println("mY:nY="+mY+":"+nY);
	    if( mX != mY)
	    	Errors.errexitNoStack("mX should be same as mY.");

	    // k
	    int k = Args.intValue("k");
	    int[] iselect = RangeList.selection(Args.stringValue("iselect"), nX);
	    // null means no initial selection
	    if(iselect == null) iselect = new int[0];

	    if(k <= 0)
	    	Errors.errexitNoStack("Nothing to be selected?");
	    if((k+iselect.length) >= nX)
	    	Errors.errexitNoStack("all points are to be selected?");

	    int[] range = DuplicateRows.uniques(X, null, iselect);

	    boolean p = Args.booleanValue("progress");
	    //////// finished reading arguments ////////////



	    START_TIME = System.nanoTime();

	    // compute H: initial eigendecomposition
		Heuristic H  = new Heuristic(X, Y, k, p);
		System.gc();


		// search
		// START_TIME = System.nanoTime();// search time
		
	    Object[] search_info = Algorithms.run(alg, k, nX, range, eps_wAStar, iselect, H, p);

	    //print results
        System.out.println("=============================================");
        progress(p, "finish searching");

	    Node goal_node = (Node) search_info[0];
	    double goal_node_value = goal_node.fg()[0];
	    double post_bound = (Double) search_info[1];
        
        goal_node.println("solution:");
	    System.out.println("bound = " + post_bound
			 +", normalized bound = " + post_bound/goal_node_value);
	    
        
        System.out.println("error = " + goal_node_value
			 +", percentage error = " + (goal_node_value/H.trace())*100);

	    System.out.println("mX:nX = "+mX+":"+nX);
	    System.out.println("mY:nY = "+mY+":"+nY);
	    System.out.println("=============================================");
	}
	finally {}
    }

    private static double getepsilon(String arg) {
		// epsilon value for wastar.
		double eps = Double.parseDouble(arg);
		if(eps < 0 && eps != -1)
		    Errors.errexit("eps range is [0-infinity). It can't be " + eps);
		return(eps);
    }

    public static double elapsed() {
		long time_ns = System.nanoTime() - START_TIME;
		double time_seconds = (double)time_ns/1e9;
		return(time_seconds);
    }

    public static void progress(boolean p, String message) {
		if(p) System.out.println(message + ". " + elapsed() + " seconds");
    }

    //////////////////////////////////////////////////////////////
}

