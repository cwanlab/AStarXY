import hsvis.*;
import java.util.*;

/*
  The head of this queue is the least element

    public void add(final Node n)
    public void addAll(final ArrayList<Node> N)
    public Node peek() // look at top without removing it
    public Node poll()
    public int size() 
    public boolean remove(final Node n)

    public Iterator<Node> iterator()

    The pruning threshold T is applied as follows:
    Nodes with f value above T are not considered.
    poll_T keeps removing nodes until a node at or below T is found
    peek_T also remove nodes until one at or below threshold is peeked on.

    public double T()
    public void update_pruning_threshold(double T) 
    public Node poll_T()
    public Node peek_T()
    public boolean isEmpty_T()

 */

public class Fringe {
    public Fringe() {}
    public Fringe(boolean pruning) { this.no_pruning = !pruning; }

    private boolean no_pruning = true;
    private PriorityQueue<Node> Q = new PriorityQueue<Node> ();

    public void add(final Node n) {
	Q.add(n);
    }
    public void addAll(final ArrayList<Node> N) {
	Q.addAll(N);
    }
    public Node peek() {return(Q.peek());} // look at top without removing it
    public Node poll() {return(Q.poll());} // remove top
    public int size() {return(Q.size());}
    public boolean remove(final Node n) { return(Q.remove(n)); }

    public Iterator<Node> iterator() { return(Q.iterator()); }

    private double T = Double.POSITIVE_INFINITY;
    public double T() {return(T);}
    
    public void update_pruning_threshold(double T) 
    { if(T < this.T) this.T = T; }

    public Node poll_T() {
	if(no_pruning) return(poll());
	Node n;
	while((n = poll()) != null)
	    if(n.fg()[0] <= T) return(n);
	return(null);
    }

    public Node peek_T() {
	if(no_pruning) return(peek());
	Node n;
	while((n = peek()) != null) {
	    if(n.fg()[0] <= T) return(n);
	    else poll();
	}
	return(null);
    }
    
    public boolean isEmpty_T() {
	Node n = peek_T();
	return(n == null);
    }

    public void println() {
	Object[] nodes = Q.toArray();
	System.out.println("Fringe size is " + nodes.length);
	for(int i = 0 ; i < nodes.length ; i++) {
	    Node n = (Node) nodes[i];
	    n.println(i + ":");
	}
	    
    }
    public void println(String message) {
	System.out.println("message");
	println();
    }
}

