import hsvis.*;
import java.util.*;


/*
    Choose k from initial_range
    
    // constructor
    public AStarAlgorithm
	(final Heuristic H,final int k, int n, int[] initial_range, boolean progress)
 
    // return value: 
    //   search_info ={goal_node, post_bound}
    public Object[] search_iteratively(final int[] iselect)
    public Object[] search_iteratively(final Node root)

    @paper: Heuristic Search for Approximating One Matrix in Terms of Another Matrix, IJCAI 2021
    @authors: Guihong Wan, Haim Schweitzer, 9/10/2020.
 */

public class AStarAlgorithm {
    final private Heuristic H;
    final private int k;
    final private int n;
    private int[] initial_range;
    final private boolean progress;

    // FRINGE and CLOSED
    private Fringe FRINGE = null;
    private Closed CLOSED = null;

    // constructor
    public AStarAlgorithm
	(final Heuristic H, final int k, int n, final int[] range,boolean progress)
    {
	this.H = H;
	this.k = k;
	this.n = n;
	this.progress = progress;
	this.initial_range = range;
    }
    
    //create the children nodes
    private static ArrayList<Node> createChildrenList
	(final int[] parent, final int[]subset_xi,
	 final Object[] CH_info, final Heuristic H)
    {
		// subset_xi is nonempty
		ArrayList<Node> nodes = new ArrayList<Node>();
		for(int xi : subset_xi) {

		    int[] child_subset = Subsets.append(parent, xi);
		    double[] fg = H.child_heuristic(CH_info, xi);

		    // fg can be null
		    if(fg != null) {
			    Node node = new Node(child_subset, fg);
			    nodes.add(node);
		    }
		}
		return(nodes);
    }


    // create children, prun them, and add the pruned to the fringe
    private void add_pruned_children(final int[] parent) {

		int[] subset_xi = Children.pruned_subset_xi
		    (this.initial_range, parent, this.CLOSED);
		if(subset_xi == null || subset_xi.length == 0) return;
        
        Object[] CH_info = H.ChildHeuristic_info(parent); // expensive

		ArrayList<Node> children = createChildrenList(parent, subset_xi, CH_info, H);
        
		this.FRINGE.addAll(children);
    }

    // Return value is goal_node
    private Node search_once(final int[] iselect) {
		int k = this.k;
		if(this.CLOSED == null && this.FRINGE == null) {
		    this.CLOSED = new Closed(this.n);
		    this.FRINGE = new Fringe();
		}


		// add children of root node
		add_pruned_children(iselect);
		// all nodes in fringe have already been added to Closed
		
		while(!this.FRINGE.isEmpty_T()) {
		    Node node = this.FRINGE.poll_T(); //node is removed from top of heap
		    if(progress) node.println("Selected from fringe:");
		    if(node.selection_length() == k) 
			return(node); // goal node found

		    // here node is not a goal node.
		    // create children, prun them,  and add the unpruned to the fringe
		    add_pruned_children(node.selection());
		    AStar.progress(progress, " Fringe size =" + this.FRINGE.size());

		    if (this.FRINGE.size()%1000 == 0) System.gc();
		}
		return(null); // goal node not found
    }

    // return value:
    // search_info = {goal_node, post_bound}
    public Object[] search_iteratively(final int[] iselect) 
    {
	    Node goal_node = search_once(iselect);
	    if(goal_node == null)
		    Errors.errexitNoStack("goal node not found");
        
        // found goal node
	    double goal_node_value = goal_node.fg()[0]; // f_ss
	    Node f_min_node = Bounds.f_min_node(this.FRINGE);
	    double post_bound = 
		    Bounds.post_bound(goal_node_value, f_min_node);

	    System.out.println(" C size =" + this.CLOSED.size() + "," 
		       + " Fringe size =" + this.FRINGE.size());

		return(new Object[]
		    {goal_node,
		     post_bound
		     });
    }
}

