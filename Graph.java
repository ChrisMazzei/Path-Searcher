import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of a weighted undirected Graph.
 * @param <T> The type that would be used as vertex.
 */
public class Graph<T> {
    final private HashMap<T, Set<T>> adjacencyList;
    final private HashMap<Set<T>, T> edgeMap;
    
    // Create Graph Object
    public Graph() {
        this.adjacencyList = new HashMap<>();
        this.edgeMap = new HashMap<>();
    }
    
    /**
     * Add vertex to the graph.
     * @param v The vertex object. 
     */
    public void addVertex(T v) {
        if (this.adjacencyList.containsKey(v)) {
            throw new IllegalArgumentException("Vertex already exists.");
        }
        
        this.adjacencyList.put(v, new HashSet<T>());
    }
    
    /**
     * Remove vertex from the graph.
     */
    public void removeVertex(T v) {
        if (!this.adjacencyList.containsKey(v)) {
            throw new IllegalArgumentException("Vertex doesn't exist.");
        }
        
        this.adjacencyList.remove(v);
        
        for (T u: this.getAllVertices()) {
            this.adjacencyList.get(u).remove(v);
        }
    }
    
    /**
     * Add new edge between vertices. Adding new edge from u to v should
     * automatically add new edge from v to u since the graph is undirected.
     * @param v Start vertex.
     * @param u Goal vertex.
     */
    public void addEdge(T v, T u, T t) {
        if (!this.adjacencyList.containsKey(v) || !this.adjacencyList.containsKey(u)) {
            throw new IllegalArgumentException();
        }
        
        this.adjacencyList.get(v).add(u);
        this.adjacencyList.get(u).add(v);
        Set<T> set = new HashSet<>();
        Collections.addAll(set, v, u);
        this.edgeMap.put(set, t);
    }
    
    /**
     * Remove edge from vertices. Since u is connected to v and v is connected to u both vertices must be disconnected.
     * @param v Start vertex
     * @param u Goal vertex
     */
    public void removeEdge(T v, T u) {
        if (!this.adjacencyList.containsKey(v) || !this.adjacencyList.containsKey(u)) {
            throw new IllegalArgumentException();
        }
        
        this.adjacencyList.get(v).remove(u);
        this.adjacencyList.get(u).remove(v);
        Set<T> set = new HashSet<>();
        Collections.addAll(set, v, u);
        this.edgeMap.remove(set);
    }
    
    /**
     * Get the value of the edge between two vertices.
     * @param v Start vertex
     * @param u Goal vertex
     * @return Value of the edge from v to u
     */
    public T getEdge(T v, T u) {
    	Set<T> set = new HashSet<>();
    	Collections.addAll(set, v, u);
    	return this.edgeMap.get(set);
    }
    
    /**
     * Check adjacency between 2 vertices in the graph.
     * @param v Start vertex.
     * @param u Destination vertex.
     * @return True if the vertex v and u are connected, otherwise false.
     */
    public boolean isAdjacent(T v, T u) {
        return this.adjacencyList.get(v).contains(u);
    }
    
    /**
     * Get vertices connected to a vertex.
     * @param v The vertex.
     * @return An Iterable for connected vertices.
     */
    public Iterable<T> getNeighbors(T v) {
        return this.adjacencyList.get(v);
    }
    
    /**
     * Get all vertices in the graph.
     * @return An Iterable for all vertices in the graph.
     */
    public Iterable<T> getAllVertices() {
        return this.adjacencyList.keySet();
    }
    
    /** Throw away function
    public ArrayList<List<T>> getVertices(T v) {
    	ArrayList<List<T>> listylist = new ArrayList<List<T>>();
    	ArrayList<T> list = new ArrayList<>();
    	for(Entry<Set<T>, T> entry : edgeMap.entrySet()) {
    		if(entry.getValue().equals(v)) {
    			list.addAll(entry.getKey());
    			listylist.add(list);
    			list = new ArrayList<>();
    		}
    	}
    	return listylist;
    }
    */
}